package com.dbs.portal.ui.component.pagetable;


public interface IPagedTableParameter {

	public String getAlignment() ;
	public void setAlignment(String alignment);

	public PagedTableParameterType getColumnType();
	public void setColumnType(PagedTableParameterType columnType);

	public String getPropertyId();
	
	public void setWidth(int width);
	public int getWidth();
	
	public void setShowComponent(PagedTableComponentVisibility showComponent);
	public PagedTableComponentVisibility getShowComponent();
	
	public void setMaxWidth(int maxWidth);
	public int getMaxWidth();
}

