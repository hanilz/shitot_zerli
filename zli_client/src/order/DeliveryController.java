package order;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
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
import util.ManageScreens;
import util.Screens;

public class DeliveryController implements Initializable {

	@FXML
	private TextField addressField;

	@FXML
	private Button backButton;

    @FXML
    private ComboBox<?> branchComboBox;

    @FXML
	private Button checkoutButton;

	@FXML
	private ToggleGroup delivery;

	@FXML
	private DatePicker deliveryDatePicker;

	@FXML
	private RadioButton deliveryRadioButton;

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
    private ComboBox<?> regionComboBox;
	
	@FXML
	private Label required;

	int deliveryButton = 0, pickupButton = 0;

	@FXML
	void changeToCheckoutScreen(MouseEvent event) {
		ManageScreens.changeScreenTo(Screens.CHECKOUT);
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		hourComboBox.getItems().addAll("00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15",
		        "16","17","18","19","20","21","22","23");
		minuteComboBox.getItems().addAll("00","15","30","45");
		
		
	}

}
