package gui;

import java.io.IOException;
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
		sv = new ServerController(Integer.parseInt(port), ip);
		StringBuffer buff = new StringBuffer();
		buff.append(sv.connectToDB());
		try {
			String result = sv.runServer();
			buff.append(result);
			if(result.contains("Server listening for connections on port")) {
				connectButton.setDisable(true);
				disconnectButton.setDisable(false);
			}
			else {
				sv.close();
				System.out.println("Closed Shita Bepita");
			}
		} catch (Exception e) {
			buff.append("ERROR - Could not listen for clients!\n");
		}
		consoleField.setText(buff.toString());

	}

	@FXML
    void clickOnDisconnect(MouseEvent event) {
		try {
			sv.close();
			System.out.println("Closed WOOHOO");
		} catch (IOException e) {
			System.out.println("Can't close");
			e.printStackTrace();
		}
		
		connectButton.setDisable(false);
		disconnectButton.setDisable(true);
		consoleField.setText("Server has disconnected.");
    }

	@FXML
	void closeWindow(MouseEvent event) {
		System.out.println("Dasvidanya");
		System.exit(0);
	}
	
	private boolean checkParameters() {
		if(!portTextField.getText().matches("-?\\d+")) { //if the string is an integer, throw Exception
			consoleField.setText("ERROR - Could not connect!");
			return false;
		}
		return true;
	}

}
