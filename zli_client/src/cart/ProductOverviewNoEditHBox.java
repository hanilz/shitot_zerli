package cart;

import customProduct.CustomProductHBox;
import entities.ProductsBase;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ProductOverviewNoEditHBox extends CustomProductHBox {
	private int quantity = 1;
	private Label quantityLabel = new Label("Quantity:");
	private Label quantityAmountLabel;
	private VBox quantityVBox = new VBox(quantityLabel);
	

	public ProductOverviewNoEditHBox(ProductsBase product, int quantity) {
		super(product);
		this.quantity = quantity;
	}

	public void initHBox() {
		super.initHBox();
		nameLabel.setPrefWidth(170);

		initQuantityVBox();
		priceHBox.setSpacing(5);
		priceHBox.getChildren().add(quantityVBox);
	}

	private void initQuantityVBox() {
		quantityAmountLabel = new Label("" + quantity);
		
		quantityVBox.setAlignment(Pos.CENTER);
		quantityVBox.setSpacing(10);	
		
		quantityVBox.getChildren().add(quantityAmountLabel);
	}

	public int getQuantity() {
		return quantity;
	}
}
