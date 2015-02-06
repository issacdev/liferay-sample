package com.dbs.portal.ui.component.menu;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import org.vaadin.jouni.animator.AnimatorProxy;
import org.vaadin.jouni.animator.AnimatorProxy.AnimationEvent;
import org.vaadin.jouni.animator.AnimatorProxy.AnimationListener;
import org.vaadin.jouni.animator.client.ui.VAnimatorProxy.AnimType;

import com.dbs.portal.ui.component.application.IMenuApplication;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class AnimatedMenu extends VerticalLayout implements IMenuView{

	private MenuItem root;
	private IMenuWindow window;
	private AnimatorProxy proxy = new AnimatorProxy();
	
	private int duration = 500;
	private int delay = 50;
	
	private int menuItemHeight = 14;
	
	private boolean isFavouritable = false;
	
	private AbstractLayout favouriteMenuItem;
	private MenuItem favouriteMenu;
	
	private Vector<MenuItem> selectMenuItem = new Vector<MenuItem>();
	
	private Set<MenuItem> expendedRootMenuItem = new HashSet<MenuItem>();
	
	public void setRootItem(MenuItem root){
		this.root = root;
	}
	
	@Override
	public void packLayout() {
		int level = 1;
		if (root != null){
			this.setMargin(false);
			
			CssLayout layout = new CssLayout();
			layout.setWidth("100%");
			proxy.addStyleName("menu_proxy");
			
			for (MenuItem rootItem : root.getChildList()){
				layout.addComponent(createMenu(rootItem, level));
			}
			layout.addComponent(proxy);
			this.addComponent(layout);
			
		}
		this.setImmediate(true);
		
	}
	
	private AbstractLayout createMenu(MenuItem item, int level){
		if (item.hasChild() || (level ==  1 && item.isFavouriteItem())){
			
			final CssLayout menuLayout = new CssLayout();
			menuLayout.setWidth("100%");
			menuLayout.setImmediate(true);
			
			CssLayout menuItemLayout = new CssLayout();
			menuItemLayout.setWidth("100%");
			Label label = new Label(item.getMenuName());
			label.setHeight(menuItemHeight+"px");
			label.setData(item);
			label.addStyleName("animatedMenuParent_Level"+level);
			menuItemLayout.addComponent(label);
			item.setComponent(label);
			
			menuLayout.addComponent(menuItemLayout);
			//layout for child
			final CssLayout childLayout = new CssLayout();
			
			if (isFavouritable && item.isFavouriteItem() && level == 1){
				favouriteMenuItem = childLayout;
				favouriteMenu = item;
			}
			
			if (item.isExpanded()){
				expendedRootMenuItem.add(item);
			}
			
			for (MenuItem childItem : item.getChildList()){
				childLayout.addComponent(createMenu(childItem, level + 1));
				if (!item.isExpanded()){
					childItem.getComponent().setHeight("0px");
				}
			}
			
			childLayout.setImmediate(true);
			
			menuLayout.addComponent(childLayout);
			
			menuItemLayout.addListener(new LayoutClickListener() {
				
				@Override
				public void layoutClick(LayoutClickEvent event) {
					if (event.getChildComponent() instanceof Label){
						Label b = (Label)event.getChildComponent();
						MenuItem item = (MenuItem)b.getData();
						
						if (item.isExpanded()){
							
							for (final MenuItem child : item.getChildList()){
								proxy.animate(child.getComponent(), AnimType.FADE_OUT).setDuration(duration).setDelay(delay);
								proxy.animate(child.getComponent(), AnimType.ROLL_UP_CLOSE).setDuration(duration).setDelay(delay);
								proxy.addListener(new AnimationListener() {
									
									@Override
									public void onAnimation(AnimationEvent event) {
										child.getComponent().setHeight("0px");
									}
								});
							}
							item.setExpanded(false);
							expendedRootMenuItem.remove(item);
						}else{
							for (final MenuItem child : item.getChildList()){
								child.getComponent().setHeight(null);
								proxy.animate(child.getComponent(), AnimType.ROLL_DOWN_OPEN).setDuration(duration).setDelay(delay);
								proxy.animate(child.getComponent(), AnimType.FADE_IN).setDuration(duration).setDelay(delay);
								
								
								proxy.addListener(new AnimationListener() {
									
									@Override
									public void onAnimation(AnimationEvent event) {
										child.getComponent().setHeight(null);
									}
								});
							}
							item.setExpanded(true);
							expendedRootMenuItem.add(item);
						}
					}
				}
			});
			
			return menuLayout;
		}else{
			CssLayout menuItemLayout = new CssLayout();
			menuItemLayout.setWidth("100%");
			Label label = new Label(item.getMenuName());
			label.setHeight(null);
			label.setData(item);
			
			menuItemLayout.addComponent(label);
			item.setComponent(menuItemLayout);
			if (item.isSelected())
				label.addStyleName("animatedMenuSelected");
			else
				label.addStyleName("animatedMenuLeaf");
			
			if (isFavouritable){
				if (item.isFavouriteItem()){
					label.addStyleName("animatedMenuLeafRemoveFavourite");
				}else{
					label.addStyleName("animatedMenuLeafAddFavourite");
				}
			}
			
			menuItemLayout.addListener(new LayoutClickListener() {
				
				@Override
				public void layoutClick(LayoutClickEvent event) {
					
					if (event.getChildComponent() instanceof Label){
						Label label = (Label)event.getChildComponent();
						
						if (isFavouritable && event.getRelativeX() > 184){
							AbstractLayout layout = (AbstractLayout)event.getSource();
							IMenuApplication application = (IMenuApplication)((Window)window).getApplication();
							if (label.getStyleName().contains("animatedMenuLeafAddFavourite")){
								MenuItem favItem = cloneToFavourite((MenuItem)label.getData());
								AbstractLayout favLayout = createMenu(favItem, 2);
								favItem.setComponent(favLayout);
								boolean success = application.addFavouriteMenuItem(favItem);
								if (success && favouriteMenuItem != null){
									favouriteMenuItem.addComponent(favLayout);
								}
							}else if (label.getStyleName().contains("animatedMenuLeafRemoveFavourite")){
								boolean success = application.removeFavouritemMenuItem((MenuItem)label.getData());
								if (success && layout.getParent() != null)
									((AbstractLayout)layout.getParent()).removeComponent(layout);
							}
						}else{
							synchronized (AnimatedMenu.class) {
								MenuItem item = (MenuItem)label.getData();
								if (isFavouritable){
									((Window)window).executeJavaScript("document.getElementById('IFRAME_iframe').src='"+item.getUrl()+"'");
									
									String breadcrumbString = getBredcrumbString(item, true, new StringBuilder());
									((Window)window).executeJavaScript("updateBreadcrumb('"+breadcrumbString+"')");
									
									
									((Window)window).executeJavaScript("setTimeout('scroll(0,0)',500)");
									
									if (selectMenuItem.size() > 0){
										for (MenuItem i : selectMenuItem){
											for (Iterator<Component> it = ((CssLayout)i.getComponent()).getComponentIterator() ; it.hasNext(); ){
												Component component = it.next();
												if (component instanceof Label){
													((Label)component).removeStyleName("animatedMenuSelected");
													((Label)component).addStyleName("animatedMenuLeaf");
												}
											}
										}
										selectMenuItem.clear();
									}
									
									selectMenuItem.add(item);
									for (Iterator<Component> it = ((CssLayout)item.getComponent()).getComponentIterator() ; it.hasNext(); ){
										Component component = it.next();
										if (component instanceof Label){
											((Label)component).addStyleName("animatedMenuSelected");
											((Label)component).removeStyleName("animatedMenuLeaf");
										}
									}
									//collapse opened second level
									for (Iterator<MenuItem> itm = AnimatedMenu.this.expendedRootMenuItem.iterator() ; itm.hasNext() ;){
										MenuItem currentItem = itm.next();
										MenuItem parent = getRootItem(item, null);
										if (parent != null && !parent.equals(currentItem)){
											for (final MenuItem child : currentItem.getChildList()){
												proxy.animate(child.getComponent(), AnimType.FADE_OUT).setDuration(duration).setDelay(delay);
												proxy.animate(child.getComponent(), AnimType.ROLL_UP_CLOSE).setDuration(duration).setDelay(delay);
												proxy.addListener(new AnimationListener() {
													
													@Override
													public void onAnimation(AnimationEvent event) {
														child.getComponent().setHeight("0px");
													}
												});
											}
											currentItem.setExpanded(false);
										}
									}
								
								}else{
									window.open(new ExternalResource(item.getUrl()));
								}
							}							
						}
					}
				}
			});
			
			return menuItemLayout;
		}
	}

	@Override
	public void setWindow(IMenuWindow window) {
		this.window = window;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	private MenuItem cloneToFavourite(MenuItem item){
		MenuItem favouriteMenuItem = new MenuItem(item.getName(), item.getUrl(), item.getMenuName());
		favouriteMenuItem.setFavouriteItem(true);
		favouriteMenuItem.setExpanded(favouriteMenu.isExpanded());
		favouriteMenuItem.setSelected(false);
		return favouriteMenuItem;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public int getMenuItemHeight() {
		return menuItemHeight;
	}

	public void setMenuItemHeight(int menuItemHeight) {
		this.menuItemHeight = menuItemHeight;
	}

	@Override
	public void setFavouritable(boolean isFavouritable) {
		this.isFavouritable = isFavouritable;
	}
	
	private MenuItem getRootItem(MenuItem item, MenuItem currentItem){
		if (item.getParent() == null){
			return currentItem;
		}else{
			return getRootItem(item.getParent(), item);
		}
	}
	
	private String getBredcrumbString(MenuItem item, boolean current, StringBuilder sbs){
		StringBuilder sb = new StringBuilder();
		String target = "";
		sb.append("<li ");
		if (current){
			sb.append("class=\"last\" ");
			target = " target=\"IFRAME_iframe\"";
		}
		sb.append("><span><a href=\""+item.getUrl()+"\""+target+">"+item.getName()+"</a></span></li>");
		
		if (item.getParent() != null){
			return getBredcrumbString(item.getParent(), false, sb.append(sbs.toString()));
		}else{
			return sb.toString()+sbs.toString();
		}
	}

}
