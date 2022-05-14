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
			break;
		case CUSTOMER:
			return new ArrayList<>(Arrays.asList(new Screens[] {Screens.CATALOG}));
		case CUSTOMER_SERVICE:
			return new ArrayList<>(Arrays.asList(new Screens[] {Screens.SURVEY_HOME,Screens.COMPLAINT_HOME}));
		case DELIVERY_COORDINATOR:
			break;
		case MARKETING_WORKER:
			break;
		case SERVICE_EXPERT:
			break;
		case STORE_MANGER:
			return new ArrayList<>(Arrays.asList(new Screens[] {Screens.MANAGE_USERS}));
		case STORE_WORKER:
			break;
		default:
			return null;
		}
		return null;
	}
			
}
