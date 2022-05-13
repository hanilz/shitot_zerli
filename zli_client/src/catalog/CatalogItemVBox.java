package catalog;

import entities.Item;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import util.InputChecker;

public class CatalogItemVBox extends VBox implements ICatalogVBox {
	private Item item; // will be used to get the data from item
	private Label nameLabel = new Label(); // will show the item name
	private ImageView image;
	private Label priceLabel = new Label("Price:");
	private Label amountLabel = new Label();; // will show the price
	private HBox priceHBox = new HBox();
	private HBox quantityHBox = new HBox();
	private Button subtractQuantityButton = new Button("-");
	private TextField quantityField = new TextField("0");
	private Button addQuantityButton = new Button("+");

	public CatalogItemVBox(Item item) {
		this.item = item;
	}

	/**
	 * 
	 */
	public void initVBox() {
		nameLabel.setText(item.getItemName());
		initImageProduct();

		amountLabel.setText("" + InputChecker.price(((int) item.getItemPrice())));
		int quantity = Integer.valueOf(quantityField.getText());

		// force the field to be numeric only
		quantityField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					quantityField.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
		
		subtractQuantityButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int quantity = 0;
				try {
					quantity = Integer.valueOf(quantityField.getText());
				} catch (Exception e) { return;}
				if (quantity >= 1)
					quantityField.setText("" + quantity--);
			}
		});
		
		addQuantityButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int quantity = 0;
				try {
					quantity = Integer.valueOf(quantityField.getText());
				} catch (Exception e) { return;}
				quantityField.setText("" + quantity++);
			}
		});
		
		initPriceHBox();
		initQuantityHBox();

		this.getChildren().add(nameLabel);
		this.getChildren().add(image);
		this.getChildren().add(priceHBox);
		this.getChildren().add(quantityHBox);

		this.setAlignment(Pos.CENTER);
	}

	private void initPriceHBox() {
		priceHBox.getChildren().add(priceLabel);
		priceHBox.getChildren().add(amountLabel);
		priceHBox.setAlignment(Pos.CENTER);
		priceHBox.setSpacing(25);
	}

	private void initQuantityHBox() {
		priceHBox.getChildren().add(subtractQuantityButton);
		priceHBox.getChildren().add(quantityField);
		priceHBox.getChildren().add(addQuantityButton);
		priceHBox.setAlignment(Pos.CENTER);
		priceHBox.setSpacing(10);
	}

	private void initImageProduct() {
		image = new ImageView(item.getImagePath());
		image.setFitHeight(100);
		image.setFitWidth(166);
		image.setPreserveRatio(true);
		image.setCursor(Cursor.HAND);
	}
}
