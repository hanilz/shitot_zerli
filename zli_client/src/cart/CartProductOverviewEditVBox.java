package cart;

import java.util.HashMap;

import entities.Cart;
import entities.CustomProduct;
import entities.Item;
import entities.Product;
import entities.ProductsBase;
import inputs.InputChecker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import util.ManageScreens;
import util.Screens;

public class CartProductOverviewEditVBox extends CartProductOverviewVBox {
	private Button editCustomProductButton = new Button("Save Changes");
	private Button editCustomProductInBuilderButton = new Button("Edit Product");
	private HBox editButtonsHBox = new HBox();
	private double price;

	public CartProductOverviewEditVBox(Product product) {
		super(product);
		price = product.getPrice();
	}

	public void initVBox() {
		super.initVBox();

		initItemsHBoxes();

		initProductsHBoxes();

		super.initTotalPriceHBox();

		productImage.setFitHeight(100); // custom product image isn't that important
		
		initEditButtonsHBox();

		this.getChildren().add(editButtonsHBox);
	}

	
	
	private void initEditButtonsHBox() {
		editButtonsHBox.getChildren().add(editCustomProductButton);
		editCustomProductButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				HashMap<Item, Integer> items = new HashMap<>();
				HashMap<Product, Integer> products = new HashMap<>();
				for (Node currentNode : itemsProductsVBox.getChildren()) {
					if (currentNode instanceof ProductOverviewEditHBox) {
						ProductOverviewEditHBox currentProductOverviewHBox = (ProductOverviewEditHBox) currentNode;
						ProductsBase currentProductsBase = currentProductOverviewHBox.getProduct();
						if (currentProductsBase instanceof Product) {
							Product currentProduct = (Product) currentProductsBase;
							products.put(currentProduct, currentProductOverviewHBox.getQuantity());
						} else {
							Item currentItem = (Item) currentProductsBase;
							items.put(currentItem, currentProductOverviewHBox.getQuantity());
						}
					}
				}
				CustomProduct editedCustomProduct = new CustomProduct(0, product.getName(), "Custom", price, "Custom",
						"/resources/catalog/customProductImage.png", items, products);
				Cart.getInstance().removeFromCart(product);
				Cart.getInstance().addToCart(editedCustomProduct, 1, true);
				System.out.println("Edited custom product WOOHOOOOO");
				CartController.connectionWithCartHBox("refresh cart");
				product = editedCustomProduct;
			}
		});

		editCustomProductInBuilderButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Cart.getInstance().setProductToEdit(product);
				ManageScreens.changeScreenTo(Screens.CUSTOM_PRODUCT_BUILDER);
			}
		});

		editButtonsHBox.getChildren().add(editCustomProductInBuilderButton);
		editButtonsHBox.setAlignment(Pos.CENTER);
		editButtonsHBox.setSpacing(20);
	}

	private void initProductsHBoxes() {
		CustomProduct customProduct = (CustomProduct) product;

		for (Product currentProduct : customProduct.getProducts().keySet()) {
			ProductOverviewEditHBox productOverviewHBox = new ProductOverviewEditHBox(currentProduct, this,
					customProduct.getProducts().get(currentProduct));
			productOverviewHBox.initHBox();
			itemsProductsVBox.getChildren().add(productOverviewHBox);
		}
	}

	private void initItemsHBoxes() {
		for (Item currentItem : product.getItems().keySet()) {
			ProductOverviewEditHBox productOverviewHBox = new ProductOverviewEditHBox(currentItem, this,
					product.getItems().get(currentItem));
			productOverviewHBox.initHBox();
			itemsProductsVBox.getChildren().add(productOverviewHBox);
		}
	}

	public void removeHBoxFromOverview(ProductOverviewEditHBox productOverviewHBox) {
		for (Node currentNode : itemsProductsVBox.getChildren()) {
			if (currentNode instanceof ProductOverviewEditHBox) {
				ProductOverviewEditHBox currentProductOverviewHBox = (ProductOverviewEditHBox) currentNode;
				if (currentProductOverviewHBox == productOverviewHBox) {
					addToTotalPrice(-currentProductOverviewHBox.getPrice());
					itemsProductsVBox.getChildren().remove(currentProductOverviewHBox);
					break;
				}
			}
		}
	}

	public void addToTotalPrice(double delta) {
		price += delta;
		TotalPriceAmountLabel.setText(InputChecker.price(price));
	}
}
