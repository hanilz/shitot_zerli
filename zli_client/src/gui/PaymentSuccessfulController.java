package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class PaymentSuccessfulController {

    @FXML
    private Button backToCatalogButton;

    @FXML
    void changeToCatalog(MouseEvent event) {
    	if(ClientScreen.popupStage != null)
    		ClientScreen.popupStage.close();
    	try {
			ClientScreen.changeScene(getClass().getResource("CatalogScreen2.fxml"), "Catalog Screen");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

}
