package home;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import util.ManageClients;
import util.ManageScreens;
import util.Screens;

/**
 * Guest Home Screen - Allowing to Exit And To Start A Purchase
 *
 */
public class HomeGuestController{

	/**
	 * Reset flow(cart,catalog,home) of login,loading login popup to login in from home
	 */
	@FXML
	void changeToLoginScreen(MouseEvent event) {
		LoginScreenController.resetLogin();
		ManageScreens.changeScreenTo(Screens.LOGIN);
	}

	/**
	 * Change screen to catalog screen
	 */
	@FXML
	void changeToCatalogScreen(MouseEvent event) {
		ManageScreens.changeScreenTo(Screens.CATALOG);
	}

	/**
	 * Clicking on exit disconnect from eerver and close the screen
	 */
	@FXML
	void exitApplication(MouseEvent event) {
		ManageClients.exitClient();
		System.exit(0);
	}

}
