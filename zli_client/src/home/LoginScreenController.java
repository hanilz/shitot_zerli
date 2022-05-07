package home;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.ClientFormController;
import client.ClientScreen;
import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.ManageScreens;
import util.Status;
import util.UserType;

public class LoginScreenController implements Initializable {
	@FXML
	private Label errorLabel;

	@FXML
	private Button loginButton;

	@FXML
	private PasswordField passwordLabel;

	@FXML
	private TextField usernameLabel;

	@FXML
	private Button backButton; // TODO - We need to remove the back button

	private static boolean isPopup;

	@FXML
	void loginUserIntoSystem(MouseEvent event) {
		String username = usernameLabel.getText();
		String password = passwordLabel.getText();
		HashMap<String, Object> message = new HashMap<String, Object>();
		message.put("command", "login user");
		message.put("username", username);
		message.put("password", password);
		checkUserInputAndLogin(username, password);
		HashMap<String, Object> response = (HashMap<String, Object>) ClientFormController.client.accept(message);
		responseAction(event, username, response);
	}

	private void checkUserInputAndLogin(String username, String password) {
		if (User.getUserInstance().isUserLoggedIn() || username.equals("") || password.equals("")) {// Label empty or
																									// already client or
																									// already logged in
			if (User.getUserInstance().isUserLoggedIn())// one user is already active in this client
			{
				if (User.getUserInstance().getUsername().equals(username))// user already logged in this computer
					errorLabel.setText("You already logged in as " + User.getUserInstance().getUsername());
				else// trying to log to a different account but must to logout first
					errorLabel.setText("First logout from user " + User.getUserInstance().getUsername());
				loginButton.setDisable(true);// user logged in
			} else// exist combination of the labels in which at least one is empty
				errorLabel.setText("Please enter Username and Password!");
			errorLabel.setVisible(true);// show error
			return;
		}
	}

	private void responseAction(MouseEvent event, String username, HashMap<String, Object> response) {
		switch ((Status) (response).get("response")) {
		case NEW_LOG_IN:
			try {
				errorLabel.setVisible(false);
				int idUser = (Integer) response.get("idUser");
				int idAccount = (Integer) response.get("idAccount");
				UserType userType = (UserType) response.get("userType");

				User.getUserInstance().login(idUser, username, idAccount, userType);// creating running user
				System.out.println("running : " + User.getUserInstance());
				if (popup(event))
					return;
				ManageScreens.changeScene(ClientScreen.class.getResource("../catalog/CatalogScreen.fxml"), "Catalog");//login
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

	public static void enablePopup(boolean Popup) {
		isPopup = Popup;
	}

	@FXML
	void changeToHomeScreen(MouseEvent event) {
		if (popup(event))
			return;
		try {
			ManageScreens.changeScene(ClientScreen.class.getResource("../home/HomeGuestScreen.fxml"), "Home");//back guest
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean popup(MouseEvent event) {
		if (isPopup) {
			Node n = ((Node) (event.getSource()));
			Stage s = ((Stage) n.getScene().getWindow());
			s.close();
			return true;
		}
		return false;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (User.getUserInstance().isUserLoggedIn()) {// one user is already active in this client
			loginButton.setDisable(true);// user logged in
			errorLabel.setText("You already logged in as " + User.getUserInstance().getUsername());
			errorLabel.setVisible(true);
		}

	}
}
