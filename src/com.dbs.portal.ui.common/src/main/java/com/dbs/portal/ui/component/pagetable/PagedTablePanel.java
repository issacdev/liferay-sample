package com.dbs.portal.ui.component.pagetable;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.dbs.portal.ui.component.application.IApplication;
import com.dbs.portal.ui.util.LanguageChanger;
import com.vaadin.data.Container;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.validator.IntegerValidator;
import com.vaadin.terminal.Resource;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

public class PagedTablePanel extends Panel{
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	private CssLayout captionLayout = new CssLayout();
	
	private PagedTable table;
	private Container container;
	private boolean sizeFull = false;
	
	//controls for paging
	private boolean controlRequired;
	private List<ComboBox> itemsPerPageSelectList = new ArrayList<ComboBox>();
	private List<TextField> pageNoFieldList = new ArrayList<TextField>();
	private List<Label> totalCountList = new ArrayList<Label>();
	private List<Label> totalPageList = new ArrayList<Label>();
	
	private int contentCount = 0;
	
	private Integer defaultSelectedPageLength = 25;
	private Integer selectedPageLength = 25;
	private String[] selectedPageList = new String[] {"5", "10", "25", "50"};
	
	private boolean pageUpdating = false;
	
	private boolean toggle = false;
	
	private int currentPage = 0;
	private int totalPage = 0;
	
	public PagedTablePanel(PagedTable table, boolean controlRequired){
		this(table, controlRequired, true);
		captionLayout.setVisible(false);
	}
	
	public PagedTablePanel(PagedTable table, boolean controlRequired, boolean sizeFull){
		super();
		this.table = table;
		table.setImmediate(true);
		this.controlRequired = controlRequired;
		this.sizeFull = sizeFull;
		captionLayout.setVisible(false);
		
	}
	
	public void packLayout(){
		VerticalLayout mainLayout = new VerticalLayout();
		mainLayout.addStyleName("pagedTable-setmargin");
		mainLayout.setMargin(false);
		this.setContent(mainLayout);
		
		if (controlRequired){
			this.addComponent(createControlLayout());
		}
		
		mainLayout.addComponent(captionLayout);
		mainLayout.addComponent(this.table);
		
/*		if (sizeFull)*/
			this.setSizeFull();
/*		else
			this.setSizeUndefined();*/
		
		this.setStyleName("borderless");
		
		((AbstractLayout)this.getContent()).setMargin(false);
	}
	
	public void setCaption(String tableCaption) {
		addCaption(tableCaption);
		captionLayout.setVisible(true);
	}
	
	private void addCaption(String caption) {
		captionLayout.setHeight("25px");
		captionLayout.addStyleName("pagedTable-caption2");
		captionLayout.setWidth(100, Sizeable.UNITS_PERCENTAGE);
		
		Resource icon = new ThemeResource("img/table_tit_ico.png");
		Label iconLabel = new Label();
		iconLabel.setIcon(icon);
		iconLabel.setHeight(null);
		iconLabel.setWidth(null);
		iconLabel.addStyleName("pagedTable-caption-icon");
		
		Label titleLabel = new Label(caption);
		titleLabel.setWidth(null);
		titleLabel.addStyleName("pagedTable-caption");
		
		LanguageChanger changer = ((IApplication)this.getApplication()).getLanguageChanger();
		changer.changeCode(titleLabel);
		
		captionLayout.addComponent(iconLabel);
		captionLayout.addComponent(titleLabel);
	}
	
	public void setContainerDataSource(Container container){
		this.table.removeAllItems();
		this.table.setContainerDataSource(container);
		this.container = container;
		
		for (Label label : totalCountList){
			label.setValue(this.container.getItemIds().size());
		}
		
		if (!controlRequired){
			selectedPageLength = Integer.MAX_VALUE;
		}else{
			if (itemsPerPageSelectList.size() == 0)
				selectedPageLength = defaultSelectedPageLength;
			else
				selectedPageLength = Integer.parseInt((String)itemsPerPageSelectList.get(0).getValue());	
		}
		
		contentCount = container.getItemIds().size();
		
		if (!controlRequired || contentCount < selectedPageLength){
			this.table.setPageLength(contentCount);
			selectedPageLength = contentCount;
		}else{
			//TODO
			this.table.setPageLength(selectedPageLength);
		}
		
		this.table.requestRepaint();
		
		if (selectedPageLength > 0)
			checkLastPage();
	}
	
