package com.dbs.portal.ui.component.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class BaseWindow extends Window implements IWindow{

	private CssLayout mainLayout = new CssLayout();
	private Map<String, IView> mapOfLayout = null;
	
	private Map<String, IView> hiddenLayout = new HashMap<String, IView>();
	private Map<IView, Boolean> originalVisibility = new HashMap<IView, Boolean>();
	
	private boolean timeStampRequired = true;
	private TimeStampView timeStampView = new TimeStampView();
	
	private List<String> layoutPackedList = new ArrayList<String>();
	
	private String id = null;
	
	private boolean isExporting = false;
	
	public void init() {
		
		((AbstractLayout)this.getContent()).addStyleName("main-margin");
		
		mainLayout.setSizeUndefined();
		mainLayout.setWidth("100%");
		this.setWidth("90%");
		this.addComponent(mainLayout);
		
		VerticalLayout othersLayout = new VerticalLayout();
		othersLayout.setSpacing(true);
		boolean firstLayout = true;
		
		if (mapOfLayout != null){
			for (Iterator<String> it = mapOfLayout.keySet().iterator() ; it.hasNext() ;){
				String key = it.next();
				IView layout = mapOfLayout.get(key);
				if (firstLayout){
					mainLayout.addComponent((AbstractLayout)layout);
					firstLayout = false;
					mainLayout.addComponent(othersLayout);
				}else
					othersLayout.addComponent((AbstractLayout)layout);
				if (((AbstractLayout)layout).isVisible()){
					layout.packLayout();
					layoutPackedList.add(key);
				}
				originalVisibility.put(layout, ((AbstractLayout)layout).isVisible());
			}
		}
		
		if (hiddenLayout != null){
			for (Iterator<String> it = hiddenLayout.keySet().iterator() ; it.hasNext() ;){
				String key = it.next();
				IView layout = hiddenLayout.get(key);
				othersLayout.addComponent((AbstractLayout)layout);
				if (((AbstractLayout)layout).isVisible()){
					layout.packLayout();
					layoutPackedList.add(key);
				}
				othersLayout.removeComponent((AbstractLayout)layout);
				originalVisibility.put(layout, ((AbstractLayout)layout).isVisible());
			}
		}
		
		if (timeStampRequired){
			timeStampView.packLayout();
			mainLayout.addComponent(timeStampView);
		}
		
		//add close listener inorder to close
		//application immediately after user leave the page
		this.addListener(new CloseListener(){

			@Override
			public void windowClose(CloseEvent e) {
				if (!isExporting){
					BaseWindow.this.getApplication().close();
				}else{
					isExporting = false;
				}
			}
		});
	}

	public void setMapOfLayout(Map<String, IView> mapOfLayout) {
		this.mapOfLayout = mapOfLayout;
	}
	
	public Map<String, IView> getMapOfLayout(){
		return this.mapOfLayout;
	}
	
	public void setHiddenLayout(Map<String, IView> hiddenLayout){
		this.hiddenLayout = hiddenLayout;
	}

	public IView getView(String name) {
		IView view = mapOfLayout.get(name);
		
		if (view == null){
			view = hiddenLayout.get(name);
		}
		
		if (!layoutPackedList.contains(name)){
			view.packLayout();
			layoutPackedList.add(name);
		}
		
		return view;
	}
	
/*	public void setAllInvisible(){
		int i = 0;
		for(Iterator<String> it = mapOfLayout.keySet().iterator() ; it.hasNext() ;){
			String key = it.next();
			IView layout = mapOfLayout.get(key);
			layout.setVisible(false);
		}
	}*/
	
	public void setNoResultViewInvisible() {
		String key = "NoResultView";
		if (mapOfLayout.containsKey(key)) {
			IView layout = mapOfLayout.get(key);
			if (layout != null) {
				layout.setVisible(false);
			}
		}
	}

	public void resetPage(){
		for (Iterator<IView> it = originalVisibility.keySet().iterator() ; it.hasNext() ;){
			IView visibleView = it.next();
			visibleView.setVisible(originalVisibility.get(visibleView));
		}
	}
	
	public void setTimeStampRequired(boolean timeStampRequired){
		this.timeStampRequired = timeStampRequired;
	}
	
	public boolean getTimeStampRequired(){
		return this.timeStampRequired;
	}
	
	public void updateTimeStamp(){
		timeStampView.refresh();
	}
	
	public void setId(String id){
		this.id= id;
	}
	
	public String getId(){
		return this.id;
	}

	@Override
	public void retrieveInfo() {
		if (mapOfLayout != null){
			for (Iterator<String> it = mapOfLayout.keySet().iterator() ; it.hasNext() ;){
				String key = it.next();
				IView layout = mapOfLayout.get(key);
				if (layout instanceof IEnquiryView){
					((IEnquiryView)layout).retrieveInfo();
				}
			}
		}
		
		if (hiddenLayout != null){
			for (Iterator<String> it = hiddenLayout.keySet().iterator() ; it.hasNext() ;){
				String key = it.next();
				IView layout = hiddenLayout.get(key);
				if (layout instanceof IEnquiryView){
					((IEnquiryView)layout).retrieveInfo();
				}
			}
		}
	}
	
	public void setExporting(boolean isExporting){
		this.isExporting = isExporting;
	}
	
	public boolean isExporting(){
		return this.isExporting;
	}
}
