package catalog;

import entities.Cart;
import entities.Item;
import inputs.InputChecker;
import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CatalogItemVBox extends CatalogVBox implements ICatalogVBox {
	private Item item; // will be used to get the data from item
	private HBox quantityHBox = new HBox();
	private Button subtractQuantityButton = new Button("-");
	private TextField quantityField = new TextField("0");
	private Button addQuantityButton = new Button("+");
	private Text originalPriceTxt;

	public CatalogItemVBox(Item item) {
		this.item = item;
	}

	/**
	 * 
	 */
	public void initVBox() {
		nameLabel.setText(item.getName() + " - " + item.getColor());
		initImageProduct();

		amountLabel.setText("" + InputChecker.price((item.getPrice())));

		// force the field to be numeric only
		quantityField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					quantityField.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
		quantityField.setMaxWidth(50);
		quantityField.setAlignment(Pos.CENTER);

		subtractQuantityButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int quantity = 0;
				try {
					quantity = Integer.valueOf(quantityField.getText());
				} catch (Exception e) {
					return;
				}
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
				} catch (Exception e) {
					return;
				}
				quantityField.setText("" + (++quantity));
			}
		});

		initPriceHBox();
		
		if(item.isDiscount()) {
			discountLabel.setText(InputChecker.price(item.calculateDiscount()));
			originalPriceTxt = new Text(InputChecker.price(item.getPrice()));
			originalPriceTxt.setStrikethrough(true);
			originalPriceTxt.setStyle("-fx-font-size: 16px;");
			priceHBox.getChildren().remove(amountLabel);
			priceHBox.getChildren().add(originalPriceTxt);
			priceHBox.getChildren().add(discountLabel);
			
			amountLabel.setId("priceAfterDiscountLabel");;
		}
		
		initQuantityHBox();

		initAddToCartButton();

		super.initVBox();
		this.getChildren().add(quantityHBox);
		this.getChildren().add(addToCartButton);
	}

	private void initImageProduct() { // same function but not with the event so we will call super.
		image = new ImageView(item.getImagePath());
		setImageProp();

		image.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				ProductVBox popup = new ProductVBox(item);
				popup.initProductVBox();
				Scene scene = new Scene(popup);
				Stage stage = new Stage();
				stage.setTitle("Product Details - " + item.getName());
				stage.setScene(scene);
				stage.showAndWait();
			}
		});
	}

	private void initAddToCartButton() {
		addToCartButton.setCursor(Cursor.HAND);
		addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String quantityString = quantityField.getText();
				if(quantityString.isEmpty())
					quantityField.setText("0");
				int quantity = Integer.valueOf(quantityString);
				if (quantity < 1)
					return;
				Cart.getInstance().addToCart(item, quantity, false);
				System.out.println("Cart is: " + Cart.getInstance().getCart());
				System.out.println(item.getName() + " added to cart woohoooo");
				CatalogController.refreshTotalAmountInCart();
				addToCartButton.setText("Item Added To Cart!");
				PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
				pause.setOnFinished(e -> addToCartButton.setText("Add To Cart"));
				pause.play();
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
