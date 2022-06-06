package home;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.ClientFormController;
import entities.Notification;
import entities.User;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.util.Duration;
import notifications.NotificationController;
import util.Commands;
import util.ManageClients;
import util.ManageScreens;
import util.Screens;

/**
 * User Home Screen - Allowing to Exit,Logout and Use Given
 *         Options
 *
 */
public class HomeUserTypesController implements Initializable {
	/**
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 */
	private ArrayList<Notification> newNotifications = new ArrayList<>();
	/**
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 */
	private ArrayList<Notification> readNotifications = new ArrayList<>();
	/**
	 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 */
	private Timeline notificationThread;

	/**
	 * GUI-Show users near the bell how many notifications they have
	 */
	@FXML
	private Label notificationLabel;


	/**
	 * GUI-contains user home buttons
	 */
	@FXML
	private TilePane gridOptions;


	/**
	 * GUI-adding user name to home screen 
	 */
	@FXML
	private Label userNameLabel;

	/**
	 * Setting Needed User Information and Show
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.GET_USER_SCREENS);
		message.put("id", User.getUserInstance().getIdUser());
		message.put("userType", User.getUserInstance().getType());
		User.getUserInstance().setUserScreens((ArrayList<Screens>) ClientFormController.client.accept(message));
		ArrayList<Screens> userScreens = User.getUserInstance().getUserHomeScreens();
		setScreen(userScreens);//
		userNameLabel.setText(User.getUserInstance().getUsername());

		getNotifications();
		// update notifications every 10 seconds
		notificationThread = new Timeline(new KeyFrame(Duration.seconds(10.0), e -> {
			getNotifications();
		}));
		notificationThread.setCycleCount(Timeline.INDEFINITE);
		notificationThread.play();
	}

	/**
	 * Clicking on Exit, Disconnect Client from Server and Close The Screen
	 */
	public void exitHomeScreen(MouseEvent event) {
		ManageClients.exitClient();
		System.exit(0);
	}

	/**
	 * Clicking On Logout, Logout The User and return to Guest Home
	 */
	public void logoutFromUser(MouseEvent event) {
		User.getUserInstance().logout();
		ManageScreens.home();
	}

	/**
	 * Setting User Notifications from DB
	 */
	@SuppressWarnings("unchecked")
	private void getNotifications() {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.FETCH_NOTIFICATIONS);
		message.put("idUser", User.getUserInstance().getIdUser());
		Object response = ClientFormController.client.accept(message);
		newNotifications = (ArrayList<Notification>) response;
		for (Notification notif : newNotifications)
			if (notif.isRead())
				readNotifications.add(notif);
		newNotifications.removeAll(readNotifications);
		notificationLabel.setText("" + newNotifications.size());
	}

	/**
	 * Setting Buttons from DB for User Home Screen
	 */
	private void setScreen(ArrayList<Screens> userScreens) {
		if (userScreens != null) {
			if (userScreens.size() > 4) {
				gridOptions.setPadding(new Insets(0, 0, 0, 0));
			}
			for (Screens screen : userScreens) {
				gridOptions.getChildren().add(new HomeVBox(screen));
			}
		}
	}

	/**
	 * Clicking on Bell Shows Notifications Popup
	 */
	@FXML
	void showNotifications(MouseEvent event) {
		try {
			NotificationController.newNotifications = newNotifications;
			NotificationController.readNotifications = readNotifications;
			ManageScreens.changeScreenTo(Screens.NOTIFICATIONS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}