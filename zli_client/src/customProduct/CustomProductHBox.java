package customProduct;

import catalog.ProductDetailsController;
import catalog.ItemDetailsVBox;
import entities.Product;
import entities.ProductsBase;
import inputs.InputChecker;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.ManageScreens;

public class CustomProductHBox extends HBox implements ICustomProductHBox {
	protected ProductsBase product;

	private ImageView image;
	protected VBox imageVBox = new VBox();
	protected VBox idNameVBox = new VBox();
	private Label idLabel;
	protected Label nameLabel;
	protected HBox priceHBox = new HBox();
	protected VBox priceVBox = new VBox();
	protected Text amountLabel;

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

	protected void initPriceHBox() {
		priceVBox.setId("priceVBox");
		amountLabel = new Text(InputChecker.price(product.getPrice()));
		amountLabel.setId("ammountLabel");
		priceVBox.getChildren().add(amountLabel);
		priceVBox.setSpacing(7);
		priceHBox.setAlignment(Pos.CENTER_RIGHT);
		priceHBox.getChildren().add(priceVBox);
		this.getChildren().add(priceHBox);
	}

	private void initProductDetailsVBox() {
		idLabel = new Label("CatID: " + product.getId());
		nameLabel = new Label(product.getName());
		nameLabel.setId("nameLabel");
		nameLabel.setMinWidth(Control.USE_PREF_SIZE);
		nameLabel.setPrefWidth(240);
		nameLabel.setWrapText(true);
		
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
		imageVBox.setAlignment(Pos.CENTER);
		
		
		image.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(product instanceof Product)
					try {
						ProductDetailsController.setProduct((Product)product);
						ManageScreens.openPopupFXML(ProductDetailsController.class.getResource("ProductDetailsPopup.fxml"), product.getName());
					} catch (Exception e) {
						e.printStackTrace();
					}
				else {
					ItemDetailsVBox popup = new ItemDetailsVBox(product);
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

	public ProductsBase getProduct() {
		return product;
	}
}
