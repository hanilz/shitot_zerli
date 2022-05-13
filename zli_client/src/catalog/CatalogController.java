package catalog;


import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import client.ClientFormController;
import entities.Item;
import entities.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.ManageScreens;
import util.Screens;

public class CatalogController implements Initializable {

	@FXML
	private ImageView cartImage;

	@FXML
	private Label catalogLbl;

	@FXML
	private HBox hbox;

	@FXML
	private ImageView homeImage;

	@FXML
	private ScrollPane scrollCatalogPane;

	private ObservableList<Product> products = FXCollections.observableArrayList();

	private ObservableList<Item> items = FXCollections.observableArrayList();
	
    @FXML
    private GridPane catalogGrid;

	private VBox[] productVBoxList;
	private CatalogItemVBox[] itemVBoxList;
	
	@FXML
	void goToHomeScreen(MouseEvent event) {
		ManageScreens.home();
	}

    @FXML
    void changeToCartScreen(MouseEvent event) {
    	ManageScreens.changeScreenTo(Screens.CART);
    }
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// first - we will fetch all the products and items from the db
		fetchProducts();
		fetchItems();
		initCatalogProductVBoxes();
		initCatalogItemVBoxes();
		initGrid();
	}

	private void fetchItems() {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", "fetch items");
		Object response = ClientFormController.client.accept(message);
		items = (ObservableList<Item>) response;
	}

	private void fetchProducts() {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", "fetch products");
		Object response = ClientFormController.client.accept(message);
		products = (ObservableList<Product>) response;
	}

	private void initGrid() {
		int numberOfRows = (int) Math.ceil(products.size()/3.0);
        for (int i = 0; i < numberOfRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / numberOfRows);
            catalogGrid.getRowConstraints().add(rowConst);         
        }		
		for (int i = 0; i < productVBoxList.length; i++) {
				catalogGrid.add(productVBoxList[i], i%3, i/3);
		}
	}

	private void initCatalogItemVBoxes() {
		itemVBoxList = new CatalogItemVBox[items.size()];
		for (int i = 0; i < items.size(); i++) {
			CatalogItemVBox itemVBox = new CatalogItemVBox(items.get(i));
			itemVBox.initVBox();
			itemVBoxList[i] = itemVBox;
		}
	}

	private void initCatalogProductVBoxes() {
		productVBoxList = new CatalogProductVBox[products.size()];
		for (int i = 0; i < products.size(); i++) {
			CatalogProductVBox catalogVBox = new CatalogProductVBox(products.get(i));
			catalogVBox.initVBox();
			productVBoxList[i] = catalogVBox;
		}
	}
	
	@FXML
	void openProductDetails(MouseEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("ProductDetailsPopup.fxml"));
			Scene scene = new Scene(fxmlLoader.load(), 456, 595);
			Stage stage = new Stage();
			stage.setTitle("New Window");
			stage.setScene(scene);
			stage.showAndWait();
			// productImage1.setDisable(false);
		} catch (IOException e) {
			Logger logger = Logger.getLogger(getClass().getName());
			logger.log(Level.SEVERE, "Failed to create new Window.", e);
		}
	}

}
