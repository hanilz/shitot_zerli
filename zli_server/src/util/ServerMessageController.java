package util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import entities.AccountPayment;
import entities.Branch;
import entities.DeliveriesOrders;
import entities.Delivery;
import entities.Order;
import entities.OrderProduct;
import entities.Product;
import entities.ManageUsers;
import ocsf.server.ConnectionToClient;
import server.ServerController;

public class ServerMessageController {

	private static ServerMessageController instance = null;

	private ServerMessageController() {

	}

	public static ServerMessageController getServerMessageController() {
		if (instance == null)
			instance = new ServerMessageController();
		return instance;
	}

	/**
	 * This class will help us the control all the messages that the server recieves
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
		@SuppressWarnings("unchecked")
		HashMap<String, Object> message = (HashMap<String, Object>) msg;
		String command = (String) message.get("command");
		/*if (command.equals("fetch orders")) { // if the string equals to fetch orders -> execute the select * query from
			// table orders
			ArrayList<Order> orders = AnaylzeCommand.selectAllOrders();
			try {
				message.put("response", orders);
				client.sendToClient(message); // send the list to fetch all the orders to the server
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/ if (command.equals("client disconnected")) {
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
			try {
				message.put("command", "client disconnected");
				client.sendToClient(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (command.equals("fetch branches")) {
			ArrayList<Branch> branches = AnaylzeCommand.selectAllBranches();
			
			try {
				message.put("response", branches);
				client.sendToClient(message); // send the list to fetch all the orders to the server
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (command.equals("fetch products")) {
			ArrayList<Product> products = AnaylzeCommand.selectAllProducts();
			try {
				message.put("response", products);
				client.sendToClient(message); // send the list to fetch all the orders to the server
			} catch (IOException e) {
				e.printStackTrace();
			}
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
		
		else if(command.equals("fetch all user details")) {
			ArrayList<ManageUsers> users = AnaylzeCommand.selectAllUsers();
			try {
				message.put("response", users);
				client.sendToClient(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		else if (command.equals("change user status")) {
			message.get("id");
			String response = AnaylzeCommand.changeUserStatus(message.get("id"));
			try {
				message.put("response", response);
				client.sendToClient(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
		
		else if (command.equals("logout")) {
			message.put("logout", AnaylzeCommand.logoutUser((int) message.get("logoutID")));
			try {
				client.sendToClient(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		else if (command.equals("insert account payment")) {
			AccountPayment accountPayment = (AccountPayment) message.get("account payment");
			boolean isInsert = AnaylzeCommand.insertAccountPayment(accountPayment.getFullName(), accountPayment.getCardNumber(), accountPayment.getCardDate(), accountPayment.getCardVCC(), accountPayment.getUser().getIdUser());
			try {
				if(isInsert)
					message.put("respose", "insert account payment successful");
				else
					message.put("respose", "insert account payment failed");
				client.sendToClient(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		else if (command.equals("insert order")) {
			Order order = (Order) message.get("order");
			int idOrderReturned = AnaylzeCommand.insertNewOrder(order.getPrice(), order.getGreetingCard(), order.getdOrder(), order.getBranch().getIdBranch(), order.getStatus(), order.getPaymentMethod(), order.getUser().getIdUser());
			try {
				message.put("respose", idOrderReturned);
				client.sendToClient(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		else if (command.equals("insert order products")) {
			@SuppressWarnings("unchecked")
			ArrayList<OrderProduct> orderProductsList = (ArrayList<OrderProduct>) message.get("list order products");
			boolean isInserted = AnaylzeCommand.insertOrderProducts(orderProductsList);
			try {
				if(isInserted)
					message.put("respose", "insert order products successful");
				else
					message.put("respose", "insert order products failed");
				client.sendToClient(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		else if (command.equals("insert delivery")) {
			Delivery delivery = (Delivery) message.get("delivery");
			int idDeliveryReturned = AnaylzeCommand.insertNewDelivery(delivery.getAddress(), delivery.getReceiverName(), delivery.getPhoneNumber(), delivery.getDeliveryDate(), delivery.getStatus());
			try {
				message.put("respose", idDeliveryReturned);
				client.sendToClient(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		else if (command.equals("insert delivery order")) {
			DeliveriesOrders deliveryOrder = (DeliveriesOrders) message.get("delivery order");
			boolean isInserted = AnaylzeCommand.insertDeliveryOrder(deliveryOrder.getIdOrder(), deliveryOrder.getIdDelivery());
			try {
				if(isInserted)
					message.put("respose", "insert delivery order successful");
				else
					message.put("respose", "insert delivery order failed");
				client.sendToClient(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
