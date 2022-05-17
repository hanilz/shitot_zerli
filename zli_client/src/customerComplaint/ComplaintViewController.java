package customerComplaint;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.ClientFormController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import util.Commands;
import util.ManageScreens;
import util.Screens;

public class ComplaintViewController implements Initializable {
	private static Complaint complaint;
	private Double refund = 0.0 ;
	private Double orderPrice;
	
    @FXML
    private Button back;

    @FXML
    private Button closeRequest;

    @FXML
    private Label comlaintReason;

    @FXML
    private Label complaintContent;

    @FXML
    private Label complaintNumber;

    @FXML
    private Label errorLabel;

    @FXML
    private Label maxLabel;

    @FXML
    private Label orderNumber;

    @FXML
    private Button refundButton;

    @FXML
    private Slider refundSlider;

    @FXML
    private TextField refundText;

    @FXML
    void backToComlaints(ActionEvent event) {
		ManageScreens.changeScreenTo(Screens.COMPLAINT_HOME);
    }

    @FXML
    void closeAndDeleteRequest(ActionEvent event) {
    	HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.DELETE_COMPLAINT);
		message.put("Complaint Number", complaint.getComplaintID());
		Object response = ClientFormController.client.accept(message);
		if (!(boolean) response) {
			errorLabel.setText("*Failed to close request");
			errorLabel.setVisible(true);
			return;
		}
		displayPopUp("Complaint Closed","Complaint has been closed\n No refund was issued to the customer");
		ManageScreens.changeScreenTo(Screens.COMPLAINT_HOME);
	}

	private void displayPopUp(String title, String text) {
		Alert a = new Alert(AlertType.NONE,title,ButtonType.CLOSE);
		a.setTitle(title);
		a.setContentText(text);
		a.show();
	}

    @FXML
    void refundUser(ActionEvent event) {
    	refund = Double.parseDouble(refundText.getText());
    	HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.DELETE_COMPLAINT);//might need to be changed in the future to save the refund to the user
		message.put("Complaint Number", complaint.getComplaintID());
		Object response = ClientFormController.client.accept(message);
		if (!(boolean) response) {
			errorLabel.setText("*Failed to close request");
			errorLabel.setVisible(true);
			return;
		}
		displayPopUp("Customer refunded","Complaint has been closed\nCustomer has been refunded "+ refund+" NIS.");
		ManageScreens.changeScreenTo(Screens.COMPLAINT_HOME);
    }

    
    @FXML
    void setRefund(MouseEvent event) {//slider Event
    	refund = refundSlider.getValue();
    	refundText.setText(refundSlider.getValue()+"");
    }
    

    @FXML
    void setRefundBox(ActionEvent event) {
    	Double value = Double.parseDouble(refundText.getText());
    	if(value < orderPrice) {
    		refund = Double.parseDouble(refundText.getText());
    		refundSlider.setValue(refund);    		
    	}
    	else
    		refundText.setText("0");
    		
    }
    
    public static void setComplaint(Complaint complaint2) {
    	complaint = complaint2;
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		complaintNumber.setText(""+complaint.getComplaintID());
		complaintContent.setText(complaint.getComplaintContent());
		comlaintReason.setText(complaint.getComplaintReason());
		orderNumber.setText(""+complaint.getOrderID());
		
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.GET_ORDER_SUM);//might need to be changed in the future to save the refund to the user
		message.put("Order Number", complaint.getOrderID());
		Object response = ClientFormController.client.accept(message);
		orderPrice = (Double)response;
		
		refundSlider.setMax(orderPrice);
		maxLabel.setText(orderPrice+" NIS");
	}

}
