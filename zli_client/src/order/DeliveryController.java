package order;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.ClientFormController;
import entities.Branch;
import entities.Product;
import entities.SingletonOrder;
import javafx.animation.PauseTransition;
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
import javafx.util.Duration;
import util.ManageScreens;

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
		try {
//			if(deliveryRadioButton.isSelected()) {  // if delivery is selected 
//				String addressString = addressField.getText();
//				String recieverName = recieverNameField.getText();
//				String reciever = recieverPhoneField.getText();
//
//				if(addressString.isEmpty() || recieverName.isEmpty() || reciever.isEmpty()) {
//					switchFillAllFields();
//					return;
//				}
//				try {
//					String deliveryDate = deliveryDatePicker.getPromptText();
//					String deliveryHour = hourComboBox.getPromptText();
//					String deliveryMinute= minuteComboBox.getPromptText();
//					String region = regionComboBox.getPromptText();
//					for(Branch branch : branches) {
//						if(branch.getRegion().equals(region)) {
//							SingletonOrder.getInstance().setBranch(branch);
//							break;
//						}	
//					}
//				} catch (NullPointerException ex) {  // One of the comboBoxes is null
//					switchFillAllFields();
//					return;
//				}
//				SingletonOrder.getInstance().set(null);;
//				SingletonOrder.getInstance().setBranch(branch);
//				SingletonOrder.getInstance().setBranch(branch);
//				SingletonOrder.getInstance().setBranch(branch);
//				SingletonOrder.getInstance().setBranch(branch);
//				SingletonOrder.getInstance().setBranch(branch);
//				SingletonOrder.getInstance().setBranch(branch);
//
//			}
//			else {  // if pick-up is selected 
//				
//				
//				
//			}
			ManageScreens.changeScene(getClass().getResource("../order/CheckoutScreen.fxml"), "Checkout Screen");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void switchFillAllFields() {
		fillAllFieldsLabel.setText("* Please fill all the fields");
		PauseTransition pause = new PauseTransition(Duration.seconds(5));
		pause.setOnFinished(e -> fillAllFieldsLabel.setText(""));
		pause.play();
	}
	
	
	@FXML
	void changeToGreetingCardScreen(MouseEvent event) {
		try {
			ManageScreens.changeScene(getClass().getResource("../order/GreetingCardScreen.fxml"),
					"Greeting Card Screen");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void changeToHomeScreen(MouseEvent event) {
		try {
			ManageScreens.changeScene(getClass().getResource("../home/HomeGuestScreen.fxml"), "Home Screen");
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", "fetch branches");
		Object response = ClientFormController.client.accept(message);
		branches = (ObservableList<Branch>) response;
		
		for(Branch branch : branches) {
			regionComboBox.getItems().addAll(branch.getRegion());
			branchComboBox.getItems().addAll(branch.getAddress()+ ", " + branch.getCity());
		}
		
	}

}
