package mangeUsers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.ClientFormController;
import entities.ManageUsers;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import util.Commands;
import util.ManageClients;
import util.ManageScreens;
import util.Screens;
import util.UserType;

/**
 * @author dolev Adding/Removing a chosen employee screens cards(VBox as
 *         Buttons) from their home screen
 */
public class ManageUsersPermissionController implements Initializable {

	/**
	 * GUI contains all screen the specific user is given
	 */
	@FXML
	private TilePane userHomeScreens;

	/**
	 * GUI showing a list of employees(id+name) to choose from
	 */
	@FXML
	private ComboBox<String> usersOption;
	/**
	 * GUI user type of chosen employee
	 */
	@FXML
	private Label userTypeLable;

	/**
	 * GUI name of chosen employee
	 */
	@FXML
	private Label usernameLable;
	/**
	 * GUI add screens button(HBox as a button)
	 */
	@FXML
	private HBox addScreenButton;

	/**
	 * GUI setting default screens button(HBox as a button)
	 */
	@FXML
	private HBox defaultScreenButton;

	/**
	 * GUI saving shown screens button(HBox as a button)
	 */
	@FXML
	private HBox saveScreenButton;

	/**
	 * GUI error message shown to the user that managing the screens
	 */
	@FXML
	private Label errorLable;
	/**
	 * List of employees
	 */
	private static ObservableList<ManageUsers> users = FXCollections.observableArrayList();

	/**
	 * User id of the chosen employee
	 */
	private int userId;
	/**
	 * User type of the chosen employee
	 */
	private UserType userType;
	/**
	 * User home screens of the chosen employee
	 */
	private static ArrayList<Screens> userScreens, initUserScreens = new ArrayList<>();
	/**
	 * Static access to this screen
	 */
	private static ManageUsersPermissionController instace;
	/**
	 * The current user the the manager choose
	 */
	private ManageUsers curUser;
	/**
	 * Timing the error message
	 */
	private PauseTransition visiblePause;

	/**
	 * Setting up the GUI for a specific user screens
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		instace = this;
		users = getUsersFromDB();
		ObservableList<String> idAndName = FXCollections.observableArrayList();
		for (ManageUsers user : users) {
			idAndName.add(user.getIdUser() + " " + user.getFirstName());
		}
		usersOption.setItems(idAndName);// set the information in the table
		if (AddScreensController.getUser() != null) {
			curUser = AddScreensController.getUser();
			setTextForUser();
			getUserHomeScreen();
		}
		showMessage("User can have Up to 6 Screens", "blue");
	}

	/**
	 * Clicking on Save User Screens, Saving the showing screens in DB for the
	 * specific user
	 */
	public void clickSaveScreen() {
		if (userScreens.isEmpty()) {
			showMessage("No Screens Is NOT Allowed", "red");
		} else if (!saveScreensInDB()) {
			showMessage("Screens Not Saved Due To A DB Problem", "red");
		}
		disableSave();
		setInitUserScreen();
		showMessage("Screens Saved", "blue");

	}

	/**
	 * @param event Change screen to home screen
	 */
	@FXML
	void changeToHome(Event event) {
		AddScreensController.removeUser();
		ManageScreens.home();
	}

	/**
	 * Adding the screen that were added in Add screen
	 */
	private void setInitUserScreen() {
		initUserScreens.clear();
		initUserScreens.addAll(userScreens);
	}

