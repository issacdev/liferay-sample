package com.dbs.portal.ui.validator;

import org.apache.log4j.Logger;

import com.vaadin.data.validator.AbstractStringValidator;
import com.vaadin.ui.AbstractField;

public class EitherOneValidator extends AbstractStringValidator {	

	private AbstractField[] fields;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
//	public EitherOneValidator(String errorMessage) {
//		super(errorMessage);
//		// TODO Auto-generated constructor stub
//	}
	
	public EitherOneValidator(String errorMessage, AbstractField[] fields){
		super(errorMessage);
		this.fields = fields;
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected boolean isValidString(String value) {
		if (value == null)
			return false;
	
		for (AbstractField field : fields){
			if (field.getValue() != null && ((String)field.getValue()).trim().length() > 0){
				return true;
			}
		}
		
		return false;
	}
	
}
