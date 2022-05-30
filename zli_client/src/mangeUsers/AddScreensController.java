package mangeUsers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import entities.ManageUsers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import util.ManageScreens;
import util.Screens;
import util.UserType;

public class AddScreensController implements Initializable {

	@FXML
	private Label errorLable;

	@FXML
	private TilePane userHomeScreens;

	@FXML
	private Label userTypeLable;

	@FXML
	private Label usernameLable;
	// data
	// private int userId=ManageUsersPermissionController.connect().UserId();
	private static ManageUsers user = null;
	private static ArrayList<Screens> userScreens;
	private UserType userType;
	private static AddScreensController addScreensController;
	private static ArrayList<Screens> screens;
	// private static ArrayList<Screens>
	// userScreens=ManageUsersPermissionController.getUserScreens();

	@FXML
	void changeToHome(MouseEvent event) {
		user = null;
		ManageScreens.home();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addScreensController = this;
		userType = ManageUsersPermissionController.connect().getUserType();
		user = ManageUsersPermissionController.connect().User();
		userTypeLable.setText(userType.name());
		userScreens = ManageUsersPermissionController.getUserScreens();
		usernameLable.setText(user.getFirstName() + " " + user.getLastName());
		screens = new ArrayList<>(Arrays.asList(Screens.values()));
		screens.removeAll(userScreens);
		screens.removeAll(Arrays.asList(new Screens[] { Screens.GUEST_HOME, Screens.USER_HOME, Screens.LOGIN }));
		setScreens(screens);
	}

	private void setScreens(ArrayList<Screens> screens) {
		userHomeScreens.getChildren().clear();
		if (screens != null)
			for (Screens screen : screens) {
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(getClass().getResource("addScreensVBox.fxml"));
				VBox vbox = null;
				try {
					vbox = fxmlLoader.load();
				} catch (IOException e) {
					e.printStackTrace();
				}
				AddVBoxController addVBoxController = fxmlLoader.getController();
				if (!ManageScreens.getIconPath(screen).equals("")) {
					addVBoxController.setScreen(screen);
					userHomeScreens.getChildren().add(vbox);
				}
			}
	}

	public static void removeScreens(Screens screen) {
		screens.remove(screen);
		addScreensController.setScreens(screens);
	}

	public void backToManageUser() {
		ManageScreens.changeScreenTo(Screens.USER_PREMISSION);
	}

	public static ArrayList<Screens> setScreens() {
		return userScreens;
	}

	public static ManageUsers getUser() {
		return user;
	}

	public static void removeUser() {
		user = null;
	}

}