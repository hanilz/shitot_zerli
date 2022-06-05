package customProduct;

import entities.ProductsBase;
import inputs.InputChecker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class SelectorHBox extends CustomProductHBox implements ICustomProductHBox {

	private CheckBox selected = new CheckBox();
	private SelectedHBox selectedHBox;
	private Label discountLabel = new Label();
	
	public SelectorHBox(ProductsBase product) {
		super(product);
	}

	@Override
	public void initHBox() {
		this.setId("selectorHBox");
		this.setCursor(Cursor.HAND);
		setCheckBoxEvent();
		this.getChildren().add(selected);
		super.initHBox();
		super.initPriceHBox();
		if(product.isDiscount())
			initDiscount();
	}

	private void initDiscount() {
		discountLabel.setText(InputChecker.price(product.calculateDiscount()));
		amountLabel.setId("originalPriceTxt");
		discountLabel.setId("discountLabel");
		priceVBox.getChildren().add(discountLabel);
	}

	private void setCheckBoxEvent() {
		EventHandler<MouseEvent> eventHBox = new EventHandler<>() {
			@Override
			public void handle(MouseEvent event) {
				if(selected.isSelected()) 
					selected.setSelected(false);
				else
					selected.setSelected(true);
				if(selected.isSelected()) {
					//init selectedHBox when the product/item is selected
					selectedHBox = new SelectedHBox(product, 1);
					selectedHBox.initHBox();
					CustomProductBuilderController.updateOverViewVBox("add to overview", selectedHBox);
					selected.setSelected(true);
				}
				else {
					//if not selected - remove the product/item from the overview
					//selectedHBox = null;
					CustomProductBuilderController.updateOverViewVBox("remove from overview", selectedHBox);
					selected.setSelected(false);
				}
			}
		};
		EventHandler<ActionEvent> eventCheckBox = new EventHandler<>() {
			@Override
			public void handle(ActionEvent event) {
				if (event.getSource() instanceof CheckBox) {
					if(selected.isSelected()) {
						//init selectedHBox when the product/item is selected
						selectedHBox = new SelectedHBox(product, 1);
						selectedHBox.initHBox();
						CustomProductBuilderController.updateOverViewVBox("add to overview", selectedHBox);
						selected.setSelected(true);
					}
					else {
						//if not selected - remove the product/item from the overview
						//selectedHBox = null;
						CustomProductBuilderController.updateOverViewVBox("remove from overview", selectedHBox);
						selected.setSelected(false);
					}
				}
			}
		};
		this.setOnMouseReleased(eventHBox);
		selected.setOnAction(eventCheckBox);
	}

	public SelectedHBox getSelectedHBox() {
		return selectedHBox;
	}
	
	public void setSelectedHBox(SelectedHBox selectedHBox) {
		this.selectedHBox = selectedHBox;
	}

	public CheckBox getSelected() {
		return selected;
	}

}
