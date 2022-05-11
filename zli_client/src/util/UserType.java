package util;

import java.util.ArrayList;
import java.util.Arrays;

import entities.User;

public enum UserType {
	CUSTOMER, STORE_WORKER, STORE_MANGER, DELIVERY_COORDINATOR, CUSTOMER_SERVICE, UNDEFINED, CEO, MARKETING_WORKER,
	SERVICE_EXPERT;

	public static UserType get(String type) {
		String upperCaseType = type.toUpperCase();
		System.out.println(upperCaseType);
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
		}
		return UNDEFINED;
	}

}
