package catalog;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import cart.ProductOverviewNoEditHBox;
import entities.Item;
import entities.Product;
import inputs.InputChecker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import util.ManageScreens;

/**
 * Controller class for product details popup.
 */
public class ProductDetailsController implements Initializable {
	private static Product product;

	private static HashMap<Product, Integer> products = new HashMap<>();

	@FXML
	private Button closeButton;

	@FXML
	private Label flowerTypeLabel;

	@FXML
	private VBox productContentVBox;

	@FXML
	private Label productDescriptionlabel;

	@FXML
	private Label productNameLabel;

	@FXML
	private Label productPriceLabel;

	@FXML
	private Label productTypeLabel;

	@FXML
	private ImageView productImage;

	/**
	 * Set behavior of close button.
	 */
	@FXML
	void closePopup() {
		if (ManageScreens.getPopupStage() != null) {
			products.clear();
			ManageScreens.getPopupStage().close();
		}
	}

	/**
	 * Initialize popup based on given product.
	 */
	public void initProduct() {
		productImage.setImage(new Image(product.getImagePath()));
		productTypeLabel.setText(product.getType());
		productNameLabel.setText(product.getName());
		productPriceLabel.setText(InputChecker.price(product.getPrice()));
		flowerTypeLabel.setText(product.getFlowerType());
		productDescriptionlabel.setText(product.getProductDescription());
		initItems();
	}

	/**
	 * Initialize items and products, if there are any, in the VBox.
	 */
	private void initItems() {
		if (products != null) {
			for (Product productRow : products.keySet()) {
				ProductOverviewNoEditHBox itemHBox = new ProductOverviewNoEditHBox(productRow,
						products.get(productRow));
				itemHBox.initHBox();
				productContentVBox.getChildren().add(itemHBox);
			}
			products.clear();
		}
		for (Item item : product.getItems().keySet()) {
			ProductOverviewNoEditHBox itemHBox = new ProductOverviewNoEditHBox(item, product.getItems().get(item));
			itemHBox.initHBox();
			productContentVBox.getChildren().add(itemHBox);
		}
	}

	/**
	 * Set the current product.
	 * @param product
	 */
	public static void setProduct(Product product) {
		ProductDetailsController.product = product;
	}

	/**
	 * Set the current custom product(Hashmap of products).
	 * @param product
	 */
	public static void setCustomProduct(HashMap<Product, Integer> products) {
		ProductDetailsController.products.clear();
		ProductDetailsController.products.putAll(products);
	}

	/**
	 * Initialize the popup.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initProduct();
	}
}
