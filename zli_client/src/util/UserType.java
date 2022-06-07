package util;

/**
 *Types of user in the system 
 */
public enum UserType {
	CUSTOMER, STORE_WORKER, STORE_MANGER, DELIVERY_COORDINATOR, CUSTOMER_SERVICE, UNDEFINED, CEO, MARKETING_WORKER,
	SERVICE_EXPERT, NEW_CUSTOMER;

	/**
	 * @return name of the user type
	 */
	public static UserType get(String type) {
		String upperCaseType = type.toUpperCase();
		switch (upperCaseType) {
		case "CUSTOMER":
			return CUSTOMER;
		case "STORE_WORKER":
			return STORE_WORKER;
		case "STORE_MANGER":
			return STORE_MANGER;
		case "DELIVERY_COORDINATOR":
			return DELIVERY_COORDINATOR;
		case "CUSTOMER_SERVICE":
			return CUSTOMER_SERVICE;
		case "MARKETING_WORKER":
			return MARKETING_WORKER;
		case "SERVICE_EXPERT":
			return SERVICE_EXPERT;
		case "CEO":
			return CEO;
		case "NEW_CUSTOMER":
			return NEW_CUSTOMER;
		}
		return UNDEFINED;
	}

}
