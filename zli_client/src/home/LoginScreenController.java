package home;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import cart.CartController;
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
import util.Screens;
import util.Status;
import util.UserType;

/**
 * @author dolev Login Screen Allowing to Login To User That Registered In The
 *         System
 */
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

	/**
	 * Labels React to Enter Pressed If User Already Logged In Disable Login Button
	 * in GUI
	 */
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

	/**
	 * Read Input from GUI, If Input isn't Empty Check Validation with DB then Login
	 */
	@FXML
	void loginUserIntoSystem(Event event) {
		String username = usernameLabel.getText();
		String password = passwordLabel.getText();
		if (!isUserInputValid(username, password))
			return;
		response = setRespondToServer(username, password);
		responseAction(username, response);
	}

	/**
	 * @param username
	 * @param password
	 * @return if GUI Fields are not empty or A User is Already Logged in Check GUI
	 *         Login Validation
	 */
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

	/**
	 * @param username
	 * @param password
	 * @return Status of Input Correlated to DB and User Details, if Can Send Send
	 *         Username and Password From GUI to DB through Server
	 */
	@SuppressWarnings("unchecked")
	private HashMap<String, Object> setRespondToServer(String username, String password) {
		HashMap<String, Object> message = new HashMap<String, Object>();
		message.put("command", Commands.LOGIN);
		message.put("username", username);
		message.put("password", password);
		return (HashMap<String, Object>) ClientFormController.client.accept(message);
	}

	/**
	 * @param username Username Given from GUI
	 * @param response Login Information Returned from Server Check Login
	 *                 Status(NEW_LOG_IN=Valid,ALREADY_LOGGED_IN=Not
	 *                 Valid,NOT_REGISTERED= Not Valid,SUSPENDED=,Not Valid) IfValid
	 *                 Try to Login User If Flow Allows and Change The Screen
	 *                 According to the Flow. 
	 *                 Flows: Catalog:Customers-> Catalog,else-> User Home.
	 *                 Cart:Customers->Greeting Card,else = CANNOT LOG IN
	 *                 Home:Every User->User Home
	 */
	private void responseAction(String username, HashMap<String, Object> response) {
		switch ((Status) (response).get("response")) {
		case NEW_LOG_IN:
			loginUser(username);
			if (isCart) {
				cartFlow();
			} else if (isCatalog)
				catalogFlow();
			else {
				ManageScreens.home();
				CloseWindow();
			}
			break;
		case ALREADY_LOGGED_IN:
			setError("User already logged in");
			break;
		case NOT_REGISTERED:
			setError("Username or Password are incorrect");
			break;
		case SUSPENDED:
			setError("User Suspended");
			break;
		}

	}

	/**
	 * @param username Username Given from GUI Set All User Data From DB and Save as
	 *                 Current User
	 */
	private void loginUser(String username) {
		int idUser = (Integer) response.get("idUser");
		int idAccount = (Integer) response.get("idAccount");
		UserType userType = (UserType) response.get("userType");
		double storeCredit = (double) response.get("storeCredit");
		User.getUserInstance().login(idUser, username, idAccount, userType, storeCredit);
	}

	/**
	 * Login In Cart Screen Only Allows Customers Users To Login And Continue To
	 * Make A Purchase
	 */
	private void cartFlow() {
		if (User.getUserInstance().getType() != UserType.CUSTOMER
				&& User.getUserInstance().getType() != UserType.NEW_CUSTOMER) {
			setError("Only Customers can buy from catalog");
			User.getUserInstance().logout();
		} else {
			CartController.changeToGreatingCard();
			CloseWindow();
		}
	}

	/**
	 * Login In Catalog Screen If Customer Stay in Catalog Else Change Send to Home
	 * Screen
	 */
	private void catalogFlow() {
		if (User.getUserInstance().getType() == UserType.CUSTOMER) {
			ManageScreens.changeScreenTo(Screens.CATALOG);
			CloseWindow();
		} else
			ManageScreens.home();
		CloseWindow();
	}

	/**
	 * @param isCartFlow set Login Flow To Be From Cart
	 */
	public static void loginFromCart() {
		setLoginFlow(true,false);
	}

	/**
	 * @param isCatalogFlow Set Login Flow To Be From Catalog
	 */
	public static void loginFromCatalog() {
		setLoginFlow(false,true);
	}
	/**
	 * @param isFromCart Set Flow Of Login From Cart
	 * @param isFromCatalog Set Flow Of Login From Catalog
	 * Open Login Popup And set Behavior
	 */
	private static void setLoginFlow(boolean isFromCart,boolean isFromCatalog)
	{
		isCart = isFromCart;
		isCatalog = isFromCatalog;
		ManageScreens.changeScreenTo(Screens.LOGIN);
	}

	/**
	 * Back Button Close Login Popup
	 */
	@FXML
	private void back() {
		CloseWindow();
	}

	/**
	 * Closing Login Popup
	 */
	private void CloseWindow() {
		ManageScreens.getPopupStage().close();
	}

	/**
	 * Reset Login FLow To Be Default(Login From Guest Home)
	 */
	public static void resetLogin() {
		isCart = false;
		isCatalog = false;
	}

	/**
	 * @param err=Error Message Present Error Message on GUI
	 */
	private void setError(String err) {
		errorLabel.setText(err);
		errorLabel.setTextFill(Paint.valueOf("RED"));
	}

	/**
	 * @param textField setting TextField to Response to Enter Pressed
	 */
	private void setTextBehaviour(TextField textField) {
		textField.setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				loginUserIntoSystem(event);
			}
		});
	}

}
