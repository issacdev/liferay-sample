package com.dbs.portal.ui.layout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.dbs.portal.ui.util.LanguageChanger;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

public class HTMLGridLayout {
	private Logger logger = Logger.getLogger(this.getClass());
	
	//private Panel mainPanel = new Panel();
	private HTMLGrid glayout = new HTMLGrid();
	private CssLayout borderLayout = new CssLayout();
	
	private String xmlGridLayout = "xmlGridLayout";
	private String captionStyle = "xmlGridLayout-caption2";
	private String labelLayoutLeftStyle = "xmlGridLayout-labelLayout-left";
	private String fieldLayoutLeftStyle = "xmlGridLayout-fieldLayout-left";
	private String fieldLayoutLeftNoStyle = "xmlGridLayout-fieldLayout-noStyle-left";
	private String labelLayoutCenterStyle = "xmlGridLayout-labelLayout-middle";
	private String fieldLayoutCenterStyle = "xmlGridLayout-fieldLayout-middle";
	private String fieldLayoutCenterNoStyle = "xmlGridLayout-fieldLayout-noStyle-middle";
	private String labelLayoutRightStyle = "xmlGridLayout-labelLayout-right";
	private String fieldLayoutRightStyle = "xmlGridLayout-fieldLayout-right";
	private String fieldLayoutRightNoStyle = "xmlGridLayout-fieldLayout-noStyle-right";
	
	private String componentLeftStyle = "xmlGridLayout-component-left";
	private String componentCenterStyle = "xmlGridLayout-component-center";
	private String componentRightStyle = "xmlGridLayout-component-right";
	private String componentBoldLeftStyle = "xmlGridLayout-component-bold-left";
	private String componentBoldCenterStyle = "xmlGridLayout-component-bold-center";
	private String componentBoldRightStyle = "xmlGridLayout-component-bold-right";
	
	private boolean noBorder = false;
	
	private LanguageChanger changer = null;
	
	private Label titleLabel = null;
	
	private String caption = null;
	
	public HTMLGridLayout(int columns, int rows) {
		
		borderLayout.setWidth(99, Sizeable.UNITS_PERCENTAGE);
		borderLayout.setHeight(100, Sizeable.UNITS_PERCENTAGE);
		
		glayout.addStyleName(xmlGridLayout);
		glayout.setColumns(columns);
		glayout.setRows(rows);
		
		this.setSize();
	}
	
	public HTMLGridLayout(String caption, int columns, int rows) {
		this(columns, rows);
		addCaption(caption);
	}
	
	public void setLanguageChanger(LanguageChanger changer){
		this.changer = changer;
	}

	private void setSize() {
		glayout.setSizeFull();
		//mainPanel.setSizeFull();
	}
	
	public void setColumns(int columns) {
		glayout.setColumns(columns);
		this.setSize();
	}
	
	public void setRows(int rows) {
		glayout.setRows(rows);
		this.setSize();
	}
	
	private void addCaption(String caption) {
		this.caption = caption;
	}
	
	public void removeAllComponents(){
		glayout.removeAllComponents();
	}
	
	public void removeComponent(int column, int row) {
		glayout.removeComponent(column, row);
	}
	
	// Label
	public void addLabel(Component component, int column, int row) {
		this.addElement(component, column, row, null, null, null, true, false, false);
	}

	public void addLabel(Component component, int column, int row, Alignment alignment) {
		this.addElement(component, column, row, null, null, alignment, true, false, false);
	}
	
	public void addLabel(Component component, int column, int row,  boolean isBold) {
		this.addElement(component, column, row, null, null, null, true, isBold, false);
	}
	
	public void addLabel(Component component, int column, int row,  boolean isBold, boolean hasColon) {
		this.addElement(component, column, row, null, null, null, true, isBold, hasColon);
	}
	
	public void addLabel(Component component, int column, int row, Alignment alignment, boolean isBold) {
		this.addElement(component, column, row, null, null, alignment, true, isBold, false);
	}
	
	public void addLabel(Component component, int column, int row, Alignment alignment, boolean isBold, boolean hasColon) {
		this.addElement(component, column, row, null, null, alignment, true, isBold, hasColon);
	}
	
	public void addLabel(Component component, int column, int row, int columnSpan, int rowSpan) {
		this.addElement(component, column, row, columnSpan, rowSpan, null, true, false, false);
	}

