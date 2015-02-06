package com.dbs.portal.ui.component.pagetable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dbs.portal.ui.component.view.IViewDataHandler;

public class MultiPagedTableParameter {

	private boolean isLanguageChange = false;
	private PagedTableComponentVisibility showComponent;
	private List<String> styleList = null;

	private IViewDataHandler dataHandler;
	private List<Object> listener;
	private List<Object> validators;
	private String labelKey = "";
	private boolean isUpperCase = true;
	private int width = -1;
	private Map<Integer, List<String>> permissionMap = new HashMap<Integer, List<String>>();

	public void setLanguageChange(boolean isLanguageChange) {
		this.isLanguageChange = isLanguageChange;
	}

	public boolean isLanguageChange() {
		return this.isLanguageChange;
	}

	public void setLabelKey(String labelKey) {
		this.labelKey = labelKey;
	}

	public String getLabelKey() {
		return this.labelKey;
	}

	public void setListenerList(List<Object> listener) {
		this.listener = listener;
	}

	public List<Object> getListenerList() {
		return this.listener;
	}

	public void setDataHandler(IViewDataHandler dataHandler) {
		this.dataHandler = dataHandler;
	}

	public IViewDataHandler getDataHandler() {
		return this.dataHandler;
	}

	public void setValidators(List<Object> validators) {
		this.validators = validators;
	}

	public List<Object> getValidators() {
		return this.validators;
	}

	public void setUpperCase(boolean isUpperCase) {
		this.isUpperCase = isUpperCase;
	}

	public boolean isUpperCase() {
		return this.isUpperCase;
	}

	public void setStyleList(List<String> styleList) {
		this.styleList = styleList;
	}

	public List<String> getStyleList() {
		return this.styleList;
	}

	public void setShowComponent(PagedTableComponentVisibility showComponent) {
		this.showComponent = showComponent;
	}

	public PagedTableComponentVisibility getShowComponent() {
		return this.showComponent;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public Map<Integer, List<String>> getPermissionMap() {
		return permissionMap;
	}

	public void setPermissionMap(Map<Integer, List<String>> permissionMap) {
		this.permissionMap = permissionMap;
	}
}
