package com.dbs.portal.ui.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dbs.portal.ui.component.view.IController;
import com.dbs.portal.ui.component.view.IInit;
import com.dbs.portal.ui.component.view.IWindow;
import com.vaadin.data.validator.AbstractStringValidator;

public class UrlValidatorEnquiryView extends AbstractStringValidator implements IInit{

	private IWindow window;
	private IController control;
	
	public UrlValidatorEnquiryView(String errorMessage) {
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
		
		Pattern pattern = Pattern.compile("^[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,3}(/\\S*)?$");
		
	    Matcher matcher = pattern.matcher(value);
	    
        while (matcher.find()) {
        	return true;
        }
		
		return false;
	}
	

}
