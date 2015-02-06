package com.dbs.portal.ui.component.pagegrid;

import java.util.List;
import java.util.Map;

import com.dbs.portal.ui.component.view.BaseController;

public class BasePageController extends BaseController implements IPageControl{

	private List<Map<String, Object>> dataMap;
	private int currentPage = 0;
	
	@Override
	public void setData(List<Map<String, Object>> dataMap) {
		this.dataMap = dataMap;
		currentPage = 0;
	}

	@Override
	public Map<String, Object> nextPage() {
		if (dataMap != null){
			if (dataMap.size() > currentPage + 1) {
				return dataMap.get(++currentPage);
			}else{
				return dataMap.get(currentPage);
			}
		}
		return null;
	}

	@Override
	public Map<String, Object> previousPage() {
		if (dataMap != null){
			if (currentPage -1 >= 0) {
				return dataMap.get(--currentPage);
			}else{
				return dataMap.get(currentPage);
			}
		}
		return null;
	}

	@Override
	public Map<String, Object> goToPage(int pageNo) {
		if (dataMap != null &&  pageNo - 1 >= 0 && pageNo < dataMap.size()) {
			this.currentPage = pageNo - 1;
			return dataMap.get(this.currentPage);
		}
		return null;
	}

	@Override
	public Map<String, Object> lastPage() {
		if (dataMap != null) {
			this.currentPage = dataMap.size() - 1;
			return dataMap.get(this.currentPage);
		}
		return null;
	}

	@Override
	public Map<String, Object> firstPage() {
		if (dataMap != null) {
			this.currentPage = 0;
			return dataMap.get(this.currentPage);
		}
		return null;
	}

	@Override
	public int totalPage() {
		if (this.dataMap != null)
			return this.dataMap.size();
		return 0;
	}

	@Override
	public int getCurrentPage() {
		return this.currentPage + 1;
	}

}
