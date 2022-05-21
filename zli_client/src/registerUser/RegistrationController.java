package registerUser;

import java.util.HashMap;

import client.ClientFormController;
import entities.AccountPayment;
import entities.UserDetails;
import inputs.InputChecker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import util.Commands;
import util.ManageScreens;

public class RegistrationController {

	@FXML
	private Label errorLabel;

	@FXML
	private TextField cardNumberLabel;

	@FXML
	private Button closeButton;

	@FXML
	private Button completeRegistrationButton;

	@FXML
	private TextField emailLabel;

	@FXML
	private TextField firstNameLabel;

	@FXML
	private ImageView homeImage;

	@FXML
	private TextField idLabel;

	@FXML
	private TextField lastNameLabel;

	@FXML
	private TextField monthLabel;

	@FXML
	private TextField passwordLabel;

	@FXML
	private TextField phoneLabel;

	@FXML
	private TextField usernameLabel;

	@FXML
	private TextField yearLabel;

	@FXML
	private TextField cvvLabel;

	@FXML
	void createNewCustomerComplaint(ActionEvent event) {
		if (!checkFields()) {
			errorLabel.setText("*Please fill all the fields before submitting!");
			errorLabel.setVisible(true);
			return;
		} else
			errorLabel.setVisible(false);

		// check id correctness
		if (!InputChecker.checkID(idLabel.getText())) {
			errorLabel.setText("*ID is incorrect - make sure to enter exactly 9 numeric digits!");
			errorLabel.setVisible(true);
			return;
		} else
			errorLabel.setVisible(false);

		if (!InputChecker.checkPaymentInput("pass", "pass",
				cardNumberLabel.getText(), monthLabel.getText(), yearLabel.getText(), cvvLabel.getText(),
				"000000000")) {
			errorLabel.setText("*Please check your payment information!");
			errorLabel.setVisible(true);
			return;
		} else
			errorLabel.setVisible(false);

		// check phone number
		if (!InputChecker.isPhoneNumberVaild(phoneLabel.getText())) {
			errorLabel.setText("*Phone number is incorrect - make sure to enter 9 - 10 numeric digits!");
			errorLabel.setVisible(true);
			return;
		} else
			errorLabel.setVisible(false);

		// add to user_details
		int idAccount = sendUserDetailsToDB();
		if (idAccount == -1) {
			errorLabel.setText("User already exists - try entering a different ID!");
			errorLabel.setVisible(true);
			return;
		} else
			errorLabel.setVisible(false);

		// add to users
		int idUser = sendUserToDB(idAccount);
		if (idUser == -1) {
			errorLabel.setText("Failed to add user!");
			errorLabel.setVisible(true);
			//TODO remove user for user_details table
			deleteUserDetails(idAccount);
			return;
		} else
			errorLabel.setVisible(false);

		//add payment Method
		if (!sendPaymentToDB(idUser)) {
			errorLabel.setText("Failed to Payment info!");
			errorLabel.setVisible(true);
			return;
		} else
			errorLabel.setVisible(false);
		
		displayPopUp("User Registered","Congrats on registering a new user");
		ManageScreens.home();
	}

