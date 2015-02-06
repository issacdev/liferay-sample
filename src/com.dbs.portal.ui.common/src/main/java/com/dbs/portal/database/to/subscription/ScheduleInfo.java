package com.dbs.portal.database.to.subscription;

public class ScheduleInfo {

	private Long subscriptionId;
	private String scheduleId;
	private Frequency frequency;
	private int dayOfMonthOrWeek;
	private int hour;
	private int minute;
	private boolean lastDayOfMonth;
	
	/**
	 * @return the subscriptionId
	 */
	public Long getSubscriptionId() {
		return subscriptionId;
	}
	/**
	 * @param subscriptionId the subscriptionId to set
	 */
	public void setSubscriptionId(Long subscriptionId) {
		this.subscriptionId = subscriptionId;
	}
	/**
	 * @return the scheduleId
	 */
	public String getScheduleId() {
		return scheduleId;
	}
	/**
	 * @param scheduleId the scheduleId to set
	 */
	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}
	/**
	 * @return the frequency
	 */
	public Frequency getFrequency() {
		return frequency;
	}
	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}
	/**
	 * @return the dayOfMonthOrWeek
	 */
	public int getDayOfMonthOrWeek() {
		return dayOfMonthOrWeek;
	}
	/**
	 * @param dayOfMonthOrWeek the dayOfMonthOrWeek to set
	 */
	public void setDayOfMonthOrWeek(int dayOfMonthOrWeek) {
		this.dayOfMonthOrWeek = dayOfMonthOrWeek;
	}
	/**
	 * @return the hour
	 */
	public int getHour() {
		return hour;
	}
	/**
	 * @param hour the hour to set
	 */
	public void setHour(int hour) {
		this.hour = hour;
	}
	/**
	 * @return the minute
	 */
	public int getMinute() {
		return minute;
	}
	/**
	 * @param minute the minute to set
	 */
	public void setMinute(int minute) {
		this.minute = minute;
	}
	/**
	 * @return the lastDayOfMonth
	 */
	public boolean isLastDayOfMonth() {
		return lastDayOfMonth;
	}
	/**
	 * @param lastDayOfMonth the lastDayOfMonth to set
	 */
	public void setLastDayOfMonth(boolean lastDayOfMonth) {
		this.lastDayOfMonth = lastDayOfMonth;
	}

}
