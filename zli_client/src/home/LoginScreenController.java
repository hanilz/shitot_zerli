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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;
import util.Commands;
import util.ManageScreens;
import util.Screens;
import util.Status;
import util.UserType;

/**
 * Login screen allowing to login to user that registered in the
 *         system
 */
public class LoginScreenController implements Initializable {
	/**
	 * Data structure used to transfer information between client and server
	 */
	private HashMap<String, Object> response;
	/**
	 * GUI Setting the error message
	 */
	@FXML
	private Label errorLabel;

	/**
	 * GUI Login button in login screen 
	 */
	@FXML
	private Button loginButton;

	/**
	 * GUI Password text in login screen 
	 */
	@FXML
	private PasswordField passwordLabel;

	/**
	 * GUI User name text in login screen
	 */
	@FXML
	private TextField usernameLabel;

	/**
	 * GUI back button in login screen 
	 */
	@FXML
	private Button backButton;

	private static boolean isCart, isCatalog;

	private static LoginScreenController instance;
	/**
	 * GUI Labels react to enter pressed if user already logged in disable login button
	 * 
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setInstance(this);
		setTextBehaviour(usernameLabel);
		setTextBehaviour(passwordLabel);
		if (isUserLoggedIn()) {// one user is already active in this client
			disableLoginButton();
			errorLabel.setText("You already logged in as " + User.getUserInstance().getUsername());
			errorLabel.setTextFill(Paint.valueOf("RED"));
		}
	}

	public static void setInstance(LoginScreenController loginInstance) {
		instance = loginInstance;
	}
	
	public boolean isUserLoggedIn() {
		return User.getUserInstance().isUserLoggedIn();
	}

	/**
	 * Read input from GUI, If input isn't empty check validation with DB then login
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
	 * @return if GUI Fields are not empty or a user is already logged in check GUI
	 *         login validation
	 */
	private boolean isUserInputValid(String username, String password) {
		boolean isInputValid = false;
		if (isUserLoggedIn()) {
			setError("Already logged in");
			disableLoginButton();
		} else if (username.equals("") || password.equals(""))
			setError("Please enter Username and Password!");
		else
			isInputValid = true;
		return isInputValid;
	}

	public void disableLoginButton() {
		loginButton.setDisable(true);
	}

	/**
	 * @param username
	 * @param password
	 * @return Status of input correlated to DB and user details, If can send
	 *         username and password from GUI to DB through Server
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
	public boolean responseAction(String username, HashMap<String, Object> response) {
		boolean result = false;
		switch ((Status) (response).get("response")) {
		case NEW_LOG_IN:
			loginUser(username);
			if (isCart) {
				return cartFlow();
			} else if (isCatalog) {
				catalogFlow();
				return true;
			}
			else {
				changeToHomeScreen();
				CloseWindow();
				return true;
			}
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
		return result;
	}

	public void changeToHomeScreen() {
		ManageScreens.home();
	}

	/**
	 * @param username Username given from GUI set all user data from DB and save as
	 *                 current user
	 */
	public void loginUser(String username) {
		int idUser = (Integer) response.get("idUser");
		int idAccount = (Integer) response.get("idAccount");
		UserType userType = (UserType) response.get("userType");
		double storeCredit = (double) response.get("storeCredit");
		User.getUserInstance().login(idUser, username, idAccount, userType, storeCredit);
	}

	/**
	 * Login in cart screen only allows customers users to login and continue to
	 * make a purchase
	 * @return 
	 */
	public boolean cartFlow() {
		if (isUserNotCustomer()) {
			setError("Only Customers can buy from catalog");
			logout();
			return false;
		} else {
			changeToGreetingCard();
			CloseWindow();
			return true;
		}
	}

	public void changeToGreetingCard() {
		CartController.changeToGreatingCard();
	}

	public void logout() {
		User.getUserInstance().logout();
	}

	public boolean isUserNotCustomer() {
		return User.getUserInstance().getType() != UserType.CUSTOMER
				&& User.getUserInstance().getType() != UserType.NEW_CUSTOMER;
	}

	/**
	 * Login in catalog screen if customer stay in catalog else change send to home
	 * screen
	 */
	public void catalogFlow() {
		if (User.getUserInstance().getType() == UserType.CUSTOMER) {
			ManageScreens.changeScreenTo(Screens.CATALOG);
		} else
			changeToHomeScreen();
		CloseWindow();
	}

	/**
	 * @param isCartFlow Set login flow to be from cart
	 */
	public static void loginFromCart() {
		setLoginFlow(true,false);
	}

	/**
	 * @param isCatalogFlow Set login flow to be from catalog
	 */
	public static void loginFromCatalog() {
		setLoginFlow(false,true);
	}
	/**
	 * @param isFromCart Set Flow Of Login From Cart
	 * @param isFromCatalog Set Flow Of Login From Catalog
	 * Open login popup and set behavior
	 */
	private static void setLoginFlow(boolean isFromCart,boolean isFromCatalog)
	{
		isCart = isFromCart;
		isCatalog = isFromCatalog;
		instance.changeToLogin();
	}

	public void changeToLogin() {
		ManageScreens.changeScreenTo(Screens.LOGIN);
	}

	/**
	 * Back button close login popup
	 */
	@FXML
	private void back() {
		CloseWindow();
	}

	/**
	 * Closing login popup
	 */
	public void CloseWindow() {
		ManageScreens.getPopupStage().close();
	}

	/**
	 * Reset login flow to be default(Login From Guest Home)
	 */
	public static void resetLogin() {
		isCart = false;
		isCatalog = false;
	}

	/**
	 * @param err=Error message present error message on GUI
	 */
	public void setError(String err) {
		errorLabel.setText(err);
		errorLabel.setTextFill(Paint.valueOf("RED"));
	}

	/**
	 * @param textField setting TextField to response to enter pressed
	 */
	private void setTextBehaviour(TextField textField) {
		textField.setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				loginUserIntoSystem(event);
			}
		});
	}
}