	public void addLabel(Component component, int column, int row, int columnSpan, int rowSpan, Alignment alignment) {
		this.addElement(component, column, row, columnSpan, rowSpan, alignment, true, false, false);
	}
	
	public void addLabel(Component component, int column, int row, int columnSpan, int rowSpan, Alignment alignment, boolean isBold) {
		this.addElement(component, column, row, columnSpan, rowSpan, alignment, true, isBold, false);
	}
	
	public void addLabel(Component component, int column, int row, int columnSpan, int rowSpan, boolean isBold) {
		this.addElement(component, column, row, columnSpan, rowSpan, null, true, isBold, false);
	}
	
	public void addLabel(Component component, int column, int row, int columnSpan, int rowSpan, boolean isBold, boolean hasColon) {
		this.addElement(component, column, row, columnSpan, rowSpan, null, true, isBold, hasColon);
	}
	
	public void addLabel(Component component, int column, int row, int columnSpan, int rowSpan, Alignment alignment, boolean isBold, boolean hasColon) {
		this.addElement(component, column, row, columnSpan, rowSpan, alignment, true, isBold, hasColon);
	}
	
	// Field
	public void addField(Component component, int column, int row) {
		this.addElement(component, column, row, null, null, null, false, false, false);
	}
	
	public void addField(Component component, int column, int row, Alignment alignment) {
		this.addElement(component, column, row, null, null, alignment, false, false, false);
	}
	
	public void addField(Component component, int column, int row, boolean isBold) {
		this.addElement(component, column, row, null, null, null, false, isBold, false);
	}
	
	public void addField(Component component, int column, int row, boolean isBold, boolean noStyle) {
		this.addElement(component, column, row, null, null, null, false, isBold, noStyle);
	}
	
	public void addField(Component component, int column, int row, Alignment alignment, boolean isBold) {
		this.addElement(component, column, row, null, null, alignment, false, isBold, false);
	}
	
	public void addField(Component component, int column, int row, Alignment alignment, boolean isBold, boolean noStyle) {
		this.addElement(component, column, row, null, null, alignment, false, isBold, noStyle);
	}
	
	public void addField(Component component, int column, int row, int columnSpan, int rowSpan) {
		this.addElement(component, column, row, columnSpan, rowSpan, null, false, false, false);
	}

	public void addField(Component component, int column, int row, int columnSpan, int rowSpan, Alignment alignment) {
		this.addElement(component, column, row, columnSpan, rowSpan, alignment, false, false, false);
	}
	
	public void addField(Component component, int column, int row, int columnSpan, int rowSpan, boolean isBold) {
		this.addElement(component, column, row, columnSpan, rowSpan, null, false, isBold, false);
	}
	
	public void addField(Component component, int column, int row, int columnSpan, int rowSpan, Alignment alignment, boolean isBold) {
		this.addElement(component, column, row, columnSpan, rowSpan, alignment, false, isBold, false);
	}
	
	public void addField(Component component, int column, int row, int columnSpan, int rowSpan, Alignment alignment, boolean isBold, boolean hasColon) {
		this.addElement(component, column, row, columnSpan, rowSpan, alignment, false, isBold, hasColon);
	}
	
	private void addElement(Component component, Integer column, Integer row, Integer columnSpan, Integer rowSpan, Alignment alignment, boolean isLabel, boolean isBold, boolean hasColon) {

		List<String> styleList = new ArrayList<String>();
		styleList.add(setLayout(isLabel, alignment));
		setComponentStyle(styleList, component, alignment, isBold, hasColon);
		
		HTMLGridContent content = new HTMLGridContent();
		content.setStyleList(styleList);
		content.setContent(component);
		
		if ((column != null && row != null) && (columnSpan == null && rowSpan == null)) {
			glayout.addComponent(content, column, row);
			
		} else if ((column != null && row != null) && (columnSpan != null && rowSpan != null)) {
			int cspan = columnSpan < 1? 1: columnSpan ;
			int rspan = rowSpan < 1? 1: rowSpan ;
			
			glayout.addComponent(content, column, row,  cspan,  rspan);
		}
	}
	
