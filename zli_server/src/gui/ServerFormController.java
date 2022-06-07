package gui;

import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import server.ServerController;
import util.ClientDetails;
import util.DataBaseController;
import util.ImportUsers;


/**
 * ServerFormController will handle all the events from the gui
 *
 */
/**
 * @author Dolev
 *
 */
public class ServerFormController implements Initializable {

	/**
	 * contains database name in gui
	 */
	@FXML
	private TextField DBNameField;
	
    /**
     * import button for the external database to import users into zli server
     */
    @FXML
    private Button importButton;

	/**
	 * contains database password name in gui
	 */
	@FXML
	private PasswordField DBPasswordTextField;

	/**
	 * contains database user in gui
	 */
	@FXML
	private TextField DBUserTextField;

	/**
	 * contains database IP in gui
	 */
	@FXML
	private TextField IPTextField;

	/**
	 * connecting to database and server button
	 */
	@FXML
	private Button connectButton;

	/**
	 * disconnecting from server
	 */
	@FXML
	private Button disconnectButton;

	/**
	 * contains server port in gui
	 */
	@FXML
	private TextField portTextField;

	/**
	 * text to communicate server messages with users
	 */
	@FXML
	private TextArea consoleField;
	
    /**
     * GenerateReport button to generate for each store the report when it's the end of the month
     */
    @FXML
    private Button generateReportsButton;

	/**
	 * table containing all connections
	 */
	@FXML
	private TableView<ClientDetails> connectionTable;

	@FXML
	private TableColumn<ClientDetails, String> ipCol;

	@FXML
	private TableColumn<ClientDetails, String> hostCol;
	@FXML
	private TableColumn<ClientDetails, String> statusCol;
	/**
	 * Server controller to enable action with server
	 */
	private ServerController sv;
	
	private static ServerFormController sfc;
	
	private boolean isImported = false;

	/**
	 * By clicking on "Connect" button initialize connecting to Database and then to
	 * server. If Database could not connect properly server would not be started.
	 */
	@FXML
	void clickOnConnect(MouseEvent event) {
		//String port = portTextField.getText();
		String result = "";
		if(!getConnectionFieldsAndConnect() || DBNameField.getText().contains("external")) {
			consoleField.setText("Can't connect to externalDB.\n Use externalDB only to import users. \n");
			return;
		}
		sv = ServerController.getServerController();
		sv.setIp(IPTextField.getText());
		StringBuffer buff = new StringBuffer();
		buff.append(sv.connectToDB());
		if (DataBaseController.isConnected) {
			try {
				if(isImported) {
					String messageInsert = ImportUsers.insertUsersIntoZliDB();
					buff.append(messageInsert);
					if(messageInsert.contains("Users already"))
						importButton.setDisable(true);
				}
				result = sv.runServer();
				buff.append(result);
				if (result.contains("Server listening for connections on port")) {
					connectButton.setDisable(true);
					disconnectButton.setDisable(false);
				} else {
					if (sv.isListening())
						sv.close();
					System.out.println("Server could not started");

				}
			} catch (Exception e) {
				buff.append("ERROR - Could not listen for clients!\n");
			}
		}
		consoleField.setText(buff.toString());

		// connectionTable.getItems().addAll(ServerController.clients);
	}
	
	/**
	 * The parameters that given by the user, it will set the conncetion the database.
	 * If the parameters are illegal, it will return false and will not connect to the database.
	 * @return true or false, based on the parameters that given by the user.
	 */
	private boolean getConnectionFieldsAndConnect() {
		String ip = IPTextField.getText();
		String dbName = DBNameField.getText();
		String dbUsername = DBUserTextField.getText();
		String dbPassword = DBPasswordTextField.getText();
		String[] stringArray = new String[] { ip, dbName, dbUsername, dbPassword };

		if (!checkParameters())
			return false;
		List<String> connectionArray = Arrays.asList(stringArray);

		DataBaseController.setConnection(connectionArray);
		return true;
	}

	/**
	 * When the user will click on the disconnect button, it will close the server, the database connection and will close all the users that connected to the client.
	 */
	@FXML
	void clickOnDisconnect(MouseEvent event) {
		disconnectServerAndDB();
		connectButton.setDisable(false);
		disconnectButton.setDisable(true);
		consoleField.setText("Server and Database have disconnected.");
		connectionTable.refresh();
	}
	
	/**will close the applications and the running threads.
	 * 
	 */
	public void closeServerWindow() {
		if(sv!=null && sv.isListening())
			disconnectServerAndDB();
		else
			System.out.println("Server wasn't running");
		System.out.println("Dasvidanya ");
		System.exit(0);
	}

	/**
	 * The function disconnects from the OCSF Server and from the database.
	 */
	private void disconnectServerAndDB() {
		sv.disconnectServer();
		DataBaseController.Disconnect();
	}

	/**Checking the parameters that been given by the user.
	 * (Depending if the user inserted the right information).
	 * @return true or false
	 */
	private boolean checkParameters() {
		if (!portTextField.getText().matches("-?\\d+")) { // if the string is an integer, throw Exception
			consoleField.setText("ERROR - Could not connect!");
			return false;
		}
		return true;
	}
	
	/**
	 *by using the initialize function, it will initial the table and the context of the table
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		sfc = this;
		//setCellValueFactory: set for each attribute the cell in the table! so we can present the data in the table.
		ipCol.setCellValueFactory(new PropertyValueFactory<>("clientIP"));  // ip is client IP address
		hostCol.setCellValueFactory(new PropertyValueFactory<>("serverIP"));  // host is server IP address
		statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));  // can be either "connected" or "disconnected"

		connectionTable.setItems(ServerController.clients);
	}
	
    /**
     * If the users clicked on the import button when he connected to the externalDB, 
     * it will save the users into list to insert them into zli DB.
     * @param mouseEvent
     */
    @FXML
    void importUsersToZliAction(MouseEvent event) {
    	if(!getConnectionFieldsAndConnect() || DBNameField.getText().contains("zli")) {
    		consoleField.setText("\n Can't import zli db! \n Use only externalDB! \n");
    		return;
    	}
    	String importMessage = ImportUsers.importExternalDB();
    	consoleField.setText(importMessage);
    	if(importMessage.contains("Successfully")) {
    		isImported = true;
    		importButton.setDisable(true);
    	}
    }
	
	/**
	 * Refresh clients table after client connection/disconnection
	 */
	public static ServerFormController get() {
		return sfc;
	}
	
	/**
	 * If client connected to the server, it will refersh the table for the new clients that connected.
	 */
	public void refreshClientsTable() {
		connectionTable.refresh();
	}
	

    /**
     * If the user clicked on generateReport, it will generate the reports if it's the end of the month.
     * @param ActionEvent
     */
    @FXML
    void generateReports(ActionEvent event) {
    	sv.genrateReportCommand(new Date());
    }
}
