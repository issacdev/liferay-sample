package com.dbs.portal.ui.portlet.fast.adminOnly.control;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dbs.fast.db.bean.FastConfig;
import com.dbs.fast.db.service.IFastConfigService;
import com.dbs.fast.db.service.impl.FastConfigServiceImpl;
import com.dbs.portal.database.constants.FASTConstant;
import com.dbs.portal.ui.component.application.IApplication;
import com.dbs.portal.ui.component.data.TableDBDataProvider;
import com.dbs.portal.ui.component.view.BaseController;
import com.dbs.portal.ui.component.view.ITableResultView;
import com.dbs.portal.ui.component.view.IWindow;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.vaadin.ui.Window;

public class AdminOnlyController extends BaseController {

	private Logger logger = Logger.getLogger(this.getClass());

	private IFastConfigService fastConfigService = new FastConfigServiceImpl();

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
			List<FastConfig> configList = this.fastConfigService.getAll();
			if (configList == null || configList.size() == 0) {
				hasResult(false);
			} else {
				List<Map<String, Object>> resultList = fastConfigService
						.beanToMap(configList);

				ITableResultView resultView = (ITableResultView) view
						.getView("ConfigRecordView");
				TableDBDataProvider mainTableDataProvider = new TableDBDataProvider();
				mainTableDataProvider
						.setApplication((IApplication) ((Window) view)
								.getApplication());
				mainTableDataProvider.setData(resultList);
				mainTableDataProvider.setDataColumnList(resultView
						.getVisibleColumnHeader());
				mainTableDataProvider.setPagedTableParameterMap(resultView
						.getPagedTableParameterMap());
				resultView.updateContent(mainTableDataProvider
						.getDataContainer());

				hasResult(true);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			hasResult(false);
		}
	}

	public void hasResult(boolean hasResult) {
		view.getView("ConfigRecordView").setVisible(hasResult);
		view.getView("NoResultView").setVisible(!hasResult);
	}

	public Boolean editConfig(Map<String, Object> beanMap) throws Exception {

		FastConfig fastConfig = this.getFastConfigService().mapToBean(beanMap,
				false);

		User user = (User) ((IApplication) ((Window) getView())
				.getApplication()).getCurrentUser();
		List<UserGroup> groupList = user.getUserGroups();
		boolean check = Boolean.FALSE;
		for (UserGroup group : groupList) {
			if (FASTConstant.FAST_ADMIN_GROUP.equals(group.getName())) {
				check = Boolean.TRUE;
			}
		}
		if (user != null) {
			fastConfig.setUpdatedBy(user.getScreenName());
		}
		fastConfig.setUpdatedDateTime(new Date());

		return this.getFastConfigService().update(fastConfig);
	}

	public IFastConfigService getFastConfigService() {
		return fastConfigService;
	}
}
