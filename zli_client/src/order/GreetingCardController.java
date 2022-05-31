package order;

import java.net.URL;
import java.util.ResourceBundle;

import entities.SingletonOrder;
import inputs.InputChecker;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import util.ManageScreens;
import util.Screens;

public class GreetingCardController implements Initializable {

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
		insertGreetingCardIntoOrder();
		ManageScreens.changeScreenTo(Screens.CART);
	}

	@FXML
	void changeToDeliveryScreen(MouseEvent event) {
		// first - check if the user filled all the fields for the greeting card if
		// selected.
		// if true -> the controller will appear a label for filling all the fields
		// else -> the user can proceed.
		if (InputChecker.areGreetingCardFieldsEmptyChecker(isIncludedCheckBox.isSelected(), titleTextField.getText(),
				fromTextField.getText(), toTextField.getText(), greetingCardTextArea.getText())) {
			switchFillAllFieldsLabel(
					"* Please fill all the fields for the greeting card in order to proceed with the order!");
		} else if (isIncludedCheckBox.isSelected()
				&& !InputChecker.isGreetingCardInputVaild(fromTextField.getText(), toTextField.getText())) {
			switchFillAllFieldsLabel("* Please check the input of the greeting card!");
		} else {
			// if all the fields are filled, we will insert the greeting card into the order
			insertGreetingCardIntoOrder();
			ManageScreens.changeScreenTo(Screens.DELIVERY_DETAILS);
		}
	}

	private void switchFillAllFieldsLabel(String text) {
		isAllFilledLabel.setText(text);
		PauseTransition pause = new PauseTransition(Duration.seconds(3));
		pause.setOnFinished(e -> isAllFilledLabel.setText(""));
		pause.play();
	}

	private void insertGreetingCardIntoOrder() {
		SingletonOrder.getInstance().setGreetingCardFields(titleTextField.getText(), fromTextField.getText(),
				toTextField.getText(), greetingCardTextArea.getText());
		if (isIncludedCheckBox.isSelected()) // insert only if the user included greeting card.
			SingletonOrder.getInstance().setIsGreetingCard(true);
		else
			SingletonOrder.getInstance().setIsGreetingCard(false);
	}

	@FXML
	void changeToHomeScreen(MouseEvent event) {
		insertGreetingCardIntoOrder();
		ManageScreens.home();
	}

	@FXML
	void includeGreetingCard(MouseEvent event) {
		if (isIncludedCheckBox.isSelected()) {
			System.out.println("I am selected! :)");
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

	// for persisting the state of the screen between transitions
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		boolean wasGreetingCardSelectedBefore = SingletonOrder.getInstance().getIsGreetingCard();
		isIncludedCheckBox.setSelected(wasGreetingCardSelectedBefore);
		enableGreetingCard(!wasGreetingCardSelectedBefore);
		initTextFields();
	}

	private void initTextFields() {
		titleTextField.setText(SingletonOrder.getInstance().getGreetingCardTitle());
		toTextField.setText(SingletonOrder.getInstance().getGreetingCardTo());
		fromTextField.setText(SingletonOrder.getInstance().getGreetingCardFrom());
		greetingCardTextArea.setText(SingletonOrder.getInstance().getGreetingCardContent());
	}
}
