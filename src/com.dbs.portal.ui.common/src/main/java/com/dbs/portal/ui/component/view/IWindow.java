package com.dbs.portal.ui.component.view;

import java.util.Map;

import com.vaadin.ui.Window;

public interface IWindow {
	public void init();
	
	public IView getView(String name);
	
	public void setMapOfLayout(Map<String, IView> mapOfLayout);
	
	public Map<String, IView> getMapOfLayout();
	
	public void addWindow(Window window) throws IllegalArgumentException, NullPointerException;
	
	public void setNoResultViewInvisible();
	
	public void resetPage();
	
	public void updateTimeStamp();
	
	public String getId();
	
	public void setId(String id);
	
	public void retrieveInfo();
	
	public void setExporting(boolean isExporting);
	
}
