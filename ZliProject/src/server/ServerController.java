package src.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import src.ocsf.server.AbstractServer;
import src.ocsf.server.ConnectionToClient;
import src.util.DataBaseController;

public class ServerController extends AbstractServer implements Runnable {

	private DataBaseController db;
	/**
	 * The default port to listen on.
	 */
	final public static int DEFAULT_PORT = 5555;

	//private static String connectedMessage;

	private int port;

	private String ip;

	//private List<?> connections = new ArrayList<>();

	//private Object monitor = new Object();

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
