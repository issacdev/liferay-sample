package com.dbs.portal.ui.component.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.dbs.portal.ui.component.application.IApplication;
import com.dbs.portal.ui.component.button.DeleteButton;
import com.dbs.portal.ui.component.button.ProcessButton;
import com.dbs.portal.ui.component.pagetable.IPagedTableParameter;
import com.dbs.portal.ui.component.pagetable.PagedTable;
import com.dbs.portal.ui.component.pagetable.PagedTableContainer;
import com.dbs.portal.ui.component.pagetable.PagedTableDataType;
import com.dbs.portal.ui.component.pagetable.PagedTablePanel;
import com.dbs.portal.ui.component.pagetable.PagedTableParameterType;
import com.dbs.portal.ui.util.LanguageChanger;
import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.HeaderClickEvent;
import com.vaadin.ui.VerticalLayout;

public class BaseTableView extends VerticalLayout implements ITableResultView, ITableEnquiryView{

	private static final long serialVersionUID = 1L;

	private PagedTablePanel pagedTablePanel;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	private String[] columnHeader;
	
	private int originalColumnHeader = 0;
	private boolean initOriginalColumnHeader = false;
	
	private Object[] visibleColumnHeader;
	private List<PagedTableDataType> visibleColumnDataType;
	
	private boolean pagingRequired = true;
	
	private boolean sizeFull = true;
	
	private String tableCaption = null;
	
	private String id = "";
	
	private String dataKey = null;
	
	private String worksheetNameKey = null;

	private Container container = null;
	
	private ProcessButton processButton = null;
	private DeleteButton deleteButton = null;
	
	private boolean processButtonVisible = false;
	private boolean deleteButtonVisible = false;
	
	private String processButtonName = "common.button.process";
	private String deleteButtonName = "common.button.delete";
	
	private List<ClickListener> processButtonListenerList = new ArrayList<ClickListener>();
	private List<ClickListener> deleteButtonListenerList = new ArrayList<ClickListener>();
	
	private List<String> processButtonStyleList = new ArrayList<String>();
	private List<String> deleteButtonStyleList = new ArrayList<String>();
	
	private List<IPagedTableParameter> pagedTableParameter = new ArrayList<IPagedTableParameter>();
	private Map<String, IPagedTableParameter> pagedTableParameterMap= new HashMap<String, IPagedTableParameter>();
	
	private PagedTable table = null;
	
	private Map<String, List<String>> hiddenGroupColumnName = new HashMap<String, List<String>>();
	private Map<String, List<String>> hiddenGroupColumnDataId = new HashMap<String, List<String>>();
	
	private List<String> activateList = new ArrayList<String>();
	
	private List<String> collapsedColumn = null;
	
	private boolean firstShow = true;
	
	private String functionName = null;
	
	private List<String> totalColumnId = null;
	
	private Integer selectedPageLength = null;
	private String[] selectedPageList = null;
	
	private List<ItemClickEvent.ItemClickListener> clickListeners = null;
	private boolean appliedClickListener = false;
	
	public BaseTableView(){
	}
	
	public BaseTableView(boolean pagingRequired){
		this.pagingRequired = pagingRequired;
	}
	
	public BaseTableView(boolean pagingRequired, boolean sizeFull){
		this(pagingRequired);
		this.sizeFull = sizeFull;
	}
	
