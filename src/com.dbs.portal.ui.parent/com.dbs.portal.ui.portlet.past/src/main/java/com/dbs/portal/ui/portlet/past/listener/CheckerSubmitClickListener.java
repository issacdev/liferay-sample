package com.dbs.portal.ui.portlet.past.listener;

import com.dbs.past.db.bean.UserConfig;
import com.dbs.portal.ui.component.application.IApplication;
import com.dbs.portal.ui.component.data.TableDBDataProvider;
import com.dbs.portal.ui.component.view.IController;
import com.dbs.portal.ui.component.view.IInit;
import com.dbs.portal.ui.component.view.ITableResultView;
import com.dbs.portal.ui.component.view.IWindow;
import com.dbs.portal.ui.portlet.past.control.CheckerController;
import com.dbs.portal.ui.util.LanguageChanger;
import com.dbs.portal.ui.util.Messages;
import com.vaadin.data.Container;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window;

public class CheckerSubmitClickListener implements ClickListener, IInit {

	private IWindow view;
	private IController control;
	
	@Override
	public void setView(IWindow view) {
		this.view = view;
	}

	@Override
	public void setControl(IController control) {
		this.control = control;
	}

	@Override
	public void buttonClick(ClickEvent event) {
		
		CheckerController checkerController = (CheckerController)control;
		
		UserConfig userConfig = checkerController.getUserConfig();

		checkerController.updateRecordStatus(userConfig.getDataType());
		checkerController.refreshtable();
		
		ITableResultView resultView = (ITableResultView) view.getView(userConfig.getTableView());
		resultView.setVisible(false);
		
		checkerController.showMessage("past.checker.submit.success");
		
		((Window) view).removeWindow(event.getButton().getWindow());
		((CheckerController) control).init();
		
	}
}
