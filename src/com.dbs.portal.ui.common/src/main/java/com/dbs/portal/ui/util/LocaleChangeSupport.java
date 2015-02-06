package com.dbs.portal.ui.util;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Locale;

public class LocaleChangeSupport {
	private PropertyChangeSupport support = new PropertyChangeSupport(this);
	
	private static LocaleChangeSupport instance = null;
	
	private LocaleChangeSupport(){}
	
	public static LocaleChangeSupport getInstance(){
		if (instance == null){
			synchronized (LocaleChangeSupport.class) {
				instance = new LocaleChangeSupport();
			}
		}
		
		return instance;
	}
	
	public void addChangeLocaleListener(PropertyChangeListener listener){
		support.addPropertyChangeListener(listener);
	}
	
	public void removeChangeLocalListener(PropertyChangeListener listener){
		support.removePropertyChangeListener(listener);
	}
	
	public void localChange(Locale locale){
		support.firePropertyChange("", "", locale);
	}
}
