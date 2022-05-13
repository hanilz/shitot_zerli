package util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import entities.AccountPayment;
import entities.Branch;
import entities.DeliveriesOrders;
import entities.Delivery;
import entities.Item;
import entities.Order;
import entities.OrderProduct;
import entities.Product;
import entities.ManageUsers;
import ocsf.server.ConnectionToClient;
import server.ServerController;
import survey.Survey;

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
	public void handleMessages(Object msg, ConnectionToClient client) {
		message = (HashMap<String, Object>) msg;
		String command = (String) message.get("command");
		if (command.equals("client disconnected")) {
			String clientIP = client.getInetAddress().toString();
			System.out.println("client disconnected detected: ip is " + clientIP);
			// call database for update on disconnection
			if (message.get("logout") != null)
				AnaylzeCommand.logoutUser((int) message.get("logout"));
			for (ClientDetails currentClient : ServerController.clients) {
				if (clientIP.equals(currentClient.getClientIP())) {
					ServerController.getServerController().disconnectClient(currentClient);
					break;
				}
			}
				message.put("command", "client disconnected");
				sendToClient(client);
		} else if (command.equals("fetch branches")) {
			ArrayList<Branch> branches = AnaylzeCommand.selectAllBranches();
				message.put("response", branches);
				sendToClient(client);
		} else if (command.equals("fetch products")) {
			ArrayList<Product> products = AnaylzeCommand.selectAllProducts();
				message.put("response", products);
				sendToClient(client);
		} else if (command.equals("fetch products")) {
			ArrayList<Item> items = AnaylzeCommand.selectAllItems();
				message.put("response", items);
				sendToClient(client);
		}

		// handle login massage
		else if (command.equals("login user")) {
			HashMap<String, Object> result = AnaylzeCommand.loginUser((String) message.get("username"),
					(String) message.get("password"));
			result.put("command", "login user");
			try {
				client.sendToClient(result);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		else if (command.equals("fetch all user details")) {
			ArrayList<ManageUsers> users = AnaylzeCommand.selectAllUsers();
				message.put("response", users);
				sendToClient(client);
		}

		else if (command.equals("change user status")) {
			message.get("id");
			String response = AnaylzeCommand.changeUserStatus(message.get("id"));
			message.put("response", response);
			sendToClient(client);
		}

		else if (command.equals("logout")) {
			message.put("logout", AnaylzeCommand.logoutUser((int) message.get("logoutID")));
			sendToClient(client);
		}

		else if (command.equals("insert account payment")) {
			AccountPayment accountPayment = (AccountPayment) message.get("account payment");
			boolean isInsert = AnaylzeCommand.insertAccountPayment(accountPayment.getFullName(),
					accountPayment.getCardNumber(), accountPayment.getCardDate(), accountPayment.getCardVCC(),
					accountPayment.getUser().getIdUser());
			if (isInsert)
				message.put("response", "insert account payment successful");
			else
				message.put("response", "insert account payment failed");
			sendToClient(client);
		}

		else if (command.equals("insert order")) {
			Order order = (Order) message.get("order");
			Integer idOrderReturned = AnaylzeCommand.insertNewOrder(order.getPrice(), order.getGreetingCard(),
					order.getdOrder(), order.getBranch().getIdBranch(), order.getStatus(), order.getPaymentMethod(),
					order.getUser().getIdUser());
			message.put("response", idOrderReturned);
			sendToClient(client);
		}

		else if (command.equals("insert order products")) {
			@SuppressWarnings("unchecked")
			ArrayList<OrderProduct> orderProductsList = (ArrayList<OrderProduct>) message.get("list order products");
			boolean isInserted = AnaylzeCommand.insertOrderProducts(orderProductsList);
			if (isInserted)
				message.put("response", "insert order products successful");
			else
				message.put("response", "insert order products failed");
			sendToClient(client);
		}

		else if (command.equals("insert delivery")) {
			Delivery delivery = (Delivery) message.get("delivery");
			int idDeliveryReturned = AnaylzeCommand.insertNewDelivery(delivery.getAddress(), delivery.getReceiverName(),
					delivery.getPhoneNumber(), delivery.getDeliveryDate(), delivery.getStatus());
			message.put("response", idDeliveryReturned);
			sendToClient(client);
		}

		else if (command.equals("insert delivery order")) {
			DeliveriesOrders deliveryOrder = (DeliveriesOrders) message.get("delivery order");
			boolean isInserted = AnaylzeCommand.insertDeliveryOrder(deliveryOrder.getIdOrder(),
					deliveryOrder.getIdDelivery());
			if (isInserted)
				message.put("response", "insert delivery order successful");
			else
				message.put("response", "insert delivery order failed");
			sendToClient(client);
		}

		else if (command.equals("Fetch Surveys")) {
			ArrayList<Survey> surveys = AnaylzeCommand.selectSurveys();
			message.put("response", surveys);
			sendToClient(client);
		}

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
