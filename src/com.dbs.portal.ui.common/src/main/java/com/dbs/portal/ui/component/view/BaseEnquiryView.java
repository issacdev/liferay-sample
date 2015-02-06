package com.dbs.portal.ui.component.view;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.vaadin.jouni.animator.AnimatorProxy;
import org.vaadin.jouni.animator.AnimatorProxy.AnimationEvent;
import org.vaadin.jouni.animator.AnimatorProxy.AnimationListener;
import org.vaadin.jouni.animator.client.ui.VAnimatorProxy.AnimType;

import com.dbs.portal.database.constants.CommonConstant;
//import com.dbs.portal.database.constants.ContainerConstant;
import com.dbs.portal.database.constants.UtilizationStatisticsConstant;
import com.dbs.portal.database.to.common.GlobalPortletDataTransfer;
import com.dbs.portal.database.to.common.YesNoType;
import com.dbs.portal.ui.component.application.IApplication;
import com.dbs.portal.ui.component.button.ExtraButton;
import com.dbs.portal.ui.component.button.NormalButton;
import com.dbs.portal.ui.component.button.ProcessButton;
import com.dbs.portal.ui.component.button.StopButton;
import com.dbs.portal.ui.component.comboBox.ComboBoxItem;
import com.dbs.portal.ui.component.type.LayoutPermissionType;
import com.dbs.portal.ui.component.upload.FilePart;
import com.dbs.portal.ui.component.upload.UploadFailListener;
import com.dbs.portal.ui.component.upload.UploadFinishListener;
import com.dbs.portal.ui.component.upload.UploadProgressListener;
import com.dbs.portal.ui.component.upload.UploadReceiverListener;
import com.dbs.portal.ui.component.upload.UploadSuccessListener;
import com.dbs.portal.ui.layout.XMLFormLayout;
import com.dbs.portal.ui.util.LanguageChanger;
import com.dbs.portal.ui.util.Messages;
import com.vaadin.Application;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.terminal.gwt.server.PortletApplicationContext2;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.ProgressIndicator;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Window;

public class BaseEnquiryView extends CssLayout implements IEnquiryView {

	private Logger logger = Logger.getLogger(this.getClass());

	private XMLFormLayout formLayout;

	private String processButtonName = "common.button.process";
	private String stopButtonName = "common.button.stop";
	private String exportButtonName = "common.button.export";
	private String subscribeButtonName = "common.button.subscribe";
	private String saveTablePreferenceName = "com.button.saveTablePreference";

	private ArrayList<ClickListener> processButtonClickList = new ArrayList<ClickListener>();
	private ArrayList<ClickListener> stopButtonClickList = new ArrayList<ClickListener>();
	private ArrayList<ClickListener> exportButtonClickList = new ArrayList<ClickListener>();
	private ArrayList<ClickListener> subscribeButtonClickList = new ArrayList<ClickListener>();
	private ArrayList<ClickListener> saveTablePreferenceClickList = new ArrayList<ClickListener>();

	private ProcessButton processButton = null;
	private StopButton stopButton = null;
	private NormalButton exportButton = null;
	private NormalButton subscribeButton = null;
	private NormalButton saveTablePerferenceButton = null;

	private boolean exportButtonVisible = false;
	private boolean subscribeButtonVisible = false;
	private boolean resetButtonVisible = true;
	private boolean processButtonVisible = true;
	private boolean saveTablePerferenceButtonVisible = false;

	private boolean processButtonEnable = true;

	private List<FormField> fieldList;

	private Map<String, AbstractComponent> fieldNameMapping = new LinkedHashMap<String, AbstractComponent>();
	private Map<String, AbstractComponent> componentNameMapping = new LinkedHashMap<String, AbstractComponent>();
	private Map<String, FormField> fieldFormMapping = new LinkedHashMap<String, FormField>();

	private int labelWidth = 100;
	private int fieldWidth = 250;

	private SimpleDateFormat dateFormat = new SimpleDateFormat(CommonConstant.DB_DATE_FORMAT);
	private SimpleDateFormat displayDateFormat = new SimpleDateFormat(CommonConstant.DISPLAY_DATE_FORMAT);

	private List<String> requiredOneIncludeList = new ArrayList<String>();
	private List<String> requiredOneExcludeList = new ArrayList<String>();
	// To validate if all the fields in the list have been input
	private List<String> requiredAllList = new ArrayList<String>();
	private Label requiredOneMessageLabel = null;
	private Label missingFieldNameLabel = new Label();
	private HorizontalLayout requiredOneLayout = new HorizontalLayout();
	private String requiredOneMessageKey = null;
	private String requiredOneSubscriptionMessageKey = null;

	private Map<Upload, Upload.FailedListener> uploadFailedMap = new HashMap<Upload, Upload.FailedListener>();
	private Map<Upload, Upload.Receiver> uploadReceiverMap = new HashMap<Upload, Upload.Receiver>();
	
	private Map<Upload, UploadProgressListener> uploadProgressMap = new HashMap<Upload, UploadProgressListener>();
	private Map<Upload, UploadSuccessListener> uploadSuccessMap = new HashMap<Upload, UploadSuccessListener>();

