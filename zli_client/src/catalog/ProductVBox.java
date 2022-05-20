package catalog;

import entities.ProductsBase;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class ProductVBox extends VBox {
	private ProductsBase product;

	private ImageView image;
	private GridPane mainGrid;
	private Button closeBtn;

	public ProductVBox(ProductsBase p) {
		this.product = p;
	}

	public void initProductVBox() {
		this.setMinHeight(600);
		this.setMinWidth(450);
		this.setPrefHeight(USE_COMPUTED_SIZE);
		this.setAlignment(Pos.TOP_CENTER);
		this.setPadding(new Insets(15, 15, 30, 15));
		
		// set page title
		Label productName;
		productName = new Label("Product Details\n" + product.getName());
		productName.setTextAlignment(TextAlignment.CENTER);
		productName.setFont(new Font("-fx-font-weight: bold", 35));
		this.getChildren().add(productName);

		initImageProduct();
		
		this.getChildren().add(image);
		
		initGrid();
		initCellsInGrid();

		// add gridPane to VBox
		this.getChildren().add(mainGrid);

		// add close button to VBox
		closeBtn = new Button("Close");
		closeBtn.setCursor(Cursor.HAND);
		closeBtn.setOnAction(e -> {
			Stage stage = (Stage) closeBtn.getScene().getWindow();
			// do what you have to do
			stage.close();
		});
		this.getChildren().add(closeBtn);

	}

	private void initCellsInGrid() {
		Label cell;
		// set product product type;
		cell = new Label("Type:");
		cell.setFont(new Font("-fx-font-weight: bold", 23));
		mainGrid.add(cell, 0, 0);
		cell = new Label(product.getType());
		cell.setFont(new Font("-fx-font-weight: bold", 23));
		mainGrid.add(cell, 1, 0);

//		// set product flower type
//		cell = new Label("Flower Type:");
//		cell.setFont(new Font("-fx-font-weight: bold", 23));
//		mainGrid.add(cell, 0, 1);
//		cell = new Label(product.getFlowerType());
//		cell.setFont(new Font("-fx-font-weight: bold", 23));
//		mainGrid.add(cell, 1, 1);
//
//		// set product Description
//		cell = new Label("Description:");
//		cell.setFont(new Font("-fx-font-weight: bold", 23));
//		mainGrid.add(cell, 0, 2);
//		cell = new Label(product.getProductDescription());
//		cell.setWrapText(true);
//		cell.setFont(new Font("-fx-font-weight: bold", 23));
//		mainGrid.add(cell, 1, 2);

		// set product Price
		cell = new Label("Price:");
		cell.setFont(new Font("-fx-font-weight: bold", 23));
		mainGrid.add(cell, 0, 3);
		cell = new Label(product.getPrice() + " ILS");
		cell.setWrapText(true);
		cell.setFont(new Font("-fx-font-weight: bold", 23));
		mainGrid.add(cell, 1, 3);
	}

	private void initGrid() {
		mainGrid = new GridPane();
		mainGrid.setMinWidth(400);
		mainGrid.setPrefWidth(400);
		ColumnConstraints col = new ColumnConstraints(150);
		// ColumnConstraints row = new ColumnConstraints(100);
		mainGrid.getColumnConstraints().add(col);
		mainGrid.setAlignment(Pos.CENTER);
		mainGrid.setHgap(10);
		mainGrid.setVgap(10);
		mainGrid.setPadding(new Insets(20));
	}

	private void initImageProduct() {
		// set product image
		image = new ImageView(product.getImagePath());
		image.setFitHeight(200);
		image.setFitWidth(400);
		image.setPreserveRatio(true);
	}
}
