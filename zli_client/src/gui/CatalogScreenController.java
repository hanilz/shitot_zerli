package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class CatalogScreenController {

    @FXML
    private Button addProductBtn1;

    @FXML
    private Button addProductBtn11;

    @FXML
    private Button addProductBtn12;

    @FXML
    private Button addProductBtn13;

    @FXML
    private Button addProductBtn14;

    @FXML
    private Button addProductBtn15;

    @FXML
    private ImageView cartImage;

    @FXML
    private GridPane catalogGrid;

    @FXML
    private Label catalogLbl;

    @FXML
    private HBox hbox;

    @FXML
    private ImageView homeImage;

    @FXML
    private ImageView productImage1;

    @FXML
    private ImageView productImage11;

    @FXML
    private ImageView productImage12;

    @FXML
    private ImageView productImage13;

    @FXML
    private ImageView productImage14;

    @FXML
    private ImageView productImage15;

    @FXML
    private Label productLbl1;

    @FXML
    private Label productLbl11;

    @FXML
    private Label productLbl12;

    @FXML
    private Label productLbl13;

    @FXML
    private Label productLbl14;

    @FXML
    private Label productLbl15;

    @FXML
    private Label productLbl16;

    @FXML
    private Label productLbl161;

    @FXML
    private Label productLbl1611;

    @FXML
    private Label productLbl1612;

    @FXML
    private Label productLbl1613;

    @FXML
    private Label productLbl1614;

    @FXML
    private Label productLbl1615;

    @FXML
    void goToHomeScreen(MouseEvent event) {
		try {
			ClientMain.changeScene(getClass().getResource("HomeNotLoggedInScreen.fxml"), "Home Screen");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

}
