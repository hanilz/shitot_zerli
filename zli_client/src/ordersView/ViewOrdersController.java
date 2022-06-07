package ordersView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.ClientFormController;
import entities.CustomerOrderView;
import entities.ProductsBase;
import entities.User;
import inputs.InputChecker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import util.Commands;
import util.ManageScreens;

/**ViewOrdersController as a controller class for the ViewOrdersScreen FXML to allow the customer to view his orders and cancel them
 * the customer is presented with all of his orders
 * @author Eitan
 *
 */
public class ViewOrdersController implements Initializable{
	private static ViewOrdersController voc;
	private static HashMap<String, Object> message = new HashMap<>();
	private ArrayList<CustomerOrderView> customerOrders;
	@FXML
    private Label deliveryDateLabel;

    @FXML
    private Label deliveryStatusLabel;

    @FXML
    private Label deliveryTypeLabel;

    @FXML
    private ImageView homeImage;

    @FXML
    private VBox orderContentsVbox;

    @FXML
    private VBox orderList;

    @FXML
    private ScrollPane orderListsScrollPane;

    @FXML
    private Label orderNumberLabel;

    @FXML
    private ScrollPane orderOverViewScrollPane;

    @FXML
    private Label orderPriceLabel;

    @FXML
    private Label orderStatusLabel;

    @FXML
    private Label placedDateLabel;

    /**used to return to the home screen for the customer
     * @param event
     */
    @FXML
    void returnHome(MouseEvent event) {
    	ManageScreens.home();
    }

	/**
	 *used to get the orders for the clients and initialize the order rows in the screen
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		voc=this;
		message.clear();
		message.put("command", Commands.FETCH_ORDERS_FOR_CLIENT);
		message.put("idUser", User.getUserInstance().getIdUser());
		Object response = ClientFormController.client.accept(message);
		customerOrders = (ArrayList<CustomerOrderView>)response;
		for(CustomerOrderView cov: customerOrders) {
			HBox orderRow;
			try {
				orderRow = loadRow(cov);
				orderList.getChildren().add(orderRow);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(!customerOrders.isEmpty())
			initializeOverview(customerOrders.get(0));
	}
	
	/**used to initialize the order overview portion in the screen
	 * @param order
	 */
	public void initializeOverview(CustomerOrderView order) {
		orderNumberLabel.setText(""+order.getOrderID());
		orderStatusLabel.setText(order.getOrderStatus());
		deliveryStatusLabel.setText(order.getDeliveryStatus());
		placedDateLabel.setText(order.getOrderDate());
		deliveryDateLabel.setText(order.getDeliveryDate());
		deliveryTypeLabel.setText(order.getDeliveryType());
		orderPriceLabel.setText(InputChecker.price(order.getOrderPrice()));
		//TODO initialize order products and items
		message.clear();
		message.put("command", Commands.FETCH_ORDER_CONTENT);
		message.put("orderID", order.getOrderID());
		Object response = ClientFormController.client.accept(message);
		@SuppressWarnings("unchecked")
		HashMap<ProductsBase,Integer> orderContent = (HashMap<ProductsBase,Integer>)response;
		orderContentsVbox.getChildren().clear();
		for(ProductsBase pb : orderContent.keySet()) {
			try {
				HBox orderRowBox = loadOrderRow(pb,orderContent.get(pb));
				orderContentsVbox.getChildren().add(orderRowBox);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**used to load an order OrderViewRow.fxml to display order information
	 * @param cov
	 * @return
	 * @throws IOException
	 */
	private HBox loadRow(CustomerOrderView cov) throws IOException {
		FXMLLoader loader = new FXMLLoader(orderRowController.class.getResource("OrderViewRow.fxml"));
		HBox orderRow = (HBox) loader.load();
		((orderRowController) loader.getController()).setOrder(cov);
		((orderRowController) loader.getController()).setMainController(voc);;
		((orderRowController) loader.getController()).initializer();
		return orderRow;
	}
	
	/**used to load an order product row for the customer to be able to see the contents of his order
	 * @param pb
	 * @param qty
	 * @return
	 * @throws IOException
	 */
	private HBox loadOrderRow(ProductsBase pb,int qty) throws IOException {
		FXMLLoader loader = new FXMLLoader(OrderProductRowController.class.getResource("OrderProductRow.fxml"));
		HBox orderProductRow = (HBox) loader.load();
		((OrderProductRowController) loader.getController()).setProductBase(pb,qty);
		((OrderProductRowController) loader.getController()).initRow();
		return orderProductRow;
	}
	
	/**when a customer order is being canceled this method is used to update the delivery and order status
	 * @param cov
	 */
	public void updateOrderStatus(CustomerOrderView cov) {
		customerOrders.get(customerOrders.indexOf(cov)).setDeliveryStatus("Waiting for Cancellation");
		customerOrders.get(customerOrders.indexOf(cov)).setOrderStatus("Waiting for Cancellation");
		initializeOverview(cov);
	}
}
