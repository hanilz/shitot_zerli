package catalog;

import entities.Cart;
import entities.Product;
import inputs.InputChecker;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.ManageScreens;

public class CatalogProductVBox extends CatalogVBox implements ICatalogVBox {
	private Product product; // will be used to get the data from

	public CatalogProductVBox(Product product) {
		this.product = product;
	}

	/**
	 * 
	 */
	public void initVBox() {
		nameLabel.setText(product.getName());
		initImageProduct();

		amountLabel.setText("" + InputChecker.price((product.getPrice())));

		initPriceHBox();

		initAddToCartButton();

		super.initVBox();
		this.getChildren().add(addToCartButton);
	}

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

	private void initImageProduct() { // same function but not with the event so we will call super.
		image = new ImageView(product.getImagePath());
		setImageProp();

		image.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				ProductVBox popup = new ProductVBox(product);
				popup.initProductVBox();
				Scene scene = new Scene(popup);
				Stage stage = new Stage();
				stage.setTitle("Product Details - " + product.getName());
				stage.setScene(scene);
				ManageScreens.addPopup(stage);
				stage.showAndWait();
				ManageScreens.removePopup(stage);
			}
		});
	}
}
