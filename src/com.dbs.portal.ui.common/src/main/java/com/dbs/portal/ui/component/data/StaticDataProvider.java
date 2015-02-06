package com.dbs.portal.ui.component.data;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.dbs.portal.ui.component.comboBox.ComboBoxItem;
import com.dbs.portal.ui.component.view.IViewDataHandler;
import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;

public class StaticDataProvider implements IDataProvider{

	private Map<String, Object> data;
	
	public void setData(Map dataMap){
		this.data = dataMap;
	}
	
	
	
	@Override
	public Container getDataContainer() {
		BeanItemContainer<ComboBoxItem> itemContainer = new BeanItemContainer<ComboBoxItem>(ComboBoxItem.class);
		
		for (Iterator<String> it = data.keySet().iterator() ; it.hasNext(); ){
			String key = it.next();
			Object value = data.get(key);
			ComboBoxItem item = new ComboBoxItem(key, value);
			itemContainer.addBean(item);
		}
		
		return itemContainer;
	}



	@Override
	public void addDataHandler(String dataId, IViewDataHandler handler) {
		// TODO Auto-generated method stub
		
	}

}
