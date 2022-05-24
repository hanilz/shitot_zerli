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

	private VBox priceVBox = new VBox();
	private Label priceLabel = new Label("Price");
	private Label amountLabel;
	private Button removeButton = new Button("X");
	private Button viewContentsButton = new Button("View Contents");

	public CartHBox(ProductsBase product, int quantity) {
		this.product = product;
		this.quantity = quantity;
	}

	public void initHBox() {
		this.setAlignment(Pos.CENTER);
		this.setSpacing(20);
		this.setPadding(new Insets(0, 0, 10, 0));

		initImageProduct();

		initProductDetailsVBox();

		if(!isItem())
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
			}
		});

		this.getChildren().add(image);
		this.getChildren().add(idNameVBox);
		if(!isItem())
			this.getChildren().add(viewContentsButton);
		this.getChildren().add(quantityVBox);
		this.getChildren().add(priceVBox);
		this.getChildren().add(removeButton);
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

		idNameVBox.getChildren().add(idLabel);
		idNameVBox.getChildren().add(nameLabel);
	}

	private void initPriceDetailsVBox() {
		priceLabel.setFont(new Font(20));
		totalSumPrice = product.getPrice() * quantity;
		amountLabel = new Label(InputChecker.price(totalSumPrice));
		amountLabel.setFont(new Font(20));

		priceVBox.setAlignment(Pos.CENTER);
		priceVBox.setPrefWidth(180);
		
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

	private void initViewContentsButton() {
		viewContentsButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				CartProductOverviewVBox overviewVBox;
				if(product instanceof CustomProduct) {
					CustomProduct customProduct = (CustomProduct) product;
					overviewVBox = new CartProductOverviewEditVBox(customProduct);
				}
				else {
					Product currentProduct = (Product) product;
					overviewVBox = new CartProductOverviewNoEditVBox(currentProduct);
				}
				overviewVBox.initVBox();
				
				CartController.setOverviewVBox(overviewVBox);
			}
		});
		
	}
	
	public double getTotalSumPrice() {
		return totalSumPrice;
	}
	
	public TextField getQuantityField() {
		return quantityField;
	}
}
