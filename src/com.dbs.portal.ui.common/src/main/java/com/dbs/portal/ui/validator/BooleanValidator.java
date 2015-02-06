package com.dbs.portal.ui.validator;

import com.dbs.portal.ui.component.view.IController;
import com.dbs.portal.ui.component.view.IEnquiryView;
import com.dbs.portal.ui.component.view.IInit;
import com.dbs.portal.ui.component.view.IWindow;
import com.vaadin.data.validator.AbstractStringValidator;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.CheckBox;

public class BooleanValidator  extends AbstractStringValidator implements IInit{

	private IWindow view;
	private IController control;
	private String viewName;
	private String fieldName;
	private boolean expectedValue;
	
	public BooleanValidator(String messageKey, boolean expectedValue, String viewName, String fieldName){
		super(messageKey);
		setViewName(viewName);
		this.fieldName = fieldName;
		this.expectedValue = expectedValue;
	}
	
	@Override
	protected boolean isValidString(String value) {
		IEnquiryView targetView = (IEnquiryView)view.getView(viewName);
		if (targetView != null){
			AbstractComponent component = targetView.getComponent(fieldName);
			if (component != null && component instanceof CheckBox){
				return (expectedValue == ((Boolean) ((CheckBox)component).getValue()));
			}
		}
		return false;
	}

	@Override
	public void setView(IWindow view) {
		this.view = view;
	}

	@Override
	public void setControl(IController control) {
		this.control = control;
	}
	
	public void setViewName(String viewName){
		this.viewName = viewName;
	}
}
