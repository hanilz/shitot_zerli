package client;

import java.io.IOException;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;
import util.ManageClients;
import util.ManageScreens;
import util.Screens;

/**
 * ClientFormController will handle all the events from the gui
 *
 */
public class ClientFormController {

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
	void clickOnConnectButton(MouseEvent event) {
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
		ManageScreens.changeScreenTo(Screens.CATALOG_SPLASH_SCREEN);

	}

}
