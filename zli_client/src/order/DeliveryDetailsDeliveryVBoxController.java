package order;

import java.net.URL;
import java.util.ResourceBundle;

import entities.Delivery;
import entities.SingletonOrder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class DeliveryDetailsDeliveryVBoxController implements Initializable{

    @FXML
    private Label addressLabel;

    @FXML
    private Label branchLabel;

    @FXML
    private Label deliveryTimeLabel;

    @FXML
    private Label deliveryTypeLabel;

    @FXML
    private Label recieverNameLabel;

    @FXML
    private Label recieverPhoneLabel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Delivery delivery = SingletonOrder.getInstance().getDelivery();
		
		addressLabel.setText(delivery.getAddress());
		branchLabel.setText(SingletonOrder.getInstance().getBranch().toString());
		if(SingletonOrder.getInstance().getIsExpress())
			deliveryTimeLabel.setText("As Soon As It's Ready!");
		else
			deliveryTimeLabel.setText(delivery.getDeliveryDate());
		recieverNameLabel.setText(delivery.getReceiverName());
		recieverPhoneLabel.setText(delivery.getPhoneNumber());
		deliveryTypeLabel.setText(delivery.getType());
	}
}