	private String setLayout(boolean isLabel, Alignment alignment) {
		String style = null;
		if (isLabel) {
			if (Alignment.TOP_CENTER.equals(alignment) || Alignment.MIDDLE_CENTER.equals(alignment) || Alignment.BOTTOM_CENTER.equals(alignment)) {
				style = labelLayoutCenterStyle;
			} else if (Alignment.TOP_RIGHT.equals(alignment) || Alignment.MIDDLE_RIGHT.equals(alignment) || Alignment.BOTTOM_RIGHT.equals(alignment)) {
				style = labelLayoutRightStyle;
			} else {
				style = labelLayoutLeftStyle;
			}
		} else {
			if (noBorder) {
				if (Alignment.TOP_CENTER.equals(alignment) || Alignment.MIDDLE_CENTER.equals(alignment) || Alignment.BOTTOM_CENTER.equals(alignment)) {
					style = fieldLayoutCenterNoStyle;
				} else if (Alignment.TOP_RIGHT.equals(alignment) || Alignment.MIDDLE_RIGHT.equals(alignment) || Alignment.BOTTOM_RIGHT.equals(alignment)) {
					style = fieldLayoutRightNoStyle;
				} else {
					style = fieldLayoutLeftNoStyle;
				}
			} else {
				if (Alignment.TOP_CENTER.equals(alignment) || Alignment.MIDDLE_CENTER.equals(alignment) || Alignment.BOTTOM_CENTER.equals(alignment)) {
					style = fieldLayoutCenterStyle;
				} else if (Alignment.TOP_RIGHT.equals(alignment) || Alignment.MIDDLE_RIGHT.equals(alignment) || Alignment.BOTTOM_RIGHT.equals(alignment)) {
					style = fieldLayoutRightStyle;
				} else {
					style = fieldLayoutLeftStyle;
				}
			}
		}
		
		return style;
	}
	
	private void setComponentStyle(List<String> styleList, Component component, Alignment alignment, boolean isBold, boolean hasColon) {
		if (alignment == null) {
			alignment = Alignment.MIDDLE_LEFT;
		}
		
		if (component instanceof Label) {
			((Label)component).setWidth(null);
			
			changer.changeCode((Label)component);
			
			Label label = (Label) component;
			if (hasColon){
				label.setValue(label.getValue().toString() + " :");
			}
			
			if (Alignment.TOP_CENTER.equals(alignment) || Alignment.MIDDLE_CENTER.equals(alignment) || Alignment.BOTTOM_CENTER.equals(alignment)) {
				if (isBold) {
					styleList.add(componentBoldCenterStyle);
				} else {
					styleList.add(componentCenterStyle);
				}	
			} else if (Alignment.TOP_RIGHT.equals(alignment) || Alignment.MIDDLE_RIGHT.equals(alignment) || Alignment.BOTTOM_RIGHT.equals(alignment)) {
				if (isBold) {
					styleList.add(componentBoldRightStyle);
				} else {
					styleList.add(componentRightStyle);
				}
			} else {
				if (isBold) {
					styleList.add(componentBoldLeftStyle);
				} else {
					styleList.add(componentLeftStyle);
				}
			}
		}
		
	}
	
	public void replaceComponent(int column, int row, Component newComponent) {
		HTMLGridContent content = glayout.getComponent(column, row);
		AbstractLayout layout = (AbstractLayout)newComponent;
		for (Iterator<Component> it = layout.getComponentIterator() ; it.hasNext(); ){
			Component component = it.next();
			content.setContent(component);
		}
		glayout.replaceComponent(column, row, content);
	}
	
	public void addComponentStyle(int column, int row, List list) {
		HTMLGridContent vlayout = glayout.getComponent(column, row);
		List<String> styleList = vlayout.getStyleList();
		for (Object style: list) {
			styleList.add((String)style);
		}
	}

	public AbstractLayout getMainLayout() {
		if (caption != null)
			glayout.setCaption(changer.getMessages().getString(this.caption));
		
		borderLayout.removeAllComponents();
		borderLayout.addStyleName("HTMLGridLayout");
		borderLayout.addComponent(glayout.getLayout());
		
		return borderLayout;
	}
	
	public void repaint(){
//		glayout.requestRepaintAll();
	}

	public boolean isNoBorder() {
		return noBorder;
	}

	public void setNoBorder(boolean noBorder) {
		this.noBorder = noBorder;
	}
}
