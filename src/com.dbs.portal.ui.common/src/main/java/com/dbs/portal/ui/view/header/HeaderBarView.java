package com.dbs.portal.ui.view.header;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dbs.portal.ui.component.application.IApplication;
import com.dbs.portal.ui.component.view.IView;
import com.dbs.portal.ui.util.LanguageChanger;
import com.dbs.portal.ui.util.LanguageSwitcher;
import com.dbs.portal.ui.util.Messages;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class HeaderBarView extends HorizontalLayout implements IView{
	
	private List<String> headerDescription;
	private Map<String, Label> headerMap; 
	private Messages messages;
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	
	public HeaderBarView(){
		
	}
	
	public void setHeaderDescription(List<String> headerDescription){
		this.headerDescription = headerDescription;
	}
	
	public void changeHeader(int headerSequence){
		resetSelectedStyle();
		setSelected(headerSequence);
	}
	
	private void resetSelectedStyle(){
		for (int i = 0 ; i < this.headerDescription.size() ; i++){
			Label label = this.headerMap.get(i+"");
			Label edge = this.headerMap.get(i+"_mid");
			
			if (label != null){
				label.removeStyleName("header_selected");
				label.addStyleName("header_nonselected");
			}
			if (edge != null){
				edge.removeStyleName("header_edge_selected");
				edge.removeStyleName("header_edge_half_selected");
				edge.addStyleName("header_edge_nonselected");
			}
		}
	}
	
	private void setSelected(int sequence){
		if (headerMap != null){
			Label label = this.headerMap.get(sequence+"");
			Label edge = this.headerMap.get(sequence+"_mid");
			Label half = this.headerMap.get((sequence-1)+"_mid");
			
			
			if (label != null){
				label.removeStyleName("header_nonselected");
				label.addStyleName("header_selected");
			}
			if (edge != null){
				edge.removeStyleName("header_edge_nonselected");
				edge.addStyleName("header_edge_selected");
			}
			if (half != null){
				half.removeStyleName("header_edge_nonselected");
				half.addStyleName("header_edge_half_selected");
			}
		}
	}

	@Override
	public void packLayout() {
		//init header Mapping
		headerMap = new HashMap<String, Label>();
		LanguageChanger changer = ((IApplication)this.getApplication()).getLanguageChanger();
				
		if (headerDescription != null){
			
			for (int i = 0 ; i < headerDescription.size() ; i++){
				//init label
				Label label = new Label(headerDescription.get(i));
				changer.changeCode(label);
				headerMap.put(i+"", label);
				this.addComponent(label);
				
				if (i + 1 < headerDescription.size()){
					Label edgeLabel = new Label("");
					headerMap.put(i+"_mid",edgeLabel);
					this.addComponent(edgeLabel);
				}
			}
			
			changeHeader(0);
		}
	}
}
