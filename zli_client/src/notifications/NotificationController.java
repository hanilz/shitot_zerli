package notifications;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.ManageScreens;

public class NotificationController implements Initializable{
	public static ArrayList<Notification> notifications;
	
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
    	 ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nc = this;
		try {
			for(Notification notif : notifications) {
				FXMLLoader loader = new FXMLLoader(NotificationController.class.getResource("NotifcationRow.fxml"));
				HBox notificationRow = (HBox)loader.load();
				((NotificationRowController) loader.getController()).setNotification(notif);
				if(notif.isRead()) {
					((Button)notificationRow.getChildren().get(1)).setText("Delete");
					readNotificationsVBox.getChildren().add(notificationRow);
				}
				else
					newNotificationsVBox.getChildren().add(notificationRow);				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}   
	
	public static void moveToRead(HBox notificationRow,Notification notif) {
		nc.newNotificationsVBox.getChildren().remove(notificationRow);
		nc.readNotificationsVBox.getChildren().add(notificationRow);
		notifications.get(notifications.indexOf(notif)).setRead(true);
		
	}

	public static void removeNotification(HBox notificationRow,Notification notif) {
		nc.readNotificationsVBox.getChildren().remove(notificationRow);
		notifications.remove(notif);
	}
}
