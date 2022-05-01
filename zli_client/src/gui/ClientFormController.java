package gui;

import java.io.IOException;

import client.ClientController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;

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
	 * The initial screen: the user will insert the server-ip so he can connect to the server.
	 * By clicking the connect button, we will sent to the client controller the ip of the user so the server can update the connection table (which client is connected to the server)
	 * @param event
	 */
	@FXML
	void clickOnConnectButton(MouseEvent event) {
		String ip = ipTextField.getText();
		try {
			client = new ClientController(ip);
			System.out.println("All good bruh");
			ClientMain.clientStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent e) {
					try {
						if(client.isConnected()) {
							Object response = ClientFormController.client.accept("client disconnected");
							client.closeConnection();
							System.out.println("disconnected! yayyyy");
						}
						else {System.out.println("not connected to anything - babye!");}

					} catch (IOException ex) {
						System.out.println("Oh no!");
						ex.printStackTrace();
					}
				}
			});
		} catch (IOException e) {
			System.out.println("Shita");
			errorLabel.setVisible(true);
			return;
		}
		changeSceneToCatalog();
	}

//	/**
//	 * This function will help us to switch between the screens after the user connected to the server-ip
//	 */
//	private void changeSceneToViewOrders() {
//		try {
//			ClientMain.changeScene(getClass().getResource("ViewOrders.fxml"), "View Orders");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	/**
	 * This function will help us to switch between the screens after the user connected to the server-ip
	 */
	private void changeSceneToCatalog() {
		try {
			ClientMain.changeScene(getClass().getResource("CatalogScreen.fxml"), "Catalog");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
