package com.dbs.portal.ui.component.view;

import com.vaadin.data.Container;
import com.vaadin.ui.AbstractComponent;

public interface IResultView extends IView{
	
	public void updateContent(Container container);
	
	public AbstractComponent getComponent(String dataId);
	
	public String getId();
	
	public void setId(String id);
	
	public void setDataKey(String dataKey);
	
	public String getDataKey();
}
