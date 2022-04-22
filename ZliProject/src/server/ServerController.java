package server;

import java.io.IOException;
import java.util.ArrayList;

import entities.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import util.ClientDetails;
import util.DataBaseController;


public class ServerController extends AbstractServer implements Runnable {

	/**
	 * The default port to listen on.
	 */
	final public static int DEFAULT_PORT = 5555;

	//private static String connectedMessage;

	private int port;

	private String ip;


	public static final ObservableList<ClientDetails> clients = FXCollections.observableArrayList();

	public ServerController(int port, String ip) {
		super(port);
		this.port = port;
		this.ip = ip;
	}

	public String connectToDB() {
		return DataBaseController.connect();
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		if (msg instanceof String) {
			if (msg.equals("fetch orders")) {
				ArrayList<Order> orders = DataBaseController.selectAllOrders();
				try {
					client.sendToClient(orders);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return;
	}

	/**
	 * Disconnect server from the connection.
	 */
	public void disconnectServer() {
		try {
			close();
			disconnectAllClients();
			System.out.println("disconnected server");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public String runServer() throws Exception {
		if (!isListening())// if server not already running start it
			try {
				listen();
			} catch (Exception e) {
				close();
				System.out.println(e.getMessage());
				return "Can not listen";
        			}
		return isListening() ? "Server listening for connections on port " + getPort()
				: "Server has stopped listening for connections.";
	}

	
	@Override
	protected void clientConnected(ConnectionToClient client) {
		String clientAddress = client.getInetAddress().toString();
		boolean isExists = false;
		for (ClientDetails currentClient : clients) {
			if (currentClient.getClientIP().equals(clientAddress)) {
				System.out.println("Client already exists!");
				currentClient.setStatus("Connected");
				isExists = true;
				break;
			}
		}
		if (!isExists) {
			System.out.println("Client connected");
			ClientDetails newClient = new ClientDetails(ip, clientAddress, "Connected");

			clients.add(newClient);
			System.out.println(newClient);
		}
	}

	/**
	   * Hook method called each time a client disconnects.
	   * The default implementation does nothing. The method
	   * may be overridden by subclasses but should remains synchronized.
	   *
	   * @param client the connection with the client.
	   */
	@Override
	synchronized protected void clientDisconnected(ConnectionToClient client) {
		System.out.println("client has disconnected! (from clientDisconnected) ");
	}
	  
	private void disconnectAllClients() {
		System.out.println("Disconnecting all clients!");
		for (ClientDetails currentClient : clients) 
			disconnectClient(currentClient);
	}
	
	private void disconnectClient(ClientDetails client) {
		String clientAddress = client.getClientIP();
		System.out.println("Hi! I disconnected =)");

		for (ClientDetails currentClient : clients) {
			if (currentClient.getClientIP().equals(clientAddress)) {
				System.out.println("Client " + currentClient.getClientIP() + " found - disconnecting.");
				currentClient.setStatus("Disconnected");
				System.out.println("Client " + currentClient.getClientIP() + " status is - " + currentClient.getStatus());
			}
		}
	}



}
