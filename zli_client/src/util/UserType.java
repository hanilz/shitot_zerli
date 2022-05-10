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

	public static ArrayList<String[]> get(UserType type) {//return matched GUI for user type
		ArrayList<String[]> result = new ArrayList<>();
		String[] buttonsNames = null;
		String[] fxmls = null;
		String[] imagePath = null;
		switch (User.getUserInstance().getType()) {
		case CUSTOMER: {
			buttonsNames = new String[] { "catalog" };
			fxmls = new String[] { "../catalog/CatalogScreen.fxml" };
			imagePath = new String[] { "resources/home/store.png" };
			break;
		}
		case STORE_WORKER: {
			buttonsNames = new String[] { "catalog" };
			fxmls = new String[] { "../catalog/CatalogScreen.fxml" };
			imagePath = new String[] { "resources/home/store.png" };
			break;
		}
		case DELIVERY_COORDINATOR: {
			buttonsNames = new String[] { "" };
			fxmls = new String[] { "" };
			imagePath = new String[] { "" };
			break;
		}
		case STORE_MANGER: {
			buttonsNames = new String[] { "" };
			fxmls = new String[] { "" };
			imagePath = new String[] { "" };
			break;
		}
		case MARKETING_WORKER: {
			buttonsNames = new String[] { "" };
			fxmls = new String[] { "" };
			imagePath = new String[] { "" };
			break;
		}
		case CUSTOMER_SERVICE: {
			buttonsNames = new String[] { "" };
			fxmls = new String[] { "" };
			imagePath = new String[] { "" };
			break;
		}
		case SERVICE_EXPERT: {
			buttonsNames = new String[] { "" };
			fxmls = new String[] { "" };
			imagePath = new String[] { "" };
			break;
		}
		case CEO: {
			buttonsNames = new String[] { "" };
			fxmls = new String[] { "" };
			imagePath = new String[] { "" };
			break;
		}
		}
		result.add(buttonsNames);
		result.add(fxmls);
		result.add(imagePath);
		return result;
	}
}
