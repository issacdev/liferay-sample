package com.dbs.portal.ui.component.menu;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.ui.AbstractComponent;

public class MenuItem {
	
	private MenuItem parent = null;
	private List<MenuItem> childList = new ArrayList<MenuItem>();
	private String name;
	private String url;
	private String menuName;
	private AbstractComponent component = null;
	
	private boolean isExpanded = false;
	private boolean isSelected = false; 
	private boolean isFavouriteItem = false;
	private boolean isHidden = false;
	
	public MenuItem(String name, String url, String menuName, List<MenuItem> childList){
		setName(name);
		setUrl(url);
		setMenuName(menuName);
		for (MenuItem child : childList){
			addChild(child);
		}
	}
	
	public MenuItem(String name, String url, String menuName){
		setName(name);
		setUrl(url);
		setMenuName(menuName);
	}
	
	public boolean hasParent(){
		return parent != null;
	}

	public MenuItem getParent() {
		return parent;
	}

	public void setParent(MenuItem parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	
	public void addChild(MenuItem menuItem){
		if (!menuItem.isHidden)
			this.childList.add(menuItem);
		menuItem.setParent(this);
	}
	
	public void addChildAt(int position, MenuItem menuItem){
		this.childList.add(position, menuItem);
		menuItem.setParent(this);
	}
	
	public void removeChild(MenuItem item){
		this.childList.remove(item);
	}
	
	public List<MenuItem> getChildList(){
		return this.childList;
	}
	
	public boolean hasChild(){
		return this.childList != null && this.childList.size() > 0;
	}
	
	public boolean isExpanded(){
		return this.isExpanded;
	}
	
	public void setExpanded(boolean isExpanded){
		this.isExpanded = isExpanded;
	}
	
	public boolean isSelected(){
		return this.isSelected;
	}
	
	public void setSelected(boolean isSelected){
		this.isSelected = isSelected;
	}
	
	public String toString(){
		return this.menuName;
	}

	public AbstractComponent getComponent() {
		return component;
	}

	public void setComponent(AbstractComponent component) {
		this.component = component;
	}
	
	public void setChildList(List<MenuItem> children){
		for (MenuItem child : childList){
			addChild(child);
		}
	}
	
	public void setFavouriteItem(boolean isFavouriteItem){
		this.isFavouriteItem = isFavouriteItem;
	}
	
	public boolean isFavouriteItem(){
		return this.isFavouriteItem;
	}

	public boolean isHidden() {
		return isHidden;
	}

	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}
	
}
