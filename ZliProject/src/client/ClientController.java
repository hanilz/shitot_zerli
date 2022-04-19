// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package client;

import java.io.*;
import ocsf.client.AbstractClient;

public class ClientController extends AbstractClient {
	// Class variables *************************************************

	/**
	 * The default port to connect on.
	 */
	final public static int DEFAULT_PORT = 5555;

	// Instance variables **********************************************

	/**
	 * The instance of the client that created this ConsoleChat.
	 */
	// Constructors ****************************************************

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

	// Instance methods ************************************************

	/**
	 * This method waits for input from the console. Once it is received, it sends
	 * it to the client's message handler.
	 */
	public void accept(String str) {
		// client.handleMessageFromClientUI(str);
		return;
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		// TODO Auto-generated method stub

	}
}