	public void updateContent(Container container){
		
		int added = originalColumnHeader;
		if (activateList != null && activateList.size() > 0){
			for (String activateKey : activateList){
				if (hiddenGroupColumnName.containsKey(activateKey)){
					List<String> hiddenColumn = hiddenGroupColumnName.get(activateKey);
					if (hiddenColumn != null){
						added += hiddenColumn.size();
					}
				}
			}
		}
		
		String[] tempColumnHeader = new String[added];
		Object[] tempVisibleColumnHeader = new String[added];
		
		int count = 0;
		for (count = 0 ; count < originalColumnHeader ; count++){
			tempColumnHeader[count] = columnHeader[count];
			tempVisibleColumnHeader[count] = visibleColumnHeader[count];
		}
		
		//then add hidden column
		if (activateList != null && activateList.size() > 0){
			for (String activateKey : activateList){
				if (hiddenGroupColumnName.containsKey(activateKey)){
					List<String> hiddenColumn = hiddenGroupColumnName.get(activateKey);
					List<String> hiddenColumnData = hiddenGroupColumnDataId.get(activateKey);
					if (hiddenColumn != null && hiddenColumnData != null){
						for (int i = 0 ; i < hiddenColumn.size() ; i++){
							tempColumnHeader[count+i] = hiddenColumn.get(i);
							tempVisibleColumnHeader[count + i] = hiddenColumnData.get(i);
						}
						count += hiddenColumn.size();
					}
				}
			}
		}
		
		
		table.setContainerDataSource(getEmptyContainer(tempVisibleColumnHeader));
		
        table.setVisibleColumns(tempVisibleColumnHeader);
        table.setColumnHeaders(tempColumnHeader);
        firstShow = false;
        
        LanguageChanger changer = ((IApplication)this.getApplication()).getLanguageChanger();
        changer.changeCode(table);
	
        
		this.container = container;
		pagedTablePanel.setContainerDataSource(container);
		
		this.activateList.clear();
		
		hideColumn();
		
		hideUnwantColumn(tempVisibleColumnHeader);
	}
	
	private void hideColumn(){
        if (collapsedColumn != null){
	        for (String collapse : collapsedColumn){
	        	table.setColumnCollapsed(collapse, true);
	        }
        }
	}
	
	private void hideUnwantColumn(Object[] visibleColumn){
		Set<Object> columnSet = new HashSet();
		for (Object column : visibleColumn){
			columnSet.add(column);
		}
		
		if (visibleColumn != null && visibleColumn.length > 0){
			List<Object> removeList = new ArrayList<Object>();
			for (Object obj : table.getVisibleColumns()){
				if (!columnSet.contains(obj)){
					removeList.add(obj);
				}else{
					table.setColumnCollapsed(obj, false);
				}
			}
			
			for (Object removeObj : removeList){
				table.setColumnCollapsed(removeObj, true);
			}
		}
	}

