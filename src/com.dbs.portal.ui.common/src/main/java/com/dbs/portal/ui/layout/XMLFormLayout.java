package com.dbs.portal.ui.layout;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import com.dbs.portal.ui.util.LanguageChanger;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.ProgressIndicator;
import com.vaadin.ui.Upload;
import com.vaadin.ui.VerticalLayout;

public class XMLFormLayout extends CssLayout{
	
	private int labelWidth = 100;
	
	private int fieldWidth = 200;
	
	private Vector<AbstractField> componentList = new Vector<AbstractField>();
	
	private Map<AbstractComponent, Label> errorLabelMapping = new HashMap<AbstractComponent, Label>();
	
	private Map<AbstractComponent, AbstractLayout> componentLayoutMapping = new HashMap<AbstractComponent, AbstractLayout>();
	
	private Map<AbstractComponent, Object> originalValueMapping = new HashMap<AbstractComponent, Object>();
	
	private Map<AbstractComponent, String> originalLabelKey = new HashMap<AbstractComponent, String>();
	
	private Map<AbstractComponent, ProgressIndicator> uploadProcessMap = new HashMap<AbstractComponent, ProgressIndicator>();
	
	private boolean extraErrorLabel = true;
	
	private boolean requiredLabel = false;
	
	private boolean labelRequired = true;
	
	private int paddingLength = 50;
	private boolean paddingRequired = true;
	
	private LanguageChanger changer = null;
	
	public XMLFormLayout(int labelWidth){
		super();
		this.labelWidth = labelWidth;
	}
	
	public XMLFormLayout(int labelWidth, int fieldWidth){
		super();
		this.labelWidth = labelWidth;
		this.fieldWidth = fieldWidth;
	}
	
	public void setLanguageChanger(LanguageChanger changer){
		this.changer = changer;
	}
	
	public void addField(AbstractComponent component){
		if (component != null){

			if (!(component instanceof CheckBox) && labelRequired){
				Label captionLabel = new Label(component.getCaption());
				captionLabel.setWidth(labelWidth +"px");
				component.setCaption(null);
				
				component.setWidth(fieldWidth+"px");
				HorizontalLayout layout = new HorizontalLayout();
				layout.setMargin(false, requiredLabel, false, requiredLabel);
				layout.setSpacing(requiredLabel);
				layout.addStyleName("xmlformspacing");
				layout.addComponent(captionLabel);
				if (paddingRequired)
					layout.setWidth((labelWidth + fieldWidth + paddingLength)+"px");
				else
					layout.setWidth((labelWidth + fieldWidth)+"px");
				
				
				if (component instanceof AbstractField){
					AbstractField field = (AbstractField)component;
					VerticalLayout contentLayout = new VerticalLayout();
					Label label = new Label();
					label.addStyleName("validation-error-label");
					contentLayout.addComponent(component);
					contentLayout.addComponent(label);
					
					errorLabelMapping.put(field, label);
					originalValueMapping.put(field, field.getValue());
					componentList.add(field);
					
					component.setWidth(fieldWidth+"px");
					
					layout.addComponent(contentLayout);
					changer.changeCode(captionLabel);
					changer.changeCode(component);
				}else{
					layout.addComponent(component);
				}
				this.addComponent(layout);
			}else{
				changer.changeCode(component);
				this.addComponent(component);
			}
		}
	}
	
	public void addField(Label label, Label component){
		if (component != null){
			label.setWidth(labelWidth +"px");
			
			HorizontalLayout layout = new HorizontalLayout();
			layout.addStyleName("xmlformspacing");
			layout.setSpacing(requiredLabel);
			layout.setMargin(false, requiredLabel, false, requiredLabel);
			layout.addComponent(label);
			layout.addComponent(component);
			component.setWidth(fieldWidth+"px");
			
			changer.changeCode(label);
			changer.changeCode(component);
			this.addComponent(layout);
			
		}
	}
	
	public void addField(Label[] label, int[] size){
		if (label != null){
			
			HorizontalLayout layout = new HorizontalLayout();
			layout.addStyleName("xmlformspacing");
			layout.setSpacing(requiredLabel);
			layout.setMargin(false, requiredLabel, false, requiredLabel);

			for (int i = 0 ; i < label.length ; i++){
				layout.addComponent(label[i]);
				label[i].setWidth(size[i]+"px");
				changer.changeCode(label[i]);
			}
			
			this.addComponent(layout);
			
		}
	}
	