	private Object fieldObject = null;
	private Object additionalFieldObject = null;

	private boolean supportPageReset = true;

	private Map<String, Object> lastCriteria = null;

	private AnimatorProxy proxy = new AnimatorProxy();
	private boolean expandable = true;
	private CssLayout outlineLayout = new CssLayout();
	private int duration = 1200;
	private int delay = 1000;

	private CssLayout keyValueLayout = new CssLayout();

	private int height = 0;

	private long maxFileSize = 1024 * 1024 * 10;

	private boolean isSubscription = false;

	private List<ExtraButton> extraButtonList;

	private Button exportButtonTiny = null;

	private boolean timeFixedForSubscription = false;

	private boolean autoCollapse = false;
	
	private boolean canCollapse = true;
	
	private boolean styleRequire = true;

	public BaseEnquiryView() {
		super();
		formLayout = new XMLFormLayout(labelWidth, fieldWidth);
		initResetViewListener();
	}

	public BaseEnquiryView(int labelWidth, int fieldWidth) {
		super();
		this.labelWidth = labelWidth;
		this.fieldWidth = fieldWidth;
		formLayout = new XMLFormLayout(labelWidth, fieldWidth);
		initResetViewListener();

	}

	public void setFieldList(Object a) {
		fieldObject = a;
	}

	public void setAdditionalFieldList(Object additional) {
		additionalFieldObject = additional;
	}

	public boolean isExportButtonVisible() {
		return exportButtonVisible;
	}

	public void setExportButtonVisible(boolean exportButtonVisible) {
		this.exportButtonVisible = exportButtonVisible;
	}

	public boolean isSubscribeButtonVisible() {
		return subscribeButtonVisible;
	}

	public void setSubscribeButtonVisible(boolean subscribeButtonVisible) {
		this.subscribeButtonVisible = subscribeButtonVisible;
	}

	public void setResetButtonVisible(boolean resetButtonVisible) {
		this.resetButtonVisible = resetButtonVisible;
	}

	public void setProcessButtonVisible(boolean processButtonVisible) {
		this.processButtonVisible = processButtonVisible;
	}

	public void setProcessButtonEnabled(boolean enabled) {
		this.processButtonEnable = enabled;
		if (processButton != null)
			this.processButton.setEnabled(enabled);
	}

	public void setSaveTablePerferenceButtonVisible(boolean saveTablePerferenceButtonVisible) {
		this.saveTablePerferenceButtonVisible = saveTablePerferenceButtonVisible;
	}

	public boolean getSaveTablePerferenceButtonVisible() {
		return this.saveTablePerferenceButtonVisible;
	}

	public boolean isResetButtonVisible() {
		return this.resetButtonVisible;
	}

