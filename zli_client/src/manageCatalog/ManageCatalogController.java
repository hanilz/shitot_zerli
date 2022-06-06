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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import util.ManageData;
import util.ManageScreens;
import util.Screens;

/**
 * This class controllers all the functionality of the manageCatalog. Using this
 * controller, it gives a employee option to edit each product/items in the
 * catalog.
 */
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

	/**
	 * This method initialize the products/items into the screen so the employee can
	 * edit them.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		manageCatalogController = this;
		manageTile.getChildren().add(addNewProduct());
		loadProductsAndItems();
	}

	/**
	 * This method load the product/items to the manage catalog screen.
	 */
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

	/**
	 * This method creates a new vbox for adding items/products to the catalog. If
	 * the employee clicks on the add new product button, It will open a new screen.
	 * @return VBox of new product
	 */
	private VBox addNewProduct() {
		VBox newItemVBox = new VBox();
		newItemVBox.setId("customProductCard");
		Label title = new Label("Click To Add\nNew Product");
		newItemVBox.getChildren().add(title);
		ImageView image = new ImageView("/resources/catalog/newProduct.png");
		image.setFitHeight(150);
		image.setFitWidth(150);
		image.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				ManageScreens.changeScreenTo(Screens.CREATE_NEW_PRODUCT);
			}
		});
		image.setStyle("-fx-cursor: hand;");
		newItemVBox.getChildren().add(image);
		return newItemVBox;
	}

	/**
	 * This method changing the screen to the home screen if the employee clicked on
	 * the button.
	 * @param event
	 */
	@FXML
	void changeToHomeScreen(MouseEvent event) {
		ManageScreens.home();
	}

	@FXML
	void search(MouseEvent event) {

	}

	/**
	 * This method updates the current manage catalog screen if specific
	 * product/items removed from the screen after editing the product.
	 * @param productVBoxController
	 */
	@SuppressWarnings("unlikely-arg-type")
	public void removeProduct(ProductVBoxController productVBoxController) {
		int i = manageTile.getChildren().indexOf(productVBoxController.getProductVBox());
		System.out.println(i);
		manageTile.getChildren().remove(i);
		productMap.remove(productVBoxController);
		if (productVBoxController.getProduct() instanceof Product)
			ManageData.products.remove(productVBoxController.getProduct());
		else
			ManageData.items.remove(productVBoxController.getProduct());
	}

	/**
	 * This method initialize the Vbox for the products/items that we have in the
	 * catalog.
	 * @param pb
	 * @return VBox of product or item.
	 * @throws IOException
	 */
	private VBox loadProduct(ProductsBase pb) throws IOException {
		FXMLLoader loader = new FXMLLoader(ProductVBoxController.class.getResource("MangaeCatalogVBox.fxml"));
		VBox productVBox = (VBox) loader.load();
		((ProductVBoxController) loader.getController()).initProduct(pb);
		((ProductVBoxController) loader.getController()).setManageCatalogController(manageCatalogController);
		productMap.put(pb, loader.getController());
		return productVBox;

	}
}