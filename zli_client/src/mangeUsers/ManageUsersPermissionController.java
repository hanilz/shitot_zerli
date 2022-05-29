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
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import util.Commands;
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

	private static ObservableList<ManageUsers> users = FXCollections.observableArrayList();
	@FXML
	private Label errorLable;
	private int userId;
	private UserType userType;
	private static ArrayList<Screens> userScreens;
	private static ManageUsersPermissionController instace;

	@FXML
	void changeToHome(Event event) {
		ManageScreens.home();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		instace=this;
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.FETCH_ALL_USERS_DETAILS);
		Object response = ClientFormController.client.accept(message);
		users = (ObservableList<ManageUsers>) response;
		System.out.println("got users");
		ObservableList<Integer> ids = FXCollections.observableArrayList();
		for (ManageUsers user : users) {
			ids.add(user.getIdUser());
		}
		usersOption.setItems(ids);// set the information in the table
	}

	@FXML
	void getUser(ActionEvent event) {
		userId = usersOption.getValue();
		for (ManageUsers user : users) {
			if (user.getIdUser() == userId) {
				userType = UserType.valueOf(user.getUserType());
				userTypeLable.setText(userType.toString());
				usernameLable.setText(user.getFirstName() + " " + user.getLastName());
			}
		}
		getUserHomeScreen();
	}

	@SuppressWarnings("unchecked")
	void getUserHomeScreen() {
		if (usersOption.getValue() == null) {
			errorLable.setVisible(true);
			PauseTransition visiblePause = new PauseTransition(Duration.seconds(2));
			visiblePause.setOnFinished(event -> errorLable.setVisible(false));
			visiblePause.play();
		} else {
			HashMap<String, Object> message = new HashMap<>();
			message.put("command", Commands.GET_USER_SCREENS);
			message.put("id", userId);
			message.put("userType", userType);
			Object response = ClientFormController.client.accept(message);
			userScreens = (ArrayList<Screens>) response;
			setScreen(userScreens);
		}
	}

	private void setScreen(ArrayList<Screens> userScreens2) {
		userHomeScreens.getChildren().clear();
		if (userScreens2 != null)
			for (Screens screen : userScreens2) {
			FXMLLoader fxmlLoader=new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("ManageHomeVBox.fxml"));
			VBox vbox = null;
			try {
				vbox = fxmlLoader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
			ManageHomeVBoxController MVBController=fxmlLoader.getController();
			MVBController.setScreen(screen);
			userHomeScreens.getChildren().add(vbox);
			}
	}
	public static void removeScreen(Screens screen)
	{
		userScreens.remove(screen);
		instace.setScreen(userScreens);
	}
}
