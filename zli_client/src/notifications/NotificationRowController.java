package notifications;

import entities.Notification;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**used to control a single row of a notification, controls the NotificationRow FXML file
 * @author Eitan
 *
 */
public class NotificationRowController {
	private Notification notifiction; 
	
    @FXML
    private Button markReadButton;

    @FXML
    private Label notificationText;

    @FXML
    private HBox notificationRow;
    
    /**when the Mark As Read button is pressed the notification moves to the second tab
     * the notification changes it status as read
     * if a notification is already marked as read, a delete button appears which deletes the notification from the DB
     * @param event
     */
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
    
    /**used to set the text of the notification
     * @param notification
     */
    private void setText(String notification) {
    	notificationText.setText(notification);
    }
    
    /**Notification must be set before initializing the notification row
     * @param notifiction
     */
    public void setNotification(Notification notifiction) {
    	this.notifiction=notifiction;
    	setText(notifiction.getTitle());
    }
    
    
    /** returns the notification
     * @return
     */
    public Notification getNotification() {
    	return notifiction;
    }

}