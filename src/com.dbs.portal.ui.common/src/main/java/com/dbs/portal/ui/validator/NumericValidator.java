package com.dbs.portal.ui.validator;

import com.dbs.portal.ui.component.view.IController;
import com.dbs.portal.ui.component.view.IInit;
import com.dbs.portal.ui.component.view.IWindow;
import com.vaadin.data.validator.AbstractStringValidator;

public class NumericValidator extends AbstractStringValidator implements IInit {

	private IWindow window;
	private IController control;

	public NumericValidator(String errorMessage) {
		super(errorMessage);
	}

	public void setView(IWindow view) {
		this.window = view;
	}

	public void setControl(IController control) {
		this.control = control;
	}

	@Override
	public boolean isValidString(String value) {
		if (value != null && value.length() > 0) {
			for (char c : value.toCharArray()) {
				if (!Character.isDigit(c)) {
					return false;
				}
			}
		} else {
			return false;
		}

		return true;
	}

}