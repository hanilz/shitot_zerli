package cart;

import entities.Item;
import entities.Product;
import javafx.scene.layout.VBox;

public class CartProductOverviewNoEditVBox extends CartProductOverviewVBox {
	private VBox productsItemsVBox = new VBox(); // used to store all Products and Items HBoxes

	public CartProductOverviewNoEditVBox(Product product) {
		super(product);
	}

	@Override
	public void initVBox() {
		super.initVBox();

		initItemsHBoxes();

		this.getChildren().add(productsItemsVBox);
		
		super.initTotalPrice();
	}

	private void initItemsHBoxes() {
		for (Item currentItem : product.getItems().keySet()) {
			ProductOverviewNoEditHBox productOverviewHBox = new ProductOverviewNoEditHBox(currentItem, product.getItems().get(currentItem));
			productOverviewHBox.initHBox();
			productsItemsVBox.getChildren().add(productOverviewHBox);
		}
	}
}
