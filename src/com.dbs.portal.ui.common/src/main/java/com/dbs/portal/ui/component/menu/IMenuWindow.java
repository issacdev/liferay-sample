package com.dbs.portal.ui.component.menu;

import com.vaadin.terminal.Resource;

public interface IMenuWindow {
	
	public void setRootItem(MenuItem rootItem);
	
	public void init();
	
	public void setMenuView(IMenuView menuView);
	
	public void open(Resource resource, String target);
	
	public void open(Resource resource);
	
	public void setFavouritable(boolean isFavouritable);
}
