package manageCatalog;

import entities.ProductsBase;
import inputs.InputChecker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import util.ManageScreens;

/**
 * This controller helps to init the products/items in the manageCatalog Screen. It
 * displays the current price, the title of the product or the item and the image.
 */
public class ProductVBoxController {
	private ProductsBase product;
	private ManageCatalogController manageCatalogController;

	@FXML
	private ImageView productImage;

	@FXML
	private Button editProductButton;

	@FXML
	private Label priceLabel;

	@FXML
	private Label productNameLabel;

	@FXML
	private VBox productVBox;

	/**
	 * This method will open product/item in Product editor screen as a popup if the
	 * employee wants to edit the product/item in the manageCatalog Screen.
	 * @param ActionEvent
	 */
	@FXML
	void openProductEditor(ActionEvent event) {
		ProductEditorController.setProductEditorProduct(product);
		ProductEditorController.setManageCatalogController(manageCatalogController);
		ProductEditorController.setProductVBox(this);
		try {
			ManageScreens.openPopupFXML(ProductEditorController.class.getResource("ProductEditorPopup.fxml"),
					"Product Editor");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method init the information of the product/item in manageCatalog screen.
	 * @param product
	 */
	public void initProduct(ProductsBase product) {
		this.product = product;
		priceLabel.setText(InputChecker.price(product.getPrice()));
		productNameLabel.setText(product.getName());
		productImage.setImage(new Image(product.getImagePath()));
	}

	/**
	 * This method setting the controller for display the productVBox.
	 * @param manageCatalogController
	 */
	public void setManageCatalogController(ManageCatalogController manageCatalogController) {
		this.manageCatalogController = manageCatalogController;
	}

	/**
	 * This method returns the productVBox to display it in the manageCatalogScreen.
	 * @return productVBox
	 */
	public VBox getProductVBox() {
		return productVBox;
	}

	/**
	 * This method returns the product.
	 * @return product as type ProductsBase
	 */
	public ProductsBase getProduct() {
		return product;
	}

}
