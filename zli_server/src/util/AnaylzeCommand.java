package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import entities.Branch;
import entities.Item;
import entities.ManageUsers;
import entities.OrderProduct;
import entities.Product;
import survey.Survey;

/**
 * AnaylzeCommand - will anaylze the command that given from the server
 * controller and will execute the sql query.
 */
public class AnaylzeCommand {

	public static boolean isUpdated = false;

	public static ArrayList<Product> selectAllProducts() {
		ArrayList<Product> products = new ArrayList<>();
		try {
			Statement selectStmt = DataBaseController.getConn().createStatement();
			ResultSet rs = selectStmt.executeQuery("SELECT * FROM products;");
			while (rs.next()) {
				int productId = rs.getInt(1);
				String productName = rs.getString(2);
				String flowerType = rs.getString(3);
				String productColor = rs.getString(4);
				double productPrice = rs.getInt(5);
				String productType = rs.getString(6);
				String productDesc = rs.getString(7);
				String imagePath = rs.getString(8);
				Product productResult = new Product(productId, productName, flowerType, productColor, productPrice,
						productType, productDesc, imagePath);
				products.add(productResult);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return products;
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
				Item itemResult = new Item(itemID, itemName, itemColor, itemPrice, itemType, imagePath);
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

	/**
	 * selectAllOrder will execute select * from the order table for presenting the
	 * data to the client screen
	 * 
	 * @return
	 */
	/*
	 * public static ArrayList<Order> selectAllOrders() { ArrayList<Order> orders =
	 * new ArrayList<>(); orders.add(new Order(-1, 0.0, "", "", "", null, "", null,
	 * "")); try { Statement selectStmt =
	 * DataBaseController.getConn().createStatement(); ResultSet rs =
	 * selectStmt.executeQuery("SELECT * FROM orders;"); while (rs.next()) { int
	 * orderNumber = rs.getInt(1); double price = rs.getDouble(2); String
	 * greetingCard = rs.getString(3); String color = rs.getString(4); String dOrder
	 * = rs.getString(5); String shop = rs.getString(6); String date =
	 * rs.getString(7); String orderDate = rs.getString(8); Order resultOrder = new
	 * Order(orderNumber, price, greetingCard, color, dOrder, shop, date,
	 * orderDate); orders.add(resultOrder); } } catch (SQLException e) {
	 * e.printStackTrace(); } return orders; }
	 */

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

	public static boolean insertAccountPayment(String fullName, String cardNumber, String cardDate, String cardVCC,
			int idUser) {
		Connection conn;
		conn = DataBaseController.getConn();
		String query = "INSERT INTO account_payment (fullName, cardNumber, cardDate, cardVCC, idUser) VALUES (?, ?, ?, ?, ?);";
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, fullName);
			preparedStmt.setString(2, cardNumber);
			preparedStmt.setString(3, cardDate);
			preparedStmt.setString(4, cardVCC);
			preparedStmt.setInt(5, idUser);
			preparedStmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static int insertNewOrder(double totalPrice, String greetingCard, String dOrder, int idBranch, String status,
			String paymentMethod, int idUser) {
		Connection conn;
		int idOrder = -1;
		conn = DataBaseController.getConn();
		ResultSet rs = null;
		String query = "INSERT INTO orders (price, greetingCard, dOrder, idBranch, status, paymentMethod, idUser) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStmt.setDouble(1, totalPrice);
			preparedStmt.setString(2, greetingCard);
			preparedStmt.setString(3, dOrder);
			preparedStmt.setInt(4, idBranch);
			preparedStmt.setString(5, status);
			preparedStmt.setString(6, paymentMethod);
			preparedStmt.setInt(7, idUser);
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
			buff.append("(" + currentInsert.getIdOrder() + "," + currentInsert.getProduct().getProductID() + ","
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

	public static int insertNewDelivery(String address, String recieverName, String phoneNumber, String deliveryDate,
			String status) {
		Connection conn;
		ResultSet rs = null;
		int idOrder = -1;
		conn = DataBaseController.getConn();
		String query = "INSERT INTO deliveries (address, receiverName, phoneNumber, deliveryDate, status) VALUES (?, ?, ?, ?, ?);";
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStmt.setString(1, address);
			preparedStmt.setString(2, recieverName);
			preparedStmt.setString(3, phoneNumber);
			preparedStmt.setString(4, deliveryDate);
			preparedStmt.setString(5, status);
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

	public static ArrayList<Survey> selectSurveys() {
		ArrayList<Survey> surveys = new ArrayList<>();
		try {
			Statement selectStmt = DataBaseController.getConn().createStatement();
			ResultSet rs = selectStmt.executeQuery("SELECT * FROM surveys;");
			while (rs.next()) {
				int idSurvey = rs.getInt(1);
				String surveyName = rs.getString(2);
				String[] questions = new String[6];

				for (int i = 0; i < 6; i++) {
					questions[i] = rs.getString(i + 3);
				}
				Survey survey = new Survey(idSurvey, surveyName, questions);
				surveys.add(survey);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("returned surveys");
		return surveys;
	}

}
