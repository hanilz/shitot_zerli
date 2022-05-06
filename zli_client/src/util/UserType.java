package util;

public enum UserType {
	CUSTOMER, STORE_WORKER, MANGER, DELIVERY_COORDINATOR, CUSTOMER_SERVICE,UNDEFINED;
	
	public static UserType get(String type) {
		String upperCaseType=type.toUpperCase();
		System.out.println(upperCaseType);
		switch (upperCaseType) {
		case "CUSTOMER":
			return CUSTOMER;
		case "STORE_WORKER":
			return STORE_WORKER;
		case "MANGER":
			return MANGER;
		case "DELIVERY_COORDINATOR":
			return DELIVERY_COORDINATOR;
		case "CUSTOMER_SERVICE":
			return CUSTOMER_SERVICE;
		}
		return UNDEFINED;
	}

}
