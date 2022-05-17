package customProduct;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.ClientFormController;
import entities.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import util.Commands;
import util.ManageScreens;
import util.Screens;

public class CustomProductBuilderController implements Initializable {

    @FXML
    private Button addToCartButton;

    @FXML
    private Button backToCatalogButton;

    @FXML
    private CheckBox carnationFilterCheckBox;

    @FXML
    private CheckBox chocolateFilterCheckBox;

    @FXML
    private CheckBox daffodilFilterCheckBox;

    @FXML
    private CheckBox flowerFilterCheckBox;

    @FXML
    private ImageView homeImage;

    @FXML
    private VBox itemSelectorVBox;

    @FXML
    private CheckBox lilyFilterCheckBox;

    @FXML
    private CheckBox orchidFilterCheckBox;

    @FXML
    private CheckBox peonyFilterCheckBox;

    @FXML
    private CheckBox pinklFilterCheckBox;

    @FXML
    private CheckBox redFilterCheckBox;

    @FXML
    private CheckBox roseFilterCheckBox;

    @FXML
    private ScrollPane scrollCatalogPane;

    @FXML
    private CheckBox whiteFilterCheckBox;

    @FXML
    private CheckBox yellowFilterCheckBox;

	private ObservableList<Item> items = FXCollections.observableArrayList();
    
    @FXML
    void addToCart(MouseEvent event) {

    }

    @FXML
    void changeToCatalog(MouseEvent event) {
		ManageScreens.changeScreenTo(Screens.CATALOG);
    }

    @FXML
    void goToHomeScreen(MouseEvent event) {
		ManageScreens.home();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fetchItems();
		initCatalogItemVBoxes();
	}

	private void fetchItems() {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command",Commands.FETCH_ITEMS);
		Object response = ClientFormController.client.accept(message);
		items = (ObservableList<Item>) response;
	}

	private void initCatalogItemVBoxes() {
		for (int i = 0; i < items.size(); i++) {
			CustomProductItemHBox customProductItemHBox = new CustomProductItemHBox(items.get(i));
			customProductItemHBox.initHBox();
			itemSelectorVBox.getChildren().add(customProductItemHBox);
		}
	}	
}