	private HorizontalLayout createControlLayout(){
		
		LanguageChanger changer = ((IApplication)this.getApplication()).getLanguageChanger();
		
		HorizontalLayout counterLayout = new HorizontalLayout();
		counterLayout.setSpacing(true);
		
		//Total Count Label
		Label counterCaptionLabel = new Label("common.table.totalCount");
		changer.changeCode(counterCaptionLabel);
		counterLayout.addComponent(counterCaptionLabel);
		
		Label colonLabel = new Label(" : ");
		counterLayout.addComponent(colonLabel);
		
		Label totalCountLabel = new Label("0");
		totalCountList.add(totalCountLabel);
		counterLayout.addComponent(totalCountLabel);
		
		//Row Per Page 	
		Label rowPerPageCaptionLabel = new Label("common.table.rowPerPage");
		changer.changeCode(rowPerPageCaptionLabel);
		counterLayout.addComponent(rowPerPageCaptionLabel);
		
		Label rowPerPageColonLabel = new Label(" : ");
		counterLayout.addComponent(rowPerPageColonLabel);
		
		ComboBox itemsPerPageSelect = new ComboBox();
		itemsPerPageSelectList.add(itemsPerPageSelect);

		itemsPerPageSelect.setTextInputAllowed(false);
		
		for (String selectedPage : selectedPageList) {
			itemsPerPageSelect.addItem(selectedPage);
		}
				
		itemsPerPageSelect.setImmediate(true);
		itemsPerPageSelect.setNullSelectionAllowed(false);
		itemsPerPageSelect.setWidth("50px");
		
		itemsPerPageSelect.addListener(new ValueChangeListener() {
			private static final long serialVersionUID = -2255853716069800092L;

			public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
				if (!pageUpdating){
					synchronized (PagedTablePanel.class) {
					
						pageUpdating = true;
						selectedPageLength = Integer.valueOf(String.valueOf(event.getProperty().getValue())); 
						for (ComboBox comboBox : itemsPerPageSelectList){
							if (!comboBox.getValue().equals(event.getProperty().getValue()))
								comboBox.setValue(String.valueOf(selectedPageLength));
						}
						
						PagedTablePanel.this.table.setPageLength(selectedPageLength);
						pageUpdating = false;
						toggle = false;
						
						checkLastPage();
						
						resetSize();
					}
				}
			}
		});
		itemsPerPageSelect.select(defaultSelectedPageLength.toString());
		table.setPageLength(defaultSelectedPageLength);
		selectedPageLength = defaultSelectedPageLength;

		counterLayout.addComponent(itemsPerPageSelect);
		
		//Page of Total Page
		Label pageLabel = new Label("Page:&nbsp;", Label.CONTENT_XHTML);
		final TextField currentPageTextField = new TextField();
		pageNoFieldList.add(currentPageTextField);
		
