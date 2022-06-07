package catalog;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Component for displaying a ProductsBase in the catalog grid.
 */
public class CatalogVBox extends VBox implements ICatalogVBox{
	protected Label nameLabel = new Label();  // will show the product name
	protected VBox nameVBox = new VBox();
	protected ImageView image;
	protected VBox imageVBox = new VBox();
	protected Label amountLabel = new Label();  // will show the price
	protected Label discountLabel = new Label();
	protected HBox priceHBox = new HBox();
	protected Button addToCartButton = new Button("Add To Cart");

	/**
	 * Initialize the VBox - All the sizes and design of the component.
	 */
	@Override
	public void initVBox() {
		this.setPrefWidth(200);
		nameLabel.setMinHeight(40);
		nameLabel.setMaxWidth(200);

		nameLabel.setWrapText(true);
		nameLabel.setAlignment(Pos.BOTTOM_CENTER);
		addToCartButton.setId("catalogBtn");
		nameLabel.setId("productsLabel");
		amountLabel.setId("priceLabel");
		discountLabel.setId("discountLabel");
		this.getChildren().add(nameLabel);
		this.getChildren().add(imageVBox);
		this.getChildren().add(priceHBox);
		this.setAlignment(Pos.CENTER);
	}
	
	/**
	 * Initialize the size of the price.
	 */
	protected void initPriceHBox() {
		priceHBox.getChildren().add(amountLabel);
		priceHBox.setAlignment(Pos.CENTER);
		priceHBox.setSpacing(25);
	}

	/**
	 * Initialize all the sizes of the image.
	 */
	protected void setImageProp() {
		imageVBox.getChildren().add(image);
		image.setFitHeight(140);
		image.setFitWidth(200);
		image.setPreserveRatio(true);
		image.setCursor(Cursor.HAND);
		imageVBox.setPrefHeight(140);
		imageVBox.setPrefWidth(200);
		imageVBox.setMinHeight(Control.USE_PREF_SIZE);
		imageVBox.setMinWidth(Control.USE_PREF_SIZE);
		imageVBox.setMaxHeight(Control.USE_PREF_SIZE);
		imageVBox.setMaxWidth(Control.USE_PREF_SIZE);
		imageVBox.setAlignment(Pos.CENTER);
	}
}
