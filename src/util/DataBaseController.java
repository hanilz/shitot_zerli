package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import gui.ServerFormController;
import javafx.scene.control.TextArea;

public class DataBaseController {
	// make singleton
	private static Connection conn = null;
	private static List<String> args = new ArrayList<>();
	private TextArea consoleField;
	
	public DataBaseController(TextArea consoleField) {
		this.consoleField = consoleField;
	}

	public static void setConnection(List<String> args) {
		DataBaseController.args.clear();
		for (int i = 0; i < args.size(); i++)
			DataBaseController.args.add(args.get(i));
	}

	public boolean connect() {
		StringBuffer buff = new StringBuffer();
		if(!configDriver(buff))
			return false;
		String ip = args.get(0);
		String dbName = args.get(1);
		String dbUsername = args.get(2);
		String dbPassword = args.get(3);

		try {
			conn = DriverManager.getConnection("jdbc:mysql://" + ip + "/" + dbName + "?serverTimezone=IST",
					dbUsername, dbPassword);
			buff.append("\nDatabase connection succeeded!\n");
			consoleField.setText(buff.toString());
			return true;
		} catch(SQLException e) {
			buff.append("\nDatabase connection failed!\n");
			consoleField.setText(buff.toString());
			return false;
		}
	}

	private boolean configDriver(StringBuffer buff) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			buff.append("\nDriver definition succeed\n");
			return true;
		} catch (Exception ex) {
			/* handle the error */
			buff.append("\nDriver definition failed\n");
			return false;
		}
	}

}
