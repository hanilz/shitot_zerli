package gui;

import java.net.URL;
import java.util.ResourceBundle;

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
import javafx.collections.FXCollections;

public class DeliveryController implements Initializable{

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
    private Label requiredLabel;
    

    int deliveryButton = 0, pickupButton = 0;
    
    @FXML
    void changeToCheckoutScreen(MouseEvent event) {
    	try {
			ClientScreen.changeScene(getClass().getResource("CheckoutScreen.fxml"), "Checkout Screen");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void changeToGreetingCardScreen(MouseEvent event) {
    	try {
			ClientScreen.changeScene(getClass().getResource("GreetingCardScreen.fxml"), "Greeting Card Screen");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void changeToHomeScreen(MouseEvent event) {
    	try {
			ClientScreen.changeScene(getClass().getResource("HomeNotLoggedInScreen.fxml"), "Home Screen");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void selectDelivery(MouseEvent event) {
    	if(deliveryRadioButton.isSelected() && deliveryButton == 0) {
    		addressField.setDisable(false);
    		deliveryDatePicker.setDisable(false);
    		recieverNameField.setDisable(false);
    		hourComboBox.setDisable(false);
    		minuteComboBox.setDisable(false);
    		recieverPhoneField.setDisable(false);
    		requiredLabel.setVisible(true);
    		
    		pickupButton = 0;
    	}
    	deliveryButton++;
    }

    @FXML
    void selectPickup(MouseEvent event) {
    	if(pickUpRadioButton.isSelected() && pickupButton == 0) {
    		addressField.setDisable(true);
    		deliveryDatePicker.setDisable(true);
    		recieverNameField.setDisable(true);
    		hourComboBox.setDisable(true);
    		minuteComboBox.setDisable(true);
    		recieverPhoneField.setDisable(true);
    		requiredLabel.setVisible(false);
    		
    		deliveryButton = 0;

    	}
		pickupButton++;
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {}

}
