package com.dbs.portal.database.to.subscription;

import java.util.Date;
import java.util.List;

public class SubscriptionInfo extends ExportRequestInfo {
	// Subscription ID
	private Long id;
	private String subscriptionName;
	private String remarks;
	private Date createdDateTime;

	// Job Recipients
	private List<String> recipients;

	// Job Schedules (children)
	private List<ScheduleInfo> schedules;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the subscriptionName
	 */
	public String getSubscriptionName() {
		return subscriptionName;
	}

	/**
	 * @param subscriptionName the subscriptionName to set
	 */
	public void setSubscriptionName(String subscriptionName) {
		this.subscriptionName = subscriptionName;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the createdDateTime
	 */
	public Date getCreatedDateTime() {
		return createdDateTime;
	}

	/**
	 * @param createdDateTime the createdDateTime to set
	 */
	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	/**
	 * @return the recipients
	 */
	public List<String> getRecipients() {
		return recipients;
	}

	/**
	 * @param recipients the recipients to set
	 */
	public void setRecipients(List<String> recipients) {
		this.recipients = recipients;
	}

	/**
	 * @return the schedules
	 */
	public List<ScheduleInfo> getSchedules() {
		return schedules;
	}

	/**
	 * @param schedules the schedules to set
	 */
	public void setSchedules(List<ScheduleInfo> schedules) {
		this.schedules = schedules;
	}

}
