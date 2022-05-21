package customProduct;

import entities.ProductsBase;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import util.InputChecker;

public class CustomProductHBox extends HBox implements ICustomProductHBox {
	protected ProductsBase product;

	private ImageView image;
	protected VBox imageVBox = new VBox();
	protected VBox idNameVBox = new VBox();
	private Label idLabel;
	private Label nameLabel;
	protected HBox priceHBox = new HBox();
	private VBox priceVBox = new VBox();
	private Label priceLabel = new Label("Price:");
	protected Label amountLabel;

	public CustomProductHBox(ProductsBase product) {
		this.product = product;
	}

	@Override
	public void initHBox() {
		this.setAlignment(Pos.CENTER_LEFT);
		this.setSpacing(10);
		this.setPadding(new Insets(0, 0, 10, 10));
		this.setMinHeight(20);

		initImageProduct();
		initPriceHBox();
		initProductDetailsVBox();

		this.getChildren().add(imageVBox);
		this.getChildren().add(idNameVBox);
		this.getChildren().add(priceHBox);
	}
	
	private void initPriceHBox() {
		amountLabel = new Label(InputChecker.price(product.getPrice()));
		amountLabel.setFont(new Font(23));
		priceVBox.getChildren().add(priceLabel);
		priceVBox.getChildren().add(amountLabel);
		priceVBox.setSpacing(5);
		priceHBox.setAlignment(Pos.CENTER_RIGHT);
		priceHBox.getChildren().add(priceVBox);
	}

	private void initProductDetailsVBox() {
		idLabel = new Label("CatID: " + product.getId());

		nameLabel = new Label(product.getName());
		nameLabel.setFont(new Font(20));
		nameLabel.setMinWidth(Control.USE_PREF_SIZE);
		nameLabel.setPrefWidth(200);

		idNameVBox.getChildren().add(idLabel);
		idNameVBox.getChildren().add(nameLabel);
	}

	private void initImageProduct() {
		image = new ImageView(product.getImagePath());

		image.setFitHeight(60);
		image.setFitWidth(60);
		image.setPreserveRatio(true);

		imageVBox.getChildren().add(image);

		imageVBox.setPrefWidth(60);
		imageVBox.setAlignment(Pos.CENTER_LEFT);
	}

	public ProductsBase getProduct() {
		return product;
	}
}
