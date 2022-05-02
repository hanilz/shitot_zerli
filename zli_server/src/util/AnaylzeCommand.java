package util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import entities.Order;
import entities.Product;

/**AnaylzeCommand - will anaylze the command that given from the server controller and will execute the sql query.
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
				double productPrice = rs.getDouble(5);
				String productType = rs.getString(6);
				String productDesc = rs.getString(7);
				String imagePath = rs.getString(8);
				Product productResult = new Product(productId, productName, flowerType, productColor, productPrice, productType, productDesc, imagePath);
				products.add(productResult);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return products;
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
}
