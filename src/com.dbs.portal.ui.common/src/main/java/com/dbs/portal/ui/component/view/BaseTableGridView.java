package com.dbs.portal.ui.component.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.dbs.portal.ui.component.application.IApplication;
import com.dbs.portal.ui.layout.HTMLGridLayout;
import com.dbs.portal.ui.util.LanguageChanger;
import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

public class BaseTableGridView extends VerticalLayout implements ITableGridResultView{
	
	private HTMLGridLayout gridLayout;
	
	private int noOfColumn = 1;
	private int noOfRow = 1;
	private int maxRow = 1;
	
	private String dataKey = null;
	
	private String id = null;
	
	private List<GridHeaderField> headerFieldList = null;
	private Map<String, GridHeaderField> headerNameMapping = new HashMap<String, GridHeaderField>();
	
	private ArrayList<String> spanList = new ArrayList<String>();
	
	private Map<Integer, Integer> sequenceMap = new HashMap<Integer, Integer>();
	
	public BaseTableGridView(){
		super();
		gridLayout = new HTMLGridLayout(noOfColumn, noOfRow);
	}
	
	public BaseTableGridView(String caption){
		super();
		gridLayout = new HTMLGridLayout(caption, noOfColumn, noOfRow);
	}

	public void setFieldList(Object a){
		this.headerFieldList = (ArrayList<GridHeaderField>)a;
	}
	
	@Override
	public void packLayout() {
		this.setSizeFull();
		LanguageChanger changer = ((IApplication)this.getApplication()).getLanguageChanger();
		gridLayout.setLanguageChanger(changer);
		
		this.setSizeFull();
		this.addComponent(gridLayout.getMainLayout());
	}
	
	@Override
	public AbstractComponent getComponent(String dataId) {
		GridHeaderField field =  headerNameMapping.get(dataId);
		if (field == null)
			return null;
		
		return field.getComponent();
	}

	@Override
	public void updateContent(Container container) {
		gridLayout.removeAllComponents();
		noOfRow = 1;
		noOfColumn = 1;
		maxRow = 1;
		spanList.clear();
		
		Collection propertyIds = container.getContainerPropertyIds();
		
		int headerHeight = 1;
		int xConstant = 0;
		Map<Integer, GridHeaderField> columnFieldMapping = new HashMap<Integer, GridHeaderField>();
		Map<Integer, String> columnNameMapping = new HashMap<Integer, String>();
		
		
		//draw header first...
		for (int i = 0 ; i < headerFieldList.size() ; i++){
			GridHeaderField field = headerFieldList.get(i);
			
			expand(field, headerHeight, xConstant);
			
			if (!field.isRepeatable()){
				if (!field.isChildSpan()){
					gridLayout.addLabel(field.getComponent(), field.getxCordinate() + xConstant, field.getyCordinate(), field.getColumnSpan(), field.getRowSpan(), field.getAlignment(), field.isBold());
					columnFieldMapping.put(field.getxCordinate() + xConstant, field);
					columnNameMapping.put(field.getxCordinate() + xConstant , field.getDataId());
				}else{
					
						if (field.isSequence()){
							if (sequenceMap.containsKey(field.getxCordinate()+xConstant)){
								Integer sequence = sequenceMap.get(field.getxCordinate()+xConstant);
								sequence ++;
								if (field.getComponent() instanceof Label){
									((Label)field.getComponent()).setValue(((String)((Label)field.getComponent()).getValue())+sequence);
								}
							}else{
								Integer sequence = 1;
								sequenceMap.put(field.getxCordinate()+xConstant, sequence);
								if (field.getComponent() instanceof Label){
									((Label)field.getComponent()).setValue(((String)((Label)field.getComponent()).getValue())+sequence);
								}
							}
						}
						
						gridLayout.addLabel(field.getComponent(), field.getxCordinate() + xConstant, field.getyCordinate(), field.getColumnSpan(), field.getRowSpan(), field.getAlignment(), field.isBold());
						columnFieldMapping.put(field.getxCordinate() + xConstant, field);
						columnNameMapping.put(field.getxCordinate() + xConstant , field.getDataId());
					
				}
			}else{
				int repeatCount = 0;
				List<GridHeaderField> childList = field.getChild();
				boolean haveData = true;
				while (haveData){

					if (childList != null && childList.size() > 0){
						
						//check if necessary to draw this group of child
						for (int  j = 0 ; j < childList.size() ; j++){
							GridHeaderField childField = childList.get(j);
							if (childField.getDataId() != null && !"".equals(childField.getDataId())){
								if (!propertyIds.contains(childField.getDataId()+"_"+repeatCount)){
									haveData = false;
								}else{
									haveData = true;
									break;
								}
							}
						}
						
						if (haveData){
							//then draw header
							expand(field, headerHeight, xConstant);
							gridLayout.addLabel(new Label(field.getFieldNameKey()), field.getxCordinate() + xConstant , field.getyCordinate(), field.getColumnSpan(), field.getRowSpan(), field.getAlignment(), field.isBold());
							
							//then children
							for (int  j = 0 ; j < childList.size() ; j++){
								
								GridHeaderField childField = childList.get(j);
								
								expand(childField, headerHeight, xConstant);
								
								gridLayout.addLabel(new Label(childField.getFieldNameKey()), childField.getxCordinate() + xConstant , childField.getyCordinate(), childField.getColumnSpan(), childField.getRowSpan(), childField.getAlignment(), childField.isBold());
								
								if (childField.getDataId() != null && !"".equals(childField.getDataId())){
									columnFieldMapping.put(childField.getxCordinate() + xConstant, childField);
									columnNameMapping.put(childField.getxCordinate() + xConstant, childField.getDataId()+"_"+repeatCount);
								}
							}
							xConstant += field.getRequiredColumn();
						}
					}
					repeatCount++;
					
				}
			}
		}
		
		int currentRow = noOfRow;
		
		//then draw grid table content
		Collection itemIds = container.getItemIds();
		for (Iterator it = itemIds.iterator(); it.hasNext() ; ){
			Object itemId = it.next();
			if (currentRow + 1 > maxRow){
				gridLayout.setRows(currentRow + 1);
				maxRow = currentRow + 1;
			}
			
			for (Iterator<Integer> itColumn = columnNameMapping.keySet().iterator() ; itColumn.hasNext(); ){
				Integer column = itColumn.next();
				GridHeaderField headerField = columnFieldMapping.get(column);
				String dataId = columnNameMapping.get(column);
				Property property = container.getContainerProperty(itemId, dataId);
				
				String value = (String)property.getValue();
				Label label = new Label(value);
				
								//gridLayout.addField(label, column, currentRow  , headerField.getColumnSpan(), headerField.getRowSpan(), headerField.getAlignment(), headerField.isBold());
				if (!(spanList.contains((column)+"."+currentRow))){
					if (!headerField.isChildSpan()){
						gridLayout.addField(label, column, currentRow  , headerField.getColumnSpan(), 1, headerField.getAlignment(), headerField.isBold());
					}else{
						expandChild(headerField, headerHeight, xConstant, currentRow);
						gridLayout.addField(label, column, currentRow  , headerField.getChildColSpan() , headerField.getChildRowSpan()  , headerField.getAlignment(), headerField.isBold());
					}
				}
				
				if (headerField.isChildSpan() && !(spanList.contains((column)+"."+currentRow))){
					if (headerField.getChildColSpan() > 0 && headerField.getChildRowSpan() > 0){
						spanList.add((headerField.getxCordinate() + xConstant)+"."+headerField.getyCordinate());	
						for (int x = headerField.getxCordinate() + xConstant ; x < headerField.getxCordinate() + xConstant + headerField.getChildColSpan() ; x++){
							for (int y = currentRow ; y < currentRow + headerField.getChildRowSpan(); y++){
								spanList.add(x+"."+y);
							}
						}
					}else if (headerField.getChildColSpan() > 0){
						for (int x = headerField.getxCordinate() + xConstant ; x < headerField.getxCordinate() + xConstant + headerField.getChildColSpan() ; x++){
							spanList.add(x+"."+headerField.getyCordinate());
						}
					}else if (headerField.getChildRowSpan() > 0){
						for (int y = currentRow ; y < currentRow + headerField.getChildRowSpan(); y++){
							spanList.add(headerField.getxCordinate() + xConstant+"."+y);
						}
					}
				}

				
			}
			currentRow++;
		}
		
		
		this.removeAllComponents();
		this.addComponent(gridLayout.getMainLayout());
		
	}
	
