package cart;

import entities.Cart;
import entities.Item;
import entities.Product;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import util.ManageScreens;
import util.Screens;

public class CartProductOverviewNoEditVBox extends CartProductOverviewVBox {
	private Button editCustomProductInBuilderButton = new Button("Edit Product");
	private HBox editButtonsHBox = new HBox();
	
	public CartProductOverviewNoEditVBox(Product product) {
		super(product);
	}

	@Override
	public void initVBox() {
		super.initVBox();

		initItemsHBoxes();
		
		super.initTotalPriceHBox();
		
		itemsProductsScrollPane.setPrefHeight(210);
		
		initEditButtonsHBox();
		
		this.getChildren().add(editButtonsHBox);
	}

	private void initItemsHBoxes() {
		for (Item currentItem : product.getItems().keySet()) {
			ProductOverviewNoEditHBox productOverviewHBox = new ProductOverviewNoEditHBox(currentItem, product.getItems().get(currentItem));
			productOverviewHBox.initHBox();
			itemsProductsVBox.getChildren().add(productOverviewHBox);
		}
	}
	
	private void initEditButtonsHBox() {
		editCustomProductInBuilderButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Cart.getInstance().setProductToEdit(product);
				ManageScreens.changeScreenTo(Screens.CUSTOM_PRODUCT_BUILDER);
			}
		});

		editButtonsHBox.getChildren().add(editCustomProductInBuilderButton);
		editButtonsHBox.setAlignment(Pos.CENTER);
	}
}
