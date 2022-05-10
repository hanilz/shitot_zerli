package order;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import util.ManageScreens;
import util.Screens;

public class PaymentSuccessfulController {

    @FXML
    private Button backToCatalogButton;

    @FXML
    void changeToCatalog(MouseEvent event) {
    	if(ManageScreens. getPopupStage() != null)
    		ManageScreens. getPopupStage().close();
    	ManageScreens.changeScreenTo(Screens.CATALOG);

    }

}
