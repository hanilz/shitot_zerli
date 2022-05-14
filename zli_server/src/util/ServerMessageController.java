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
			boolean isInsert = AnaylzeCommand.insertAccountPayment(accountPayment.getFullName(),
					accountPayment.getCardNumber(), accountPayment.getCardDate(), accountPayment.getCardVCC(),
					accountPayment.getUser().getIdUser());
			String insertionResult = isInsert ? "insert account payment successful" : "insert account payment failed";
			message.put("response", insertionResult);
			break;
		case INSERT_DELIVERY:
			Delivery delivery = (Delivery) message.get("delivery");
			int idDeliveryReturned = AnaylzeCommand.insertNewDelivery(delivery.getAddress(), delivery.getReceiverName(),
					delivery.getPhoneNumber(), delivery.getDeliveryDate(), delivery.getStatus());
			message.put("response", idDeliveryReturned);
			break;
		case INSERT_DELIVERY_ORDER:
			DeliveriesOrders deliveryOrder = (DeliveriesOrders) message.get("delivery order");
			boolean isInserted = AnaylzeCommand.insertDeliveryOrder(deliveryOrder.getIdOrder(),
					deliveryOrder.getIdDelivery());
			if (isInserted)
				message.put("response", "insert delivery order successful");
			else
				message.put("response", "insert delivery order failed");
			break;
		case INSERT_ORDER:
			Order order = (Order) message.get("order");
			Integer idOrderReturned = AnaylzeCommand.insertNewOrder(order.getPrice(), order.getGreetingCard(),
					order.getdOrder(), order.getBranch().getIdBranch(), order.getStatus(), order.getPaymentMethod(),
					order.getUser().getIdUser());
			message.put("response", idOrderReturned);
			break;
		case INSERT_ORDERS_PRODUCT:
			ArrayList<OrderProduct> orderProductsList = (ArrayList<OrderProduct>) message.get("list order products");
			boolean isOrderInserted = AnaylzeCommand.insertOrderProducts(orderProductsList);
			if (isOrderInserted)
				message.put("response", "insert order products successful");
			else
				message.put("response", "insert order products failed");
			break;
		case LOGIN:
			message=AnaylzeCommand.loginUser((String) message.get("username"),(String) message.get("password"));
			message.put("command", Commands.LOGIN);
			break;
		case LOGOUT:
			message.put("logout", AnaylzeCommand.logoutUser((int) message.get("logoutID")));
			break;
		case SERVER_DISCONNEDTED:
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
			boolean isSubmitted = AnaylzeCommand.submitSurvey((HashMap<SurveyQuestion, Integer>) message.get("answers"));
			String submissionResult = isSubmitted ? "insert survey answer successful" : "insert survey answer failed";
			message.put("response", submissionResult);
		case SUBMIT_COMPLAINT:
			boolean compailntSubmited = AnaylzeCommand.submitComplaint((String) message.get("Personal ID"),(String) message.get("Complaint"));
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
