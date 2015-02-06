package com.dbs.portal.ui.component.view;

import java.util.List;
import java.util.Map;

import com.dbs.portal.ui.component.pagetable.IPagedTableParameter;
import com.dbs.portal.ui.component.pagetable.PagedTableDataType;
import com.vaadin.data.Container;

public interface ITableResultView extends IResultView{
	public Object[] getVisibleColumnHeader();
	
	public List<String> getCurrentCollapseColumnHeader();
	
	public void setSavedColumnHeader(Object[] dataId, String[] collapsedDataIdList);
	
	public Map<String, IPagedTableParameter> getPagedTableParameterMap();
	
	public Object[] getCurrentVisibleColumn();
	
	public String[] getCurrentVisibleColumnName();
	
	public void activateColumn(String gorupKey);
	
	public void clearActivatedColumn();
	
	public void setDeleteButtonEnable(boolean deleteButtonEnable);
	
	public Object[] getOriginalColumnDataId();
	
	public String[] getOriginalColumnHeader();
	
	public Object[] getVisibleColumns();
	
	public Container getContainer();
	
	public List<PagedTableDataType> getVisibleColumnDataType();
	
	public String getFunctionName();
	
	public List<String> getTotalColumnId();
	
	public void sort(Object[] columnIds, boolean[] ascending);
}
