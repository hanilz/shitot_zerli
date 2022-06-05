package cart;

import entities.Product;
import inputs.InputChecker;
import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class CartProductOverviewVBox extends VBox {
	protected Product product;

	private Label nameLabel;
	protected ImageView productImage;
	protected Label totalPriceLabel = new Label();
	private HBox totalPriceHBox = new HBox(totalPriceLabel);
	protected Label TotalPriceDiscountLabel;
	protected Text originalPriceTxt;
	protected VBox itemsProductsVBox = new VBox();
	protected ScrollPane itemsProductsScrollPane = new ScrollPane(itemsProductsVBox);

	public CartProductOverviewVBox(Product product) {
		super();
		this.product = product;
	}

	public void initVBox() {
		totalPriceLabel.setId("totalPriceLabel");
		
		this.setAlignment(Pos.TOP_CENTER);
		this.setPrefHeight(340);
		this.setMaxWidth(Control.USE_PREF_SIZE);
		this.setPrefWidth(450);
		this.setMinWidth(Control.USE_PREF_SIZE);
		this.setSpacing(10);
		
		nameLabel = new Label(product.getName());

		initProductImage();

		initItemsProductsScrollPane();
		
		this.getChildren().add(nameLabel);
		this.getChildren().add(productImage);
		this.getChildren().add(itemsProductsScrollPane);
	}

	protected void initTotalPriceHBox() {
		totalPriceHBox.setAlignment(Pos.CENTER);
		totalPriceHBox.setSpacing(20);
		originalPriceTxt = new Text(InputChecker.price(product.getPrice()));
		originalPriceTxt.setId("totalPriceAmountLabel");
		originalPriceTxt.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
		totalPriceHBox.getChildren().add(originalPriceTxt);
		if(product.isDiscount()) {
			originalPriceTxt.setId("totalOriginalPriceTxt");
			originalPriceTxt.setStyle("-fx-font-size: 16px;");
			TotalPriceDiscountLabel = new Label(InputChecker.price(product.calculateDiscount()));
			TotalPriceDiscountLabel.setId("totalPriceDiscountLabel");
			TotalPriceDiscountLabel.setStyle("-fx-text-fill: red;");
			totalPriceHBox.getChildren().add(TotalPriceDiscountLabel);
		}
		this.getChildren().add(totalPriceHBox);
	}
	
	private void initProductImage() {
		productImage = new ImageView(product.getImagePath());
		productImage.setFitHeight(140);
		productImage.setFitWidth(200);
		productImage.setPreserveRatio(true);
	}
	private void initItemsProductsScrollPane() {
		itemsProductsScrollPane.setPrefHeight(250);
		itemsProductsScrollPane.setMinHeight(Control.USE_PREF_SIZE);
	}
}