	public void addField(Label label, Label[] components){
		if (components != null){
			label.setWidth(labelWidth +"px");
			
			HorizontalLayout layout = new HorizontalLayout();
			layout.setSpacing(requiredLabel);
			layout.addStyleName("xmlformspacing");
			layout.setMargin(false, requiredLabel, false, requiredLabel);
			layout.addComponent(label);
			for (int i = 0 ; i < components.length ; i++){
				components[i].setCaption(null);
				layout.addComponent(components[i]);
				changer.changeCode(components[i]);
				components[i].setWidth(fieldWidth+"px");
			}
			this.addComponent(layout);
			changer.changeCode(label);
			
		}
	}
	
	public void addField(AbstractComponent[] components){
		if (components != null){
			int[] size = new int[components.length];
			for (int i = 0 ; i < components.length ; i++){
				size[i] = fieldWidth;
			}
			addField(components, size);
		}
	}
	
	public void addField(AbstractComponent[] components, int[] size){
		boolean[] required = new boolean[components.length];
		for (boolean require : required){
			require = true;
		}
		
		addField(components, size, required);
	}
	
	public void addField(AbstractComponent[] components, int[] size, boolean[] flexLayout){
		int[] labelWidth = new int[components.length];
		for (int width : labelWidth)
			width = this.labelWidth;
		
		addField(components, size, labelWidth, flexLayout);
	}
	
	public void addField(AbstractComponent[] components, int[] size, int[] labelWidth, boolean[] flexLayout){
		if (components != null && components.length > 0){
			
			CssLayout layout = new CssLayout();
			layout.setWidth(null);
			layout.setHeight(null);
			if (requiredLabel){
				layout.addStyleName("xmlformSpacingRequired");
			}
			layout.addStyleName("xmlformspacing");

			for (int i = 0 ; i < components.length ; i++){
				if (components[i] instanceof Label){
					components[i].setWidth(size[i]+"px");
					layout.addComponent(components[i]);
					changer.changeCode(components[i]);
				}else if (components[i] instanceof OptionGroup && !flexLayout[i]){
					components[i].setWidth(labelWidth[i]+"px");
					originalLabelKey.put(components[i], components[i].getCaption());
					components[i].setCaption(null);
					layout.addComponent(components[i]);
					changer.changeCode(components[i]);
				}else if (!(components[i] instanceof CheckBox) && flexLayout[i]){
					
					if (i == 0 || (requiredLabel && i >0 )){
						Label captionLabel = new Label(components[i].getCaption(), Label.CONTENT_PREFORMATTED);
						captionLabel.addStyleName("label-wrapping");
						captionLabel.setWidth(labelWidth[i]+"px");
//						captionLabel.setWidth(labelWidth +"px");
						originalLabelKey.put(components[i], components[i].getCaption());
						layout.addComponent(captionLabel);
						changer.changeCode(captionLabel);
					}
					
					components[i].setCaption(null);
					if (size.length >= components.length)
						components[i].setWidth(size[i]+"px");
					
					if (components[i] instanceof AbstractField || components[i] instanceof Upload){
						CssLayout contentLayout = new CssLayout();
//						Label label = new Label();
//						label.addStyleName("validation-error-label");
//						label.setHeight("0px");
						
						components[i].addStyleName("xmlForm-fieldStyle");
						
						contentLayout.addComponent(components[i]);
						componentLayoutMapping.put(components[i], contentLayout);
//						contentLayout.addComponent(label);

						if (components[i] instanceof Upload){
							components[i].addStyleName("xmlUpload");
							ProgressIndicator indicator = new ProgressIndicator();
							indicator.setWidth("100%");
							uploadProcessMap.put(components[i], indicator);
							contentLayout.addComponent(indicator);
							if ("EN".equals(changer.getMessages().getString("common.locale"))){
								((Upload)components[i]).setLocale(Locale.US);
							}else{
								((Upload)components[i]).setLocale(Locale.CHINA);
							}
						}else{
							if (!(components[i] instanceof Upload))
								originalValueMapping.put((AbstractField)components[i], ((AbstractField) components[i]).getValue());
							componentList.add((AbstractField)components[i]);
						}
						
//						errorLabelMapping.put(components[i], label);
						
						layout.addComponent(contentLayout);
						changer.changeCode(components[i]);
						
						if (size.length >= components.length){
							components[i].setWidth(size[i]+"px");
							if (paddingRequired || i + 1 == components.length)
								contentLayout.setWidth((size[i] + paddingLength)+"px");
							else
								contentLayout.setWidth((size[i])+"px");
						}else{
							if (paddingRequired  || i + 1 == components.length)
								contentLayout.setWidth((fieldWidth + paddingLength)+"px");
							else
								contentLayout.setWidth((fieldWidth)+"px");
						}
						
					}else{
						layout.addComponent(components[i]);
						originalValueMapping.put((AbstractField)components[i], ((AbstractField)components[i]).getValue());
						componentList.add((AbstractField)components[i]);
					}
				}else{
					if (!flexLayout[i]){
						CssLayout checkBoxLayout = new CssLayout();
						checkBoxLayout.addComponent(components[i]);
						checkBoxLayout.setWidth(size[i] + paddingLength + "px");		
						layout.addComponent(checkBoxLayout);
					}else{
						layout.addComponent(components[i]);	
					}
					
					originalValueMapping.put((AbstractField)components[i], ((AbstractField)components[i]).getValue());
					componentList.add((AbstractField)components[i]);
					changer.changeCode(components[i]);
				}
			}
			
			this.addComponent(layout);
		}
	}
	