	@Override
	public void packLayout() {

		IApplication application = (IApplication) this.getApplication();
		LanguageChanger changer = application.getLanguageChanger();
		Messages messages = changer.getMessages();
		
		// create xml form layout
		if (fieldObject != null) {
			formLayout.setLanguageChanger(changer);
			ArrayList list = (ArrayList) fieldObject;

			if (additionalFieldObject != null) {
				List<List<FormField>> additonalList = (List<List<FormField>>) additionalFieldObject;
				for (List<FormField> additonal : additonalList) {
					list.add(additonal);
				}
			}

			for (int i = 0; i < list.size(); i++) {
				ArrayList fieldList = (ArrayList) list.get(i);
				AbstractComponent[] components = new AbstractComponent[fieldList.size()];
				int[] fieldWidth = new int[fieldList.size()];
				boolean[] flexLayout = new boolean[fieldList.size()];
				int[] labelWidth = new int[fieldList.size()];

				for (int j = 0; j < fieldList.size(); j++) {
					FormField formField = ((FormField) fieldList.get(j));
					components[j] = ((FormField) fieldList.get(j)).getComponent();
					fieldWidth[j] = ((FormField) fieldList.get(j)).getColumnSpan() * this.fieldWidth;
					if (((FormField) fieldList.get(j)).getLabelWidth() != -1) {
						labelWidth[j] = ((FormField) fieldList.get(j)).getLabelWidth();
					} else {
						labelWidth[j] = this.labelWidth;
					}

					if (formField.getFieldType() == FormFieldType.TEXTAREA) {
						components[j].setWidth(fieldWidth[j] + "px");
					}
					flexLayout[j] = ((FormField) fieldList.get(j)).isFlexLayout();
					storeField(formField);
					fieldFormMapping.put(formField.getDataId(), formField);
				}
				// formLayout.setLabelRequired(((FormField)fieldList.get(0)).getFieldType()
				// != FormFieldType.RADIO_BUTTON);
				formLayout.setPaddingRequired(((FormField) fieldList.get(0)).getPaddingRequired());
				formLayout.addField(components, fieldWidth, labelWidth, flexLayout);

				for (int j = 0; j < fieldList.size(); j++) {
					FormField formField = ((FormField) fieldList.get(j));
					if (formField.getComponent() instanceof Upload) {
						Upload upload = (Upload) formField.getComponent();
						ProgressIndicator indicator = formLayout.getProgressIndicator(upload);
						Label errorLabel = formLayout.getErrorLabel(upload);
						ByteArrayOutputStream baos = new ByteArrayOutputStream();

						UploadSuccessListener successListener = new UploadSuccessListener(errorLabel, messages);
						UploadFailListener failListener = new UploadFailListener(errorLabel, messages);
						UploadProgressListener progressListener = new UploadProgressListener(upload, maxFileSize, indicator,
								errorLabel, messages);
						UploadReceiverListener receiveListener = new UploadReceiverListener(baos);
						UploadFinishListener finishedListener = new UploadFinishListener(errorLabel, indicator);

						upload.addListener(successListener);
						upload.addListener(failListener);
						upload.addListener(progressListener);
						upload.setReceiver(receiveListener);
						upload.addListener(finishedListener);

						uploadFailedMap.put(upload, failListener);
						uploadReceiverMap.put(upload, receiveListener);
						
						uploadSuccessMap.put(upload, successListener);
						uploadProgressMap.put(upload, progressListener);
					}
				}
			}
		}

		this.setWidth("100%");

		if (styleRequire){
		// upper layout
			outlineLayout.addStyleName("enquiryView");
			outlineLayout.setWidth(null);
		}

		CssLayout upperLayout = new CssLayout();
		if (styleRequire){
			upperLayout.addStyleName("enquiryViewTop");
		}
		upperLayout.setWidth(null);
		upperLayout.setMargin(new MarginInfo(true, false, false, false));
		outlineLayout.addComponent(upperLayout);

		if (canCollapse){
			// collapse view layout
			CssLayout collapseLayout = new CssLayout();
			collapseLayout.setWidth(null);
			collapseLayout.setHeight("15px");
			collapseLayout.addStyleName("enquiryViewCollapse");
			collapseLayout.addListener(new LayoutClickListener() {
	
				@Override
				public void layoutClick(LayoutClickEvent event) {
					BaseEnquiryView.this.colapseView(true);
				}
			});
			outlineLayout.addComponent(collapseLayout);
		}
		
		if (styleRequire){
			// middle line
			CssLayout middleLayout = new CssLayout();
			middleLayout.setWidth(null);
			middleLayout.setHeight("1px");
			middleLayout.addStyleName("enquiryViewMiddle");
	
			outlineLayout.addComponent(middleLayout);
		}

		// lower layout
		CssLayout bottomLayout = new CssLayout();
		if (styleRequire){
			bottomLayout.addStyleName("enquiryViewBottomMiddle");
		}
		
		outlineLayout.addComponent(bottomLayout);

		if (requiredOneMessageLabel != null) {
			changer.changeCode(this.requiredOneMessageLabel);
			requiredOneLayout.addComponent(missingFieldNameLabel);
			requiredOneLayout.addComponent(requiredOneMessageLabel);
			upperLayout.addComponent(requiredOneLayout);
			// set the default Visibility to false
			requiredOneLayout.setVisible(false);
		}

		upperLayout.addComponent(formLayout);
		HorizontalLayout buttonLayout = createButtonLayout();

		CssLayout buttonBoundLayout = new CssLayout();
		buttonBoundLayout.addComponent(buttonLayout);

		int totalWidth = 0;

		if (resetButtonVisible)
			totalWidth += 80 + 15;

		if (exportButtonVisible)
			totalWidth += 80 + 15;

		if (subscribeButtonVisible && application.isPermitted(LayoutPermissionType.REPORT_SUBSCRIPTION))
			totalWidth += 80 + 15;

		if (processButtonVisible)
			totalWidth += 80 + 15;

		if (saveTablePerferenceButtonVisible) {
			totalWidth += 80 + 15;
		}

		if (extraButtonList != null) {
			for (ExtraButton button : extraButtonList) {
				if (button.getVisible()) {
					totalWidth += 80 + 15;
				}
			}
		}

		buttonBoundLayout.setWidth((totalWidth + 15) + "px");
		buttonBoundLayout.addStyleName("enquiryViewButtonBound");

		bottomLayout.addComponent(buttonBoundLayout);

		this.addComponent(outlineLayout);
		keyValueLayout.setHeight("0px");
		keyValueLayout.setWidth(null);
		keyValueLayout.addStyleName("enquiryViewDisplay");
		keyValueLayout.addStyleName("enquiryView");
		keyValueLayout.setVisible(false);
		this.addComponent(keyValueLayout);
		this.addComponent(proxy);
	}

