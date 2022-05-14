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
		// String command = (String) message.get("command");
		Commands command = (Commands) message.get("command");
		switch (command) {
		case CHANGE_USER_STATUS:
			message.get("id");
			String response = AnaylzeCommand.changeUserStatus(message.get("id"));
			message.put("response", response);
			sendToClient(client);
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
			message.put("command", Commands.CLIENT_DISCONNECTED);
			sendToClient(client);
			break;
		case FETCH_ALL_USERS_DETAILS:
			ArrayList<ManageUsers> users = AnaylzeCommand.selectAllUsers();
			message.put("response", users);
			sendToClient(client);
			break;
		case FETCH_BRANCHES:
			ArrayList<Branch> branches = AnaylzeCommand.selectAllBranches();
			message.put("response", branches);
			sendToClient(client);
			break;
		case FETCH_ORDERS:
			break;
		case FETCH_PRODUCTS:
			ArrayList<Product> products = AnaylzeCommand.selectAllProducts();
			message.put("response", products);
			sendToClient(client);
			break;
		case FETCH_SURVEYS:
			HashMap<Integer, String> surveys = AnaylzeCommand.selectSurveys();
			message.put("response", surveys);
			sendToClient(client);
			break;
		case INSERT_ACCOUNT_PAYMENT:
			AccountPayment accountPayment = (AccountPayment) message.get("account payment");
			boolean isInsert = AnaylzeCommand.insertAccountPayment(accountPayment.getFullName(),
					accountPayment.getCardNumber(), accountPayment.getCardDate(), accountPayment.getCardVCC(),
					accountPayment.getUser().getIdUser());
			String insertionResult = isInsert ? "insert account payment successful" : "insert account payment failed";
			message.put("response", insertionResult);
			sendToClient(client);
			break;
		case INSERT_DELIVERY:
			Delivery delivery = (Delivery) message.get("delivery");
			int idDeliveryReturned = AnaylzeCommand.insertNewDelivery(delivery.getAddress(), delivery.getReceiverName(),
					delivery.getPhoneNumber(), delivery.getDeliveryDate(), delivery.getStatus());
			message.put("response", idDeliveryReturned);
			sendToClient(client);
			break;
		case INSERT_DELIVERY_ORDER:
			DeliveriesOrders deliveryOrder = (DeliveriesOrders) message.get("delivery order");
			boolean isInserted = AnaylzeCommand.insertDeliveryOrder(deliveryOrder.getIdOrder(),
					deliveryOrder.getIdDelivery());
			if (isInserted)
				message.put("response", "insert delivery order successful");
			else
				message.put("response", "insert delivery order failed");
			sendToClient(client);
			break;
		case INSERT_ORDER:
			Order order = (Order) message.get("order");
			Integer idOrderReturned = AnaylzeCommand.insertNewOrder(order.getPrice(), order.getGreetingCard(),
					order.getdOrder(), order.getBranch().getIdBranch(), order.getStatus(), order.getPaymentMethod(),
					order.getUser().getIdUser());
			message.put("response", idOrderReturned);
			sendToClient(client);
			break;
		case INSERT_ORDERS_PRODUCT:
			ArrayList<OrderProduct> orderProductsList = (ArrayList<OrderProduct>) message.get("list order products");
			boolean isOrderInserted = AnaylzeCommand.insertOrderProducts(orderProductsList);
			if (isOrderInserted)
				message.put("response", "insert order products successful");
			else
				message.put("response", "insert order products failed");
			sendToClient(client);
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
			break;
		case LOGOUT:
			message.put("logout", AnaylzeCommand.logoutUser((int) message.get("logoutID")));
			sendToClient(client);
			break;
		case SERVER_DISCONNEDTED:
			break;
		case FETCH_ITEMS:
			ArrayList<Item> items = AnaylzeCommand.selectAllItems();
			message.put("response", items);
			sendToClient(client);
			break;
		case GET_SERVEY:
			ArrayList<SurveyQuestion> returnedSurveys = AnaylzeCommand.getSurvey((Integer) message.get("surveyID"));
			message.put("response", returnedSurveys);
			sendToClient(client);
			break;
		case SUBMIT_SURVEY:
			boolean isSubmitted = AnaylzeCommand.submitSurvey((HashMap<SurveyQuestion, Integer>) message.get("answers"));
			String submissionResult = isSubmitted ? "insert survey answer successful" : "insert survey answer failed";
			message.put("response", submissionResult);
			sendToClient(client);
		case SUBMIT_COMPLAINT:
			boolean compailntSubmited = AnaylzeCommand.submitComplaint((String) message.get("Personal ID"),(String) message.get("Complaint"));
			message.put("response", compailntSubmited);
			sendToClient(client);
		default:
			break;
		}

	}

