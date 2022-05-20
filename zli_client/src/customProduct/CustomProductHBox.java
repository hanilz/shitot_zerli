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

public class CustomProductHBox extends HBox implements ICustomProductHBox {
	protected ProductsBase product;

	private ImageView image;
	private VBox imageVBox = new VBox();
	private VBox idNameVBox = new VBox();
	private Label idLabel;
	private Label nameLabel;

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

		initProductDetailsVBox();

		this.getChildren().add(imageVBox);
		this.getChildren().add(idNameVBox);

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
}
