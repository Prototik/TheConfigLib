package dev.tcl.test;

import com.google.common.collect.Lists;
import dev.tcl.api.NameableEnum;
import dev.tcl.api.Option;
import dev.tcl.api.TheConfigLib;
import dev.tcl.api.controller.ControllerBuilder;
import dev.tcl.api.controller.StringControllerBuilder;
import dev.tcl.config.api.ConfigClassHandler;
import dev.tcl.config.api.ConfigField;
import dev.tcl.config.api.SerialEntry;
import dev.tcl.config.api.autogen.*;
import dev.tcl.config.api.autogen.Label;
import dev.tcl.config.api.serializer.GsonConfigSerializerBuilder;
import dev.tcl.gui.ValueFormatters;
import dev.tcl.platform.TCLPlatform;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.List;

public class AutogenConfigTest {
    public static final ConfigClassHandler<AutogenConfigTest> INSTANCE = ConfigClassHandler.createBuilder(AutogenConfigTest.class)
            .id(new ResourceLocation(TheConfigLib.MOD_ID, "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(TCLPlatform.getConfigDir().resolve("tcl-test-v2.json5"))
                    .setJson5(true)
                    .build())
            .build();

    @AutoGen(category = "test", group = "master_test")
    @MasterTickBox({"testTickBox", "testBoolean", "testInt", "testDouble", "testFloat", "testLong", "testIntField", "testDoubleField", "testFloatField", "testLongField", "testEnum", "testEnumDropdown", "testColor", "testString", "testDropdown", "testItem"})
    @SerialEntry(comment = "This option disables all the other options in this group")
    public boolean masterOption = true;

    @AutoGen(category = "test", group = "master_test")
    @TickBox
    @SerialEntry(comment = "This is a cool comment omg this is amazing")
    public boolean testTickBox = true;

    @AutoGen(category = "test", group = "master_test")
    @Bool(formatter = Bool.Formatter.YES_NO, colored = true)
    @SerialEntry(comment = "This is a cool comment omg this is amazing")
    public boolean testBoolean = true;

    @AutoGen(category = "test", group = "master_test")
    @IntSlider(min = 0, max = 10, step = 2)
    @SerialEntry public int testInt = 0;

    @AutoGen(category = "test", group = "master_test")
    @DoubleSlider(min = 0.1, max = 10.2, step = 0.1)
    @SerialEntry public double testDouble = 0.1;

    @AutoGen(category = "test", group = "master_test")
    @FloatSlider(min = 0.0f, max = 1f, step = 0.01f)
    @CustomFormat(ValueFormatters.PercentFormatter.class)
    @CustomName("A cool percentage.")
    @SerialEntry public float testFloat = 0.1f;

    @AutoGen(category = "test", group = "master_test")
    @LongSlider(min = 0, max = 10, step = 2)
    @SerialEntry public long testLong = 0;

    @AutoGen(category = "test", group = "master_test")
    @IntField(min = 0, max = 10)
    @SerialEntry public int testIntField = 0;

    @AutoGen(category = "test", group = "master_test")
    @DoubleField(min = 0.1, max = 10.2)
    @SerialEntry public double testDoubleField = 0.1;

    @AutoGen(category = "test", group = "master_test")
    @FloatField(min = 0.1f, max = 10.2f)
    @SerialEntry public float testFloatField = 0.1f;

    @AutoGen(category = "test", group = "master_test")
    @LongField(min = 0, max = 10)
    @SerialEntry public long testLongField = 0;

    @AutoGen(category = "test", group = "master_test")
    @EnumCycler
    @SerialEntry public Alphabet testEnum = Alphabet.A;

    @AutoGen(category = "test", group = "master_test")
    @EnumDropdown
    @SerialEntry
    public Alphabet testEnumDropdown = Alphabet.A;

    @AutoGen(category = "test", group = "master_test")
    @ColorField
    @SerialEntry public Color testColor = new Color(0xFF0000FF, true);

    @AutoGen(category = "test", group = "master_test")
    @StringField
    @SerialEntry public String testString = "Test string";

    @AutoGen(category = "test", group = "master_test")
    @Dropdown(values = {"Apple", "Banana", "Cherry", "Date"}, allowAnyValue = true)
    @SerialEntry public String testDropdown = "Cherry";

    @AutoGen(category = "test", group = "master_test")
    @ItemField
    @SerialEntry public Item testItem = Items.AZURE_BLUET;

    @AutoGen(category = "test", group = "misc") @Label
    private final Component testLabel = Component.literal("Test label");

    @AutoGen(category = "test")
    @ListGroup(valueFactory = TestListFactory.class, controllerFactory = TestListFactory.class)
    @SerialEntry public List<String> testList = Lists.newArrayList("A", "B");

    public enum Alphabet implements NameableEnum {
        A, B, C;

        @Override
        public @NotNull Component getDisplayName() {
            return Component.literal(name());
        }
    }

    public static class TestListFactory implements ListGroup.ValueFactory<String>, ListGroup.ControllerFactory<String> {
        @Override
        public String provideNewValue() {
            return "";
        }

        @Override
        public ControllerBuilder<String> createController(ListGroup annotation, ConfigField<List<String>> field, OptionAccess storage, Option<String> option) {
            return StringControllerBuilder.create(option);
        }
    }
}
