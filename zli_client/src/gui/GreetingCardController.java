package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class GreetingCardController {

    @FXML
    private Button backToCart;

    @FXML
    private Button deliveryButton;

    @FXML
    private TextField fromTextField;

    @FXML
    private TextArea greetingCardTextArea;

    @FXML
    private ImageView homeButton;
    
    @FXML
    private CheckBox isIncludedCheckBox;

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField toTextField;

    @FXML
    void changeToCartScreen(MouseEvent event) {
    	try {
			ClientScreen.changeScene(getClass().getResource("CartScreen.fxml"), "Cart Screen");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void changeToDeliveryScreen(MouseEvent event) {
    	try {
			ClientScreen.changeScene(getClass().getResource("DeliveryDetailsScreen.fxml"), "Delivery Details Screen");
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
    void includeGreetingCard(MouseEvent event) {
    	if(isIncludedCheckBox.isSelected()) {
    		System.out.println("I am selected!");
    		titleTextField.setDisable(false);
    		toTextField.setDisable(false);
    		fromTextField.setDisable(false);
    		greetingCardTextArea.setDisable(false);
    	}
    	else {
    		System.out.println("I am not selected :(");
    		titleTextField.setDisable(true);
    		toTextField.setDisable(true);
    		fromTextField.setDisable(true);
    		greetingCardTextArea.setDisable(true);
    	}
    }

}
