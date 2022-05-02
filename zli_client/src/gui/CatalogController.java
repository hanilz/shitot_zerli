package gui;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import entities.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class CatalogController implements Initializable {

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

	private ObservableList<Product> products = FXCollections.observableArrayList();

	@FXML
	void goToHomeScreen(MouseEvent event) {
		try {
			ClientScreen.changeScene(getClass().getResource("HomeNotLoggedInScreen.fxml"), "Home Screen");
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
			stage.show();
		} catch (IOException e) {
			Logger logger = Logger.getLogger(getClass().getName());
			logger.log(Level.SEVERE, "Failed to create new Window.", e);
		}
	}

}
