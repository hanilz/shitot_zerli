package util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import entities.Branch;
import entities.Complaint;
import entities.Item;
import entities.ManageUsers;
import entities.Product;
import entities.UserDetails;
import mangeCustomerOrders.ManagerOrderView;
import notifications.Notification;
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
			AnaylzeCommand.execueQuery(((MessageHandler)message.get("message type")));
			ArrayList<Product> products = (ArrayList<Product>) ((MessageHandler)message.get("message type")).getResponseFromDb();
			message.put("response", products);
			break;
		case FETCH_SURVEYS:
			HashMap<Integer, String> surveys = AnaylzeCommand.selectSurveys();
			message.put("response", surveys);
			break;
		case INSERT_ACCOUNT_PAYMENT:
			AnaylzeCommand.execueQuery(((MessageHandler)message.get("message type")));
			boolean isInsert = (boolean) ((MessageHandler)message.get("message type")).getResponseFromDb();
			response = isInsert ? "insert account payment successful" : "insert account payment failed";
			message.put("response", response);
			break;
		case INSERT_DELIVERY:
			AnaylzeCommand.execueQuery(((MessageHandler)message.get("message type")));
			int idDeliveryReturned = (int) ((MessageHandler)message.get("message type")).getResponseFromDb();
			message.put("response", idDeliveryReturned);
			break;
		case INSERT_DELIVERY_ORDER:
			AnaylzeCommand.execueQuery(((MessageHandler)message.get("message type")));
			boolean isInserted = (boolean) ((MessageHandler)message.get("message type")).getResponseFromDb();
			response = isInserted ? "insert delivery order successful" : "insert delivery order failed";
			message.put("response", response);
			break;
		case INSERT_ORDER:
			AnaylzeCommand.execueQuery(((MessageHandler)message.get("message type")));
			Integer idOrderReturned = (Integer) ((MessageHandler)message.get("message type")).getResponseFromDb();
			message.put("response", idOrderReturned);
			break;
		case INSERT_ORDERS_PRODUCT:
			AnaylzeCommand.execueQuery(((MessageHandler)message.get("message type")));
			boolean isOrderInserted = (boolean) ((MessageHandler)message.get("message type")).getResponseFromDb();
			response = isOrderInserted ? "insert order products successful" : "insert order products failed";
			message.put("response", response);
			break;
		case INSERT_ORDERS_ITEMS:
			AnaylzeCommand.execueQuery(((MessageHandler)message.get("message type")));
			boolean isOrdersItemsInserted = (boolean) ((MessageHandler)message.get("message type")).getResponseFromDb();
			if (isOrdersItemsInserted)
				message.put("response", "insert order items successful");
			else
				message.put("response", "insert order items failed");
			sendToClient(client);
			break;
		case LOGIN:
			message = AnaylzeCommand.loginUser((String) message.get("username"), (String) message.get("password"));
			message.put("command", Commands.LOGIN);
			break;
		case LOGOUT:
			message.put("logout", AnaylzeCommand.logoutUser((int) message.get("logoutID")));
			break;
		case FETCH_ITEMS:
			AnaylzeCommand.execueQuery(((MessageHandler)message.get("message type")));
			ArrayList<Item> items = (ArrayList<Item>) ((MessageHandler)message.get("message type")).getResponseFromDb();
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
			message.put("response", response);
			break;
		case SUBMIT_COMPLAINT:
			AnaylzeCommand.execueQuery(((MessageHandler)message.get("message type")));
			boolean compailntSubmited = (boolean) ((MessageHandler)message.get("message type")).getResponseFromDb();
			message.put("response", compailntSubmited);
			break;
		case UPDATE_PRODUCTS_BASE:
			boolean isUpdated;
			isUpdated = message.get("type").equals("item") ? AnaylzeCommand.updateItem((Item) message.get("unit"))
					: AnaylzeCommand.updateProduct((Product) message.get("unit"));
			message.put("response", isUpdated);
			break;
		case GET_ORDER_NUMBERS:
			AnaylzeCommand.execueQuery(((MessageHandler)message.get("message type")));
			ArrayList<Integer> orderNumbers  = (ArrayList<Integer>) ((MessageHandler)message.get("message type")).getResponseFromDb();
			message.put("response", orderNumbers);
			break;
		case FETCH_COMPLAINTS:
			AnaylzeCommand.execueQuery(((MessageHandler)message.get("message type")));
			ArrayList<Complaint> complaints = (ArrayList<Complaint>) ((MessageHandler)message.get("message type")).getResponseFromDb();
			message.put("response", complaints);
			break;
		case CLOSE_COMPLAINT:
			AnaylzeCommand.execueQuery(((MessageHandler)message.get("message type")));
			boolean ans = (boolean) ((MessageHandler)message.get("message type")).getResponseFromDb();
			message.put("response", ans);
			break;
		case GET_ORDER_SUM:
			AnaylzeCommand.execueQuery(((MessageHandler)message.get("message type")));
			Double sum = (Double) ((MessageHandler)message.get("message type")).getResponseFromDb();
			message.put("response", sum);
			break;
		case NEW_USER_DETAILS:
			int idAccount = AnaylzeCommand.insertNewUserDetails((UserDetails)message.get("User Details"));
			message.put("response",idAccount);
			break;
		case NEW_USERS:
			int idUser = AnaylzeCommand.insertNewUser((String)message.get("username"),(String)message.get("password"),(int)message.get("idAccount"));
			message.put("response",idUser);
			break;
		case DELETE_USER_DETAILS:
			boolean remove = AnaylzeCommand.deleteUserDetails((int)message.get("idAccount"));
			message.put("response",remove);
			break;
		case FETCH_ORDERS_MANAGER:
			ArrayList<ManagerOrderView> orders = AnaylzeCommand.selectOrdersForManager((Integer)message.get("manager id"));
			message.put("response", orders);
			break;
		case APPROVE_ORDER:
			boolean approve = AnaylzeCommand.approveOrder((Integer)message.get("order id"));
			message.put("response", approve);
			break;
		case CANCEL_ORDER:
			boolean cancel = AnaylzeCommand.cancelOrder((Integer)message.get("order id"));
			message.put("response", cancel);
			break;
		case FETCH_NOTIFICATIONS:
			ArrayList<Notification> notifications = AnaylzeCommand.getNotification((Integer)message.get("idUser"));
			message.put("response", notifications);
			break;
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
