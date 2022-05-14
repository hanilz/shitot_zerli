package util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import entities.AccountPayment;
import entities.Branch;
import entities.DeliveriesOrders;
import entities.Delivery;
import entities.Item;
import entities.ManageUsers;
import entities.Order;
import entities.OrderItem;
import entities.OrderProduct;
import entities.Product;
import ocsf.server.ConnectionToClient;
import server.ServerController;
import survey.SurveyQuestion;

public class ServerMessageController {

	private static ServerMessageController instance = null;
	private HashMap<String, Object> message;
	private String response;

	private ServerMessageController() {
	}

	public static ServerMessageController getServerMessageController() {
		if (instance == null)
			instance = new ServerMessageController();
		return instance;
	}

	/**
	 * This class will help us the control all the messages that the server receives
	 * and the server sending. for example: handling all the queries that the client
	 * sending.
	 */

	/**
	 * handleMessages handles the messages that the client send to the server. This
	 * function will execute the correct command that been sent.
	 * 
	 * @param msg
	 */
	@SuppressWarnings("unchecked")
	public void handleMessages(Object msg, ConnectionToClient client) {
		message = (HashMap<String, Object>) msg;
		Commands command = (Commands) message.get("command");
		switch (command) {
		case CHANGE_USER_STATUS:
			response = AnaylzeCommand.changeUserStatus(message.get("id"));
			message.put("response", response);
			break;
		case CLIENT_DISCONNECTED:
			String clientIP = client.getInetAddress().toString();
			System.out.println("client disconnected detected: ip is " + clientIP);
			if (message.get("logout") != null)
				AnaylzeCommand.logoutUser((int) message.get("logout"));
			for (ClientDetails currentClient : ServerController.clients) {
				if (clientIP.equals(currentClient.getClientIP())) {
					ServerController.getServerController().disconnectClient(currentClient);
					break;
				}
			}
			break;
		case FETCH_ALL_USERS_DETAILS:
			ArrayList<ManageUsers> users = AnaylzeCommand.selectAllUsers();
			message.put("response", users);
			break;
		case FETCH_BRANCHES:
			ArrayList<Branch> branches = AnaylzeCommand.selectAllBranches();
			message.put("response", branches);
			break;
		case FETCH_ORDERS:
			break;
		case FETCH_PRODUCTS:
			ArrayList<Product> products = AnaylzeCommand.selectAllProducts();
			message.put("response", products);
			break;
		case FETCH_SURVEYS:
			HashMap<Integer, String> surveys = AnaylzeCommand.selectSurveys();
			message.put("response", surveys);
			break;
		case INSERT_ACCOUNT_PAYMENT:
			AccountPayment accountPayment = (AccountPayment) message.get("account payment");
			boolean isInsert = AnaylzeCommand.insertAccountPayment(accountPayment);
			response = isInsert ? "insert account payment successful" : "insert account payment failed";
			message.put("response", response);
			break;
		case INSERT_DELIVERY:
			Delivery delivery = (Delivery) message.get("delivery");
			int idDeliveryReturned = AnaylzeCommand.insertNewDelivery(delivery);
			message.put("response", idDeliveryReturned);
			break;
		case INSERT_DELIVERY_ORDER:
			DeliveriesOrders deliveryOrder = (DeliveriesOrders) message.get("delivery order");
			boolean isInserted = AnaylzeCommand.insertDeliveryOrder(deliveryOrder.getIdOrder(),deliveryOrder.getIdDelivery());
			response = isInserted ? "insert delivery order successful" : "insert delivery order failed";
			message.put("response", response);
			break;
		case INSERT_ORDER:
			Order order = (Order) message.get("order");
			Integer idOrderReturned = AnaylzeCommand.insertNewOrder(order);
			message.put("response", idOrderReturned);
			break;
		case INSERT_ORDERS_PRODUCT:
			ArrayList<OrderProduct> orderProductsList = (ArrayList<OrderProduct>) message.get("list order products");
			boolean isOrderInserted = AnaylzeCommand.insertOrderProducts(orderProductsList);
			response = isOrderInserted ? "insert order products successful" : "insert order products failed";
			message.put("response", response);
			break;
		case INSERT_ORDERS_ITEMS:
			ArrayList<OrderItem> orderItemsList = (ArrayList<OrderItem>) message.get("list order items");
			boolean isOrdersItemsInserted = AnaylzeCommand.insertOrderItems(orderItemsList);
			if (isOrdersItemsInserted)
				message.put("response", "insert order items successful");
			else
				message.put("response", "insert order items failed");
			sendToClient(client);
			break;
		case LOGIN:// fix it
			HashMap<String, Object> result = AnaylzeCommand.loginUser((String) message.get("username"),
					(String) message.get("password"));
			result.put("command", Commands.LOGIN);
			try {
				client.sendToClient(result);
			} catch (IOException e) {
				e.printStackTrace();
			}
		case LOGIN:
			message = AnaylzeCommand.loginUser((String) message.get("username"), (String) message.get("password"));
			message.put("command", Commands.LOGIN);
			break;
		case LOGOUT:
			message.put("logout", AnaylzeCommand.logoutUser((int) message.get("logoutID")));
			break;
		case FETCH_ITEMS:
			ArrayList<Item> items = AnaylzeCommand.selectAllItems();
			message.put("response", items);
			break;
		case GET_SERVEY:
			ArrayList<SurveyQuestion> returnedSurveys = AnaylzeCommand.getSurvey((Integer) message.get("surveyID"));
			message.put("response", returnedSurveys);
			break;
		case SUBMIT_SURVEY:
			boolean isSubmitted = AnaylzeCommand
					.submitSurvey((HashMap<SurveyQuestion, Integer>) message.get("answers"));
			response = isSubmitted ? "insert survey answer successful" : "insert survey answer failed";
			message.put("response",response);
		case SUBMIT_COMPLAINT:
			boolean compailntSubmited = AnaylzeCommand.submitComplaint((String) message.get("Personal ID"),
					(String) message.get("Complaint"));
			message.put("response", compailntSubmited);
		default:
			break;
		}
		sendToClient(client);
	}

	private void sendToClient(ConnectionToClient client) {
		try {
			client.sendToClient(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
//Warehouse
/*
 * if (command.equals("fetch orders")) { // if the string equals to fetch orders
 * -> execute the select * query from // table orders ArrayList<Order> orders =
 * AnaylzeCommand.selectAllOrders(); try { message.put("response", orders);
 * client.sendToClient(message); // send the list to fetch all the orders to the
 * server } catch (IOException e) { e.printStackTrace(); } }
 */
