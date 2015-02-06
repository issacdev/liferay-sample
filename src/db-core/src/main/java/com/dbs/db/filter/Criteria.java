package com.dbs.db.filter;

import java.util.ArrayList;
import java.util.List;

public class Criteria {
	public List<Object> criteria = new ArrayList<Object>();
	public List<Sort> sorting = new ArrayList<Sort>();
	public boolean isAudit = false;
	
	public Criteria addFilter(String key, Object value, Logical expression){
		criteria.add(new Filter(key, value, expression));
		return this;
	}
	
	public Criteria addFilter(Class joinClass, String key, Object value, Logical expression){
		criteria.add(new Filter(joinClass, key, value, expression));
		return this;
	}
	
	public Criteria addLogical(Logical expression){
		criteria.add(expression);
		
		return this;
	}
	
	public Criteria addSorting(String key, Logical expression){
		sorting.add(new Sort(key, expression));
		return this;
	}
	
	public Criteria addSorting(Class joinClass, String key, Logical expression){
		sorting.add(new Sort(joinClass, key, expression));
		return this;
	}
}
