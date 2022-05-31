package order;

import java.net.URL;
import java.util.ResourceBundle;

import entities.SingletonOrder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class DeliveryDetailsPickupVBoxController implements Initializable{

    @FXML
    private Label branchLabel;

    @FXML
    private Label pickupTimeLabel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		branchLabel.setText(SingletonOrder.getInstance().getPickupBranch().toString());
		pickupTimeLabel.setText(SingletonOrder.getInstance().getPickup().getDeliveryDate());
	}
}
