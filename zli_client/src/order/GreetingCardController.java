package order;

import java.net.URL;
import java.util.ResourceBundle;

import entities.SingletonOrder;
import inputs.InputChecker;
import javafx.animation.PauseTransition;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
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
	private TextField toTextField;

	@FXML
	void changeToCartScreen(MouseEvent event) {
		insertGreetingCardIntoOrder();
		ManageScreens.changeScreenTo(Screens.CART);
	}

	@FXML
	void changeToDeliveryScreen(Event event) {
		// first - check if the user filled all the fields for the greeting card if
		// selected.
		// if true -> the controller will appear a label for filling all the fields
		// else -> the user can proceed.
		if (InputChecker.areGreetingCardFieldsEmptyChecker(isIncludedCheckBox.isSelected(), toTextField.getText(),
				greetingCardTextArea.getText())) {
			switchFillAllFieldsLabel(
					"* Please fill 'to' and 'greeting card' fields in order to proceed with the order!");
		} else if (isIncludedCheckBox.isSelected()
				&& !InputChecker.isGreetingCardInputVaild(fromTextField.getText(), toTextField.getText())) {
			switchFillAllFieldsLabel("* Please check the input of the greeting card!");
		} else if (isIncludedCheckBox.isSelected() && !InputChecker.isGreetingCardInputNotLong(fromTextField.getText(),
				toTextField.getText(), greetingCardTextArea.getText())) {
			switchFillAllFieldsLabel("* Please check the length of the greeting card!");
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
		SingletonOrder.getInstance().setGreetingCardFields(fromTextField.getText(), toTextField.getText(),
				greetingCardTextArea.getText());
		if (isIncludedCheckBox.isSelected()) // insert only if the user included greeting card.
			SingletonOrder.getInstance().setIsGreetingCard(true);
		else
			SingletonOrder.getInstance().setIsGreetingCard(false);
	}

	@FXML
	void changeToHomeScreen(Event event) {
		insertGreetingCardIntoOrder();
		ManageScreens.home();
	}

	@FXML
	void includeGreetingCard(MouseEvent event) {
		if (isIncludedCheckBox.isSelected())
			enableGreetingCard(false);
		else
			enableGreetingCard(true);

	}

	private void enableGreetingCard(boolean setOption) {
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
		toTextField.setText(SingletonOrder.getInstance().getGreetingCardTo());
		fromTextField.setText(SingletonOrder.getInstance().getGreetingCardFrom());
		greetingCardTextArea.setText(SingletonOrder.getInstance().getGreetingCardContent());
		setAdvanceBehaviour(toTextField);
		setAdvanceBehaviour(fromTextField);
	}

	private void setAdvanceBehaviour(Node node) {
		node.setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.ENTER)
				changeToDeliveryScreen(event);
			else if (event.getCode() == KeyCode.ESCAPE)
				changeToHomeScreen(event);
		});
	}
}
