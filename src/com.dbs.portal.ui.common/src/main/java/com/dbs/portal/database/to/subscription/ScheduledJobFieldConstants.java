package com.dbs.portal.database.to.subscription;


public interface ScheduledJobFieldConstants {
	// Dependent Services
	public static final String SECURITY_SVC = "securityService";
	public static final String PREFERENCES_SVC = "preferencesService";
	public static final String SUBSCRIPTION_SVC = "subscriptionService";

	// Method Invocation Maps
	public static final String FUNC_ID_TARGET_OBJ_MAP = "functionIdTargetObjectMap";
	public static final String FUNC_ID_TARGET_MTD_MAP = "functionIdTargetMethodMap";

	// Preprocessor Map
	public static final String FUNC_ID_PREPROCESSOR_MAP = "functionIdPreprocessorMap";

	// Subscription Information
	public static final String SUBSCRIPTION = "subscription";

	// Distribution
	public static final String MAIL_SENDER = "mailSender";
	public static final String SUBS_MAIL_MSG = "subscriptionMailMessages";

	// Generation
	public static final String SUBS_REPORT_PATH = "reportPath";

	// Result
	public static final String FILENAME = "filename";
	public static final String STATUS = "status";
}
