package com.dbs.portal.ui.component.view;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.net.BCodec;
import org.apache.log4j.Logger;

import com.dbs.db.filter.Criteria;
import com.dbs.db.filter.Logical;
import com.dbs.portal.database.constants.SubscriptionMaintenance;
import com.dbs.portal.database.to.common.GlobalPortletDataTransfer;
import com.dbs.portal.database.to.subscription.Frequency;
import com.dbs.portal.database.to.subscription.ScheduleInfo;
import com.dbs.portal.ui.component.application.IApplication;
import com.dbs.portal.ui.component.comboBox.ComboBoxItem;
import com.dbs.portal.ui.export.report.ExcelReportExporter;
import com.dbs.portal.ui.export.report.IReportGenertor;
import com.dbs.portal.ui.subscription.InvalidTimeFrameException;
import com.dbs.portal.ui.terminal.DownloadStreamResource;
import com.dbs.portal.ui.util.LanguageChanger;
import com.dbs.portal.ui.util.Messages;
import com.dbs.util.StringUtils;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.gwt.server.PortletApplicationContext2;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.Notification;
//import org.apache.commons.codec.net.BCodec;

public class BaseController implements IController {

//	protected IClientService clientService = null;
	protected IWindow view;
	private List clickListeners = null;
	private boolean isPublicFunction = false;
	private String browser = "";

	private String id = "";

	private Logger logger = Logger.getLogger(this.getClass());

	@Override
	public void init() {
//		clientService = (IClientService) StaticContext.getInstance().getContext().getBean("clientLocalService");

		if (clickListeners != null) {
			for (int i = 0; i < clickListeners.size(); i++) {
				IInit listener = (IInit) clickListeners.get(i);
				listener.setControl(this);
				listener.setView(view);
			}
		}
	}

	public void checkSession() {
		String sessionId = ((PortletApplicationContext2) ((Window) view).getApplication().getContext()).getPortletSession()
				.getId();
		//clientService.checkSession(sessionId);
	}

	public void setView(IWindow view) {
		this.view = view;
		view.setId(getId());
	}

