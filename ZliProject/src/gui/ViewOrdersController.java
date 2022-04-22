package gui;

import java.io.IOException;
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

public class ViewOrdersController implements Initializable {
	private static ObservableList<Order> orders = FXCollections.observableArrayList();
	
	@FXML
	private TableView<Order> OrderTable;

	@FXML
    private TextField colorTextField;
	
	@FXML
    private TextField dateTextField;

	@FXML
    private TextField orderNumberTextField;

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
	
    }
	
	@SuppressWarnings("unchecked")
	private void fetchAllOrders() {
		orders = (ObservableList<Order>)ClientFormController.client.accept("fetch orders");
	}

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