package com.dbs.portal.ui.component.view;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.dbs.portal.database.constants.CommonConstant;
import com.dbs.portal.ui.component.button.NormalButton;
import com.dbs.portal.ui.component.data.IDataProvider;
import com.dbs.portal.ui.component.type.DateFieldDefaultParameter;
import com.dbs.portal.ui.util.ValidatorFactory;
import com.dbs.portal.ui.validator.ValidationType;
import com.vaadin.data.Container.ItemSetChangeEvent;
import com.vaadin.data.Container.ItemSetChangeListener;
import com.vaadin.data.Item.PropertySetChangeListener;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.AbstractTextField.TextChangeEventMode;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.NumericField;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.Upload;

public class FormField implements ItemSetChangeListener, PropertySetChangeListener {

	private Logger logger = Logger.getLogger(this.getClass());

	private FormFieldType fieldType;
	private boolean visible = false;
	private boolean readOnly = false;
	private String fieldNameKey;
	private String dataId;
	private IDataProvider dataProvider;

	private AbstractComponent component;

	private String requiredMessageKey = null;

	private int columnSpan = 1;

	private String groupId = null;

	private boolean enabled = true;

	private boolean paddingRequired = true;

	private int defaultSelect = -1;

	private boolean toUpperCase = false;

	private boolean dataAsTextKey = false;

	private boolean flexLayout = true;

	private int labelWidth = -1;

	private boolean staticField = false;

	private List styleNames = null;

	private int labelMode = Label.CONTENT_DEFAULT;

	private int selectionRows = 6;

	public FormField(FormFieldType fieldType, String fieldNameKey, String dataId) {
		this.fieldType = fieldType;
		this.fieldNameKey = fieldNameKey;
		this.dataId = dataId;
		switch (fieldType) {
		case LABEL:
			component = new Label(fieldNameKey);
			break;
		case TEXTFIELD:
			component = new TextField(fieldNameKey);
//			component.addStyleName("upper-case");
			((TextField) component).setNullRepresentation("");
			break;
		case COMBOBOX:
			component = new ComboBox(fieldNameKey);
			break;
		case CHECK_BOX:
			component = new CheckBox(fieldNameKey);
			break;
		case RADIO_BUTTON:
			component = new OptionGroup(fieldNameKey);
			break;
		case RADIO_BUTTON_GROUP:
			component = new OptionGroup(fieldNameKey);
			component.addStyleName("horizontal");
			break;
		case PASSWORDFIELD:
			component = new PasswordField();
			break;
		case DATETIME:
			// component = new MaskPopupDateField(fieldNameKey);
			// component.setImmediate(true);
			// ((MaskPopupDateField)component).setMask("####-##-## ##:##");
			// ((MaskPopupDateField)component).setDateFormat("yyyy-MM-dd HH:mm");
			// ((MaskPopupDateField)component).setResolution(DateField.RESOLUTION_MIN);

			component = new PopupDateField(fieldNameKey);
			component.setImmediate(true);
			((PopupDateField) component).setDateFormat(CommonConstant.DATE_FORMAT);
			((PopupDateField) component).setResolution(DateField.RESOLUTION_DAY);
			break;
		case TEXTAREA:
			component = new TextArea(fieldNameKey);
			((TextArea) component).setValue(dataId);
			((TextArea) component).setNullRepresentation("");
			component.setWidth("350px");
			break;
		case LIST_SELECT:
			component = new ListSelect(fieldNameKey);
			((ListSelect) component).setRows(selectionRows);
			((ListSelect) component).setMultiSelect(true);
			break;
		case UPLOAD:
			component = new Upload(fieldNameKey, null);
			break;
		case TWINS_COL:
			component = new TwinColSelect(fieldNameKey);
			((TwinColSelect) component).setRows(selectionRows);
			((TwinColSelect) component).setImmediate(true);
			break;
		case BUTTON:
			component = new NormalButton(fieldNameKey);
			break;
		case MASKED_TEXTFIELD:
			component = new TextField(fieldNameKey);
			((TextField) component).setNullRepresentation("");
			break;
		case NUMERICFIELD:
			component = new NumericField(fieldNameKey);
			((NumericField) component).setNullRepresentation("");
			break;
		}
	}

	public FormFieldType getFieldType() {
		return fieldType;
	}

