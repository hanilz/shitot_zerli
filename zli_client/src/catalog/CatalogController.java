package catalog;


import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import client.ClientFormController;
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
import javafx.stage.Stage;
import util.ManageScreens;

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

    @FXML
    private GridPane catalogGrid;

	private CatalogVBox[] productVBoxList;

	
	@FXML
	void goToHomeScreen(MouseEvent event) {
		try {
			ManageScreens.changeScene(getClass().getResource("../home/HomeGuestScreen.fxml"), "Home Screen");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    @FXML
    void changeToCartScreen(MouseEvent event) {
		try {
			ManageScreens.changeScene(getClass().getResource("../order/CartScreen.fxml"), "Cart Screen");
		} catch (Exception e) {
			e.printStackTrace();
		}

    }
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// first - we will fetch all the orders from the db
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", "fetch products");
		Object response = ClientFormController.client.accept(message);
		products = (ObservableList<Product>) response;
		initCatalogVBoxes();
		initGrid();
		//scrollCatalogPane.setContent(catalogGrid);
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

	private void initCatalogVBoxes() {
		productVBoxList = new CatalogVBox[products.size()];
		for (int i = 0; i < products.size(); i++) {
			CatalogVBox catalogVBox = new CatalogVBox(products.get(i));
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
