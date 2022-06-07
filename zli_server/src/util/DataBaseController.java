package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DataBaseController will configure the connection and will handle all the
 * queries that the server requests.
 *
 */
public class DataBaseController {
	/**
	 * DatabaseController is singleton, there is only one conn to connect the database.
	 */
	private static Connection conn = null;

	/**
	 * For saving the information that been given from the server screen
	 */
	private static List<String> args = new ArrayList<>();

	/**
	 * status of the connection of the database
	 */
	public static boolean isConnected = false;

	private DataBaseController() {
	}

	public static Connection getConn() {
		if (conn == null) {
			configDriver();
			connect();
		}
		return conn;
	}

	/**
	 * Clearing the current args from the list and adding the new information
	 * 
	 * @param args
	 */
	public static void setConnection(List<String> args) {
		DataBaseController.args.clear();
		for (int i = 0; i < args.size(); i++)
			DataBaseController.args.add(args.get(i));
	}

	/**
	 * connecting to the database with the correct information that given from the
	 * server screen. if the information is incorrect, it will throw SQLException.
	 * 
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
			conn = DriverManager.getConnection("jdbc:mysql://" + ip + "/" + dbName + "?sessionVariables=sql_mode='NO_ENGINE_SUBSTITUTION'&jdbcCompliantTruncation=false&serverTimezone=IST&useSSL=false",
					dbUsername, dbPassword); // URL, Username, Password+changed url with message "&useSSL=false"
			buff.append("\nDatabase connection succeeded!\n");
			isConnected = true;
		} catch (SQLException e) {
			buff.append("\nDatabase connection failed!\n");
			isConnected = false;
		}
		return buff.toString();
	}

	/**
	 * configures the driver for the JDBC API
	 * @return String
	 */
	@SuppressWarnings("deprecation")
	private static String configDriver() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			return "\nDriver definition succeed\n";
		} catch (Exception ex) {
			/* handle the error */
			return "\nDriver definition failed\n";
		}
	}

	/**
	 * This function will disconnect from the database
	 * 
	 * @return true or false
	 */
	public static boolean Disconnect() {// not looking only for SQLException
		if (!isConnected)
			return true;// server was not connected to begin with
		if (conn != null) {
			try {
				conn.close();// closing database connection
				conn = null;
			} catch (Exception e) {
			}
		}
		isConnected = false;
		return true;
	}

}
