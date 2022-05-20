package customProduct;

import entities.ProductsBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import util.InputChecker;

public class SelectedHBox extends CustomProductHBox implements ICustomProductHBox {
	private int quantity=1;
	private HBox quantityHBox = new HBox();
	private Label quantityLabel = new Label("" + quantity);
	private Button addQuantity = new Button("+");
	private Button removeQuantity = new Button("-");
	private Button removeButton = new Button("X");
	private SelectedHBox selectedProduct;

	public SelectedHBox(ProductsBase product) {
		super(product);
	}

	@Override
	public void initHBox() {
		selectedProduct = this;
		initQuantityVBox();
		removeButton.setCursor(Cursor.HAND);
		removeButton.setStyle("-fx-background-color : Red ; -fx-font-size:16 ; -fx-font-weight: bold");
		removeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				CustomProductBuilderController.updateOverViewVBox("remove from overview", selectedProduct);
			}
		});
		super.initHBox();
		this.getChildren().add(quantityHBox);
		this.getChildren().add(removeButton);
	}
	
	private void initQuantityVBox() {
		addQuantity.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	++quantity;
            	if(quantity > 1)
            		removeQuantity.setDisable(false);
                quantityLabel.setText("" + quantity);
                amountLabel.setText(InputChecker.price(quantity * product.getPrice()));
                CustomProductBuilderController.updateTotalPriceLabel();
            }
        });
		
		removeQuantity.setDisable(true);
		removeQuantity.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	--quantity;
            	if(quantity == 1)
            		removeQuantity.setDisable(true);
                quantityLabel.setText("" + quantity);
                amountLabel.setText(InputChecker.price(quantity * product.getPrice()));
                CustomProductBuilderController.updateTotalPriceLabel();
            }
        });

		quantityHBox.setAlignment(Pos.CENTER);
		quantityHBox.setSpacing(10);

		quantityHBox.getChildren().add(removeQuantity);
		quantityHBox.getChildren().add(quantityLabel);
		quantityHBox.getChildren().add(addQuantity);

	}

	public int getQuantity() {
		return quantity;
	}

}
