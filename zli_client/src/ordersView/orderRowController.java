package ordersView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import client.ClientFormController;
import entities.CustomerOrderView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import util.Commands;
import util.ManageScreens;

/**
 * orderRowController used to control an orderRow FXML file, it needs to be
 * initialized with a CustomerOrderView and ViewOrdersController to run when
 * initialized
 * 
 * @author Eitan
 *
 */
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

	/**used to cancel an order by the customer
	 * @param event
	 */
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

	
	/**used to load an order details in the orderOverview on the side of the order view screen
	 * @param event
	 */
	@FXML
	void viewOrderDetails(ActionEvent event) {
		if (viewOrdersController != null)
			viewOrdersController.initializeOverview(customerOrder);
		else
			System.out.println("viewOrdersController is null");
	}

	/**used to set the CustomerOrderView prior to initializing the order row
	 * @param customerOrder
	 */
	public void setOrder(CustomerOrderView customerOrder) {
		this.customerOrder = customerOrder;
	}

	/**used to set the ViewOrdersController prior to initializing the order row
	 * @param customerOrder
	 */
	public void setMainController(ViewOrdersController viewOrdersController) {
		this.viewOrdersController = viewOrdersController;
	}

	/**
	 * used to initialize the screen before to display an order
	 */
	public void initializer() {
		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date = formatter.parse(customerOrder.getDeliveryDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (date.compareTo(new Date()) < 0 && !customerOrder.getDeliveryStatus().equals("Delivered")) {
			deliveryStatusLabel.setText(customerOrder.getDeliveryStatus() + " | Late");
		} else
			deliveryStatusLabel.setText(customerOrder.getDeliveryStatus());

		orderNumberLabel.setText("Order Number: " + customerOrder.getOrderID());
		orderStatusLabel.setText(customerOrder.getOrderStatus());
		if (customerOrder.getOrderStatus().equals("Waiting for Cancellation")
				|| customerOrder.getOrderStatus().equals("Canceled")
				|| customerOrder.getDeliveryStatus().equals("Delivered")
				|| customerOrder.getDeliveryStatus().equals("Delivered-Refunded")
				|| customerOrder.getDeliveryType().equals("express delivery") || date.compareTo(new Date()) < 0)
			cancelOrderButton.setDisable(true);
	}
}
