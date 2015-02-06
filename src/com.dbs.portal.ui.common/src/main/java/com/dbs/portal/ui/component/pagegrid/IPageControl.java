package com.dbs.portal.ui.component.pagegrid;

import java.util.List;
import java.util.Map;

import com.dbs.portal.ui.component.view.IController;

public interface IPageControl extends IController{
	
	public void setData(List<Map<String, Object>> dataMap);
	
	public Map<String, Object> nextPage();
	
	public Map<String, Object> previousPage();
	
	public Map<String, Object> goToPage(int pageNo);
	
	public Map<String, Object> lastPage();
	
	public Map<String, Object> firstPage();
	
	public int totalPage();
	
	public int getCurrentPage();
	
}
