package cart;

import customProduct.CustomProductHBox;
import entities.ProductsBase;
import inputs.InputChecker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class ProductOverviewEditHBox extends CustomProductHBox {
	private int quantity = 1;
	private HBox quantityHBox = new HBox();
	private Label quantityLabel = new Label("" + quantity);
	private Button addQuantity = new Button("+");
	private Button removeQuantity = new Button("-");
	private Button removeButton = new Button("X");
	private CartProductOverviewEditVBox cartProductOverviewVBox;
	private ProductOverviewEditHBox instance;

	public ProductOverviewEditHBox(ProductsBase product, CartProductOverviewEditVBox cartProductOverviewVBox,
			int quantity) {
		super(product);
		this.cartProductOverviewVBox = cartProductOverviewVBox;
		this.quantity = quantity;
		instance = this;
	}

	public void initHBox() {
		super.initHBox();

		initQuantityVBox();
		removeButton.setCursor(Cursor.HAND);
		removeButton.setStyle("-fx-background-color : Red ; -fx-font-size:16 ; -fx-font-weight: bold");
		removeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				cartProductOverviewVBox.removeHBoxFromOverview(instance);
			}
		});
		priceHBox.setSpacing(5);
		priceHBox.getChildren().add(quantityHBox);

		priceHBox.getChildren().add(removeButton);
		amountLabel.setText(InputChecker.price(getPrice()));
	}
	
	private void initQuantityVBox() {
		quantityLabel = new Label("" + quantity);
		addQuantity.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				++quantity;
				if (quantity > 1)
					removeQuantity.setDisable(false);
				quantityLabel.setText("" + quantity);
				amountLabel.setText(InputChecker.price(quantity * product.getPrice()));
				cartProductOverviewVBox.addToTotalPrice(product.getPrice());
			}
		});
		if(quantity == 1)
			removeQuantity.setDisable(true);
		removeQuantity.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				--quantity;
				if (quantity == 1)
					removeQuantity.setDisable(true);
				quantityLabel.setText("" + quantity);
				amountLabel.setText(InputChecker.price(quantity * product.getPrice()));
				cartProductOverviewVBox.addToTotalPrice(-product.getPrice());
			}
		});

		quantityHBox.setAlignment(Pos.CENTER);
		quantityHBox.setSpacing(10);

		quantityHBox.getChildren().add(removeQuantity);
		quantityHBox.getChildren().add(quantityLabel);
		quantityHBox.getChildren().add(addQuantity);
	}

	public double getPrice() {
		return quantity * product.getPrice();
	}

	public int getQuantity() {
		return quantity;
	}
}
