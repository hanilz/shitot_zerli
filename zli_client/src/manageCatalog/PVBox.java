package manageCatalog;

import java.util.ArrayList;
import java.util.HashMap;

import client.ClientFormController;
import entities.Item;
import entities.Product;
import entities.ProductsBase;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import util.Commands;
import util.ManageScreens;
import util.Screens;

public class PVBox extends VBox {
	private ProductsBase product;
	private ImageView image;
	private GridPane mainGrid;
	private Button closeBtn, removeBtn, saveEditBtn;
	private boolean isProduct;
	private ArrayList<TextInputControl> texts = new ArrayList<>();

	public PVBox(ProductsBase p) {
		this.product = p;

	}

	public void initProductVBox() {
		if (product instanceof Product)
			isProduct = true;
		this.setMinHeight(600);
		this.setMinWidth(500);
		this.setPrefHeight(USE_COMPUTED_SIZE);
		this.setAlignment(Pos.TOP_CENTER);
		this.setPadding(new Insets(15, 15, 30, 15));
		initImageProduct();
		this.getChildren().add(image);
		initGrid();
		initCellsInGrid();
		this.getChildren().add(mainGrid);
		HBox buttons = new HBox();
		buttons.setSpacing(15);
		buttons.setAlignment(Pos.CENTER);
		closeBtn = new Button("Close");
		closeBtn.setCursor(Cursor.HAND);
		closeBtn.setOnAction(e -> {
			close(closeBtn);
		});
		removeBtn = new Button("Remove");
		removeBtn.setCursor(Cursor.HAND);
		saveEditBtn = new Button("Save");
		saveEditBtn.setCursor(Cursor.HAND);
		saveEditBtn.setOnAction(e -> {
			HashMap<String, Object> message = new HashMap<>();
			message.put("command", Commands.UPDATE_PRODUCTS_BASE);
			message.put("type", isProduct ? "product" : "item");
			if (isProduct) {
				Product copy = (Product) product;
				copy.setName(texts.get(0).getText());
				copy.setType(texts.get(1).getText());
				copy.setFlowerType(texts.get(2).getText());
				copy.setProductDescription(texts.get(3).getText());
				copy.setPrice(Integer.valueOf(texts.get(4).getText()));
				message.put("unit",copy);
			}
			 else {
				 Item copy = (Item) product;
					copy.setName(texts.get(0).getText());
					copy.setType(texts.get(1).getText());
					copy.setColor(texts.get(2).getText());
					copy.setPrice(Integer.valueOf(texts.get(3).getText()));
					message.put("unit",copy);
			 }
			 message.put("item", Commands.UPDATE_PRODUCTS_BASE);
			 ClientFormController.client.accept(message);
			 close(saveEditBtn);
			 ManageScreens.changeScreenTo(Screens.EDIT_CATALOG);
		});
		buttons.getChildren().addAll(closeBtn, removeBtn, saveEditBtn);
		this.getChildren().addAll(buttons);
		

	}
	private void close(Button btn)
	{
		 Stage stage = (Stage) btn.getScene().getWindow();
			stage.close();
	}
	private void addSubjectCell(String lableSubject, int y) {
		Label cell;
		cell = new Label(lableSubject + ":");
		cell.setWrapText(true);
		cell.setFont(new Font("-fx-font-weight: bold", 23));
		mainGrid.add(cell, 0, y);
	}

	private void addTextCell(String lableSubject, int y, String LableSpecs) {
		addSubjectCell(lableSubject,y);
		TextField data;
		data = new TextField(LableSpecs);
		data.setFont(new Font("-fx-font-weight: bold", 23));
		data.autosize();
		mainGrid.add(data, 1, y);
		texts.add(data);

	}

	private void addLableCell(String lableSubject, int y, String LableSpecs) {
		Label cell;
		addSubjectCell(lableSubject,y);
		cell = new Label(LableSpecs);
		cell.setWrapText(true);
		cell.setFont(new Font("-fx-font-weight: bold", 23));
		mainGrid.add(cell, 1, y);
	}
	private void addTextAreaCell(String lableSubject, int y, String LableSpecs,int sx,int sy) {
		TextArea cell;
		addSubjectCell(lableSubject,y);
		cell = new TextArea(LableSpecs);
		cell.setPrefSize(sx,sy);
		cell.setWrapText(true);
		cell.setFont(new Font("-fx-font-weight: bold", 23));
		mainGrid.add(cell, 1, y);
		texts.add(cell);
	}

	private void initCellsInGrid() {
		int amount = 0;
		addLableCell("ID", amount++, "" + product.getId());
		addTextAreaCell("Name", amount++, product.getName(),700,30);
		addTextCell("Type", amount++, product.getType());
		if (isProduct) {
			addTextCell("Flower Type", amount++, ((Product) product).getFlowerType());
			addTextAreaCell("Description", amount++, ((Product) product).getProductDescription(),1000,150);
		} else
			addTextCell("Color", amount++, (((Item) product).getColor()));
		addTextCell("Price", amount++, ("" + (int)product.getPrice()));
	}

	private void initGrid() {
		mainGrid = new GridPane();
		mainGrid.setMinWidth(300);
		mainGrid.setPrefWidth(300);
		ColumnConstraints col = new ColumnConstraints(150);
		mainGrid.getColumnConstraints().add(col);
		mainGrid.setAlignment(Pos.CENTER);
		mainGrid.setHgap(10);
		mainGrid.setVgap(10);
		mainGrid.setPadding(new Insets(20));
	}

	private void initImageProduct() {
		image = new ImageView(product.getImagePath());
		image.setFitHeight(200);
		image.setFitWidth(400);
		image.setPreserveRatio(true);
	}
}
