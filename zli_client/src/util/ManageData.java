package util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import catalog.CatalogItemVBox;
import catalog.CatalogProductVBox;
import catalog.CatalogVBox;
import client.ClientFormController;
import customProduct.CustomProductHBox;
import customProduct.SelectorHBox;
import entities.Item;
import entities.Product;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

public class ManageData {
	public static ArrayList<Product> products;
	public static ArrayList<Item> items;
	public static boolean isLoaded = false;
	public static GridPane catalogGrid = new GridPane();
	public static VBox customSelectorVBox = new VBox();

	public static int customProductNumber = 1;
	private static final int NUMBER_OF_COLUMNS = 3;

	private static ArrayList<CatalogVBox> catalogVBoxList = new ArrayList<>();
	private static ArrayList<CustomProductHBox> customProductsCheckBox = new ArrayList<>();

	@SuppressWarnings("unchecked")
	public static void fetchAllProducts() {
		if(isLoaded)
			clearAllData();
		isLoaded = true;
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.FETCH_PRODUCTS);
		Object response = ClientFormController.client.accept(message);
		products = (ArrayList<Product>) response;
	}

	private static void clearAllData() {
		products.clear();
		items.clear();
		catalogVBoxList.clear();
		customProductsCheckBox.clear();
		customSelectorVBox.getChildren().clear();
		catalogGrid.getChildren().clear();
		customProductNumber = 1;
	}

	@SuppressWarnings("unchecked")
	public static void fetchAllItems() {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.FETCH_ITEMS);
		Object response = ClientFormController.client.accept(message);
		items = (ArrayList<Item>) response;
	}

	public static void initCatalogGrid() {
		initCatalogProductVBoxes();
		initCatalogItemVBoxes();
		catalogGrid = initCellsInGrid(
				(int) Math.ceil((products.size() + items.size() + 1) / ((float) NUMBER_OF_COLUMNS)));
		initCatalogVBoxListToGrid(catalogGrid);
		initCustomProductInCatalog();
	}

	private static GridPane initCellsInGrid(int numberOfRows) {
		GridPane grid = new GridPane();
		for (int i = 0; i < numberOfRows; i++) {
			RowConstraints rowConst = new RowConstraints();
			rowConst.setPercentHeight(100.0 / numberOfRows);
			grid.getRowConstraints().add(rowConst);
		}
		grid.setVgap(20);
		return grid;
	}

	private static void initCatalogVBoxListToGrid(GridPane grid) {
		for (int i = 0; i < catalogVBoxList.size(); i++) {
			grid.add(catalogVBoxList.get(i), (i + 1) % NUMBER_OF_COLUMNS, (i + 1) / NUMBER_OF_COLUMNS);
		}
	}

	private static void initCustomProductInCatalog() {
		VBox customProductVBox = new VBox();
		customProductVBox.setAlignment(Pos.CENTER);
		ImageView customProductImage = new ImageView("/resources/catalog/customProductImage.png");
		initCustomProductImage(customProductImage);
		customProductImage.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				ManageScreens.changeScreenTo(Screens.CUSTOM_PRODUCT_BUILDER);
			}
		});
		customProductVBox.setId("customProductCard");
		customProductVBox.getChildren().add(customProductImage);
		catalogGrid.setId("catalogGrid");
		catalogGrid.add(customProductVBox, 0, 0); // set as first VBox in Grid
	}

	private static void initCustomProductImage(ImageView customProductImage) {
		customProductImage.setFitHeight(200);
		customProductImage.setFitWidth(300);
		customProductImage.setPreserveRatio(true);
		customProductImage.setCursor(Cursor.HAND);
	}

	private static void initCatalogItemVBoxes() {
		for (int i = 0; i < items.size(); i++) {
			CatalogItemVBox catalogItemVBox = new CatalogItemVBox(items.get(i));
			catalogItemVBox.initVBox();
			catalogItemVBox.setId("catalogCard");
			catalogVBoxList.add(catalogItemVBox);
		}
	}

	private static void initCatalogProductVBoxes() {
		for (int i = 0; i < products.size(); i++) {
			CatalogProductVBox catalogProductVBox = new CatalogProductVBox(products.get(i));
			catalogProductVBox.initVBox();
			catalogProductVBox.setId("catalogCard");
			catalogVBoxList.add(catalogProductVBox);
		}
		products.sort(new Comparator<Product>() {
			public int compare(Product a, Product b) {
				return a.compareTo(b);
			}
		});
	}

	public static void initCustomProductItemHBoxes() {
		customSelectorVBox.setId("customSelectorVBox");
		for (int i = 0; i < items.size(); i++) {
			CustomProductHBox customProductItemHBox = new SelectorHBox(items.get(i));
			customProductItemHBox.initHBox();
			customProductsCheckBox.add(customProductItemHBox);
			customSelectorVBox.getChildren().add(customProductItemHBox);
		}
	}

	public static void initCustomProductProductsHBoxes() {
		for (int i = 0; i < products.size(); i++) {
			CustomProductHBox customProductHBox = new SelectorHBox(products.get(i));
			customProductHBox.initHBox();
			customProductsCheckBox.add(customProductHBox);
			customSelectorVBox.getChildren().add(customProductHBox);
		}
	}

	public static ArrayList<CustomProductHBox> getCustomProductsCheckBox() {
		return customProductsCheckBox;
	}
}