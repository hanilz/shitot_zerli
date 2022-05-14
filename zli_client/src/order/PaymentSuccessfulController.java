package order;

import entities.Cart;
import entities.SingletonOrder;
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
    	//Empty the cart and the singletonOrder after we inserted the order into db
		Cart.getInstance().emptyCart();
		SingletonOrder.getInstance().emptySingletonOrder();
    	ManageScreens.changeScreenTo(Screens.CATALOG);

    }

}
