package com.dbs.portal.ui.portlet.past.listener;

import com.dbs.past.db.bean.UserConfig;
import com.dbs.portal.ui.component.view.IController;
import com.dbs.portal.ui.component.view.IInit;
import com.dbs.portal.ui.component.view.ITableResultView;
import com.dbs.portal.ui.component.view.IWindow;
import com.dbs.portal.ui.portlet.past.control.DoaApproverController;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window;

public class DoaApproverSubmitClickListener implements ClickListener, IInit {

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
		
		DoaApproverController controller = (DoaApproverController)control;	
		UserConfig userConfig = controller.getUserConfig();
		
		controller.updateRecordStatus(userConfig.getDataType());
		controller.resetTableView();
		
		ITableResultView resultView = (ITableResultView) view.getView(userConfig.getTableView());
		resultView.setVisible(false);
		((Window) view).removeWindow(event.getButton().getWindow());
		((DoaApproverController) control).init();		
	}
}