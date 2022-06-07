package client;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import util.ManageScreens;
import util.Screens;

/**
 * Controller for landing screen - This screen shows a random welcome message to
 * the user after he connects
 */
public class LandingScreenController implements Initializable {

	@FXML
	private Label landingLabel;

	@FXML
	private Button startShoppingButton;

	@FXML
	private ImageView zerliIconImage;

	/**
	 * Change screen to loading screen after click
	 * 
	 * @param event
	 */
	@FXML
	void clickStartShoppingButton(MouseEvent event) {
		ManageScreens.changeScreenTo(Screens.CATALOG_SPLASH_SCREEN);
	}

	/**
	 * initialize the screen - set a list of strings and display one of them and
	 * rotate the icon.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		chooseRandomLabel();
		rotateIcon();
	}

	/**
	 * Rotate the zli icon.
	 */
	private void rotateIcon() {
		RotateTransition rt = new RotateTransition(Duration.millis(2000), zerliIconImage);
		rt.setByAngle(360);
		rt.setCycleCount(Animation.INDEFINITE);
		rt.setInterpolator(Interpolator.LINEAR);
		rt.play();
	}

	/**
	 * set a list of strings and display one of them.
	 */
	private void chooseRandomLabel() {
		ArrayList<String> labels = new ArrayList<>();
		labels.add("The most exotic flowers at the tip of your fingers!");
		labels.add("Experience beauty like never before!");
		labels.add("Feels like spring all year long!");
		labels.add("Always rooting for you!");
		labels.add("Flowers don't tell, they show!");
		Random rand = new Random();
		landingLabel.setText(labels.get(Math.abs(rand.nextInt()) % labels.size()));
	}
}
