package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import util.DataBaseController;

public class ServerController extends AbstractServer implements Runnable {
	private DataBaseController db;
	/**
	 * The default port to listen on.
	 */
	final public static int DEFAULT_PORT = 5555;

	private static String connectedMessage;

	private int port;

	private String ip;

	private List<?> connections = new ArrayList<>();

	private Object monitor = new Object();

	public ServerController(int port, String ip) {
		super(port);
		this.port = port;
		this.ip = ip;
	}

	public String connectToDB() {
		return DataBaseController.connect();
		// if (isConnected.contains("Driver definition failed") ||
		// isConnected.contains("Database connection failed!"))
	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		// connectionTable.getItems().add(new ConnectionDetails(ip, client.toString(),
		// "Connected"));
		return;
	}

	/**
	 * Disconnect server from the connection.
	 */
	public void disconnectServer() {
		try {
			close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Server started.
	 */
	protected void serverStarted() {
		synchronized (monitor) {
			connectedMessage = "Server listening for connections on port " + getPort();
			monitor.notifyAll();
		}
		// consoleField.setText("Server listening for connections on port " +
		// getPort());
	}

	/**
	 * Server stopped.
	 */
	protected void serverStopped() {
		synchronized (monitor) {
			connectedMessage = "Server has stopped listening for connections.";
			monitor.notifyAll();
		}

		// isStopped = true;
		// consoleField.setText("Server has stopped listening for connections.");
	}

	public String runServer() throws Exception {
		connectedMessage = "";
		try {
			listen(); // Start listening for connections in separate thread
		} catch (Exception e) {
			close();
			return "yayyy";
			//return "ERROR - Could not listen for clients!\n";
		}
		if (DataBaseController.isConnected) {
			System.out.println(DataBaseController.isConnected);
			synchronized (monitor) {
				if(connectedMessage.isEmpty()) connectedMessage = null;
				while (connectedMessage == null)
					monitor.wait();
			}
		}
		System.out.println(connectedMessage);
		return connectedMessage;
	}

//	private void resetFlags() {
//		isStarted = false;
//		isStopped = false;
//	}

	private class ConnectionDetails {
		private String ip;
		private String client;
		private String status;

		public ConnectionDetails(String ip, String client, String status) {
			this.ip = ip;
			this.client = client;
			this.status = status;
		}
	}

}
