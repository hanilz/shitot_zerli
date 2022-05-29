package mangeUsers;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.ClientFormController;
import entities.ManageUsers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import util.Commands;
import util.ManageScreens;

public class ManageUsersPermissionController implements Initializable {

	@FXML
	private Button selectUser;

	@FXML
	private TilePane userScreens;

	@FXML
	private ComboBox<Integer> usersOption;
	@FXML
	private Label userTypeLable;

	@FXML
	private Label usernameLable;

	private static ObservableList<ManageUsers> users = FXCollections.observableArrayList();

	@FXML
	void changeToHome(Event event) {
		ManageScreens.home();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
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
		int userId = usersOption.getValue();
		for (ManageUsers user : users) {
			if (user.getIdUser() == userId) {
				userTypeLable.setText(user.getUserType());
				usernameLable.setText(user.getFirstName() + " " + user.getLastName());
			}
		}

	}
}
