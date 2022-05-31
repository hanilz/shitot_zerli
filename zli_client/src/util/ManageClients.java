package util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import client.ClientFormController;
import entities.User;

public class ManageClients {
	
	public static void exitClient() {
		try {
			if (ClientFormController.client.isConnected()) {
				HashMap<String, Object> message = new HashMap<>();
				message.put("command", Commands.CLIENT_DISCONNECTED);
				if (User.getUserInstance().isUserLoggedIn()) {
					message.put("logout", User.getUserInstance().getIdUser());
					User.getUserInstance().logout();
				}
				Object response = ClientFormController.client.accept(message);
				ClientFormController.client.closeConnection();
				System.out.println("disconnected! yayyyy");
			} else {
				System.out.println("not connected to anything - babye!");
			}

		} catch (IOException ex) {
			System.out.println("Oh no!");
			ex.printStackTrace();
		}
	}
	public static ArrayList<Screens> getUserScreens(UserType type){//assumed user logged in 
		switch(type) {
		case CEO:
			return new ArrayList<>(Arrays.asList(new Screens[] {Screens.VIEW_REPORTS}));
		case CUSTOMER:
			return new ArrayList<>(Arrays.asList(new Screens[] {Screens.CATALOG,Screens.VIEW_ORDERS_CUSTOMER}));
		case NEW_CUSTOMER:
			return new ArrayList<>(Arrays.asList(new Screens[] {Screens.CATALOG,Screens.VIEW_ORDERS_CUSTOMER}));
		case CUSTOMER_SERVICE:
			return new ArrayList<>(Arrays.asList(new Screens[] {Screens.COMPLAINT_HOME,Screens.SURVEY_HOME,Screens.VIEW_SURVEY_ANALYSIS_RESULTS}));
		case DELIVERY_COORDINATOR:
			return new ArrayList<>(Arrays.asList(new Screens[] {Screens.DELIVER_ORDERS}));
		case MARKETING_WORKER:
			return new ArrayList<>(Arrays.asList(new Screens[] {Screens.EDIT_CATALOG}));
		case SERVICE_EXPERT:
			return new ArrayList<>(Arrays.asList(new Screens[] {Screens.VIEW_ANSWERED_SURVEYS}));
		case STORE_MANGER:
			return new ArrayList<>(Arrays.asList(new Screens[] {Screens.MANAGE_USERS, Screens.REGISTER_CUSTMER,Screens.VIEW_ORDERS_MANAGER,Screens.VIEW_REPORTS,Screens.USER_PREMISSION}));
		case STORE_WORKER:
			break;
		default:
			return new ArrayList<>();
		}
		return null;
	}

			
}
