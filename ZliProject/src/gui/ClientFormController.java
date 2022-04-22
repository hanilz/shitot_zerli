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

public class ClientFormController {

	@FXML
	private Button connectToIPButton;

	@FXML
	private TextField ipTextField;

	@FXML
	private Label errorLabel;

	public static ClientController client = null;

	/**
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
						client.closeConnection();
						System.out.println("disconnected! yayyyy");

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
		changeSceneToViewOrders();
	}

	private void changeSceneToViewOrders() {
		try {
			ClientMain.changeScene(getClass().getResource("ViewOrders.fxml"), "View Orders");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
