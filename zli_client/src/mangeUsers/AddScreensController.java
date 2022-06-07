package mangeUsers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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

/**
 * @author dolev Adding A Specific user (selected in Manage screens) screens
 *         that they will be showed in Manage screens
 *
 */
public class AddScreensController implements Initializable {

	/**
	 * GUI containing all options of buttons to add to specific user
	 */
	@FXML
	private TilePane userHomeScreens;

	/**s
	 * GUI User type of the specific user
	 */
	@FXML
	private Label userTypeLable;

	/**
	 * GUI User name of the specific user
	 */
	@FXML
	private Label usernameLable;

	/**
	 * GUI Show amount of screens the user have at the current time
	 */
	@FXML
	private Label amoutOfScreensLable;

	/**
	 * GUI Message/Instructions for user in the screen
	 */
	@FXML
	private Label limitLabel;

	/**
	 * A specific user that we add screens to
	 */
	private static ManageUsers user = null;
	/**
	 * The current screens of the specific user
	 */
	private static ArrayList<Screens> userScreens;
	/**
	 * The type of the specific user
	 */
	private UserType userType;
	/**
	 * Save instance to call on static methodes
	 */
	private static AddScreensController addScreensController;
	/**
	 * The screens that can be added to the specific user
	 */
	private static ArrayList<Screens> screens;

	/**
	 * GUI setting up the screen
	 */
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
		amoutOfScreensLable.setText(userScreens.size() + "");
	}

	/**
	 * @param screens Showing the screens that can be added to the specific user
	 */
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

	/**
	 * @param screen Add screen to the specific User and Remove it from the GUI
	 */
	public static void removeScreens(Screens screen) {
		if (userScreens.size() <= 6) {
			screens.remove(screen);
			addScreensController.setScreens(screens);
			addScreensController.amoutOfScreensLable.setText(userScreens.size() + "");
			addScreensController.limitLabel.setText("Can Have Up To 6 Screens");
		} else {
			addScreensController.limitLabel.setText("Cannot Add More Screen, Go Back And Remove Some");
		}
	}

	/**
	 * Message to have only 6 screens per user
	 */
	public static void setLimitText() {
		if (userScreens.size() < 6) {
			addScreensController.limitLabel.setText("Can Have Up To 6 Screens");
		} else {
			addScreensController.limitLabel.setText("Cannot Add More Screen, Go Back And Remove");
		}
	}

	/**
	 * @param event Pressing on home button Change screen to home
	 */
	@FXML
	void changeToHome(MouseEvent event) {
		user = null;
		ManageScreens.home();
	}

	/**
	 * Pressing on Back to Manage User Screens button change the screen to manage user
	 */
	public void backToManageUser() {
		ManageScreens.changeScreenTo(Screens.USER_PREMISSION);
	}

	/**
	 * @return The specific user new screens list
	 */
	public static ArrayList<Screens> setScreens() {
		return userScreens;
	}

	/**
	 * @return The specific user that been worked on
	 */
	public static ManageUsers getUser() {
		return user;
	}

	/**
	 * Not working on any User
	 */
	public static void removeUser() {
		user = null;
	}

}
