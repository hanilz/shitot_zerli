package cart;

import customProduct.CustomProductHBox;
import entities.ProductsBase;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ProductOverviewNoEditHBox extends CustomProductHBox {
	private int quantity = 1;
	private HBox quantityHBox = new HBox();
	private Label quantityLabel;

	public ProductOverviewNoEditHBox(ProductsBase product, int quantity) {
		super(product);
		this.quantity = quantity;
	}

	public void initHBox() {
		super.initHBox();

		initQuantityVBox();
		priceHBox.setSpacing(5);
		priceHBox.getChildren().add(quantityHBox);
	}

	private void initQuantityVBox() {
		quantityLabel = new Label("" + quantity);
		
		quantityHBox.setAlignment(Pos.CENTER);
		quantityHBox.setSpacing(10);	
		
		quantityHBox.getChildren().add(quantityLabel);
	}

	public int getQuantity() {
		return quantity;
	}
}
