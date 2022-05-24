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
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import util.Commands;
import util.ManageScreens;
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
	private Button backButton;

	private static boolean isCart, isCatalog;

	@SuppressWarnings("unchecked")
	@FXML
	void loginUserIntoSystem(Event event) {
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

	private void responseAction(Event event, String username, HashMap<String, Object> response) {
		switch ((Status) (response).get("response")) {
		case NEW_LOG_IN:
			loginUser(username);
			if (isCart) {
				cartFlow(event);
			} else if (isCatalog)
				catalogFlow(event);
			else
				CloseWindow(event);
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

	private void cartFlow(Event event) {
		if (User.getUserInstance().getType() != UserType.CUSTOMER) {
			setError("Only Customers can buy from catalog");
			System.out.println(errorLabel.getText());
			;
			User.getUserInstance().logout();
		} else {
			CloseWindow(event);
		}
	}

	private void setError(String err) {
		errorLabel.setText(err);
		errorLabel.setTextFill(Paint.valueOf("RED"));
	}

	public static void enableCartPopup(boolean isCartFlow) {
		isCart = isCartFlow;
	}

	public static void enableCatalogFlow(boolean isCatalogFlow) {
		isCatalog = isCatalogFlow;
	}

	@FXML
	void back(MouseEvent event) {
		CloseWindow(event);
	}

	private void loginUser(String username) {
		int idUser = (Integer) response.get("idUser");
		int idAccount = (Integer) response.get("idAccount");
		UserType userType = (UserType) response.get("userType");
		User.getUserInstance().login(idUser, username, idAccount, userType);// creating running user
	}

	private void catalogFlow(Event event) {
		if (User.getUserInstance().getType() == UserType.CUSTOMER)
			ManageScreens.previousScreen();
		else
			ManageScreens.home();
		CloseWindow(event);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setTextBehaviour(usernameLabel);
		setTextBehaviour(passwordLabel);
		if (User.getUserInstance().isUserLoggedIn()) {// one user is already active in this client
			loginButton.setDisable(true);// user logged in
			errorLabel.setText("You already logged in as " + User.getUserInstance().getUsername());
			errorLabel.setTextFill(Paint.valueOf("RED"));
		}
	}

	private void CloseWindow(Event event) {
		Node n = ((Node) (event.getSource()));
		Stage s = ((Stage) n.getScene().getWindow());
		s.close();
	}

	private void setTextBehaviour(TextField txt) {
		txt.setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				loginUserIntoSystem(event);
			}
		});
	}
}
