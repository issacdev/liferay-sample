package com.dbs.portal.ui.validator;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.dbs.portal.ui.component.view.IController;
import com.dbs.portal.ui.component.view.IEnquiryView;
import com.dbs.portal.ui.component.view.IInit;
import com.dbs.portal.ui.component.view.IWindow;
import com.vaadin.data.validator.AbstractStringValidator;
import com.vaadin.ui.DateField;

public class DateWithinRangeValidator  extends AbstractStringValidator implements IInit{
	
	private List<String> dateList = null;
	private String enquiryViewName = null;
	
	private int minDateDifference = -1;
	private int minDateDifferenceType = Calendar.MONTH;
	
	private int maxDateDifference = 0;
	private int maxDateDifferenceType = Calendar.MONTH;

	private IWindow view;
	/*
	 * <bean id="dateRangeValidator" class="com.dbs.portal.ui.validator.DateRangeValidator">
	 * 		<constructor-arg name="errorMessage" value="{fieldNameKey}"/>
	 * 		<property name="dateField">
	 * 			<list>
	 * 				{fromDate Data Id}
	 * 				{toDate data id}
	 * 			</list>
	 * 		</property>
	 * 		<property name="enquiryViewName" value="{enquiryViewNameInBaseWindow}"/>
	 * 		<property name="differenceType">
	 * 			<util:constant static-field="java.util.Calendar.MONTH"/>
	 * 		</property>
	 * 		<property name="difference">
	 * 			<value="{no of difference Type (1 day, 1 month....)}" type="java.lang.Integer"/>
	 * 		</property>
	 * </bean>
	 */
	
	public DateWithinRangeValidator(String errorMessage) {
		super(errorMessage);
	}
	
	public void setDateField(List<String> dateList){
		this.dateList = dateList;
	}
	
	public void setEnquiryViewName(String enquiryViewName){
		this.enquiryViewName = enquiryViewName;
	}
	
	@Override
	protected boolean isValidString(String value) {
		if (dateList.size() == 2){
			IEnquiryView enquiryView = (IEnquiryView)view.getView(enquiryViewName);
			DateField fromDateField = ((DateField)enquiryView.getComponent(dateList.get(0)));
			DateField toDateField = ((DateField)enquiryView.getComponent(dateList.get(1)));
			
			fromDateField.setImmediate(true);
			toDateField.setImmediate(true);
					
			Date fromDate = (Date)fromDateField.getValue();
			Date toDate = (Date)toDateField.getValue();
			
			if (fromDate == null && toDate  == null){
				return true;
			}
			
			Calendar minTime = Calendar.getInstance();
			Calendar maxTime = Calendar.getInstance();
			
			minTime.add(minDateDifferenceType, minDateDifference);
			maxTime.add(maxDateDifference, maxDateDifferenceType);
			
			//set 0 to hour and min
			minTime.set(Calendar.MINUTE, 0);
			minTime.set(Calendar.HOUR, 0);
			minTime.set(Calendar.SECOND, 0);
			
			//set 23 and 59 for max
			maxTime.set(Calendar.MINUTE, 59);
			minTime.set(Calendar.HOUR, 23);
			minTime.set(Calendar.SECOND, 59);
			
			if (fromDate != null){
				if (fromDate.before(minTime.getTime()) || fromDate.after(maxTime.getTime()))
					return false;
			}
			
			if (toDate != null){
				if (toDate.before(minTime.getTime()) || toDate.after(maxTime.getTime()))
					return false;
			}

			return true;
		}
		return false;
	}

	@Override
	public void setView(IWindow view) {
		this.view = view;
	}

	@Override
	public void setControl(IController control) {
		// TODO Auto-generated method stub
	}
	
	public void setMinDateDifference(int minDifference){
		this.minDateDifference = minDifference;
	}
	
	public void setMinDateDifferenceType(int minDateDifferenceType){
		this.minDateDifferenceType = minDateDifferenceType;
	}
	
	public void setMaxDateDifference(int maxDateDifference){
		this.maxDateDifference = maxDateDifference;
	}
	
	public void setMaxDateDifferenceType(int maxDateDifferenceType){
		this.maxDateDifferenceType = maxDateDifferenceType;
	}
}
