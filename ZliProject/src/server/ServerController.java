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
import util.InputChecker;

/**
 * ServerController extends the superclass AbstractServer for implementing and
 * overriding the functions for our server-client project (zli)
 */
public class ServerController extends AbstractServer implements Runnable {

	/**
	 * The default port to listen on.
	 */
	final public static int DEFAULT_PORT = 5555;

	// private static String connectedMessage;

	/**
	 * Saving the port of the server
	 */
	private int port;

	/**
	 * Saving the ip of the server
	 */
	private String ip;

	/**
	 * Saving all the client that connected to the server so the server screen will
	 * present the clients
	 */
	public static final ObservableList<ClientDetails> clients = FXCollections.observableArrayList();

	public ServerController(int port, String ip) {
		super(port);
		this.port = port;
		this.ip = ip;
	}

	/**
	 * This method will connect to the database will the parameters that given by
	 * the user/gui
	 * 
	 * @return
	 */
	public String connectToDB() {
		return DataBaseController.connect();
	}

	/**
	 * This method handles any messages received from the client.
	 *
	 * @param msg    The message received from the client.
	 * @param client The connection from which the message originated.
	 * @param
	 */
	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		if (msg instanceof String) {
			String message = (String) msg;
			if (msg.equals("fetch orders")) { // if the string equals to fetch orders -> execute the select * query from
												// table orders
				ArrayList<Order> orders = DataBaseController.selectAllOrders();
				try {
					client.sendToClient(orders); // send the list to fetch all the orders to the server
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (message.contains("update orders")) {
				String[] messagesFromClient = message.split(" ");
				String sendDate = messagesFromClient[3] + " " + messagesFromClient[4];
				if(!InputChecker.checkDateFormat(sendDate)) {
					System.out.println("The date format is invalid");
					return;
				}
				DataBaseController.updateOrder(messagesFromClient[2], sendDate, messagesFromClient[5]);
				if(DataBaseController.isUpdated) {
					message = messagesFromClient[0] + " " + messagesFromClient[1] + " true";
					DataBaseController.isUpdated = false;
				}
				try {
					client.sendToClient(message);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Disconnect server from the connection and disconnecting all the clients.
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

	/**
	 * runServer will start listening to the server after clicking on the connect
	 * button with the right information
	 * 
	 * @return message depending on the try-catch
	 * @throws Exception
	 */
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
	 * Hook method called each time a client disconnects. The default implementation
	 * does nothing. The method may be overridden by subclasses but should remains
	 * synchronized.
	 *
	 * @param client the connection with the client.
	 */
	@Override
	synchronized protected void clientDisconnected(ConnectionToClient client) {
		System.out.println("client has disconnected! (from clientDisconnected) ");
	}

	/**
	 * disconnectAllClients helps us disconnect all the clients that connected to
	 * the server
	 */
	private void disconnectAllClients() {
		System.out.println("Disconnecting all clients!");
		for (ClientDetails currentClient : clients)
			disconnectClient(currentClient);
	}

	/**
	 * TODO: we need to fix this! it's not working if the client disconnecting from
	 * the server If the client disconnected from the server, it will update the
	 * table in the server screen
	 * 
	 * @param client
	 */
	private void disconnectClient(ClientDetails client) {
		String clientAddress = client.getClientIP();
		System.out.println("Hi! I disconnected =)");

		for (ClientDetails currentClient : clients) {
			if (currentClient.getClientIP().equals(clientAddress)) {
				System.out.println("Client " + currentClient.getClientIP() + " found - disconnecting.");
				currentClient.setStatus("Disconnected");
				System.out
						.println("Client " + currentClient.getClientIP() + " status is - " + currentClient.getStatus());
			}
		}
	}

}
