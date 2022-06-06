package catalog;

import entities.Cart;
import entities.Product;
import entities.ProductsBase;
import inputs.InputChecker;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import util.ManageScreens;

public class CatalogDiscountVBox {
	private Product product;

	@FXML
	private Button addToCartButton;

	@FXML
	private Text beforeDiscountTxt;

	@FXML
	private Label afterDiscountLabel;

	@FXML
	private ImageView productImage;

	@FXML
	private Label productNameLabel;

	@FXML
	private VBox productVBox;

	private double calculateDiscount(double price, double discount) {
		return price - price * discount / 100;
	}

	public void initVBox(Product product) {
		this.product = product;
		beforeDiscountTxt.setText(InputChecker.price(product.getPrice()));
		beforeDiscountTxt.setStrikethrough(true);
		afterDiscountLabel.setText(InputChecker.price(calculateDiscount(product.getPrice(), product.getDiscount())));
		initImageProduct();
		productNameLabel.setText(product.getName());
		initAddToCartButton();
	}

	public ProductsBase getProduct() {
		return product;
	}

	private void initImageProduct() { // same function but not with the event so we will call super.
		productImage.setImage(new Image(product.getImagePath()));

		productImage.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
//				ProductVBox popup = new ProductVBox(product);
//				popup.initProductVBox();
//				Scene scene = new Scene(popup);
//				Stage stage = new Stage();
//				stage.setTitle("Product Details - " + product.getName());
//				stage.setScene(scene);
//				ManageScreens.addPopup(stage);
//				stage.showAndWait();
//				ManageScreens.removePopup(stage);
				try {
					ProductDetailsController.setProduct(product);
					ManageScreens.openPopupFXML(ProductDetailsController.class.getResource("ProductDetailsPopup.fxml"), product.getName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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
				addToCartButton.setText("Added!");
				PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
				pause.setOnFinished(e -> addToCartButton.setText("Add To Cart"));
				pause.play();
			}
		});
	}
}
