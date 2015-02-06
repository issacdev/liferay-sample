package com.dbs.portal.ui.layout;

import java.awt.Dimension;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.vaadin.terminal.Resource;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;

public class HTMLGrid {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	private List<String> styleNames = new ArrayList<String>();
	private int totalColumn = 0;
	private int totalRow = 0;
	
	private boolean sizeFull = false;
	
	private StringBuilder sb = new StringBuilder();
	
	private Map<Integer, Map<Integer, HTMLGridContent>> matrix = new HashMap<Integer, Map<Integer, HTMLGridContent>>();
	private Map<String, Dimension> spanMap = new HashMap<String, Dimension>();
	
	private Set<String> filledList = new HashSet<String>();
	
	private String caption = null;
	
	public void addStyleName(String styleName){
		this.styleNames.add(styleName);
	}
	
	public void setColumns(int column){
		this.totalColumn = column;
	}
	
	public void setRows(int row){
		this.totalRow = row;
	}
	
	public void setSizeFull(){
		this.sizeFull = true;
	}
	
	public void removeAllComponents(){
		sb.delete(0, sb.length());
		matrix.clear();
	}
	
	public void setCaption(String caption){
		this.caption = caption;
	}
	
	public void removeComponent(int column, int row){
		if (matrix != null){
			Map<Integer, HTMLGridContent> rowMap = matrix.get(row);
			if (rowMap != null){
				rowMap.remove(column);
			}
		}
	}
	
	public HTMLGridContent getComponent(int column, int row){
		if (matrix != null){
			Map<Integer, HTMLGridContent> rowMap = matrix.get(row);
			if (rowMap != null)
				return rowMap.get(column);
		}
		return null;
	}
	
	public void replaceComponent(int column, int row, HTMLGridContent newComponent){
		if (matrix != null){
			Map<Integer, HTMLGridContent> rowMap = matrix.get(row);
			if (rowMap != null){
				rowMap.put(column, newComponent);
			}
		}
	}
	
	public void addComponent(HTMLGridContent component, int column, int row){
		if (matrix != null){
			Map<Integer, HTMLGridContent> rowMap = matrix.get(row);
			if (rowMap == null){
				rowMap = new HashMap<Integer, HTMLGridContent>();
				rowMap.put(column, component);
				matrix.put(row, rowMap);
			}else{
				rowMap.put(column, component);
			}
		}
		
		if (totalColumn < column){
			totalColumn = column;
		}
		
		if (totalRow < row){
			totalRow = row;
		}
	}
	
	public void addComponent(HTMLGridContent component, int column, int row, int cspan, int rspan){
		addComponent(component, column, row);
		if (cspan > 1 || rspan > 1){
			spanMap.put(row+","+column, new Dimension(rspan, cspan));
		}
	}
	
	
	public AbstractLayout getLayout(){
		ByteArrayInputStream bais = null;
		CustomLayout layout = null;
		try{
			sb.delete(0, sb.length());
			filledList.clear();
			constructHTML();
			bais = new ByteArrayInputStream(sb.toString().getBytes());
			layout = new CustomLayout(bais);
			bais.close();
		}catch(Exception e){
			logger.debug(e.getMessage(), e);
		}finally{
			if (bais != null){
				try{
					bais.close();
				}catch(Exception e){
					logger.debug(e.getMessage(), e);
				}
			}
		}
		
		return layout;
	}
	
	private void constructHTML(){
		//create table
		sb.append("<table ");
		if (sizeFull)
			sb.append("width=\"99%\" ");
		if (styleNames.size() > 0){
			sb.append("class=\"");
			for (String styleName : styleNames){
				sb.append(styleName+" ");
			}
			sb.append("\"");
		}
		sb.append(">");
		
		constructHeader();
		
		constructRow(matrix);
		
		//end of table
		sb.append("</table>");
	}
	
	private void constructHeader(){
		if (caption != null && !"".equals(caption.trim())){
			sb.append("<tr>");
			sb.append("<td class=\"xmlGridLayout-caption2\" height=\"25px\" colspan=\""+totalColumn+"\">");
			sb.append("<img src=\"/kms/html/VAADIN/themes/dbsportal/img/table_tit_ico.png\" class=\"xmlGridLayout-caption-icon\"/>");
			sb.append("<span class=\"xmlGridLayout-caption\">"+caption+"</span>");
			sb.append("</td>");
			sb.append("</tr>");
		}
	}
	
	private void constructRow(Map<Integer, Map<Integer, HTMLGridContent>> matrixMap){
		for (int i = 0 ; i <= totalRow ; i++){
			sb.append("<tr>");
			Map<Integer, HTMLGridContent> rowMap = matrixMap.get(i);
			if (rowMap == null)
				constructEmpty(i);
			else
				constructColumn(i, rowMap);
			sb.append("</tr>");
		}
		
	}
	
	private void constructColumn(int rowNo, Map<Integer, HTMLGridContent> rowMap){
		int[] columnArray = new int[rowMap.size()];
		int count = 0 ;
		Iterator<Integer> itColumn = rowMap.keySet().iterator();
		while (itColumn.hasNext()){
			columnArray[count++] = itColumn.next();
		}
		
		Arrays.sort(columnArray);
		
		for (int i = 0 ; i < totalColumn ; i++){
			HTMLGridContent component = rowMap.get(i);
			if (component == null){
				if (!filledList.contains(rowNo+","+i)){
					sb.append("<td>&nbsp</td>");
					filledList.add(rowNo+","+i);
				}
			}else{
				sb.append("<td ");
				HTMLGridContent tempLayout = (HTMLGridContent)rowMap.get(i);
				if (tempLayout.getStyleList() != null && !tempLayout.getStyleList().isEmpty()){
					sb.append(" class=\""+tempLayout.getStyleNames()+"\" ");
				}
				
				if (spanMap.get(rowNo+","+i) != null){
					Dimension span = spanMap.get(rowNo+","+i);
					if (span != null){
						int rowSpan = (int)span.getWidth();
						int columnSpan = (int)span.getHeight();
						
						if (rowSpan > 1){
							sb.append("rowspan=\""+(rowSpan)+"\" ");
						}
						
						if (columnSpan > 1){
							sb.append("colspan=\""+(columnSpan)+"\"");
						}
						
						for (int row = rowNo ; row < rowNo + rowSpan ; row++){
							for (int j = i ; j < i + columnSpan ; j++){
								filledList.add(row+","+j);
							}
						}
					}
				}else{
					filledList.add(rowNo+","+i);
				}
				
				sb.append(">");
				if (component.getType() == HTMLGridType.LABEL){
					sb.append(component.getContent());
				}else if (component.getType() == HTMLGridType.IMAGE){
					String imageSrc = "/html/VAADIN/themes/dbsportal/"+component.getContent();
					sb.append("<img src=\""+imageSrc+"\"/>");
				}
				sb.append("</td>");
			}
		}
	}
	
	private void constructEmpty(int rowNo){
		sb.append("<tr>");
			for (int i = 0 ; i < totalColumn ; i++){
				if (!filledList.contains(rowNo+","+i)){
					filledList.add(rowNo+","+i);
					sb.append("<td>&nbsp;</td>");
				}
			}
		sb.append("</tr>");
	}
}
