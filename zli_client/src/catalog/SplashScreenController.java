package catalog;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import util.ManageData;
import util.ManageScreens;
import util.Screens;

/**
 * Controller for loading screen - This screen is used for loading all the major
 * screens in the application.
 */
public class SplashScreenController implements Initializable {

	@FXML
	private ImageView loadingScreen;

	@FXML
	private ImageView zerliIconImage;

	/**
	 * Set up the screen - Load all data and make the zli icon rotate.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rotateZliIcon();
		new ZliLoader().start();
	}

	/**
	 * Rotation animation for the zli icon.
	 */
	private void rotateZliIcon() {
		RotateTransition rt = new RotateTransition(Duration.millis(2000), zerliIconImage);
		rt.setByAngle(360);
		rt.setCycleCount(Animation.INDEFINITE);
		rt.setInterpolator(Interpolator.LINEAR);
		rt.play();
	}

	/**
	 * A thread for loading all the major data and components in our application.
	 */
	class ZliLoader extends Thread {
		@Override
		public void run() {
			ManageData.fetchAllProducts();
			ManageData.fetchAllItems();
			ManageData.initCatalogGrid();
			ManageData.initCustomProductItemHBoxes();
			ManageData.initCustomProductProductsHBoxes();
			ManageScreens.changeScreenTo(Screens.CATALOG);
		}
	}
}
