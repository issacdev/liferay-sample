package com.dbs.portal.database.constants;

public class FASTConstant {

	// user group
	public static final String FAST_ADMIN_GROUP = "FAST_ADMIN_GROUP";
	public static final String FAST_MAKER_GROUP = "FAST_MAKER_GROUP";
	public static final String FAST_CHECKER_GROUP = "FAST_CHECKER_GROUP";

	// record status
	public static final String STATUS_ACTIVE = "A";
	public static final String STATUS_PENDING_ACTIVE = "B";
	public static final String STATUS_CREATE = "C";
	public static final String STATUS_PENDING_CREATE = "D";
	public static final String STATUS_INACTIVE = "E";
	public static final String STATUS_PENDING_INACTIVE = "F";
	public static final String STATUS_UPDATE = "G";
	public static final String STATUS_PENDING_UPDATE = "H";
	public static final String STATUS_DELETE = "I";
	public static final String STATUS_PENDING_DELETE = "J";

	public static final String STATUS_YES = "Y";
	public static final String STATUS_NO = "N";

	public static final String DEFAULT = "default";

	public static final String NAME = "NAME";
	public static final String PATH = "path";
	public static final String VERSION = "VERSION";
	public static final String ACTION = "ACTION";

	public static final String STATUS_CODE = "STATUS_CODE";
	public static final String STATUS_NAME = "STATUS_NAME";

	public static final String SEQUENCE_NAME = "sequenceName";
	public static final String SEQUENCE_VALUE = "SEQUENCE_VALUE";

	public static final String NEW_STATUS = "FAST_NEW_STATUS";

	public static final String DESCRIPTION = "description";
	public static final String STATUS = "status";
	public static final String STATUS_DESCRIPTION = "status_description";
	public static final String SETTING_STATUS = "SETTING_STATUS";
	public static final String CREATED_BY = "CREATED_BY";
	public static final String CREATED_DATE_TIME = "CREATED_DATE_TIME";
	public static final String UPDATED_BY = "UPDATED_BY";
	public static final String UPDATED_DATE_TIME = "UPDATED_DATE_TIME";
	public static final String LAST_MAKER = "LAST_MAKER";
	public static final String MAKER_DATE_TIME = "MAKER_DATE_TIME";
	public static final String LAST_CHECKER = "LAST_CHECKER";
	public static final String CHECKER_DATE_TIME = "CHECKER_DATE_TIME";

	// Key Map with DataBase
	public static final String SEQUENCE = "seq";
	public static final String SYSCODE = "sysCode";
	public static final String CONTENT = "FAST_CONTENT";
	public static final String CODE = "code";
	public static final String CODE_DISPLAY = "code_display";
	public static final String ID = "id";
	public static final String FKEY = "fkey";
	public static final String VALUE = "value";
	public static final String VALUE_ORIGINAL = "FAST_VALUE_ORIGINAL";
	public static final String KEY_DAY = "FAST_KEY_DAY";
	public static final String KEY_MAP = "FAST_KEY_MAP";
	public static final String TYPE = "type";
	public static final String FILE_ID = "FAST_FILE_ID";
	public static final String FILE_NAME = "fileName";
	public static final String FILE_PATH = "FAST_FILE_PATH";
	public static final String FILE_LOCATION = "FAST_FILE_LOCATION";
	public static final String FILE_DATE_TIME = "FAST_FILE_DATE_TIME";
	public static final String FILE_YEAR = "FAST_FILE_YEAR";
	public static final String FILE_MONTH = "FAST_FILE_MONTH";
	public static final String FILE_IMPORT_METHOD = "FAST_FILE_IMPORT_METHOD";
	public static final String FILE_IMPORT_BY = "FAST_FILE_IMPORT_BY";
	public static final String FILE_LINE_NUMBER = "FAST_FILE_LINE_NUMBER";
	public static final String FILE_DETAILS = "FAST_FILE_DETAILS";
	public static final String EXCHANGE_RATE = "exchange_rate";
	public static final String VALIDATE = "validate";
	public static final String DEFINITION = "definition";
	public static final String MODULE_CODE = "moduleCode";
	public static final String EMAIL_ADDRESS = "emailAddress";
	public static final String MERCHANT_CODE = "merchantCode";
	public static final String CCY_CODE = "ccyCode";
	public static final String ORG_CCY_CODE = "ORG_CCY_CODE";
	public static final String ORG_VALUE = "ORG_VALUE";
	public static final String CARD_CODE = "cardCode";
	public static final String METHOD_CODE = "methodCode";
	public static final String ORG_METHOD_CODE = "ORG_METHOD_CODE";

