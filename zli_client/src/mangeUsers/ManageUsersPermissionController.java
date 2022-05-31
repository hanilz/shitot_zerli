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

public class ManageUsersPermissionController implements Initializable {

	@FXML
	private TilePane userHomeScreens;

	@FXML
	private ComboBox<String> usersOption;
	@FXML
	private Label userTypeLable;

	@FXML
	private Label usernameLable;
	@FXML
	private HBox addScreenButton;

	@FXML
	private HBox defaultScreenButton;

	@FXML
	private HBox saveScreenButton;

	private static ObservableList<ManageUsers> users = FXCollections.observableArrayList();
	@FXML
	private Label errorLable;
	private int userId;
	private UserType userType;
	private static ArrayList<Screens> userScreens, initUserScreens = new ArrayList<>();
	private static ManageUsersPermissionController instace;
	private ManageUsers curUser;
	private PauseTransition visiblePause;

	@FXML
	void changeToHome(Event event) {
		AddScreensController.removeUser();
		ManageScreens.home();
	}

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
	}

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
		userId = Integer.parseInt(usersOption.getValue().split(" ")[0]);
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
			showMessage("ERROR! PLEASE SELECT A USER FIRST!", "red");
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
			saveScreenButton.setDisable(false);
			((Labeled) saveScreenButton.getChildren().get(0)).setUnderline(true);
			saveScreenButton.setStyle("-fx-background-radius: 20; -fx-background-color: AAC6BD;");// AAC6BC
			showMessage("Please Save Before Switching ID", "red");
		} else
			disableSave();
		if (userScreens.isEmpty()) {
			showMessage("User Must Have At Least One Screen", "red");
		}
	}

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
		((Labeled) saveScreenButton.getChildren().get(0)).setUnderline(false);
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
