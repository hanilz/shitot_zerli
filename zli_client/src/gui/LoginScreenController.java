package gui;

import java.util.HashMap;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginScreenController {
	@FXML
	private Label errorLabel;

	@FXML
	private Button loginButton;

	@FXML
	private TextField passwordLabel;

	@FXML
	private TextField usernameLabel;

	@FXML
    void loginUserIntoSystem(MouseEvent event) {
    	String username = usernameLabel.getText();
    	String password = passwordLabel.getText();
    	//at this point we need to login the user into the system 
    	//but we need to still add the functionality on the server side
    	//** add server connection
    	//we assume the credentials were entered correctly
		HashMap<String, Object> message = new HashMap<String, Object>();
		message.put("command", "login user");
		message.put("username", username);
		message.put("password", password);
		if((boolean)ClientFormController.client.accept(message)) {
			try {
				ClientScreen.changeScene(ClientScreen.class.getResource("CatalogScreen2.fxml"), "Catalog");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			errorLabel.setVisible(false);
		}
		else
			errorLabel.setVisible(true);	
    }

	// TODO: handle login with OCSF
	private Boolean loginClient(String username, String password) {
//    	if(user connected(username,password)) {
//    		return false;
//    	}
//    	
		return true;
	}
}
