package com.dbs.portal.ui.component.data;

import java.util.List;

import com.dbs.portal.ui.component.comboBox.ComboBoxItem;
import com.dbs.portal.ui.component.view.IViewDataHandler;
import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;

public class ComboBoxDataProvider implements IDataProvider{

	private List<String> dataList;
	
	public void setData(List<String> data){
		this.dataList = data;
	}
	
	@Override
	public Container getDataContainer() {
		BeanItemContainer<ComboBoxItem> itemContainer = new BeanItemContainer<ComboBoxItem>(ComboBoxItem.class);
		
		for (String item : dataList){
			ComboBoxItem comBoBoxitem = new ComboBoxItem(item, item);
			itemContainer.addBean(comBoBoxitem);
		}
		
		return itemContainer;
	}

	@Override
	public void addDataHandler(String dataId, IViewDataHandler handler) {
		// TODO Auto-generated method stub
		
	}

}
