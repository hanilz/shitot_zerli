package gui;

import java.net.URL;
import java.util.ResourceBundle;

import entities.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * ViewOrdersController will handle all the events from the gui
 *
 */
public class ViewOrdersController implements Initializable {
	private static ObservableList<Order> orders = FXCollections.observableArrayList();

	/**
	 * contains all the order table from the database
	 */
	@FXML
	private TableView<Order> OrderTable;

	/**
	 * for updating the color from the existing orders from the database
	 */
	@FXML
	private TextField colorTextField;

	/**
	 * for updating the date from the existing orders from the database
	 */
	@FXML
	private TextField dateTextField;

	// TODO: why do we need orderNumber field? we need to find the primary by
	// ourself!
	@FXML
	private TextField orderNumberTextField;

	/**
	 * updateButton will update the order table
	 */
	@FXML
	private Button updateButton;

	@FXML
	private TableColumn<Order, Integer> orderNumberCol;

	@FXML
	private TableColumn<Order, Double> priceCol;

	@FXML
	private TableColumn<Order, String> greetingCardCol;

	@FXML
	private TableColumn<Order, String> colorCol;

	@FXML
	private TableColumn<Order, String> dOrderCol;

	@FXML
	private TableColumn<Order, String> shopCol;

	@FXML
	private TableColumn<Order, String> dateCol;

	@FXML
	private TableColumn<Order, String> orderDateCol;

	@FXML
	void updateDateColor(MouseEvent event) {
		String orderNumber = orderNumberTextField.getText(); // index 2 after split
		String date = dateTextField.getText(); // index 3+4 after split
		String color = colorTextField.getText(); // index 5 after split
		StringBuffer buff = new StringBuffer();
		buff.append("update orders " + orderNumber + " " + date + " " + color);
		Object response = ClientFormController.client.accept(buff.toString());
		if (response.equals("DB updated")) {
			fetchAllOrders();
			OrderTable.setItems(orders);
		}
		else
			System.out.println("Update failed! check the input");
	}

	/**
	 * fetchAllOrder will send the message to the server for fetching all the order
	 * table from the database
	 */
	@SuppressWarnings("unchecked")
	private void fetchAllOrders() {
		orders = (ObservableList<Order>) ClientFormController.client.accept("fetch orders");
	}

	/**
	 * by using the initialize function, it will initial the table and the context
	 * of the table
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		orderNumberCol.setCellValueFactory(new PropertyValueFactory<>("orderNumber"));
		priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
		greetingCardCol.setCellValueFactory(new PropertyValueFactory<>("greetingCard"));
		colorCol.setCellValueFactory(new PropertyValueFactory<>("color"));
		dOrderCol.setCellValueFactory(new PropertyValueFactory<>("DOrder"));
		shopCol.setCellValueFactory(new PropertyValueFactory<>("shop"));
		dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
		orderDateCol.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
		fetchAllOrders();
		OrderTable.setItems(orders);
	}
}