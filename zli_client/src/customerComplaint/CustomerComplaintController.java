package customerComplaint;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.ClientFormController;
import entities.Complaint;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import util.Commands;
import util.ManageScreens;
import util.Screens;

/**
 *	this class is used as a controller for the CustomerComplaintScreen.fxml
 */
public class CustomerComplaintController implements Initializable {
	private ObservableList<Integer> orderNumbers = FXCollections.observableArrayList();

	@FXML
	private Label complaintError;

	@FXML
	private TextField complaintReason;

	@FXML
	private TextArea complaintText;

	@FXML
	private Button discardButton;

	@FXML
	private Label errorLabel;

	@FXML
	private Label orderLabel;

	@FXML
	private ComboBox<Integer> orderNumberCombo;

	@FXML
	private Button submitButton;

	/**
	 *gets the order numbers the do not have a complaint yet and displays them to the customer service employee to write a complaint
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.GET_ORDER_NUMBERS);
		orderNumbers = (ObservableList<Integer>) ClientFormController.client.accept(message);
		orderNumberCombo.setItems(orderNumbers);
	}

	/**returns back to the main complaint screen without saving the complaint
	 * @param event
	 */
	@FXML
	void discardComplaint(ActionEvent event) {
		if(ManageScreens.getYesNoDecisionAlert("Discard Complaint", "Are you sure you want to cancel this complaint?", null))
			ManageScreens.changeScreenTo(Screens.COMPLAINT_HOME);
	}

	/**returns back to the main complaint screen after saving the complaint
	 * @param event
	 */
	@FXML
	void saveComplaint(ActionEvent event) {
		if (!checkInput())
			return;
		if(!ManageScreens.getYesNoDecisionAlert("Submit Complaint", "Are you sure you want submit this complaint?", null))
			return;
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.SUBMIT_COMPLAINT);
		
		message.put("Complaint", new Complaint(User.getUserInstance().getIdUser(), orderNumberCombo.getValue(), complaintReason.getText(), complaintText.getText()));
		Object response = ClientFormController.client.accept(message);
		if (!(boolean) response) {
			errorLabel.setVisible(true);
			return;
		}
		errorLabel.setText("Submitted Successfuly");
		errorLabel.setTextFill(Color.GREEN);
		errorLabel.setVisible(true);
		ManageScreens.displayAlert("Complaint Submitted","Your complaint has been submitted\nplease make sure to review it within the next 24 hours");
		ManageScreens.changeScreenTo(Screens.COMPLAINT_HOME);
	}

	/**used to make sure that all the fields have been filled
	 * @return
	 */
	private boolean checkInput() {
		
		if (orderNumberCombo.getValue() == null) {
			// idLabel.setText("*ID does not exist in DB");
			orderLabel.setTextFill(Color.RED);
			errorLabel.setVisible(true);
			return false;
		} else {
			orderLabel.setText("OK");
			orderLabel.setTextFill(Color.GREEN);
		}
		if (complaintReason.getText().length() == 0 || complaintText.getText().length() == 0 || complaintReason.getText().length() >1024 || complaintText.getText().length() > 128 ) {
			complaintError.setTextFill(Color.RED);
			errorLabel.setText("*Reason length or Content is empty or to long (Reason: "+complaintReason.getText().length()+"/128, Complaint: "+complaintText.getText().length()+"/1024 )");
			errorLabel.setVisible(true);
			return false;
		}
		return true;
	}
}
