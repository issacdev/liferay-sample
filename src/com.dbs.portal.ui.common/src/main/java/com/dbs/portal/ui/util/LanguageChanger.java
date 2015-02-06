package com.dbs.portal.ui.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import com.dbs.portal.ui.component.comboBox.ComboBoxItem;
import com.vaadin.data.Validator;
import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;

public class LanguageChanger {
	
	private Messages messages = null;
	
//	private Map<AbstractComponent, LanguageInfo> captionCodeMap = new ConcurrentHashMap<AbstractComponent, LanguageInfo>();
//	private Map<AbstractValidator, String> validatorCodeMap = new ConcurrentHashMap<AbstractValidator, String>();
//	private Map<Table, Map<Object, String>> tableCodeMap = new ConcurrentHashMap<Table, Map<Object, String>>();
	
	public Messages getMessages(){
		return this.messages;
	}
	
	public void setMessages(Messages messages){
		this.messages = messages;
	}
	
	public void changeCode(AbstractComponent component){
		if (component != null){
			if (component instanceof Label){
				Label l = (Label)component;
				if (l.getValue() != null){
//					LanguageInfo info = new LanguageInfo(l.getValue().toString(), null);
//					captionCodeMap.put(component, info);
					l.setValue(messages.getString(l.getValue().toString()));
				}
			}else if (component instanceof Button){
				Button b = (Button)component;
				if (b.getCaption() != null){
					b.setCaption(messages.getString(b.getCaption()));
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
//				tableCodeMap.put(table, propertyColumnMap);
				
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
					
					Collection<Validator> validators = field.getValidators();
					if (validators != null)
						for (Validator validator : validators){
							changeCode(validator);
						}
					
					info.setMessage(LanguageInfo.REQUIRED,field.getRequiredError());
					((AbstractField)component).setRequiredError(messages.getString(field.getRequiredError()));
					
					if (component instanceof AbstractSelect){
						AbstractSelect selection = (AbstractSelect)component;
						Collection items = selection.getItemIds();
						
						for (Iterator it = items.iterator() ; it.hasNext() ; ){
							Object obj = it.next();
							if (obj instanceof ComboBoxItem){
								ComboBoxItem item = (ComboBoxItem)obj;
								if (component instanceof ComboBox)
									item.setName(messages.getString(item.getMessageKey(), Locale.US));
								else
									item.setName(messages.getString(item.getMessageKey()));
							}
						}
						
					}else if (field instanceof TextArea){
						if (field.getValue() !=null){
							info.setMessage(LanguageInfo.CONTENT, (String)field.getValue());
							field.setValue(messages.getString((String)field.getValue()));
						}
					}else if (field instanceof PopupDateField){
						PopupDateField dateField = (PopupDateField)field;
						dateField.setParseErrorMessage(messages.getString("common.date.validation.format"));
					}
				}
//				captionCodeMap.put(component, info);
				if (component.getCaption() != null)
					component.setCaption(messages.getString(component.getCaption()));
			}
		}
	}
	
	public void changeCode(Validator validator){
		if (validator != null){
//			validatorCodeMap.put((AbstractValidator)validator, ((AbstractValidator)validator).getErrorMessage());
			((AbstractValidator)validator).setErrorMessage(messages.getString(((AbstractValidator)validator).getErrorMessage()));
		}
	}
	
//	public LanguageInfo getI18nLabel(AbstractComponent component){
//		return this.captionCodeMap.get(component);
//	}
	
}
