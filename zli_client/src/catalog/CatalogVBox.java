package catalog;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CatalogVBox extends VBox implements ICatalogVBox {
	protected Label nameLabel = new Label(); // will show the product name
	protected VBox nameVBox = new VBox();
	protected ImageView image;
	protected Label priceLabel = new Label("Price:");
	protected Label amountLabel = new Label();; // will show the price
	protected HBox priceHBox = new HBox();
	protected Button addToCartButton = new Button("Add To Cart");

	@Override
	public void initVBox() {
//		this.setPrefHeight(150);
		this.setPrefWidth(200);
////
////		this.setMinHeight(USE_PREF_SIZE);
////		this.setMaxHeight(USE_PREF_SIZE);
////		this.setMinWidth(USE_PREF_SIZE);
////		this.setMaxWidth(USE_PREF_SIZE);
//		nameVBox.setMinHeight(USE_PREF_SIZE);
//		nameVBox.setPrefHeight(20);
//		nameVBox.setAlignment(Pos.CENTER);
		nameLabel.setMinHeight(40);
		nameLabel.setMaxWidth(200);

		nameLabel.setWrapText(true);
		nameLabel.setAlignment(Pos.BOTTOM_CENTER);
//		nameVBox.getChildren().add(nameLabel);
		
		addToCartButton.setId("catalogBtn");
		nameLabel.setId("productsLabel");
		priceLabel.setId("priceLabel");
		amountLabel.setId("priceLabel");
		this.getChildren().add(nameLabel);
		this.getChildren().add(image);
		this.getChildren().add(priceHBox);
		this.setAlignment(Pos.CENTER);
	}
	
	protected void initPriceHBox() { //same
		priceHBox.getChildren().add(priceLabel);
		priceHBox.getChildren().add(amountLabel);
		priceHBox.setAlignment(Pos.CENTER);
		priceHBox.setSpacing(25);
	}
	
	protected void setImageProp() {
		image.setFitHeight(140);
		image.setFitWidth(200);
		image.setPreserveRatio(true);
		image.setCursor(Cursor.HAND);
	}
}
