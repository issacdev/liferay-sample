package com.dbs.portal.ui.component.menu;

public interface IMenuView {
	
	public void setRootItem(MenuItem rootItem);
	
	public void packLayout();
	
	public void setWindow(IMenuWindow window);
	
	public void setFavouritable(boolean isFavouritable);
}
