package ordersView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import client.ClientFormController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import util.Commands;
import util.ManageScreens;

public class orderRowController {
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
		boolean cancel = ManageScreens.getYesNoDecisionAlert("Order Cancellation",
				"Press yes if you want to cancel the order",
				"Canceling order requires confirmation form the manager.\nAre you sure you want to cancel the order?");
		if (!cancel)
			return;
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.CANCEL_ORDER_REQUEST);
		message.put("order id", customerOrder.getOrderID());
		Object response = ClientFormController.client.accept(message);
		if (!(boolean) response) {
			ManageScreens.displayAlert("Cancellation Failed", "Failed to cancel order please try again!");
			return;
		}
		orderStatusLabel.setText("Order Status: Waiting for Cancellation");
		cancelOrderButton.setDisable(true);
		viewOrdersController.updateOrderStatus(customerOrder);
		ManageScreens.displayAlert("Order Cancell Requested",
				"We are sorry you didnt like your experience with us.\nA cencallation request has been sent to our store manager and he will attend it add soon as possible!");

	}

	@FXML
	void viewOrderDetails(ActionEvent event) {
		if (viewOrdersController != null)
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
		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date = formatter.parse(customerOrder.getDeliveryDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(date.compareTo(new Date())<0 && !customerOrder.getDeliveryStatus().equals("Delivered")) {
			deliveryStatusLabel.setText("Delivery Status: " + customerOrder.getDeliveryStatus() + " | Late");
		}
		else
			deliveryStatusLabel.setText("Delivery Status: " + customerOrder.getDeliveryStatus());

		orderNumberLabel.setText("Order Number: " + customerOrder.getOrderID());
		orderStatusLabel.setText("Order Status: " + customerOrder.getOrderStatus());
		if (customerOrder.getOrderStatus().equals("Waiting for Cancellation")
				|| customerOrder.getOrderStatus().equals("Canceled")
				|| customerOrder.getDeliveryStatus().equals("Delivered")
				|| customerOrder.getDeliveryStatus().equals("Delivered-Refunded")
				|| customerOrder.getDeliveryType().equals("express delivery")
				|| date.compareTo(new Date()) < 0)
			cancelOrderButton.setDisable(true);
	}
}
