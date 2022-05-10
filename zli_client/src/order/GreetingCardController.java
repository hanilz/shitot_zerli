package order;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import util.ManageScreens;
import util.Screens;

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
    	ManageScreens.changeScreenTo(Screens.CART);
    }

    @FXML
    void changeToDeliveryScreen(MouseEvent event) {
    	ManageScreens.changeScreenTo(Screens.DELIVERY_DETAILS);
    }

    @FXML
    void changeToHomeScreen(MouseEvent event) {
    	ManageScreens.home();
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
