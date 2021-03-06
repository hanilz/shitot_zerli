package customerComplaint;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.ClientFormController;
import entities.Complaint;
import inputs.InputChecker;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import util.Commands;
import util.ManageScreens;
import util.Screens;

/**
 *controller class for customerComplaintView
 */
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
    private Label minLabel;

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

    
    /**
     * closeAndDeleteRequest closes a request without refund to the user
     * @param event
     */
    @FXML
    void closeAndDeleteRequest(ActionEvent event) {
    	HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.CLOSE_COMPLAINT);
		message.put("Complaint Number", complaint.getComplaintID());
		message.put("refund", 0.0);
		Object response = ClientFormController.client.accept(message);
		if (!(boolean) response) {
			errorLabel.setText("*Failed to close request");
			errorLabel.setVisible(true);
			return;
		}
		ManageScreens.displayAlert("Complaint Closed","Complaint has been closed\n No refund was issued to the customer");
		ManageScreens.changeScreenTo(Screens.COMPLAINT_HOME);
	}
    
    /**
     * refundUser closes the user and refunds the user the selected ammount by the slider
     * @param event
     */
    @FXML
    void refundUser(ActionEvent event) {
    	HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.CLOSE_COMPLAINT);
		message.put("Complaint Number", complaint.getComplaintID());
		message.put("refund", refund);
		Object response = ClientFormController.client.accept(message);
		if (!(boolean) response) {
			errorLabel.setText("*Failed to close request");
			errorLabel.setVisible(true);
			return;
		}
		ManageScreens.displayAlert("Customer refunded","Complaint has been closed\nCustomer has been refunded "+ InputChecker.price(refund));
		ManageScreens.changeScreenTo(Screens.COMPLAINT_HOME);
    }

    
    
    /**sets the text of the refund label according to the refund on the slider
     * @param event
     */
    @FXML
    void setRefund(MouseEvent event) {
    	refund = refundSlider.getValue();
    	refundText.setText(String.format("%.0f", refundSlider.getValue()));
    }
    

    /**sets the refund whenever a key is pressed in the check box
     * @param event
     */
    @FXML
    void setRefundBox(KeyEvent event) {
    	Double value = !refundText.getText().isEmpty() ? Double.parseDouble(refundText.getText()) : 0.0;
    	if(value <= orderPrice) {
    		refund = value;
    	}
    	else {
    		refundText.setText(orderPrice+"");
    		refund = orderPrice;
    	}
    	refundSlider.setValue(refund);
    	refundText.positionCaret(refundText.getText().length());
    	

    	System.out.println(refundText.getText());
    }
    
    
    /**the complaint must be set before initializing the screen
     * @param complaint2
     */
    public static void setComplaint(Complaint complaint2) {
    	complaint = complaint2;
    }
    
    
    
	/**
	 *	initializes all the fields in the screen
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		complaintNumber.setText(""+complaint.getComplaintID());
		complaintContent.setText(complaint.getComplaintContent());
		comlaintReason.setText(complaint.getComplaintReason());
		orderNumber.setText(""+complaint.getOrderID());
		minLabel.setText(InputChecker.price(0));
		
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.GET_ORDER_SUM);//might need to be changed in the future to save the refund to the user
		message.put("Order Number", complaint.getOrderID());
		Object response = ClientFormController.client.accept(message);
		orderPrice = (Double)response;
		refundSlider.setMax(orderPrice);
		maxLabel.setText(InputChecker.price(orderPrice));
		refundText.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	refundText.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
		
	}

}
