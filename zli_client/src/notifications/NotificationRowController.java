package notifications;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class NotificationRowController {
	private Notification notifiction; 
	
    @FXML
    private Button markReadButton;

    @FXML
    private Label notificationText;

    @FXML
    private HBox notificationRow;
    
    @FXML
    void notificationAction(ActionEvent event) {
    	if(markReadButton.getText().equals("Mark As Read")) {
    		NotificationManager.markAsRead(notifiction);
    		NotificationController.moveToRead(notificationRow,notifiction);  
    		markReadButton.setText("Delete");
    	}
    	else {
    		NotificationManager.deleteNotification(notifiction);
    		NotificationController.removeNotification(notificationRow,notifiction);  
    	}
    }
    
    private void setText(String notification) {
    	notificationText.setText(notification);
    }
    
    public void setNotification(Notification notifiction) {
    	this.notifiction=notifiction;
    	setText(notifiction.getTitle());
    }
    
    public Notification getNotification() {
    	return notifiction;
    }

}