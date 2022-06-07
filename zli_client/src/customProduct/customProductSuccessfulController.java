package customProduct;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import util.ManageScreens;
import util.Screens;

/**
 * Popup controller class for displaying after a successful creation and
 * addition of a custom product to the cart.
 */
public class customProductSuccessfulController {

	@FXML
	private Button backToCatalogButton;

	@FXML
	private Button viewCartButton;

	@FXML
	void changeToCart(MouseEvent event) {
		if (ManageScreens.getPopupStage() != null)
			ManageScreens.getPopupStage().close();
		ManageScreens.changeScreenTo(Screens.CART);
	}

	@FXML
	void changeToCatalog(MouseEvent event) {
		if (ManageScreens.getPopupStage() != null)
			ManageScreens.getPopupStage().close();
		ManageScreens.changeScreenTo(Screens.CATALOG);
	}

}
