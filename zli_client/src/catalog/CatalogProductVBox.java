package catalog;

import entities.Cart;
import entities.Product;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.InputChecker;

public class CatalogProductVBox extends CatalogVBox implements ICatalogVBox {
		private Product product; // will be used to get the data from
		private ImageView productIcon;

	public CatalogProductVBox(Product product) {
		this.product = product;
	}

	/**
	 * 
	 */
	public void initVBox() {
		nameLabel.setText(product.getProductName());
		initImageProduct();
		initProductIcon();
		
		amountLabel.setText("" + InputChecker.price(((int) product.getProductPrice())));

		initPriceHBox();

		initAddToCartButton();
		
		super.initVBox();
		this.getChildren().add(productIcon);
		this.getChildren().add(addToCartButton);
	}

	private void initAddToCartButton() {
		addToCartButton.setCursor(Cursor.HAND);
		addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Cart.getInstance().addToProductCart(product, 1, true);
				System.out.println("Cart is: " + Cart.getInstance().getProductCart());
				System.out.println(product.getProductName() + " added to cart woohoooo");
			}
		});
	}

	private void initImageProduct() { //same function but not with the event so we will call super.
		image = new ImageView(product.getImagePath());
		setImageProp();

		image.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				ProductVBox popup = new ProductVBox(product);
				popup.initProductVBox();
				Scene scene = new Scene(popup);
				Stage stage = new Stage();
				stage.setTitle("Product Details - " + product.getProductName());
				stage.setScene(scene);
				stage.showAndWait();
			}
		});
	}
	
	private void initProductIcon() {
		productIcon = new ImageView("/resources/productImages/icon/bouquet_icon.png");
		productIcon.setFitHeight(30);
		productIcon.setFitWidth(30);
		productIcon.setPreserveRatio(true);
	}
}