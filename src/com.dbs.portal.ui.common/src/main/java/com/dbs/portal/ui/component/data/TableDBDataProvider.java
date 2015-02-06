package com.dbs.portal.ui.component.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dbs.portal.ui.component.application.IApplication;
import com.dbs.portal.ui.component.pagetable.IPagedTableParameter;
import com.dbs.portal.ui.component.pagetable.MultiPagedTableParameter;
import com.dbs.portal.ui.component.pagetable.MultiPagedTableParameterList;
import com.dbs.portal.ui.component.pagetable.PagedTableComponentVisibility;
import com.dbs.portal.ui.component.pagetable.PagedTableParameter;
import com.dbs.portal.ui.component.view.IViewDataHandler;
import com.dbs.portal.ui.util.Messages;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.Reindeer;

public class TableDBDataProvider extends DBDataProvider{

	
	private Object[] dataColumnList = new Object[0];
	private Map<String, IPagedTableParameter> parameterMap = new HashMap<String, IPagedTableParameter>();
	private IApplication application = null;
	
	@Override
	public List<Map<String, Object>> getData() {
		// TODO Auto-generated method stub
		return this.mapData;
	}
	
	public void setDataColumnList(Object[] list){
		this.dataColumnList = list;
	}
	
	public void setPagedTableParameterMap(Map<String, IPagedTableParameter> parameterMap){
		if (parameterMap != null)
			this.parameterMap = parameterMap;
	}
	
