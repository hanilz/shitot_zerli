package manageCatalog;

import entities.ProductsBase;
import inputs.InputChecker;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import util.ManageScreens;

public class ManageCatalogVBox extends VBox {
	protected Label nameLabel = new Label(); // will show the product name
	protected ImageView image;
	protected Label priceLabel = new Label("Price:");
	protected Label amountLabel = new Label(); // will show the price
	protected HBox priceHBox = new HBox();
	private ProductsBase product; // will be used to get the data from
	private ManageCatalogController manageCatalogController;

	public ManageCatalogVBox(ProductsBase product, ManageCatalogController manageCatalogController) {
		this.product = product;
		this.manageCatalogController= manageCatalogController;
	}

	public void initVBox() {
		nameLabel.setText(product.getName());
		this.getChildren().add(nameLabel);
		initImageProduct();
		VBox imageFrame = new VBox();
		imageFrame.setPrefHeight(130);
		imageFrame.setPrefWidth(130);
		imageFrame.setAlignment(Pos.CENTER);
		imageFrame.getChildren().add(image);
		this.getChildren().add(imageFrame);
		amountLabel.setText("" + InputChecker.price(((double) product.getPrice())));//int->double
		if(product.getDiscount() != 0) {
			double discount = product.getPrice() - (product.getPrice()*(product.getDiscount()/100));
			amountLabel.setId("discount");
			amountLabel.setStyle("-fx-text-fill: #F70000;");
			amountLabel.setText(InputChecker.price(discount));
		}
		initPriceHBox();
		this.getChildren().add(priceHBox);
		this.setAlignment(Pos.CENTER);
	}

	protected void initPriceHBox() { // same
		priceHBox.getChildren().add(priceLabel);
		priceHBox.getChildren().add(amountLabel);
		priceHBox.setAlignment(Pos.CENTER);
		priceHBox.setSpacing(25);
	}

	protected void setImageProp() {
		image.setFitHeight(130);
		image.setFitWidth(130);
		image.setPreserveRatio(true);
		image.setCursor(Cursor.HAND);
	}

	private void initImageProduct() { 
		image = new ImageView(product.getImagePath());
		setImageProp();
		image.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				ProductEditorController.setProductEditorProduct(product);
				ProductEditorController.setManageCatalogController(manageCatalogController);
				try {
					ManageScreens.openPopupFXML(ProductEditorController.class.getResource("ProductEditorPopup.fxml"),
							"Product Editor");
				} catch (Exception e) {}
			}
		});
	}
}
