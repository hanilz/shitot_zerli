package util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import deliveryCoordination.DeliveryCoordinatorView;
import entities.AccountPayment;
import entities.Branch;
import entities.Complaint;
import entities.CustomProduct;
import entities.Delivery;
import entities.Item;
import entities.ManageUsers;
import entities.ManagerOrderView;
import entities.Notification;
import entities.Order;
import entities.OrderCustomProduct;
import entities.OrderItem;
import entities.OrderProduct;
import entities.Product;
import entities.ProductsBase;
import entities.Report;
import entities.SurveyAnalysisFile;
import entities.SurveyQuestion;
import entities.UserDetails;
import ocsf.server.ConnectionToClient;
import ordersView.CustomerOrderView;
import server.ServerController;
import surveyAnalysis.QuestionAnswer;

public class ServerMessageController {

	private static ServerMessageController instance = null;
	private HashMap<String, Object> message;
	private String response;

	private ServerMessageController() {
	}

	public static ServerMessageController getServerMessageController() {
		if (instance == null)
			instance = new ServerMessageController();
		return instance;
	}

	/**
	 * This class will help us the control all the messages that the server receives
	 * and the server sending. for example: handling all the queries that the client
	 * sending.
	 */

	/**
	 * handleMessages handles the messages that the client send to the server. This
	 * function will execute the correct command that been sent.
	 * 
	 * @param msg
	 */
	@SuppressWarnings("unchecked")
	public void handleMessages(Object msg, ConnectionToClient client) {
		message = (HashMap<String, Object>) msg;
		Commands command = (Commands) message.get("command");
		switch (command) {
		case CHANGE_USER_STATUS:
			response = AnalayzeCommand.changeUserStatus(message.get("id"));
			message.put("response", response);
			break;
		case CLIENT_DISCONNECTED:
			String clientIP = client.getInetAddress().toString();
			System.out.println("client disconnected detected: ip is " + clientIP);
			if (message.get("logout") != null)
				AnalayzeCommand.logoutUser((int) message.get("logout"));
			for (ClientDetails currentClient : ServerController.clients) {
				if (clientIP.equals(currentClient.getClientIP())) {
					ServerController.getServerController().disconnectClient(currentClient);
					break;
				}
			}
			break;
		case FETCH_ALL_USERS_DETAILS:
			ArrayList<ManageUsers> users = AnalayzeCommand.selectAllUsers();
			message.put("response", users);
			break;
		case FETCH_BRANCHES:
			ArrayList<Branch> branches = AnalayzeCommand.selectAllBranches();
			message.put("response", branches);
			break;
		case FETCH_BRANCHES_PER_MANAGER:
			ArrayList<Branch> branchesPerManager = AnalayzeCommand
					.selectBranchesPerManager((Integer) message.get("manager id"));
			message.put("response", branchesPerManager);
			break;
		case FETCH_REPORTS:
			ArrayList<Report> reports = AnalayzeCommand.selectAllReports();
			message.put("response", reports);
			break;
		case FETCH_PRODUCTS:
			ArrayList<Product> products = AnalayzeCommand.selectAllProducts();
			message.put("response", products);
			break;
		case FETCH_SURVEYS:
			HashMap<Integer, String> surveys = AnalayzeCommand.selectSurveys();
			message.put("response", surveys);
			break;
		case FETCH_ACCOUNT_PAYMENTS:
			int idUserAccountPayment = (int) message.get("idUser");
			ArrayList<AccountPayment> accountPayments = AnalayzeCommand.selectAccountPayments(idUserAccountPayment);
			message.put("response", accountPayments);
			break;
		case INSERT_ACCOUNT_PAYMENT:
			AccountPayment accountPayment = (AccountPayment) message.get("account payment");
			boolean isInsert = AnalayzeCommand.insertAccountPayment(accountPayment);
			response = isInsert ? "insert account payment successful" : "insert account payment failed";
			message.put("response", response);
			break;
		case INSERT_DELIVERY:
			Delivery delivery = (Delivery) message.get("delivery");
			int idDeliveryOrder = (Integer) message.get("idOrder");
			boolean isExpress = delivery.getType().contains("express");
			int idDeliveryReturned = AnalayzeCommand.insertNewDelivery(delivery, idDeliveryOrder, isExpress);
			message.put("response", idDeliveryReturned);
			break;
		case INSERT_PICKUP:
			Delivery pickup = (Delivery) message.get("delivery");
			int idPickupOrder = (Integer) message.get("idOrder");
			int idPickupReturned = AnalayzeCommand.insertNewPickup(pickup, idPickupOrder);
			message.put("response", idPickupReturned);
			break;
		case INSERT_ORDER:
			Order order = (Order) message.get("order");
			Integer idOrderReturned = AnalayzeCommand.insertNewOrder(order);
			message.put("response", idOrderReturned);
			break;
		case INSERT_CUSTOM_PRODUCTS:
			ArrayList<CustomProduct> insertCustomProductsList = (ArrayList<CustomProduct>) message
					.get("custom products");
			insertCustomProductsList = AnalayzeCommand.insertCustomProducts(insertCustomProductsList);
			message.put("response", insertCustomProductsList);
			break;
		case INSERT_CUSTOM_PRODUCT_PRODUCTS:
			ArrayList<CustomProduct> insertCustomProductProductsList = (ArrayList<CustomProduct>) message
					.get("custom products");
			boolean isInsertCustomProductProducts = AnalayzeCommand
					.insertCustomProductProducts(insertCustomProductProductsList);
			response = isInsertCustomProductProducts ? "insert custom product products successful"
					: "insert custom product products failed";
			message.put("response", response);
			break;
		case INSERT_CUSTOM_PRODUCT_ITEMS:
			ArrayList<CustomProduct> insertCustomProductItemsList = (ArrayList<CustomProduct>) message
					.get("custom products");
			boolean isInsertCustomProductItems = AnalayzeCommand.insertCustomProductItems(insertCustomProductItemsList);
			response = isInsertCustomProductItems ? "insert custom product items successful"
					: "insert custom product items failed";
			message.put("response", response);
			break;
		case INSERT_ORDER_CUSTOM_PRODUCTS:
			ArrayList<OrderCustomProduct> insertOrderCustomProductsList = (ArrayList<OrderCustomProduct>) message
					.get("list order custom products");
			boolean isInsertOrderCustomProducts = AnalayzeCommand
					.insertOrderCustomProducts(insertOrderCustomProductsList);
			response = isInsertOrderCustomProducts ? "insert order custom products successful"
					: "insert order custom products failed";
			message.put("response", response);
			break;
		case INSERT_ORDERS_PRODUCT:
			ArrayList<OrderProduct> orderProductsList = (ArrayList<OrderProduct>) message.get("list order products");
			boolean isOrderInserted = AnalayzeCommand.insertOrderProducts(orderProductsList);
			response = isOrderInserted ? "insert order products successful" : "insert order products failed";
			message.put("response", response);
			break;
		case INSERT_ORDERS_ITEMS:
			ArrayList<OrderItem> orderItemsList = (ArrayList<OrderItem>) message.get("list order items");
			boolean isOrdersItemsInserted = AnalayzeCommand.insertOrderItems(orderItemsList);
			response = isOrdersItemsInserted ? "insert order items successful" : "insert order items failed";
			message.put("response", response);
			sendToClient(client);
			break;
		case LOGIN:
			message = AnalayzeCommand.loginUser((String) message.get("username"), (String) message.get("password"));
			message.put("command", Commands.LOGIN);
			break;
		case LOGOUT:
			message.put("logout", AnalayzeCommand.logoutUser((int) message.get("logoutID")));
			break;
		case FETCH_ITEMS:
			ArrayList<Item> items = AnalayzeCommand.selectAllItems();
			message.put("response", items);
			break;
		case GET_SERVEY:
			ArrayList<SurveyQuestion> returnedSurveys = AnalayzeCommand.getSurvey((Integer) message.get("surveyID"));
			message.put("response", returnedSurveys);
			break;
		case SUBMIT_SURVEY:
			boolean isSubmitted = AnalayzeCommand
					.submitSurvey((HashMap<SurveyQuestion, Integer>) message.get("answers"));
			response = isSubmitted ? "insert survey answer successful" : "insert survey answer failed";
			message.put("response", response);
			break;
		case SUBMIT_COMPLAINT:
			boolean compailntSubmited = AnalayzeCommand.submitComplaint((Complaint) message.get("Complaint"));
			message.put("response", compailntSubmited);
			break;
		case UPDATE_PRODUCTS_BASE:
			boolean isUpdated = message.get("type").equals("item")
					? AnalayzeCommand.updateItem((Item) message.get("unit"))
					: AnalayzeCommand.updateProduct((Product) message.get("unit"));
			message.put("response", isUpdated);
			break;
		case UPDATE_USER_TYPE_TO_CUSTOMER:
			boolean isUserTypeUpdated;
			int idUserToUpdateType = (int) message.get("idUser");
			isUserTypeUpdated = AnalayzeCommand.updateUserTypeToCustomer(idUserToUpdateType);
			response = isUserTypeUpdated ? "update user type to customer successful"
					: "update user type to customer failed";
			message.put("response", response);
			break;
		case UPDATE_USER_STORE_CREDIT:
			boolean isUserStoreCreditUpdated;
			double newStoreCredit = (double) message.get("store credit");
			int idUserToUpdateStoreCredit = (int) message.get("idUser");
			isUserStoreCreditUpdated = AnalayzeCommand.updateUserStoreCredit(newStoreCredit, idUserToUpdateStoreCredit);
			response = isUserStoreCreditUpdated ? "update user store credit successful"
					: "update user store credit failed";

			message.put("response", isUserStoreCreditUpdated);
			break;
		case GET_ORDER_NUMBERS:
			ArrayList<Integer> orderNumbers;
			orderNumbers = AnalayzeCommand.getOrderNumbers();
			message.put("response", orderNumbers);
			break;
		case FETCH_COMPLAINTS:
			ArrayList<Complaint> complaints;
			complaints = AnalayzeCommand.getAllComplaintsForManager((Integer) message.get("HandlerID"));
			message.put("response", complaints);
			break;
		case CLOSE_COMPLAINT:
			boolean ans = AnalayzeCommand.setComlaintClosedAndRefund((Integer) message.get("Complaint Number"),(Double) message.get("refund"));
			message.put("response", ans);
			break;
		case GET_ORDER_SUM:
			Double sum = AnalayzeCommand.getOrderPrice((Integer) message.get("Order Number"));
			message.put("response", sum);
			break;
		case NEW_USER_DETAILS:
			int idAccount = AnalayzeCommand.insertNewUserDetails((UserDetails) message.get("User Details"));
			message.put("response", idAccount);
			break;
		case NEW_USERS:
			int idUser = AnalayzeCommand.insertNewUser((String) message.get("username"),
					(String) message.get("password"), (int) message.get("idAccount"));
			message.put("response", idUser);
			break;
		case DELETE_USER_DETAILS:
			boolean remove = AnalayzeCommand.deleteUserDetails((int) message.get("idAccount"));
			message.put("response", remove);
			break;
		case FETCH_ORDERS_MANAGER:
			ArrayList<ManagerOrderView> orders = AnalayzeCommand
					.selectOrdersForManager((Integer) message.get("manager id"));
			message.put("response", orders);
			break;
		case APPROVE_ORDER:
			boolean approve = AnalayzeCommand.approveOrder((Integer) message.get("order id"),(String)message.get("orderType"));
			message.put("response", approve);
			break;
		case CANCEL_ORDER:
			boolean cancel = AnalayzeCommand.cancelOrder((Integer) message.get("order id"),
					(Double) message.get("refund"), (Integer) message.get("idUser"));
			message.put("response", cancel);
			break;
		case FETCH_NOTIFICATIONS:
			ArrayList<Notification> notifications = AnalayzeCommand.getNotification((Integer) message.get("idUser"));
			message.put("response", notifications);
			break;
		case SEND_NOTIFICATION:
			boolean sent = AnalayzeCommand.sendNotification((Integer) message.get("idUser"),
					(String) message.get("notification"));
			message.put("response", sent);
			break;
		case MARK_READ_NOTIFICATION:
			boolean read = AnalayzeCommand.markReadNotification((Integer) message.get("idNotification"));
			message.put("response", read);
			break;
		case DELETE_NOTIFICATION:
			boolean deleted = AnalayzeCommand.deleteNotification((Integer) message.get("idNotification"));
			message.put("response", deleted);
			break;
		case GET_SURVEY_ANSWERS:
			ArrayList<QuestionAnswer> questions = AnalayzeCommand.getSurveyAnswers((Integer) message.get("surveyID"));
			message.put("response", questions);
			break;
		case UPLOAD_FILE:
			//File saveFileToDB = FilesHandler.saveFile((SurveyAnalysisFile) message.get("FILE"));
			boolean uploaded = AnalayzeCommand.uploadFileToDB((File) message.get("FILE")); //and then send saveFileToDB
			message.put("response", uploaded);
			break;
		case FETCH_FILES:
			ArrayList<SurveyAnalysisFile> files = AnalayzeCommand.retrieveFileFromDB();
			message.put("response", files);
			break;
		case GET_ITEMS_INCOME_REPORT:
			Map<String, Integer> itemsIncomeLabels = AnalayzeCommand
					.getItemsIncomeReport((Report) message.get("selected report"));
			message.put("response", itemsIncomeLabels);
			break;
		case GET_PRODUCTS_INCOME_REPORT:
			Map<String, Integer> productsIncomeLabels = AnalayzeCommand
					.getProductsIncomeReport((Report) message.get("selected report"));
			message.put("response", productsIncomeLabels);
			break;
		case GET_ITEMS_ORDERS_REPORT:
			Map<String, Integer> itemsOrdersLabels = AnalayzeCommand
					.getItemsOrdersReport((Report) message.get("selected report"));
			message.put("response", itemsOrdersLabels);
			break;
		case GET_PRODUCTS_ORDERS_REPORT:
			Map<String, Integer> productsOrdersLabels = AnalayzeCommand
					.getProductsOrdersReport((Report) message.get("selected report"));
			message.put("response", productsOrdersLabels);
			break;
		case GET_COMPLAINT_REPORT:
			Map<String, Integer> complaintsReportAxis = AnalayzeCommand
					.getComplaintsReport((Report) message.get("selected report"));
			message.put("response", complaintsReportAxis);
			break;
		case GET_INCOME_HISTOGRAM_REPORT:
			Map<String, Integer> incomeHistogramReportAxis = AnalayzeCommand
					.getIncomeHistogramReport((Report) message.get("selected report"));
			message.put("response", incomeHistogramReportAxis);
			break;
		case GET_CUSTOM_INCOME_REPORT:
			Integer customIncome = AnalayzeCommand.getCustomIncomeReport((Report) message.get("selected report"));
			message.put("response", customIncome);
			break;
		case GET_CUSTOM_ORDERS_REPORT:
			Integer customOrders = AnalayzeCommand.getCustomOrdersReport((Report) message.get("selected report"));
			message.put("response", customOrders);
			break;
		case FETCH_ORDERS_FOR_CLIENT:
			ArrayList<CustomerOrderView> clientOrders = AnalayzeCommand
					.getCustomerOrders((Integer) message.get("idUser"));
			message.put("response", clientOrders);
			break;
		case FETCH_ORDER_CONTENT:
			HashMap<ProductsBase, Integer> productsInOrder = AnalayzeCommand
					.getOrderProducts((Integer) message.get("orderID"));
			productsInOrder.putAll(AnalayzeCommand.getOrderItems((Integer) message.get("orderID")));
			productsInOrder.putAll(AnalayzeCommand.getOrderCustomProducts((Integer) message.get("orderID")));
			message.put("response", productsInOrder);
			break;
		case GET_USER_SCREENS:
			ArrayList<Screens> userScreens = AnalayzeCommand.getUserHomeScreens((Integer) message.get("id"),
					(UserType) message.get("userType"));
			message.put("response", userScreens);
			break;
		case SAVE_SCREENS:
			System.out.println((Integer) message.get("id"));
			System.out.println((ArrayList<Screens>) message.get("screens"));
			boolean isSaved = AnalayzeCommand.insertScreens((Integer) message.get("id"),
					(ArrayList<Screens>) message.get("screens"));
			message.put("response", isSaved);
			break;
		case CANCEL_ORDER_REQUEST:
			boolean requested = AnalayzeCommand.cancelOrderRequest((int) message.get("order id"));
			message.put("response", requested);
			break;
		case REMOVE_PRODUCT_BASE:
			boolean isProductRemoved = message.get("type").equals("item")
					? AnalayzeCommand.removeItemFromDB((Integer) message.get("item"))
					: AnalayzeCommand.removeProductFromDB((Integer) message.get("product"));
			message.put("response", isProductRemoved);
			break;
		case GET_PRODUCT:
			Product updatedProduct = AnalayzeCommand.getSelectedProduct((Integer) message.get("product"));
			message.put("response", updatedProduct);
			break;
		case GET_ITEM:
			Item updatedItem = AnalayzeCommand.getSelectedItem((Integer) message.get("item"));
			message.put("response", updatedItem);
			break;
		case GET_TOTAL_REFUNDS:
			Integer totalRefunds = AnalayzeCommand.getTotalRefunds((Report) message.get("selected report"));
			message.put("response", totalRefunds);
			break;
		case FETCH_ORDERS_DELIVERY_COORDINATOR:
			ArrayList<DeliveryCoordinatorView> ordersReadyForDelivery = AnalayzeCommand
					.fetchOrdersForDeliveryCoordinator();
			message.put("response", ordersReadyForDelivery);
			break;
		case CONFIRM_DELIVERY:
			boolean delivered = AnalayzeCommand.markOrderAsDelivered((Integer) message.get("idOrder"));
			message.put("response", delivered);
			break;
		case REFUND_USER_FOR_LATE_DELIVERY:
			boolean refunded = AnalayzeCommand.refundUserForLateDelivery((Integer) message.get("idUser"),
					(Integer) message.get("idOrder"));
			message.put("response", refunded);
			break;
		default:
			break;
		}
		sendToClient(client);
	}

	private void sendToClient(ConnectionToClient client) {
		try {
			client.sendToClient(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
