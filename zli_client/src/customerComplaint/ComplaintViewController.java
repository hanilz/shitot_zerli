package customerComplaint;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ComplaintViewController implements Initializable {
	private static Complaint complaint;
	
    @FXML
    private Label comlaintReason;

    @FXML
    private Label complaintContent;

    @FXML
    private Label complaintNumber;

    @FXML
    private Button discardButton;

    @FXML
    private Label errorLabel;

    @FXML
    private Label orderNumber;

    @FXML
    private Button submitButton;

    @FXML
    void discardComplaint(ActionEvent event) {

    }

    @FXML
    void saveComplaint(ActionEvent event) {

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
	}

}
