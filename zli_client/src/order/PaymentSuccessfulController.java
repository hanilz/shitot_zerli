package order;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import util.ChangeScreen;

public class PaymentSuccessfulController {

    @FXML
    private Button backToCatalogButton;

    @FXML
    void changeToCatalog(MouseEvent event) {
    	if(ChangeScreen. getPopupStage() != null)
    		ChangeScreen. getPopupStage().close();
    	try {
    		ChangeScreen.changeScene(getClass().getResource("../catalog/CatalogScreen.fxml"), "Catalog Screen");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

}
