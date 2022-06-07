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

/**
 * Component for editing a customProduct's items and products in the cart.
 */
public class CartProductOverviewEditVBox extends CartProductOverviewVBox {
	private Button editCustomProductButton = new Button("Save Changes");
	private Button editCustomProductInBuilderButton = new Button("Edit Product");
	private HBox editButtonsHBox = new HBox();
	private double price;
	private double discountPrice;

	public CartProductOverviewEditVBox(Product product) {
		super(product);
		price = product.getPrice();
		discountPrice = product.calculateDiscount();
	}

	public void initVBox() {
		super.initVBox();

		initItemsHBoxes();

		initProductsHBoxes();

		super.initTotalPriceHBox();

		productImage.setFitHeight(100); // custom product image isn't that important

		initEditButtonsHBox();

		this.getChildren().add(editButtonsHBox);
		totalPriceLabel.setText("Custom Product Price:");
	}

	/**
	 * Sets the edit buttons behavior.
	 */
	private void initEditButtonsHBox() {
		editButtonsHBox.getChildren().add(editCustomProductButton);
		editCustomProductButton.setOnAction(new EventHandler<ActionEvent>() {
			/**
			 * Get all the products and items in this vbox, save them in a new
			 * CustomProduct, and replace that customProduct with the original
			 * customProduct, which is in the cart.
			 */
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
			/**
			 * Go to custom product builder with the items and products of this
			 * customProduct
			 * 
			 * @param e
			 */
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

	/**
	 * Set the products of this customProduct as HBoxes in the ScrollPane.
	 */
	private void initProductsHBoxes() {
		CustomProduct customProduct = (CustomProduct) product;

		for (Product currentProduct : customProduct.getProducts().keySet()) {
			ProductOverviewEditHBox productOverviewHBox = new ProductOverviewEditHBox(currentProduct, this,
					customProduct.getProducts().get(currentProduct));
			productOverviewHBox.initHBox();
			itemsProductsVBox.getChildren().add(productOverviewHBox);
		}
	}

	/**
	 * Set the items of this customProduct as HBoxes in the ScrollPane.
	 */
	private void initItemsHBoxes() {
		for (Item currentItem : product.getItems().keySet()) {
			ProductOverviewEditHBox productOverviewHBox = new ProductOverviewEditHBox(currentItem, this,
					product.getItems().get(currentItem));
			productOverviewHBox.initHBox();
			itemsProductsVBox.getChildren().add(productOverviewHBox);
		}
	}

	/**
	 * Behavior for removing a HBox from the scrollpane - update the price.
	 * 
	 * @param productOverviewHBox
	 */
	public void removeHBoxFromOverview(ProductOverviewEditHBox productOverviewHBox) {
		for (Node currentNode : itemsProductsVBox.getChildren()) {
			if (currentNode instanceof ProductOverviewEditHBox) {
				ProductOverviewEditHBox currentProductOverviewHBox = (ProductOverviewEditHBox) currentNode;
				if (currentProductOverviewHBox == productOverviewHBox) {
					addToTotalPrice(
							-currentProductOverviewHBox.getProduct().getPrice()
									* currentProductOverviewHBox.getQuantity(),
							-currentProductOverviewHBox.getProduct().calculateDiscount()
									* currentProductOverviewHBox.getQuantity());
					itemsProductsVBox.getChildren().remove(currentProductOverviewHBox);
					break;
				}
			}
		}
	}

	/**
	 * Update price and discountPrice labels.
	 * @param delta
	 * @param discountDelta
	 */
	public void addToTotalPrice(double delta, double discountDelta) {
		price += delta;
		originalPriceTxt.setText(InputChecker.price(price));
		discountPrice += discountDelta;
		TotalPriceDiscountLabel.setText(InputChecker.price(discountPrice));
	}
}
