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

public class LandingScreenController implements Initializable{

    @FXML
    private Label landingLabel;

    @FXML
    private Button startShoppingButton;
    
    @FXML
    private ImageView zerliIconImage;

    @FXML
    void clickStartShoppingButton(MouseEvent event) {
    	ManageScreens.changeScreenTo(Screens.CATALOG_SPLASH_SCREEN);
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ArrayList<String> labels = new ArrayList<>();
		labels.add("The most exotic flowers at the tip of your fingers!");
		labels.add("Experience beauty like never before!");
		labels.add("Feels like spring all year long!");
		labels.add("Always rooting for you!");
		labels.add("Flowers don't tell, they show!");
		Random rand = new Random();
		landingLabel.setText(labels.get(Math.abs(rand.nextInt()) % labels.size()));
		RotateTransition rt = new RotateTransition(Duration.millis(2000), zerliIconImage);
		rt.setByAngle(360);
		rt.setCycleCount(Animation.INDEFINITE);
		rt.setInterpolator(Interpolator.LINEAR);
		rt.play();
	}
}
