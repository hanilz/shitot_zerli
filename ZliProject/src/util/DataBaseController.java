package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entities.Order;

/**
 * DataBaseController will configure the connection and will handle all the queries that the server requests.
 *
 */
public class DataBaseController {
	// TODO: make singleton
	private static Connection conn = null;

	/**
	 * For saving the information that been given from the server screen
	 */
	private static List<String> args = new ArrayList<>();

	/**
	 * status of the connection of the database
	 */
	public static boolean isConnected = false;

	// private static PreparedStatement ps = null;

	// private static ResultSet rs = null;

	/**Clearing the current args from the list and adding the new information
	 * @param args
	 */
	public static void setConnection(List<String> args) {
		DataBaseController.args.clear();
		for (int i = 0; i < args.size(); i++)
			DataBaseController.args.add(args.get(i));
	}

	/**connecting to the database with the correct information that given from the server screen.
	 * if the information is incorrect, it will throw SQLException.
	 * @return string depending on the information
	 */
	public static String connect() {
		StringBuffer buff = new StringBuffer();
		buff.append(configDriver());
		if (buff.toString().equals("\nDriver definition failed\n"))
			return buff.toString();
		String ip = args.get(0);
		String dbName = args.get(1);
		String dbUsername = args.get(2);
		String dbPassword = args.get(3);
		try {
			conn = DriverManager.getConnection("jdbc:mysql://" + ip + "/" + dbName + "?serverTimezone=IST&useSSL=false",
					dbUsername, dbPassword); // URL, Username, Password+changed url with message "&useSSL=false"
			buff.append("\nDatabase connection succeeded!\n");
			isConnected = true;
		} catch (SQLException e) {
			buff.append("\nDatabase connection failed!\n");
			isConnected = false;
		}
		return buff.toString();
	}

	private static String configDriver() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			return "\nDriver definition succeed\n";
		} catch (Exception ex) {
			/* handle the error */
			return "\nDriver definition failed\n";
		}
	}

	/**This function will disconnect from the database
	 * @return true or false
	 */
	public static boolean Disconnect() {// not looking only for SQLException
		if (!isConnected)
			return true;// server was not connected to begin with
//		if (rs != null) {
//			try {
//				rs.close();//closing ResultSet
//			} catch (Exception e) {
//				}
//		}
//		if (ps != null) {
//			try {
//				ps.close();//closing PreparedStatement
//			} catch (Exception e) {
//				}
//		}
		if (conn != null) {
			try {
				conn.close();// closing database connection
			} catch (Exception e) {
			}
		}
		isConnected = false;
		return true;
	}

	/**selectAllOrder will execute select * from the order table for presenting the data to the client screen
	 * @return
	 */
	public static ArrayList<Order> selectAllOrders() {
		ArrayList<Order> orders  = new ArrayList<>();
		orders.add(new Order(-1, 0.0, "", "", "", "", "", ""));
		try {
			Statement selectStmt = conn.createStatement();
			ResultSet rs = selectStmt.executeQuery("SELECT * FROM orders;");
			while(rs.next()) {
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

}
