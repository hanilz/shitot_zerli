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

	@FXML
	void openProductEditor(ActionEvent event) {
		ProductEditorController.setProductEditorProduct(product);
		ProductEditorController.setManageCatalogController(manageCatalogController);
		ProductEditorController.setProductVBox(this);
		try {
			ManageScreens.openPopupFXML(ProductEditorController.class.getResource("ProductEditorPopup.fxml"),"Product Editor");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void initProduct(ProductsBase product) {
		this.product=product;
		priceLabel.setText(InputChecker.price(product.getPrice()));
		productNameLabel.setText(product.getName());
		productImage.setImage(new Image(product.getImagePath()));
	}

	public void setManageCatalogController(ManageCatalogController manageCatalogController) {
		this.manageCatalogController = manageCatalogController;
	}
	
	public VBox getProductVBox() {
		return productVBox;
	}
	
	public ProductsBase getProduct() {
		return product;
	}

}
