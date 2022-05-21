package home;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.ClientFormController;
import entities.User;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.Commands;
import util.ManageScreens;
import util.Screens;
import util.Status;
import util.UserType;

public class LoginScreenController implements Initializable {
	private HashMap<String, Object> response;
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
	
	private static boolean isPopup,isHome=true;

	@SuppressWarnings("unchecked")
	@FXML
	void loginUserIntoSystem(MouseEvent event) {
		String username = usernameLabel.getText();
		String password = passwordLabel.getText();
		if (!isUserInputValid(username, password))
			return;
		HashMap<String, Object> message = setRespondToServer(username, password);
		response = (HashMap<String, Object>) ClientFormController.client.accept(message);
		responseAction(event, username, response);
	}

	private HashMap<String, Object> setRespondToServer(String username, String password) {
		HashMap<String, Object> message = new HashMap<String, Object>();
		message.put("command", Commands.LOGIN);
		message.put("username", username);
		message.put("password", password);
		return message;
	}

	private boolean isUserInputValid(String username, String password) {
		boolean isInputValid = false;
		if (User.getUserInstance().isUserLoggedIn()) {
			setError("Already logged in");
			loginButton.setDisable(true);
		} else if (username.equals("") || password.equals(""))
			setError("Please enter Username and Password!");
		else
			isInputValid = true;
		return isInputValid;
	}

	private void responseAction(MouseEvent event, String username, HashMap<String, Object> response) {
		switch ((Status) (response).get("response")) {
		case NEW_LOG_IN:
			loginUser(username);
			if (isCatalogPopUp(event)) {
				if (User.getUserInstance().getType() != UserType.CUSTOMER)
					setError("Only Customers can buy from catalog");
				return;
			} else
				ManageScreens.home();
			break;
		case ALREADY_LOGGED_IN:
			setError("User already logged in");
			break;
		case NOT_REGISTERED:
			setError("Username or Password are incorrect");
			break;
		case SUSPENDED:
			setError("User Suspended");
		}
	}

	private void setError(String err) {
		errorLabel.setText(err);
		errorLabel.setVisible(true);
	}

	public static void enablePopup(boolean Popup) {
		isPopup = Popup;
	}
	public static void enableHomeFlow(boolean flowToHome) {
		isHome = flowToHome;
	}

	@FXML
	void changeToHomeScreen(MouseEvent event) {
		if (isCatalogPopUp(event)) {
			CloseWindow(event);
		} else if(isHome)
			ManageScreens.home();
		else 
			ManageScreens.changeScreenTo(Screens.CATALOG);
	}

	private void loginUser(String username) {
		errorLabel.setVisible(false);
		int idUser = (Integer) response.get("idUser");
		int idAccount = (Integer) response.get("idAccount");
		UserType userType = (UserType) response.get("userType");
		User.getUserInstance().login(idUser, username, idAccount, userType);// creating running user
	}

	private boolean isCatalogPopUp(MouseEvent event) {
		if (isPopup) {
			if (User.getUserInstance().getType() == UserType.CUSTOMER)
				CloseWindow(event);
			else
				User.getUserInstance().logout();
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

	private void CloseWindow(Event event) {
		Node n = ((Node) (event.getSource()));
		Stage s = ((Stage) n.getScene().getWindow());
		s.close();
	}
}
