package notifications;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entities.Notification;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.ManageScreens;

public class NotificationController implements Initializable {
	public static ArrayList<Notification> newNotifications;
	public static ArrayList<Notification> readNotifications;

	@FXML
	private Button closeButton;

	@FXML
	private VBox newNotificationsVBox;

	@FXML
	private VBox readNotificationsVBox;

	private static NotificationController nc;

	@FXML
	void closeNotificationCenter(ActionEvent event) {
		ManageScreens.home();
		((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nc = this;
		try {
		for (Notification notif : newNotifications) {
			HBox notificationRow;
				notificationRow = loadRow(notif);
			newNotificationsVBox.getChildren().add(notificationRow);
		}
		for (Notification notif : readNotifications) {
			HBox notificationRow = loadRow(notif);
			((Button) notificationRow.getChildren().get(1)).setText("Delete");
			readNotificationsVBox.getChildren().add(notificationRow);
		}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private HBox loadRow(Notification notif) throws IOException {
		FXMLLoader loader = new FXMLLoader(NotificationController.class.getResource("NotifcationRow.fxml"));
		HBox notificationRow = (HBox) loader.load();
		((NotificationRowController) loader.getController()).setNotification(notif);
		return notificationRow;
	}

	public static void moveToRead(HBox notificationRow, Notification notif) {
		nc.newNotificationsVBox.getChildren().remove(notificationRow);
		nc.readNotificationsVBox.getChildren().add(notificationRow);
		newNotifications.get(newNotifications.indexOf(notif)).setRead(true);

	}

	public static void removeNotification(HBox notificationRow, Notification notif) {
		nc.readNotificationsVBox.getChildren().remove(notificationRow);
		newNotifications.remove(notif);
	}
}
