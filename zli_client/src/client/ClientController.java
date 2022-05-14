// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package client;

import java.io.IOException;
import java.util.HashMap;

import ocsf.client.AbstractClient;
import util.ClientMessageController;

/**
 * ClientController extends the superclass AbstractClient for implementing and overriding the functions for our server-client project (zli)
 */
public class ClientController extends AbstractClient {
	/**
	 * The default port to connect on.
	 */
	final public static int DEFAULT_PORT = 5555;

	/**
	 * The response object that we got from the server after executing the query/command
	 */
	private static Object response;

	/**
	 * waiting for response after the client sent the message
	 */
	public static boolean awaitResponse = false;

	// private ServerController sv;

	/**
	 * Constructs an instance of the ClientConsole UI.
	 *
	 * @param host The host to connect to.
	 * @param port The port to connect on.
	 * @throws IOException
	 */
	public ClientController(String host) throws IOException {
		super(host, DEFAULT_PORT);
		openConnection();
	}

	@Override
	protected void connectionClosed() {
		return;
	}

	/**
	 * This method waits for input from the console. Once it is received, it sends
	 * it to the client's message handler.
	 */
	public Object accept(HashMap<String, Object> message) {
		handleMessageFromClientUI(message);
		return response;
	}

	/**
	 * This method handles all data coming from the UI
	 *
	 * @param message The message from the UI.
	 */

	public void handleMessageFromClientUI(HashMap<String, Object> message) {
		try {
			openConnection();// in order to send more than one message
			awaitResponse = true;
			sendToServer(message);
			// wait for response
			while (awaitResponse) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method handles all data that comes in from the server.
	 *
	 * @param msg The message from the server.
	 */
	@Override
	protected void handleMessageFromServer(Object msg) {
		awaitResponse = false;
		ClientMessageController.getClientMessageController().handleMessages(msg);
	}

	/**
	 * This method terminates the client.
	 */
	public void quit() {
		try {
			closeConnection();
		} catch (IOException e) {
		}
		System.exit(0);
	}

	/**Setting the response after the MessageController handled the message that been sent.
	 * @param response
	 */
	public static void setResponse(Object response) {
		ClientController.response = response;
	}
}
