package com.dbs.portal.ui.validator;

import java.util.List;

import com.dbs.portal.ui.component.comboBox.ComboBoxItem;
import com.dbs.portal.ui.component.view.IController;
import com.dbs.portal.ui.component.view.IEnquiryView;
import com.dbs.portal.ui.component.view.IInit;
import com.dbs.portal.ui.component.view.IWindow;
import com.dbs.portal.ui.util.LanguageSwitcher;
import com.vaadin.data.validator.AbstractStringValidator;
import com.vaadin.ui.AbstractSelect;

public class VesselVoyageValidator extends AbstractStringValidator implements IInit{

	private List<String> dataidList = null;
	private IWindow view = null;
	private String enquiryViewName = "";
	
	public VesselVoyageValidator(String errorMessage) {
		super(errorMessage);
//		LanguageSwitcher.getLanguageSwitcher().changeCode(this);
	}

	public void setdataIdList(List<String> dataIdList){
		dataidList = dataIdList;
	}
	
	@Override
	protected boolean isValidString(String value) {
		AbstractSelect vesselNameField = (AbstractSelect)((IEnquiryView)view.getView(enquiryViewName)).getComponent(dataidList.get(0));
		AbstractSelect voyageNoField = (AbstractSelect)((IEnquiryView)view.getView(enquiryViewName)).getComponent(dataidList.get(1));
		
		if (vesselNameField.getValue() == null && voyageNoField.getValue() == null)
			return true;
		else{
			ComboBoxItem vesselItem = (ComboBoxItem)vesselNameField.getValue();
			ComboBoxItem voyageItem = (ComboBoxItem)voyageNoField.getValue();
			
			if (vesselItem != null && voyageItem != null){
				if (vesselItem.getItem() != null && voyageItem.getItem() != null)
					return true;
			}
		}
		return false;
	}

	@Override
	public void setView(IWindow view) {
		this.view = view;
	}

	@Override
	public void setControl(IController control) {}
	
	public void setEnquiryViewName(String name){
		this.enquiryViewName = name;
	}
}
