package com.dbs.portal.ui.util;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
	private String BUNDLE_NAME = "languages.messages";
	private String COMMON_BUNDLE_NAME = "languages.common";
	private Locale locale = new Locale("zh","CN");
	private ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
	private ResourceBundle commonBundle = ResourceBundle.getBundle(COMMON_BUNDLE_NAME, locale);
	private Locale previousLocale = null;
	
	public Messages(){
	}
	
	public  String getString(String key) {
		try {
			String value = bundle.getString(key);
			if (value == null || value.equals(key)){
				return commonBundle.getString(key);
			}else
				return value;
		} catch (MissingResourceException e) {
			try{
				return commonBundle.getString(key);
			}catch (MissingResourceException ex) {
				return key;
			}
			
		}
	}
	
	public  String getString(String key, Locale locale) {
		try {
			String value = ResourceBundle.getBundle(BUNDLE_NAME, locale).getString(key);
			if (value == null || value.equals(key)){
				return ResourceBundle.getBundle(COMMON_BUNDLE_NAME, locale).getString(key);
			}else
				return value;
		} catch (MissingResourceException e) {
			try{
				return ResourceBundle.getBundle(COMMON_BUNDLE_NAME, locale).getString(key);
			}catch (MissingResourceException ex) {
				return key;
			}
			
		}
	}

	public void setLocale(Locale locale){
		if (!locale.equals(previousLocale)){
			bundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
			commonBundle = ResourceBundle.getBundle(COMMON_BUNDLE_NAME, locale);
			previousLocale = locale;
		}
	}
}