	public void packLayout() {
		LanguageChanger changer = ((IApplication)this.getApplication()).getLanguageChanger();
		
		this.setMargin(false);
		this.addStyleName("PagedTableLayout");
		
		table = new PagedTable();
		table.setImmediate(true);
		if (sizeFull)
			this.setSizeFull();
		else
			this.setSizeUndefined();
		
//		//Table table = new Table();
		table.addStyleName("big striped");

		table.setSizeFull();
        
        table.setColumnCollapsingAllowed(true);
        table.setColumnReorderingAllowed(true);

        table.setContainerDataSource(getEmptyContainer());
        if (visibleColumnHeader != null)
        	table.setVisibleColumns(visibleColumnHeader);
        if (columnHeader != null)
        	table.setColumnHeaders(columnHeader);
        
        for (Object obj : pagedTableParameter){
        	IPagedTableParameter parameter = (IPagedTableParameter)obj;
        	table.setColumnAlignment(parameter.getPropertyId(), parameter.getAlignment());
        	table.setColumnWidth(parameter.getPropertyId(), parameter.getWidth());
        }
        
        changer.changeCode(table);
		
		pagedTablePanel = new PagedTablePanel(table, pagingRequired, sizeFull);
		
		this.addComponent(pagedTablePanel);
		
		if (this.tableCaption != null && !this.tableCaption.isEmpty())
			pagedTablePanel.setCaption(tableCaption);
		pagedTablePanel.setScrollable(true);
		if (sizeFull)
			pagedTablePanel.setSizeFull();
		pagedTablePanel.setContainerDataSource(getEmptyContainer());
		
		if (selectedPageLength != null && selectedPageList != null) {
			pagedTablePanel.setDefaultSelectedPageLength(selectedPageLength);
			pagedTablePanel.setSelectedPageLength(selectedPageLength);
			pagedTablePanel.setSelectedPageList(selectedPageList);
		}
		pagedTablePanel.packLayout();
		
		if (!this.appliedClickListener && clickListeners != null){
			appliedClickListener = true;
			for (ItemClickListener listener : clickListeners){
				table.addListener(listener);
			}
		}

		Panel panel = new Panel();
		((AbstractLayout)panel.getContent()).setMargin(false, true, false, false);
		panel.setSizeFull();
		panel.setHeight(null);
		panel.setScrollable(false);
		panel.addComponent(pagedTablePanel);
		panel.addStyleName("borderless");
		
		this.addComponent(panel);
		
		if (processButtonVisible || deleteButtonVisible){
			HorizontalLayout buttonLayout = new HorizontalLayout();
			buttonLayout.addStyleName("buttonPanel");
			buttonLayout.setSpacing(true);
			buttonLayout.setMargin(true);
			if (processButtonVisible){
				processButton = new ProcessButton(processButtonName);
				for (String style : processButtonStyleList) {
					processButton.addStyleName(style);
				}
				for (ClickListener listener : processButtonListenerList){
					processButton.addListener(listener);
				}
				processButton.setData(BaseTableView.this);
				buttonLayout.addComponent(processButton);
				changer.changeCode(processButton);
			}
			
			
			if (deleteButtonVisible){
				deleteButton = new DeleteButton(deleteButtonName);
				for (String style : deleteButtonStyleList) {
					deleteButton.addStyleName(style);
				}
				for (ClickListener listener : deleteButtonListenerList){
					deleteButton.addListener(listener);
				}
				deleteButton.setData(BaseTableView.this);
				buttonLayout.addComponent(deleteButton);
				changer.changeCode(deleteButton);
			}
			this.addComponent(buttonLayout);
		}
		
		this.setHeight(null);
		
		hideColumn();
		
		applyHeaderListener();
	}

	public String[] getColumnHeader() {
		return columnHeader;
	}

	public void setColumnHeader(String[] columnHeader) {
		if (!initOriginalColumnHeader){
			originalColumnHeader = columnHeader.length;
			initOriginalColumnHeader = true;
		}
		firstShow = true;
		this.columnHeader = columnHeader;
	}

	public Object[] getVisibleColumnHeader() {
		
		setVisibleColumnHeader(table.getVisibleColumns());
		setColumnHeader(table.getColumnHeaders());
		setCurrentCollapseColumnHeader(getCurrentCollapseColumnHeader());
		
		int added = columnHeader.length;
		if (activateList != null && activateList.size() > 0){
			for (String activateKey : activateList){
				if (hiddenGroupColumnName.containsKey(activateKey)){
					List<String> hiddenColumn = hiddenGroupColumnName.get(activateKey);
					if (hiddenColumn != null){
						added += hiddenColumn.size();
					}
				}
			}
		}
		
		Object[] tempVisibleColumnHeader = new String[added];
		
		int count = 0;
		for (count = 0 ; count < columnHeader.length ; count++){
			tempVisibleColumnHeader[count] = visibleColumnHeader[count];
		}
		
		//then add hidden column
		if (activateList != null && activateList.size() > 0){
			for (String activateKey : activateList){
				if (hiddenGroupColumnName.containsKey(activateKey)){
					List<String> hiddenColumn = hiddenGroupColumnName.get(activateKey);
					List<String> hiddenColumnData = hiddenGroupColumnDataId.get(activateKey);
					if (hiddenColumn != null && hiddenColumnData != null){
						for (int i = 0 ; i < hiddenColumn.size() ; i++){
							tempVisibleColumnHeader[count + i] = hiddenColumnData.get(i);
						}
						count += hiddenColumn.size();
					}
				}
			}
		}
		
		return tempVisibleColumnHeader;
	}

