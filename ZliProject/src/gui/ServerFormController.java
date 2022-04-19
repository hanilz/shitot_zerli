package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import server.ServerController;
import util.ClientDetails;
import util.DataBaseController;

public class ServerFormController implements Initializable {

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
	private TableView<ClientDetails> connectionTable;

	@FXML
	private TableColumn<ClientDetails, String> ipCol;

	@FXML
	private TableColumn<ClientDetails, String> hostCol;
	@FXML
	private TableColumn<ClientDetails, String> statusCol;

	private ServerController sv;

	@FXML
	void clickOnConnect(MouseEvent event) {
		String port = portTextField.getText();
		String ip = IPTextField.getText();
		String dbName = DBNameField.getText();
		String dbUsername = DBUserTextField.getText();
		String dbPassword = DBPasswordTextField.getText();
		String[] stringArray = new String[] { ip, dbName, dbUsername, dbPassword };

		if (!checkParameters())
			return;
		List<String> connectionArray = Arrays.asList(stringArray);

		DataBaseController.setConnection(connectionArray);
		sv = new ServerController(Integer.parseInt(port), ip);
		StringBuffer buff = new StringBuffer();
		buff.append(sv.connectToDB());
		try {
			String result = sv.runServer();
			buff.append(result);
			if (result.contains("Server listening for connections on port")) {
				connectButton.setDisable(true);
				disconnectButton.setDisable(false);
				connectionTable.getItems().addAll(ServerController.clients);

			} else {
				sv.close();
			}
		} catch (Exception e) {
			buff.append("ERROR - Could not listen for clients!\n");
		}
		consoleField.setText(buff.toString());

		// connectionTable.getItems().addAll(ServerController.clients);
	}

	@FXML
	void clickOnDisconnect(MouseEvent event) {
		try {
			sv.close();
		} catch (IOException e) {
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
		if (!portTextField.getText().matches("-?\\d+")) { // if the string is an integer, throw Exception
			consoleField.setText("ERROR - Could not connect!");
			return false;
		}
		return true;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//setCellValueFactory: set for each attribute the cell in the table! so we can present the data in the table.
		ipCol.setCellValueFactory(new PropertyValueFactory<>("ip"));
		hostCol.setCellValueFactory(new PropertyValueFactory<>("client"));
		statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

		connectionTable.setItems(ServerController.clients);

	}

}
