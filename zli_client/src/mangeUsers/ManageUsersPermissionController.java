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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import util.Commands;
import util.ManageClients;
import util.ManageScreens;
import util.Screens;
import util.UserType;

public class ManageUsersPermissionController implements Initializable {

	@FXML
	private TilePane userHomeScreens;

	@FXML
	private ComboBox<Integer> usersOption;
	@FXML
	private Label userTypeLable;

	@FXML
	private Label usernameLable;
	@FXML
	private Button addScreenButton;

	@FXML
	private Button defaultScreenButton;

	@FXML
	private Button saveScreenButton;

	private static ObservableList<ManageUsers> users = FXCollections.observableArrayList();
	@FXML
	private Label errorLable;
	private int userId;
	private UserType userType;
	private static ArrayList<Screens> userScreens, initUserScreens = new ArrayList<>();
	private static ManageUsersPermissionController instace;
	private ManageUsers curUser;

	@FXML
	void changeToHome(Event event) {
		AddScreensController.removeUser();
		ManageScreens.home();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		instace = this;
		users = getUsersFromDB();
		ObservableList<Integer> ids = FXCollections.observableArrayList();
		for (ManageUsers user : users) {
			ids.add(user.getIdUser());
		}
		usersOption.setItems(ids);// set the information in the table
		if (AddScreensController.getUser() != null) {
			curUser = AddScreensController.getUser();
			setTextForUser();
			getUserHomeScreen();
		}
	}

	public void clickSaveScreen() {
		if (userScreens.isEmpty()) {
			errorLable.setText("No Screens Is NOT Allowed");
			showError();
		} else if (!saveScreensInDB()) {
			errorLable.setText("Screens Not Saved Due To A DB Problem");
			showError();
		}
		disableSave();
		setInitUserScreen();
	}

	private void setInitUserScreen() {
		initUserScreens.clear();
		initUserScreens.addAll(userScreens);
	}

	private boolean saveScreensInDB() {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.SAVE_SCREENS);// might change
		message.put("id", curUser.getIdUser());
		message.put("screens", userScreens);
		return (boolean) ClientFormController.client.accept(message);
	}

	@SuppressWarnings("unchecked")
	private ObservableList<ManageUsers> getUsersFromDB() {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.FETCH_ALL_USERS_DETAILS);// might change
		Object response = ClientFormController.client.accept(message);
		return (ObservableList<ManageUsers>) response;
	}

	@FXML
	void getUser(ActionEvent event) {
		disableSave();
		curUser = null;
		AddScreensController.removeUser();
		userId = usersOption.getValue();
		for (ManageUsers user : users) {
			if (user.getIdUser() == userId) {
				curUser = user;
				setTextForUser();
			}
		}
		getUserHomeScreen();
	}

	private void setTextForUser() {
		userType = UserType.valueOf(curUser.getUserType());
		userTypeLable.setText(curUser.getUserType().toString());
		usernameLable.setText(curUser.getFirstName() + " " + curUser.getLastName());
	}

	void getUserHomeScreen() {// get home icons
		if (usersOption.getValue() == null && curUser == null) {
			errorLable.setText("ERROR! PLEASE SELECT A USER FIRST!");
			showError();
		} else {
			if (AddScreensController.getUser() != null)
				userScreens = AddScreensController.setScreens();
			else {
				userScreens = getScreensFromDB();
				setInitUserScreen();
			}
			setScreen(userScreens);
		}
	}

	private void showError() {
		errorLable.setVisible(true);
		PauseTransition visiblePause = new PauseTransition(Duration.seconds(5));
		visiblePause.setOnFinished(event -> errorLable.setVisible(false));
		visiblePause.play();
	}

	@SuppressWarnings("unchecked")
	private ArrayList<Screens> getScreensFromDB() {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.GET_USER_SCREENS);
		message.put("id", userId);
		message.put("userType", userType);
		return (ArrayList<Screens>) ClientFormController.client.accept(message);
	}

	public void setDefaultScreens() {
		userScreens = ManageClients.getUserScreens(userType);
		setScreen(userScreens);
	}

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

	public void enableSave() {
		if (!userScreens.equals(initUserScreens) && !userScreens.isEmpty()) {
			System.out.println(userScreens);
			System.out.println(initUserScreens);
			saveScreenButton.setDisable(false);
			saveScreenButton.setStyle("-fx-background-radius: 20; -fx-background-color: AAC6BC;");
			errorLable.setText("Please Save Before Switching ID");
			showError();
		} else
			disableSave();
		if (userScreens.isEmpty()) {
			errorLable.setText("User Must Have At Least One Screen");
			showError();
		}
	}

	@SuppressWarnings("unlikely-arg-type")
	public void enableDefault() {
		if (userScreens.containsAll(ManageClients.getUserScreens(UserType.valueOf(curUser.getUserType())))&&(ManageClients.getUserScreens(UserType.valueOf(curUser.getUserType())).containsAll(userScreens))) {
			defaultScreenButton.setStyle("-fx-background-radius: 20; -fx-background-color: E4C2C2;");
			defaultScreenButton.setDisable(true);
		} else {
			defaultScreenButton.setStyle("-fx-background-radius: 20; -fx-background-color: AAC6BC;");
			defaultScreenButton.setDisable(false);
		}
	}

	private void enableButtons() {
		enableSave();
		enableDefault();
		enableAddButton();
	}

	private void enableAddButton() {
		addScreenButton.setStyle("-fx-background-radius: 20; -fx-background-color: AAC6BC;");
		addScreenButton.setDisable(false);
	}

	public void disableSave() {
		saveScreenButton.setDisable(true);
		saveScreenButton.setStyle("-fx-background-radius: 20; -fx-background-color: E4C2C2;");
	}

	public static void removeScreen(Screens screen) {
		userScreens.remove(screen);
		instace.setScreen(userScreens);
	}
	public static void addScreen(Screens screen) {
		userScreens.add(screen);
		instace.setScreen(userScreens);
	}

	public static ManageUsersPermissionController connect() {
		return instace;
	}

	public void addScreens() {
		ManageScreens.changeScreenTo(Screens.ADD_SCREENS);
	}

	public UserType getUserType() {
		return userType;
	}

	public static ArrayList<Screens> getUserScreens() {
		return userScreens;
	}

	public int UserId() {
		return userId;
	}

	public ManageUsers User() {
		return curUser;
	}
}
