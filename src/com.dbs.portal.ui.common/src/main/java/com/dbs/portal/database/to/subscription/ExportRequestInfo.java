package com.dbs.portal.database.to.subscription;

import java.util.Map;

public class ExportRequestInfo {

	// User ID
	private String userId;

	// Service Call ID
	private String serviceCallId;
	
	// Descriptive Details
	private Long organizationId;
	private String organization;
	
	// Job Details
	private Map<String, Object> criteriaMap;
	private Map<String, String> tableKeyMap;
	private Map<String, String> criteriaDisplayMap;
	private Map<String, Object> resultKeyMap;

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the serviceCallId
	 */
	public String getServiceCallId() {
		return serviceCallId;
	}

	/**
	 * @param serviceCallId the serviceCallId to set
	 */
	public void setServiceCallId(String serviceCallId) {
		this.serviceCallId = serviceCallId;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	/**
	 * @return the organization
	 */
	public String getOrganization() {
		return organization;
	}

	/**
	 * @param organization the organization to set
	 */
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	
	/**
	 * @return the tableKeyMap
	 */
	public Map<String, String> getTableKeyMap() {
		return tableKeyMap;
	}

	/**
	 * @param tableKeyMap the tableKeyMap to set
	 */
	public void setTableKeyMap(Map<String, String> tableKeyMap) {
		this.tableKeyMap = tableKeyMap;
	}

	/**
	 * @return the criteriaMap
	 */
	public Map<String, Object> getCriteriaMap() {
		return criteriaMap;
	}

	/**
	 * @param criteriaMap the criteriaMap to set
	 */
	public void setCriteriaMap(Map<String, Object> criteriaMap) {
		this.criteriaMap = criteriaMap;
	}

	/**
	 * @return the criteriaDisplayMap
	 */
	public Map<String, String> getCriteriaDisplayMap() {
		return criteriaDisplayMap;
	}

	/**
	 * @param criteriaDisplayMap the criteriaDisplayMap to set
	 */
	public void setCriteriaDisplayMap(Map<String, String> criteriaDisplayMap) {
		this.criteriaDisplayMap = criteriaDisplayMap;
	}

	/**
	 * @return the resultKeyMap
	 */
	public Map<String, Object> getResultKeyMap() {
		return resultKeyMap;
	}

	/**
	 * @param resultKeyMap the resultKeyMap to set
	 */
	public void setResultKeyMap(Map<String, Object> resultKeyMap) {
		this.resultKeyMap = resultKeyMap;
	}

}
