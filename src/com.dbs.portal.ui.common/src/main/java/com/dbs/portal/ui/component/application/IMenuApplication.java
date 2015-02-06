package com.dbs.portal.ui.component.application;

import com.dbs.portal.ui.component.menu.MenuItem;

public interface IMenuApplication extends IApplication {
	
	public boolean addFavouriteMenuItem(MenuItem item);
	public boolean removeFavouritemMenuItem(MenuItem item);
}
