package client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.WindowEvent;
import util.ManageClients;
import util.ManageScreens;
import util.Screens;

/**
 * ClientFormController will handle all the events from the gui
 *
 */
public class ClientFormController implements Initializable{

	@FXML
	private Button connectToIPButton;

	@FXML
	private TextField ipTextField;

	/**
	 * indicator if the user connect to the server or not.
	 */
	@FXML
	private Label errorLabel;

	public static ClientController client = null;

	/**
	 * The initial screen: the user will insert the server-ip so he can connect to
	 * the server. By clicking the connect button, we will sent to the client
	 * controller the ip of the user so the server can update the connection table
	 * (which client is connected to the server)
	 * 
	 * @param event
	 */
	@FXML
	void clickOnConnectButton(Event event) {
		String ip = ipTextField.getText();
		try {
			client = new ClientController(ip);
			System.out.println("All good bruh");
			exitFromWindow();
		} catch (IOException e) {
			System.out.println("Shita");
			errorLabel.setVisible(true);
			return;
		}
		changeSceneToCatalog();
	}
	
	/**
	 * Disconnectiong the client from the server after the user clicked on 'X' button.
	 */
	public void exitFromWindow() {
		ManageScreens.getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {  // Windows X button pressed
			@Override
			public void handle(WindowEvent e) {
				ManageClients.exitClient();
				ManageScreens.closeAllPopups();
			}
		});
	}

	/**
	 * This function will help us to switch between the screens after the user
	 * connected to the server-ip
	 */
	private void changeSceneToCatalog() {
		ManageScreens.changeScreenTo(Screens.LANDING);
	}

	/**
	 * If the user clicking on enter rather on 'Connect to Server' button.
	 * @param txt
	 */
	private void setTextBehaviour(Node txt) {
		txt.setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				clickOnConnectButton(event);
			}
		});
	}

	/**
	 * Initialize the behaviour when the user clicks on enter rather on the button.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setTextBehaviour(ipTextField);		
	}	
}
