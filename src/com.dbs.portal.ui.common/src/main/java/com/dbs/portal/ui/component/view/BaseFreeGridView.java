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
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public abstract class BaseFreeGridView extends VerticalLayout implements IGridResultView{
	
	protected HTMLGridLayout gridLayout;
	
	private List<FormField> fieldList;
	
	protected Map<String, GridField> fieldNameMapping = new HashMap<String, GridField>();
	private Map<String, AbstractComponent> componentNameMapping = new HashMap<String , AbstractComponent>();
	
	private int noOfColumn;
	private int noOfRow;
	
	private boolean noBorder = false;
	
	private Object fieldObject = null;
	
	private String dataKey = null;
	
	private String id = null;
	
	public BaseFreeGridView(int noOfColumn, int noOfRow){
		super();
		gridLayout = new HTMLGridLayout(noOfColumn, noOfRow);
	}
	
	public BaseFreeGridView(String caption, int noOfColumn, int noOfRow){
		super();
		this.noOfColumn = noOfColumn;
		this.noOfRow = noOfRow;
		gridLayout = new HTMLGridLayout(caption, noOfColumn, noOfRow);
	}

	public void setFieldList(Object a){
		fieldObject = a;
	}
	
	@Override
	public ArrayList<GridField> getGridFieldList(){
		return (ArrayList)fieldObject;
	}

	@Override
	public void packLayout() {
		LanguageChanger changer = ((IApplication)this.getApplication()).getLanguageChanger();
		gridLayout.setLanguageChanger(changer);
		
		if (fieldObject != null){
			ArrayList fieldList = (ArrayList)fieldObject;
			for (int j = 0 ;  j <  fieldList.size() ; j++){
				GridField gridField = ((GridField)fieldList.get(j));
				storeField(gridField);
				switch (gridField.getFieldType()){
					case LABEL:
						gridLayout.addLabel(gridField.getComponent(), gridField.getxCordinate(), gridField.getyCordinate(), gridField.getColumnSpan(), gridField.getRowSpan(), gridField.getAlignment(), gridField.isBold(), gridField.getHaveColon());
						break;
					case FIELD:
						gridLayout.addField(gridField.getComponent(), gridField.getxCordinate(), gridField.getyCordinate(), gridField.getColumnSpan(), gridField.getRowSpan(), gridField.getAlignment(), gridField.isBold(), gridField.getHaveColon());
						break;
					case IMAGE_LABEL:
						gridLayout.addLabel(gridField.getComponent(), gridField.getxCordinate(), gridField.getyCordinate(), gridField.getColumnSpan(), gridField.getRowSpan(), gridField.getAlignment());
						break;
					case IMAGE_FIELD:
						gridLayout.addField(gridField.getComponent(), gridField.getxCordinate(), gridField.getyCordinate(), gridField.getColumnSpan(), gridField.getRowSpan(), gridField.getAlignment());
						break;
				}
				gridLayout.addComponentStyle(gridField.getxCordinate(), gridField.getyCordinate(), gridField.getStyleList());
			}
		}
		
		this.setSizeFull();
		
	}
	
	private void storeField(GridField field){
		componentNameMapping.put(field.getDataId(), field.getComponent());
		fieldNameMapping.put(field.getDataId(), field);
	}
	
	@Override
	public AbstractComponent getComponent(String dataId) {
		AbstractComponent component = componentNameMapping.get(dataId);
		if (component == null)
			component = fieldNameMapping.get(dataId).getComponent();
		
		return component;
	}

	@Override
	public void updateContent(Container container) {
		Collection propertyIds = container.getContainerPropertyIds();
		
		Collection itemIds = container.getItemIds();
		for (Iterator it = itemIds.iterator(); it.hasNext() ; ){
			Object itemId = it.next();
			for (Iterator itp = propertyIds.iterator() ; itp.hasNext();){
				Object propertyId = itp.next();
				Property property = container.getContainerProperty(itemId, propertyId);
				GridField field = fieldNameMapping.get(propertyId);
				if (field != null) {
					switch (field.getFieldType()){
						case LABEL:
						case FIELD:
						case DATA:
							Label label = (Label)field.getComponent();
							label.setValue(property.getValue());
							break;
							
						case IMAGE_LABEL:
						case IMAGE_FIELD:
							Label labelIcon = (Label)field.getComponent();
							labelIcon.setIcon(new ThemeResource((String)property.getValue()));
							break;
//							field.setFieldNameKey((String)property.getValue());
//							if (field.getComponent() instanceof Embedded){
//								Embedded image = (Embedded)field.getComponent();
//								this.gridLayout.replaceComponent(field.getxCordinate(), field.getyCordinate(), image);
//							}
//							break;
					}
				}
			}
		}
		
		postUpdate();
		this.removeAllComponents();
		this.addComponent(gridLayout.getMainLayout());
	}
	
	public abstract void postUpdate();

	public void setNoBorder(boolean noBorder) {
		this.noBorder = noBorder;
		gridLayout.setNoBorder(this.noBorder);
	}

	public String getDataKey() {
		return dataKey;
	}

	public void setDataKey(String dataKey) {
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
}
