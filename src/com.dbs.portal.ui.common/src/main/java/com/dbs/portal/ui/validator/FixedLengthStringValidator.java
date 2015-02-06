package com.dbs.portal.ui.validator;

import com.dbs.portal.ui.component.view.IController;
import com.dbs.portal.ui.component.view.IInit;
import com.dbs.portal.ui.component.view.IWindow;
import com.vaadin.data.validator.AbstractStringValidator;

public class FixedLengthStringValidator  extends AbstractStringValidator implements IInit{

	private IWindow window;
	private IController control;
	private int fixedLength = 1;
	
	public FixedLengthStringValidator(String errorMessage) {
		super(errorMessage);
	}
	
	public void setView(IWindow view) {
		this.window = view;
	}

	public void setControl(IController control) {
		this.control = control;
	}

	@Override
	protected boolean isValidString(String value) {
		
		if (value == null)
			return false;
		
		if (value.length() != fixedLength)
			return false;
		
		return true;
	}
	
	public void setFixedLength(int fixedLength){
		this.fixedLength = fixedLength;
	}
	
	public int getFixedLength(){
		return this.fixedLength;
	}
	
	

}
