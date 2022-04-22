package src.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataBaseController {
	// make singleton
	private static Connection conn = null;

	private static List<String> args = new ArrayList<>();

	public static boolean isConnected = false;

	//private static PreparedStatement ps = null;
	
	//private static ResultSet rs = null;

	public static void setConnection(List<String> args) {
		DataBaseController.args.clear();
		for (int i = 0; i < args.size(); i++)
			DataBaseController.args.add(args.get(i));
	}

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
			conn = DriverManager.getConnection("jdbc:mysql://" + ip + "/" + dbName + "?serverTimezone=IST&useSSL=false", dbUsername,
					dbPassword);//URL, Username, Password+changed url with message "&useSSL=false"
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

	public static boolean Disconnect() {//not looking only for SQLException 
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
				conn.close();//closing database connection
			} catch (Exception e) {
				}
		}
		isConnected = false;
		return true;

	}

}
