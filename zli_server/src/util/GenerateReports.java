package util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entities.Branch;
import entities.Report;

public class GenerateReports {

	private static List<String> connectionArray = new ArrayList<>();

	public static void setConnectionToDB() {
		if (connectionArray.isEmpty()) {
			String ip = "localhost";
			String dbName = "zli";
			String dbUsername = "root";
			String dbPassword = "braude123";
			String[] stringArray = new String[] { ip, dbName, dbUsername, dbPassword };
			connectionArray = Arrays.asList(stringArray);
			DataBaseController.setConnection(connectionArray);
		}
	}

	public static boolean generateNewReports(ArrayList<String> reportsTypes, ArrayList<Branch> branches, Date date) {
		String query = "insert into reports (type, date, idBranch) values ";
		Statement stmt;
		StringBuffer buffer = new StringBuffer(query);
		java.sql.Timestamp currentDateTime = new java.sql.Timestamp(date.getTime());
		String currentDate = currentDateTime.toString().substring(0, currentDateTime.toString().length() - 4);
		for (Branch currentBranch : branches) {
			for (String currentType : reportsTypes) {
				buffer.append("('" + currentType + "','" + currentDate + "'," + currentBranch.getIdBranch() + "), ");
			}
		}
		buffer.delete(buffer.toString().length() - 2, buffer.toString().length());
		buffer.append(";");
		try {
			stmt = DataBaseController.conn.createStatement();
			stmt.executeUpdate(buffer.toString());
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean isGeneratedReports(Date date) {
		Statement stmt;
		java.sql.Timestamp currentDateTime = new java.sql.Timestamp(date.getTime());
		String currentDate = currentDateTime.toString().substring(0, currentDateTime.toString().length() - 13);
		String query = "SELECT date FROM zli.reports WHERE date BETWEEN '" + currentDate + "' AND '" + currentDate
				+ " 23:59:59';";
		try {
			stmt = DataBaseController.conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				if (rs.getString(1).contains(currentDate))
					return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static Map<String, Integer> getItemsIncomeReport(Report report) {
		Map<String, Integer> incomeLabels = new HashMap<>();
		try {
			Statement selectStmt = DataBaseController.conn.createStatement();
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
			Statement selectStmt = DataBaseController.conn.createStatement();
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
			Statement selectStmt = DataBaseController.conn.createStatement();
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
			Statement selectStmt = DataBaseController.conn.createStatement();
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
			Statement selectStmt = DataBaseController.conn.createStatement();
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

	/* The method that we want to check! */
	public static Map<String, Integer> getIncomeHistogramReport(Report report) {
		Map<String, Integer> incomeData = new HashMap<>();
		try {
			//Statement selectStmt = DataBaseController.getConn().createStatement();
			Statement stmt = DataBaseController.conn.createStatement(); //for testing ONLY

			ResultSet rs = stmt
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
			Statement selectStmt = DataBaseController.conn.createStatement();
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
	
	public static ArrayList<Report> selectAllReports() {
		ArrayList<Report> reports = new ArrayList<>();
		try {
			Statement stmt = DataBaseController.conn.createStatement();
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

	public static int getCustomOrdersReport(Report report) {
		int totalQuantity = 0;
		try {
			Statement selectStmt = DataBaseController.conn.createStatement();
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
}
