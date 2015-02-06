package com.dbs.portal.ui.component.data;

import java.util.List;
import java.util.Map;

import com.dbs.portal.ui.component.view.IViewDataHandler;

public class DefaultDBDataProvider extends DBDataProvider{

	@Override
	public List<Map<String, Object>> getData() {
		// TODO Auto-generated method stub
		return this.mapData;
	}
}
