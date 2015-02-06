package com.dbs.portal.ui.validator;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.dbs.portal.ui.component.view.IController;
import com.dbs.portal.ui.component.view.IEnquiryView;
import com.dbs.portal.ui.component.view.IInit;
import com.dbs.portal.ui.component.view.IWindow;
import com.vaadin.data.validator.AbstractStringValidator;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.DateField;

public class DateRangeValidator  extends AbstractStringValidator implements IInit{
	
	private List<String> dateList = null;
	private String enquiryViewName = null;
	private int difference = 1;
	private int calendarType = Calendar.MONTH;
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
	
	public DateRangeValidator(String errorMessage) {
		super(errorMessage);
	}
	
	public void setDateField(List<String> dateList){
		this.dateList = dateList;
	}
	
	public void setEnquiryViewName(String enquiryViewName){
		this.enquiryViewName = enquiryViewName;
	}
	
	public void setDifferenceType(int calendarType){
		this.calendarType = calendarType;
	}
	public void setDifference(int difference){
		this.difference = difference;
	}
	
	@Override
	protected boolean isValidString(String value) {
//		if (dateList != null && dateList.size() !=2){
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
			}else if (fromDate == null || toDate == null){
				return true; 
			}
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fromDate);
			calendar.add(calendarType, difference);
			if (!toDate.after(calendar.getTime())){
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
	public void setControl(IController control) {
		// TODO Auto-generated method stub
		
	}

}
