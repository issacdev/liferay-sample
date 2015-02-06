package com.dbs.portal.ui.component.data;

import com.dbs.portal.ui.component.view.IViewDataHandler;
import com.vaadin.data.Container;

public interface IDataProvider {
	public Container getDataContainer();
	
	public void addDataHandler(String dataId, IViewDataHandler handler);
}