	public void setVisibleColumnHeader(Object[] visibleColumnHeader) {
		this.visibleColumnHeader = visibleColumnHeader;
	}
	
	public void setVisibleColumnDataType(List<PagedTableDataType> visibleColumnDataType){
		this.visibleColumnDataType = visibleColumnDataType;
	}
	
	public List<PagedTableDataType> getVisibleColumnDataType(){
		return this.visibleColumnDataType;
	}
	
	public Object[] getOriginalColumnDataId(){
		return this.visibleColumnHeader;
	}
	
	public String[] getOriginalColumnHeader(){
		return this.columnHeader;
	}
	
	public void setTableCaption(String tableCaption){
		this.tableCaption = tableCaption;
	}
	
	public Object[] getCurrentVisibleColumn(){
		
		//exclude collapsed column
		List propIds = new LinkedList<Object>(Arrays.asList(table.getVisibleColumns()));
		final Iterator<Object> iterator = propIds.iterator();
        while (iterator.hasNext()) {
            final Object propId = iterator.next();
            if (table.isColumnCollapsed(propId)) {
                iterator.remove();
            }
        }
        
        return propIds.toArray();
	}
	
	public List<String> getCurrentCollapseColumnHeader(){
		//exclude collapsed column
		List<String> collapsedColumn = new LinkedList<String>();
		
		List propIds = new LinkedList<Object>(Arrays.asList(table.getVisibleColumns()));
		final Iterator<Object> iterator = propIds.iterator();
        while (iterator.hasNext()) {
            final Object propId = iterator.next();
            if (table.isColumnCollapsed(propId)) {
            	collapsedColumn.add((String)propId);
            }
        }
        
        return collapsedColumn;
	}
	
	public void setCurrentCollapseColumnHeader(List<String> collapsedColumn){
		this.collapsedColumn = collapsedColumn;
	}
	
	public void setSavedColumnHeader(Object[] dataId, String[] collapsedDataIdList){
		if (visibleColumnHeader != null){
			List<Object> currentDataIdList = Arrays.asList(this.visibleColumnHeader);
			
			List<Object> newDataIdList = new LinkedList<Object>();
			List<String> newColumnName = new LinkedList<String>();
			List<Object> hiddenId = new LinkedList<Object>();
			List<String> hiddenName = new LinkedList<String>();
			
			if (dataId != null){
				for (int i = 0 ; i < dataId.length ; i++){
					if (currentDataIdList.contains(dataId[i])){
						newDataIdList.add(dataId[i]);
						newColumnName.add(this.columnHeader[currentDataIdList.indexOf(dataId[i])]);
					}else{
						hiddenId.add(dataId[i]);
						hiddenName.add(this.columnHeader[currentDataIdList.indexOf(dataId[i])]);
					}
				}
				
				//add back those missing column...
				for (int i = 0 ; i < hiddenId.size() ; i++){
					newDataIdList.add(hiddenId.get(i));
					newColumnName.add(hiddenName.get(i));
				}
				this.visibleColumnHeader = newDataIdList.toArray();
				this.columnHeader = newColumnName.toArray(new String[0]);
			}
			
			List<String> tempDataIdList = new ArrayList<String>();
			for (Object currentData : currentDataIdList ){
				tempDataIdList.add(currentData+"");
			}
			
			if (collapsedDataIdList != null){
				collapsedColumn = new ArrayList<String>();
				for (int i = 0 ; i < collapsedDataIdList.length ; i++){
					if (tempDataIdList.contains(collapsedDataIdList[i])){
						this.collapsedColumn.add(collapsedDataIdList[i]);
					}
				}
			}
			
			//add back those missing column...
			for (Object o : hiddenId){
				this.collapsedColumn.add((String)o);
			}
		}
	}
	
