package order;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import util.ChangeScreen;
import util.Screens;

public class PaymentSuccessfulController {

    @FXML
    private Button backToCatalogButton;

    @FXML
    void changeToCatalog(MouseEvent event) {
    	if(ChangeScreen. getPopupStage() != null)
    		ChangeScreen. getPopupStage().close();
    	ChangeScreen.to(Screens.CATALOG);
    }

}
