package gui;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import util.Status;
import util.UserType;

public class LoginScreenController implements Initializable{
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
		HashMap<String, Object> message = new HashMap<String, Object>();
		message.put("command", "login user");
		message.put("username", username);
		message.put("password", password);
		if (User.getUserInstance().isUserLoggedIn()||username.equals("") || password.equals("") ) {//Label empty or already client already logged in
			if (User.getUserInstance().isUserLoggedIn())//one user is already active in this client
			{
				if (User.getUserInstance().getUsername().equals(username))//user already logged in this computer
					errorLabel.setText("You already logged in as "+User.getUserInstance().getUsername() );
				else//trying to log to a different account but must to logout first
					errorLabel.setText("First logout from user " + User.getUserInstance().getUsername());
				loginButton.setDisable(true);//user logged in
			}
			else//exist combination of the labels in which at least one is empty
				errorLabel.setText("Please enter Username and Password!");
			errorLabel.setVisible(true);//show error
			return;
		}
		HashMap<String, Object> response = (HashMap<String, Object>) ClientFormController.client.accept(message);
		switch ((Status) (response).get("response")) {
		case NEW_LOG_IN:
			try {
				errorLabel.setVisible(false);
				int idUser = (Integer) response.get("idUser");
				int idAccount = (Integer) response.get("idAccount");
				UserType userType = (UserType) response.get("userType");

				User.getUserInstance().login(idUser, username, idAccount, userType);//creating running user
				System.out.println("running : "+User.getUserInstance());
				ClientScreen.changeScene(ClientScreen.class.getResource("CatalogScreen2.fxml"), "Catalog");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case ALREADY_LOGGED_IN:
			errorLabel.setText("User already logged in");
			errorLabel.setVisible(true);
			break;
		case NOT_REGISTERED:
			errorLabel.setText("Username or Password are incorrect");
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (User.getUserInstance().isUserLoggedIn()){//one user is already active in this client
			loginButton.setDisable(true);//user logged in
			errorLabel.setText("You already logged in as "+User.getUserInstance().getUsername() );
			errorLabel.setVisible(true);
		}
	
		
	}
}
