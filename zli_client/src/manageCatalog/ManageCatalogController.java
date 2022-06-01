package manageCatalog;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import util.ManageData;
import util.ManageScreens;
import util.Screens;

public class ManageCatalogController implements Initializable {// might extends

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

    @FXML
    private TilePane manageTile;
    
	private GridPane manageGrid;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		manageGrid = ManageData.manageGrid;
//		manageGrid.add(addNewProduct(), 0, 0);
//		//manageScrollPane.setContent(manageGrid);
		manageTile.getChildren().add(addNewProduct());
		manageTile.getChildren().addAll( ManageData.manageVBoxList);
	
	}

	private VBox addNewProduct() {
		VBox newItemVBox = new VBox();
		newItemVBox.setAlignment(Pos.CENTER);
		ImageView image = new ImageView("/resources/catalog/newProduct.png");
		image.setFitHeight(130);
		image.setFitWidth(130);
		image.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				ManageScreens.changeScreenTo(Screens.CREATE_NEW_PRODUCT);
			}

		});
		newItemVBox.getChildren().add(image);
		// TODO Auto-generated method stub
		return newItemVBox;
	}

	@FXML
	void changeToHomeScreen(MouseEvent event) {
		ManageScreens.home();
	}

	@FXML
	void search(MouseEvent event) {

	}

}