package util;

import java.util.ArrayList;
import java.util.HashMap;

import catalog.CatalogItemVBox;
import catalog.CatalogProductVBox;
import catalog.CatalogVBox;
import client.ClientFormController;
import customProduct.CustomProductHBox;
import customProduct.SelectorHBox;
import entities.Item;
import entities.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import manageCatalog.ManageCatalogVBox;

public class ManageData {
	public static ObservableList<Product> products = FXCollections.observableArrayList();
	public static ObservableList<Item> items = FXCollections.observableArrayList();
	public static GridPane catalogGrid = new GridPane();
	public static GridPane manageGrid = new GridPane();
	public static VBox customSelectorVBox = new VBox();

	public static int customProductNumber = 1;
	private static final int NUMBER_OF_COLUMNS = 3;

	private static ArrayList<CatalogVBox> catalogVBoxList = new ArrayList<>();
	private static ArrayList<ManageCatalogVBox> manageVBoxList = new ArrayList<>();
	private static ArrayList<CustomProductHBox> customProductsCheckBox = new ArrayList<>();

	public static void fetchAllProducts() {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.FETCH_PRODUCTS);
		Object response = ClientFormController.client.accept(message);
		products = (ObservableList<Product>) response;
	}

	public static void fetchAllItems() {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.FETCH_ITEMS);
		Object response = ClientFormController.client.accept(message);
		items = (ObservableList<Item>) response;
	}

	public static void initCatalogGrid() {
		initCatalogProductVBoxes();
		initCatalogItemVBoxes();
		catalogGrid = initCellsInGrid(
				(int) Math.ceil((products.size() + items.size() + 1) / ((float) NUMBER_OF_COLUMNS)));
		initCatalogVBoxListToGrid(catalogGrid);
		initCustomProductInCatalog();
		initManageCatalog();
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
	
	private static void initManageVBoxListToGrid(GridPane grid) {
		for (int i = 0; i < manageVBoxList.size(); i++) {
			grid.add(manageVBoxList.get(i), (i + 1) % NUMBER_OF_COLUMNS, (i + 1) / NUMBER_OF_COLUMNS);
		}
	}

	private static void initManageItemVBoxes() {
		for (int i = 0; i < ManageData.products.size(); i++) {
			ManageCatalogVBox catalogProductVBox = new ManageCatalogVBox(ManageData.products.get(i));
			catalogProductVBox.initVBox();
			manageVBoxList.add(catalogProductVBox);
		}
		for (int i = 0; i < ManageData.items.size(); i++) {
			ManageCatalogVBox catalogItemVBox = new ManageCatalogVBox(ManageData.items.get(i));
			catalogItemVBox.initVBox();
			manageVBoxList.add(catalogItemVBox);
		}
	}

	public static void initManageCatalog() {
		initManageItemVBoxes();
		manageGrid = initCellsInGrid((int) Math.ceil((products.size() + items.size()) / 3.0));
		initManageVBoxListToGrid(manageGrid);
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
	}

	public static void initCustomProductItemHBoxes() {
		customSelectorVBox.setPrefWidth(400);
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
