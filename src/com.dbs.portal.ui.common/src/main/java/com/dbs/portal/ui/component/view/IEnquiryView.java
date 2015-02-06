package com.dbs.portal.ui.component.view;

import java.util.Map;

import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Button.ClickListener;

public interface IEnquiryView extends IView{
	public Map<String, Object> submit();
	
	public boolean validate();
	
	public boolean validate(boolean reset);
	
	public void setProcessClickListener(ClickListener listener);
	
	public void setResetClickListener(ClickListener listener);
	
	public void setExportClickListener(ClickListener listener);
	
	public void setSubscribeClickListener(ClickListener listener);
	
	public void setSaveTablePreferenceClickList(ClickListener listener);
	
	public AbstractComponent getComponent(String dataId);
	
	public Map<String, String> getDisplayMap();
	
	public Map<String, String> getDisplayMap(boolean subscription);
	
	public Map<String, String> getDisplayMap(boolean subscription, boolean display);
	
	public void resetPage();
	
	public void resetForm();
	
	public void setSupportPageReset(boolean pageReset);
	
	public Map<String, Object> getLastCriteria();
	
	public void retrieveInfo();
	
	public void updateData(Map<String, Object> dataMap);
	
	public Map<String, Object> submit(boolean reset);
	
	public Map<String, Object> submit(boolean reset, boolean isSubscription);
	
	public void colapseView();
	
	public void colapseView(boolean collapse);
	
	public boolean validateSubscription();
	
	public boolean isExportButtonVisible();
	
	public boolean isSubscribeButtonVisible();
	
	public void enablePreferenceButton(boolean enable);
}