	private void expand(GridHeaderField field, int headerHeight, int xConstant){
		if (field.getyCordinate() + field.getRowSpan() > (headerHeight - 1)){
			headerHeight = field.getyCordinate() + (field.getRowSpan() == 0 ? 1 : field.getRowSpan());
			if (headerHeight > (noOfRow - 1)){
				gridLayout.setRows(headerHeight);
				noOfRow = headerHeight;
			}
		}
		
		if (xConstant + field.getxCordinate() + field.getColumnSpan() > (noOfColumn-1)){
			noOfColumn = xConstant + field.getxCordinate() + (field.getColumnSpan() == 0 ? 1 : field.getColumnSpan());
			gridLayout.setColumns(noOfColumn);
		}
	}
	
	private void expandChild(GridHeaderField field, int headerHeight, int xConstant, int currentRow){
		if (field.getyCordinate() + Math.max(field.getRowSpan(), field.getChildRowSpan()) > (headerHeight - 1)){
			headerHeight = currentRow + (Math.max(field.getRowSpan(), field.getChildRowSpan()) <=1 ? 1 : Math.max(field.getRowSpan(), field.getChildRowSpan()));
			if (headerHeight > (noOfRow - 1)){
				gridLayout.setRows(headerHeight);
				maxRow = headerHeight;
//				noOfRow = headerHeight;
			}
		}
		
		if (xConstant + field.getxCordinate() + Math.max(field.getColumnSpan(), field.getChildColSpan()) > (noOfColumn-1)){
			noOfColumn = xConstant + field.getxCordinate() + (Math.max(field.getColumnSpan(), field.getChildColSpan()) <= 1 ? 1 : Math.max(field.getColumnSpan(),field.getChildColSpan()));
			gridLayout.setColumns(noOfColumn);
		}
	}
	
	public String getDataKey(){
		return this.dataKey;
	}
	
	public void setDataKey(String dataKey){
		this.dataKey = dataKey;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public List<GridHeaderField> getHeaderFieldList() {
		return this.headerFieldList;
	}
	
}
