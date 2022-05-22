package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import client.ClientFormController;
import customerComplaint.Complaint;
import gui.ServerFormController;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import notifications.NotificationController;
import notifications.NotificationManager;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import util.AnalayzeCommand;
import util.ClientDetails;
import util.Commands;
import util.DataBaseController;
import util.NotificationType;
import util.ServerMessageController;

/**
 * ServerController extends the superclass AbstractServer for implementing and
 * overriding the functions for our server-client project (zli)
 */
public class ServerController extends AbstractServer implements Runnable {

	/**
	 * The default port to listen on.
	 */
	final public static int DEFAULT_PORT = 5555;

	/**
	 * Saving the ip of the server
	 */
	private String ip;

	/**
	 * Saving all the client that connected to the server so the server screen will
	 * present the clients
	 */
	public static final ObservableList<ClientDetails> clients = FXCollections.observableArrayList();

	// singleton
	private static ServerController sc = null;

	private ServerController() {
		super(DEFAULT_PORT);
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public static ServerController getServerController() {
		if (sc == null)
			sc = new ServerController();
		return sc;
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
		ServerMessageController.getServerMessageController().handleMessages(msg, client);
	}

	/**
	 * Disconnect server from the connection and disconnecting all the clients.
	 */
	public void disconnectServer() {
		try {
			HashMap<String, Object> message = new HashMap<>();

			message.put("command", Commands.SERVER_DISCONNEDTED);
			sendToAllClients(message); // make all clients go back to main client screen
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
		runComplaintsThread();
		return isListening() ? "Server listening for connections on port " + getPort()
				: "Server has stopped listening for connections.";
	}

	private	synchronized void runComplaintsThread() {
		Thread complaints = new Thread(new Runnable() {
			@Override
			public void run() {
				ArrayList<Complaint> complaintList;
				while(true) {
					complaintList = AnalayzeCommand.getAllComplaintsForServer();
					if(!complaintList.isEmpty()) {
						for (Iterator<Complaint> iterator = complaintList.iterator(); iterator.hasNext();) {
							Complaint comp = iterator.next();
							if(comp.getRemainingMinutes()<=0) 
								util.ServerNotificationManager.sendNotification(comp.getIdUser(),NotificationType.COMPLAINT_DUE,comp.getComplaintID() );
							else
								iterator.remove();
						}
						AnalayzeCommand.updateComplaintsStatus(complaintList);						
					}
					try {
						Thread.sleep(60000);
					} catch (InterruptedException e) {
						System.out.println(e);
					}
					
				}
			}
		});
		complaints.start();
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
				ServerFormController.get().refreshClientsTable();
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
	public void disconnectClient(ClientDetails client) {
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
		ServerFormController.get().refreshClientsTable();
	}
}
