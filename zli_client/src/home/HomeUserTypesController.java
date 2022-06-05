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
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import notifications.NotificationController;
import util.Commands;
import util.ManageClients;
import util.ManageScreens;
import util.Screens;

/**
 * @author dolev User Home Screen - Allowing to Exit,Logout and Use Given
 *         Options
 *
 */
public class HomeUserTypesController implements Initializable {
	private ArrayList<Notification> newNotifications = new ArrayList<>();
	private ArrayList<Notification> readNotifications = new ArrayList<>();
	private Timeline notificationThread;
	@FXML
	private ImageView notificationIcon;

	@FXML
	private Label notificationLabel;


	@FXML
	private TilePane gridOptions;

	@FXML
	private HBox hbox;

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
		userNameLabel.setId("userName");
		
		getNotifications();
		// update notifications every 10 seconds
		notificationThread = new Timeline(new KeyFrame(Duration.seconds(10.0), e -> {
			getNotifications();
		}));
		notificationThread.setCycleCount(Timeline.INDEFINITE);
		notificationThread.play();
	}

	/**
	 * Clicking on Exit Disconnect from Server and Close The Screen
	 */
	public void exitHomeScreen(MouseEvent event) {
		// release the client from the OCSF server + disconnect from the DB
		ManageClients.exitClient();
		// exit window
		System.exit(0);
	}

	/**
	 * Clicking On Logout Empty The User Instance and return to Guest Home
	 */
	public void logoutFromUser(MouseEvent event) {
		User.getUserInstance().logout();
		ManageScreens.changeScreenTo(Screens.GUEST_HOME);
	}

	/**
	 * Setting User Notifications
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
	 * Setting Buttons for User Screen From DB
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
	 * Clicking on Bell Shows Notifications
	 */
	@FXML
	void showNotifications(MouseEvent event) {
		try {
			NotificationController.newNotifications = newNotifications;
			NotificationController.readNotifications = readNotifications;
			ManageScreens.openPopupFXML(NotificationController.class.getResource("NotificationMainScreen.fxml"),
					"Notification Center");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}