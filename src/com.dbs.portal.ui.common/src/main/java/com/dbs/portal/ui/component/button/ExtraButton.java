package com.dbs.portal.ui.component.button;

import java.util.List;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;

public class ExtraButton {
	private NormalButton button;
	private String buttonName;
	private List<ClickListener> listener;
	private boolean visible = true;
	
	public ExtraButton(){
		this.button = new NormalButton();
	}
	
	public String getButtonName() {
		return buttonName;
	}
	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}
	public List<ClickListener> getListener() {
		return listener;
	}
	public void setListener(List<ClickListener> listener) {
		this.listener = listener;
	}
	
	public void setVisible(boolean visible){
		this.visible = visible;
	}
	
	public boolean getVisible(){
		return this.visible;
	}
	
	public Button getButton(){
		return this.button;
	}
	
	
}
