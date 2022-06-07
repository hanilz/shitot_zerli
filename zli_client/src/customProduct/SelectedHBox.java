package customProduct;

import entities.ProductsBase;
import inputs.InputChecker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Component class for a selected product/item in the right side of custom product builder.
 * Was created by a SelectorHBox and bound to it.
 * Will be removed when a user clicks on the checkbox of the SelectorHBox.
 */
public class SelectedHBox extends CustomProductHBox implements ICustomProductHBox {
	private int quantity = 1;
	private HBox quantityHBox = new HBox();
	private Label quantityLabel;
	private Button addQuantity = new Button("+");
	private Button removeQuantity = new Button("-");
	private Button removeButton = new Button("X");
	private SelectedHBox selectedProduct;
	private Label discountLabel = new Label();

	public SelectedHBox(ProductsBase product, int quantity) {
		super(product);
		this.quantity = quantity;
	}

	@Override
	public void initHBox() {
		this.setId("selectedHBox");
		selectedProduct = this;
		
		initQuantityVBox();
		initRemoveButton();
		
		super.initHBox();
		priceHBox.setSpacing(5);
		priceHBox.getChildren().add(quantityHBox);
		super.initPriceHBox();
		priceHBox.getChildren().add(removeButton);
		amountLabel.setText(InputChecker.price(getPrice()));
		if (product.isDiscount()) {
			amountLabel.setStyle(
					"	-fx-strikethrough: true;\r\n" + "	-fx-font-size: 18px;\r\n" + "	-fx-font-weight: bold;");
			initDiscount();
		}
	}

	/**
	 * Remove selectedHBox from overview and refresh prices
	 */
	private void initRemoveButton() {
		removeButton.setCursor(Cursor.HAND);
		removeButton.setStyle("-fx-background-color : Red ; -fx-font-size:16 ; -fx-font-weight: bold");
		removeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				CustomProductBuilderController.updateOverViewVBox("remove from overview", selectedProduct);
			}
		});
	}

	private void initDiscount() {
		discountLabel.setText(InputChecker.price(product.calculateDiscount() * quantity));
		amountLabel.setId("originalPriceTxt");
		discountLabel.setId("discountLabel");
		priceVBox.getChildren().add(discountLabel);
	}

	private void initQuantityVBox() {
		quantityLabel = new Label("" + quantity);
		
		initAddQuantityButton();

		initRemoveQuantityButton();

		quantityLabel.setId("quantityLabel");

		quantityHBox.setAlignment(Pos.CENTER);
		quantityHBox.setSpacing(10);

		quantityHBox.getChildren().add(removeQuantity);
		quantityHBox.getChildren().add(quantityLabel);
		quantityHBox.getChildren().add(addQuantity);
	}

	private void initRemoveQuantityButton() {
		if (quantity == 1)
			removeQuantity.setDisable(true);
		removeQuantity.setOnAction(new EventHandler<ActionEvent>() {
			/**
			 * Remove quantity and refresh prices.
			 */
			@Override
			public void handle(ActionEvent event) {
				--quantity;
				if (quantity == 1)
					removeQuantity.setDisable(true);
				quantityLabel.setText("" + quantity);
				amountLabel.setText(InputChecker.price(quantity * product.getPrice()));
				discountLabel.setText(InputChecker.price(quantity * product.calculateDiscount()));
				CustomProductBuilderController.updateTotalPriceLabel();
			}
		});
	}

	private void initAddQuantityButton() {
		addQuantity.setOnAction(new EventHandler<ActionEvent>() {
			/**
			 * Add quantity and refresh prices.
			 */
			@Override
			public void handle(ActionEvent event) {
				++quantity;
				if (quantity > 1)
					removeQuantity.setDisable(false);
				quantityLabel.setText("" + quantity);
				amountLabel.setText(InputChecker.price(quantity * product.getPrice()));
				discountLabel.setText(InputChecker.price(quantity * product.calculateDiscount()));
				CustomProductBuilderController.updateTotalPriceLabel();
			}
		});
	}

	/**
	 * @return quantity.
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @return product price * quantity.
	 */
	public double getPrice() {
		return product.getPrice() * quantity;
	}

	/**
	 * @return product price after discount * quantity.
	 */
	public double getDiscountedPrice() {
		return product.calculateDiscount() * quantity;
	}
}