	public void setMapOfListener(List object) {
		this.clickListeners = object;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

//	public void subscribeReport(Map<String, Object> infoMap, Map<String, Object> criteria, Map<String, String> displayMap,
//			List<IView> viewList) throws SystemException, PortalException, InvalidTimeFrameException {
//
//		User user = ((IApplication) ((Window) view).getApplication()).getCurrentUser();
//		String userId = user.getScreenName();
//
//		String sessionId = ((PortletApplicationContext2) ((Window) view).getApplication().getContext()).getPortletSession()
//				.getId();
//
//		IApplication application = (IApplication) ((Window) view).getApplication();
//		LanguageChanger changer = application.getLanguageChanger();
//		Messages message = changer.getMessages();
//
//		List<Organization> orgs = user.getOrganizations();
//		String companyName = "";
//		Long companyId = 0L;
//		if (orgs != null && orgs.size() > 0) {
//			companyName = String.valueOf(orgs.get(0).getName());
//			companyId = orgs.get(0).getCompanyId();
//		}
//
//		SubscriptionInfo subscriptionInfo = new SubscriptionInfo();
//		subscriptionInfo.setUserId(userId);
//		subscriptionInfo.setOrganization(companyName);
//		subscriptionInfo.setOrganizationId(companyId);
//		subscriptionInfo.setRemarks((String) infoMap.get(SubscriptionMaintenance.DESCRIPTION));
//		subscriptionInfo.setSubscriptionName((String) infoMap.get(SubscriptionMaintenance.NAME));
//		subscriptionInfo.setServiceCallId(this.getId());
//		subscriptionInfo.setCreatedDateTime(new Date());
//
//		// criteria dict
//		subscriptionInfo.setCriteriaMap(criteria);
//
//		// display dict
//		subscriptionInfo.setCriteriaDisplayMap(displayMap);
//
//		criteria.put(SubscriptionMaintenance.FUNCTION_NAME, message.getString(this.getId()));
//		criteria.put(SubscriptionMaintenance.LANGUAGE, message.getString("common.locale"));
//
//		// table key dict
//		Map<String, String> viewKeyDict = new LinkedHashMap<String, String>();
//		viewKeyDict.put(SubscriptionMaintenance.CRITERIA_TABLE_KEY, message.getString("common.subscription.criteria"));
//		if (viewList.size() == 1) {
//			if (((IResultView) viewList.get(0)).getDataKey() == null
//					|| ((IResultView) viewList.get(0)).getDataKey().length() == 0)
//				viewKeyDict.put(this.getId(), message.getString(((IResultView) viewList.get(0)).getId()));
//			else
//				viewKeyDict.put(((IResultView) viewList.get(0)).getDataKey(),
//						message.getString(((IResultView) viewList.get(0)).getId()));
//		} else {
//			for (IView view : viewList) {
//				if (view instanceof IResultView) {
//					IResultView resultView = (IResultView) view;
//					viewKeyDict.put(resultView.getDataKey(), message.getString(resultView.getId()));
//				}
//			}
//		}
//		subscriptionInfo.setTableKeyMap(viewKeyDict);
//
//		// Result dictionary for presentation purpose
//		Map<String, Object> resultDict = new LinkedHashMap<String, Object>();
//		for (IView view : viewList) {
//			if (view instanceof ITableResultView) {
//				ITableResultView resultView = (ITableResultView) view;
//				Object[] visibleColumn = resultView.getCurrentVisibleColumn();
//				String[] columnHeader = resultView.getCurrentVisibleColumnName();
//
//				for (int i = 0; i < visibleColumn.length; i++) {
//					Object visibleColumnName = visibleColumn[i];
//					if (viewList.size() > 1) {
//						visibleColumnName = resultView.getDataKey() + "." + visibleColumn[i];
//					}
//					resultDict.put((String) visibleColumnName, columnHeader[i]);
//				}
//			} else if (view instanceof IGridResultView) {
//				IGridResultView resultView = (IGridResultView) view;
//				ArrayList<GridField> fieldList = resultView.getGridFieldList();
//
//				Messages messages = ((IApplication) ((Window) this.view).getApplication()).getLanguageChanger().getMessages();
//
//				for (int i = 0; i < fieldList.size(); i++) {
//					GridField field = fieldList.get(i);
//					if (field.getFieldType() != GridFieldType.LABEL) {
//						resultDict.put(field.getDataId(), messages.getString(field.getFieldNameKey()));
//					}
//				}
//			} else if (view instanceof ITableGridResultView) {
//				ITableGridResultView resultView = (ITableGridResultView) view;
//				List<GridHeaderField> fieldList = resultView.getHeaderFieldList();
//
//				for (int i = 0; i < fieldList.size(); i++) {
//					GridHeaderField field = fieldList.get(i);
//
//					ReportHeader header = new ReportHeader(field.getxCordinate(), field.getyCordinate(), message.getString(field
//							.getFieldNameKey()), field.isRepeatable());
//
//					resultDict.put(field.getDataId(), header);
//				}
//
//			}
//		}
//
//		subscriptionInfo.setResultKeyMap(resultDict);
//
//		// Recipient list
//		List recipients = new ArrayList();
//		String emails = (String) infoMap.get(SubscriptionMaintenance.EMAIL);
//
//		String[] emailList = emails.split(";");
//
//		recipients.addAll(Arrays.asList(emailList));
//		subscriptionInfo.setRecipients(recipients);
//
//		List<ScheduleInfo> schedules = createSubscriptionSchedule(infoMap);
//
//		subscriptionInfo.setSchedules(schedules);
//
//		//clientService.addSubscription(subscriptionInfo, sessionId);
//		// subscriptionService.addSubscription(subscription);
//	}

	public void exportReport(Map<String, Object> criteria, Map<String, String> displayMap, List<IView> viewList)
			throws Exception {
		
		IReportGenertor excelGenerator = new ExcelReportExporter();
		byte[] fileByte = excelGenerator.generateReport(criteria, displayMap, viewList, (IApplication) ((Window) view).getApplication(), this);
		
/*
		String sessionId = null;
		String companyId = null;
		String userId = null;

		if (!isPublicFunction) {
			User user = ((IApplication) ((Window) view).getApplication()).getCurrentUser();

			if (user != null)
				user.getScreenName();

			if (user != null) {
				List<Organization> orgs = user.getOrganizations();
				if (orgs != null && orgs.size() > 0)
					companyId = String.valueOf(orgs.get(0).getCompanyId());
			} else {
				sessionId = null;
			}

			if (((Window) view).getApplication() != null) {
				if (((PortletApplicationContext2) ((Window) view).getApplication().getContext()) != null) {
					if (((PortletApplicationContext2) ((Window) view).getApplication().getContext()).getPortletSession() != null) {
						sessionId = ((PortletApplicationContext2) ((Window) view).getApplication().getContext())
								.getPortletSession().getId();
					}
				}
			}
		}

*/
		IApplication application = (IApplication) ((Window) view).getApplication();
		LanguageChanger changer = application.getLanguageChanger();
		Messages message = changer.getMessages();
/*
		ExportRequestInfo exportRequestInfo = new ExportRequestInfo();
		exportRequestInfo.setUserId(userId);
		exportRequestInfo.setOrganization(companyId);
		exportRequestInfo.setServiceCallId(this.getId());

		// criteria dict
		exportRequestInfo.setCriteriaMap(criteria);

		// display dict
		exportRequestInfo.setCriteriaDisplayMap(displayMap);

		// table key dict
		Map<String, String> viewKeyDict = new LinkedHashMap<String, String>();
		viewKeyDict.put(SubscriptionMaintenance.CRITERIA_TABLE_KEY, message.getString("common.subscription.criteria"));
*/
		String reportName = message.getString(((IResultView) viewList.get(0)).getId() + "_E");
		if (reportName.equals(((IResultView) viewList.get(0)).getId() + "_E")) {
			reportName = message.getString(((IResultView) viewList.get(0)).getId());
		}
		/*
		if (viewList.size() == 1) {
			if (((IResultView) viewList.get(0)).getDataKey() == null
					|| ((IResultView) viewList.get(0)).getDataKey().length() == 0)
				viewKeyDict.put(this.getId(), reportName);
			else
				viewKeyDict.put(((IResultView) viewList.get(0)).getDataKey(), reportName);
		} else {
			for (IView view : viewList) {
				if (view instanceof ITableResultView || view instanceof ITableGridResultView || view instanceof IGridResultView) {
					IResultView resultView = (IResultView) view;
					viewKeyDict.put(resultView.getDataKey(), message.getString(resultView.getId()));
				}
			}
		}
		exportRequestInfo.setTableKeyMap(viewKeyDict);

		// Result dictionary for presentation purpose
		Map<String, Object> resultDict = new LinkedHashMap<String, Object>();
		Messages messages = ((IApplication) ((Window) this.view).getApplication()).getLanguageChanger().getMessages();
		for (IView view : viewList) {
			if (view instanceof ITableResultView) {
				ITableResultView resultView = (ITableResultView) view;
				Object[] visibleColumn = resultView.getCurrentVisibleColumn();
				String[] columnHeader = resultView.getCurrentVisibleColumnName();

				for (int i = 0; i < visibleColumn.length; i++) {
					Object visibleColumnName = visibleColumn[i];
					if (viewList.size() > 1) {
						visibleColumnName = resultView.getDataKey() + "." + visibleColumn[i];
					}
					resultDict.put((String) visibleColumnName, columnHeader[i]);
				}
			} else if (view instanceof IGridResultView) {
				IGridResultView resultView = (IGridResultView) view;
				ArrayList<GridField> fieldList = resultView.getGridFieldList();

				for (int i = 0; i < fieldList.size(); i++) {
					GridField field = fieldList.get(i);
					if (field.getFieldType() != GridFieldType.LABEL && field.getDataId() != null
							&& field.getDataId().length() > 0) {
						if (viewList.size() > 1) {
							resultDict.put(resultView.getDataKey() + "." + field.getDataId(),
									messages.getString(field.getFieldNameKey()));
						} else {
							resultDict.put(field.getDataId(), messages.getString(field.getFieldNameKey()));
						}
					}
				}
			} else if (view instanceof ITableGridResultView) {
				ITableGridResultView resultView = (ITableGridResultView) view;
				List<GridHeaderField> fieldList = resultView.getHeaderFieldList();
				int xCount = 0;
				for (int i = 0; i < fieldList.size(); i++) {
					GridHeaderField field = fieldList.get(i);

					if (field.getFieldNameKey() != null && !"".equals(field.getFieldNameKey())) {
						String visibleColumnName = field.getDataId();
						if (visibleColumnName == null || "".equals(visibleColumnName))
							visibleColumnName = field.getFieldNameKey();

						if (viewList.size() > 1) {
							visibleColumnName = resultView.getDataKey() + "." + visibleColumnName;
						}

						ReportHeader header = null;
						if (!field.isRepeatable()) {
							header = new ReportHeader(field.getyCordinate(), field.getxCordinate() + xCount,
									message.getString(field.getFieldNameKey()), field.getDataId() == null
											|| "".equals(field.getDataId()));
							resultDict.put(visibleColumnName, header);
						} else {
							header = new ReportHeader(field.getyCordinate(), field.getxCordinate() + xCount,
									message.getString(field.getFieldNameKey()), field.getDataId() == null
											|| "".equals(field.getDataId()), field.isRepeatable(), field.getColumnSpan());
							resultDict.put(visibleColumnName, header);
							for (GridHeaderField child : field.getChild()) {
								ReportHeader childHeader = new ReportHeader(child.getyCordinate(),
										child.getxCordinate() + xCount, message.getString(child.getFieldNameKey()),
										child.getDataId() == null || "".equals(child.getDataId()), field.isRepeatable(),
										field.getColumnSpan());

								String childDataId = child.getDataId();
								if (childDataId == null || "".equals(childDataId))
									childDataId = message.getString(child.getFieldNameKey());

								if (viewList.size() > 1) {
									childDataId = resultView.getDataKey() + "." + childDataId;
								}

								resultDict.put(childDataId, childHeader);
							}

							xCount += field.getColumnSpan();
						}

					}
				}

			}
		}

		exportRequestInfo.setResultKeyMap(resultDict);

		Map<String, Object> result = null; 
				//clientService.exportReport(exportRequestInfo, sessionId);

		
		
		if (result != null) {

			if (result.get(ScheduledJobFieldConstants.STATUS) != null) {

				if (DistributionStatus.GENERATED.equals(result.get(ScheduledJobFieldConstants.STATUS))) {
				*/
					String fileName = (String) reportName;
//					String filePath = (String) result.get(ScheduledJobFieldConstants.SUBS_REPORT_PATH);

//					if (!filePath.endsWith(File.pathSeparator))
//						filePath = filePath + File.separator + fileName;
					
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
					try {

//						modifyExcel(filePath);
						
						DownloadStreamResource stream = null;
						if (browser == null || browser.indexOf("MSIE") >= 0 || browser.toUpperCase().indexOf("CHROME") >= 0)
							stream = new DownloadStreamResource(fileByte, URLEncoder.encode(reportName, "UTF8") + "-"
									+ format.format(new Date()) + ".xls", ((Window) view).getApplication());
						else
							stream = new DownloadStreamResource(fileByte, new BCodec().encode(reportName, "UTF8") + "-"
									+ format.format(new Date()) + ".xls", ((Window) view).getApplication());
						stream.setMIMEType("application/x-octet-stream");
						view.setExporting(true);
						((Window) view).open(stream);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
/*
				} else if (DistributionStatus.FAILED_TO_GENERATE.equals(result.get(ScheduledJobFieldConstants.STATUS))) {
					throw new MessageException("common.report.export.create.fail");
				} else if (DistributionStatus.NO_NEED_TO_GENERATE.equals(result.get(ScheduledJobFieldConstants.STATUS))) {
					throw new MessageException("common.report.export.empty");
				}
			}

		}
		*/
	}

	public List<ScheduleInfo> createSubscriptionSchedule(Map<String, Object> infoMap) throws InvalidTimeFrameException {
		List<ScheduleInfo> schedules = new ArrayList<ScheduleInfo>();

		boolean weekly = infoMap.get(SubscriptionMaintenance.WEEKLY) != null;
		boolean monthly = infoMap.get(SubscriptionMaintenance.MONTHLY) != null;
		boolean daily = infoMap.get(SubscriptionMaintenance.DAILY) != null;

		if (weekly) {
			infoMap.put(SubscriptionMaintenance.WEEKLY, true);
			int scheduleId = 0;

			Map<Integer, Boolean> weeklyMap = new LinkedHashMap<Integer, Boolean>();

			weeklyMap.put(1, (Boolean) infoMap.get(SubscriptionMaintenance.SUNDAY));
			weeklyMap.put(2, (Boolean) infoMap.get(SubscriptionMaintenance.MONDAY));
			weeklyMap.put(3, (Boolean) infoMap.get(SubscriptionMaintenance.TUESDAY));
			weeklyMap.put(4, (Boolean) infoMap.get(SubscriptionMaintenance.WEDNESDAY));
			weeklyMap.put(5, (Boolean) infoMap.get(SubscriptionMaintenance.THURSDAY));
			weeklyMap.put(6, (Boolean) infoMap.get(SubscriptionMaintenance.FRIDAY));
			weeklyMap.put(7, (Boolean) infoMap.get(SubscriptionMaintenance.SATURDAY));

			for (Iterator<Integer> it = weeklyMap.keySet().iterator(); it.hasNext();) {
				Integer key = it.next();
				if (weeklyMap.get(key)) {
					ScheduleInfo scheduleInfo = new ScheduleInfo();
					scheduleInfo.setDayOfMonthOrWeek(key);
					scheduleInfo.setFrequency(Frequency.WEEKLY);
					scheduleInfo.setHour(0);
					scheduleInfo.setMinute(0);
					scheduleInfo.setLastDayOfMonth(false);
					scheduleInfo.setScheduleId(String.valueOf(scheduleId++));

					schedules.add(scheduleInfo);
				}
			}
		} else if (monthly) {
			infoMap.put(SubscriptionMaintenance.MONTHLY, true);
			int scheduleId = 0;
			String[] monthlyDays = new String[] { SubscriptionMaintenance.DAY1, SubscriptionMaintenance.DAY2,
					SubscriptionMaintenance.DAY3 };

			for (String dayField : monthlyDays) {
				String day = (String) infoMap.get(dayField);
				if (day != null) {
					ScheduleInfo scheduleInfo = new ScheduleInfo();
					scheduleInfo.setFrequency(Frequency.MONTHLY);
					scheduleInfo.setDayOfMonthOrWeek(Integer.parseInt(day));
					scheduleInfo.setHour(0);
					scheduleInfo.setMinute(0);
					scheduleInfo.setLastDayOfMonth(false);
					scheduleInfo.setScheduleId(String.valueOf(scheduleId++));

					schedules.add(scheduleInfo);
				}

			}

		} else if (daily) {
			infoMap.put(SubscriptionMaintenance.DAILY, true);
			int scheduleId = 0;
			String[] dailyTimes = new String[] { SubscriptionMaintenance.TIME1, SubscriptionMaintenance.TIME2,
					SubscriptionMaintenance.TIME3 };

			for (String dayField : dailyTimes) {
				String time = (String) infoMap.get(dayField);
				if (time != null) {
					String[] times = time.split(":");

					if (times.length == 2) {
						ScheduleInfo scheduleInfo = new ScheduleInfo();
						scheduleInfo.setFrequency(Frequency.DAILY);
						scheduleInfo.setHour(Integer.parseInt(times[0]));
						scheduleInfo.setMinute(Integer.parseInt(times[1]));
						scheduleInfo.setLastDayOfMonth(false);
						scheduleInfo.setScheduleId(String.valueOf(scheduleId++));

						schedules.add(scheduleInfo);
					}
				}

			}
		}

		if (schedules.size() == 0) {
			throw new InvalidTimeFrameException();
		}

		return schedules;
	}

	public void portToPortlet(String id, Map<String, Object> data, String path) {

		String preUrl = PropsUtil.get(PropsKeys.COMPANY_DEFAULT_HOME_URL);
		GlobalPortletDataTransfer dataTransfer = GlobalPortletDataTransfer.getInstance();
		if (dataTransfer != null) {
			String sessionId = ((PortletApplicationContext2) ((Window) view).getApplication().getContext()).getPortletSession()
					.getId();
			dataTransfer.setAttribute(sessionId + "_" + id, data);
		}
		((Window) view).open(new ExternalResource(preUrl + "/" + path));
	}

	public void showNotification(String description, int duration) {
		Notification n = new Notification("");
		n.setPosition(Notification.POSITION_CENTERED);
		n.setDescription(description);
		n.setDelayMsec(duration); // sec->msec
		((Window) view).showNotification(n);
	}

	public void setPublicFunction(boolean isPublicFunction) {
		this.isPublicFunction = isPublicFunction;
	}

	public boolean isPublicFunction() {
		return this.isPublicFunction;
	}

//	@Override
//	public Map<String, List<String>> getPreference(PreferencesInfoType infoType, Map<Dimension, Object> dimension) {
//		//String userSessionId = ((PortletApplicationContext2) ((Window) view).getApplication().getContext()).getPortletSession()
//		//		.getId();
//
//		if (dimension == null) {
//			dimension = new HashMap<Dimension, Object>();
//		}
//
//		//Map<String, Object> result = clientService.getPreferences(infoType, dimension, userSessionId);
//		Map<String, List<String>> output = new HashMap<String, List<String>>();
//
///*		if (result != null) {
//			for (Iterator<String> it = result.keySet().iterator(); it.hasNext();) {
//				String key = it.next();
//				String value = (String) result.get(key);
//				String[] values = value.split(",.,");
//				output.put(key, Arrays.asList(values));
//			}
//		}*/
//
//		return output;
//	}
//
//	@Override
//	public void updatePreference(PreferencesInfoType infoType, Map<Dimension, Object> dimension, Map<String, List<String>> value) {
//		try {
//			String userSessionId = ((PortletApplicationContext2) ((Window) view).getApplication().getContext())
//					.getPortletSession().getId();
//
//			User user = ((IApplication) ((Window) view).getApplication()).getCurrentUser();
//
//			if (dimension == null)
//				dimension = new HashMap<Dimension, Object>();
//
//			PreferencesInfo pInfo = new PreferencesInfo();
//			pInfo.setDimensionMap(dimension);
//			pInfo.setPreferencesType(infoType);
//			pInfo.setUserId(user.getScreenName());
//
//			Map<String, Object> pref = new HashMap<String, Object>();
//
//			for (Iterator<String> it = value.keySet().iterator(); it.hasNext();) {
//				String key = it.next();
//				List<String> list = value.get(key);
//
//				String stringList = StringUtils.join(list, ",.,");
//				pref.put(key, stringList);
//			}
//			pInfo.setPreferencesMap(pref);
//
//			//clientService.putPreferences(pInfo, userSessionId);
//		} catch (Exception ex) {
//			logger.error(ex.getMessage(), ex);
//		}
//	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getBrowser() {
		return this.browser;
	}

//	public void updateStatistic(String type) {
//		if (this.getId() != null && !this.getId().trim().isEmpty()) {
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put(UtilizationStatisticsConstant.FUNCTION_ID, this.getId());
//			map.put(UtilizationStatisticsConstant.PAGE_TYPE, type);
//			try {
//				if (clientService == null)
//					clientService = (IClientService) StaticContext.getInstance().getContext().getBean("clientLocalService");
//				//clientService.addUtilization(map);
//			} catch (Exception e) {
//
//			}
//		}
//	}
	
	private void modifyExcel(String filePath){}
	
	protected void addCriteria(Map<String, Object> beanMap, String mapKey, String dbKey, Logical logical, Criteria criteria){
		this.addCriteria(beanMap, mapKey, dbKey, logical, criteria, null);
	}
	
	protected void addCriteria(Map<String, Object> beanMap, String mapKey, String dbKey, Logical logical, Criteria criteria, Class joinClass){
		Object obj = beanMap.get(mapKey);
		Object value = null;
		if (obj != null){
			if (obj instanceof String){
				value = (String)obj;
			}else if (obj instanceof ComboBoxItem){
				value = (String)((ComboBoxItem)obj).getItem();
			}else {
				value = obj;
			}
		}

		if (StringUtils.isNotEmpty(value)){
			if (criteria.criteria.size() > 0){
				criteria.addLogical(Logical.AND);
			}
			if (!Logical.LIKE.equals(logical)){
				criteria.addFilter(joinClass, dbKey, beanMap.get(mapKey), logical);	
			}else{
				criteria.addFilter(joinClass, dbKey, "%"+beanMap.get(mapKey)+"%", logical);
			}
		}
	}
}
