package deliveryCoordination;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import util.ManageScreens;

public class OrderRowController implements Initializable{

    @FXML
    private Button confirmButton;
    

    @FXML
    private Label orderLabel;

    @FXML
    void confirmOrder(ActionEvent event) {
    	//TODO update order and delivery stauts to delivered in db
    	ManageScreens.displayAlert("Order Received", "Order has been marked as received");
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		orderLabel.setText(String.format("Order Number: %d\tReceiver Name: %s\t\tAddress: %s",0,"jhon doe","Sesemy Street 234 23423 4"));
		
	}
    

}
