package manageCatalog;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import util.ManageData;
import util.ManageScreens;

public class ManageCatalogController implements Initializable {// might extends


    @FXML
    private Button addNewProductButton;

    @FXML
    private VBox catalogVBox;

    @FXML
    private Button filterButton;

    @FXML
    private ScrollPane filterScrollPane;

    @FXML
    private ImageView homeImage;

    @FXML
    private VBox homeVBox;

    @FXML
    private HBox loginHBox;

    @FXML
    private VBox loginVBox;

    @FXML
    private ScrollPane manageScrollPane;

    @FXML
    private TextField searchBar;

    @FXML
    private Button searchButton;

	private GridPane manageGrid;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		manageGrid = ManageData.manageGrid;
		manageScrollPane.setContent(manageGrid);
	}

	@FXML
	void changeToHomeScreen(MouseEvent event) {
		ManageScreens.home();
	}
	
    @FXML
    void changeToAddNewProduct(MouseEvent event) {

    }

	@FXML
	void search(MouseEvent event) {

	}

}
