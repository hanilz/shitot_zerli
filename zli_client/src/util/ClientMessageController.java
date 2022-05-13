package util;

import java.util.ArrayList;
import java.util.HashMap;

import client.ClientController;
import client.ClientScreen;
import entities.Branch;
import entities.Item;
import entities.Order;
import entities.Product;
import entities.ManageUsers;
import javafx.collections.FXCollections;

/**
 * This class will help us the control all the messages that the client and the
 * server sending. for example: handling all the queries that the client
 * sending.
 */
public class ClientMessageController {

	private static ClientMessageController instance = null;
	private HashMap<String, Object> message=null;
	private Object response=null;

	private ClientMessageController() {}

	public static ClientMessageController getClientMessageController() {
		if (instance == null)
			instance = new ClientMessageController();
		return instance;
	}

	/**
	 * handleMessages handles the messages that the client send to the server. This
	 * function will execute the correct command that been sent.
	 * 
	 * @param msg
	 */
	@SuppressWarnings("unchecked")
	public void handleMessages(Object msg) {//from server To client
		message = (HashMap<String, Object>) msg;
		String command = (String) message.get("command");
//start of if commands
		if (command.contains("fetch orders")) {
			ArrayList<Order> ordersList = (ArrayList<Order>) message.get("response");
			if (!ordersList.isEmpty()) {
				Order messageOrder = ordersList.get(0);
				int orderNumber = messageOrder.getIdOrder();
				switch (orderNumber) { // because we will use the orders table for the next assignment, we will use
										// switch-case for extending the behavior of the orders table queries
				case -1: // is fetch all orders
					ordersList.remove(0); // get rid of indicative order
					break;
				}
			}
			returnServerListRespond(ordersList);
		} else if (command.contains("fetch items")) {
			returnServerListRespond(new ArrayList<Item>());
		}
		
		else if (command.contains("server disconnected")) {
				System.out.println("disconnected from server, back to client main.");
				ManageScreens.changeScreenTo(Screens.CLIENT);
		}
		else if (command.contains("client disconnected")) {
				ClientController.setResponse("client disconnected");
		}
		else if(command.contains("logout"))
				ClientController.setResponse((boolean)message.get("logout"));//logout 
		
		else if (command.contains("login user")) {
				ClientController.setResponse(message);
		}
		
		else if(command.contains("fetch products")) {
			returnServerListRespond(new ArrayList<Product>());
		}	

		else if(command.contains("fetch branches")) {
			returnServerListRespond(new ArrayList<Branch>());
		}

		else if(command.contains("fetch all user details")) {
			returnServerListRespond(new ArrayList<ManageUsers>());
		}
		//////////else
		else if(command.equals("change user status")) {
			returnServerRespond();
		}
		
		else if(command.equals("insert account payment")) {
			returnServerRespond();
		}
		
		else if(command.equals("insert order")) {
			returnServerRespond();
		}
		
		else if(command.equals("insert order products")) {
			returnServerRespond();
		}
		
		else if(command.equals("insert delivery")) {
			returnServerRespond();
		}
		
		else if(command.equals("insert delivery order")) {
			returnServerRespond();
		}
		
		else if(command.equals("Fetch Surveys")) {
			returnServerRespond();
		}
		//////////else
	}
	private void returnServerRespond()
	{
		ClientController.setResponse(message.get("response"));
	}
	private void returnServerListRespond(ArrayList<?> listResponse)
	{
		ArrayList<?> respondAsList=((listResponse.isEmpty())?(ArrayList<?>)message.get("response"):listResponse);
		response = FXCollections.observableArrayList(respondAsList);
		ClientController.setResponse(response);
	}
	

}
