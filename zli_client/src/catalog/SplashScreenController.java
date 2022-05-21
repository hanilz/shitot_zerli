package catalog;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import util.ManageData;
import util.ManageScreens;
import util.Screens;

public class SplashScreenController implements Initializable {
	
    @FXML
    private ImageView loadingScreen;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
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
