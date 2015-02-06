package com.dbs.portal.ui.component.pagetable;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.dbs.portal.ui.component.application.XMLApplication;
import com.vaadin.data.Container;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;

public class PagedTable extends Table {
	private static final long serialVersionUID = 6881455780158545828L;

	private Logger logger = Logger.getLogger(this.getClass());
	
	public interface PageChangeListener {
		public void pageChanged(PagedTableChangeEvent event);
	}

	public class PagedTableChangeEvent {

		final PagedTable table;

		public PagedTableChangeEvent(PagedTable table) {
			this.table = table;
		}

		public PagedTable getTable() {
			return table;
		}

		public int getCurrentPage() {
			return table.getCurrentPage();
		}

		public int getTotalAmountOfPages() {
			return table.getTotalAmountOfPages();
		}
	}

	private List<PageChangeListener> listeners = null;
	
	private PagedTableContainer container;

	public PagedTable() {
		this(null);
	}

	public PagedTable(String caption) {
		super(caption);
		setPageLength(25);
		addStyleName("pagedtable");
	}

	public HorizontalLayout getTotalRows(){
		containerItemSetChange(new Container.ItemSetChangeEvent() {
			private static final long serialVersionUID = -5083660879306951876L;

			public Container getContainer() {
				return container;
			}
		});
		HorizontalLayout totalRowPanel = new HorizontalLayout();
		int size = container.getContainer().size();
		Label l = new Label("Total number of rows:" + size);
		totalRowPanel.addComponent(l);
		return totalRowPanel;
	}

	@Override
	public Container.Indexed getContainerDataSource() {
		return container;
	}

	@Override
	public void setContainerDataSource(Container newDataSource) {
		if (!(newDataSource instanceof Container.Indexed)) {
			throw new IllegalArgumentException(
					"PagedTable can only use containers that implement Container.Indexed");
		}
		PagedTableContainer pagedTableContainer = new PagedTableContainer(
				(Container.Indexed) newDataSource);
		pagedTableContainer.setPageLength(getPageLength());
		super.setContainerDataSource(pagedTableContainer);
		this.container = pagedTableContainer;
		firePagedChangedEvent();
	}

	private void setPageFirstIndex(int firstIndex) {
		if (container != null) {
			if (firstIndex <= 0) {
				firstIndex = 0;
			}
			if (firstIndex > container.getRealSize() - 1) {
				int size = container.getRealSize() - 1;
				int pages = 0;
				if (getPageLength() != 0) {
					pages = (int) Math.floor(0.0 + size / getPageLength());
				}
				firstIndex = pages * getPageLength();
			}
			container.setStartIndex(firstIndex);
			containerItemSetChange(new Container.ItemSetChangeEvent() {
				private static final long serialVersionUID = -5083660879306951876L;

				public Container getContainer() {
					return container;
				}
			});
			if (alwaysRecalculateColumnWidths) {
				for (Object columnId : container.getContainerPropertyIds()) {
					setColumnWidth(columnId, -1);
				}
			}
			firePagedChangedEvent();
		}
	}

	private void firePagedChangedEvent() {
		if (listeners != null) {
			PagedTableChangeEvent event = new PagedTableChangeEvent(this);
			for (PageChangeListener listener : listeners) {
				listener.pageChanged(event);
			}
		}
	}

	@Override
	public void setPageLength(int pageLength) {
		if (pageLength >= 0 && getPageLength() != pageLength) {
			container.setPageLength(pageLength);
			super.setPageLength(pageLength);
			firePagedChangedEvent();
		}
	}
	
	//beaware to using this function....
	//page no will no be updated...
	public void setPageLengthWithoutUpdatePageNo(int pageLength){
		if (pageLength >= 0 && getPageLength() != pageLength) {
			container.setPageLength(pageLength);
			super.setPageLength(pageLength);
		}
	}

	public void nextPage() {
		setPageFirstIndex(container.getStartIndex() + getPageLength());
	}

	public void previousPage() {
		setPageFirstIndex(container.getStartIndex() - getPageLength());
	}
	
	public void previousPage(int currentPageLength){
		setPageFirstIndex(container.getStartIndex() - currentPageLength);
	}

	public int getCurrentPage() {
		double pageLength = getPageLength();
		
		if (pageLength > 0){
			int page = (int) Math.floor((double) container.getStartIndex() / pageLength) + 1;
			if (page < 1) {
				page = 1;
			}
			return page;
		}
		return 1;
	}

	public void setCurrentPage(int page) {
		int newIndex = (page - 1) * getPageLength();
		if (newIndex < 0) {
			newIndex = 0;
		}
		if (newIndex >= 0 && newIndex != container.getStartIndex()) {
			setPageFirstIndex(newIndex);
		}
	}

	public int getTotalAmountOfPages() {
		double pageLength = getPageLength();
		if (pageLength > 0){
			int size = container.getContainer().size();
			int pageCount = (int) Math.ceil(size / pageLength);
			if (pageCount < 1) {
				pageCount = 1;
			}
			return pageCount;
		}
		return 1;
	}

	public void addListener(PageChangeListener listener) {
		if (listeners == null) {
			listeners = new ArrayList<PageChangeListener>();
		}
		listeners.add(listener);
	}

	public void removeListener(PageChangeListener listener) {
		if (listeners == null) {
			listeners = new ArrayList<PageChangeListener>();
		}
		listeners.remove(listener);
	}
	
	public void setAlwaysRecalculateColumnWidths(
			boolean alwaysRecalculateColumnWidths) {
		this.alwaysRecalculateColumnWidths = alwaysRecalculateColumnWidths;
	}
	
	public int getContainerFirstIndex(){
		return container.getStartIndex();
	}

}