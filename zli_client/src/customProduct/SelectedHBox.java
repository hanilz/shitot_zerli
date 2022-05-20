package customProduct;

import entities.ProductsBase;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class SelectedHBox extends CustomProductHBox implements ICustomProductHBox {
	private int quantity;
	private VBox quantityVBox = new VBox();
	private Label quantityLabel = new Label("Quantity");
	private TextField quantityField = new TextField();
	private Button removeButton = new Button("X");

	public SelectedHBox(ProductsBase product) {
		super(product);
	}

	@Override
	public void initHBox() {
		initQuantityVBox();
		removeButton.setCursor(Cursor.HAND);
		removeButton.setStyle("-fx-background-color : Red ; -fx-font-size:16 ; -fx-font-weight: bold");
		removeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				// TODO - refresh the selected products/items component?
			}
		});
		this.getChildren().add(removeButton);
		this.getChildren().add(quantityVBox);
		super.initHBox();
	}
	
	private void initQuantityVBox() {
		quantityField.setText("" + quantity);
		quantityField.setAlignment(Pos.CENTER);
		quantityField.setMinWidth(50);
		quantityField.setPrefWidth(50);
		quantityField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				//TODO - change total price label
			}
		});

		quantityVBox.setAlignment(Pos.CENTER);
		quantityVBox.setSpacing(10);

		quantityVBox.getChildren().add(quantityLabel);
		quantityVBox.getChildren().add(quantityField);
	}

}
