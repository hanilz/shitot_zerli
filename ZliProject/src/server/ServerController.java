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

	private static boolean isConnected;
	
	private TextArea consoleField;
	
	private TableView<?> connectionTable;
	
	private int port;
	
	private String ip;
	
	private List<?> connections = new ArrayList<>();

	public ServerController(int port, String ip, TextArea consoleField, TableView<?> connectionTable) {
		super(port);
		this.port = port;
		this.ip = ip;
		this.consoleField = consoleField;
		this.connectionTable = connectionTable;
		db = new DataBaseController(consoleField);
		isConnected = db.connect();
		if (!isConnected) {
			return;
		}
		runServer();

	}

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		//connectionTable.getItems().add(new ConnectionDetails(ip, client.toString(), "Connected"));
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
		consoleField.setText("Server listening for connections on port " + getPort());
	}

	/**
	 * Server stopped.
	 */
	protected void serverStopped() {
		consoleField.setText("Server has stopped listening for connections.");
	}
	
	public void runServer() {
		try {
			listen(); // Start listening for connections
		} catch (Exception ex) {
			consoleField.setText("ERROR - Could not listen for clients!\n");
		}
	}
	
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
