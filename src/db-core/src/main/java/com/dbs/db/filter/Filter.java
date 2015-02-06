package com.dbs.db.filter;

public class Filter {
	private String key = null;
	private Object value = null;
	private Logical expression = null;
	private Class joinClass = null;
	
	public Filter(String key, Object value, Logical expression){
		this.key = key ;
		this.value = value;
		this.expression = expression;
	}
	
	public Filter(Class joinClass, String key, Object value, Logical expression){
		this(key, value, expression);
		this.joinClass = joinClass;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Logical getExpression() {
		return expression;
	}

	public void setExpression(Logical expression) {
		this.expression = expression;
	}

	public Class getJoinClass() {
		return joinClass;
	}

	public void setJoinClass(Class joinClass) {
		this.joinClass = joinClass;
	}
	
}
