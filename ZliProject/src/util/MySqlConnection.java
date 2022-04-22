package src.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class MySqlConnection {
	// make singleton
	private static Connection conn = null;
	
	public static boolean connect(List<String> args) throws SQLException {
		
		configDriver();

		try {
			String port = args.get(0);
			String ip = args.get(1);
			String dbName = args.get(2);
			String dbUsername = args.get(3);
			String dbPassword = args.get(4);
			
			conn = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + dbName + "?serverTimezone=IST", dbUsername,
					dbPassword);
			System.out.println("Database connection succeeded!");
			
			return true;
		} catch (SQLException ex) { /* handle any errors */
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return false;
		}
	}

	private static void configDriver() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			System.out.println("Driver definition succeed");
		} catch (Exception ex) {
			/* handle the error */
			System.out.println("Driver definition failed");
		}
	}

}