	/**
	 * @return If screens were saved properly Sending the screens to the server and
	 *         saving them in DB
	 */
	private boolean saveScreensInDB() {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.SAVE_SCREENS);// might change
		message.put("id", curUser.getIdUser());
		message.put("screens", userScreens);
		return (boolean) ClientFormController.client.accept(message);
	}

	/**
	 * @return List of employees Getting the list of employees related to the user
	 *         in the manage screens
	 */
	@SuppressWarnings("unchecked")
	private ObservableList<ManageUsers> getUsersFromDB() {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.FETCH_ALL_EMPLOYEES);// might change
		Object response = ClientFormController.client.accept(message);
		return (ObservableList<ManageUsers>) response;
	}

	/**
	 * Get selected User details
	 */
	@FXML
	void getUser(ActionEvent event) {
		disableSave();
		curUser = null;
		AddScreensController.removeUser();
		userId = Integer.parseInt(usersOption.getValue().split(" ")[0]);
		for (ManageUsers user : users) {
			if (user.getIdUser() == userId) {
				curUser = user;
				setTextForUser();
			}
		}
		getUserHomeScreen();
	}

	/**
	 * Setting chosen employee details
	 */
	private void setTextForUser() {
		userType = UserType.valueOf(curUser.getUserType());
		userTypeLable.setText(curUser.getUserType().toString());
		usernameLable.setText(curUser.getFirstName() + " " + curUser.getLastName());
	}

	/**
	 * Show the screens of the employee
	 */
	void getUserHomeScreen() {// get home icons
		if (usersOption.getValue() == null && curUser == null) {
			showMessage("ERROR! PLEASE SELECT A USER FIRST!", "red");
		} else {
			if (AddScreensController.getUser() != null)
				userScreens = AddScreensController.setScreens();// get screens
			else {
				userScreens = getScreensFromDB();
				setInitUserScreen();
			}
			setScreen(userScreens);
		}
	}

	/** 
	 * setting the message by the message that been sent and coloring
	 * it by the color that been sent
	 */
	private void showMessage(String message, String color) {
		errorLable.setText(message);
		errorLable.setTextFill(Paint.valueOf(color));
		errorLable.setVisible(true);
		if (visiblePause != null)
			visiblePause.stop();
		visiblePause = new PauseTransition(Duration.seconds(10));
		visiblePause.setOnFinished(event -> errorLable.setVisible(false));
		visiblePause.play();
	}

	/**
	 * @return Screen of chosen employee from DB
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<Screens> getScreensFromDB() {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.GET_USER_SCREENS);
		message.put("id", userId);
		message.put("userType", userType);
		return (ArrayList<Screens>) ClientFormController.client.accept(message);
	}

	/**
	 * Setting the default screen of the specific employee
	 */
	public void setDefaultScreens() {
		userScreens = ManageClients.getUserScreens(userType);
		setScreen(userScreens);
	}

	/**
	 * @param userScreens2
	 * Setting the employee screens to be the given one
	 */
	private void setScreen(ArrayList<Screens> userScreens2) {
		userHomeScreens.getChildren().clear();
		if (userScreens2 != null)
			for (Screens screen : userScreens2) {
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(getClass().getResource("ManageHomeVBox.fxml"));
				VBox vbox = null;
				try {
					vbox = fxmlLoader.load();
				} catch (IOException e) {
					e.printStackTrace();
				}
				ManageHomeVBoxController manageHomeVBoxController = fxmlLoader.getController();
				manageHomeVBoxController.setScreen(screen);
				userHomeScreens.getChildren().add(vbox);
			}
		enableButtons();
	}

	/**
	 * Only if there is difference in the employee screens allow to save 
	 */
	public void enableSave() {
		if (!userScreens.equals(initUserScreens) && !userScreens.isEmpty()) {
			saveScreenButton.setDisable(false);
			saveScreenButton.setStyle("-fx-background-radius: 20; -fx-background-color: AAC6BD;");// AAC6BC
			showMessage("Please Save Before Switching ID", "red");
		} else
			disableSave();
		if (userScreens.isEmpty()) {
			showMessage("User Must Have At Least One Screen", "red");
		}
	}

	/**
	 * Only if employee screen are not the same as default allow to set to be default screens(defined in ManageClients)
	 */
	public void enableDefault() {
		if (userScreens.containsAll(ManageClients.getUserScreens(UserType.valueOf(curUser.getUserType())))
				&& (ManageClients.getUserScreens(UserType.valueOf(curUser.getUserType())).containsAll(userScreens))) {
			defaultScreenButton.setStyle("-fx-background-radius: 20; -fx-background-color: E4C2C2;");
			defaultScreenButton.setDisable(true);
		} else {
			defaultScreenButton.setStyle("-fx-background-radius: 20; -fx-background-color: AAC6BC;");
			defaultScreenButton.setDisable(false);
		}
	}

	/**
	 * Allow/Forbid buttons to be clicked 
	 */
	private void enableButtons() {
		enableSave();
		enableDefault();
		enableAddButton();
	}

	/**
	 * Only if employee has more than 5 screens forbid adding screens
	 */
	private void enableAddButton() {
		if (userScreens.size() == 6) {
			addScreenButton.setStyle("-fx-background-radius: 20; -fx-background-color: E4C2C2;");
			addScreenButton.setDisable(true);
			showMessage("Can only have up to 6 screens", "red");
		} else {
			addScreenButton.setStyle("-fx-background-radius: 20; -fx-background-color: AAC6BC;");
			addScreenButton.setDisable(false);
		}
	}

	/**
	 * Disable save button 
	 */
	public void disableSave() {
		saveScreenButton.setDisable(true);
		saveScreenButton.setStyle("-fx-background-radius: 20; -fx-background-color: E4C2C2;");
	}

	/**
	 * @param screen
	 * Remove the given screen from the employee screens
	 */
	public static void removeScreen(Screens screen) {
		userScreens.remove(screen);
		instace.setScreen(userScreens);
	}

	/**
	 * @param screen
	 * Add the given screen to the employee screens
	 */
	public static void addScreen(Screens screen) {
		userScreens.add(screen);
		instace.setScreen(userScreens);
	}

	/**
	 * @return controller of the screen
	 */
	public static ManageUsersPermissionController connect() {
		return instace;
	}

	/**
	 * Clicking on add screens change to Adding screens screen
	 */
	public void addScreens() {
		ManageScreens.changeScreenTo(Screens.ADD_SCREENS);
	}

	/**
	 * @return Employee user type
	 */
	public UserType getUserType() {
		return userType;
	}

	/**
	 * @return Employee screens
	 */
	public static ArrayList<Screens> getUserScreens() {
		return userScreens;
	}

	/**
	 * @return employee user id
	 */
	public int UserId() {
		return userId;
	}

	/**
	 * @return Employee
	 */
	public ManageUsers User() {
		return curUser;
	}
}
