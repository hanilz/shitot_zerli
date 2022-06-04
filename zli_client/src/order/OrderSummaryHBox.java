package order;

import catalog.ProductDetailsController;
import catalog.ProductVBox;
import entities.CustomProduct;
import entities.Product;
import entities.ProductsBase;
import inputs.InputChecker;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import util.ManageScreens;

public class OrderSummaryHBox extends HBox {
	private ImageView image;

	private Label priceLabel = new Label();

	private Label quantityLabel = new Label();

	private ProductsBase product;

	private int quantity;

	public OrderSummaryHBox(ProductsBase product, int quantity) {
		this.product = product;
		this.quantity = quantity;
	}

	public void initHBox() {
		this.setAlignment(Pos.CENTER);
		this.setSpacing(15);

		initImageProduct();

		quantityLabel.setText("" + quantity);

		priceLabel.setFont(new Font(20));
		priceLabel.setText(InputChecker.price(product.getPrice()));

		this.getChildren().add(image);
		this.getChildren().add(quantityLabel);
		this.getChildren().add(new Label("X"));
		this.getChildren().add(priceLabel);
	}

	private void initImageProduct() {
		image = new ImageView(product.getImagePath());
		image.setFitHeight(80);
		image.setFitWidth(70);
		image.setPreserveRatio(true);
		setImageProductEvent();
	}

	private void setImageProductEvent() {
		image.setCursor(Cursor.HAND);
		image.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				if(product instanceof Product)
					try {
						ProductDetailsController.setProduct((Product)product);
						if(product instanceof CustomProduct)
							ProductDetailsController.setCustomProduct(((CustomProduct)product).getProducts());
						ManageScreens.openPopupFXML(ProductDetailsController.class.getResource("ProductDetailsPopup.fxml"), product.getName());
					} catch (Exception e) {
						e.printStackTrace();
					}
				else {
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
			}
		});
	}
}
