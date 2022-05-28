package ordersView;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import util.ManageScreens;

public class ViewOrdersController {

    @FXML
    private ImageView homeImage;

    @FXML
    private ScrollPane orderListsScrollPane;

    @FXML
    private ScrollPane orderOverViewScrollPane;

    @FXML
    void returnHome(MouseEvent event) {
    	ManageScreens.home();
    }

}