	@Override
	public Container getDataContainer() {
		IndexedContainer container = new IndexedContainer();
		List<Map<String, Object>> dataMapList = getData();
		Messages messages = null;
		if (application != null){
			messages = application.getLanguageChanger().getMessages();
		}
		
		if (mapData != null){
			
			for (int i = 0; i < dataColumnList.length; i++) {
				Class clazz = String.class;
				
				if (parameterMap.get(dataColumnList[i]) != null){
					IPagedTableParameter parameter = parameterMap.get(dataColumnList[i]);
					switch(parameter.getColumnType()){
						case STRING:
							break;
						case BUTTON:
						case LINK_BUTTON:
							clazz = Button.class;
							break;
						case CHECKBOX:
							clazz = CheckBox.class;
							break;
						case TEXT_FIELD:
							clazz = TextField.class;
							break;
						case MULTIBUTTON:
							clazz = CssLayout.class;
							break;
					}
				}
				
				container.addContainerProperty((String)dataColumnList[i], clazz, null);
			}
	
			for (int i = 0 ; i < dataMapList.size() ; i++){
				Map<String , Object> map = dataMapList.get(i);
				Item added = container.addItem(i);
				if (added == null){
					added = container.getItem(i);
				}
				for (Object name : dataColumnList){
					String nameKey = (String)name;
					if (map.get(nameKey) != null){
						Object obj = null;
						
						boolean visible = true;
						if (nameKey != null){
							IPagedTableParameter parameter = parameterMap.get(nameKey);
							if (parameter != null){
								PagedTableComponentVisibility componentVisibility = parameter.getShowComponent();
								if (componentVisibility != null){
									componentVisibility.setApplication(this.application);
									visible = componentVisibility.showComponent(map);
								}
							}
						}
						
						if (parameterMap.get(nameKey) != null && visible){
							IPagedTableParameter parameter = parameterMap.get(nameKey);
							
							switch(parameter.getColumnType()){
								case STRING:
									PagedTableParameter paramString = (PagedTableParameter)parameter;
									if (paramString.getDataHandler() != null){
										obj = paramString.getDataHandler().translate(map.get(nameKey));
									}else
										obj = map.get(nameKey);
									break;
								case BUTTON:
									PagedTableParameter paramButton = (PagedTableParameter)parameter;
									String labelKey = paramButton.getLabelKey();
									if (messages != null)
										labelKey = messages.getString(labelKey);
									obj = new Button(labelKey);
									((Button)obj).setData(map);
									((Button)obj).setWidth(parameter.getWidth() + "px");
									if (paramButton.getListenerList() != null){
										for (Object o : paramButton.getListenerList()){
											ClickListener lis = (ClickListener)o;
											((Button)obj).addListener(lis);
										}
									}
									break;
								case LINK_BUTTON:
									PagedTableParameter paramLink = (PagedTableParameter)parameter;
									obj = new Button(map.get(nameKey)+"");
									((Button)obj).addStyleName(Reindeer.BUTTON_LINK);
									((Button)obj).setData(map);
									((Button)obj).setSizeFull();
									if (paramLink.getStyleList() == null){
										((Button)obj).addStyleName("link-button-center");
									}
									if (paramLink.getStyleList() != null)
										for (String style : paramLink.getStyleList()){
											((Button)obj).addStyleName(style);
										}
								
									if (paramLink.getListenerList() != null){
										for (Object o : paramLink.getListenerList()){
											ClickListener lis = (ClickListener)o;
											((Button)obj).addListener(lis);
										}
									}
									break;
								case CHECKBOX:
									PagedTableParameter paramCheckbox = (PagedTableParameter)parameter;
									String checkKey = paramCheckbox.getLabelKey();
									if (messages != null)
										checkKey = messages.getString(checkKey);
									obj = new CheckBox(checkKey);
									((CheckBox)obj).setData(map);
									if (map.get(nameKey) != null && map.get(nameKey) instanceof Boolean)
										((CheckBox)obj).setValue((Boolean)map.get(nameKey));
									if (paramCheckbox.getListenerList() != null){
										for (Object o : paramCheckbox.getListenerList()){
											ValueChangeListener lis = (ValueChangeListener)o;
											((CheckBox)obj).addListener(lis);
										}
									}
									
									break;
								case TEXT_FIELD:
									PagedTableParameter paramTextField = (PagedTableParameter)parameter;
									obj = new TextField();
									if (paramTextField.isUpperCase())
										((TextField)obj).addStyleName("upper-case");
									((TextField)obj).setValue(map.get(nameKey));
									((TextField)obj).setImmediate(true);
									((TextField)obj).setData(map);
									if (paramTextField.getListenerList() != null){
										for (Object o : paramTextField.getListenerList()){
											TextChangeListener lis = (TextChangeListener)o;
											((TextField)obj).addListener(lis);
										}
									}
									
									if (paramTextField.getValidators() != null){
										for (Object o : paramTextField.getValidators()){
											AbstractValidator validator = (AbstractValidator)o;
											((TextField)obj).addValidator(validator);
											if (messages != null){
												validator.setErrorMessage(messages.getString(validator.getErrorMessage()));
											}
										}
									}
									
									if (parameter.getMaxWidth() > 0){
										((TextField)obj).setMaxLength(parameter.getMaxWidth());
									}
									break;
								case MULTIBUTTON:
									MultiPagedTableParameterList paramMultiButton = (MultiPagedTableParameterList)parameter;
									obj = new CssLayout();
									String buttonName = null;
									for (MultiPagedTableParameter param : paramMultiButton.getParameters()){
										
										PagedTableComponentVisibility componentVisibility = param.getShowComponent();
										if (componentVisibility != null){
											componentVisibility.setApplication(application);
										}
										//also check with permission...
										if (componentVisibility == null || componentVisibility.showComponent(map)){
											buttonName = param.getLabelKey();
											if (messages != null)
												buttonName = messages.getString(buttonName);
											Button b = new Button(buttonName);
											b.setData(map);
											if (param.getListenerList() != null){
												for (Object o : param.getListenerList()){
													ClickListener listener = (ClickListener)o;
													b.addListener(listener);
												}
											}
											b.setWidth(param.getWidth()+"px");
											((CssLayout)obj).addComponent(b);
										}
									}
							}
							
							added.getItemProperty(nameKey).setValue(obj);
						}else{
							IPagedTableParameter parameter = parameterMap.get(nameKey);
							if (parameter != null){
								switch(parameter.getColumnType()){
									case BUTTON:
									case MULTIBUTTON:
									case LINK_BUTTON:
										Object oobj = new Button("");
										((Button)oobj).addStyleName(Reindeer.BUTTON_LINK);
										((Button)oobj).addStyleName("emptyLinkButton");
										break;
								}
								
							} else {
								added.getItemProperty(nameKey).setValue(map.get(nameKey));
							}
						}
					}else if (parameterMap.get(nameKey) != null){
							IPagedTableParameter parameter = parameterMap.get(nameKey);
							switch(parameter.getColumnType()){
								case BUTTON:
								case MULTIBUTTON:
								case LINK_BUTTON:
									Object obj = new Button("");
									((Button)obj).addStyleName(Reindeer.BUTTON_LINK);
									((Button)obj).addStyleName("emptyLinkButton");
									added.getItemProperty(nameKey).setValue(obj);
									break;
							}
						
					}else {
						added.getItemProperty(nameKey).setValue("");
					}
				}
			}
		}

		return container;
	}

	@Override
	public void addDataHandler(String dataId, IViewDataHandler handler) {
		// TODO Auto-generated method stub
		
	}
	
	public void setApplication(IApplication application){
		this.application = application;
	}
}
