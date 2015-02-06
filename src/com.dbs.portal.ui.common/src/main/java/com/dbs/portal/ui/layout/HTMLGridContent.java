package com.dbs.portal.ui.layout;

import java.util.List;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;

public class HTMLGridContent {
	private List<String> styleList = null;
	private Component content = null;
	private HTMLGridType type = null;
	
	public List<String> getStyleList() {
		return styleList;
	}
	public void setStyleList(List<String> styleList) {
		this.styleList = styleList;
	}
	public String getStyleNames(){
		
		if (styleList != null){
			String output = "";
			for (String style : styleList){
				output += style +" ";
			}
			return output;
		}else{
			return null;
		}
	}
	public Object getContent() {
		if (content instanceof Label){
			Label label = (Label)content;
			if (label.getIcon() != null){
				ThemeResource resource = (ThemeResource)label.getIcon();
				return resource.getResourceId();	
			}else{
				if (label.getValue() == null)
					return "";
				else
					return  label.getValue();	
			}
		}
		return null;
	}
	public void setContent(Component content) {
		this.content = content;
	}
	
	public HTMLGridType getType(){
		if (content instanceof Label){
			Label label = (Label)content;
			if (label.getIcon() != null){
				return HTMLGridType.IMAGE;
			}else
				return HTMLGridType.LABEL;
		}
		return null;
	}
	
	
	
	
}
