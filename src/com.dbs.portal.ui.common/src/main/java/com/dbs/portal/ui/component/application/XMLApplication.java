package com.dbs.portal.ui.component.application;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.xml.DOMConfigurator;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import com.dbs.portal.ui.component.type.LayoutPermissionType;
import com.dbs.portal.ui.component.view.IController;
import com.dbs.portal.ui.component.view.IEnquiryView;
import com.dbs.portal.ui.component.view.IView;
import com.dbs.portal.ui.component.view.IWindow;
import com.dbs.portal.ui.util.LanguageChanger;
import com.dbs.portal.ui.util.Messages;
import com.dbs.portal.ui.util.SpringContextHelper;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.PortletApplicationContext2;
import com.vaadin.terminal.gwt.server.PortletApplicationContext2.PortletListener;
import com.vaadin.terminal.gwt.server.PortletRequestListener;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.Notification;

public class XMLApplication<T extends Window, U> extends Application implements PortletRequestListener, PortletListener,
		IApplication {

	private Logger logger = Logger.getLogger(this.getClass());

	private static final long serialVersionUID = -968934365006706274L;
	private static final CustomizedSystemMessages CUSTOMIZED_SYSTEM_MESSAGES = new CustomizedSystemMessages();
	protected SpringContextHelper contextHelper;
	private Messages messages;

	private LanguageChanger changer = new LanguageChanger();

	private final String messagingName = "messaging";

	private Map<String, Boolean> permissionMap = null;

	private User user = null;

	protected T view;
	protected U control;

	private boolean init = false;

	public XMLApplication() {
		super();
	}

	@Override
	public void onRequestStart(PortletRequest request, PortletResponse response) {

		try {
			if (user == null) {
				 ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		         user = themeDisplay.getUser();
//				user = PortalUtil.getUser(request);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		try {
			if (messages != null) {
				Locale locale = request.getLocale();
				if (locale != null) {
					messages.setLocale(locale);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void onRequestEnd(PortletRequest request, PortletResponse response) {
	}

	public void setView(T window) {
		this.view = window;
	}

	public T getWindow() {
		return this.view;
	}

	public void setControl(U control) {
		this.control = control;
	}

	public U getControl() {
		return this.control;
	}

	@Override
	public void init() {

		// Set Vaadin Communication Error
		String comErrEnabledStr = PropsUtil.get("vaadin.communicationError.notificationEnabled");
		if (comErrEnabledStr != null) {
			CUSTOMIZED_SYSTEM_MESSAGES.setCommunicationErrorNotificationEnabled(Boolean.parseBoolean(comErrEnabledStr));
		}
		CUSTOMIZED_SYSTEM_MESSAGES.setCommunicationErrorURL(PropsUtil.get("vaadin.communicationError.url"));

		setMainWindow(new Window());

		if (getContext() instanceof PortletApplicationContext2) {
			PortletApplicationContext2 ctx = (PortletApplicationContext2) getContext();
			ctx.addPortletListener(this, this);
		} else {
			// TODO The servlet has been initialized directly so we need to
			// restart it.
			getMainWindow().showNotification("Not inited via Portal!", Notification.TYPE_ERROR_MESSAGE);
		}
		launchUI();
	}

	@Override
	public void handleRenderRequest(RenderRequest request, RenderResponse response, Window window) {
		launchInitUI(request);
	}

	@Override
	public void handleActionRequest(ActionRequest request, ActionResponse response, Window window) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleEventRequest(EventRequest request, EventResponse response, Window window) {
	}

	@Override
	public void handleResourceRequest(ResourceRequest request, ResourceResponse response, Window window) {
		launchInitUI(request);
	}

	@Override
	public LanguageChanger getLanguageChanger() {
		return changer;
	}

	@Override
	public User getCurrentUser() {
		return this.user;
	}

	public static SystemMessages getSystemMessages() {
		return CUSTOMIZED_SYSTEM_MESSAGES;
	}

	@Override
	public boolean isPermitted(String key) {
		if (permissionMap.containsKey(key)) {
			return permissionMap.get(key);
		}
		return false;
	}

	private void grapPermission(PortletRequest request, Set rightsSet) {
		if (!rightsSet.isEmpty()) {
			permissionMap = new HashMap<String, Boolean>();

			String[] actionIds = new String[rightsSet.size()];

			int count = 0;
			for (Iterator<String> it = rightsSet.iterator(); it.hasNext();) {
				actionIds[count++] = it.next();
			}

			String portletName = (String) request.getAttribute(WebKeys.PORTLET_ID);
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
			Layout layout = themeDisplay.getLayout();
			PermissionChecker permissionChecker = PermissionThreadLocal.getPermissionChecker();

			try {
				User user = themeDisplay.getUser();
				long groupId = user.getGroupId();

				for (String actionId : actionIds) {
					try {
						permissionMap.put(actionId,
								PortletPermissionUtil.contains(permissionChecker, groupId, layout, portletName, actionId));
					} catch (Exception ex) {
						logger.debug(ex.getMessage(), ex);
					}
				}
			} catch (Exception e) {
				for (String actionId : actionIds) {
					try {
						permissionMap.put(actionId,
								PortletPermissionUtil.contains(permissionChecker, layout, portletName, actionId));
					} catch (Exception exx) {
						logger.debug(exx.getMessage(), exx);
					}
				}
			}
		}
	}

	private Set getRightsToCheck(T w) {
		Set<String> set = new HashSet<String>();
		IWindow window = (IWindow) w;

		Map<String, IView> layouts = window.getMapOfLayout();
		if (layouts != null) {
			for (Iterator<String> it = layouts.keySet().iterator(); it.hasNext();) {
				String key = it.next();
				IView view = layouts.get(key);

				if (view instanceof IEnquiryView) {
					if (((IEnquiryView) view).isSubscribeButtonVisible()) {
						set.add(LayoutPermissionType.REPORT_SUBSCRIPTION);
					}
				}
			}
		}

		return set;
	}

	public void launchUI() {
		this.setLogoutURL("");

		setTheme("dbsportal");
		contextHelper = new SpringContextHelper(this);
		messages = (Messages) contextHelper.getContext().getBean(messagingName);

		Locale locale = this.getLocale();

		if (locale != null) {
			messages.setLocale(locale);
		}

		changer.setMessages(messages);

		view = (T) contextHelper.getBean(((Class) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0]).getSimpleName());
		control = (U) contextHelper.getBean(((Class) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[1]).getSimpleName());

		setMainWindow(view);
	}

	private void launchInitUI(PortletRequest request) {
		if (!init) {
			init = true;
			// log4j logger init.
			try {
				Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
						.parse(this.getClass().getResourceAsStream("/portlet-log4j.xml"));
				DOMConfigurator.configure(doc.getDocumentElement());
			} catch (Exception e) {
				logger.error(this.getClass().getName(), e);
			}
			launchUI(request);
		}
	}

	public void launchUI(PortletRequest request) {

		try {
			// not to throw exception...

			// get customized permission
			grapPermission(request, getRightsToCheck(view));

			((IWindow) view).init();

			((IController) control).init();

			if (getContext() instanceof PortletApplicationContext2) {
				PortletApplicationContext2 ctx = (PortletApplicationContext2) getContext();
				((IController) control).setBrowser(ctx.getBrowser().getBrowserApplication());

			}

			((IWindow) view).retrieveInfo();

//			initTableSequence();

			// updateStatistic(UtilizationStatisticsConstant.PAGE_TYPE_VIEW);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

//	private void initTableSequence() {
//		IWindow w = ((IWindow) view);
//		IController c = ((IController) control);
//
//		Map<String, IView> viewMaps = w.getMapOfLayout();
//		if (viewMaps != null) {
//			for (Iterator<String> it = viewMaps.keySet().iterator(); it.hasNext();) {
//				String key = it.next();
//				IView v = viewMaps.get(key);
//				if (v instanceof ITableResultView) {
//					ITableResultView tableResult = (ITableResultView) v;
//					Map<Dimension, Object> orderDimensionMap = new HashMap<Dimension, Object>();
//					orderDimensionMap.put(Dimension.FUNCTION_ID, c.getId());
//					orderDimensionMap.put(Dimension.TABLE_ID, tableResult.getId());
//
//					Map<Dimension, Object> hiddenDimesionMap = new HashMap<Dimension, Object>();
//					hiddenDimesionMap.put(Dimension.FUNCTION_ID, c.getId());
//					hiddenDimesionMap.put(Dimension.TABLE_ID, tableResult.getId());
//
//					Map<String, List<String>> orderMap = c.getPreference(PreferencesInfoType.USERPREF_TABLE_COLUMN_ORDER,
//							orderDimensionMap);
//					Map<String, List<String>> hiddenMap = c.getPreference(PreferencesInfoType.USERPREF_TABLE_COLUMN_SHOWN,
//							hiddenDimesionMap);
//
//					List<String> orderList = orderMap.get("order");
//					List<String> hiddenList = hiddenMap.get("hidden");
//
//					Object[] orders = null;
//					String[] hiddens = null;
//
//					if (orderList != null) {
//						orders = new Object[orderList.size()];
//						for (int i = 0; i < orderList.size(); i++) {
//							orders[i] = orderList.get(i);
//						}
//					}
//
//					if (hiddenList != null) {
//						hiddens = new String[hiddenList.size()];
//						for (int i = 0; i < hiddenList.size(); i++) {
//							hiddens[i] = hiddenList.get(i);
//						}
//					}
//
//					if (orders != null || hiddens != null)
//						tableResult.setSavedColumnHeader(orders, hiddens);
//
//				}
//			}
//		}
//	}

//	public void updateStatistic(String type) {
//		((IController) control).updateStatistic(type);
//	}
}
