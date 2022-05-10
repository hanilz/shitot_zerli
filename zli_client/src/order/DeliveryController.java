package order;

import java.net.URL;
import java.util.ResourceBundle;

import entities.User;
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
import util.ChangeScreen;
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
	private DatePicker deliveryDatePicker;

	@FXML
	private RadioButton deliveryRadioButton;

	@FXML
	private ImageView homeButton;

	@FXML
	private ComboBox<?> hourComboBox;

	@FXML
	private ComboBox<?> minuteComboBox;

	@FXML
	private RadioButton pickUpRadioButton;

	@FXML
	private TextField recieverNameField;

	@FXML
	private TextField recieverPhoneField;

	@FXML
	private Label required;

	int deliveryButton = 0, pickupButton = 0;

	@FXML
	void changeToCheckoutScreen(MouseEvent event) {
		ChangeScreen.to(Screens.CHECKOUT);
	}

	@FXML
	void changeToGreetingCardScreen(MouseEvent event) {
		ChangeScreen.to(Screens.GREATING_CARD);
	}

	@FXML
	void changeToHomeScreen(MouseEvent event) {
		ChangeScreen.home();
	}

	@FXML
	void selectDelivery(MouseEvent event) {
		if (deliveryRadioButton.isSelected() && deliveryButton == 0) {
			enableDeliveryOptions(false);

			pickupButton = 0;
		}
		deliveryButton++;
	}

	private void enableDeliveryOptions(boolean setOption) {
		addressField.setDisable(setOption);
		deliveryDatePicker.setDisable(setOption);
		recieverNameField.setDisable(setOption);
		hourComboBox.setDisable(setOption);
		minuteComboBox.setDisable(setOption);
		recieverPhoneField.setDisable(setOption);
		required.setVisible(!setOption);
	}

	@FXML
	void selectPickup(MouseEvent event) {
		if (pickUpRadioButton.isSelected() && pickupButton == 0) {
			enableDeliveryOptions(true);

			deliveryButton = 0;

		}
		pickupButton++;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {//?
	}

}
