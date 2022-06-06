package ordersView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.ClientFormController;
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

    @FXML
    void returnHome(MouseEvent event) {
    	ManageScreens.home();
    }

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
	
	private HBox loadRow(CustomerOrderView cov) throws IOException {
		FXMLLoader loader = new FXMLLoader(orderRowController.class.getResource("OrderViewRow.fxml"));
		HBox orderRow = (HBox) loader.load();
		((orderRowController) loader.getController()).setOrder(cov);
		((orderRowController) loader.getController()).setMainController(voc);;
		((orderRowController) loader.getController()).initializer();
		return orderRow;
	}
	
	private HBox loadOrderRow(ProductsBase pb,int qty) throws IOException {
		FXMLLoader loader = new FXMLLoader(OrderProductRowController.class.getResource("OrderProductRow.fxml"));
		HBox orderProductRow = (HBox) loader.load();
		((OrderProductRowController) loader.getController()).setProductBase(pb,qty);
		((OrderProductRowController) loader.getController()).initRow();
		return orderProductRow;
	}
	
	public void updateOrderStatus(CustomerOrderView cov) {
		customerOrders.get(customerOrders.indexOf(cov)).setDeliveryStatus("Waiting for Cancellation");
		customerOrders.get(customerOrders.indexOf(cov)).setOrderStatus("Waiting for Cancellation");
		initializeOverview(cov);
	}
}
