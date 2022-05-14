package customerComplaint;

import java.util.HashMap;

import client.ClientFormController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import util.Commands;
import util.InputChecker;
import util.ManageScreens;
import util.Screens;

public class CustomerComplaintController {
	
    @FXML
    private Label idLabel;
    
	@FXML
	private TextField complaintReason;

	@FXML
	private TextArea complaintText;

	@FXML
	private Button discardButton;

	@FXML
	private Label errorLabel;

	@FXML
	private TextField nameString;

	@FXML
	private TextField personalID;

	@FXML
	private Button submitButton;

	@FXML
	void discardComplaint(ActionEvent event) {
		ManageScreens.changeScreenTo(Screens.COMPLAINT_HOME);
	}

	@FXML
	void saveComplaint(ActionEvent event) {
		if(!checkID())//check if id format is correct
			return;
		if(!sendComplaintToDB()) {
			idLabel.setText("*ID does not exist in DB");
			idLabel.setTextFill(Color.RED);
			return;
		}
		//ManageScreens.changeScreenTo(Screens.COMPLAINT_HOME);
	}

	//returns true if id format is incorrect
	private boolean checkID() {
		if(!InputChecker.checkID(personalID.getText())) {
			idLabel.setText("*Please enter correct ID");
			idLabel.setTextFill(Color.RED);
			return false;
		}
		else {
			if(!idLabel.getText().equals("*9 digits")) {
				idLabel.setText("Valid!");
				idLabel.setTextFill(Color.BLUE);
			}
		}
		return true;
	}
	
	
	/**
	 * @return
	 * this method is used to send the complaint to the db
	 * will return false if the id of the user does not exist in db
	 * 
	 */
	private boolean sendComplaintToDB() {
		StringBuilder sb = new StringBuilder("Complainer Name: "+ nameString.getText());
		sb.append("\nComplainer ID: " + personalID.getText());
		sb.append("\nComplaint Reason: " + complaintReason.getText());
		sb.append("\nComplaint Content:\n" + complaintText.getText());
		if(sb.length()>1024)
			return false;
		else {
			HashMap<String, Object> message = new HashMap<>();
			message.put("command", Commands.SUBMIT_COMPLAINT);
			message.put("Personal ID", personalID.getText());
			message.put("Complaint", sb.toString());
			Object response = ClientFormController.client.accept(message);
			if(!(boolean)response)
				return false;
		}
		return true;
	}
}
