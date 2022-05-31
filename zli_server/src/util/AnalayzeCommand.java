package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
import entities.OrderItem;
import entities.OrderProduct;
import entities.Product;
import entities.ProductsBase;
import entities.Report;
import entities.SurveyQuestion;
import entities.UserDetails;
import ordersView.CustomerOrderView;
import surveyAnalysis.QuestionAnswer;

/**
 * AnaylzeCommand - will anaylze the command that given from the server
 * controller and will execute the sql query.
 */
public class AnalayzeCommand {

	public static boolean isUpdated = false;

	public static ArrayList<Product> selectAllProducts() {
		ArrayList<Product> products = new ArrayList<>();
		HashMap<Integer, HashMap<Item, Integer>> products_items = new HashMap<>(); // {productID : items}
		try {
			Statement selectStmt = DataBaseController.getConn().createStatement();
			ResultSet rs = selectStmt.executeQuery(
					"SELECT p.*, pi.quantity, i.* FROM products p JOIN product_items pi ON p.productID = pi.idProduct JOIN items i ON pi.idItem = i.itemID ORDER BY p.productID;");
			while (rs.next()) {
				int productId = rs.getInt(1);
				Item currentItem = getItemFromResultSet(rs);
				if (!products_items.containsKey(productId)) {
					String productName = rs.getString(2);
					String flowerType = rs.getString(3);
					String productColor = rs.getString(4);
					double productPrice = rs.getDouble(5);// double
					String productType = rs.getString(6);
					String productDesc = rs.getString(7);
					String imagePath = rs.getString(8);
					int discount = rs.getInt(9);
					Product productResult = new Product(productId, productName, productColor, productPrice, productType,
							imagePath, discount, flowerType, productDesc);
					products.add(productResult);
					HashMap<Item, Integer> items = new HashMap<>();
					items.put(currentItem, rs.getInt(10));
					products_items.put(productId, items);
				} else
					products_items.get(productId).put(currentItem, rs.getInt(9));
			}

			for (Product product : products)
				product.setItems(products_items.get(product.getId()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return products;
	}

	private static Item getItemFromResultSet(ResultSet rs) throws SQLException {
		int itemID = rs.getInt(11);
		String itemName = rs.getString(12);
		String itemColor = rs.getString(13);
		double itemPrice = rs.getDouble(14);
		String itemType = rs.getString(15);
		String imagePath = rs.getString(16);
		int discount = rs.getInt(17);
		Item itemResult = new Item(itemID, itemName, itemColor, itemPrice, itemType, imagePath, discount);

		return itemResult;
	}

	public static ArrayList<Item> selectAllItems() {
		ArrayList<Item> items = new ArrayList<>();
		try {
			Statement selectStmt = DataBaseController.getConn().createStatement();
			ResultSet rs = selectStmt.executeQuery("SELECT * FROM items;");
			while (rs.next()) {
				int itemID = rs.getInt(1);
				String itemName = rs.getString(2);
				String itemColor = rs.getString(3);
				double itemPrice = rs.getDouble(4);
				String itemType = rs.getString(5);
				String imagePath = rs.getString(6);
				int ratio = rs.getInt(7);
				Item itemResult = new Item(itemID, itemName, itemColor, itemPrice, itemType, imagePath, ratio);
				items.add(itemResult);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return items;
	}

	public static ArrayList<Branch> selectAllBranches() {
		ArrayList<Branch> branches = new ArrayList<>();
		try {
			Statement selectStmt = DataBaseController.getConn().createStatement();
			ResultSet rs = selectStmt.executeQuery("SELECT * FROM branches;");
			while (rs.next()) {
				int idBranch = rs.getInt(1);
				String city = rs.getString(2);
				String address = rs.getString(3);
				String region = rs.getString(4);

				Branch branchResult = new Branch(idBranch, city, address, region);
				branches.add(branchResult);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return branches;
	}

	public static ArrayList<Branch> selectBranchesPerManager(int userId) {
		ArrayList<Branch> branches = new ArrayList<>();
		try {
			Statement selectStmt = DataBaseController.getConn().createStatement();
			ResultSet rs = selectStmt.executeQuery("SELECT * FROM branches where idManager=" + userId + ";");
			while (rs.next()) {
				int idBranch = rs.getInt(1);
				String city = rs.getString(2);
				String address = rs.getString(3);
				String region = rs.getString(4);
				Branch branchResult = new Branch(idBranch, city, address, region);
				branches.add(branchResult);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return branches;

	}

	public static void updateOrder(String orderNumber, String date, String color) {
		try {
			int sendOrderNumberDB = Integer.parseInt(orderNumber);
			PreparedStatement stmt = DataBaseController.getConn()
					.prepareStatement("UPDATE orders SET color = ?, date = ? WHERE orderNumber = ?");
			stmt.setString(1, color);
			stmt.setString(2, date);
			stmt.setInt(3, sendOrderNumberDB);

			isUpdated = true;
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			isUpdated = false;
			return;
		}
	}

	public static ArrayList<Screens> getUserHomeScreens(int userId, UserType userType) {
		Connection conn;
		ArrayList<Screens> userHomeScreens = new ArrayList<Screens>();
		conn = DataBaseController.getConn();
		ResultSet rs;
		String query = "SELECT screen FROM user_screen WHERE idUser=?;";
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, userId);
			rs = preparedStmt.executeQuery();
			if (!rs.next()) {
				insertScreens(userId,ManageClients.getUserScreens(userType));//save default in screens not exist
				userHomeScreens.addAll(ManageClients.getUserScreens(userType));
			}
			else {
				rs.previous();
				while (rs.next()) {
					userHomeScreens.add(Screens.valueOf((rs.getString(1))));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userHomeScreens;
	}

	public static HashMap<String, Object> loginUser(String username, String password) {
		Connection conn;
		Status status = Status.NOT_REGISTERED;
		HashMap<String, Object> login = new HashMap<>();
		conn = DataBaseController.getConn();
		ResultSet rs;
		String query = "SELECT * FROM users WHERE username=? AND password=?";
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, username);
			preparedStmt.setString(2, password);
			rs = preparedStmt.executeQuery();
			if (!rs.next())// if user not exist
				status = Status.NOT_REGISTERED;
			else if (rs.getString(7).toLowerCase().equals("suspended"))// if user is suspended
				status = Status.SUSPENDED;
			else {
				if (rs.getBoolean(6))// if user already logged in
					status = Status.ALREADY_LOGGED_IN;
				else {
					login.put("idUser", Integer.parseInt(rs.getString(1)));
					login.put("idAccount", Integer.parseInt(rs.getString(4)));
					login.put("userType", UserType.get(rs.getString(5)));
					query = "UPDATE users SET isLogin = ? WHERE username = ? AND password = ?";
					preparedStmt = conn.prepareStatement(query);
					preparedStmt.setInt(1, 1);
					preparedStmt.setString(2, username);
					preparedStmt.setString(3, password);
					if (preparedStmt.executeUpdate() == 1)
						status = Status.NEW_LOG_IN;

				}
			}
		} catch (SQLException e) {
			System.out.println("failed to fetch user");
			e.printStackTrace();
		}

		login.put("response", status);// status of login
		return login;// default for any throw would be unregistered
	}

	public static boolean logoutUser(int idUser) {
		Connection conn;
		conn = DataBaseController.getConn();
		String query = "UPDATE users SET isLogin = ? WHERE idUser = ?";
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, 0);
			preparedStmt.setInt(2, idUser);
			System.out.println(preparedStmt.executeUpdate());
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// this method is used to get all the users from user_details table
	public static ArrayList<ManageUsers> selectAllUsers() {
		System.out.println("getting all users");
		ArrayList<ManageUsers> users = new ArrayList<>();
		try {
			Statement selectStmt = DataBaseController.getConn().createStatement();
			ResultSet rs = selectStmt.executeQuery(
					"SELECT U.idUser, UD.firstName, UD.lastName, UD.id,U.userType, U.status FROM users U, user_details UD where U.idAccount = UD.idAccount;");
			while (rs.next()) {
				int idUser = rs.getInt(1);
				String firstName = rs.getString(2);
				String lastName = rs.getString(3);
				String id = rs.getString(4);
				String userType = rs.getString(5);
				String status = rs.getString(6);
				ManageUsers resultOrder = new ManageUsers(idUser, firstName, lastName, id, userType, status);
				users.add(resultOrder);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("returned users");
		return users;
	}

	// this methods gets an Account id and changes the status of the user
	public static String changeUserStatus(Object object) {

		System.out.println("Changing user status, id: " + (int) object);
		Connection conn = DataBaseController.getConn();
		ResultSet rs;
		int id = (Integer) object;
		String response = "failed";
		try {
			// first we get the current user status
			String query = "SELECT * FROM users WHERE idUser = ?";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, id);
			rs = preparedStmt.executeQuery();
			if (!rs.next())
				return "failed";
			String userStatus = rs.getString(7);
			// preparedStmt = conn.prepareStatement(query);
			// second we find the user again and change its status to be the inverse of the
			// current status
			query = "UPDATE users SET status = ? WHERE idUser = ?";
			preparedStmt = conn.prepareStatement(query);
			if (userStatus.equals("Active")) {
				preparedStmt.setString(1, "Suspended");
				response = "Suspended";
			} else {
				preparedStmt.setString(1, "Active");
				response = "Active";
			}
			preparedStmt.setInt(2, id);
			preparedStmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("failed to fetch user");
			e.printStackTrace();
		}

		return response;
	}

	public static boolean insertAccountPayment(AccountPayment accountPayment) {
		Connection conn;
		conn = DataBaseController.getConn();
		String query = "INSERT INTO account_payment (fullName, cardNumber, cardDate, cardVCC, idUser) VALUES (?, ?, ?, ?, ?);";
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, accountPayment.getFullName());
			preparedStmt.setString(2, accountPayment.getCardNumber());
			preparedStmt.setString(3, accountPayment.getCardDate());
			preparedStmt.setString(4, accountPayment.getCardVCC());
			if (accountPayment.getIdUser() == -1)
				preparedStmt.setInt(5, accountPayment.getUser().getIdUser());
			else
				preparedStmt.setInt(5, accountPayment.getIdUser());
			preparedStmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// insert new user into user_details table
	public static int insertNewUserDetails(UserDetails userDetails) {
		int idAccount = -1;
		Connection conn;
		conn = DataBaseController.getConn();
		String query = "INSERT INTO user_details (firstName, lastName, id, email, phoneNumber) VALUES (?, ?, ?, ?, ?);";
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStmt.setString(1, userDetails.getFirstName());
			preparedStmt.setString(2, userDetails.getLastName());
			preparedStmt.setString(3, userDetails.getId());
			preparedStmt.setString(4, userDetails.getEmail());
			preparedStmt.setString(5, userDetails.getPhoneNumber());
			preparedStmt.executeUpdate();
			ResultSet rs = preparedStmt.getGeneratedKeys();
			if (rs.next())
				idAccount = rs.getInt(1);
			return idAccount;
		} catch (SQLException e) {
			// e.printStackTrace();
			return -1;
		}
	}

	// insert new user into users table
	public static int insertNewUser(String username, String password, int idAccount) {
		int iduser = -1;
		Connection conn;
		conn = DataBaseController.getConn();
		String query = "INSERT INTO users (username, password, idAccount) VALUES (?, ?, ?);";
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStmt.setString(1, username);
			preparedStmt.setString(2, password);
			preparedStmt.setInt(3, idAccount);
			preparedStmt.executeUpdate();
			ResultSet rs = preparedStmt.getGeneratedKeys();
			if (rs.next())
				iduser = rs.getInt(1);
			return iduser;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public static int insertNewOrder(Order order) {
		Connection conn;
		int idOrder = -1;
		conn = DataBaseController.getConn();
		ResultSet rs = null;
		String query = "INSERT INTO orders (price, greetingCard, dOrder, idBranch, status, paymentMethod, idUser) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStmt.setDouble(1, order.getPrice());
			preparedStmt.setString(2, order.getGreetingCard());
			preparedStmt.setString(3, order.getdOrder());
			preparedStmt.setInt(4, order.getBranch().getIdBranch());
			preparedStmt.setString(5, order.getStatus());
			preparedStmt.setString(6, order.getPaymentMethod());
			preparedStmt.setInt(7, order.getUser().getIdUser());
			preparedStmt.executeUpdate();
			rs = preparedStmt.getGeneratedKeys();
			if (rs.next())
				idOrder = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idOrder;
	}

	public static boolean insertOrderProducts(ArrayList<OrderProduct> orderProductsList) {
		StringBuffer buff = new StringBuffer();
		buff.append("INSERT INTO order_products (idOrder, idProduct, quantity) VALUES ");
		for (OrderProduct currentInsert : orderProductsList) {
			buff.append("(" + currentInsert.getIdOrder() + "," + currentInsert.getProduct().getId() + ","
					+ currentInsert.getQuantity() + "),");
		}
		buff.deleteCharAt(buff.length() - 1);
		buff.append(";");
		try {
			Connection conn;
			conn = DataBaseController.getConn();
			PreparedStatement preparedStmt = conn.prepareStatement(buff.toString());
			preparedStmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean insertOrderItems(ArrayList<OrderItem> orderItemsList) {
		StringBuffer buff = new StringBuffer();
		buff.append("INSERT INTO order_items (idOrder, idItem, quantity) VALUES ");
		for (OrderItem currentInsert : orderItemsList) {
			buff.append("(" + currentInsert.getIdOrder() + "," + currentInsert.getItem().getId() + ","
					+ currentInsert.getQuantity() + "),");
		}
		buff.deleteCharAt(buff.length() - 1);
		buff.append(";");
		try {
			Connection conn;
			conn = DataBaseController.getConn();
			PreparedStatement preparedStmt = conn.prepareStatement(buff.toString());
			preparedStmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static int insertNewDelivery(Delivery delivery) {
		Connection conn;
		ResultSet rs = null;
		int idOrder = -1;
		conn = DataBaseController.getConn();
		String query = "INSERT INTO deliveries (address, receiverName, phoneNumber, deliveryDate, status) VALUES (?, ?, ?, ?, ?);";
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStmt.setString(1, delivery.getAddress());
			preparedStmt.setString(2, delivery.getReceiverName());
			preparedStmt.setString(3, delivery.getPhoneNumber());
			preparedStmt.setString(4, delivery.getDeliveryDate());
			preparedStmt.setString(5, delivery.getStatus());
			preparedStmt.executeUpdate();
			rs = preparedStmt.getGeneratedKeys();
			if (rs.next())
				idOrder = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idOrder;
	}

	public static boolean insertDeliveryOrder(int idOrder, int idDelivery) {
		Connection conn;
		conn = DataBaseController.getConn();
		String query = "INSERT INTO deliveries_orders (idOrder, idDelivery) VALUES (?, ?);";
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, idOrder);
			preparedStmt.setInt(2, idDelivery);
			preparedStmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static HashMap<Integer, String> selectSurveys() {
		HashMap<Integer, String> surveys = new HashMap<>();
		try {
			Statement selectStmt = DataBaseController.getConn().createStatement();
			ResultSet rs = selectStmt.executeQuery("SELECT s.idSurvey,s.surveyName FROM surveys s;");
			while (rs.next()) {
				int idSurvey = rs.getInt(1);
				String surveyName = rs.getString(2);
				surveys.put(idSurvey, surveyName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("returned surveys");
		return surveys;
	}

	public static ArrayList<SurveyQuestion> getSurvey(int surveyID) {
		ArrayList<SurveyQuestion> questions = new ArrayList<>();
		Connection conn = DataBaseController.getConn();
		String query = "SELECT * FROM survey_questions where idSurvey = ?;";
		try {
			// Statement selectStmt = DataBaseController.getConn().createStatement(); why???
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, surveyID);
			ResultSet rs = preparedStmt.executeQuery();
			while (rs.next()) {
				questions.add(new SurveyQuestion(rs.getInt(1), rs.getInt(2), rs.getString(3)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("returned qustions for survey: " + surveyID);
		return questions;
	}

	public static boolean submitSurvey(HashMap<SurveyQuestion, Integer> answers) {
		StringBuffer buff = new StringBuffer();
		buff.append("INSERT INTO survey_answers (idQuestion, answer) VALUES ");
		for (SurveyQuestion sq : answers.keySet()) {
			buff.append("(" + sq.getQuestionID() + "," + answers.get(sq) + "),");
		}
		buff.deleteCharAt(buff.length() - 1);
		buff.append(";");
		try {
			Connection conn;
			conn = DataBaseController.getConn();
			PreparedStatement preparedStmt = conn.prepareStatement(buff.toString());
			preparedStmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean submitComplaint(Complaint complaint) {
		Connection conn = DataBaseController.getConn();
		String query = "INSERT INTO complaints (idUser, orderId, reason, content) VALUES (?, ?, ?, ?)";
		PreparedStatement preparedStmt;
		try {
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, complaint.getIdUser());
			preparedStmt.setInt(2, complaint.getOrderID());
			preparedStmt.setString(3, complaint.getComplaintReason());
			preparedStmt.setString(4, complaint.getComplaintContent());
			preparedStmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println("Failed to insert complaint");
			e.printStackTrace();
			return false;
		}
	}

	public static ArrayList<Complaint> getAllComplaintsForManager(Integer handlerID) {
		Connection conn = DataBaseController.getConn();
		ArrayList<Complaint> complaints = new ArrayList<>();
		try {
			String query = "SELECT C.*, TIMESTAMPDIFF(MINUTE, C.date, now()) AS 'diff_of_day' FROM complaints C WHERE C.idUser = ? AND (C.status = 'Active' OR C.status = 'Due');";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, handlerID);
			ResultSet rs = preparedStmt.executeQuery();
			while (rs.next()) {
				int complaintID = rs.getInt(1);
				int idUser = rs.getInt(2);
				int orderID = rs.getInt(3);
				int date = rs.getInt(8);
				String status = rs.getString(5);
				String complaintReason = rs.getString(6);
				String complaintContent = rs.getString(7);
				Complaint complaint = new Complaint(complaintID, idUser, orderID, date, status, complaintReason,
						complaintContent);
				complaints.add(complaint);
			}
			// String name
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return complaints;
	}

	public static ArrayList<Complaint> getAllComplaintsForServer() {
		Connection conn = DataBaseController.getConn();
		ArrayList<Complaint> complaints = new ArrayList<>();
		try {
			String query = "SELECT C.*, TIMESTAMPDIFF(MINUTE, C.date, now()) AS 'diff_of_day' FROM complaints C WHERE C.status = 'Active';";
			Statement Stmt = conn.createStatement();
			ResultSet rs = Stmt.executeQuery(query);
			while (rs.next()) {
				int complaintID = rs.getInt(1);
				int idUser = rs.getInt(2);
				int orderID = rs.getInt(3);
				int date = rs.getInt(8);
				String status = rs.getString(5);
				String complaintReason = rs.getString(6);
				String complaintContent = rs.getString(7);
				Complaint complaint = new Complaint(complaintID, idUser, orderID, date, status, complaintReason,
						complaintContent);
				complaints.add(complaint);
			}
			// String name
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return complaints;
	}

	// updates complaint status for complaint that are due
	public static void updateComplaintsStatus(ArrayList<Complaint> complaintList) {
		Connection conn;
		conn = DataBaseController.getConn();
		String query = "UPDATE complaints SET status = 'Due' WHERE idComplaint = ?";
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			for (Complaint comp : complaintList) {
				preparedStmt.setInt(1, comp.getComplaintID());
				preparedStmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static boolean updateItem(Item item) {
		Connection conn;
		conn = DataBaseController.getConn();
		String query = "UPDATE items SET itemPrice=? , discount = ? WHERE itemID = ?";
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setDouble(1, item.getPrice());
			preparedStmt.setDouble(2, item.getDiscount());
			preparedStmt.setInt(3, item.getId());
			preparedStmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean updateProduct(Product product) {
		Connection conn;
		conn = DataBaseController.getConn();
		String query = "UPDATE products SET productPrice=?, productDescription=?, discount = ? WHERE productID = ?;";
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setDouble(1, product.getPrice());
			preparedStmt.setString(2, product.getProductDescription());
			preparedStmt.setDouble(3, product.getDiscount());
			preparedStmt.setInt(4, product.getId());
			preparedStmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static ArrayList<Integer> getOrderNumbers() {
		ArrayList<Integer> orders = new ArrayList<>();
		Connection conn = DataBaseController.getConn();
		String query = "SELECT O.idOrder FROM orders O WHERE O.idOrder NOT IN (SELECT C.orderId FROM complaints C);";
		Statement selectStmt;
		try {
			selectStmt = conn.createStatement();
			ResultSet rs = selectStmt.executeQuery(query);
			while (rs.next()) {
				int idOrder = rs.getInt(1);
				orders.add(idOrder);
			}
		} catch (SQLException e) {
			System.out.println("failed to fetch order numbers");
			e.printStackTrace();
		}
		return orders;
	}

	public static boolean deleteComlaint(int complaintID) {
		Connection conn = DataBaseController.getConn();
		String query = "UPDATE complaints SET status = 'Closed' WHERE idComplaint = ? ;";
		// String query = "UPDATE FROM complaints WHERE idComplaint = ?;";
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, complaintID);
			int row = preparedStmt.executeUpdate();
			if (row == 0)
				return false;
		} catch (SQLException e) {
			System.out.println("failed to fetch order numbers");
			e.printStackTrace();
		}
		return true;
	}

	public static Double getOrderPrice(Integer orderNum) {
		double idOrder = -1;
		Connection conn = DataBaseController.getConn();

		String query = "SELECT O.price FROM orders O WHERE O.idOrder = ?;";

		try {
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, orderNum);
			ResultSet rs = preparedStmt.executeQuery();
			if (!rs.next()) {
				System.out.println("failed to fetch order price");
			}
			idOrder = rs.getDouble(1);
		} catch (SQLException e) {
			System.out.println("failed to fetch order numbers");
			e.printStackTrace();
		}
		return idOrder;
	}

	public static boolean deleteUserDetails(int idAccount) {
		Connection conn = DataBaseController.getConn();
		String query = "DELETE FROM user_details WHERE idAccount = ?;";
		PreparedStatement preparedStmt;
		try {
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, idAccount);
			preparedStmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println("Failed to delete user_details");
			e.printStackTrace();
			return false;
		}
	}

	public static ArrayList<ManagerOrderView> selectOrdersForManager(int mangerID) {
		ArrayList<ManagerOrderView> orders = new ArrayList<>();
		Connection conn = DataBaseController.getConn();
		//String query = "SELECT O.idOrder, O.price, UD.firstName,UD.lastName,O.date,O.status,O.idUser FROM zli.orders o, zli.branches b,zli.user_details UD,zli.users U WHERE o.idBranch=b.idBranch AND b.idManager =? AND (O.status = 'Waiting for Approval' OR O.status = 'Waiting for Cancellation' ) AND U.idUser = O.idUser AND UD.idAccount=U.idAccount;";
		String query = "SELECT distinct o.idOrder, o.price, UD.firstName,UD.lastName,o.status,o.idUser,o.date AS orderDate, d.deliveryDate,d.type,o.lastModified,TIMESTAMPDIFF(minute,o.lastModified,d.deliveryDate) AS timeTillDelivery FROM zli.orders o, zli.branches b,zli.user_details UD,zli.users U,zli.orders, zli.deliveries d WHERE o.idBranch=b.idBranch AND b.idManager =? AND (o.status = 'Waiting for Approval' OR o.status = 'Waiting for Cancellation' ) AND U.idUser = o.idUser AND UD.idAccount=U.idAccount AND d.idOrder=o.idOrder;";
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, mangerID);
			ResultSet rs = preparedStmt.executeQuery();
			while (rs.next()) {
				int idOrder = rs.getInt(1);
				Double price = rs.getDouble(2);
				String firstName = rs.getString(3);
				String lastName= rs.getString(4);
				String status= rs.getString(5);
				int idUser= rs.getInt(6);
				String orderDate= rs.getString(7);
				String deliveryTime= rs.getString(8);
				String deliveryType= rs.getString(9);
				String lastModified= rs.getString(10);
				int timeTillDelivery= rs.getInt(11);
				orders.add(new ManagerOrderView(idOrder, price, firstName, lastName, status, idUser,orderDate,deliveryTime,deliveryType,lastModified,timeTillDelivery));
			}
		} catch (SQLException e) {
			System.out.println("failed to fetch orders for manager");
			e.printStackTrace();
		}
		return orders;
	}

	public static boolean approveOrder(int idOrder) {
		Connection conn = DataBaseController.getConn();
		String query = "UPDATE orders SET status = 'Approved' WHERE idOrder = ? ;";
		// String query = "UPDATE FROM complaints WHERE idComplaint = ?;";
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, idOrder);
			int row = preparedStmt.executeUpdate();
			if (row == 0)
				return false;
			query = "UPDATE deliveries SET deliveries.status = 'Awaiting Delivery' WHERE idOrder = ?;";
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, idOrder);
			preparedStmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("failed to fetch order numbers");
			e.printStackTrace();
		}
		return true;
	}

	public static boolean cancelOrder(int idOrder, Double refund,int idUser) {
		Connection conn = DataBaseController.getConn();
		String query = "UPDATE orders SET status = 'Canceled', refund =? WHERE idOrder = ? ;";
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setDouble(1, refund);
			preparedStmt.setInt(2, idOrder);
			int row = preparedStmt.executeUpdate();
			if (row == 0)
				return false;
			query = "UPDATE deliveries SET deliveries.status = 'Canceled' WHERE idOrder = ?;";
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, idOrder);
			preparedStmt.executeUpdate();
			query = "UPDATE users SET storeCredit = storeCredit+? WHERE idUser = ?;";
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.setDouble(1, refund);
			preparedStmt.setInt(2, idUser);
			preparedStmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("failed to cancel order");
			e.printStackTrace();
		}
		return true;
	}

	public static ArrayList<Notification> getNotification(int idUser) {
		ArrayList<Notification> notification = new ArrayList<>();
		Connection conn = DataBaseController.getConn();
		String query = "SELECT * FROM notifications WHERE idUser = ?;";
		try {
			// Statement selectStmt = DataBaseController.getConn().createStatement(); why???
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, idUser);
			ResultSet rs = preparedStmt.executeQuery();
			while (rs.next()) {
				notification.add(new Notification(rs.getInt(1), rs.getInt(2), rs.getBoolean(3), rs.getString(4)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return notification;
	}

	public static ArrayList<CustomProduct> insertCustomProducts(ArrayList<CustomProduct> customProducts) {
		StringBuffer buff = new StringBuffer();
		buff.append("INSERT INTO custom_products (productName,price) VALUES ");
		for (CustomProduct currentInsert : customProducts) {
			buff.append("('" + currentInsert.getName() + "', " + currentInsert.getPrice() + "),");
		}
		buff.deleteCharAt(buff.length() - 1);
		buff.append(";");
		try {
			Connection conn;
			conn = DataBaseController.getConn();
			PreparedStatement preparedStmt = conn.prepareStatement(buff.toString(), Statement.RETURN_GENERATED_KEYS);
			preparedStmt.executeUpdate();
			ResultSet rs = preparedStmt.getGeneratedKeys();
			for (CustomProduct customProduct : customProducts) {
				rs.next();
				customProduct.setId(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customProducts;
	}

	public static boolean insertCustomProductProducts(ArrayList<CustomProduct> customProducts) {
		StringBuffer buff = new StringBuffer();
		buff.append("INSERT INTO custom_product_products (idCustomProduct, idProduct, quantity) VALUES ");
		for (CustomProduct customProduct : customProducts) {
			HashMap<Product, Integer> products = customProduct.getProducts();
			for (Product product : products.keySet())
				buff.append("(" + customProduct.getId() + ", " + product.getId() + ", " + products.get(product) + "),");
		}
		buff.deleteCharAt(buff.length() - 1);
		buff.append(";");
		try {
			Connection conn;
			conn = DataBaseController.getConn();
			PreparedStatement preparedStmt = conn.prepareStatement(buff.toString());
			preparedStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean insertCustomProductItems(ArrayList<CustomProduct> customProducts) {
		StringBuffer buff = new StringBuffer();
		buff.append("INSERT INTO custom_product_items (idCustomProduct, idItem, quantity) VALUES ");
		for (CustomProduct customProduct : customProducts) {
			HashMap<Item, Integer> items = customProduct.getItems();
			for (Item item : items.keySet())
				buff.append("(" + customProduct.getId() + ", " + item.getId() + ", " + items.get(item) + "),");
		}
		buff.deleteCharAt(buff.length() - 1);
		buff.append(";");
		try {
			Connection conn;
			conn = DataBaseController.getConn();
			PreparedStatement preparedStmt = conn.prepareStatement(buff.toString());
			preparedStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean insertOrderCustomProducts(ArrayList<CustomProduct> customProducts, Integer idOrder) {
		StringBuffer buff = new StringBuffer();
		buff.append("INSERT INTO order_custom_products (idOrder, idCustomProduct) VALUES ");
		for (CustomProduct customProduct : customProducts)
			buff.append("(" + idOrder + ", " + customProduct.getId() + "),");
		buff.deleteCharAt(buff.length() - 1);
		buff.append(";");
		try {
			Connection conn;
			conn = DataBaseController.getConn();
			PreparedStatement preparedStmt = conn.prepareStatement(buff.toString());
			preparedStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean sendNotification(Integer idUser, String title) {
		Connection conn = DataBaseController.getConn();
		String query = "INSERT INTO notifications (idUser, title) VALUES (?, ?)";
		PreparedStatement preparedStmt;
		try {
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, idUser);
			preparedStmt.setString(2, title);
			preparedStmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println("Failed to insert notification");
			e.printStackTrace();
			return false;
		}
	}

	public static boolean markReadNotification(int idNotification) {
		Connection conn = DataBaseController.getConn();
		String query = "UPDATE notifications SET isRead = 1 WHERE idNotification = ? ;";
		PreparedStatement preparedStmt;
		try {
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, idNotification);
			preparedStmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println("Failed to insert notification");
			e.printStackTrace();
			return false;
		}
	}

	public static boolean deleteNotification(int idNotification) {
		Connection conn = DataBaseController.getConn();
		String query = "DELETE FROM notifications WHERE idNotification = ?;";
		PreparedStatement preparedStmt;
		try {
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, idNotification);
			preparedStmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println("Failed to delete notification");
			e.printStackTrace();
			return false;
		}
	}

	public static ArrayList<QuestionAnswer> getSurveyAnswers(int idSurvey) {
		ArrayList<QuestionAnswer> questions = new ArrayList<>();
		Connection conn = DataBaseController.getConn();
		String query = "SELECT sq.idSurveyQuestion,sq.question,sa.answer,COUNT(*) FROM zli.survey_answers sa JOIN zli.survey_questions sq ON sq.idSurveyQuestion = sa.idQuestion WHERE sq.idSurvey=? GROUP BY sa.idQuestion, sa.answer ORDER BY sa.idQuestion;";
		PreparedStatement preparedStmt;
		try {
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, idSurvey);
			ResultSet rs = preparedStmt.executeQuery();
			int currentQuestion;
			while (rs.next()) {
				currentQuestion = rs.getInt(1);
				String question = rs.getString(2);
				int[] answers = new int[10];
				do {
					if (rs.getInt(1) == currentQuestion) {
						answers[rs.getInt(3) - 1] = rs.getInt(4);
					} else {
						break;
					}
				} while (rs.next());
				questions.add(new QuestionAnswer(currentQuestion, question, answers));
				rs.previous();
			}
		} catch (SQLException e) {
			System.out.println("Failed to delete notification");
			e.printStackTrace();
		}
		return questions;
	}

	public static ArrayList<Report> selectAllReports() {
		ArrayList<Report> reports = new ArrayList<>();
		try {
			Statement stmt = DataBaseController.getConn().createStatement();
			ResultSet rs = stmt.executeQuery("SELECT *, QUARTER(date) FROM reports;");
			while (rs.next()) {
				Report reportResult = new Report(rs.getInt(1), rs.getString(2), rs.getDate(3), rs.getInt(4),
						rs.getInt(5));
				reports.add(reportResult);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reports;
	}

	// might not work correctly - probably doesn't
	public static boolean uploadFileToDB(File file) {
		Connection conn = DataBaseController.getConn();
		byte[] fileBytes = new byte[(int) file.length()];
		PreparedStatement psmnt;
		try {
			FileInputStream io = new FileInputStream(file);
			Blob fileBlob = conn.createBlob();
			fileBlob.setBytes(1, fileBytes);
			psmnt = conn.prepareStatement("INSERT INTO blob_file_table (idblobFile, blobFile) VALUES  (?,?)");
			psmnt.setString(1, file.getName());
			// psmnt.setBlob(2, fileBlob);
			psmnt.setBinaryStream(2, (InputStream) io, (int) file.length());
			if (psmnt.executeUpdate() == 0)
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	// public static ArrayList<File> retriveFileFromDB() {
	// as of right now this is just a big mess, the file downloads but its only 1 kb
	// and it wont open because it was corrupted and im not sure if
	// it happened in the encoding or decoding process
	public static File retriveFileFromDB() {
//		ArrayList<File> files = new ArrayList<>();
//		try {
//			Statement stmt = DataBaseController.getConn().createStatement();
//			ResultSet rs = stmt.executeQuery("SELECT * FROM blob_file_table;");
//			while (rs.next()) {
//				Blob blobFile = rs.getBlob(2);
//				byte[] byteFile = blobFile.getBytes(blobFile.length(),1);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return files;

		Connection conn = null;
		PreparedStatement stmt = null;
		InputStream input = null;
		FileOutputStream output = null;
		ResultSet rs = null;
		String fileName = "";
		File ret = null;
		try {

			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connecting...");

			conn = DataBaseController.getConn();
			System.out.println("Connection successful..\nNow creating query...");

			stmt = conn.prepareStatement("SELECT * FROM blob_file_table;");
			rs = stmt.executeQuery();

			if (rs.next()) {
				fileName = rs.getString(1);
				ret = new File("D:\\" + fileName);
				output = new FileOutputStream(ret);
				System.out.println("Getting file please be patient..");

				input = rs.getBinaryStream("idblobFile"); // get it from col name
				int r = 0;

				/*
				 * there I've tried with array but nothing changed..Like this : byte[] buffer =
				 * new byte[2048]; int r = 0; while((r = input.read(buffer)) != -1){
				 * out.write(buffer,0,r);}
				 */

				while ((r = input.read()) != -1) {
					output.write(r);
				}
			}
			System.out.println("File writing complete !");

		} catch (ClassNotFoundException e) {
			System.err.println("Class not found!");
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("Connection failed!");
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.err.println("File not found!");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("File writing error..!");
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					input.close();
					output.flush();
					output.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
		// return new File("D:\\" + fileName);
		return ret;
	}

	public static Map<String, Integer> getItemsIncomeReport(Report report) {
		Map<String, Integer> incomeLabels = new HashMap<>();
		try {
			Statement selectStmt = DataBaseController.getConn().createStatement();
			ResultSet rs = selectStmt
					.executeQuery("SELECT itemType, SUM(quantity*itemPrice) as totalSum FROM items JOIN "
							+ " order_items ON items.itemId=order_items.idItem JOIN orders ON orders.idOrder"
							+ " = order_items.idOrder AND orders.idBranch = " + report.getIdBranch()
							+ " and orders.date between " + report.getDateRange() + " GROUP BY itemType;");
			while (rs.next()) {
				String itemType = rs.getString(1);
				int totalSum = rs.getInt(2);
				incomeLabels.put(itemType, totalSum);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return incomeLabels;
	}

	public static Map<String, Integer> getProductsIncomeReport(Report report) {
		Map<String, Integer> incomeLabels = new HashMap<>();
		try {
			Statement selectStmt = DataBaseController.getConn().createStatement();
			ResultSet rs = selectStmt
					.executeQuery("SELECT productType, SUM(quantity*productPrice) as totalSum FROM products JOIN"
							+ " order_products ON products.productId=order_products.idProduct JOIN orders ON"
							+ " orders.idOrder = order_products.idOrder AND orders.idBranch = " + report.getIdBranch()
							+ " and orders.date between " + report.getDateRange() + " GROUP BY " + " productType;");
			while (rs.next()) {
				String productType = rs.getString(1);
				int totalSum = rs.getInt(2);
				incomeLabels.put(productType, totalSum);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return incomeLabels;
	}

	public static Map<String, Integer> getItemsOrdersReport(Report report) {
		Map<String, Integer> incomeLabels = new HashMap<>();
		try {
			Statement selectStmt = DataBaseController.getConn().createStatement();
			ResultSet rs = selectStmt.executeQuery(
					"SELECT itemType, SUM(quantity) as totalQuantity FROM items JOIN order_items ON items.itemId=order_items.idItem JOIN orders ON orders.idOrder = order_items.idOrder AND orders.idBranch = "
							+ report.getIdBranch() + " and orders.date between " + report.getDateRange()
							+ " GROUP BY itemType;");
			while (rs.next()) {
				String productType = rs.getString(1);
				int totalSum = rs.getInt(2);
				incomeLabels.put(productType, totalSum);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return incomeLabels;
	}

	public static Map<String, Integer> getProductsOrdersReport(Report report) {
		Map<String, Integer> incomeLabels = new HashMap<>();
		try {
			Statement selectStmt = DataBaseController.getConn().createStatement();
			ResultSet rs = selectStmt.executeQuery(
					"SELECT productType, SUM(quantity) as totalQuantity FROM products JOIN order_products ON products.productId=order_products.idProduct JOIN orders ON orders.idOrder = order_products.idOrder AND orders.idBranch = "
							+ report.getIdBranch() + " and orders.date between " + report.getDateRange()
							+ " GROUP BY productType;");
			while (rs.next()) {
				String productType = rs.getString(1);
				int totalSum = rs.getInt(2);
				incomeLabels.put(productType, totalSum);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return incomeLabels;
	}

	public static Map<String, Integer> getComplaintsReport(Report report) {
		Map<String, Integer> complaintsData = new HashMap<>();
		try {
			Statement selectStmt = DataBaseController.getConn().createStatement();
			ResultSet rs = selectStmt.executeQuery(
					"SELECT MONTHNAME(complaints.date) as complaint_month, count(complaints.idComplaint) as number_of_complaints\r\n"
							+ "FROM complaints\r\n"
							+ "JOIN orders ON complaints.orderId = orders.idOrder and orders.idBranch = "
							+ report.getIdBranch() + "\r\n" + "WHERE complaints.date between " + report.getDateRange()
							+ "\r\n" + "GROUP BY complaint_month ORDER BY complaints.date;");
			while (rs.next()) {
				String month = rs.getString(1);
				int totalComplaintCountPerMonth = rs.getInt(2);
				complaintsData.put(month, totalComplaintCountPerMonth);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return complaintsData;
	}

	public static Map<String, Integer> getIncomeHistogramReport(Report report) {
		Map<String, Integer> incomeData = new HashMap<>();
		try {
			Statement selectStmt = DataBaseController.getConn().createStatement();

			ResultSet rs = selectStmt
					.executeQuery("SELECT MONTHNAME(orders.date) as orders_month, SUM(orders.price) as totalInMonth\r\n"
							+ "FROM orders\r\n" + "WHERE orders.date between " + report.getDateRange()
							+ " and orders.idBranch = " + report.getIdBranch() + "\r\n" + "GROUP BY orders_month\r\n"
							+ "ORDER BY orders.date;");
			while (rs.next()) {
				String month = rs.getString(1);
				int totalIncomePerMonth = rs.getInt(2);
				incomeData.put(month, totalIncomePerMonth);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return incomeData;
	}

	public static int getCustomIncomeReport(Report report) {
		int totalSum = 0;
		try {
			Statement selectStmt = DataBaseController.getConn().createStatement();
			ResultSet rs = selectStmt
					.executeQuery("SELECT SUM(order_custom_products.quantity*custom_products.price) as totalSum\r\n"
							+ "FROM custom_products\r\n"
							+ "JOIN order_custom_products ON custom_products.id=order_custom_products.idCustomProduct\r\n"
							+ "JOIN orders ON orders.idOrder = order_custom_products.idOrder and orders.idBranch = "
							+ report.getIdBranch() + " and orders.date between " + report.getDateRange()
							+ " GROUP BY orders.idBranch;");
			while (rs.next()) {
				totalSum = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return totalSum;
	}

	public static int getCustomOrdersReport(Report report) {
		int totalQuantity = 0;
		try {
			Statement selectStmt = DataBaseController.getConn().createStatement();
			ResultSet rs = selectStmt.executeQuery("SELECT SUM(order_custom_products.quantity) as totalQuantity\r\n"
					+ "FROM custom_products\r\n"
					+ "JOIN order_custom_products ON custom_products.id=order_custom_products.idCustomProduct\r\n"
					+ "JOIN orders ON orders.idOrder = order_custom_products.idOrder and orders.idBranch = "
					+ report.getIdBranch() + " and orders.date between " + report.getDateRange()
					+ " GROUP BY orders.idBranch;");
			while (rs.next()) {
				totalQuantity = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return totalQuantity;
	}

	public static ArrayList<CustomerOrderView> getCustomerOrders(int idUser) {
		ArrayList<CustomerOrderView> cov = new ArrayList<>();
		try {
			Statement selectStmt = DataBaseController.getConn().createStatement();
			ResultSet rs = selectStmt.executeQuery(
					"SELECT o.idOrder,o.status,d.status,o.date,d.deliveryDate,d.type,o.price FROM zli.orders o INNER JOIN deliveries d ON o.idOrder = d.idOrder WHERE o.idUser ="
							+ idUser + " ORDER BY o.date DESC;");
			while (rs.next()) {
				cov.add(new CustomerOrderView(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getDouble(7)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cov;
	}

	/**
	 * returns the products in a given order
	 * 
	 * @param idOrder
	 * @return
	 */
	public static HashMap<ProductsBase, Integer> getOrderProducts(int idOrder) {
		HashMap<ProductsBase, Integer> products = new HashMap<>();
		Connection conn = DataBaseController.getConn();
		PreparedStatement preparedStmt;
		String query = "SELECT * FROM zli.order_products op INNER JOIN zli.products p ON op.idProduct=p.productID WHERE op.idOrder=?;";
		try {
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, idOrder);
			ResultSet rs = preparedStmt.executeQuery();
			while (rs.next()) {
				products.put(new ProductsBase(rs.getInt(4), rs.getString(5), rs.getString(7), rs.getDouble(8),
						rs.getString(9), rs.getString(11)), rs.getInt(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return products;
	}

	/**
	 * returns the items in a given order
	 * 
	 * @param idOrder
	 * @return
	 */
	public static HashMap<ProductsBase, Integer> getOrderItems(int idOrder) {
		HashMap<ProductsBase, Integer> items = new HashMap<>();
		Connection conn = DataBaseController.getConn();
		PreparedStatement preparedStmt;
		String query = "SELECT * FROM zli.order_items oi INNER JOIN zli.items i ON oi.idItem =i.itemID WHERE oi.idOrder=?;";
		try {
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, idOrder);
			ResultSet rs = preparedStmt.executeQuery();
			while (rs.next()) {
				items.put(new ProductsBase(rs.getInt(4), rs.getString(5), rs.getString(6), rs.getDouble(7),
						rs.getString(8), rs.getString(9)), rs.getInt(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return items;
	}

	/**
	 * returns the Custom Products in a given order
	 * 
	 * @param idOrder
	 * @return
	 */
	public static HashMap<ProductsBase, Integer> getOrderCustomProducts(int idOrder) {
		HashMap<ProductsBase, Integer> customProducts = new HashMap<>();
		Connection conn = DataBaseController.getConn();
		PreparedStatement preparedStmt;
		String query = "SELECT * FROM zli.order_custom_products ocp INNER JOIN zli.custom_products cp ON ocp.idCustomProduct=cp.id WHERE ocp.idOrder=?;";
		try {
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, idOrder);
			ResultSet rs = preparedStmt.executeQuery();
			while (rs.next()) {
				customProducts.put(new ProductsBase(rs.getInt(4), rs.getString(5), "not specified", rs.getDouble(6),
						"not specified", "/resources/catalog/customProductImage.png"), rs.getInt(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customProducts;
	}

	public static boolean insertScreens(Integer id, ArrayList<Screens> userScreen) {
		Connection conn = DataBaseController.getConn();
		String query = "DELETE FROM user_screen WHERE idUser = ?;";
		PreparedStatement preparedStmt;
		try {
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, id);
			preparedStmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Failed to delete user_details");
			e.printStackTrace();
		}
		for (Screens screen : userScreen) {
			 query = "INSERT INTO user_screen (idUser, screen) VALUES (?, ?);";
			try {
				preparedStmt = conn.prepareStatement(query);
				preparedStmt.setInt(1, id);
				preparedStmt.setString(2, screen.toString());
				preparedStmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	public static boolean cancelOrderRequest(int idOrder) {
		Connection conn = DataBaseController.getConn();
		String query = "UPDATE orders SET status = 'Waiting for Cancellation',lastModified = CURRENT_TIMESTAMP WHERE idOrder = ? ;";
		PreparedStatement preparedStmt;
		try {
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, idOrder);
			preparedStmt.executeUpdate();
			query = "UPDATE deliveries SET status = 'Waiting for Cancellation' WHERE idOrder = ? ;";
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, idOrder);
			preparedStmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println("Failed to update status");
			e.printStackTrace();
			return false;
		}
	}

	public static boolean removeProductFromDB(int productId) {
		Connection conn = DataBaseController.getConn();
		String query = "DELETE FROM products WHERE productID = ?;";
		PreparedStatement preparedStmt;
		try {
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, productId);
			preparedStmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println("Failed to delete notification");
			e.printStackTrace();
			return false;
		}
	}

	public static boolean removeItemFromDB(int itemId) {
		Connection conn = DataBaseController.getConn();
		String query = "DELETE FROM items WHERE itemID = ?;";
		PreparedStatement preparedStmt;
		try {
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, itemId);
			preparedStmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println("Failed to delete notification");
			e.printStackTrace();
			return false;
		}
	}

	public static Product getSelectedProduct(Integer id) {
		Product productResult = null;
		Connection conn = DataBaseController.getConn();
		String query = "SELECT * FROM PRODUCTS WHERE ProductID = ?;";
		PreparedStatement preparedStmt;
		try {
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, id);
			ResultSet rs = preparedStmt.executeQuery();
			while (rs.next()) {
				int productId = rs.getInt(1);
				String productName = rs.getString(2);
				String flowerType = rs.getString(3);
				String productColor = rs.getString(4);
				double productPrice = rs.getDouble(5);// double
				String productType = rs.getString(6);
				String productDesc = rs.getString(7);
				String imagePath = rs.getString(8);
				int discount = rs.getInt(9);
				productResult = new Product(productId, productName, productColor, productPrice, productType, imagePath,
						discount, flowerType, productDesc);
			}
			return productResult;
		} catch (SQLException e) {
			System.out.println("Failed to delete notification");
			e.printStackTrace();
			return null;
		}
	}

	public static Item getSelectedItem(Integer id) {
		Item itemResult = null;
		Connection conn = DataBaseController.getConn();
		String query = "SELECT * FROM Items WHERE itemID = ?;";
		PreparedStatement preparedStmt;
		try {
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, id);
			ResultSet rs = preparedStmt.executeQuery();
			while (rs.next()) {
				int itemID = rs.getInt(1);
				String itemName = rs.getString(2);
				String itemColor = rs.getString(3);
				double itemPrice = rs.getDouble(4);
				String itemType = rs.getString(5);
				String imagePath = rs.getString(6);
				int discount = rs.getInt(7);
				itemResult = new Item(itemID, itemName, itemColor, itemPrice, itemType, imagePath, discount);
			}
			return itemResult;
		} catch (SQLException e) {
			System.out.println("Failed to delete notification");
			e.printStackTrace();
			return null;
		}
	}

	public static int getTotalRefunds(Report report) {
		int totalRefund = 0;
		try {
			Statement selectStmt = DataBaseController.getConn().createStatement();
			ResultSet rs = selectStmt
					.executeQuery("SELECT SUM(refund) as totalRefund\r\n"
							+ "FROM zli.orders\r\n"
							+ "WHERE idBranch = "+report.getIdBranch()+" and orders.date between "+report.getDateRange()+" \r\n"
							+ "GROUP BY idBRanch;");
			while (rs.next()) {
				totalRefund = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return totalRefund;
	}
}
