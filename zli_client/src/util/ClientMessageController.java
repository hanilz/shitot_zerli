package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import client.ClientController;
import entities.AccountPayment;
import entities.Branch;
import entities.Complaint;
import entities.Item;
import entities.ManageUsers;
import entities.Product;
import entities.Report;
import javafx.collections.FXCollections;


/**
 * This class will help us the control all the messages that the client and the
 * server sending. for example: handling all the queries that the client
 * sending.
 */
public class ClientMessageController {

	/**
	 * Controller instance used for singleton
	 */
	private static ClientMessageController instance = null;
	/**
	 * Data structure used to transfer information
	 */
	private HashMap<String, Object> message = null;
	
	/**
	 * save response from communication between server and client
	 */
	private Object response = null;

	/**
	 * Constructor for singleton
	 */
	private ClientMessageController() {
	}

	/**
	 * @return controller (singleton)
	 */
	public static ClientMessageController getClientMessageController() {
		if (instance == null)
			instance = new ClientMessageController();
		return instance;
	}

	/**
	 * @param msg message from server
	 * Receive message from server and return client the proper respond
	 */
	@SuppressWarnings("unchecked")
	public void handleMessages(Object msg) {
		message = (HashMap<String, Object>) msg;
		Commands command = (Commands) message.get("command");

		switch (command) {
		case CLIENT_DISCONNECTED:
			ClientController.setResponse("client disconnected");
			break;
		case FETCH_ALL_USERS_DETAILS:
			returnServerListRespond(new ArrayList<ManageUsers>());
			break;
		case FETCH_ALL_EMPLOYEES:
			returnServerListRespond(new ArrayList<ManageUsers>());
			break;
		case FETCH_BRANCHES:
			returnServerListRespond(new ArrayList<Branch>());
			break;
		case FETCH_BRANCHES_PER_MANAGER:
			returnServerListRespond(new ArrayList<Branch>());
			break;
//		case FETCH_ITEMS:
//			returnServerListRespond(new ArrayList<Item>());
//			break;
		case FETCH_REPORTS:
			returnServerListRespond(new ArrayList<Report>());
			break;
		case FETCH_ORDERS:
			break;
//		case FETCH_PRODUCTS:
//			returnServerListRespond(new ArrayList<Product>());
//			break;
		case FETCH_ACCOUNT_PAYMENTS:
			returnServerListRespond(new ArrayList<AccountPayment>());
			break;
		case LOGIN:
			ClientController.setResponse(message);
			break;
		case LOGOUT:
			ClientController.setResponse((boolean) message.get("logout"));
			break;
		case SERVER_DISCONNEDTED:
			ManageScreens.closeAllPopups();;
			System.out.println("disconnected from server, back to client main.");
			ManageScreens.changeScreenTo(Screens.CLIENT);
			break;
		case GET_ORDER_NUMBERS:
			returnServerListRespond(new ArrayList<Integer>());
			break;
		case FETCH_COMPLAINTS:
			returnServerListRespond(new ArrayList<Complaint>());
			break;
		case GET_ITEMS_INCOME_REPORT:
			returnServerMapRespond(new HashMap<String,Integer>());
			break;
		case GET_PRODUCTS_INCOME_REPORT:
			returnServerMapRespond(new HashMap<String,Integer>());
			break;
		case GET_ITEMS_ORDERS_REPORT:
			returnServerMapRespond(new HashMap<String,Integer>());
			break;
		case GET_PRODUCTS_ORDERS_REPORT:
			returnServerMapRespond(new HashMap<String,Integer>());
			break;
		case GET_COMPLAINT_REPORT:
			returnServerMapRespond(new HashMap<String,Integer>());
			break;
		case GET_INCOME_HISTOGRAM_REPORT:
			returnServerMapRespond(new HashMap<String,Integer>());
			break;
		default:
			returnServerRespond();
			break;
		}
	}

	/**
	 * set client the respond in the hashMap under key "response"
	 */
	private void returnServerRespond() {
		ClientController.setResponse(message.get("response"));
	}

	/** 
	 * set client the respond in the hashMap as a given ArrayList<>
	 */
	private void returnServerListRespond(ArrayList<?> listResponse) {
		ArrayList<?> respondAsList = ((listResponse.isEmpty()) ? (ArrayList<?>) message.get("response") : listResponse);
		response = FXCollections.observableArrayList(respondAsList);
		ClientController.setResponse(response);
	}
	
	/**
	 * set client the respond in the hashMap as a given Map<,>
	 */
	private void returnServerMapRespond(Map<?,?> mapResponse) {
		Map<?,?> respondAsMap = ((mapResponse.isEmpty()) ? (Map<?,?>) message.get("response") : mapResponse);
		response = respondAsMap;
		ClientController.setResponse(response);
	}

}
