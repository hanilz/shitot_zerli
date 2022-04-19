package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewOrdersController implements Initializable {
	@FXML
	private TableView<Order> OrderTable = new TableView<Order>();

	@FXML
	private TextField colorField;

	@FXML
	private TextField dateField;

	@FXML
	private TextField orderNumber;

	@FXML
	private Button updateButton;

	@FXML
	private TableColumn<Order, String> orderNumbers;

	@FXML
	private TableColumn<Order, String> price;

	@FXML
	private TableColumn<Order, String> greetingCard;

	@FXML
	private TableColumn<Order, String> color;

	@FXML
	private TableColumn<Order, String> dOrder;

	@FXML
	private TableColumn<Order, String> shop;
	@FXML
	private TableColumn<Order, String> date;
	@FXML
	private TableColumn<Order, String> orderDate;

	@FXML
	void updateDateColor(ActionEvent event) {
		//initialize(null,null);
		// OrderTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}

	ObservableList<Order> orders = FXCollections.observableArrayList();

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		orderNumbers.setCellValueFactory(new PropertyValueFactory<Order, String>("orderNumbers"));
		price.setCellValueFactory(new PropertyValueFactory<Order, String>("price"));
		greetingCard.setCellValueFactory(new PropertyValueFactory<Order, String>("greetingCard"));
		color.setCellValueFactory(new PropertyValueFactory<Order, String>("color"));
		dOrder.setCellValueFactory(new PropertyValueFactory<Order, String>("dOrder"));
		shop.setCellValueFactory(new PropertyValueFactory<Order, String>("shop"));
		date.setCellValueFactory(new PropertyValueFactory<Order, String>("date"));
		orderDate.setCellValueFactory(new PropertyValueFactory<Order, String>("orderDate"));
		OrderTable.getColumns().setAll(orderNumber, price, greetingCard, color, dOrder, shop, date, orderDate);
		orders.add(new Order("1", "22", "null", "blue", "null", "temp", "2022-05-24 12:45:30", "33"));
		orders.add(new Order("2", "33", "sdfsad", "blue", "null", "temp", "2022-05-24 12:45:30", "4554"));
		OrderTable.setItems(orders);
	}
}