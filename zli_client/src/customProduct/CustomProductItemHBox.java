package customProduct;

import entities.Cart;
import entities.Product;
import entities.ProductsBase;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import order.CartController;
import util.InputChecker;

public class CustomProductItemHBox extends HBox {
	private Cart cart = Cart.getInstance();

	private ProductsBase product;

	private int quantity;

	private double totalSumPrice = 0;

	private CheckBox selectItem = new CheckBox();
	private ImageView image;
	private VBox idNameVBox = new VBox();
	private Label idLabel;
	private Label nameLabel;
	private VBox quantityVBox = new VBox();
	private Label quantityLabel = new Label("Quantity");
	private TextField quantityField = new TextField();
	private VBox priceVBox = new VBox();
	private Label priceLabel = new Label("Price");
	private Label amountLabel;

	public CustomProductItemHBox(ProductsBase product) {
		this.product = product;
	}

	public void initHBox() {
		this.setAlignment(Pos.CENTER_LEFT);
		this.setSpacing(0);
		this.setPadding(new Insets(0, 0, 10, 0));

		initImageProduct();

		initProductDetailsVBox();

		initQuantityVBox();

		initPriceDetailsVBox();

		this.getChildren().add(selectItem);
		this.getChildren().add(image);
		this.getChildren().add(idNameVBox);
		this.getChildren().add(quantityVBox);
		this.getChildren().add(priceVBox);
	}

	private void initProductDetailsVBox() {
		idLabel = new Label("CatID: " + product.getId());

		nameLabel = new Label(product.getName());
		nameLabel.setFont(new Font(30));
		nameLabel.setMinWidth(200);
		nameLabel.setPrefWidth(200);

		idNameVBox.getChildren().add(idLabel);
		idNameVBox.getChildren().add(nameLabel);
	}

	private void initPriceDetailsVBox() {
		priceLabel.setFont(new Font(20));
		totalSumPrice = product.getPrice() * quantity;
		amountLabel = new Label(InputChecker.price(totalSumPrice));
		amountLabel.setFont(new Font(20));

		priceVBox.setAlignment(Pos.CENTER);

		priceVBox.getChildren().add(priceLabel);
		priceVBox.getChildren().add(amountLabel);
	}

	private void initQuantityVBox() {
		quantityField.setText("" + quantity);
		quantityField.setAlignment(Pos.CENTER);
		quantityField.setMinWidth(50);
		quantityField.setPrefWidth(50);
		// force the field to be numeric only
		quantityField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					quantityField.setText(newValue.replaceAll("[^\\d]", ""));
				}
				Integer newQuantity = 0;
				if (quantityField.getText().isEmpty())
					return;
				newQuantity = Integer.parseInt(quantityField.getText());
				cart.addToCart(product, newQuantity, false);
				totalSumPrice = product.getPrice() * newQuantity;
				System.out.println("new price is " + totalSumPrice);
				amountLabel.setText(InputChecker.price(totalSumPrice));
				quantity = newQuantity;
				if (quantity == 0)
					CartController.connectionWithCartHBox("refresh cart");
				else
					CartController.connectionWithCartHBox("refresh total price");
			}
		});

		quantityVBox.setAlignment(Pos.CENTER);
		quantityVBox.setSpacing(10);

		quantityVBox.getChildren().add(quantityLabel);
		quantityVBox.getChildren().add(quantityField);
	}

	private void initImageProduct() {
		image = new ImageView(product.getImagePath());

		image.setFitHeight(80);
		image.setFitWidth(100);
		image.setPreserveRatio(true);
	}

	public double getTotalSumPrice() {
		return totalSumPrice;
	}
}