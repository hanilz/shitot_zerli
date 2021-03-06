package cart;

import customProduct.CustomProductHBox;
import entities.Product;
import entities.ProductsBase;
import inputs.InputChecker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import util.ManageScreens;

/**
 * Component class for product overview with edit - adds +- buttons and a remove
 * button.
 */
public class ProductOverviewEditHBox extends CustomProductHBox {
	private int quantity = 1;
	private HBox quantityHBox = new HBox();
	private Label quantityLabel = new Label("" + quantity);
	private Button addQuantity = new Button("+");
	private Button removeQuantity = new Button("-");
	private Button removeButton = new Button("X");
	private Label discountLabel = new Label("");
	private CartProductOverviewEditVBox cartProductOverviewVBox;
	private ProductOverviewEditHBox instance;
	private double price;

	/**
	 * Constructor.
	 * @param product for basing all information on.
	 * @param cartProductOverviewVBox for connection with the containing container.
	 * @param quantity
	 */
	public ProductOverviewEditHBox(ProductsBase product, CartProductOverviewEditVBox cartProductOverviewVBox,
			int quantity) {
		super(product);
		this.cartProductOverviewVBox = cartProductOverviewVBox;
		this.quantity = quantity;
		instance = this;
	}

	public void initHBox() {
		this.setId("editHBox");
		
		super.initHBox();
		super.initPriceHBox();
		
		initPriceAndDiscount();
		
		initQuantityVBox();
		
		initRemoveButton();
		
		priceHBox.setSpacing(5);
		priceHBox.getChildren().remove(priceVBox);
		priceHBox.getChildren().add(quantityHBox);
		priceVBox.setAlignment(Pos.CENTER);
		priceHBox.getChildren().add(priceVBox);
		nameLabel.setId("NameLabel");
		nameLabel.setPrefWidth(145);

		priceHBox.getChildren().add(removeButton);
		amountLabel.setText(InputChecker.price(quantity * product.getPrice()));
	}

	/**
	 * Set behavior for remove button.
	 */
	private void initRemoveButton() {
		removeButton.setId("removeBtn");
		removeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				String prompt = "";
				String title = "";
				if (product instanceof Product) {
					prompt = "Are you sure you want to remove this product from the custom product?";
					title = "Remove Product From Custom Product";
				} else {
					prompt = "Are you sure you want to remove this item from the custom product?";
					title = "Remove Item From Custom Product";
				}
				if (ManageScreens.getYesNoDecisionAlert(title, "", prompt)) {
					cartProductOverviewVBox.removeHBoxFromOverview(instance);
				}
			}
		});
	}

	private void initPriceAndDiscount() {
		amountLabel.setStyle("-fx-font-size: 20px;\r\n" + "	-fx-font-weight: bold;");
		price = product.getPrice();
		if (product.isDiscount()) {
			amountLabel.setStyle(
					"-fx-strikethrough: true;\r\n" + "	-fx-font-size: 18px;\r\n" + "	-fx-font-weight: bold;");
			initDiscountLabel();
		}
	}

	private void initDiscountLabel() {
		discountLabel.setText(InputChecker.price(quantity * product.calculateDiscount()));
		discountLabel.setStyle("-fx-text-fill: red;\r\n" + "	-fx-font-weight: bold;\r\n" + "	-fx-font-size: 20px;");
		priceVBox.getChildren().add(discountLabel);
		price = product.calculateDiscount();
	}

	private void initQuantityVBox() {
		quantityLabel = new Label("" + quantity);
		quantityLabel.setMinWidth(15);
		quantityLabel.setAlignment(Pos.CENTER);
		
		initAddQuantityButton();
		initRemoveQuantityButton();

		quantityHBox.setAlignment(Pos.CENTER);
		quantityHBox.setSpacing(10);

		quantityHBox.getChildren().add(removeQuantity);
		quantityHBox.getChildren().add(quantityLabel);
		quantityHBox.getChildren().add(addQuantity);
	}

	/**
	 * set behavior for remove quantity button.
	 */
	private void initRemoveQuantityButton() {
		if (quantity == 1)
			removeQuantity.setDisable(true);
		removeQuantity.setCursor(Cursor.HAND);
		removeQuantity.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				--quantity;
				if (quantity == 1)
					removeQuantity.setDisable(true);
				quantityLabel.setText("" + quantity);
				amountLabel.setText(InputChecker.price(quantity * product.getPrice()));
				discountLabel.setText(InputChecker.price(quantity * product.calculateDiscount()));
				cartProductOverviewVBox.addToTotalPrice(-product.getPrice(), -product.calculateDiscount());
			}
		});
	}

	/**
	 * set behavior for add quantity button.
	 */
	private void initAddQuantityButton() {
		addQuantity.setCursor(Cursor.HAND);
		addQuantity.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				++quantity;
				if (quantity > 1)
					removeQuantity.setDisable(false);
				quantityLabel.setText("" + quantity);
				amountLabel.setText(InputChecker.price(quantity * product.getPrice()));
				discountLabel.setText(InputChecker.price(quantity * product.calculateDiscount()));
				cartProductOverviewVBox.addToTotalPrice(product.getPrice(), product.calculateDiscount());
			}
		});
	}

	/**
	 * @return quantity * price
	 */
	public double getPrice() {
		return quantity * price;
	}

	/**
	 * @return quantity
	 */
	public int getQuantity() {
		return quantity;
	}
}
