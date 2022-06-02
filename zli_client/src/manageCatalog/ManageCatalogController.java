package manageCatalog;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import entities.Product;
import entities.ProductsBase;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import util.ManageData;
import util.ManageScreens;
import util.Screens;

public class ManageCatalogController implements Initializable {// might extends
	private ManageCatalogController manageCatalogController;
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
	private TilePane manageTile;

	@FXML
	private TextField searchBar;

	@FXML
	private Button searchButton;

	private HashMap<ProductsBase, ProductVBoxController> productMap = new HashMap<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		manageCatalogController = this;
		manageTile.getChildren().add(addNewProduct());
		loadProductsAndItems();
	}

	private void loadProductsAndItems() {
		for (ProductsBase product : ManageData.products) {
			try {
				manageTile.getChildren().add(loadProduct(product));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		for (ProductsBase item : ManageData.items) {
			try {
				manageTile.getChildren().add(loadProduct(item));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private VBox addNewProduct() {
		VBox newItemVBox = new VBox();
		newItemVBox.setPadding(new Insets(20));
		newItemVBox.setSpacing(20);
		Label title = new Label("Click To Add\nNew Product");
		title.setStyle("-fx-font-size : 16 ; -fx-font-weight : bold");
		newItemVBox.getChildren().add(title);
		newItemVBox.setStyle("-fx-background-color :  FAE1DD ; -fx-background-radius : 20");
		newItemVBox.setAlignment(Pos.TOP_CENTER);
		ImageView image = new ImageView("/resources/catalog/newProduct.png");
		image.setFitHeight(150);
		image.setFitWidth(150);
		image.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				ManageScreens.changeScreenTo(Screens.CREATE_NEW_PRODUCT);
			}
		});
		newItemVBox.getChildren().add(image);
		return newItemVBox;
	}

	@FXML
	void changeToHomeScreen(MouseEvent event) {
		ManageScreens.home();
	}

	@FXML
	void search(MouseEvent event) {

	}

	@SuppressWarnings("unlikely-arg-type")
	public void removeProduct(ProductVBoxController productVBoxController) {
		int i = manageTile.getChildren().indexOf(productVBoxController.getProductVBox());
		System.out.println(i);
		manageTile.getChildren().remove(i);
		productMap.remove(productVBoxController);
		if(productVBoxController.getProduct() instanceof Product)
			ManageData.products.remove(productVBoxController.getProduct());
		else
			ManageData.items.remove(productVBoxController.getProduct());
	}

	private VBox loadProduct(ProductsBase pb) throws IOException {
		FXMLLoader loader = new FXMLLoader(ProductVBoxController.class.getResource("MangaeCatalogVBox.fxml"));
		VBox productVBox = (VBox) loader.load();
		((ProductVBoxController) loader.getController()).initProduct(pb);
		((ProductVBoxController) loader.getController()).setManageCatalogController(manageCatalogController);
		productMap.put(pb, loader.getController());
		return productVBox;

	}
}