package mangeCustomerOrders;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import buttonStrategy.ApproveOrder;
import buttonStrategy.ColumnAdder;
import client.ClientFormController;
import entities.ManageUsers;
import entities.Order;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import util.Commands;
import util.ManageScreens;

public class ManageCustomerOrdersController implements Initializable{
	private static ObservableList<ManagerOrderView> ordersToApprove = FXCollections.observableArrayList();
	private static ObservableList<ManagerOrderView> ordersToCancel = FXCollections.observableArrayList();
	

    @FXML
    private Button approveCancelButton;

    @FXML
    private Button approveOrderButton;

    @FXML
    private Button cancelOrderButton;
    
    @FXML
    private Button denyCancelButton;
	
    @FXML
    private TableView<ManagerOrderView> cancelOrdersTable;

    @FXML
    private Button closeButton;

    @FXML
    private Label errorLabel;

    @FXML
    private TableColumn<ManagerOrderView, String> firstNameCol;

    @FXML
    private TableColumn<ManagerOrderView, String> firstNameCol1;

    @FXML
    private ImageView homeImage;

    @FXML
    private TableColumn<ManagerOrderView, Integer> idOrderCol;

    @FXML
    private TableColumn<ManagerOrderView, Integer> idOrderCol1;

    @FXML
    private TableColumn<ManagerOrderView, String> lastNameCol;

    @FXML
    private TableColumn<ManagerOrderView, String> lastNameCol1;

    @FXML
    private TableView<ManagerOrderView> newOrderTable;

    @FXML
    private TableColumn<ManagerOrderView, String> orderDateCol;

    @FXML
    private TableColumn<ManagerOrderView, String> orderDateCol1;

    @FXML
    private TableColumn<ManagerOrderView, Double> priceCol;

    @FXML
    private TableColumn<ManagerOrderView, Double> priceCol1;

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.FETCH_ORDERS_MANAGER);
		message.put("manager id", User.getUserInstance().getIdUser());
		Object response = ClientFormController.client.accept(message);
		for(ManagerOrderView mov : (ArrayList<ManagerOrderView>)response) {
			if(mov.getStatus().equals("Waiting for approvel"))
				ordersToApprove.add(mov);
			else
				ordersToCancel.add(mov);
		}
		//table1
		idOrderCol.setCellValueFactory(new PropertyValueFactory<>("idOrder"));
		priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
		firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		orderDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
		newOrderTable.setItems(ordersToApprove);//set the information in the table
		
		
		//table2
		idOrderCol1.setCellValueFactory(new PropertyValueFactory<>("idOrder"));
		priceCol1.setCellValueFactory(new PropertyValueFactory<>("price"));
		firstNameCol1.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		lastNameCol1.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		orderDateCol1.setCellValueFactory(new PropertyValueFactory<>("date"));
		cancelOrdersTable.setItems(ordersToCancel);//set the information in the table	
	}

	@FXML
	void returnHome(ActionEvent event) {
		ManageScreens.home();
	}
	
    @FXML
    void approveCancelRequest(ActionEvent event) {

    }

    @FXML
    void approveOrder(ActionEvent event) {

    }

    @FXML
    void cancelOrder(ActionEvent event) {

    }

    @FXML
    void denyCancelRequest(ActionEvent event) {

    }


}
