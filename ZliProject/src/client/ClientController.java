// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package client;

import java.io.*;
import java.util.ArrayList;

import entities.Order;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import ocsf.client.AbstractClient;
import server.ServerController;


public class ClientController extends AbstractClient {
	/**
	 * The default port to connect on.
	 */
	final public static int DEFAULT_PORT = 5555;
	
	private static Object response; 
	
	public static boolean awaitResponse = false;
	
	private ServerController sv;

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
	public Object accept(Object message) {
		handleMessageFromClientUI(message);
		return response;
	}

	/**
	   * This method handles all data coming from the UI            
	   *
	   * @param message The message from the UI.    
	   */
	  
	  public void handleMessageFromClientUI(Object message) {
	    try {
	    	openConnection();//in order to send more than one message
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
	    }
	    catch(IOException e) {
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
		
		if(msg instanceof ArrayList) {
			if(!((ArrayList<?>)msg).isEmpty()) {
				Object message = ((ArrayList<?>)msg).get(0);
				if(message instanceof Order) {
					ArrayList<Order> messageOrders = (ArrayList<Order>) msg;  // list of orders
					Order messageOrder = (Order)message;
					int orderNumber = messageOrder.getOrderNumber();
					if(orderNumber == -1) { // is fetch all orders
						messageOrders.remove(0);  // get rid of indicative order
						response = FXCollections.observableArrayList(messageOrders);  // cast to ObservableList
					}
				}
			}
		}
	}
	
	/**
	   * This method terminates the client.
	   */
	public void quit() {
		try {
			closeConnection();
	    } catch(IOException e) {}
	    System.exit(0);
	  }
}
