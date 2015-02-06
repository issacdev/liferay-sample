package com.dbs.portal.ui.util;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.dbs.portal.ui.component.comboBox.ComboBoxItem;
import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;


public class LanguageSwitcher implements PropertyChangeListener{

	private static ThreadLocal<LanguageSwitcher> languageContext = new ThreadLocal<LanguageSwitcher>();
	private Map<AbstractComponent, LanguageInfo> captionCodeMap = new ConcurrentHashMap<AbstractComponent, LanguageInfo>();
	private Map<AbstractValidator, String> validatorCodeMap = new ConcurrentHashMap<AbstractValidator, String>();
	private Map<Table, Map<Object, String>> tableCodeMap = new ConcurrentHashMap<Table, Map<Object, String>>();
	private static Messages messages;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	public LanguageSwitcher(){
//		if (LanguageSwitcher.getLanguageSwitcher() == null)
			LanguageSwitcher.setLanguageSwitcher(this);
			
			LocaleChangeSupport.getInstance().addChangeLocaleListener(this);
	}
	
	public static void setLanguageSwitcher(LanguageSwitcher ctx) {
		languageContext.set(ctx);
	}

	public static LanguageSwitcher getLanguageSwitcher() {
		if (languageContext.get() == null){
			new LanguageSwitcher();
		}
		return languageContext.get();
	}
	
	public void changeCode(AbstractComponent component){
		if (component != null){
			if (component instanceof Label){
				Label l = (Label)component;
				if (l.getValue() != null){
					LanguageInfo info = new LanguageInfo(l.getValue().toString(), null);
					captionCodeMap.put(component, info);
					l.setValue(messages.getString(l.getValue().toString()));
				}
			}else if (component instanceof Table){
				
				Table table = (Table)component;
				String[] headerKey = table.getColumnHeaders();
				Object[] visibleColumn = table.getVisibleColumns();
				
				Map<Object, String> propertyColumnMap = new HashMap<Object, String>();
				String[] headerColumns = new String[visibleColumn.length];
				
				for (int i = 0 ; i < visibleColumn.length ; i++){
					propertyColumnMap.put(visibleColumn[i], headerKey[i]);
					headerColumns[i] = messages.getString(headerKey[i]);
				}
				tableCodeMap.put(table, propertyColumnMap);
				
				//change language for table header
				table.setColumnHeaders(headerColumns);
				
				String tableCaption = table.getCaption();
				if (tableCaption != null && tableCaption.length() > 0){
					propertyColumnMap.put("TABLECAPTION", tableCaption);
					table.setCaption(messages.getString(tableCaption));
				}
				
			}else{
				LanguageInfo info = new LanguageInfo();
				info.setMessage(LanguageInfo.CAPTION, component.getCaption());
				if (component instanceof AbstractField){
					AbstractField field = (AbstractField)component;
					
					info.setMessage(LanguageInfo.REQUIRED,field.getRequiredError());
					((AbstractField)component).setRequiredError(messages.getString(field.getRequiredError()));
					
					if (component instanceof AbstractSelect){
						AbstractSelect selection = (AbstractSelect)component;
						Collection items = selection.getItemIds();
						for (Iterator it = items.iterator() ; it.hasNext() ; ){
							Object obj = it.next();
							if (obj instanceof ComboBoxItem){
								ComboBoxItem item = (ComboBoxItem)obj;
								item.setName(messages.getString(item.getMessageKey()));
							}
						}
						
					}else if (field instanceof TextArea){
						if (field.getValue() !=null){
							info.setMessage(LanguageInfo.CONTENT, (String)field.getValue());
							field.setValue(messages.getString((String)field.getValue()));
						}
					}
				}
				captionCodeMap.put(component, info);
				if (component.getCaption() != null)
					component.setCaption(messages.getString(component.getCaption()));
			}
		}
	}
	
	public void changeCode(AbstractValidator validator){
		if (validator != null){
			validatorCodeMap.put(validator, validator.getErrorMessage());
			validator.setErrorMessage(messages.getString(validator.getErrorMessage()));
		}
	}
	
	public void switchLanguage(){
		
		//fields
		for (Iterator<AbstractComponent> it = captionCodeMap.keySet().iterator() ; it.hasNext(); ){
			AbstractComponent component = it.next();
			if (component != null){
				LanguageInfo info = captionCodeMap.get(component);
				if (component instanceof Label){
					Label l = (Label)component;
					if (info.getMessage(LanguageInfo.CAPTION) != null && messages.getString(info.getMessage(LanguageInfo.CAPTION)) != null){
						l.setValue(messages.getString(info.getMessage(LanguageInfo.CAPTION)));
					}
				}else{
					if (info.getMessage(LanguageInfo.CAPTION) != null)
						component.setCaption(messages.getString(info.getMessage(LanguageInfo.CAPTION)));
					if (component instanceof AbstractField ){
						AbstractField field = ((AbstractField)component);
						if (field.isRequired()){
							field.setRequiredError(messages.getString(info.getMessage(LanguageInfo.REQUIRED)));
						}
						if (field instanceof TextArea){
							TextArea area = (TextArea)field;
							if (info.getMessage(LanguageInfo.CONTENT) != null){
								area.setValue(messages.getString(info.getMessage(LanguageInfo.CONTENT)));
								area.setCaption(null);
							}
						}
					}
					
					if (component instanceof AbstractSelect){
						AbstractSelect selection = (AbstractSelect)component;
						Collection items = selection.getItemIds();
						for (Iterator itSelect = items.iterator() ; itSelect.hasNext() ; ){
							Object obj = itSelect.next();
							if (obj instanceof ComboBoxItem){
								ComboBoxItem item = (ComboBoxItem)obj;
								item.setName(messages.getString(item.getMessageKey()));
							}
						}
						
					}
				}
			}
		}
		
		//validator
		for (Iterator<AbstractValidator> it = validatorCodeMap.keySet().iterator() ; it.hasNext(); ){
			AbstractValidator validator = it.next();
			if (validator != null){
				validator.setErrorMessage(messages.getString(validatorCodeMap.get(validator)));
			}
		}
		
		//table
		for (Iterator<Table> it = tableCodeMap.keySet().iterator() ; it.hasNext() ;){
			Table table = it.next();
			Map<Object, String> propertyColumnMap = tableCodeMap.get(table);
			
			Object[] visibleColumn = table.getVisibleColumns();
			
			String[] headerColumn = new String[visibleColumn.length];
			
			for (int i = 0 ; i < visibleColumn.length ; i++){
				if (propertyColumnMap.get(visibleColumn[i]) != null)
					headerColumn[i] = messages.getString(propertyColumnMap.get(visibleColumn[i]));
			}
			
			table.setColumnHeaders(headerColumn);
			
			if (propertyColumnMap.get("TABLECAPTION") !=  null){
				table.setCaption(messages.getString(propertyColumnMap.get("TABLECAPTION")));
			}
		}
	}
	
	public static void setMessages(Messages msg){
		messages = msg;
	}
	
	public Messages getMessages(){
		return messages;
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		Locale locale = (Locale)event.getNewValue();
		messages.setLocale(locale);
		this.switchLanguage();
	}
	
}
