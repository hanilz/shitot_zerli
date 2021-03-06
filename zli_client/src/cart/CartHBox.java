package cart;

import entities.Cart;
import entities.CustomProduct;
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
import javafx.scene.text.Text;
import util.ManageScreens;

/**
 * Component class of cart, is used in the cartScreen to represent a
 * ProductsBase which is in the cart.
 */
public class CartHBox extends HBox {
	private Cart cart = Cart.getInstance();

	private ProductsBase product;

	private int quantity;

	private double totalSumPrice = 0;
	private double totalSumDiscountPrice = 0;

	private ImageView image;
	private VBox idNameVBox = new VBox();
	private Label idLabel;
	private Label nameLabel;
	private VBox quantityVBox = new VBox();
	private Label quantityLabel = new Label("Quantity");
	private TextField quantityField = new TextField();

	private HBox imageHBox = new HBox();
	private VBox priceVBox = new VBox();
	private Text originalPriceText;
	private Label discountLabel = new Label();
	private Button removeButton = new Button("X");
	private double price;

	/**
	 * Constructor.
	 * @param product
	 * @param quantity
	 */
	public CartHBox(ProductsBase product, int quantity) {
		this.product = product;
		this.quantity = quantity;
		setPrice(product.isDiscount() ? product.calculateDiscount() : product.getPrice());
	}

	/**
	 * Set, load and add all nodes to the component.
	 */
	public void initHBox() {
		setVBoxSize();

		initImageProduct();

		initProductDetailsVBox();

		initViewContentsButton();

		initQuantityVBox();

		initPriceDetailsVBox();

		initRemoveButton();

		this.getChildren().add(imageHBox);
		this.getChildren().add(idNameVBox);
		this.getChildren().add(quantityVBox);
		this.getChildren().add(priceVBox);
		this.getChildren().add(removeButton);
		this.setId("cartHbox");
	}

	/**
	 * Set the size and spacing of the component.
	 */
	private void setVBoxSize() {
		this.setAlignment(Pos.CENTER);
		this.setSpacing(20);
		this.setPadding(new Insets(0, 0, 10, 0));
		this.setPrefHeight(100);
		this.setMinHeight(100);
	}

	/**
	 * Initialize the behavior of the remove button.
	 */
	private void initRemoveButton() {
		removeButton.setCursor(Cursor.HAND);
		removeButton.setStyle("-fx-background-color : Red ; -fx-font-size:16 ; -fx-font-weight: bold");
		removeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				String prompt = "";
				String title = "";
				if (product instanceof CustomProduct) {
					prompt = "Are you sure you want to remove this custom product from the cart?";
					title = "Remove Custom Product From Cart";
				} else if (product instanceof Product) {
					prompt = "Are you sure you want to remove this product from the cart?";
					title = "Remove Product From Cart";
				} else {
					prompt = "Are you sure you want to remove this item from the cart?";
					title = "Remove Item From Cart";
				}
				if (ManageScreens.getYesNoDecisionAlert(title, "", prompt)) {
					cart.removeFromCart(product);
					CartController.connectionWithCartHBox("refresh cart");
					CartController.instance.clearCartOverview(product);
				}
			}
		});
	}

	/**
	 * Set the product details.
	 */
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
		totalSumPrice = product.getPrice() * quantity;
		totalSumDiscountPrice = product.calculateDiscount() * quantity;
		originalPriceText = new Text(InputChecker.price(totalSumPrice));
		originalPriceText.setId("priceLabel");
		priceVBox.setAlignment(Pos.CENTER);
		priceVBox.setSpacing(10);
		priceVBox.getChildren().add(originalPriceText);
		initDiscountLabel();

		priceVBox.setPrefWidth(180);
	}

	private void initDiscountLabel() {
		if (product.isDiscount()) {
			discountLabel.setText(InputChecker.price(totalSumDiscountPrice));
			originalPriceText.setId("originalPriceTxt");
			discountLabel.setId("discountLabel");
			priceVBox.getChildren().add(discountLabel);
		}
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
				totalSumDiscountPrice = product.calculateDiscount() * newQuantity;
				originalPriceText.setText(InputChecker.price(totalSumPrice));
				discountLabel.setText(InputChecker.price(totalSumDiscountPrice));
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

	/**
	 * Display the product overview of this productsBase.
	 */
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

	public double getTotalSumDiscountPrice() {
		return totalSumDiscountPrice;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
