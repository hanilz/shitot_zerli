package mangeCustomerOrders;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.ClientFormController;
import entities.ManagerOrderView;
import entities.User;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import notifications.NotificationManager;
import util.Commands;
import util.ManageScreens;
import util.NotificationType;

public class ManageCustomerOrdersController implements Initializable{
	private static ObservableList<ManagerOrderView> ordersToApprove = FXCollections.observableArrayList();
	private static ObservableList<ManagerOrderView> ordersToCancel = FXCollections.observableArrayList();
	private int messageDelay = 3;

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

	
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ordersToApprove.clear();
		ordersToCancel.clear();
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.FETCH_ORDERS_MANAGER);
		message.put("manager id", User.getUserInstance().getIdUser());
		Object response = ClientFormController.client.accept(message);
		for(ManagerOrderView mov : (ArrayList<ManagerOrderView>)response) {
			if(mov.getStatus().equals("Waiting for Approval"))
				ordersToApprove.add(mov);
			else if(mov.getStatus().equals("Waiting for Cancelation"))
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

	//returns user to home screen
    @FXML
    void returnHome(MouseEvent event) {
    	ManageScreens.home();
    }

    @FXML
    void returnHomeBtn(ActionEvent event) {
    	ManageScreens.home();
    }
	

    //approves a new order - changes status to Approved
    @FXML
    void approveOrder(ActionEvent event) {
    	ManagerOrderView mov = newOrderTable.getSelectionModel().getSelectedItem();
    	if(mov == null) {
    		displayError(messageDelay);
    		return;
    	}
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.APPROVE_ORDER);
		message.put("order id", mov.getIdOrder());
		Object response = ClientFormController.client.accept(message);
		if((boolean)response) {
			displayAlert("Order Approved","Order "+ mov.getIdOrder()+" Approved!");
			ordersToApprove.remove(mov);
			newOrderTable.refresh();
		}
		NotificationManager.sendNotification(mov.getIdUser(), NotificationType.ORDER_ACCEPTED, mov.getIdOrder());
    }


    //cancels new order
    @FXML
    void cancelOrder(ActionEvent event) {
    	ManagerOrderView mov = newOrderTable.getSelectionModel().getSelectedItem();
    	if(mov == null) {
    		displayError(messageDelay);
    		return;
    	}
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.CANCEL_ORDER);
		message.put("order id", mov.getIdOrder());
		Object response = ClientFormController.client.accept(message);
		if((boolean)response) {
			displayAlert("Order Canceled","Order "+ mov.getIdOrder()+" Canceled!");
			ordersToApprove.remove(mov);
			newOrderTable.refresh();
		}
		//TODO issue refund for the user
		NotificationManager.sendNotification(mov.getIdUser(), NotificationType.ORDER_NOT_ACCEPTED, mov.getIdOrder());
    }

    //cancels an order that was already in accepted
    @FXML
    void approveCancelRequest(ActionEvent event) {
    	ManagerOrderView mov = cancelOrdersTable.getSelectionModel().getSelectedItem();
    	if(mov == null) {
    		displayError(messageDelay);
    		return;
    	}
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.CANCEL_ORDER);
		message.put("order id", mov.getIdOrder());
		Object response = ClientFormController.client.accept(message);
		if((boolean)response) {
			displayAlert("Order Canceled","Cancel request accepted!\nOrder "+ mov.getIdOrder()+" Canceled!");
			ordersToCancel.remove(mov);
			newOrderTable.refresh();
		}
		//TODO calculate refund and refund the user in the system
		NotificationManager.sendNotification(mov.getIdUser(), NotificationType.CANCEL_REQUEST_APPROVED, 404);
    }

    //denies cancel request
    @FXML
    void denyCancelRequest(ActionEvent event) {
    	ManagerOrderView mov =cancelOrdersTable.getSelectionModel().getSelectedItem();
    	if(mov == null) {
    		displayError(messageDelay);
    		return;
    	}
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.APPROVE_ORDER);
		message.put("order id", mov.getIdOrder());
		Object response = ClientFormController.client.accept(message);
		if((boolean)response) {
			displayAlert("Cancel Request Not Approved","Cancel Request Not Approved!\nOrder "+ mov.getIdOrder()+" returned to status Approved");
			ordersToCancel.remove(mov);
			newOrderTable.refresh();
		}
		NotificationManager.sendNotification(mov.getIdUser(), NotificationType.CANCEL_REQUEST_DENIED, mov.getIdOrder());
    }

    //displays error message for n seconds
    private void displayError(int n) {
    	errorLabel.setVisible(true);
    	PauseTransition pause = new PauseTransition(Duration.seconds(n));
    	pause.setOnFinished(e -> errorLabel.setVisible(false));
    	pause.play();
    }

	private void displayAlert(String title, String text) {
		Alert a = new Alert(AlertType.NONE,title,ButtonType.CLOSE);
		a.setTitle(title);
		a.setContentText(text);
		a.show();
	}
}
