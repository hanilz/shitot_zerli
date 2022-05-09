package order;

import entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import util.ChangeScreen;

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
    		ChangeScreen.changeScene(getClass().getResource("../order/CartScreen.fxml"), "Cart Screen");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void changeToDeliveryScreen(MouseEvent event) {
    	try {
    		ChangeScreen.changeScene(getClass().getResource("../order/DeliveryDetailsScreen.fxml"), "Delivery Details Screen");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void changeToHomeScreen(MouseEvent event) {
    	try {
			if (User.getUserInstance().isUserLoggedIn())
				ChangeScreen.changeScene(getClass().getResource("../home/HomeLoggedInScreen.fxml"), "HomeScreen");
			else
    		ChangeScreen.changeScene(getClass().getResource("../home/HomeNotLoggedInScreen.fxml"), "Home Screen");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void includeGreetingCard(MouseEvent event) {
    	if(isIncludedCheckBox.isSelected()) {
    		System.out.println("I am selected!");
    		enableGreetingCard(false);
    	}
    	else {
    		System.out.println("I am not selected :(");
    		enableGreetingCard(true);
    	}
    }

	private void enableGreetingCard(boolean setOption) {
		titleTextField.setDisable(setOption);
		toTextField.setDisable(setOption);
		fromTextField.setDisable(setOption);
		greetingCardTextArea.setDisable(setOption);
	}

}
