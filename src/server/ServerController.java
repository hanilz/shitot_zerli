package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import util.mysqlConnection;

public class ServerController {

    @FXML
    private TextField DBNameField;

    @FXML
    private TextField DBPasswordTextField;

    @FXML
    private TextField DBUserTextField;

    @FXML
    private TextField IPTextField;

    @FXML
    private Button closeButton;

    @FXML
    private Button connectButton;

    @FXML
    private Button disconnectButton;

    @FXML
    private TextField portTextField;

    @FXML
    void clickOnConnect(MouseEvent event) {
    	String port = portTextField.getText();
    	String ip = IPTextField.getText();
    	String dbName = DBNameField.getText();
    	String dbUsername = DBUserTextField.getText();
    	String dbPassword = DBPasswordTextField.getText();
    	String[] stringArray = new String[] {port, ip, dbName, dbUsername, dbPassword};
    	
    	List<String> connectionArray = Arrays.asList(stringArray);
    	
    	try {mysqlConnection.connect(connectionArray);
    	} catch (SQLException ex) { /* handle any errors */
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
    }

    @FXML
    void clickOnDisconnect(MouseEvent event) {

    }

    @FXML
    void closeWindow(MouseEvent event) {

    }

}
