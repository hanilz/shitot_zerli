package order;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.ClientFormController;
import entities.Branch;
import entities.SingletonDelivery;
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
	private ComboBox<String> branchComboBox;

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
	private TextField recieverNameField;

	@FXML
	private TextField recieverPhoneField;

	@FXML
	private ComboBox<String> regionComboBox;

	@FXML
	private Label required;

	int deliveryButton = 0, pickupButton = 0;

	ObservableList<Branch> branches;

	@FXML
	void changeToCheckoutScreen(MouseEvent event) {
		/*
		 * try { // if(deliveryRadioButton.isSelected()) { // if delivery is selected //
		 * String addressString = addressField.getText(); // String recieverName =
		 * recieverNameField.getText(); // String reciever =
		 * recieverPhoneField.getText(); // // if(addressString.isEmpty() ||
		 * recieverName.isEmpty() || reciever.isEmpty()) { // switchFillAllFields(); //
		 * return; // } // try { // String deliveryDate =
		 * deliveryDatePicker.getPromptText(); // String deliveryHour =
		 * hourComboBox.getPromptText(); // String deliveryMinute=
		 * minuteComboBox.getPromptText(); // String region =
		 * regionComboBox.getPromptText(); // for(Branch branch : branches) { //
		 * if(branch.getRegion().equals(region)) { //
		 * SingletonOrder.getInstance().setBranch(branch); // break; // } // } // }
		 * catch (NullPointerException ex) { // One of the comboBoxes is null //
		 * switchFillAllFields(); // return; // } //
		 * SingletonOrder.getInstance().set(null);; //
		 * SingletonOrder.getInstance().setBranch(branch); //
		 * SingletonOrder.getInstance().setBranch(branch); //
		 * SingletonOrder.getInstance().setBranch(branch); //
		 * SingletonOrder.getInstance().setBranch(branch); //
		 * SingletonOrder.getInstance().setBranch(branch); //
		 * SingletonOrder.getInstance().setBranch(branch); // // } // else { // if
		 * pick-up is selected // // // // }
		 * ManageScreens.changeScene(getClass().getResource(
		 * "../order/CheckoutScreen.fxml"), "Checkout Screen"); } catch (Exception e) {
		 * e.printStackTrace(); }
		 */
		boolean canProceed = true;
		// First - we will check the fields in the deilivery option if selected.
		// if the method returned true for the fields -> one of them are empty or ALL of
		// them.
		// if the method returned false for the comboBox -> the user didn't select any
		// option.
		// else -> the user filled all the fields
		if (deliveryRadioButton.isSelected()) {
			if (!InputChecker.isDeliveryComboBoxIsChanged(hourComboBox.getValue(), minuteComboBox.getValue(),
					regionComboBox.getValue())) {
				switchFillAllFields();
				canProceed = false;
			}
			else if (InputChecker.isFieldsAreEmptyChecker(true, addressField.getText(), recieverNameField.getText(),
					deliveryDatePicker.getValue().toString(), recieverPhoneField.getText())) {
				switchFillAllFields();
				canProceed = false;
			}
		}
		// the same thing for pickUp option, if the comboBox has not changed -> the
		// label will appear for selecting the branch.
		else if (pickUpRadioButton.isSelected()) {
			//if (!InputChecker.isPickUpComboBoxIsChanged(branchComboBox.getValue())) {
				//TODO - label for pickup if the user didn't selected a branch
				//canProceed = false;
			//}
		}
		// if everything is filled and selected, the user will proceed to checkout and
		// we will set the delivery if selected and the branch for the order
		if(canProceed) {
			insertDelivery();
			if (pickUpRadioButton.isSelected())
				initBranchForOrder(branchComboBox.getValue());
			else
				initBranchForOrder(addressField.getText());
			ManageScreens.changeScreenTo(Screens.CHECKOUT);
		}
	}

	private void insertDelivery() {
		if (deliveryRadioButton.isSelected()) {
			SingletonDelivery.getDeliveryInstance().setAddress(addressField.getText());
			SingletonDelivery.getDeliveryInstance().setDeliveryDate(deliveryDatePicker.getValue().toString());
			SingletonDelivery.getDeliveryInstance().setPhoneNumber(recieverPhoneField.getText());
			SingletonDelivery.getDeliveryInstance().setReceiverName(recieverNameField.getText());
			SingletonDelivery.getDeliveryInstance().setStatus("Pending");
		}
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

	private void switchFillAllFields() {
		fillAllFieldsLabel.setText("* Please fill all the fields");
		PauseTransition pause = new PauseTransition(Duration.seconds(3));
		pause.setOnFinished(e -> fillAllFieldsLabel.setText(""));
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
		hourComboBox.getItems().addAll("08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20");
		minuteComboBox.getItems().addAll("00", "15", "30", "45");

		HashMap<String, Object> message = new HashMap<>();
		message.put("command", "fetch branches");
		Object response = ClientFormController.client.accept(message);
		branches = (ObservableList<Branch>) response;

		for (Branch branch : branches) {
			regionComboBox.getItems().addAll(branch.getRegion());
			branchComboBox.getItems().addAll(branch.toString());
		}

	}

}
