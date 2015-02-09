package com.dbs.portal.ui.portlet.sample.example.control;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dbs.db.filter.Criteria;
import com.dbs.db.filter.Logical;
import com.dbs.portal.database.constants.SampleConstant;
import com.dbs.portal.ui.component.application.IApplication;
import com.dbs.portal.ui.component.data.TableDBDataProvider;
import com.dbs.portal.ui.component.view.BaseController;
import com.dbs.portal.ui.component.view.ITableResultView;
import com.dbs.portal.ui.component.view.IWindow;
import com.dbs.sample.db.bean.Sample;
import com.dbs.sample.db.service.ISampleService;
import com.dbs.sample.db.service.impl.SampleServiceImpl;
import com.liferay.portal.model.User;
import com.vaadin.ui.Window;

public class SampleController extends BaseController {

	private Logger logger = Logger.getLogger(this.getClass());

	private ISampleService sampleService = new SampleServiceImpl();

	public IWindow getView() {
		return view;
	}

	@Override
	public void init() {
		super.init();
		refreshTable();
	}

	public void refreshTable() {
		try {
			List<Sample> sampleList = this.sampleService.getAll();
			if (sampleList == null || sampleList.size() == 0) {
				hasResult(false);
			} else {
				List<Map<String, Object>> resultList = sampleService.beanToMap(sampleList);

				ITableResultView resultView = (ITableResultView) view.getView("RecordView");
				TableDBDataProvider mainTableDataProvider = new TableDBDataProvider();
				mainTableDataProvider.setApplication((IApplication) ((Window) view).getApplication());
				mainTableDataProvider.setData(resultList);
				mainTableDataProvider.setDataColumnList(resultView.getVisibleColumnHeader());
				mainTableDataProvider.setPagedTableParameterMap(resultView.getPagedTableParameterMap());
				resultView.updateContent(mainTableDataProvider.getDataContainer());

				hasResult(true);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			hasResult(false);
		}
	}

	public void hasResult(boolean hasResult) {
		view.getView("RecordView").setVisible(hasResult);
		view.getView("NoResultView").setVisible(!hasResult);
	}

	public List<Map<String, Object>> selectSample(Map<String, Object> beanMap) throws Exception {
		Criteria criteria = new Criteria();
		if (beanMap.get(SampleConstant.ID) != null) {
			beanMap.put(SampleConstant.ID, Integer.parseInt((String) beanMap.get(SampleConstant.ID)));
			this.addCriteria(beanMap, SampleConstant.ID, SampleConstant.ID, Logical.EQUAL, criteria);
		}
		if (beanMap.get(SampleConstant.SKEY) != null) {
			this.addCriteria(beanMap, SampleConstant.SKEY, SampleConstant.SKEY, Logical.EQUAL, criteria);
		}
		if (beanMap.get(SampleConstant.VALUE) != null) {
			this.addCriteria(beanMap, SampleConstant.VALUE, SampleConstant.VALUE, Logical.EQUAL, criteria);
		}
		if (beanMap.get(SampleConstant.FROM_DATE) != null && beanMap.get(SampleConstant.TO_DATE) != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			Date fromDate = sdf.parse((String) beanMap.get(SampleConstant.FROM_DATE));

			Calendar c = Calendar.getInstance();
			c.setTime(sdf.parse((String) beanMap.get(SampleConstant.TO_DATE)));
			c.add(Calendar.DATE, 1);
			Date toDate = c.getTime();

			beanMap.put(SampleConstant.FROM_DATE_TIME, fromDate);
			beanMap.put(SampleConstant.TO_DATE_TIME, toDate);

			this.addCriteria(beanMap, SampleConstant.FROM_DATE_TIME, SampleConstant.UPDATED_DATE_TIME, Logical.GREATER_EQUAL_THAN, criteria);
			this.addCriteria(beanMap, SampleConstant.TO_DATE_TIME, SampleConstant.UPDATED_DATE_TIME, Logical.LESS_EQUAL_THAN, criteria);
		}
		criteria.addSorting(SampleConstant.SKEY, Logical.DECENDING);
		List<Sample> result = this.sampleService.findByCriteria(criteria);
		if (result != null && result.size() > 0) {
			return this.sampleService.beanToMap(result);
		} else {
			return null;
		}
	}

	public Boolean createSample(Map<String, Object> beanMap) throws Exception {
		Sample sample = this.getSampleService().mapToBean(beanMap, true);
		User user = (User) ((IApplication) ((Window) getView()).getApplication()).getCurrentUser();
		if (user != null) {
			sample.setUpdatedBy(user.getScreenName());
		}
		sample.setUpdatedDateTime(new Date());
		return this.getSampleService().create(sample);
	}

	public Boolean editSample(Map<String, Object> beanMap) throws Exception {
		Sample sample = this.getSampleService().mapToBean(beanMap, false);
		User user = (User) ((IApplication) ((Window) getView()).getApplication()).getCurrentUser();
		if (user != null) {
			sample.setUpdatedBy(user.getScreenName());
		}
		sample.setUpdatedDateTime(new Date());
		return this.getSampleService().update(sample);
	}

	public Boolean deleteSample(Map<String, Object> beanMap) throws Exception {
		Sample sample = this.getSampleService().mapToBean(beanMap, false);
		User user = (User) ((IApplication) ((Window) getView()).getApplication()).getCurrentUser();
		if (user != null) {
			sample.setUpdatedBy(user.getScreenName());
		}
		sample.setUpdatedDateTime(new Date());
		return this.getSampleService().update(sample) && this.getSampleService().delete(sample);
	}

	public ISampleService getSampleService() {
		return sampleService;
	}

}
