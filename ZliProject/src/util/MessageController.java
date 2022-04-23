package util;

import java.io.Serializable;
import java.util.ArrayList;

import client.ClientController;
import entities.Order;
import javafx.collections.FXCollections;

/**
 * This class will help us the control all the messages that the client and the server sending.
 * for example: handling all the queries that the client sending.
 * @author Yana Raitsin
 *
 */
public class MessageController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * handleMessages handles the messages that the client send to the server.
	 * This function will execute the correct command that been sent.
	 * @param msg
	 */
	public void handleMessages(Object msg) {
		Object response = null;
		if(msg instanceof String) {
			String checkMsg = (String)msg;
			String[] checkFlag = checkMsg.split(" ");
			if(checkMsg.contains("update orders") && checkFlag[2].equals("true")) {
				response = "DB updated";
				ClientController.setResponse(response);
			}
			else {
				response = "update failed";
				ClientController.setResponse(response);
			}
		}
		if (msg instanceof ArrayList) {
			if (!((ArrayList<?>) msg).isEmpty()) {
				Object message = ((ArrayList<?>) msg).get(0);
				if (message instanceof Order) {
					@SuppressWarnings("unchecked")
					ArrayList<Order> ordersList = (ArrayList<Order>) msg; // list of orders
					Order messageOrder = (Order) message;
					int orderNumber = messageOrder.getOrderNumber();
					switch (orderNumber) { //because we will use the orders table for the next assignment, we will use switch-case for extending the behavior of the orders table queries
						case -1: // is fetch all orders
							ordersList.remove(0); // get rid of indicative order
							response = FXCollections.observableArrayList(ordersList); // cast to ObservableList
							ClientController.setResponse(response);
							break;
					}
				}
			}
		}
	}
}
