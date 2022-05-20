package catalog;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import util.ManageData;
import util.ManageScreens;
import util.Screens;

public class CatalogController implements Initializable {

    @FXML
    private ImageView cartImage;

    @FXML
    private VBox cartVBox;

    @FXML
    private Label catalogLbl;

    @FXML
    private ScrollPane catalogScrollPane;

    @FXML
    private VBox catalogVBox;

    @FXML
    private Button filterButton;

    @FXML
    private ImageView homeImage;

    @FXML
    private VBox homeVBox;

    @FXML
    private HBox loginHBox;

    @FXML
    private TextField searchBar;

    @FXML
    private Button searchButton;
    
    @FXML
    private ScrollPane filterScrollPane;

	private GridPane catalogGrid = ManageData.catalogGrid;

	@FXML
	void changeToCartScreen(MouseEvent event) {
		ManageScreens.changeScreenTo(Screens.CART);
	}

	@FXML
	void changeToHomeScreen(MouseEvent event) {
		ManageScreens.home();
	}

	@FXML
	void openLoginPopup(MouseEvent event) {

	}

	@FXML
	void search(MouseEvent event) {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		catalogGrid = ManageData.catalogGrid;
		catalogScrollPane.setContent(catalogGrid);
	}
}
