package com.dbs.portal.ui.component.pagetable;

import java.util.Map;

import com.dbs.portal.ui.component.application.IApplication;

public interface PagedTableComponentVisibility {
	public boolean showComponent(Map<String, Object> data);
	public void setApplication(IApplication application);
}
