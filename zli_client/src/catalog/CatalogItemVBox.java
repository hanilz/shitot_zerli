package catalog;

import entities.Item;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
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
		nameLabel.setText(item.getItemName());
		setImageProp();

		amountLabel.setText("" + InputChecker.price(((int) item.getItemPrice())));
		//int quantity = Integer.valueOf(quantityField.getText());

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

	private void initQuantityHBox() {
		priceHBox.getChildren().add(subtractQuantityButton);
		priceHBox.getChildren().add(quantityField);
		priceHBox.getChildren().add(addQuantityButton);
		priceHBox.setAlignment(Pos.CENTER);
		priceHBox.setSpacing(10);
	}

	protected void setImageProp() {
		image = new ImageView(item.getImagePath());
		super.setImageProp();
	}
}
