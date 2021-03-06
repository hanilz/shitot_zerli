package cart;

import customProduct.CustomProductHBox;
import entities.ProductsBase;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * Component class for displaying items of a product.
 */
public class ProductOverviewNoEditHBox extends CustomProductHBox {
	private int quantity = 1;
	private Label quantityLabel = new Label("Quantity:");
	private Label quantityAmountLabel;
	private VBox quantityVBox = new VBox(quantityLabel);
	

	public ProductOverviewNoEditHBox(ProductsBase product, int quantity) {
		super(product);
		this.quantity = quantity;
	}

	/**
	 * Display image, name, catID and quantity in the HBox.
	 */
	public void initHBox() {
		this.setId("noEditHBox");
		super.initHBox();
		super.initPriceHBox();
		
		priceHBox.getChildren().remove(priceVBox);
		nameLabel.setPrefWidth(190);

		nameLabel.setId("NameLabel");
		initQuantityVBox();
		priceHBox.setSpacing(5);
		priceHBox.getChildren().add(quantityVBox);
	}

	private void initQuantityVBox() {
		quantityAmountLabel = new Label("" + quantity);
		quantityAmountLabel.setFont(new Font(18));
		quantityVBox.setAlignment(Pos.CENTER);
		quantityVBox.setSpacing(10);	
		
		quantityVBox.getChildren().add(quantityAmountLabel);
	}

	/**
	 * @return quantity
	 */
	public int getQuantity() {
		return quantity;
	}
}
