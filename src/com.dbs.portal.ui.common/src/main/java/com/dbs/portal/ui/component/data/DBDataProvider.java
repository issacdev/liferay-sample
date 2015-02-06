package com.dbs.portal.ui.component.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.dbs.portal.ui.component.pagetable.PagedTableParameter;
import com.dbs.portal.ui.component.view.IController;
import com.dbs.portal.ui.component.view.IViewDataHandler;
import com.dbs.portal.ui.util.Messages;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;

public abstract class DBDataProvider implements IDataProvider {

	protected List<Map<String, Object>> mapData = null;
	
	private IController controller = null;
	
	private List<PagedTableParameter> propertyColumnList = new ArrayList<PagedTableParameter>();
	
	private Map<String, PagedTableParameter> propertyMap = new HashMap<String, PagedTableParameter>();
	
	private Map<String, IViewDataHandler> dataHandlerMap = new HashMap<String, IViewDataHandler>();
	
	private Messages messages = null;
	
	public DBDataProvider(){}
	
	public DBDataProvider(Messages messages){
		this.messages = messages;
	}
	
	public void setData(List<Map<String, Object>> mapData){
		this.mapData = mapData;
	}
	
	public abstract List<Map<String, Object>> getData();

	@Override
	public Container getDataContainer() {
		IndexedContainer container = new IndexedContainer();
		List<Map<String, Object>> dataMapList = getData();
		if (mapData != null){
			List<String> keyList = new ArrayList<String>();
			if (dataMapList != null && dataMapList.size() > 0) {
				Map<String, Object> dataMap = dataMapList.get(0);
				if (dataMap != null) {
					for (Iterator<String> it = dataMap.keySet().iterator(); it.hasNext();) {
						keyList.add(it.next());
					}
				}
			}
	
			for (int i = 0; i < keyList.size(); i++) {
				container.addContainerProperty(keyList.get(i), String.class, null);
			}
	
			for (int i = 0 ; i < dataMapList.size() ; i++){
				Map<String , Object> map = dataMapList.get(i);
				Item added = container.addItem(i);
				if (added == null){
					added = container.getItem(i);
				}
				for (String nameKey : keyList){
					if (map.get(nameKey) != null){
						
						if (propertyMap.get(nameKey) != null && propertyMap.get(nameKey).isLanguageChange() && messages != null){
							try{
								added.getItemProperty(nameKey).setValue(messages.getString((String)map.get(nameKey)));
							}catch(Exception e){
								added.getItemProperty(nameKey).setValue(map.get(nameKey));
							}
						}else{
							if (this.dataHandlerMap.get(nameKey) == null)
								added.getItemProperty(nameKey).setValue(map.get(nameKey));
							else{
								IViewDataHandler handler = this.dataHandlerMap.get(nameKey);
								added.getItemProperty(nameKey).setValue(handler.translate(map.get(nameKey)));
							}
						}
					}else
						added.getItemProperty(nameKey).setValue("");
				}
			}
		}

		return container;
	}

	public IController getController() {
		return controller;
	}

	public void setController(IController controller) {
		this.controller = controller;
	}
	
	public void setColumnProperty(List<PagedTableParameter> propertyColumnList){
		this.propertyColumnList = propertyColumnList;
		
		for (PagedTableParameter parameter : propertyColumnList){
			propertyMap.put(parameter.getPropertyId(), parameter);
		}
	}
	
	@Override
	public void addDataHandler(String dataId, IViewDataHandler handler) {
		this.dataHandlerMap.put(dataId, handler);
	}
	
	

}