	public AbstractComponent getComponent() {
		component.setData(staticField);
		return component;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.component.setVisible(visible);
		this.visible = visible;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.component.setReadOnly(readOnly);
		this.readOnly = readOnly;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public IDataProvider getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(IDataProvider dataProvider) {
		this.dataProvider = dataProvider;
		switch (fieldType) {
		case COMBOBOX:
		case LIST_SELECT:
		case TWINS_COL:
			((AbstractSelect) component).setImmediate(true);
			((AbstractSelect) component).setContainerDataSource(dataProvider.getDataContainer());

			if (this.defaultSelect >= 0) {
				Object value = null;
				try {
					int x = 0;
					for (Iterator it = ((AbstractSelect) component).getItemIds().iterator(); x < this.defaultSelect; x++) {
						value = it.next();
					}
				} catch (Exception e) {
					value = null;
				}

				((AbstractSelect) component).setValue(value);
			}

			break;
		case RADIO_BUTTON_GROUP:
			((AbstractSelect) component).setContainerDataSource(dataProvider.getDataContainer());
			AbstractSelect sb = ((AbstractSelect) component);
			try {
				sb.setValue(sb.getItemIds().iterator().next());
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			break;
		case RADIO_BUTTON:
			((AbstractSelect) component).setContainerDataSource(dataProvider.getDataContainer());
			break;
		}
	}

	public void setRequired(String requiredMessageKey) {
		switch (fieldType) {
		case LABEL:
			break;
		default:
			((AbstractField) component).setRequired(true);
			((AbstractField) component).setRequiredError(requiredMessageKey);
			break;
		}
	}

	public void setValidationList(List validations) {
		this.setValidation(validations);
	}

	public void setValidationLists(List<List> validations) {
		for (List l : validations) {
			setValidation(l);
		}
	}

	public void setValidationObjList(List validations) {
		for (int i = 0; i < validations.size(); i++) {
			this.setValidationObj((AbstractValidator) validations.get(i));
		}
	}

	public void setValidation(List validations) {
		if (validations != null && validations.size() > 0 && component instanceof AbstractField) {
			AbstractField field = (AbstractField) component;
			ValidationType validationType = (ValidationType) validations.get(0);
			switch (validationType) {
			case StringLength:
				String s = (String) validations.get(1);
				int i1 = (Integer) validations.get(2);
				int i2 = (Integer) validations.get(3);
				boolean b = (Boolean) validations.get(4);
				field.addValidator(ValidatorFactory.getInstance().createStringLengthValidator(s,
						i1, i2, b));
				break;
			case Email:
				field.addValidator(ValidatorFactory.getInstance().createEmailValidator((String) validations.get(1)));
				break;
			case Integer:
				field.addValidator(ValidatorFactory.getInstance().createIntegerValidator((String) validations.get(1)));
				break;
			case EitherOne:
				field.addValidator(ValidatorFactory.getInstance().createEitherOneValidtor((String) validations.get(1),
						(AbstractField[]) validations.get(2)));
				break;
			case Boolean:
				field.addValidator(ValidatorFactory.getInstance().createBooleanValidator((String) validations.get(1),
						(Boolean) validations.get(2), (String) validations.get(3), (String) validations.get(4)));
				break;
			}
		}
	}

	public void setValidationObj(AbstractValidator validator) {
		this.setValidation(validator);
	}

	public void setValidation(AbstractValidator validator) {
		AbstractField field = (AbstractField) component;
		field.addValidator(validator);
	}

	public void setColumnSpan(int columnSpan) {
		this.columnSpan = columnSpan;
	}

	public int getColumnSpan() {
		return this.columnSpan;
	}

	public void setDefault(Object value) {
		switch (fieldType) {
		case DATETIME:
			Calendar c = GregorianCalendar.getInstance();
			PopupDateField dateField = (PopupDateField) component;
			if (value instanceof String) {
				if (!"".equals((String) value)) {
					c.add(Calendar.DATE, Integer.parseInt((String) value));
					dateField.setValue(c.getTime());
				} else {
					dateField.setValue(null);
				}
			} else if (value instanceof DateFieldDefaultParameter) {
				DateFieldDefaultParameter parameter = (DateFieldDefaultParameter) value;
				if (dateField.getValue() == null)
					dateField.setValue(c.getTime());

				c.setTime((Date) dateField.getValue());
				if (!parameter.isAdjustment())
					c.set(parameter.getDefaultType(), parameter.getValue());
				else
					c.add(parameter.getDefaultType(), parameter.getValue());

				dateField.setValue(c.getTime());
			}
			break;
		case LABEL:
			break;
		case CHECK_BOX:
			AbstractField select = (AbstractField) component;
			select.setValue(Boolean.valueOf((String) value));
			break;
		case RADIO_BUTTON:
			AbstractSelect sb = ((AbstractSelect) component);
			try {
				sb.setValue(sb.getItemIds().iterator().next());
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			break;
		case TWINS_COL:
			((TwinColSelect) component).setValue(Collections.unmodifiableList((List<String>) value));
			break;
		default:
			AbstractField field = (AbstractField) component;
			field.setValue(value);
			break;
		}
	}

	public void setDefaultList(List<Object> valueList) {
		for (Object obj : valueList) {
			setDefault(obj);
		}
	}

	@Override
	public void containerItemSetChange(ItemSetChangeEvent event) {

	}

	@Override
	public void itemPropertySetChange(com.vaadin.data.Item.PropertySetChangeEvent event) {

	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public void setEnabled(boolean isEnabled) {
		if (component instanceof AbstractField) {
			((AbstractField) component).setEnabled(isEnabled);
		}
	}

	public String getFieldNameKey() {
		return fieldNameKey;
	}

	public void setPaddingRequired(boolean paddingRequired) {
		this.paddingRequired = paddingRequired;
	}

	public boolean getPaddingRequired() {
		return this.paddingRequired;
	}

	public void setDefaultSelect(int defaultSelect) {
		this.defaultSelect = defaultSelect;
	}

	public int getDefaultSelect() {
		return this.defaultSelect;
	}

	public void setListenerList(List<Object> listener) {
		if (!(component instanceof Label)) {
			component.setImmediate(true);
			for (Object obj : listener) {
				if (obj instanceof ValueChangeListener) {
					ValueChangeListener lis = (ValueChangeListener) obj;
					if (component instanceof AbstractSelect) {
						((AbstractSelect) component).addListener(lis);
					} else {
						((AbstractField) component).addListener(lis);
					}
				} else if (obj instanceof TextChangeListener && this.fieldType == FormFieldType.TEXTFIELD) {
					((TextField) component).addListener((TextChangeListener) obj);
					((TextField) component).setTextChangeEventMode(TextChangeEventMode.EAGER);
				} else if (obj instanceof ClickListener && this.fieldType == FormFieldType.BUTTON) {
					((NormalButton) component).addListener((ClickListener) obj);
				}
			}

		}
	}

	public void setMaxLength(String length) {
		if (component instanceof TextField && Integer.parseInt(length) > 0) {
			((TextField) component).setMaxLength(Integer.parseInt(length));
		}
	}

	public void setUpperCase(boolean toUpperCase) {
		this.toUpperCase = toUpperCase;
		switch (fieldType) {
		case TEXTFIELD:
//			if (!toUpperCase)
//				component.removeStyleName("upper-case");
			if (toUpperCase)
				component.addStyleName("upper-case");
			break;
		}
	}

	public void setAllowEmptyItem(boolean allowEmptyItem) {
		switch (fieldType) {
		case COMBOBOX:
			((ComboBox) component).setNullSelectionAllowed(allowEmptyItem);
			break;
		case LIST_SELECT:
			((ListSelect) component).setNullSelectionAllowed(allowEmptyItem);
			break;
		}
	}

	public void setAllowNewItem(boolean allowNewItem) {
		switch (fieldType) {
		case TWINS_COL:
			((TwinColSelect) component).setNewItemsAllowed(allowNewItem);
			break;
		}
	}

	public void setDataAsTextKey(boolean dataAsTextKey) {
		this.dataAsTextKey = dataAsTextKey;
		if (dataAsTextKey && component instanceof AbstractField) {
			((AbstractField) component).setValue(dataId);
		} else {
			((AbstractField) component).setValue("");
		}
	}

	public void setSelectionRows(int rows) {
		selectionRows = rows;
		switch (fieldType) {
		case LIST_SELECT:
			((ListSelect) component).setRows(rows);
			break;
		case TWINS_COL:
			((TwinColSelect) component).setRows(rows);
			break;
		}
	}

	public int getSelectionRows() {
		return this.selectionRows;
	}

	public boolean isDataAsTextKey(boolean dataAsTextKey) {
		return this.dataAsTextKey;
	}

	public void setFlexLayout(boolean flexLayout) {
		this.flexLayout = flexLayout;
	}

	public boolean isFlexLayout() {
		return this.flexLayout;
	}

	public void setLabelWidth(int labelWidth) {
		this.labelWidth = labelWidth;
	}

	public int getLabelWidth() {
		return this.labelWidth;
	}

	public void setImmediate(boolean immediate) {
		component.setImmediate(immediate);
	}

	public boolean getImmediate() {
		return component.isImmediate();
	}

	public void setStaticField(boolean staticField) {
		this.staticField = staticField;
	}

	public boolean getStaticField() {
		return this.staticField;
	}

	public void setStyleList(List styleNames) {
		this.styleNames = styleNames;

		for (Object obj : styleNames) {
			component.addStyleName((String) obj);
		}
	}

	public List getStyleList() {
		return this.styleNames;
	}

	public void setLabelMode(int labelMode) {
		if (component instanceof Label) {
			this.labelMode = labelMode;
			((Label) component).setContentMode(labelMode);
		}
	}

	public int getLabelMode() {
		return labelMode;
	}
	
	public void setMask(String mask){
		if (component instanceof TextField){
			((TextField)component).setPropertyDataSource(new MaskedFormatProperty(mask));
			((TextField)component).setTextChangeTimeout(1);
			((TextField)component).setImmediate(true);
			((TextField)component).addListener(new ValueChangeListener(){

				@Override
				public void valueChange(ValueChangeEvent event) {
					((TextField)component).getValue();
				}
			
			});
		}
	}

}
