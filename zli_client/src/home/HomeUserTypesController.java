package home;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.ClientFormController;
import entities.User;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import notifications.Notification;
import notifications.NotificationController;
import util.Commands;
import util.ManageClients;
import util.ManageScreens;
import util.Screens;

public class HomeUserTypesController implements Initializable {
	private ArrayList<Notification> notifications = new ArrayList<>();
	Timeline notificationThread;
	@FXML
	private ImageView notificationIcon;

	@FXML
	private Label notificationLabel;

	@FXML
	private Button exitButton;

	@FXML
	private GridPane gridOptions;

	@FXML
	private HBox hbox;

	@FXML
	private Button logoutButton;

	@FXML
	private Label userNameLabel;

	private ArrayList<HomeVBox> buttons = new ArrayList<>();

	public void exitHomeScreen(MouseEvent event) {
		// release the client from the ocsf server + disconnect from the db
		ManageClients.exitClient();
		// exit window
		System.exit(0);
	}

	public void logoutFromUser(MouseEvent event) {
		User.getUserInstance().logout();
		ManageScreens.changeScreenTo(Screens.GUEST_HOME);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ArrayList<Screens> userScreens = ManageClients.getUserScreens(User.getUserInstance().getType());
		setScreen(userScreens);//
		userNameLabel.setText(User.getUserInstance().getUsername());
		logoutButton.setCursor(Cursor.HAND);
		exitButton.setCursor(Cursor.HAND);
		
		getNotifications();
		// update notifications every 30 seconds
		notificationThread = new Timeline(new KeyFrame(Duration.seconds(10.0), e -> {
			getNotifications();
		}));
		notificationThread.setCycleCount(Timeline.INDEFINITE);
		notificationThread.play();

//				HashMap<String, Object> message = new HashMap<>();
//				message.put("command", Commands.FETCH_NOTIFICATIONS);
//				message.put("idUser", User.getUserInstance().getIdUser());
//				Object response = ClientFormController.client.accept(message);
//				notifications = (ArrayList<Notification>) response;
//				notificationLabel.setText("" + notifications.size());
	}

	@SuppressWarnings("unchecked")
	private void getNotifications() {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.FETCH_NOTIFICATIONS);
		message.put("idUser", User.getUserInstance().getIdUser());
		Object response = ClientFormController.client.accept(message);
		notifications = (ArrayList<Notification>) response;
		notificationLabel.setText("" + notifications.size());
	}

	private void setScreen(ArrayList<Screens> userScreens) {//
		if (userScreens != null)
			for (int i = 0; i < userScreens.size(); i++) {
				if (i / 3 > 0 && i % 3 == 0)
					gridOptions.addRow(i / 3);
				buttons.add(new HomeVBox(userScreens.get(i)));
				this.gridOptions.add(buttons.get(i), (i + 2) % 3, (i + 2) / 3);
			}
	}

	@FXML
	void showNotifications(MouseEvent event) {
		try {
			NotificationController.notifications = notifications;
			ManageScreens.openPopupFXML(NotificationController.class.getResource("NotificationMainScreen.fxml"),
					"Notification Center");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}