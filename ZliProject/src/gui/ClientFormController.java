package gui;

import java.io.IOException;

import client.ClientController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ClientFormController {

    @FXML
    private Button connectToIPButton;

    @FXML
    private TextField ipTextField;
    
    @FXML
    private Label errorLabel;

    @FXML
    void clickOnConnectButton(MouseEvent event) {
    	String ip = ipTextField.getText();
    	try {
			ClientController client = new ClientController(ip);
			System.out.println("All good bruh");
		} catch (IOException e) {
			System.out.println("Shita");
	    	errorLabel.setVisible(true);
		}
    	
    }

}
