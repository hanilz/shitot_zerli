package ordersView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class orderRowController{
	private CustomerOrderView customerOrder;
	private ViewOrdersController viewOrdersController;
	@FXML
	private Button cancelOrderButton;

	@FXML
	private Label deliveryStatusLabel;

	@FXML
	private Label orderNumberLabel;

	@FXML
	private Label orderStatusLabel;

	@FXML
	private Button viewDeatilsButton;

	@FXML
	void cancelOrder(ActionEvent event) {

	}

	@FXML
	void viewOrderDetails(ActionEvent event) {
		if(viewOrdersController!=null)
			viewOrdersController.initializeOverview(customerOrder);
		else
			System.out.println("viewOrdersController is null");
	}

	public void setOrder(CustomerOrderView customerOrder) {
		this.customerOrder = customerOrder;
	}
	
	public void setMainController(ViewOrdersController viewOrdersController) {
		this.viewOrdersController = viewOrdersController;
	}

	public void initializer() {
		deliveryStatusLabel.setText("Delivery Status: "+customerOrder.getDeliveryStatus());
		orderNumberLabel.setText("Order Number: " + customerOrder.getOrderID());
		orderStatusLabel.setText("Order Status: "+customerOrder.getOrderStatus());
		if(customerOrder.getOrderStatus().equals("Waiting for Cancelation"))
			cancelOrderButton.setDisable(true);
	}

}
