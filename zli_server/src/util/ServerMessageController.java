package util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import entities.Order;
import ocsf.server.ConnectionToClient;
import server.ServerController;

public class ServerMessageController {
	
	private static ServerMessageController instance = null;
	
	private ServerMessageController() {
		
	}
	
	public static ServerMessageController getServerMessageController() {
		if(instance==null)
			instance = new ServerMessageController();
		return instance;
	}
	
	/**
	 * This class will help us the control all the messages that the server recieves and the
	 * server sending. for example: handling all the queries that the client
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
		if (command.equals("fetch orders")) { // if the string equals to fetch orders -> execute the select * query from
										  // table orders
			ArrayList<Order> orders = DataBaseController.selectAllOrders();
			try {
				message.put("response", orders);
				client.sendToClient(message); // send the list to fetch all the orders to the server
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if(command.equals("client disconnected")) {
			String clientIP = client.getInetAddress().toString();
			System.out.println("client disconnected detected: ip is " + clientIP);
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
		}
	}
}