	// type of key
	public static final String FAST_KEY_STATUS = "STATUS_KEY";
	public static final String FAST_KEY_SYSTEM = "SYSTEM_KEY";
	public static final String FAST_KEY_MODULE = "MODULE_KEY";
	public static final String FAST_KEY_METHOD = "METHOD_KEY";
	public static final String FAST_KEY_FILE = "FILE_KEY";
	public static final String FAST_KEY_CARD = "CARD_KEY";
	public static final String FAST_KEY_MERCHANT = "MERCHANT_KEY";
	public static final String FAST_KEY_TRAN = "TRAN_CODE_KEY";
	public static final String FAST_KEY_CURRENCY = "CURRENCY_KEY";

	// system setting key
	public static final String MANUAL_BATCH = "manual.batch";
	public static final String MANUAL_BATCH_DATE = "manual.batch.lastDate";
	public static final String MANUAL_FILE_UPLOAD = "manual.file.upload";
	public static final String MANUAL_FILE_UPLOAD_DATE = "manual.file.upload.lastDate";
	public static final String SYSTEM_BATCH = "sys.batch";
	public static final String SYSTEM_BATCH_DATE = "sys.batch.lastDate";
	public static final String SYSTEM_FILE_UPLOAD = "sys.file.import";
	public static final String SYSTEM_FILE_UPLOAD_DATE = "sys.file.import.lastDate";
	public static final String SYSTEM_PSGL_FILE_GENERATE = "sys.psgl.file.generate";
	public static final String SYSTEM_PSGL_FILE_GENERATE_DATE = "sys.psgl.file.generate.lastDate";

	public static final String CURRENCY_EXCHANGE_RATE = "currency.exchange_rate";
	public static final String MERCHANT_MAPPING_KEY = "merchant.mapping.key";
	public static final String FILE_FILE_NAME = "file.file_name";

	// key validate
	public static final String FAST_VALIDATE_CHAR = "CHAR";
	public static final String FAST_VALIDATE_STRING = "STRING";
	public static final String FAST_VALIDATE_YESNO = "YESNO";
	public static final String FAST_VALIDATE_DATETIME = "DATETIME";
	public static final String FAST_VALIDATE_EMAIL = "EMAIL";
	public static final String FAST_VALIDATE_DOUBLE = "DOUBLE";

	// table for sequence
	public static final String FAST_KEY = "fast_key";
	public static final String SYSTEM_MST = "system_mst";
	public static final String SYSTEM_SETTING = "system_settting";
	public static final String FAST_CURRENCY_MST = "fast_currency_mst";
	public static final String FAST_CURRENCY_SETTING = "fast_currency_setting";
	public static final String FAST_CURRENCY_SETTING_HIST = "fast_currency_setting_hist";
	public static final String FAST_CARD_MST = "fast_card_mst";
	public static final String FAST_CARD_SETTING = "fast_card_setting";
	public static final String FAST_CARD_SETTING_HIST = "fast_card_setting_hist";
	public static final String FAST_FILE_MST = "fast_file_mst";
	public static final String FAST_FILE_SETTING = "fast_file_setting";
	public static final String FAST_FILE_SETTING_HIST = "fast_file_setting_hist";
	public static final String FAST_MERCHANT_MST = "fast_merchant_mst";
	public static final String FAST_MERCHANT_SETTING = "fast_merchant_setting";
	public static final String FAST_MERCHANT_SETTING_HIST = "fast_merchant_setting_hist";
	public static final String FAST_MODULE_MST = "fast_module_mst";
	public static final String FAST_MODULE_SETTING = "fast_module_setting";
	public static final String FAST_MODULE_SETTING_HIST = "fast_module_setting_hist";
	public static final String FAST_METHOD_MST = "fast_method_mst";
	public static final String FAST_METHOD_SETTING = "fast_method_setting";
	public static final String FAST_METHOD_SETTING_HIST = "fast_method_setting_hist";
	public static final String FAST_TRANCODE_MST = "fast_tranCode_mst";
	public static final String FAST_TRANCODE_SETTING = "fast_tranCode_setting";
	public static final String FAST_TRANCODE_SETTING_HIST = "fast_trancode_setting_hist";

}
