package gui;

import java.util.HashMap;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import util.Status;

public class LoginScreenController {
	@FXML
	private Label errorLabel;

	@FXML
	private Button loginButton;

	@FXML
	private PasswordField passwordLabel;

	@FXML
	private TextField usernameLabel;

    @FXML
    private Button backButton;
    
	@FXML
	void loginUserIntoSystem(MouseEvent event) {
		String username = usernameLabel.getText();
		String password = passwordLabel.getText();
		// at this point we need to login the user into the system
		// but we need to still add the functionality on the server side
		// ** add server connection
		// we assume the credentials were entered correctly
		HashMap<String, Object> message = new HashMap<String, Object>();
		message.put("command", "login user");
		message.put("username", username);
		message.put("password", password);
		if(username.equals("")||password.equals(""))
		{
			errorLabel.setText("Please Enter Username And Password!");
			errorLabel.setVisible(true);
			return;
		}
		switch ((Status) ClientFormController.client.accept(message)) {
		case NEW_LOG_IN:
			try {
				errorLabel.setVisible(false);
				
				ClientScreen.changeScene(ClientScreen.class.getResource("CatalogScreen2.fxml"), "Catalog");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case ALREADY_LOGGED_IN:
			errorLabel.setText("User Already Logged In");
			errorLabel.setVisible(true);
			break;
		case NOT_REGISTERED:
			errorLabel.setText("Username or Password is incorrect");
			errorLabel.setVisible(true);
			break;
		}
	}
    @FXML
    void changeToHomeScreen(MouseEvent event) {
		try {
			ClientScreen.changeScene(ClientScreen.class.getResource("HomeNotLoggedInScreen.fxml"), "Home");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}

