package order;

import entities.SingletonOrder;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import util.InputChecker;
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
	private Label isAllFilledLabel;

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
		// first - check if the user filled all the fields for the greeting card if
		// selected.
		// if true -> the controller will appear a label for filling all the fields
		// else -> the user can proceed.
		if (InputChecker.isFieldsAreEmptyChecker(isIncludedCheckBox.isSelected(), titleTextField.getText(),
				fromTextField.getText(), toTextField.getText(), greetingCardTextArea.getText())) {
			isAllFilledLabel.setText("* Please fill all the fields for the greeting card for proceeding the order!");
			PauseTransition pause = new PauseTransition(Duration.seconds(5));
			pause.setOnFinished(e -> isAllFilledLabel.setText(""));
			pause.play();

		} 
		else {
			//if all the fields are filled, we will insert the greeting card into the order
			insertGreetingCardIntoOrder();
			ManageScreens.changeScreenTo(Screens.DELIVERY_DETAILS);
		}
	}

	private void insertGreetingCardIntoOrder() {
		if(isIncludedCheckBox.isSelected()) //insert only if the user included greeting card.
			SingletonOrder.getInstance().setGreetingCard(String.format("Title: %sm,From: %s,To: %s,GreetingCard: %s", titleTextField.getText(),
					fromTextField.getText(), toTextField.getText(), greetingCardTextArea.getText()));
	}

	@FXML
	void changeToHomeScreen(MouseEvent event) {
		ManageScreens.home();
	}

	@FXML
	void includeGreetingCard(MouseEvent event) {
		if (isIncludedCheckBox.isSelected()) {
			System.out.println("I am selected!");
			enableGreetingCard(false);

		} else {
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
