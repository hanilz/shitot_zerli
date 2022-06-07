package order;

import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.ClientFormController;
import entities.Branch;
import entities.Delivery;
import entities.SingletonOrder;
import inputs.InputChecker;
import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
import util.Commands;
import util.ManageScreens;
import util.Screens;

public class DeliveryController implements Initializable {

	@FXML
	private TextField addressField;

	@FXML
	private Button backButton;

	@FXML
	private Button checkoutButton;

	@FXML
	private ToggleGroup delivery;

	@FXML
	private ComboBox<Branch> deliveryBranchComboBox;

	@FXML
	private DatePicker deliveryDatePicker;

	@FXML
	private Label deliveryFeeLabel;

	@FXML
	private ComboBox<String> deliveryHourComboBox;

	@FXML
	private ComboBox<String> deliveryMinuteComboBox;

	@FXML
	private RadioButton deliveryRadioButton;

	@FXML
	private VBox deliveryVBox;

	@FXML
	private Label fillAllFieldsLabel;

	@FXML
	private ImageView homeButton;

	@FXML
	private RadioButton pickUpRadioButton;

	@FXML
	private ComboBox<Branch> pickupBranchComboBox;

	@FXML
	private DatePicker pickupDatePicker;

	@FXML
	private HBox pickupHBox;

	@FXML
	private ComboBox<String> pickupHourComboBox;

	@FXML
	private ComboBox<String> pickupMinuteComboBox;

	@FXML
	private TextField recieverNameField;

	@FXML
	private TextField recieverPhoneField;

	@FXML
	private Label required;

	@FXML
	private Label selectABranchLabel;

	@FXML
	private CheckBox expressDeliveryCheckBox;

	int deliveryButton = 0, pickupButton = 0;

