package order;

import entities.Cart;
import entities.SingletonOrder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;
import util.ManageScreens;
import util.Screens;

public class PaymentSuccessfulController{

    @FXML
    private Button backToCatalogButton;
    
    @FXML
    private Button viewOrdersButton;

    @FXML
    void changeToCatalog(MouseEvent event) {
    	returnToCatalog();
    }


	public static void returnToCatalog() {
		if(ManageScreens. getPopupStage() != null)
    		ManageScreens. getPopupStage().close();
    	//Empty the cart and the singletonOrder after we inserted the order into db
		Cart.getInstance().emptyCart();
		SingletonOrder.getInstance().emptySingletonOrder();
    	ManageScreens.changeScreenTo(Screens.CATALOG);
	}

    
    @FXML
    void openViewOrdersScreen(ActionEvent event) {
		if(ManageScreens. getPopupStage() != null)
    		ManageScreens. getPopupStage().close();
    	ManageScreens.changeScreenTo(Screens.VIEW_ORDERS_CUSTOMER);
    }
  
}
