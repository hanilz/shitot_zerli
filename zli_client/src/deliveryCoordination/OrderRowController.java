package deliveryCoordination;

import java.util.HashMap;

import client.ClientFormController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import notifications.NotificationManager;
import util.Commands;
import util.ManageScreens;
import util.NotificationType;

public class OrderRowController {
	DeliveryCoordinatorView deliveryCoordinatorView;
	DeliveryCoordinatorController deliveryCoordinatorController;
	
	@FXML
    private Label addressLabel;

    @FXML
    private Button confirmButton;

    @FXML
    private Label dateLabel;

    @FXML
    private Label deliveryTypeLabel;

    @FXML
    private Label idNumberLabel;

    @FXML
    private HBox mainHBox;

    @FXML
    private Label nameLabel;

    @FXML
    private Label numberLabel;


	@FXML
	void confirmOrder(ActionEvent event) {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.CONFIRM_DELIVERY);
		message.put("idOrder",deliveryCoordinatorView.getIdOrder() );
		Object response = ClientFormController.client.accept(message);
		boolean confirmed = (boolean)response;
		if(!confirmed) {
			ManageScreens.displayAlert("Failed to Confirm Delivery", "Failed to mark order as delivered, Please try again!");
			return;
		}
		if(deliveryCoordinatorView.getRemainingTime()<0 && !deliveryCoordinatorView.getDeliverytype().equals("express delivery") ) {
			message.clear();
			message.put("command", Commands.REFUND_USER_FOR_LATE_DELIVERY);
			message.put("idOrder", deliveryCoordinatorView.getIdOrder());
			message.put("idUser", deliveryCoordinatorView.getIdUser());
			response = ClientFormController.client.accept(message);
			NotificationManager.sendNotification(deliveryCoordinatorView.getIdUser(),NotificationType.DELIVERY_LATE_REFUND , deliveryCoordinatorView.getIdOrder());
		}else {
			NotificationManager.sendNotification(deliveryCoordinatorView.getIdUser(),NotificationType.DELIVERY_CONFIRMATION , deliveryCoordinatorView.getIdOrder());
		}
		deliveryCoordinatorController.removeRow(deliveryCoordinatorView,mainHBox);
		ManageScreens.displayAlert("Order Delivered", "Order has been marked as Delivered");			
	}

	public void setOrder(DeliveryCoordinatorView deliveryCoordinatorView) {
		this.deliveryCoordinatorView = deliveryCoordinatorView;
		addressLabel.setText(deliveryCoordinatorView.getAddress());
		dateLabel.setText(deliveryCoordinatorView.getDeliveryDate());
		nameLabel.setText(deliveryCoordinatorView.getFirstName() + " " + deliveryCoordinatorView.getLastName());
		numberLabel.setText(deliveryCoordinatorView.getIdOrder() + "");
		idNumberLabel.setText(deliveryCoordinatorView.getPersonalID());
		deliveryTypeLabel.setText(deliveryCoordinatorView.getDeliverytype());
	}

	public void setDeliveryController(DeliveryCoordinatorController deliveryCoordinatorController) {
		this.deliveryCoordinatorController = deliveryCoordinatorController;
	}

}