		currentPageTextField.setValue(String.valueOf(table.getCurrentPage()));
		currentPageTextField.addValidator(new IntegerValidator(null));
		Label separatorLabel = new Label("&nbsp;/&nbsp;", Label.CONTENT_XHTML);
		final Label totalPagesLabel = new Label(String.valueOf(table.getTotalAmountOfPages()), Label.CONTENT_XHTML);
		totalPageList.add(totalPagesLabel);
		currentPageTextField.setStyleName(Reindeer.TEXTFIELD_SMALL);
		currentPageTextField.setImmediate(true);
		currentPageTextField.addListener(new ValueChangeListener() {
			private static final long serialVersionUID = -2255853716069800092L;

			public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
				if (currentPageTextField.isValid() && currentPageTextField.getValue() != null) {
					int page = Integer.valueOf(String.valueOf(currentPageTextField.getValue()));
					PagedTablePanel.this.table.setPageLength(selectedPageLength);
					PagedTablePanel.this.table.setCurrentPage(page);
				}
			}
		});
		pageLabel.setWidth(null);
		currentPageTextField.setWidth("40px");
		separatorLabel.setWidth(null);
		totalPagesLabel.setWidth(null);
		
	
		final Button first = new Button("<<", new ClickListener() {
			private static final long serialVersionUID = -355520120491283992L;

			public void buttonClick(ClickEvent event) {
				PagedTablePanel.this.table.setPageLength(selectedPageLength);
				PagedTablePanel.this.table.setCurrentPage(0);
				checkLastPage();
			}
		});
		final Button previous = new Button("<", new ClickListener() {
			private static final long serialVersionUID = -355520120491283992L;

			public void buttonClick(ClickEvent event) {
				PagedTablePanel.this.table.setPageLengthWithoutUpdatePageNo(selectedPageLength);
				PagedTablePanel.this.table.previousPage(selectedPageLength);
				checkLastPage();
			}
		});
		final Button next = new Button(">", new ClickListener() {
			private static final long serialVersionUID = -1927138212640638452L;

			public void buttonClick(ClickEvent event) {
				if (currentPage != totalPage){
					PagedTablePanel.this.table.setPageLength(selectedPageLength);
					PagedTablePanel.this.table.nextPage();
					checkLastPage();
				}
			}
		});
		final Button last = new Button(">>", new ClickListener() {
			private static final long serialVersionUID = -355520120491283992L;

			public void buttonClick(ClickEvent event) {
				if (currentPage != totalPage){
					PagedTablePanel.this.table.setPageLength(selectedPageLength);
					PagedTablePanel.this.table.setCurrentPage(PagedTablePanel.this.table.getTotalAmountOfPages());
					checkLastPage();
				}
			}
		});
		
		HorizontalLayout hLayout = new HorizontalLayout();
		hLayout.setWidth("200");
		hLayout.addComponent(first);
		hLayout.addComponent(previous);
		hLayout.addComponent(pageLabel);
		hLayout.addComponent(currentPageTextField);
		hLayout.addComponent(separatorLabel);
		hLayout.addComponent(totalPagesLabel);
		hLayout.addComponent(next);
		hLayout.addComponent(last);
		
		counterLayout.addComponent(hLayout);
		
		first.setStyleName(Reindeer.BUTTON_LINK);
		previous.setStyleName(Reindeer.BUTTON_LINK);
		next.setStyleName(Reindeer.BUTTON_LINK);
		last.setStyleName(Reindeer.BUTTON_LINK);

		first.addStyleName("pagedtable-first");
		previous.addStyleName("pagedtable-previous");
		next.addStyleName("pagedtable-next");
		last.addStyleName("pagedtable-last");

		first.addStyleName("pagedtable-button");
		previous.addStyleName("pagedtable-button");
		next.addStyleName("pagedtable-button");
		last.addStyleName("pagedtable-button");
		
		counterLayout.addStyleName("pagedTableCounterBar");
		return counterLayout;
	}
	
	private void addComponentStyle(Component component) {
		component.setWidth(null);
		component.addStyleName("pagedTableControl-padding");
	}
	
	private void checkLastPage(){
		if (selectedPageLength > 0){
			totalPage = ((contentCount % selectedPageLength == 0)?0 : 1) + contentCount / selectedPageLength;
			if (PagedTablePanel.this.table.getContainerFirstIndex() >= contentCount - selectedPageLength){
				for (TextField field : pageNoFieldList){
					field.setValue(totalPage);
				}
				PagedTablePanel.this.table.setPageLength((contentCount % selectedPageLength) == 0 ? selectedPageLength : contentCount % selectedPageLength );
			}else{
				int currentPage = PagedTablePanel.this.table.getContainerFirstIndex() / selectedPageLength;
				for (TextField field : pageNoFieldList){
					field.setValue(currentPage + 1);
				}
			}
			for (Label label : totalPageList){
				label.setValue(totalPage);
			}
		}
	}
	
	private void resetSize(){
		try{
			this.getWindow().executeJavaScript("resizedelay();");
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
	}

	public Integer getSelectedPageLength() {
		return selectedPageLength;
	}

	public void setSelectedPageLength(Integer selectedPageLength) {
		this.selectedPageLength = selectedPageLength;
	}

	public Integer getDefaultSelectedPageLength() {
		return defaultSelectedPageLength;
	}

	public void setDefaultSelectedPageLength(Integer defaultSelectedPageLength) {
		this.defaultSelectedPageLength = defaultSelectedPageLength;
	}

	public String[] getSelectedPageList() {
		return selectedPageList;
	}

	public void setSelectedPageList(String[] selectedPageList) {
		this.selectedPageList = selectedPageList;
	}
}
