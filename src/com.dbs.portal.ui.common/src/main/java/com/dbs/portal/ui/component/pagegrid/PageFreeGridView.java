package com.dbs.portal.ui.component.pagegrid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.dbs.portal.ui.component.application.IApplication;
import com.dbs.portal.ui.component.data.DBDataProvider;
import com.dbs.portal.ui.component.data.DefaultDBDataProvider;
import com.dbs.portal.ui.component.view.BaseFreeGridView;
import com.dbs.portal.ui.component.view.GridField;
import com.dbs.portal.ui.component.view.IController;
import com.dbs.portal.ui.component.view.IInit;
import com.dbs.portal.ui.component.view.IView;
import com.dbs.portal.ui.component.view.IViewDataHandler;
import com.dbs.portal.ui.component.view.IWindow;
import com.dbs.portal.ui.util.LanguageChanger;
import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class PageFreeGridView extends BaseFreeGridView implements IInit{

	private Label firstPageButton = new Label("<<");
	private Label previousPageButton = new Label("<");
	private Label nextPageButton = new Label(">");
	private Label lastPageButton = new Label(">>");
	private Label currentPageLabel = new Label("1");
	private Label totalPageLabel = new Label("1");
	private Label middleLabel = new Label("/");
	private String labelStyle = "pageFreeGridView-label";
	
	
	private IWindow view = null;
	private IController control = null;
	
	private ComboBox combobox = null;
	
	private Map<String, IViewDataHandler> dataHandlerMap = new HashMap<String, IViewDataHandler>();
	
	private AbstractLayout headerLayout = null;
	private AbstractLayout footerLayout = null;
	private AbstractLayout mainLayout = null;
	
	public PageFreeGridView(int noOfColumn, int noOfRow) {
		super(noOfColumn, noOfRow);
	}
	
	public PageFreeGridView(String caption, int noOfColumn, int noOfRow){
		super(caption,noOfColumn,noOfRow);
	}

	@Override
	public void postUpdate() {
	}
	
	@Override
	public void packLayout() {
		this.setSizeFull();
		
		LanguageChanger changer = ((IApplication)this.getApplication()).getLanguageChanger();
		gridLayout.setLanguageChanger(changer);
		
		super.packLayout();
		
		this.addComponent(getControlBar());
		
		if (headerLayout != null){
			this.addComponent(headerLayout);
			((IView)headerLayout).packLayout();
		}
		
		mainLayout = gridLayout.getMainLayout();
		
		this.addComponent(mainLayout);
		
		if (footerLayout != null){
			this.addComponent(footerLayout);
			((IView)footerLayout).packLayout();
		}
	}
	
	private HorizontalLayout getControlBar(){
		
		LanguageChanger changer = ((IApplication)this.getApplication()).getLanguageChanger();
		
		HorizontalLayout hLayout = new HorizontalLayout();
		hLayout.setSpacing(true);
		
		//assign button
		firstPageButton.addStyleName(labelStyle);
		CssLayout firstPageLayout = new CssLayout();
		firstPageLayout.setSizeUndefined();
		firstPageLayout.addComponent(firstPageButton);
		hLayout.addComponent(firstPageLayout);
		
		previousPageButton.addStyleName(labelStyle);
		CssLayout previousPageLayout = new CssLayout();
		previousPageLayout.setSizeUndefined();
		previousPageLayout.addComponent(previousPageButton);
		hLayout.addComponent(previousPageLayout);
		
		VerticalLayout middleLayout = new VerticalLayout();
		middleLayout.setSizeFull();
		
		HorizontalLayout middleHLayout = new HorizontalLayout();
		middleHLayout.addComponent(currentPageLabel);
		middleHLayout.addComponent(middleLabel);
		middleHLayout.addComponent(totalPageLabel);
		
		middleLayout.addComponent(middleHLayout);
		hLayout.addComponent(middleLayout);
		
//		middleLayout.setComponentAlignment(middleHLayout, Alignment.MIDDLE_CENTER);
		
		nextPageButton.addStyleName(labelStyle);
		CssLayout nextPageLayout = new CssLayout();
		nextPageLayout.setSizeUndefined();
		nextPageLayout.addComponent(nextPageButton);
		hLayout.addComponent(nextPageLayout);
		
		lastPageButton.addStyleName(labelStyle);
		CssLayout lastPageLayout = new CssLayout();
		lastPageLayout.setSizeUndefined();
		lastPageLayout.addComponent(lastPageButton);
		hLayout.addComponent(lastPageLayout);
		
		HorizontalLayout comboxBoxLayout = new HorizontalLayout();
		Label comboxBoxLabel = new Label("common.combobox.gotopage");
		changer.changeCode(comboxBoxLabel);
		combobox = new ComboBox();
		combobox.setImmediate(true);
		combobox.setWidth("60px");
		comboxBoxLayout.addComponent(comboxBoxLabel);
		comboxBoxLayout.addComponent(combobox);
		hLayout.addComponent(comboxBoxLayout);
		
		//assign listener
		firstPageLayout.addListener(new LayoutClickListener() {
			
			@Override
			public void layoutClick(LayoutClickEvent event) {
				Map<String, Object> dataMap = ((IPageControl)control).firstPage();
				changePage(dataMap);
			}
		});
		
		//assign listener
		previousPageLayout.addListener(new LayoutClickListener() {
			
			@Override
			public void layoutClick(LayoutClickEvent event) {
				Map<String, Object> dataMap = ((IPageControl)control).previousPage();
				changePage(dataMap);
			}
		});
		
		//assign listener
		nextPageLayout.addListener(new LayoutClickListener() {
			
			@Override
			public void layoutClick(LayoutClickEvent event) {
				Map<String, Object> dataMap = ((IPageControl)control).nextPage();
				changePage(dataMap);
			}
		});
		
		//assign listener
		lastPageLayout.addListener(new LayoutClickListener() {
			
			@Override
			public void layoutClick(LayoutClickEvent event) {
				Map<String, Object> dataMap = ((IPageControl)control).lastPage();
				changePage(dataMap);
			}
		});
		

		
		combobox.addListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				Property p = event.getProperty();
				if (p != null && p.getValue() != null){
					int value = (Integer)p.getValue();
					Map<String, Object> dataMap = ((IPageControl)control).goToPage(value);
					changePage(dataMap);
				}
			}
		});
		
		return hLayout;
	}

	@Override
	public void setView(IWindow view) {
		this.view = view;
	}

	@Override
	public void setControl(IController control) {
		this.control = control;
		totalPageLabel.setValue(((IPageControl)control).totalPage());
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
					}
				}
			}
		}
		
		postUpdate();
		
		AbstractLayout tempLayout = gridLayout.getMainLayout();
		this.replaceComponent(mainLayout, tempLayout);
		mainLayout = tempLayout;
		
		totalPageLabel.setValue(((IPageControl)control).totalPage());
		
		combobox.removeAllItems();
		int total = ((IPageControl)control).totalPage();
		
		if (total > 0){
			currentPageLabel.setValue("1");
		}
		
		for (int i = 1; i <= total; i++) {
			combobox.addItem(i);
		}
		
		this.getWindow().executeJavaScript("resizedelay()");
	}
	
	public void setDataHandler(Map<String, IViewDataHandler> mapOfDataHandler){
		this.dataHandlerMap  =mapOfDataHandler;
	}
	
	public Map<String, IViewDataHandler> getDataHandler(){
		return this.dataHandlerMap;
	}
	
	private void changePage(Map<String , Object> dataMap){
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		listMap.add(dataMap);
		if (dataMap != null){
			DBDataProvider resultTable = new DefaultDBDataProvider();
			resultTable.setData(listMap);
			
			for (Iterator<String> it = this.dataHandlerMap.keySet().iterator() ; it.hasNext(); ){
				String dataId = it.next();
				IViewDataHandler handler = this.dataHandlerMap.get(dataId);
				
				LanguageChanger changer = ((IApplication)((Window)this.view).getApplication()).getLanguageChanger();
				handler.setMessages(changer.getMessages());
				resultTable.addDataHandler(dataId, handler);
			}
			PageFreeGridView.this.updateContent(resultTable.getDataContainer());
			PageFreeGridView.this.currentPageLabel.setValue(((IPageControl)control).getCurrentPage());
		}
	}

	public AbstractLayout getHeaderLayout() {
		return headerLayout;
	}

	public void setHeaderLayout(AbstractLayout headerLayout) {
		this.headerLayout = headerLayout;
	}

	public AbstractLayout getFooterLayout() {
		return footerLayout;
	}

	public void setFooterLayout(AbstractLayout footerLayout) {
		this.footerLayout = footerLayout;
	}
	
	

}
