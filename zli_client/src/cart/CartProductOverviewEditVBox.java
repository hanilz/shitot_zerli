package cart;

import entities.CustomProduct;
import entities.Item;
import entities.Product;
import entities.ProductsBase;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class CartProductOverviewEditVBox extends CartProductOverviewVBox {
	private VBox productsItemsVBox = new VBox();  // used to store all Products and Items HBoxes
	
	public CartProductOverviewEditVBox(Product product) {
		super(product);
	}

	public void initVBox() {
		super.initVBox();
		
		initProductsHBoxes();
		
		initItemsHBoxes();
		
		this.getChildren().add(productsItemsVBox);
		
		super.initTotalPrice();
	}
	
	private void initProductsHBoxes() {
		CustomProduct customProduct = (CustomProduct) product;
		
		for(Product currentProduct : customProduct.getProducts().keySet()) {
			ProductOverviewEditHBox productOverviewHBox = new ProductOverviewEditHBox(currentProduct, this, customProduct.getProducts().get(currentProduct));
			productOverviewHBox.initHBox();
			productsItemsVBox.getChildren().add(productOverviewHBox);
		}
	}
	
	private void initItemsHBoxes() {
		for(Item currentItem : product.getItems().keySet()) {
			ProductOverviewEditHBox productOverviewHBox = new ProductOverviewEditHBox(currentItem, this, product.getItems().get(currentItem));
			productOverviewHBox.initHBox();
			productsItemsVBox.getChildren().add(productOverviewHBox);
		}
	}	
	
	public void removeHBoxFromOverview(ProductOverviewEditHBox productOverviewHBox) {
		for(Node currentNode : productsItemsVBox.getChildren()) {
			if(currentNode instanceof ProductOverviewEditHBox) {
				ProductOverviewEditHBox currentProductOverviewHBox = (ProductOverviewEditHBox) currentNode;
				if(currentProductOverviewHBox == productOverviewHBox) {
					
					
				}
			}
		}
	}
}
