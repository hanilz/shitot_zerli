package catalog;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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

	private ArrayList<CatalogVBox> catalogVBoxList = new ArrayList<>();
	
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
		int numberOfRows = (int) Math.ceil((products.size() + items.size())/3.0);
        for (int i = 0; i < numberOfRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / numberOfRows);
            catalogGrid.getRowConstraints().add(rowConst);         
        }		
		for (int i = 0; i < catalogVBoxList.size(); i++) {
				catalogGrid.add(catalogVBoxList.get(i), i%3, i/3);
		}
		catalogGrid.setVgap(20);
	}

	private void initCatalogItemVBoxes() {
		for (int i = 0; i < items.size(); i++) {
			CatalogItemVBox catalogItemVBox = new CatalogItemVBox(items.get(i));
			catalogItemVBox.initVBox();
			catalogVBoxList.add(catalogItemVBox);
		}
	}

	private void initCatalogProductVBoxes() {
		for (int i = 0; i < products.size(); i++) {
			CatalogProductVBox catalogProductVBox = new CatalogProductVBox(products.get(i));
			catalogProductVBox.initVBox();
			catalogVBoxList.add(catalogProductVBox);
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