	private HorizontalLayout createButtonLayout() {

		IApplication application = (IApplication) this.getApplication();
		LanguageChanger languageChanger = application.getLanguageChanger();
		Messages messages = languageChanger.getMessages();

		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.addStyleName("buttonPanel");
		buttonLayout.setSizeFull();

		processButton = new ProcessButton(processButtonName);
		stopButton = new StopButton(stopButtonName);
		exportButton = new NormalButton(exportButtonName);
		subscribeButton = new NormalButton(subscribeButtonName);
		saveTablePerferenceButton = new NormalButton(saveTablePreferenceName);

		initResetListener();
		createExpandableEvent(processButton);

		applyListener(processButtonClickList, processButton);
		applyListener(stopButtonClickList, stopButton);
		applyListener(exportButtonClickList, exportButton);
		applyListener(subscribeButtonClickList, subscribeButton);
		applyListener(saveTablePreferenceClickList, saveTablePerferenceButton);

		if (processButtonVisible) {
			buttonLayout.addComponent(processButton);
			processButton.setVisible(processButtonVisible);
			processButton.setEnabled(processButtonEnable);
			languageChanger.changeCode(processButton);
		}
		if (exportButtonVisible) {
			buttonLayout.addComponent(exportButton);
			exportButton.setVisible(exportButtonVisible);
			languageChanger.changeCode(exportButton);
		}
		if (resetButtonVisible) {
			buttonLayout.addComponent(stopButton);
			stopButton.setVisible(resetButtonVisible);
			languageChanger.changeCode(stopButton);
		}


		if (subscribeButtonVisible && application.isPermitted(LayoutPermissionType.REPORT_SUBSCRIPTION)) {
			buttonLayout.addComponent(subscribeButton);
			languageChanger.changeCode(subscribeButton);
			subscribeButton.setVisible(subscribeButtonVisible
					&& application.isPermitted(LayoutPermissionType.REPORT_SUBSCRIPTION));
		}

		if (extraButtonList != null) {
			for (ExtraButton button : extraButtonList) {
				if (button.getVisible()) {
					button.getButton().setCaption(messages.getString(button.getButtonName()));
					buttonLayout.addComponent(button.getButton());
					applyListener(button.getListener(), button.getButton());
				}
			}
		}

		if (saveTablePerferenceButtonVisible) {
			buttonLayout.addComponent(saveTablePerferenceButton);
			saveTablePerferenceButton.setVisible(saveTablePerferenceButtonVisible);
			saveTablePerferenceButton.setEnabled(false);
			languageChanger.changeCode(saveTablePerferenceButton);
		}

		buttonLayout.setMargin(true, true, true, true);
		buttonLayout.setSpacing(true);

		return buttonLayout;
	}

	public void setRequiredLabel(boolean requiredLabel) {
		formLayout.setRequiredLabel(requiredLabel);
	}

	private void storeField(FormField field) {
		switch (field.getFieldType()) {
		case LABEL:
			componentNameMapping.put(field.getDataId(), field.getComponent());
			break;
		default:
			storeField(field.getDataId(), field.getComponent());
			break;

		}

	}

	private void storeField(String dataId, AbstractComponent component) {
		fieldNameMapping.put(dataId, component);
	}

	@Override
	public Map<String, Object> submit() {
		return submit(true);
	}

	@Override
	public Map<String, Object> submit(boolean reset) {
		return submit(reset, false);
	}

	public Map<String, Object> submit(boolean reset, boolean isSubscription) {
		Map<String, Object> dataMap = new LinkedHashMap<String, Object>();

		if (reset) {
			resetPageOnly();

			if (this.getWindow() instanceof IWindow)
				((IWindow) this.getWindow()).updateTimeStamp();
		}

		Messages messages = ((IApplication) ((Window) this.getWindow()).getApplication()).getLanguageChanger().getMessages();
		dataMap.put(CommonConstant.LANGUAGE, messages.getString("common.locale"));

		for (Iterator<String> it = fieldNameMapping.keySet().iterator(); it.hasNext();) {
			String dataId = it.next();
			if (fieldNameMapping.get(dataId) instanceof Upload) {
				Upload upload = (Upload) fieldNameMapping.get(dataId);
				UploadReceiverListener receiver = (UploadReceiverListener) uploadReceiverMap.get(upload);

				FilePart filepart = new FilePart();
				filepart.setFileName(receiver.getFileName());

				ByteArrayOutputStream os = (ByteArrayOutputStream) receiver.getOutputStream();
				filepart.setContent(os.toByteArray());

				dataMap.put(dataId, filepart.getContent());
			} else {
				AbstractField field = (AbstractField) fieldNameMapping.get(dataId);
				Object value = field.getValue();

				if (value instanceof Date) {
					((Date) value).setSeconds(0);
					if (!isSubscription)
						value = dateFormat.format((Date) value);
					else if (!this.timeFixedForSubscription)
						value = dateFormat.format((Date) value);
					else
						continue;
				}

				if (field instanceof TwinColSelect) {
					if (value instanceof Set) {
						Set set = (Set) value;
						Set<String> newSet = new HashSet<String>();
						for (Iterator its = set.iterator(); its.hasNext();) {
							Object item = its.next();
							if (item instanceof ComboBoxItem) {
								newSet.add((String) ((ComboBoxItem) item).getItem());
							} else {
								newSet.add((String) item);
							}
						}
						dataMap.put(dataId, newSet);
					} else {
						dataMap.put(dataId, value);
					}
				} else if (!(value instanceof ComboBoxItem)) {
					if (value != null) {
						try {
							if (field.getStyleName() != null && field.getStyleName().indexOf("upper-case") >= 0)
								dataMap.put(dataId, ((String) value).toUpperCase());
							else
								dataMap.put(dataId, value);
						} catch (Exception e) {
							dataMap.put(dataId, value);
						}
					}
				} else {
					ComboBoxItem item = ((ComboBoxItem) value);
					if (item != null) {
						if (item.getItem() != null)
							dataMap.put(dataId, ((ComboBoxItem) value).getItem());
					}
				}
			}
		}

		lastCriteria = dataMap;
		return dataMap;
	}