	public boolean validate(){
		boolean isValid = true;
		resetErrorLabel();
		for (AbstractField field : componentList){
			try{
				isValid = isValid && field.isValid();
				field.validate();
				Label label = getErrorLabel(field);
				label.setHeight("0px");
			}catch(InvalidValueException e){
				if (extraErrorLabel){
					Label label = getErrorLabel(field);
					label.setValue(e.getMessage());
					label.setHeight(null);
				}else{
					field.addStyleName("validation-error");
				}
			}
		}
		
		return isValid;
	}
	
	public boolean validateSubscription(){
		boolean isValid = true;
		resetErrorLabel();
		for (AbstractField field : componentList){
			try{
				if (!(field instanceof DateField)){
					isValid = isValid && field.isValid();
					field.validate();
				}
			}catch(InvalidValueException e){
				if (extraErrorLabel){
					Label label = getErrorLabel(field);
					label.setValue(e.getMessage());
					label.setHeight(null);
				}else{
					field.addStyleName("validation-error");
				}
			}
		}
		
		return isValid;
	}
	
	public void reset(){
		resetErrorLabel();
		for (AbstractField field : componentList){
			try{
				if (!(Boolean)field.getData()){
					Object value = originalValueMapping.get(field);
					if (value != null)
						if (value instanceof String){ 
							if (!"".equals(value))
								field.setValue(value);
							else
								field.setValue(null);
						}else{
							field.setValue(value);	
						}
					else{
						field.setValue(null);
					}
				}
			}catch(InvalidValueException e){
				Label label = getErrorLabel(field);
				label.setValue(e.getMessage());
			}
		}
	}
	
	private void resetErrorLabel(){
		for (AbstractField field : componentList){
			Label label = getErrorLabel(field);
			if (label != null){
				label.setValue(null);
				label.setHeight("0px");
				field.removeStyleName("validation-error");
			}
		}
	}
	
	public void needExtraErrorLabel(boolean extraErrorLabel){
		this.extraErrorLabel = extraErrorLabel;
	}
	
	public void setRequiredLabel(boolean required){
		this.requiredLabel = required;
	}
	
	public void setPaddingRequired(boolean paddingRequired){
		this.paddingRequired = paddingRequired;
	}
	
	public void setLabelRequired(boolean labelRequired){
		this.labelRequired = labelRequired;
	}
	
	public Map<AbstractComponent, String> getLabelMap(){
		return this.originalLabelKey;
	}
	
	public ProgressIndicator getProgressIndicator(AbstractComponent component){
		return uploadProcessMap.get(component);
	}
	
	public Label getErrorLabel(AbstractComponent component){
		Label label = errorLabelMapping.get(component);
		if (label == null){
			label = new Label();
			label.addStyleName("validation-error-label");
			label.setHeight("0px");
			AbstractLayout layout = componentLayoutMapping.get(component);
			if (layout != null){
				componentLayoutMapping.get(component).addComponent(label);
				errorLabelMapping.put(component, label);
			}
		}
		return label;
	}
}
