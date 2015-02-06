package com.dbs.portal.ui.component.pagetable;

import java.util.List;

import com.dbs.portal.ui.component.view.IViewDataHandler;
import com.vaadin.ui.Table;

public class PagedTableParameter implements IPagedTableParameter{
	private String alignment = Table.ALIGN_LEFT;
	private PagedTableParameterType columnType = PagedTableParameterType.STRING;
	private String propertyId;
	private boolean isLanguageChange = false;
	private PagedTableComponentVisibility showComponent;
	private List<String> styleList = null;
	
	private IViewDataHandler dataHandler;
	
	private List<Object> listener;
	
	private List<Object> validators;
	
	private String labelKey = "";
	
	private int width = -1;
	
	private boolean isUpperCase = true;
	
	private int maxWidth = -1;
	
	public PagedTableParameter(String columnName){
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
	
	public void setLanguageChange(boolean isLanguageChange){
		this.isLanguageChange = isLanguageChange;
	}
	
	public boolean isLanguageChange(){
		return this.isLanguageChange;
	}
	
	public void setLabelKey(String labelKey){
		this.labelKey = labelKey;
	}
	
	public String getLabelKey(){
		return this.labelKey;
	}
	
	
	public void setListenerList(List<Object> listener){
		this.listener = listener;
	}
	
	public List<Object> getListenerList(){
		return this.listener;
	}
	
	public void setDataHandler(IViewDataHandler dataHandler){
		this.dataHandler = dataHandler;
	}
	
	public IViewDataHandler getDataHandler(){
		return this.dataHandler;
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
	
	public void setValidators(List<Object> validators){
		this.validators = validators;
	}
	public List<Object> getValidators(){
		return this.validators;
	}
	
	public void setUpperCase(boolean isUpperCase){
		this.isUpperCase = isUpperCase;
	}
	
	public boolean isUpperCase(){
		return this.isUpperCase;
	}
	
	public void setMaxWidth(int maxWidth){
		this.maxWidth = maxWidth;
	}
	
	public int getMaxWidth(){
		return this.maxWidth;
	}
	
	public void setStyleList(List<String> styleList){
		this.styleList = styleList;
	}
	
	public List<String> getStyleList(){
		return this.styleList;
	}
}