	public String[] getCurrentVisibleColumnName(){
		
		//exclude collapsed column
		List<Object> propIds = new LinkedList<Object>(Arrays.asList(table.getVisibleColumns()));
		List<String> columnHeaders = new LinkedList<String>(Arrays.asList(table.getColumnHeaders()));
		final Iterator<Object> iterator = propIds.iterator();
		final Iterator<String> headerIterator = columnHeaders.iterator();
		
        while (iterator.hasNext()) {
        	
            final Object propId = iterator.next();
            headerIterator.next();
            if (table.isColumnCollapsed(propId)) {
                iterator.remove();
                headerIterator.remove();
            }
        }
		
        String[] header = new String[columnHeaders.size()];
        columnHeaders.toArray(header);
        return header;
	}
	
	private IndexedContainer getEmptyContainer(){
		IndexedContainer container = new IndexedContainer();
		if (visibleColumnHeader != null){
			for (int i = 0 ; i < visibleColumnHeader.length ; i++){
				
				Class clazz = String.class;
				
				if (pagedTableParameterMap.get(visibleColumnHeader[i]) != null){
					IPagedTableParameter parameter = pagedTableParameterMap.get(visibleColumnHeader[i]);
					switch(parameter.getColumnType()){
						case STRING:
							break;
						case BUTTON:
							clazz = Button.class;
							break;
						case CHECKBOX:
							clazz = CheckBox.class;
							break;
					}
				}
				
				container.addContainerProperty(visibleColumnHeader[i], clazz, null);
			}
		}
		return container;
	}
	
	private IndexedContainer getEmptyContainer(Object[] visibleColumnHeader){
		IndexedContainer container = new IndexedContainer();
		for (int i = 0 ; i < visibleColumnHeader.length ; i++){
			
			Class clazz = String.class;
			
			if (pagedTableParameterMap.get(visibleColumnHeader[i]) != null){
				IPagedTableParameter parameter = pagedTableParameterMap.get(visibleColumnHeader[i]);
				switch(parameter.getColumnType()){
					case STRING:
						break;
					case BUTTON:
						clazz = Button.class;
						break;
					case CHECKBOX:
						clazz = CheckBox.class;
						break;
					case MULTIBUTTON:
						clazz = CssLayout.class;
						break;
				}
			}
			
			container.addContainerProperty(visibleColumnHeader[i], clazz, null);
		}
		
		return container;
	}
	
	private void applyHeaderListener(){
		if (pagedTableParameterMap != null && visibleColumnHeader != null){
			for (int i = 0 ; i < visibleColumnHeader.length ; i++){
				final IPagedTableParameter parameter = pagedTableParameterMap.get(visibleColumnHeader[i]);
				if (parameter != null){
					if (parameter.getColumnType() == PagedTableParameterType.CHECKBOX){
						this.table.addListener(new Table.HeaderClickListener() {
							
							@Override
							public void headerClick(HeaderClickEvent event) {
								if (parameter.getPropertyId() == event.getPropertyId()){
									PagedTableContainer container = (PagedTableContainer)table.getContainerDataSource();
									Collection itemIds = container.getItemIds();
									for (Object item : itemIds){
										Property property = container.getContainerProperty(item, event.getPropertyId());
										CheckBox box = (CheckBox)property.getValue();
										if (box != null){
											box.setValue(!(Boolean)box.getValue());
										}
									}
								}
							}
						});
					}
				}
			}
		}
	}

	@Override
	public AbstractComponent getComponent(String dataId) {
		return null;
	}
	
	public void setPagedTableParameter(List<IPagedTableParameter> obj){
		this.pagedTableParameter = obj;
		for (IPagedTableParameter parameter : obj){
			pagedTableParameterMap.put(parameter.getPropertyId(), parameter);
		}
	}
	
	public List<IPagedTableParameter> getPagedTableParameter(){
		return this.pagedTableParameter;
	}
	
	public Map<String, IPagedTableParameter> getPagedTableParameterMap(){
		return this.pagedTableParameterMap;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public String getId(){
		return this.id;
	}

	public String getDataKey() {
		return dataKey;
	}

	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}

	public String getWorksheetNameKey() {
		return worksheetNameKey;
	}

