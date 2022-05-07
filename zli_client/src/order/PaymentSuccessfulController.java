package order;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import util.ManageScreens;

public class PaymentSuccessfulController {

    @FXML
    private Button backToCatalogButton;

    @FXML
    void changeToCatalog(MouseEvent event) {
    	if(ManageScreens. getPopupStage() != null)
    		ManageScreens. getPopupStage().close();
    	try {
    		ManageScreens.changeScene(getClass().getResource("../catalog/CatalogScreen.fxml"), "Catalog Screen");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

}
