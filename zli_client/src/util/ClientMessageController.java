package util;

import java.util.ArrayList;
import java.util.HashMap;

import client.ClientController;
import entities.Order;
import entities.Product;
import gui.ClientScreen;
import javafx.collections.FXCollections;

/**
 * This class will help us the control all the messages that the client and the
 * server sending. for example: handling all the queries that the client
 * sending.
 */
public class ClientMessageController {

	private static ClientMessageController instance = null;

	private ClientMessageController() {

	}

	public static ClientMessageController getClientMessageController() {
		if (instance == null)
			instance = new ClientMessageController();
		return instance;
	}

	/**
	 * handleMessages handles the messages that the client send to the server. This
	 * function will execute the correct command that been sent.
	 * 
	 * @param msg
	 */
	public void handleMessages(Object msg) {
		@SuppressWarnings("unchecked")
		HashMap<String, Object> message = (HashMap<String, Object>) msg;
		String command = (String) message.get("command");
		Object response = null;
		if (command.contains("fetch orders")) {
			@SuppressWarnings("unchecked")
			ArrayList<Order> ordersList = (ArrayList<Order>) message.get("response");
			if (!ordersList.isEmpty()) {
				Order messageOrder = ordersList.get(0);
				int orderNumber = messageOrder.getOrderNumber();
				switch (orderNumber) { // because we will use the orders table for the next assignment, we will use
										// switch-case for extending the behavior of the orders table queries
				case -1: // is fetch all orders
					ordersList.remove(0); // get rid of indicative order
					break;
				}
			}
			response = FXCollections.observableArrayList(ordersList); // cast to ObservableList
			ClientController.setResponse(response);
		} else if (command.contains("server disconnected")) {
			System.out.println("disconnected from server, back to client main.");
			changeSceneToMainClient();
		} else if (command.contains("client disconnected")) {
			ClientController.setResponse("client disconnected");
		}

		
		if (command.contains("login user")) {
			Status status = (Status)message.get("response");
			try {
				ClientController.setResponse(status);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		else if(command.contains("fetch products")) {
			ArrayList<Product> productsList = (ArrayList<Product>) message.get("response");
			response = FXCollections.observableArrayList(productsList);
			ClientController.setResponse(response);
		}
	}

	/**
	 * This function will help us to switch between the screens after the user
	 * connected to the server-ip
	 */
	private void changeSceneToMainClient() {
		try {
			ClientScreen.changeScene(ClientScreen.class.getResource("ClientScreen.fxml"), "Zli Client");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

// -----  Code warehouse  -----