	private void deleteUserDetails(int idAccount) {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.DELETE_USER_DETAILS);
		message.put("idAccount", idAccount);
		Object response = ClientFormController.client.accept(message);
		if((boolean)response)
			System.out.println("deleted user: "+ idAccount);
	}

	private void displayPopUp(String title, String text) {
		Alert a = new Alert(AlertType.NONE,title,ButtonType.CLOSE);
		a.setTitle(title);
		a.setContentText(text);
		a.show();
	}

	// this method is used to insert a user into user_details Table
	private int sendUserDetailsToDB() {
		UserDetails ud = new UserDetails(0, firstNameLabel.getText(), lastNameLabel.getText(), idLabel.getText(),
				emailLabel.getText(), phoneLabel.getText(), "Active");
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.NEW_USER_DETAILS);
		message.put("User Details", ud);
		Object response = ClientFormController.client.accept(message);
		return (int) response;
	}

	// this method is used to insert a user into users Table
	private int sendUserToDB(int idAccount) {
		String username = usernameLabel.getText();
		String password = passwordLabel.getText();
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.NEW_USERS);
		message.put("username", username);
		message.put("password", password);
		message.put("idAccount", idAccount);
		Object response = ClientFormController.client.accept(message);
		return (int) response;
	}

	// this method is used to insert user payment Method to DB
	private boolean sendPaymentToDB(int idUser) {
		AccountPayment ap = new AccountPayment(firstNameLabel.getText() + " " + lastNameLabel.getText(),
				cardNumberLabel.getText(), monthLabel.getText() + "/" + yearLabel.getText(), cvvLabel.getText(),
				idUser);
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.INSERT_ACCOUNT_PAYMENT);
		message.put("account payment", ap);
		Object response = ClientFormController.client.accept(message);
		if(((String)response).equals("insert account payment failed"))
			return false;
		return true;
	}

    @FXML
    void returnHome(MouseEvent event) {
    	ManageScreens.home();
    }

    @FXML
    void returnHomebtn(ActionEvent event) {
    	ManageScreens.home();
    }

	// this method is used to check if all fields were filled in this form
	private boolean checkFields() {
		if (firstNameLabel.getText().isEmpty() || lastNameLabel.getText().isEmpty() || idLabel.getText().isEmpty()
				|| monthLabel.getText().isEmpty() || yearLabel.getText().isEmpty() || phoneLabel.getText().isEmpty()
				|| passwordLabel.getText().isEmpty() || usernameLabel.getText().isEmpty()
				|| cardNumberLabel.getText().isEmpty() || emailLabel.getText().isEmpty()
				|| cvvLabel.getText().isEmpty()) {
			return false;
		}
		return true;
//		boolean ok = true;
//		if (firstNameLabel.getText().isEmpty()) {
//			firstNameLabel.styleProperty().set("-fx-background-color : PINK");
//			ok = false;
//		} else {
//			firstNameLabel.styleProperty().set("-fx-background-color : NONE");
//		}
//		if (lastNameLabel.getText().isEmpty()) {
//			lastNameLabel.styleProperty().set("-fx-background-color : PINK");
//			ok = false;
//		} else
//			lastNameLabel.styleProperty().set("-fx-background-color : NONE");
//		if (idLabel.getText().isEmpty()) {
//			idLabel.styleProperty().set("-fx-background-color : PINK");
//			ok = false;
//		} else
//			idLabel.styleProperty().set("-fx-background-color : NONE");
//		if (monthLabel.getText().isEmpty()) {
//			monthLabel.styleProperty().set("-fx-background-color : PINK");
//			ok = false;
//		} else
//			monthLabel.styleProperty().set("-fx-background-color : NONE");
//		if (yearLabel.getText().isEmpty()) {
//			yearLabel.styleProperty().set("-fx-background-color : PINK");
//			ok = false;
//		} else
//			yearLabel.styleProperty().set("-fx-background-color : NONE");
//		if (phoneLabel.getText().isEmpty()) {
//			phoneLabel.styleProperty().set("-fx-background-color : PINK");
//			ok = false;
//		} else
//			phoneLabel.styleProperty().set("-fx-background-color : NONE");
//		if (passwordLabel.getText().isEmpty()) {
//			passwordLabel.styleProperty().set("-fx-background-color : PINK");
//			ok = false;
//		} else
//			passwordLabel.styleProperty().set("-fx-background-color : NONE");
//		if (usernameLabel.getText().isEmpty()) {
//			usernameLabel.styleProperty().set("-fx-background-color : PINK");
//			ok = false;
//		} else
//			usernameLabel.styleProperty().set("-fx-background-color : NONE");
//		return ok;
	}
}
