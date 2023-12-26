package dev.tcl.gui.controllers.dropdown;

import dev.tcl.api.Option;
import dev.tcl.api.utils.Dimension;
import dev.tcl.gui.AbstractWidget;
import dev.tcl.gui.TCLScreen;

import java.util.List;

public class DropdownStringController extends AbstractDropdownController<String> {

	public DropdownStringController(Option<String> option, List<String> allowedValues, boolean allowEmptyValue, boolean allowAnyValue) {
		super(option, allowedValues, allowEmptyValue, allowAnyValue);
	}

	@Override
	public String getString() {
		return option().pendingValue();
	}

	@Override
	public void setFromString(String value) {
		option().requestSet(getValidValue(value));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AbstractWidget provideWidget(TCLScreen screen, Dimension<Integer> widgetDimension) {
		return new DropdownStringControllerElement(this, screen, widgetDimension);
	}

}