//		if (command.equals("client disconnected")) {
//			String clientIP = client.getInetAddress().toString();
//			System.out.println("client disconnected detected: ip is " + clientIP);
//			// call database for update on disconnection
//			if (message.get("logout") != null)
//				AnaylzeCommand.logoutUser((int) message.get("logout"));
//			for (ClientDetails currentClient : ServerController.clients) {
//				if (clientIP.equals(currentClient.getClientIP())) {
//					ServerController.getServerController().disconnectClient(currentClient);
//					break;
//				}
//			}
//			try {
//				message.put("command", "client disconnected");
//				client.sendToClient(message);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		} 
//		else if (command.equals("fetch branches")) {
//			ArrayList<Branch> branches = AnaylzeCommand.selectAllBranches();
//			try {
//				message.put("response", branches);
//				client.sendToClient(message); // send the list to fetch all the orders to the server
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		} else if (command.equals("fetch products")) {
//			ArrayList<Product> products = AnaylzeCommand.selectAllProducts();
//			try {
//				message.put("response", products);
//				client.sendToClient(message); // send the list to fetch all the products to the client
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		} else if (command.equals("fetch items")) {
//			ArrayList<Item> items = AnaylzeCommand.selectAllItems();
//			try {
//				message.put("response", items);
//				client.sendToClient(message); // send the list to fetch all the items to the client
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//
//		// handle login massage
//		else if (command.equals("login user")) {
//			HashMap<String, Object> result = AnaylzeCommand.loginUser((String) message.get("username"),
//					(String) message.get("password"));
//			result.put("command", "login user");
//			try {
//				client.sendToClient(result);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//
//		else if (command.equals("fetch all user details")) {
//			ArrayList<ManageUsers> users = AnaylzeCommand.selectAllUsers();
//			try {
//				message.put("response", users);
//				client.sendToClient(message);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//
//		else if (command.equals("change user status")) {
//			message.get("id");
//			String response = AnaylzeCommand.changeUserStatus(message.get("id"));
//			try {
//				message.put("response", response);
//				client.sendToClient(message);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//
//		else if (command.equals("logout")) {
//			message.put("logout", AnaylzeCommand.logoutUser((int) message.get("logoutID")));
//			try {
//				client.sendToClient(message);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//
//		else if (command.equals("insert account payment")) {
//			AccountPayment accountPayment = (AccountPayment) message.get("account payment");
//			boolean isInsert = AnaylzeCommand.insertAccountPayment(accountPayment.getFullName(),
//					accountPayment.getCardNumber(), accountPayment.getCardDate(), accountPayment.getCardVCC(),
//					accountPayment.getUser().getIdUser());
//			try {
//				if (isInsert)
//					message.put("response", "insert account payment successful");
//				else
//					message.put("response", "insert account payment failed");
//				client.sendToClient(message);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//
//		else if (command.equals("insert order")) {
//			Order order = (Order) message.get("order");
//			Integer idOrderReturned = AnaylzeCommand.insertNewOrder(order.getPrice(), order.getGreetingCard(),
//					order.getdOrder(), order.getBranch().getIdBranch(), order.getStatus(), order.getPaymentMethod(),
//					order.getUser().getIdUser());
//			try {
//				message.put("response", idOrderReturned);
//				client.sendToClient(message);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//
//		else if (command.equals("insert order products")) {
//			@SuppressWarnings("unchecked")
//			ArrayList<OrderProduct> orderProductsList = (ArrayList<OrderProduct>) message.get("list order products");
//			boolean isInserted = AnaylzeCommand.insertOrderProducts(orderProductsList);
//			try {
//				if (isInserted)
//					message.put("response", "insert order products successful");
//				else
//					message.put("response", "insert order products failed");
//				client.sendToClient(message);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//
//		else if (command.equals("insert delivery")) {
//			Delivery delivery = (Delivery) message.get("delivery");
//			int idDeliveryReturned = AnaylzeCommand.insertNewDelivery(delivery.getAddress(), delivery.getReceiverName(),
//					delivery.getPhoneNumber(), delivery.getDeliveryDate(), delivery.getStatus());
//			try {
//				message.put("response", idDeliveryReturned);
//				client.sendToClient(message);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//
//		else if (command.equals("insert delivery order")) {
//			DeliveriesOrders deliveryOrder = (DeliveriesOrders) message.get("delivery order");
//			boolean isInserted = AnaylzeCommand.insertDeliveryOrder(deliveryOrder.getIdOrder(),
//					deliveryOrder.getIdDelivery());
//			try {
//				if (isInserted)
//					message.put("response", "insert delivery order successful");
//				else
//					message.put("response", "insert delivery order failed");
//				client.sendToClient(message);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//
//		else if (command.equals("Fetch Surveys")) {
//			HashMap<Integer, String> surveys = AnaylzeCommand.selectSurveys();
//			try {
//				message.put("response", surveys);
//				client.sendToClient(message);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//
//		else if (command.equals("Get Survey")) {
//			ArrayList<SurveyQuestion> surveys = AnaylzeCommand.getSurvey((Integer) message.get("surveyID"));
//			try {
//				message.put("response", surveys);
//				client.sendToClient(message);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//
//		else if (command.equals("Submit survey Answer")) {
//			// TODO
//			boolean response = AnaylzeCommand.submitSurvey((HashMap) message.get("answers"));
//			try {
//				if (response)
//					message.put("response", "insert survey answer successful");
//				else
//					message.put("response", "insert survey answer failed");
//				client.sendToClient(message);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//
//	}

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
