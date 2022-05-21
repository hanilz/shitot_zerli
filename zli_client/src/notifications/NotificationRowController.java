package notifications;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class NotificationRowController {
    @FXML
    private Button markReadButton;

    @FXML
    private Label notificationText;

    @FXML
    void markRead(ActionEvent event) {
    	
    }
    
    public void setText(String notification) {
    	notificationText.setText(notification);
    }

}