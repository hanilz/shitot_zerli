package customProduct;

import entities.ProductsBase;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import util.InputChecker;

public class CustomProductItemHBox extends HBox {
	private ProductsBase product;

	private CheckBox selectItem = new CheckBox();
	private ImageView image;
	private VBox imageVBox = new VBox();
	private VBox idNameVBox = new VBox();
	private Label idLabel;
	private Label nameLabel;
	private VBox priceVBox = new VBox();
	private Label priceLabel = new Label("Price:");
	private Label amountLabel;

	public CustomProductItemHBox(ProductsBase product) {
		this.product = product;
	}

	public void initHBox() {
		this.setAlignment(Pos.CENTER_LEFT);
		this.setSpacing(10);
		this.setPadding(new Insets(0, 0, 10, 10));
		this.setMinHeight(20);
		
		initImageProduct();

		initProductDetailsVBox();
		
		initPriceVBox();

		this.getChildren().add(selectItem);
		this.getChildren().add(imageVBox);
		this.getChildren().add(idNameVBox);
		this.getChildren().add(priceVBox);
	}

	private void initProductDetailsVBox() {
		idLabel = new Label("CatID: " + product.getId());
		
		nameLabel = new Label(product.getName());
		nameLabel.setFont(new Font(30));
		nameLabel.setMinWidth(Control.USE_PREF_SIZE);
		nameLabel.setPrefWidth(160);

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
	
	private void initPriceVBox() {
		amountLabel = new Label(InputChecker.price(product.getPrice()));
		amountLabel.setFont(new Font(23));
		priceVBox.getChildren().add(priceLabel);
		priceVBox.getChildren().add(amountLabel);
		priceVBox.setSpacing(5);
	}
}