	double deliveryFee = 15;

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
		if (deliveryRadioButton.isSelected())
			canProceed = checkInputDelivery(canProceed);
		// the same thing for pickUp option, if the comboBox has not changed -> the
		// label will appear for selecting the branch.
		else
			canProceed = checkInputPickup(canProceed);
		// if everything is filled and selected, the user will proceed to checkout and
		// we will set the delivery if selected and the branch for the order
		if (canProceed) {
			storeState();

			ManageScreens.changeScreenTo(Screens.CHECKOUT);
		}
	}

	private boolean checkInputPickup(boolean canProceed) {
		if (!InputChecker.isBranchNotNull(pickupBranchComboBox.getValue())) {
			switchFillAllFields(selectABranchLabel, "* Please select a branch!");
			canProceed = false;
		} else if (InputChecker.isNull(pickupDatePicker.getValue())) {
			switchFillAllFields(selectABranchLabel, "* Please select a date!");
			canProceed = false;
		} else if (InputChecker.isNull(pickupHourComboBox.getValue())) {
			switchFillAllFields(selectABranchLabel, "* Please select an hour!");
			canProceed = false;
		} else if (InputChecker.isNull(pickupMinuteComboBox.getValue())) {
			switchFillAllFields(selectABranchLabel, "* Please select the minutes!");
			canProceed = false;
		}
		if (canProceed) {
			if (checkDateNotPassed(pickupDatePicker.getValue(), pickupHourComboBox.getValue(),
					pickupMinuteComboBox.getValue())) {
				switchFillAllFields(selectABranchLabel, "* Please select a date that isn't sooner than the next 3 hours!");
				canProceed = false;
			}
		}
		return canProceed;
	}

	private boolean checkDateNotPassed(LocalDate value, String value2, String value3) {
		String dateTime = String.format("%s %s:%s:00", value.toString(), value2, value3);
		return InputChecker.isDateBeforeNow3Hours(dateTime);
	}

	private boolean checkInputDelivery(boolean canProceed) {
		if (InputChecker.isFieldsAreEmptyChecker(addressField.getText(), recieverNameField.getText(),
				recieverPhoneField.getText())) {
			switchFillAllFields(fillAllFieldsLabel, "* Please fill all the fields!");
			canProceed = false;
		} else if (InputChecker.isNull(deliveryBranchComboBox.getValue())) {
			switchFillAllFields(fillAllFieldsLabel, "* Please select a branch!");
			canProceed = false;
		} else if (!InputChecker.isDeliveryInputValid(recieverPhoneField.getText(), recieverNameField.getText())) {
			switchFillAllFields(fillAllFieldsLabel, "* Please check the phone number or reciever name!");
			canProceed = false;
		} else if (!InputChecker.isPhoneNumberVaild(recieverPhoneField.getText())) {
			switchFillAllFields(fillAllFieldsLabel, "* Please check the phone number!");
			canProceed = false;
		} else if (!expressDeliveryCheckBox.isSelected()) {
			if (InputChecker.areDateTimeFieldsNotSelected(deliveryDatePicker.getValue(),
					deliveryHourComboBox.getValue(), deliveryMinuteComboBox.getValue())) {
				switchFillAllFields(fillAllFieldsLabel, "* Please select delivery date and time!");
				canProceed = false;
			} else if (checkDateNotPassed(deliveryDatePicker.getValue(), deliveryHourComboBox.getValue(),
					deliveryMinuteComboBox.getValue())) {
				switchFillAllFields(fillAllFieldsLabel, "* Please select a date that isn't sooner than the next 3 hours!");
				canProceed = false;
			}
		}
		return canProceed;
	}

	private void insertDelivery() {
		String address = addressField.getText();

		String deliveryDate = buildDeliveryDate(deliveryDatePicker.getValue(), deliveryHourComboBox.getValue(),
				deliveryMinuteComboBox.getValue());
		String phoneNumber = recieverPhoneField.getText();
		String receiverName = recieverNameField.getText();
		String status = "pending"; // TODO: const class or enum for delivery options
		String type = (expressDeliveryCheckBox.isSelected() ? "express delivery" : "delivery");

		SingletonOrder.getInstance()
				.setDelivery(new Delivery(address, receiverName, phoneNumber, deliveryDate, status, type));
		SingletonOrder.getInstance().setBranch(deliveryBranchComboBox.getValue());
		SingletonOrder.getInstance().setIsExpress(expressDeliveryCheckBox.isSelected());
	}

	private String buildDeliveryDate(LocalDate date, String hour, String minutes) {
		String deliveryDate = "";
		if (date != null)
			deliveryDate += date.toString() + " ";
		else
			deliveryDate += "None ";
		if (hour != null)
			deliveryDate += hour + ":";
		else
			deliveryDate += "None:";
		if (minutes != null)
			deliveryDate += minutes + ":";
		else
			deliveryDate += "None:";

		deliveryDate += "00";

		return deliveryDate;
	}

	private void insertPickup() {
		String deliveryDate = buildDeliveryDate(pickupDatePicker.getValue(), pickupHourComboBox.getValue(),
				pickupMinuteComboBox.getValue());
		String status = "pending"; // TODO: const class or enum for delivery options
		String type = "pickup";

		SingletonOrder.getInstance().setPickup(new Delivery(deliveryDate, status, type));
		SingletonOrder.getInstance().setPickupBranch(pickupBranchComboBox.getValue());
	}

	private void switchFillAllFields(Label label, String textToShow) {
		label.setText(textToShow);
		PauseTransition pause = new PauseTransition(Duration.seconds(3));
		pause.setOnFinished(e -> label.setText(""));
		pause.play();
	}

	@FXML
	void changeToGreetingCardScreen(MouseEvent event) {
		storeState();
		ManageScreens.changeScreenTo(Screens.GREETING_CARD);
	}

	private void storeState() {
		insertDelivery();
		insertPickup();
		SingletonOrder.getInstance().setIsPickup(pickUpRadioButton.isSelected());
	}

	@FXML
	void changeToHomeScreen(MouseEvent event) {
		storeState();
		ManageScreens.home();
	}

	@FXML
	void selectDeliveryEvent(MouseEvent event) {
		selectDelivery();
	}

	private void selectDelivery() {
		if (deliveryRadioButton.isSelected() && deliveryButton == 0) {
			pickupHBox.setVisible(false);
			deliveryVBox.setVisible(true);

			pickupButton = 0;
			updateDeliveryFeeLabel();
		}
		deliveryButton++;
	}

	@FXML
	void selectPickupEvent(MouseEvent event) {
		selectPickup();
	}

	private void selectPickup() {
		if (pickUpRadioButton.isSelected() && pickupButton == 0) {
			pickupHBox.setVisible(true);
			deliveryVBox.setVisible(false);

			deliveryButton = 0;
			updateDeliveryFeeLabel();
		}
		pickupButton++;
	}

	private void updateDeliveryFeeLabel() {
		deliveryFee = (deliveryRadioButton.isSelected() ? (expressDeliveryCheckBox.isSelected() ? 30 : 15) : 0);

		deliveryFeeLabel.setText(InputChecker.price(deliveryFee));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initTimeComboBoxes();

		initBranchComboBoxes();

		initScreenState();

		updateDeliveryFeeLabel();
	}

	// retains the input the user has put before moving to another screen
	private void initScreenState() {
		Delivery pickup = SingletonOrder.getInstance().getPickup();
		Delivery delivery = SingletonOrder.getInstance().getDelivery();
		Branch pickupBranch = SingletonOrder.getInstance().getPickupBranch();
		Branch DeliveryBranch = SingletonOrder.getInstance().getBranch();
		boolean isExpress = SingletonOrder.getInstance().getIsExpress();
		boolean isPickup = SingletonOrder.getInstance().getIsPickup();
		if (pickup != null)
			initPickupState(pickup, pickupBranch);
		if (delivery != null)
			initDeliveryState(delivery, DeliveryBranch, isExpress);
		initRadioButtonsState(isPickup);
	}

	private void initPickupState(Delivery pickup, Branch pickupBranch) {
		if (InputChecker.isBranchNotNull(pickupBranch))
			pickupBranchComboBox.setValue(pickupBranch);
		initDateTimeBoxes(pickup, pickupDatePicker, pickupHourComboBox, pickupMinuteComboBox);
	}

	private void initDateTimeBoxes(Delivery delivery, DatePicker datePicker, ComboBox<String> hourComboBox,
			ComboBox<String> minuteComboBox) {
		String[] dateTime = delivery.getDeliveryDate().split(" "); // [0] is date [1] is time
		String[] time = dateTime[1].split(":"); // [0] is hours [1] is minutes [2] is seconds

		if (!InputChecker.isStringNone(dateTime[0])) // not empty
			datePicker.setValue(LocalDate.parse(dateTime[0]));

		if (!InputChecker.isStringNone(time[0])) // hours
			hourComboBox.setValue(time[0]);

		if (!InputChecker.isStringNone(time[1])) // minutes
			minuteComboBox.setValue(time[1]);
	}

	private void initDeliveryState(Delivery delivery, Branch deliveryBranch, boolean isExpress) {
		addressField.setText(delivery.getAddress());
		recieverNameField.setText(delivery.getReceiverName());
		recieverPhoneField.setText(delivery.getPhoneNumber());

		if (InputChecker.isBranchNotNull(deliveryBranch))
			deliveryBranchComboBox.setValue(deliveryBranch);

		initDateTimeBoxes(delivery, deliveryDatePicker, deliveryHourComboBox, deliveryMinuteComboBox);

		expressDeliveryCheckBox.setSelected(isExpress);
		switchExpress();
	}

	private void initRadioButtonsState(boolean isPickup) {
		if (isPickup) {
			delivery.selectToggle(pickUpRadioButton);
			selectPickup();
		} else
			selectDelivery();
	}

	private void initTimeComboBoxes() {
		deliveryHourComboBox.getItems().addAll("08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
				"20");
		pickupHourComboBox.getItems().addAll("08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
				"20");
		deliveryMinuteComboBox.getItems().addAll("00", "15", "30", "45");
		pickupMinuteComboBox.getItems().addAll("00", "15", "30", "45");
	}

	private void initBranchComboBoxes() {
		fetchBranches();

		deliveryBranchComboBox.getItems().addAll(branches);
		pickupBranchComboBox.getItems().addAll(branches);
	}

	// for getting all branches for the branches ComboBoxes
	@SuppressWarnings("unchecked")
	private void fetchBranches() {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.FETCH_BRANCHES);
		Object response = ClientFormController.client.accept(message);
		branches = (ObservableList<Branch>) response;
	}

	@FXML
	void selectExpressEvent(MouseEvent event) {
		switchExpress();

		updateDeliveryFeeLabel();
	}

	private void switchExpress() {
		deliveryDatePicker.setDisable(expressDeliveryCheckBox.isSelected());
		deliveryHourComboBox.setDisable(expressDeliveryCheckBox.isSelected());
		deliveryMinuteComboBox.setDisable(expressDeliveryCheckBox.isSelected());
	}
}
