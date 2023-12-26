package dev.tcl.gui.image;

import dev.tcl.api.TheConfigLib;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Environment(EnvType.CLIENT)
public class TCLImageReloadListener implements PreparableReloadListener {
    @Override
    public @NotNull CompletableFuture<Void> reload(
            PreparationBarrier preparationBarrier, 
            ResourceManager resourceManager, 
            ProfilerFiller preparationsProfiler, 
            ProfilerFiller reloadProfiler, 
            Executor backgroundExecutor, 
            Executor gameExecutor
    ) {
        Map<ResourceLocation, Resource> imageResources = resourceManager.listResources(
                "",
                location -> ImageRendererManager.PRELOADED_IMAGE_FACTORIES
                        .stream()
                        .anyMatch(factory -> factory.predicate().test(location))
        );

        // extreme mojang hackery.
        // for some reason this wait method needs to be called for the reload
        // instance to be marked as complete
        if (imageResources.isEmpty()) {
            preparationBarrier.wait(null);
        }

        List<CompletableFuture<?>> futures = new ArrayList<>(imageResources.size());

        for (Map.Entry<ResourceLocation, Resource> entry : imageResources.entrySet()) {
            ResourceLocation location = entry.getKey();
            Resource resource = entry.getValue();

            ImageRendererFactory imageFactory = ImageRendererManager.PRELOADED_IMAGE_FACTORIES
                    .stream()
                    .filter(factory -> factory.predicate().test(location))
                    .map(factory -> factory.factory().apply(location))
                    .findAny()
                    .orElseThrow();

            CompletableFuture<Optional<ImageRenderer>> imageFuture =
                    CompletableFuture.supplyAsync(
                            () -> ImageRendererManager.safelyPrepareFactory(
                                    location, imageFactory
                            ),
                            backgroundExecutor
                    )
                            .thenCompose(preparationBarrier::wait)
                            .thenApplyAsync(imageSupplierOpt -> {
                                if (imageSupplierOpt.isEmpty()) {
                                    return Optional.empty();
                                }
                                ImageRendererFactory.ImageSupplier supplier = imageSupplierOpt.get();

                                ImageRenderer imageRenderer;
                                try {
                                    imageRenderer = supplier.completeImage();
                                } catch (Exception e) {
                                    TheConfigLib.LOGGER.error("Failed to create image '{}'", location, e);
                                    return Optional.empty();
                                }

                                ImageRendererManager.PRELOADED_IMAGE_CACHE.put(location, imageRenderer);

                                return Optional.of(imageRenderer);
                            }, gameExecutor);

            futures.add(imageFuture);

            imageFuture.whenComplete((result, throwable) -> {
                if (throwable != null) {
                    CrashReport crashReport = CrashReport.forThrowable(throwable, "Failed to load image");
                    CrashReportCategory category = crashReport.addCategory(TheConfigLib.SHORT_NAME + " Gui");
                    category.setDetail("Image identifier", location.toString());
                    throw new ReportedException(crashReport);
                }
            });
        }

        return CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new));
    }
}
