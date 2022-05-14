package catalog;

import entities.Cart;
import entities.Item;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import util.InputChecker;

public class CatalogItemVBox extends CatalogVBox implements ICatalogVBox {
	private Item item; // will be used to get the data from item
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
		nameLabel.setText(item.getName() + " - " + item.getColor());
		setImageProp();

		amountLabel.setText("" + InputChecker.price(((int) item.getPrice())));

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
					quantityField.setText("" + (--quantity));
			}
		});
		
		addQuantityButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int quantity = 0;
				try {
					quantity = Integer.valueOf(quantityField.getText());	
				} catch (Exception e) { return;}
				quantityField.setText("" + (++quantity));
			}
		});
		
		initPriceHBox();
		initQuantityHBox();

		initAddToCartButton();
		
		super.initVBox();
		this.getChildren().add(quantityHBox);
		this.getChildren().add(addToCartButton);
	}

	private void initAddToCartButton() {
		addToCartButton.setCursor(Cursor.HAND);
		addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int quantity = Integer.valueOf(quantityField.getText());
				if(quantity<1)
					return;
				Cart.getInstance().addToCart(item, quantity, true);
				System.out.println("Cart is: " + Cart.getInstance().getCart());
				System.out.println(item.getName() + " added to cart woohoooo");
			}
		});
	}

	
	private void initQuantityHBox() {
		quantityHBox.getChildren().add(subtractQuantityButton);
		quantityHBox.getChildren().add(quantityField);
		quantityHBox.getChildren().add(addQuantityButton);
		quantityHBox.setAlignment(Pos.CENTER);
		quantityHBox.setSpacing(10);
	}

	protected void setImageProp() {
		image = new ImageView(item.getImagePath());
		super.setImageProp();
	}
}
