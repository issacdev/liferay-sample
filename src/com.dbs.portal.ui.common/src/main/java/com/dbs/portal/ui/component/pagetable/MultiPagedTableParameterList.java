package com.dbs.portal.ui.component.pagetable;

import java.util.List;

import com.vaadin.ui.Table;

public class MultiPagedTableParameterList implements IPagedTableParameter{
	private String alignment = Table.ALIGN_LEFT;
	private PagedTableParameterType columnType = PagedTableParameterType.STRING;
	private String propertyId;
	private PagedTableComponentVisibility showComponent;
	private int width = -1;
	private int maxWidth = -1;
	
	private List<MultiPagedTableParameter> parameters = null;
	
	public MultiPagedTableParameterList(String columnName){
		this.propertyId = columnName;
	}

	public String getAlignment() {
		return alignment;
	}

	public void setAlignment(String alignment) {
		this.alignment = alignment;
	}

	public PagedTableParameterType getColumnType() {
		return columnType;
	}

	public void setColumnType(PagedTableParameterType columnType) {
		this.columnType = columnType;
	}

	public String getPropertyId() {
		return propertyId;
	}
	
	public void setWidth(int width){
		this.width = width;
	}
	
	public int getWidth(){
		return this.width;
	}
	
	public void setShowComponent(PagedTableComponentVisibility showComponent){
		this.showComponent = showComponent;
	}
	
	public PagedTableComponentVisibility getShowComponent(){
		return this.showComponent;
	}
	
	public void setMaxWidth(int maxWidth){
		this.maxWidth = maxWidth;
	}
	
	public int getMaxWidth(){
		return this.maxWidth;
	}
	
	public void setParameters(List<MultiPagedTableParameter> parameters){
		this.parameters = parameters;
	}
	
	public List<MultiPagedTableParameter> getParameters(){
		return this.parameters;
	}

}