	public void setWorksheetNameKey(String worksheetNameKey) {
		this.worksheetNameKey = worksheetNameKey;
	}
	
	public void setHiddenGroupColumnName(Map<String, List<String>> hiddenGroupColumnName){
		this.hiddenGroupColumnName = hiddenGroupColumnName;
	}
	
	public void setHiddenGroupColumnDataId(Map<String, List<String>> hiddenGroupColumnDataId){
		this.hiddenGroupColumnDataId = hiddenGroupColumnDataId;
	}
	
	public void activateColumn(String groupKey){
		if (!activateList.contains(groupKey))
			activateList.add(groupKey);
	}
	
	public void clearActivatedColumn(){
		if (activateList != null){
			this.activateList.clear();
		}
	}
	
	public Container getContainer(){
		return this.container;
	}
	
	public void setProcessButtonVisible(boolean processButtonVisible){
		this.processButtonVisible = processButtonVisible;
	}
	
	public void setDeleteButtonVisible(boolean deleteButtonVisible){
		this.deleteButtonVisible = deleteButtonVisible;
	}
	
	public boolean getProcessButtonVisible(){
		return this.processButtonVisible;
	}
	
	public boolean getDeleteButtonVisible(){
		return this.deleteButtonVisible;
	}
	
	public void setProcessButtonListenerList(List<ClickListener> processButtonListenerList){
		this.processButtonListenerList = processButtonListenerList;
	}
	
	public void setDeleteButtonListenerList(List<ClickListener> deleteButtonListenerList){
		this.deleteButtonListenerList = deleteButtonListenerList;
	}
	
	public List<ClickListener> getProcessButtonListenerList(){
		return this.processButtonListenerList;
	}
	
	public List<ClickListener> getDeleteButtonListenerList(){
		return this.deleteButtonListenerList;
	}
	
	public void setProcessButtonName(String processButtonName){
		this.processButtonName = processButtonName;
	}
	
	public void setDeleteButtonName(String deleteButtonName){
		this.deleteButtonName = deleteButtonName;
	}
	
	public void setDeleteButtonEnable(boolean deleteButtonEnable) {
		if (this.deleteButton != null) {
			this.deleteButton.setEnabled(deleteButtonEnable);
		}
	}
	
	public List<String> getProcessButtonStyleList() {
		return processButtonStyleList;
	}

	public void setProcessButtonStyleList(List<String> processButtonStyleList) {
		this.processButtonStyleList = processButtonStyleList;
	}

	public List<String> getDeleteButtonStyleList() {
		return deleteButtonStyleList;
	}

	public void setDeleteButtonStyleList(List<String> deleteButtonStyleList) {
		this.deleteButtonStyleList = deleteButtonStyleList;
	}

	@Override
	public Object[] getVisibleColumns() {
		return this.table.getVisibleColumns();
	}
	
	public void setFunctionName(String functionName){
		this.functionName = functionName;
	}
	
	public String getFunctionName(){
		return this.functionName;
	}

	@Override
	public List<String> getTotalColumnId() {
		return this.totalColumnId;
	}
	
	public void setTotalColumnId(List<String> totalColumnId){
		this.totalColumnId = totalColumnId;
	}

	public Integer getSelectedPageLength() {
		return selectedPageLength;
	}

	public void setSelectedPageLength(Integer selectedPageLength) {
		this.selectedPageLength = selectedPageLength;
	}

	public String[] getSelectedPageList() {
		return selectedPageList;
	}

	public void setSelectedPageList(String[] selectedPageList) {
		this.selectedPageList = selectedPageList;
	}
	
	public void sort(Object[] columnIds, boolean[] ascending){
		table.sort(columnIds, ascending);
	}

	public void setItemClickListeners(List<ItemClickEvent.ItemClickListener> clickListeners){
		this.clickListeners = clickListeners;
	}
	
	public void removeItemClickListeners(ItemClickEvent.ItemClickListener clickListener){
		table.removeListener(clickListener);
	}

}
