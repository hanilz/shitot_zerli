package util;

import java.io.IOException;
import java.util.HashMap;

import client.ClientFormController;
import entities.User;

public class ManageClients {
	public static void exitClient() {
		try {
			if (ClientFormController.client.isConnected()) {
				HashMap<String, Object> message = new HashMap<>();
				message.put("command", "client disconnected");
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
}
