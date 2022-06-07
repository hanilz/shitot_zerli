package order;

import catalog.ProductDetailsController;
import catalog.ItemDetailsVBox;
import entities.CustomProduct;
import entities.Product;
import entities.ProductsBase;
import inputs.InputChecker;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.ManageScreens;

public class OrderSummaryHBox extends HBox {
	private ImageView image;

	private Text priceLabel = new Text();
	
	private Label discountLabel = new Label();

	private Label quantityLabel = new Label();
	
	private VBox priceVBox = new VBox();

	private ProductsBase product;

	private int quantity;

	public OrderSummaryHBox(ProductsBase product, int quantity) {
		this.product = product;
		this.quantity = quantity;
	}

	public void initHBox() {
		this.setId("productSummaryHBox");
		this.setAlignment(Pos.CENTER_LEFT);
		this.setPadding(new Insets(0,0,0,60));
		this.setSpacing(15);

		initImageProduct();

		quantityLabel.setText("" + quantity);
		quantityLabel.setMinWidth(40);
		quantityLabel.setAlignment(Pos.CENTER);
		priceLabel.setFont(new Font(20));
		priceLabel.setText(InputChecker.price(product.getPrice() * quantity));
		priceLabel.setStyle("-fx-font-weight: bold;");
		priceVBox.getChildren().add(priceLabel);
		priceVBox.setAlignment(Pos.CENTER);
		VBox imageVbox = new VBox(image);
		imageVbox.setAlignment(Pos.CENTER);
		imageVbox.setMinWidth(70);
		this.getChildren().add(imageVbox);
		this.getChildren().add(quantityLabel);
		this.getChildren().add(new Label("X"));
		this.getChildren().add(priceVBox);
		discountLabel.setId("discountLabel");
		if(product.isDiscount()) {
			priceLabel.setStyle("-fx-strikethrough: true;\r\n"
					+ "	-fx-font-size: 18px;\r\n"
					+ "	-fx-font-weight: bold");
			discountLabel.setText(InputChecker.price(product.calculateDiscount() * quantity));
			discountLabel.setStyle("-fx-text-fill: red;\r\n"
					+ "	-fx-font-weight: bold;\r\n"
					+ "	-fx-font-size: 20px;");
			priceVBox.getChildren().add(discountLabel);		
			priceLabel.setStrikethrough(true);
		}
	}

	private void initImageProduct() {
		image = new ImageView(product.getImagePath());
		image.setFitHeight(80);
		image.setFitWidth(70);
		image.setPreserveRatio(true);
		setImageProductEvent();
	}

	private void setImageProductEvent() {
		image.setCursor(Cursor.HAND);
		image.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				if(product instanceof Product)
					try {
						ProductDetailsController.setProduct((Product)product);
						if(product instanceof CustomProduct)
							ProductDetailsController.setCustomProduct(((CustomProduct)product).getProducts());
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
}
