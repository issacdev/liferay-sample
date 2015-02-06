package com.dbs.portal.ui.component.view;


public class DefaultFreeGridView extends BaseFreeGridView{

	public DefaultFreeGridView(int noOfColumn, int noOfRow) {
		super(noOfColumn, noOfRow);
	}
	
	public DefaultFreeGridView(String caption, int noOfColumn, int noOfRow){
		super(caption,noOfColumn,noOfRow);
	}
	
//	public DefaultFreeGridView(int noOfColumn, int noOfRow, int componentHeight, int conponentWidth){
//		super(noOfColumn,noOfRow,componentHeight,conponentWidth);
//	}
//	
//	public DefaultFreeGridView(String caption, int noOfColumn, int noOfRow, int componentHeight, int conponentWidth){
//		super(caption,noOfColumn,noOfRow,componentHeight,conponentWidth);
//	}

	@Override
	public void postUpdate() {
	}

}
