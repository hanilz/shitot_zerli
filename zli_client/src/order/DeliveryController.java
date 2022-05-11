package order;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.ClientFormController;
import entities.Branch;
import entities.Delivery;
import entities.SingletonOrder;
import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import util.InputChecker;
import util.ManageScreens;
import util.Screens;

public class DeliveryController implements Initializable {

	@FXML
	private TextField addressField;

	@FXML
	private Button backButton;

	@FXML
	private ComboBox<Branch> branchComboBox;

	@FXML
	private Button checkoutButton;

	@FXML
	private ToggleGroup delivery;

	@FXML
	private DatePicker deliveryDatePicker;

	@FXML
	private RadioButton deliveryRadioButton;

	@FXML
	private Label fillAllFieldsLabel;

	@FXML
	private VBox deliveryVBox;

	@FXML
	private ImageView homeButton;

	@FXML
	private ComboBox<String> hourComboBox;

	@FXML
	private ComboBox<String> minuteComboBox;

	@FXML
	private HBox pickupHBox;

	@FXML
	private RadioButton pickUpRadioButton;

    @FXML
    private Label selectABranchLabel;
	
	@FXML
	private TextField recieverNameField;

	@FXML
	private TextField recieverPhoneField;

	@FXML
	private ComboBox<String> regionComboBox;

	@FXML
	private Label required;

	int deliveryButton = 0, pickupButton = 0;

	ObservableList<Branch> branches;

	/**
	 * @param event
	 */
	@FXML
	void changeToCheckoutScreen(MouseEvent event) {
		boolean canProceed = true;
		// First - we will check the fields in the delivery option if selected.
		// if the method returned true for the fields -> one of them are empty or ALL of
		// them.
		// if the method returned false for the comboBox -> the user didn't select any
		// option.
		// else -> the user filled all the fields
		if (deliveryRadioButton.isSelected()) {
			canProceed = checkInputDelivery(canProceed);
		}
		// the same thing for pickUp option, if the comboBox has not changed -> the
		// label will appear for selecting the branch.
		else if (pickUpRadioButton.isSelected()) {
			if (!InputChecker.isPickUpComboBoxChanged(branchComboBox.getValue())) {
				switchFillAllFields(selectABranchLabel, "* Please select a branch!");
				canProceed = false;
			}
		}
		// if everything is filled and selected, the user will proceed to checkout and
		// we will set the delivery if selected and the branch for the order
		if(canProceed) {
			if (deliveryRadioButton.isSelected())
				insertDelivery();
			else if (pickUpRadioButton.isSelected())
				SingletonOrder.getInstance().setBranch(branchComboBox.getValue());
			ManageScreens.changeScreenTo(Screens.CHECKOUT);
		}
	}

	private boolean checkInputDelivery(boolean canProceed) {
		if (!InputChecker.isDeliveryComboBoxChanged(hourComboBox.getValue(), minuteComboBox.getValue(),
				regionComboBox.getValue())) {
			switchFillAllFields(fillAllFieldsLabel, "* Please fill all the fields!");
			canProceed = false;
		}
		else if (InputChecker.isFieldsAreEmptyChecker(true, addressField.getText(), recieverNameField.getText(),
				deliveryDatePicker.getValue().toString(), recieverPhoneField.getText())) {
			switchFillAllFields(fillAllFieldsLabel, "* Please fill all the fields!");
			canProceed = false;
		}
		else if(!InputChecker.isDeliveryInputValid(recieverPhoneField.getText(), recieverNameField.getText())) {
			switchFillAllFields(fillAllFieldsLabel, "* Please check the phone number or reciever name!");
			canProceed = false;
		}
		else if(!InputChecker.isPhoneNumberVaild(recieverPhoneField.getText())) {
			switchFillAllFields(fillAllFieldsLabel, "* Please check the phone number!");
			canProceed = false;
		}
		return canProceed;
	}

	private void insertDelivery() {
		String address = addressField.getText();
		String pattern = "dd/MM/yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String dateSelected = "";
		try {
			dateSelected = simpleDateFormat.parse(deliveryDatePicker.getValue().toString()).toString();
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		String deliveryDate = String.format("%s %s:%s:00",dateSelected,hourComboBox.getValue(),minuteComboBox.getValue());
		String phoneNumber = recieverPhoneField.getText();
		String receiverName = recieverNameField.getText();
		String status = "Pending";  // TODO: const class or enum for delivery options

		SingletonOrder.getInstance().setDelivery(new Delivery(address, receiverName, phoneNumber, deliveryDate, status));
		initBranchForOrder(regionComboBox.getValue());		
	}

	private void initBranchForOrder(String checkString) {
		Branch selectedBranch = selectBranch(checkString);
		SingletonOrder.getInstance().setBranch(selectedBranch);
	}

	private Branch selectBranch(String checkString) {
		for (Branch currentBranch : branches)
			if (currentBranch.getRegion().contains(checkString))
				return currentBranch;
		return null;
	}

	private void switchFillAllFields(Label label, String textToShow) {
		label.setText(textToShow);
		PauseTransition pause = new PauseTransition(Duration.seconds(3));
		pause.setOnFinished(e -> label.setText(""));
		pause.play();
	}

	@FXML
	void changeToGreetingCardScreen(MouseEvent event) {
		ManageScreens.changeScreenTo(Screens.GREATING_CARD);
	}

	@FXML
	void changeToHomeScreen(MouseEvent event) {
		ManageScreens.home();
	}

	@FXML
	void selectDelivery(MouseEvent event) {
		if (deliveryRadioButton.isSelected() && deliveryButton == 0) {
			pickupHBox.setVisible(false);
			deliveryVBox.setVisible(true);

			pickupButton = 0;
		}
		deliveryButton++;
	}

	@FXML
	void selectPickup(MouseEvent event) {
		if (pickUpRadioButton.isSelected() && pickupButton == 0) {
			pickupHBox.setVisible(true);
			deliveryVBox.setVisible(false);

			deliveryButton = 0;

		}
		pickupButton++;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		SingletonOrder.getInstance().setBranch(null);
		SingletonOrder.getInstance().setDelivery(null);
		
		hourComboBox.getItems().addAll("08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20");
		minuteComboBox.getItems().addAll("00", "15", "30", "45");

		HashMap<String, Object> message = new HashMap<>();
		message.put("command", "fetch branches");
		Object response = ClientFormController.client.accept(message);
		branches = (ObservableList<Branch>) response;

		for (Branch branch : branches) {
			regionComboBox.getItems().add(branch.getRegion());
			branchComboBox.getItems().add(branch);
		}

	}

}
