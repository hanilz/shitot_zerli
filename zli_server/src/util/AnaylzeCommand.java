package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import entities.Branch;
import entities.Order;
import entities.Product;
import entities.User;
import entities.UserDetails;

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
	public static ArrayList<Order> selectAllOrders() {
		ArrayList<Order> orders = new ArrayList<>();
		orders.add(new Order(-1, 0.0, "", "", "", "", "", ""));
		try {
			Statement selectStmt = DataBaseController.getConn().createStatement();
			ResultSet rs = selectStmt.executeQuery("SELECT * FROM orders;");
			while (rs.next()) {
				int orderNumber = rs.getInt(1);
				double price = rs.getDouble(2);
				String greetingCard = rs.getString(3);
				String color = rs.getString(4);
				String dOrder = rs.getString(5);
				String shop = rs.getString(6);
				String date = rs.getString(7);
				String orderDate = rs.getString(8);
				Order resultOrder = new Order(orderNumber, price, greetingCard, color, dOrder, shop, date, orderDate);
				orders.add(resultOrder);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orders;
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
			else if (rs.getBoolean(6))// if user already logged in
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
		} catch (SQLException e) {
			System.out.println("failed to fetch user");
			e.printStackTrace();
		}
		login.put("response", status);// status of login
		return login;// default for any throw would be unregistered
	}

	public static void logoutUser(int idUser) {
		Connection conn;
		conn = DataBaseController.getConn();
		ResultSet rs;
		String query = "SELECT * FROM users WHERE idUser";
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			query = "UPDATE users SET isLogin = ? WHERE idUser = ?";
			preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, 0);
			preparedStmt.setInt(2, idUser);
			preparedStmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("failed to fetch user");
			e.printStackTrace();
		}
	}

	//this method is used to get all the users from user_details table
	public static ArrayList<UserDetails> selectAllUsers() {
		ArrayList<UserDetails> users = new ArrayList<>();
		try {
			Statement selectStmt = DataBaseController.getConn().createStatement();
			ResultSet rs = selectStmt.executeQuery("SELECT * FROM user_details;");
			while (rs.next()) {
				int idAccount = rs.getInt(1);
				String firstName =  rs.getString(2);
				String lastName =  rs.getString(3);
				String id =  rs.getString(4);
				String email =  rs.getString(5);
				String phoneNumber = rs.getString(6);
				String status = rs.getString(7);
				UserDetails resultOrder = new UserDetails(idAccount, firstName, lastName, id, email, phoneNumber,status);
				users.add(resultOrder);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	
	//this methods gets an Account id and changes the status of the user
	public static String changeUserStatus(Object object) {
		Connection conn = DataBaseController.getConn();
		ResultSet rs;
		int id = (Integer)object;
		String response = "failed";
		try {
			//first we get the current user status
			String query = "SELECT * FROM user_details WHERE idAccount = ?";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, id);
			rs = preparedStmt.executeQuery();
			if(!rs.next())
				return "failed";
			String userStatus = rs.getString(7);	
			
			//preparedStmt = conn.prepareStatement(query);
			//second we find the user again and change its status to be the inverse of the current status
			query = "UPDATE user_details SET status = ? WHERE idAccount = ?";
			preparedStmt = conn.prepareStatement(query);
			if(userStatus.equals("Active")) {
				preparedStmt.setString(1, "Suspended");
				response = "Suspended";
			}
			else {
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

}
