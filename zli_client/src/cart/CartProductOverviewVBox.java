package cart;

import entities.Product;
import inputs.InputChecker;
import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CartProductOverviewVBox extends VBox {
	protected Product product;

	private Label nameLabel;
	private ImageView productImage;
	private HBox totalPriceHBox = new HBox();
	private Label totalPriceLabel;

	public CartProductOverviewVBox(Product product) {
		super();
		this.product = product;
	}

	public void initVBox() {
		this.setAlignment(Pos.TOP_CENTER);
		this.setPrefHeight(340);
		this.setMaxWidth(Control.USE_PREF_SIZE);
		this.setPrefWidth(400);
		this.setMinWidth(Control.USE_PREF_SIZE);
		
		nameLabel = new Label(product.getName());

		initProductImage();

		this.getChildren().add(nameLabel);
		this.getChildren().add(productImage);
	}

	protected void initTotalPrice() {
		totalPriceHBox.setAlignment(Pos.CENTER);
		totalPriceLabel = new Label("Total price: " + InputChecker.price(product.getPrice()));
		
		totalPriceHBox.getChildren().add(totalPriceLabel);
		this.getChildren().add(totalPriceHBox);
	}
	
	private void initProductImage() {
		productImage = new ImageView(product.getImagePath());
		productImage.setFitHeight(140);
		productImage.setFitWidth(200);
		productImage.setPreserveRatio(true);
	}
}
