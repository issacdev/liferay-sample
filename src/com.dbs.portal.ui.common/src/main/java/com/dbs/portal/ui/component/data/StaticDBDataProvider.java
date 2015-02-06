package com.dbs.portal.ui.component.data;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.dbs.portal.ui.component.comboBox.ComboBoxItem;
import com.dbs.portal.ui.component.view.IViewDataHandler;
import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;

public class StaticDBDataProvider implements IDataProvider{

	private String serviceClass = null;
	private String functionName = null;
	private String keyId = null;
	private String valueId = null;
	private List<String> valueList = null;
	
	private Object serviceProxy = null;
	
	private boolean unique = false;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	public void setServiceClass(String serviceClass){
		this.serviceClass = serviceClass;
	}
	
	public void setFunctionName(String functionName){
		this.functionName = functionName;
	}
	
	public void setValueId(String keyId){
		this.keyId = keyId;
	}
	
	public void setValueList(List<String> valueList){
		this.valueList = valueList;
	}
	
	public void setKeyId(String valueId){
		this.valueId = valueId;
	}
	
	public void setServiceProxy(Object serviceProxy){
		this.serviceProxy = serviceProxy;
	}
	
	public void setUnique(boolean unique){
		this.unique = unique;
	}
	
	@Override
	public Container getDataContainer() {
		BeanItemContainer<ComboBoxItem> itemContainer = new BeanItemContainer<ComboBoxItem>(ComboBoxItem.class);
		try{
			Class cls = Class.forName(serviceClass);
			Object obj = cls.newInstance();
			Method method = cls.getMethod(functionName, null);
			
			List<Map<String, Object>> dataList = (List<Map<String, Object>>)method.invoke(obj);
			
			Set<Object> keySet = new HashSet<Object>();
			
			for (Map<String, Object> data : dataList){
				String key = "";
				if (keyId != null){
					key = (String)data.get(keyId);
				}else if (valueList != null){
					String output = "";
					for (String v : valueList){
						if (output.length() > 0){
							output += " - ";
						}
						output += data.get(v);
					}
					key = output;
				}
				
				Object value = data.get(valueId);
				if (!keySet.contains(value) || !unique){
					keySet.add(value);
					ComboBoxItem item = new ComboBoxItem(key, value);
					itemContainer.addBean(item);
				}
				
			}
		}catch(Exception e){
			logger.error("Error on get Data", e);
		}
		return itemContainer;
	}

	@Override
	public void addDataHandler(String dataId, IViewDataHandler handler) {
		// TODO Auto-generated method stub
		
	}

}
