package cart;

import entities.Cart;
import entities.CustomProduct;
import entities.Item;
import entities.Product;
import entities.ProductsBase;
import inputs.InputChecker;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class CartHBox extends HBox {
	private Cart cart = Cart.getInstance();

	private ProductsBase product;

	private int quantity;

	private double totalSumPrice = 0;

	private ImageView image;
	private VBox idNameVBox = new VBox();
	private Label idLabel;
	private Label nameLabel;
	private VBox quantityVBox = new VBox();
	private Label quantityLabel = new Label("Quantity");
	private TextField quantityField = new TextField();

	private HBox imageHBox = new HBox();
	private VBox priceVBox = new VBox();
	private Label amountLabel;
	private Label discountLabel;
	private Button removeButton = new Button("X");
	private double price;

	public CartHBox(ProductsBase product, int quantity) {
		this.product = product;
		this.quantity = quantity;
		price = product.isDiscount() ? product.calculateDiscount(): product.getPrice();
	}

	public void initHBox() {
		this.setAlignment(Pos.CENTER);
		this.setSpacing(20);
		this.setPadding(new Insets(0, 0, 10, 0));
		this.setPrefHeight(100);
		this.setMinHeight(100);

		initImageProduct();

		initProductDetailsVBox();

		initViewContentsButton();
		
		initQuantityVBox();

		initPriceDetailsVBox();

		removeButton.setCursor(Cursor.HAND);
		removeButton.setStyle("-fx-background-color : Red ; -fx-font-size:16 ; -fx-font-weight: bold");
		removeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				cart.removeFromCart(product);
				CartController.connectionWithCartHBox("refresh cart");
				CartController.instance.clearCartOverview(product);
			}
		});

		this.getChildren().add(imageHBox);
		this.getChildren().add(idNameVBox);
		this.getChildren().add(quantityVBox);
		this.getChildren().add(priceVBox);
		this.getChildren().add(removeButton);
		this.setId("cartHbox");
	}

	private boolean isItem() {
		return product instanceof Item;
	}

	private void initProductDetailsVBox() {
		idLabel = new Label("CatID: " + product.getId());

		nameLabel = new Label(product.getName());
		nameLabel.setFont(new Font(26));
		nameLabel.setMinWidth(360);
		nameLabel.setPrefWidth(360);
		nameLabel.setPrefHeight(80);
		nameLabel.setMinHeight(80);
		nameLabel.setAlignment(Pos.TOP_LEFT);
		nameLabel.setWrapText(true);
		idNameVBox.setPrefHeight(90);
		idNameVBox.setMinHeight(90);

		idNameVBox.getChildren().add(idLabel);
		idNameVBox.getChildren().add(nameLabel);
	}

	private void initPriceDetailsVBox() {
		totalSumPrice = price * quantity;
		amountLabel = new Label(InputChecker.price(quantity * product.getPrice()));
		amountLabel.setFont(new Font(20));
		amountLabel.setStyle("-fx-font-weight: bold;");
		priceVBox.setAlignment(Pos.CENTER);
		priceVBox.setSpacing(10);
		priceVBox.getChildren().add(amountLabel);
		if(product.isDiscount()) {
			amountLabel.setStyle("");
			discountLabel = new Label(InputChecker.price(totalSumPrice));
			discountLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
			amountLabel.setFont(new Font(14));
			discountLabel.setFont(new Font(20));
			priceVBox.getChildren().add(discountLabel);
		}

		priceVBox.setPrefWidth(180);
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
				totalSumPrice = price * newQuantity;
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
		
		imageHBox.getChildren().add(image);
		imageHBox.setAlignment(Pos.CENTER);
		imageHBox.setMinWidth(50);
		imageHBox.setPrefHeight(50);
	}

	private void initViewContentsButton() {
		this.setCursor(Cursor.HAND);
		this.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				CartController.createOverviewVBox(product);
			}
		});
	}
	
	public double getTotalSumPrice() {
		return totalSumPrice;
	}
	
	public TextField getQuantityField() {
		return quantityField;
	}

	public ProductsBase getProduct() {
		return product;
	}

	public void setProduct(ProductsBase product) {
		this.product = product;
	}
}
