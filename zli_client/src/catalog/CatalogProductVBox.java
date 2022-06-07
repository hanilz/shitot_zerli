package catalog;

import entities.Cart;
import entities.Product;
import inputs.InputChecker;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Duration;
import util.ManageScreens;

/**
 * Component for the catalog - Display product details, price and discount.
 */
public class CatalogProductVBox extends CatalogVBox implements ICatalogVBox {
	private Product product;  // will be used to get the data from
	private Text originalPriceTxt;

	/**
	 * Constructor.
	 * @param product
	 */
	public CatalogProductVBox(Product product) {
		this.product = product;
	}

	/**
	 * Initialize the productVBox, using the product.
	 * Set all the relevant information and node behavior for the component.
	 */
	public void initVBox() {
		nameLabel.setText(product.getName());
		
		initImageProduct();

		amountLabel.setText(InputChecker.price(product.getPrice()));
			
		initPriceHBox();

		initAddToCartButton();

		super.initVBox();
		
		initDiscountLabel();
		
		this.getChildren().add(addToCartButton);
	}

	/**
	 * Initialize discount label if there is discount for this product
	 */
	private void initDiscountLabel() {
		if(product.isDiscount()) {
			discountLabel.setText(InputChecker.price(product.calculateDiscount()));
			originalPriceTxt = new Text(InputChecker.price(product.getPrice()));
			originalPriceTxt.setStrikethrough(true);
			originalPriceTxt.setStyle("-fx-font-size: 16px;");
			priceHBox.getChildren().remove(amountLabel);
			priceHBox.getChildren().add(originalPriceTxt);
			priceHBox.getChildren().add(discountLabel);
			amountLabel.setId("priceAfterDiscountLabel");;
		}
	}

	/**
	 * Initialize the addToCartButton behavior.
	 */
	private void initAddToCartButton() {
		addToCartButton.setCursor(Cursor.HAND);
		addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Cart.getInstance().addToCart(product, 1, true);
				System.out.println("Cart is: " + Cart.getInstance().getCart());
				System.out.println(product.getName() + " added to cart woohoooo");
				CatalogController.refreshTotalAmountInCart();
				addToCartButton.setText("Product Added To Cart!");
				PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
				pause.setOnFinished(e -> addToCartButton.setText("Add To Cart"));
				pause.play();
			}
		});
	}

	/**
	 * Initialize the image and it's behavior.
	 */
	private void initImageProduct() {
		image = new ImageView(product.getImagePath());
		setImageProp();

		image.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				try {
					ProductDetailsController.setProduct(product);
					ManageScreens.openPopupFXML(ProductDetailsController.class.getResource("ProductDetailsPopup.fxml"), product.getName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