	@Override
	public void setProcessClickListener(ClickListener listener) {
		processButtonClickList.add(listener);
	}

	@Override
	public void setResetClickListener(ClickListener listener) {
		stopButtonClickList.add(listener);
	}

	@Override
	public void setExportClickListener(ClickListener listener) {
		exportButtonClickList.add(listener);
	}

	@Override
	public void setSubscribeClickListener(ClickListener listener) {
		subscribeButtonClickList.add(listener);
	}

	@Override
	public void setSaveTablePreferenceClickList(ClickListener listener) {
		saveTablePreferenceClickList.add(listener);
	}

	public boolean validate() {
		return validate(true);
	}

	@Override
	public boolean validateSubscription() {
		isSubscription = true;

		try {
			return validate();
		} finally {
			isSubscription = false;
		}
	}

	@Override
	public boolean validate(boolean reset) {

		if (requiredOneMessageLabel != null) {
			requiredOneLayout.setVisible(false);
		}

		if (reset)
			resetPageOnly();

		boolean isValid = false;
		if (!isSubscription) {
			isValid = formLayout.validate();
		} else {
			isValid = formLayout.validateSubscription();
		}

		if (isValid) {
			// check if required one exist
			if (requiredOneExcludeList.size() == 0 && requiredOneIncludeList.size() == 0 && requiredAllList.size() == 0)
				return true;
			// check if required all exist
			else if (requiredAllList.size() != 0) {
				Map<String, Object> inputField = submit(reset);

				for (String requiredAllDataId : requiredAllList) {
					if (isSubscription && getComponent(requiredAllDataId) instanceof DateField) {
						continue;
					}

					if (inputField.get(requiredAllDataId) == null || "".equals(inputField.get(requiredAllDataId))) {
						// If one of the required field is null
						if (requiredOneMessageLabel != null) {
							IApplication application = (IApplication) this.getApplication();
							Messages messages = application.getLanguageChanger().getMessages();
							String messageKey = this.requiredOneMessageKey;
							if (isSubscription) {
								if (this.requiredOneSubscriptionMessageKey != null) {
									messageKey = this.requiredOneSubscriptionMessageKey;
									requiredOneMessageLabel.setValue(messages.getString(messageKey));
								}
							} else {
								requiredOneMessageLabel.setValue(messages.getString(messageKey));
							}
							requiredOneLayout.setVisible(true);
						}
						return false;
					}
				}

				if (requiredOneMessageLabel != null) {
					requiredOneLayout.setVisible(false);
				}

				return true;
			} else {
				Map<String, Object> inputField = submit(reset);
				for (String requiredDataId : requiredOneIncludeList) {

					if (requiredOneExcludeList.contains(requiredDataId))
						break;
					else {
						if (isSubscription && getComponent(requiredDataId) instanceof DateField) {
							continue;
						}

						if (inputField.get(requiredDataId) != null && !"".equals(inputField.get(requiredDataId))) {
							if (requiredOneMessageLabel != null) {
								requiredOneLayout.setVisible(false);
							}
							return true;
						}
					}
				}
				if (requiredOneMessageLabel != null) {
					IApplication application = (IApplication) this.getApplication();
					Messages messages = application.getLanguageChanger().getMessages();
					String messageKey = this.requiredOneMessageKey;
					if (isSubscription) {
						if (this.requiredOneSubscriptionMessageKey != null) {
							messageKey = this.requiredOneSubscriptionMessageKey;
							requiredOneMessageLabel.setValue(messages.getString(messageKey));
						}
					} else {
						requiredOneMessageLabel.setValue(messages.getString(messageKey));
					}

					requiredOneLayout.setVisible(true);
				}
			}

			if (uploadFailedMap != null && uploadFailedMap.size() > 0) {
				Messages messages = ((IApplication) this.getApplication()).getLanguageChanger().getMessages();
				for (Iterator<Upload> it = uploadFailedMap.keySet().iterator(); it.hasNext();) {
					Upload upload = it.next();
					UploadFailListener listener = (UploadFailListener) uploadFailedMap.get(upload);
					if (!listener.isFailed() || upload.isUploading()) {
						Label label = formLayout.getErrorLabel(upload);
						label.setValue(messages.getString("common.upload.uploading"));
						return false;
					}
				}
			}
		}
		return false;
	}

	@Override
	public AbstractComponent getComponent(String dataId) {
		AbstractComponent component = componentNameMapping.get(dataId);
		if (component == null)
			component = fieldNameMapping.get(dataId);

		return component;
	}

