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

public class NotificationController implements Initializable{
	public static ArrayList<Notification> notifications;
    @FXML
    private Button closeButton;

    @FXML
    private VBox newNotificationsVBox;

    @FXML
    private VBox readNotificationsVBox;

    @FXML
    void closeNotificationCenter(ActionEvent event) {
    	 ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			for(Notification notif : notifications) {
				FXMLLoader loader = new FXMLLoader(NotificationController.class.getResource("NotifcationRow.fxml"));
				HBox notificationRow = (HBox)loader.load();
				((NotificationRowController) loader.getController()).setText(notif.getTitle());
				newNotificationsVBox.getChildren().add(notificationRow);				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}   
}
