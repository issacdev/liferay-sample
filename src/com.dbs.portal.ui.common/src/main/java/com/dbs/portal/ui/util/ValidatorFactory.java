package com.dbs.portal.ui.util;

import com.dbs.portal.ui.validator.BooleanValidator;
import com.dbs.portal.ui.validator.EitherOneValidator;
import com.dbs.portal.ui.validator.LongValidator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.AbstractField;

public class ValidatorFactory {
	private static ValidatorFactory instance = null;
	
	private ValidatorFactory(){}
	
	public static ValidatorFactory getInstance(){
		if (instance == null){
			synchronized (ValidatorFactory.class) {
				instance = new ValidatorFactory();
			}
		}
		
		return instance;
	}
	
	public StringLengthValidator createStringLengthValidator(String messageKey, int minLength, int maxLength, boolean allowNull){
		StringLengthValidator validator = new  StringLengthValidator(messageKey, minLength, maxLength, allowNull);
//		LanguageSwitcher.getLanguageSwitcher().changeCode(validator);
		return validator;
	}
	
	public LongValidator createIntegerValidator(String messageKey){
		LongValidator validator = new LongValidator(messageKey);
//		LanguageSwitcher.getLanguageSwitcher().changeCode(validator);
		return validator;
	}
	
	public EmailValidator createEmailValidator(String messageKey){
		EmailValidator validator = new EmailValidator(messageKey);
//		LanguageSwitcher.getLanguageSwitcher().changeCode(validator);
		return validator;
	}
	
	public EitherOneValidator createEitherOneValidtor(String messageKey, AbstractField[] fields){
		EitherOneValidator validator = new EitherOneValidator(messageKey, fields);
//		LanguageSwitcher.getLanguageSwitcher().changeCode(validator);
		return validator;
	}
	
	public BooleanValidator createBooleanValidator(String messageKey, boolean expectedValue, String viewName, String fieldName){
		BooleanValidator validator = new BooleanValidator(messageKey, expectedValue, viewName, fieldName);
//		LanguageSwitcher.getLanguageSwitcher().changeCode(validator);
		return validator;
	}

}
