package catalog;

import entities.ProductsBase;
import inputs.InputChecker;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Popup for displaying an Item's details.
 */
public class ItemDetailsVBox extends VBox {
	private ProductsBase productsBase;

	private ImageView image;
	private GridPane mainGrid;
	private Button closeBtn;

	/**
	 * constructor.
	 * @param productsBase
	 */
	public ItemDetailsVBox(ProductsBase productsBase) {
		this.productsBase = productsBase;
	}

	/**
	 * Initialize the Popup's nodes.
	 */
	public void initProductVBox() {
		this.getStylesheets().add("/resources/css/ItemDetailsVBox.css");
		this.setId("itemVBox");
		this.setMinHeight(450);
		this.setMinWidth(350);
		this.setPrefHeight(USE_COMPUTED_SIZE);
		this.setAlignment(Pos.TOP_CENTER);
		this.setPadding(new Insets(15, 15, 30, 15));

		// set page title
		Label productDetails;
		productDetails = new Label("Product Details");
		productDetails.setId("productDetails");
		this.getChildren().add(productDetails);
		Label productName;
		productName = new Label(productsBase.toString());
		productName.setId("productName");
		this.getChildren().add(productName);

		initImageProduct();

		this.getChildren().add(image);

		initGrid();
		initCellsInGrid();

		// add gridPane to VBox
		this.getChildren().add(mainGrid);

		// add close button to VBox
		closeBtn = new Button("Close");
		closeBtn.setOnAction(e -> {
			Stage stage = (Stage) closeBtn.getScene().getWindow();
			// do what you have to do
			stage.close();
		});
		this.getChildren().add(closeBtn);

	}

	/**
	 * Set all the relevant nodes in the relevant cells of the grid.
	 */
	private void initCellsInGrid() {
		mainGrid.add(new Label("Name:"), 0, 0);
		mainGrid.add(new Label(productsBase.getName()), 1, 0);
		mainGrid.add(new Label("Type:"), 0, 1);
		mainGrid.add(new Label(productsBase.getType()), 1, 1);
		mainGrid.add(new Label("Color:"), 0, 2);
		mainGrid.add(new Label(productsBase.getColor()), 1, 2);
		mainGrid.add(new Label("Price:"), 0, 3);
		mainGrid.add(new Label(InputChecker.price(productsBase.calculateDiscount())), 1, 3);
	}

	/**
	 * Initialize grid.
	 */
	private void initGrid() {
		mainGrid = new GridPane();
		mainGrid.setMinWidth(350);
		mainGrid.setPrefWidth(350);
		ColumnConstraints col = new ColumnConstraints(150);
		mainGrid.getColumnConstraints().add(col);
		mainGrid.setAlignment(Pos.CENTER);
		mainGrid.setHgap(10);
		mainGrid.setVgap(10);
		mainGrid.setPadding(new Insets(20));
	}

	/**
	 * Set product image.
	 */
	private void initImageProduct() {
		image = new ImageView(productsBase.getImagePath());
		image.setFitHeight(200);
		image.setFitWidth(350);
		image.setPreserveRatio(true);
	}
}
