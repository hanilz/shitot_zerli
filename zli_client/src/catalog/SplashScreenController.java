package catalog;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import util.ManageData;
import util.ManageScreens;
import util.Screens;

public class SplashScreenController implements Initializable {

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
			
			ManageScreens.changeScreenTo(Screens.CATALOG);						
		}
	}
}
