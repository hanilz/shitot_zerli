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

public class SplashScreenController implements Initializable {
	
    @FXML
    private ImageView loadingScreen;

    @FXML
    private ImageView zerliIconImage;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		RotateTransition rt = new RotateTransition(Duration.millis(2000), zerliIconImage);
		rt.setByAngle(360);
		rt.setCycleCount(Animation.INDEFINITE);
		rt.setInterpolator(Interpolator.LINEAR);
		rt.play();
		new SplashScreen().start();
	}

	class SplashScreen extends Thread {
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