	private void initResetListener() {
		stopButton.addListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				resetPage();
			}

		});

		resetSize();
	}

	private void initResetViewListener() {
		ClickListener listener = new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if (BaseEnquiryView.this.getWindow() instanceof IWindow)
					((IWindow) BaseEnquiryView.this.getWindow()).setNoResultViewInvisible();
			}

		};

		processButtonClickList.add(listener);
		stopButtonClickList.add(listener);
	}

	public void resetForm() {
		formLayout.reset();

		if (requiredOneMessageLabel != null) {
			requiredOneLayout.setVisible(false);
		}
		
		//reset upload field
		for (Iterator it = uploadReceiverMap.keySet().iterator() ; it.hasNext();){
			Object key = it.next();
			UploadReceiverListener listener = (UploadReceiverListener)uploadReceiverMap.get(key);
			((ByteArrayOutputStream)listener.getOutputStream()).reset();
			
			uploadSuccessMap.get(key).reset();
			uploadProgressMap.get(key).reset();
		}
	}

	public void resetPage() {
		resetForm();
		resetPageOnly();
	}

	private void resetPageOnly() {
		if (requiredOneMessageLabel != null) {
			requiredOneLayout.setVisible(false);
		}

		if (supportPageReset)
			((IWindow) formLayout.getWindow()).resetPage();
	}

	private void applyListener(List<ClickListener> listeners, Button button) {
		if (listeners != null && button != null) {
			for (ClickListener listener : listeners) {
				button.addListener(listener);
			}
		}
	}

	private void applyListener(List<LayoutClickListener> listeners, CssLayout layout) {
		if (listeners != null && layout != null) {
			for (LayoutClickListener listener : listeners) {
				layout.addListener(listener);
			}
		}
	}

	public void setRequiredOneIncludeList(List<String> requiredOneList) {
		this.requiredOneIncludeList = requiredOneList;
	}

	public void setRequiredOneExcludeList(List<String> requiredOneList) {
		this.requiredOneExcludeList = requiredOneList;
	}

	public void setRequiredAllList(List<String> requiredAllList) {
		this.requiredAllList = requiredAllList;
	}

	public void setRequiredOneMessage(String messageKey) {
		this.requiredOneMessageKey = messageKey;
		this.requiredOneMessageLabel = new Label(messageKey);
		this.requiredOneMessageLabel.setVisible(true);
		this.requiredOneMessageLabel.addStyleName("validation-error-label");
		this.requiredOneMessageLabel.addStyleName("validation-error-label-margin");
		this.missingFieldNameLabel.addStyleName("validation-error-label");
		this.requiredOneLayout.addStyleName("validation-enquiryView-error");
	}

	public void setRequiredOneSubscriptionMessage(String messageKey) {
		this.requiredOneSubscriptionMessageKey = messageKey;
	}

	public void setProcessButtonName(String key) {
		processButtonName = key;
	}

	public void setStopButtonName(String key) {
		stopButtonName = key;
	}

	public void setExportButtonName(String key) {
		exportButtonName = key;
	}

	public void setSubscribeButtonName(String key) {
		subscribeButtonName = key;
	}

	public void updateData(Map<String, Object> dataMap) {
		LanguageChanger changer = ((IApplication) this.getApplication()).getLanguageChanger();

		for (Iterator<String> it = dataMap.keySet().iterator(); it.hasNext();) {
			String key = it.next();

			if (componentNameMapping.get(key) != null) {
				AbstractComponent comp = componentNameMapping.get(key);
				Object obj = dataMap.get(key);
				if (obj instanceof YesNoType) {
					((Label) comp).setValue(((YesNoType) obj).getMessageKey());
					changer.changeCode(comp);
				} else {
					((Label) (componentNameMapping.get(key))).setValue(dataMap.get(key));
				}
			} else if (fieldNameMapping.get(key) != null) {
				if (!(fieldNameMapping.get(key) instanceof Upload)) {
					AbstractField comp = (AbstractField) fieldNameMapping.get(key);
					if (comp instanceof OptionGroup) {
						OptionGroup group = (OptionGroup) comp;
						for (Iterator ito = group.getItemIds().iterator(); ito.hasNext();) {
							Object o = ito.next();
							if (o instanceof ComboBoxItem) {
								ComboBoxItem ci = (ComboBoxItem) o;
								if (ci.getItem().equals((String) dataMap.get(key))) {
									group.setValue(o);
								}
							}
						}
					} else if (comp instanceof ComboBox) {
						ComboBox box = (ComboBox) comp;
						Collection items = box.getItemIds();
						for (Iterator itc = items.iterator(); itc.hasNext();) {
							Object o = itc.next();
							if (o instanceof ComboBoxItem) {
								ComboBoxItem ci = (ComboBoxItem) o;
								if (((ComboBoxItem) o).getItem().equals(dataMap.get(key))) {
									box.setValue(o);
								}
							} else {
								if (dataMap.get(key).equals(o))
									box.setValue(o);
							}
						}
					}
					Object obj = dataMap.get(key);
					comp.setValue(dataMap.get(key));
				}
			}
		}

		for (Iterator<String> it = componentNameMapping.keySet().iterator(); it.hasNext();) {
			String key = it.next();
			if (!dataMap.keySet().contains(key)) {
				if (!(Boolean) componentNameMapping.get(key).getData())
					((Label) (componentNameMapping.get(key))).setValue(null);
			}
		}
	}

	@Override
	public Map<String, String> getDisplayMap() {
		return getDisplayMap(false);
	}

	public Map<String, String> getDisplayMap(boolean subscription) {
		return getDisplayMap(subscription, false);
	}

	@Override
	public Map<String, String> getDisplayMap(boolean subscription, boolean display) {
		Map<String, String> dataMap = new LinkedHashMap<String, String>();
		Map<AbstractComponent, String> labelMap = this.formLayout.getLabelMap();
		LanguageChanger changer = ((IApplication) this.getApplication()).getLanguageChanger();

		for (Iterator<String> it = fieldNameMapping.keySet().iterator(); it.hasNext();) {
			String dataId = it.next();
			AbstractComponent component = fieldNameMapping.get(dataId);
			if (!(component instanceof Upload)) {
				AbstractField field = (AbstractField) component;
				Object value = field.getValue();
				String i18nKey = labelMap.get((AbstractComponent) field);
				String labelName = null;
				if (field.getCaption() != null)
					labelName = field.getCaption();
				else if (i18nKey != null)
					labelName = changer.getMessages().getString(i18nKey);

				if (value instanceof Date || field instanceof DateField) {
					if (!subscription) {
						if (value != null)
							value = displayDateFormat.format((Date) value);
					} else {
						if (!this.timeFixedForSubscription) {
							if (value != null)
								value = displayDateFormat.format((Date) value);
						} else if (!display) {
							value = dataId;
						} else {
							value = null;
						}
					}
				}

				if (!(value instanceof ComboBoxItem)) {
					if (value != null) {
						try {
							if (((String) value).length() > 0)
								dataMap.put(labelName, ((String) value).toUpperCase());
						} catch (Exception e) {
							if (value instanceof Boolean) {
								if (value == Boolean.TRUE) {
									dataMap.put(labelName, changer.getMessages().getString("common.subcription.checkbox.checked"));
								}
							} else {
								dataMap.put(labelName, String.valueOf(value));
							}
						}
					}
				} else {
					ComboBoxItem item = ((ComboBoxItem) value);
					if (item != null) {
						if (item.getItem() != null)
							if (!(field instanceof OptionGroup))
								dataMap.put(labelName,
										changer.getMessages().getString(((ComboBoxItem) value).getMessageKey(), Locale.US));
							else
								dataMap.put(changer.getMessages().getString(((ComboBoxItem) value).getMessageKey()), changer
										.getMessages().getString("common.subcription.checkbox.checked"));
					}
				}
			}
		}

		return dataMap;
	}

	public void setSupportPageReset(boolean supportPageReset) {
		this.supportPageReset = supportPageReset;
	}

	public boolean getSupportPageReset() {
		return this.supportPageReset;
	}

	public Map<String, Object> getLastCriteria() {
		return this.lastCriteria;
	}

	public void colapseView() {
		colapseView(false, true);
	}

	public void colapseView(boolean collapse) {
		colapseView(true, false);
	}

	public void colapseView(boolean collapse, boolean auto) {
//		if (auto) {
//			((IApplication) this.getApplication()).updateStatistic(UtilizationStatisticsConstant.PAGE_TYPE_SEARCH);
//		}

		if (autoCollapse || collapse) {
			if (expandable) {
				// clear keyValue layout first
				keyValueLayout.removeAllComponents();

				// insert export xls icon
				if (exportButtonClickList.size() > 0) {
					IApplication application = (IApplication) this.getApplication();
					if (exportButtonTiny == null) {

						exportButtonTiny = new Button();
						exportButtonTiny.setIcon(new ThemeResource("img/export/excel.png"));
						exportButtonTiny.setStyleName(Button.STYLE_LINK);
						exportButtonTiny.addStyleName("link_icon_button");

						for (int i = 0; i < exportButtonClickList.size(); i++)
							exportButtonTiny.addListener(exportButtonClickList.get(i));
					}

					exportButtonTiny.setDescription(application.getLanguageChanger().getMessages()
							.getString("common.button.export"));
					keyValueLayout.addComponent(exportButtonTiny);
				}

				// add value to keyValue Layout first as Label with display map
				Map<String, String> displayMap = getDisplayMap();
				if (displayMap.size() > 0) {
					for (Iterator<String> it = displayMap.keySet().iterator(); it.hasNext();) {
						String key = it.next();
						Label label = new Label("<b>" + key + " : </b>" + displayMap.get(key));
						label.setContentMode(Label.CONTENT_XHTML);
						label.addStyleName("enquiryViewDisplayLabel");
						label.setSizeUndefined();
						label.setWidth(null);
						keyValueLayout.addComponent(label);
					}
				} else {
					IApplication application = (IApplication) this.getApplication();
					String labelName = " ";
					if (application != null) {
						labelName = application.getLanguageChanger().getMessages().getString("enquiry.noCriteria");
					}
					Label label = new Label("<b>" + labelName + "</b>");
					label.setContentMode(Label.CONTENT_XHTML);
					label.addStyleName("enquiryViewDisplayLabel");
					label.setSizeUndefined();
					label.setWidth(null);
					keyValueLayout.addComponent(label);
				}

				// animation
				proxy.animate(outlineLayout, AnimType.FADE_OUT).setDuration(duration).setDelay(delay);
				proxy.animate(outlineLayout, AnimType.ROLL_UP_CLOSE).setDuration(duration).setDelay(delay);
				proxy.addListener(new AnimationListener() {

					@Override
					public void onAnimation(AnimationEvent event) {
						outlineLayout.setHeight("0px");
					}
				});

				keyValueLayout.setHeight(null);
				keyValueLayout.setVisible(true);
				proxy.animate(keyValueLayout, AnimType.ROLL_DOWN_OPEN).setDuration(duration).setDelay(delay);
				proxy.animate(keyValueLayout, AnimType.FADE_IN).setDuration(duration).setDelay(delay);
				proxy.addListener(new AnimationListener() {

					@Override
					public void onAnimation(AnimationEvent event) {
						keyValueLayout.setHeight(null);
						keyValueLayout.setVisible(true);
					}
				});
			}
		}
		resetSize();
	}

	private void createExpandableEvent(Button processButton) {
		if (expandable) {

			keyValueLayout.addListener(new LayoutClickListener() {

				@Override
				public void layoutClick(LayoutClickEvent event) {
					// clear keyValue layout first
					keyValueLayout.removeAllComponents();

					// animation
					proxy.animate(keyValueLayout, AnimType.FADE_OUT).setDuration(duration).setDelay(delay);
					proxy.animate(keyValueLayout, AnimType.ROLL_UP_CLOSE).setDuration(duration).setDelay(delay);
					proxy.addListener(new AnimationListener() {

						@Override
						public void onAnimation(AnimationEvent event) {
							keyValueLayout.setHeight("0px");
							keyValueLayout.setVisible(false);
						}
					});

					proxy.animate(outlineLayout, AnimType.ROLL_DOWN_OPEN).setDuration(0).setDelay(0);
					proxy.animate(outlineLayout, AnimType.FADE_IN).setDuration(duration).setDelay(200);
					outlineLayout.setVisible(true);
					outlineLayout.setHeight(null);

					proxy.addListener(new AnimationListener() {

						@Override
						public void onAnimation(AnimationEvent event) {
							outlineLayout.setHeight(null);
						}
					});
				}
			});
		}
	}

	@Override
	public void retrieveInfo() {
		GlobalPortletDataTransfer dataTransfer = GlobalPortletDataTransfer.getInstance();
		if (dataTransfer != null) {
			if (this != null) {
				if (this.getWindow() instanceof IWindow) {
					IWindow window = (IWindow) this.getWindow();
					Window win = this.getWindow();
					if (window != null && win != null) {
						Application application = this.getWindow().getApplication();
						if (application != null) {
							PortletApplicationContext2 context = (PortletApplicationContext2) application.getContext();
							if (context != null) {
								if (context.getPortletSession() != null) {
									String sessionId = context.getPortletSession().getId();
									Object obj = dataTransfer.getAttribute(sessionId + "_" + window.getId());
									dataTransfer.removeAttribute(sessionId + "_" + window.getId());
									if (obj != null && obj instanceof Map) {
										Map<String, Object> infoMap = (Map<String, Object>) obj;
										updateData(infoMap);

										// then call process listener
										for (ClickListener listener : processButtonClickList) {
											listener.buttonClick(null);
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public void setMaxFileSize(long maxFileSize) {
		this.maxFileSize = maxFileSize;
	}

	public long getMaxFileSize() {
		return this.maxFileSize;
	}

	public void setExtraButtonList(List<ExtraButton> extraButtonList) {
		this.extraButtonList = extraButtonList;
	}

	public List<ExtraButton> getExtraButtonList() {
		return this.extraButtonList;
	}

	private void resetSize() {
		try {
			this.getWindow().executeJavaScript("resizedelay();");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void setTimeFixedForSubscription(boolean timeFixedForSubscription) {
		this.timeFixedForSubscription = timeFixedForSubscription;
	}

	public boolean getTimeFixedForSubscription() {
		return this.timeFixedForSubscription;
	}

	public void setAutoCollapse(boolean autoCollapse) {
		this.autoCollapse = autoCollapse;
	}

	public boolean isAutoCollapse() {
		return this.autoCollapse;
	}
	
	public void setCanCollapse(boolean canCollapse){
		this.canCollapse = canCollapse;
	}
	
	public boolean isCanCollapse(){
		return this.canCollapse;
	}
	
	public void setStyleRequire(boolean styleRequire){
		this.styleRequire = styleRequire;
	}
	
	public boolean isStyleRequire(){
		return this.styleRequire;
	}

	@Override
	public void enablePreferenceButton(boolean enable) {
		if (saveTablePerferenceButtonVisible) {
			saveTablePerferenceButton.setEnabled(enable);
		}
	}
}
