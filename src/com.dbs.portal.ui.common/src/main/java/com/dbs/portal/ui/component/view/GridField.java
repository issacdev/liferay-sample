package com.dbs.portal.ui.component.view;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Label;

public class GridField {
	
	private GridFieldType fieldType = GridFieldType.LABEL;
	
	private int xCordinate = 0;
	
	private int yCordinate = 0;
	
	private int columnSpan = 0;
	
	private int rowSpan = 0;
	
	private boolean isBold = false;
	
	private Alignment alignment = Alignment.MIDDLE_LEFT;
	
	private String fieldNameKey;
	
	private String dataId;
	
	private AbstractComponent component;
	
	private boolean visible;
	
	private boolean enabled;
	
	private boolean noStyle = false;
	
	private int contentMode = Label.CONTENT_PREFORMATTED;
	
	private List styleList = new ArrayList();
	
	private boolean haveColon = false;
	
	public GridField(GridFieldType fieldType, String fieldNameKey, String dataId, int xCordinate, int yCordinate){
		this.fieldType = fieldType;
		this.fieldNameKey = fieldNameKey;
		this.dataId = dataId;
		this.xCordinate = xCordinate;
		this.yCordinate = yCordinate;
		
		switch (fieldType){
			case LABEL:
			case DATA:
				component = new Label(fieldNameKey, contentMode);
				break;
			case FIELD: 
				if (dataId == null || dataId.length() == 0)
					component = new Label(fieldNameKey, contentMode);
				else
					component = new Label("", contentMode);
				break;
			case IMAGE_LABEL:
			case IMAGE_FIELD:
				alignment = Alignment.MIDDLE_CENTER;
				if (!"".equals(fieldNameKey)) {
					component = new Embedded(null, new ThemeResource(fieldNameKey));
				} else {
					component = new Label();
				}
				break;
		}
	}
	
	public GridField(String fieldNameKey, String dataId, int xCordinate, int yCordinate){
		this(GridFieldType.LABEL, fieldNameKey, dataId, xCordinate, yCordinate);
	}
	
	public GridFieldType getFieldType() {
		return fieldType;
	}

	public void setFieldType(GridFieldType fieldType) {
		this.fieldType = fieldType;
	}

	public int getxCordinate() {
		return xCordinate;
	}

	public void setxCordinate(int xCordinate) {
		this.xCordinate = xCordinate;
	}

	public int getyCordinate() {
		return yCordinate;
	}

	public void setyCordinate(int yCordinate) {
		this.yCordinate = yCordinate;
	}

	public int getColumnSpan() {
		return columnSpan;
	}

	public void setColumnSpan(int columnSpan) {
		this.columnSpan = columnSpan;
	}

	public int getRowSpan() {
		return rowSpan;
	}

	public void setRowSpan(int rowSpan) {
		this.rowSpan = rowSpan;
	}

	public boolean isBold() {
		return isBold;
	}

	public void setBold(boolean isBold) {
		this.isBold = isBold;
	}

	public Alignment getAlignment() {
		return alignment;
	}

	public void setAlignment(Alignment alignment) {
		this.alignment = alignment;
	}
	
	public void setVisible(boolean visible){
		this.visible = visible;
		this.component.setVisible(visible);
	}
	
	public boolean isVisible(){
		return this.visible;
	}
	
	public void setEnabled(boolean enabled){
		this.enabled = enabled;
		this.component.setEnabled(enabled);
	}
	
	public boolean isEnabled(){
		return this.enabled;
	}

	public String getFieldNameKey() {
		return fieldNameKey;
	}

	public void setFieldNameKey(String fieldNameKey) {
		switch(getFieldType()){
			case IMAGE_LABEL:
			case IMAGE_FIELD:
				if (!"".equals(fieldNameKey)) {
					component = new Embedded(null, new ThemeResource(fieldNameKey));
				} else {
					component = new Label();
				}
				break;
			default:
				break;
		}
		this.fieldNameKey = fieldNameKey;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public AbstractComponent getComponent() {
		return component;
	}

	public boolean isNoStyle() {
		return noStyle;
	}

	public void setNoStyle(boolean noStyle) {
		this.noStyle = noStyle;
	}

	public int getContentMode() {
		return contentMode;
	}

	public void setContentMode(int contentMode) {
		this.contentMode = contentMode;
	}
	
	public void setStyleList(List styleList){
		this.styleList = styleList;
	}	
	
	public List getStyleList(){
		return this.styleList;
	}
	
	public void setHaveColon(boolean haveColon){
		this.haveColon = haveColon;
	}
	
	public boolean getHaveColon(){
		return this.haveColon;
	}
}
