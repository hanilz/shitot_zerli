package gui;

import java.util.Arrays;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import server.ServerController;
import util.DataBaseController;

public class ServerFormController {

	@FXML
	private TextField DBNameField;

    @FXML
    private PasswordField DBPasswordTextField;

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
	private TextArea consoleField;

	@FXML
	private TableView<?> connectionTable;
	
	private ServerController sv;
	
	@FXML
	void clickOnConnect(MouseEvent event) {
		String port = portTextField.getText();
		String ip = IPTextField.getText();
		String dbName = DBNameField.getText();
		String dbUsername = DBUserTextField.getText();
		String dbPassword = DBPasswordTextField.getText();
		String[] stringArray = new String[] { ip, dbName, dbUsername, dbPassword };
		
		if(!checkParameters()) return;
		List<String> connectionArray = Arrays.asList(stringArray);
		
		DataBaseController.setConnection(connectionArray);
		sv = new ServerController(Integer.parseInt(port),ip, consoleField, connectionTable);
		
		clearFields();
	}

	@FXML
	void clickOnDisconnect(MouseEvent event) {

	}

	@FXML
	void closeWindow(MouseEvent event) {

	}
	
	private boolean checkParameters() {
		if(!portTextField.getText().matches("-?\\d+")) { //if the string is an integer, throw Exception
			consoleField.setText("ERROR - Could not connect!");
			return false;
		}
		return true;
	}
	
	private void clearFields() {
		portTextField.setText("");
		IPTextField.setText("");
		DBNameField.setText("");
		DBUserTextField.setText("");
		DBPasswordTextField.setText("");
		//consoleField.setText("");
	}
	


}
