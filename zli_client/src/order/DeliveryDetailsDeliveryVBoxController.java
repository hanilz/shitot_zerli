package order;

import java.net.URL;
import java.util.ResourceBundle;

import entities.Delivery;
import entities.SingletonOrder;
import inputs.InputChecker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class DeliveryDetailsDeliveryVBoxController implements Initializable {

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
	
	@FXML
	private Label deliveryFeeAmount;

	double deliveryFee;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Delivery delivery = SingletonOrder.getInstance().getDelivery();

		addressLabel.setText(delivery.getAddress());
		branchLabel.setText(SingletonOrder.getInstance().getBranch().toString());
		if (SingletonOrder.getInstance().getIsExpress()) {
			deliveryTimeLabel.setText("As Soon As It's Ready!");
			deliveryFee = 30;
		} else {
			deliveryTimeLabel.setText(delivery.getDeliveryDate());
			deliveryFee = 15;
		}
		recieverNameLabel.setText(delivery.getReceiverName());
		recieverPhoneLabel.setText(delivery.getPhoneNumber());
		deliveryTypeLabel.setText(delivery.getType());
		deliveryFeeAmount.setText(InputChecker.price(deliveryFee));
	}
}
