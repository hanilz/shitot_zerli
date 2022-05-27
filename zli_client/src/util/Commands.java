package util;

public enum Commands {

	FETCH_ORDERS, FETCH_PRODUCTS, FETCH_BRANCHES, FETCH_ITEMS, FETCH_ALL_USERS_DETAILS, FETCH_SURVEYS,
	SERVER_DISCONNEDTED, CLIENT_DISCONNECTED, LOGOUT, LOGIN, CHANGE_USER_STATUS, INSERT_ACCOUNT_PAYMENT, INSERT_ORDER,
	INSERT_ORDERS_PRODUCT, INSERT_DELIVERY, INSERT_DELIVERY_ORDER, GET_SERVEY, SUBMIT_SURVEY, SUBMIT_COMPLAINT,
	INSERT_ORDERS_ITEMS, UPDATE_PRODUCTS_BASE, GET_ORDER_NUMBERS, FETCH_COMPLAINTS, CLOSE_COMPLAINT, GET_ORDER_SUM,
	NEW_USER_DETAILS, NEW_USERS, DELETE_USER_DETAILS, FETCH_ORDERS_MANAGER, APPROVE_ORDER, CANCEL_ORDER,
	FETCH_NOTIFICATIONS, INSERT_CUSTOM_PRODUCTS, INSERT_CUSTOM_PRODUCT_PRODUCTS, INSERT_CUSTOM_PRODUCT_ITEMS,
	INSERT_ORDER_CUSTOM_PRODUCTS, SEND_NOTIFICATION, GET_ALL_COMPLAINTS, UPDATE_COMPLAINTS_STATUS,
	MARK_READ_NOTIFICATION, DELETE_NOTIFICATION, FETCH_BRANCHES_PER_MANAGER, FETCH_REPORTS, GET_SURVEY_ANSWERS,
	GET_ORDERS_REPORT, GET_ITEMS_INCOME_REPORT, UPLOAD_FILE, FETCH_FILES, GET_PRODUCTS_INCOME_REPORT,
	GET_ITEMS_ORDERS_REPORT, GET_PRODUCTS_ORDERS_REPORT;
}
