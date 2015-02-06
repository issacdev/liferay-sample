package com.dbs.portal.ui.component.view;

import java.util.List;

import com.dbs.portal.ui.component.view.GridFieldType;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Label;

public class GridHeaderField {
	
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
	
	private boolean isRepeatable;
	
	private List<GridHeaderField> groupFields;
	
	private int requiredColumn = 1;
	
	private boolean sequence = false;
	
	private boolean childSpan = false;
	
	private int childRowSpan = 0;
	private int childColSpan = 0;
	
	private GridHeaderField(GridFieldType fieldType, String fieldNameKey, String dataId, int xCordinate, int yCordinate){
		this.fieldType = fieldType;
		this.fieldNameKey = fieldNameKey;
		this.dataId = dataId;
		this.xCordinate = xCordinate;
		this.yCordinate = yCordinate;
		
		switch (fieldType){
			case LABEL:
			case FIELD:
				component = new Label(fieldNameKey);
				break;
			case IMAGE_LABEL:
			case IMAGE_FIELD:
				alignment = Alignment.MIDDLE_CENTER;
				component = new Embedded(null, new ThemeResource(fieldNameKey));
				break;
		}
	}
	
	public GridHeaderField(String fieldNameKey, String dataId, int xCordinate, int yCordinate){
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
	
	public String getFieldNameKey() {
		return fieldNameKey;
	}

	public void setFieldNameKey(String fieldNameKey) {
		switch(getFieldType()){
			case IMAGE_LABEL:
			case IMAGE_FIELD:
				component = new Embedded(null, new ThemeResource(fieldNameKey));
				break;
			default:
				((Label)this.component).setValue(fieldNameKey);
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

	public boolean isRepeatable() {
		return isRepeatable;
	}

	public void setRepeatable(boolean isRepeatable) {
		this.isRepeatable = isRepeatable;
	}
	
	public void setChild(List<GridHeaderField> headerChild){
		groupFields = headerChild;
	}
	
	public List<GridHeaderField> getChild(){
		return groupFields;
	}
	
	public void setRequiredColumn(int requiredColumn){
		this.requiredColumn = requiredColumn;
	}
	
	public int getRequiredColumn(){
		return this.requiredColumn;
	}

	public boolean isSequence() {
		return sequence;
	}

	public void setSequence(boolean sequence) {
		this.sequence = sequence;
	}

	public boolean isChildSpan() {
		return childSpan;
	}

	public void setChildSpan(boolean childSpan) {
		this.childSpan = childSpan;
	}

	public int getChildRowSpan() {
		return childRowSpan;
	}

	public void setChildRowSpan(int childRowSpan) {
		this.childRowSpan = childRowSpan;
	}

	public int getChildColSpan() {
		return childColSpan;
	}

	public void setChildColSpan(int childColSpan) {
		this.childColSpan = childColSpan;
	}
	
	
}
