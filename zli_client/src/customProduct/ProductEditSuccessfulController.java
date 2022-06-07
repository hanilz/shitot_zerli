package customProduct;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import util.ManageScreens;
import util.Screens;

/**
 * Popup controller class for displaying after a successful edit of a
 * product/custom product.
 */
public class ProductEditSuccessfulController {

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
