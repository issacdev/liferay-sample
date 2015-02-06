package com.dbs.portal.ui.component.comboBox;

public class ComboBoxItem {
	private String name;
	private Object item;
	private String messageKey = null;
	
	public ComboBoxItem(String name, Object item){
		setName(name);
		setItem(item);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		setMessageKey(name);
	}

	public Object getItem() {
		return item;
	}

	public void setItem(Object item) {
		this.item = item;
	}
	
	public String toString(){
		return name;
	}
	
	public String getMessageKey(){
		return messageKey;
	}
	
	private void setMessageKey(String name){
		if (messageKey == null)
			messageKey = name;
	}
}
