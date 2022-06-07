package customProduct;

import entities.ProductsBase;
import inputs.InputChecker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

/**
 * Component class for a selector product/item in the left side of custom
 * product builder. Will create a SelectedHBox and save it when the user selects
 * the checkBox in this HBox.
 */
public class SelectorHBox extends CustomProductHBox implements ICustomProductHBox {
	private CheckBox selected = new CheckBox();
	private SelectedHBox selectedHBox;
	private Label discountLabel = new Label();

	public SelectorHBox(ProductsBase product) {
		super(product);
	}

	@Override
	public void initHBox() {
		selected.setCursor(Cursor.HAND);
		this.setId("selectorHBox");
		setCheckBoxEvent();
		this.getChildren().add(selected);
		super.initHBox();
		super.initPriceHBox();
		if (product.isDiscount()) {
			amountLabel.setStyle("-fx-strikethrough: true; -fx-font-size: 18px; -fx-font-weight: bold;");
			initDiscount();
		}
	}

	private void initDiscount() {
		discountLabel.setText(InputChecker.price(product.calculateDiscount()));
		amountLabel.setId("originalPriceTxt");
		discountLabel.setId("discountLabel");
		priceVBox.getChildren().add(discountLabel);
	}

	private void setCheckBoxEvent() {
		EventHandler<ActionEvent> eventCheckBox = new EventHandler<>() {
			/**
			 * Create or remove selectedHBox from overview.
			 * 
			 * @param event selection of checkBox
			 */
			@Override
			public void handle(ActionEvent event) {
				if (event.getSource() instanceof CheckBox) {
					if (selected.isSelected()) {
						// init selectedHBox when the product/item is selected
						selectedHBox = new SelectedHBox(product, 1);
						selectedHBox.initHBox();
						CustomProductBuilderController.updateOverViewVBox("add to overview", selectedHBox);
						selected.setSelected(true);
					} else {
						// if not selected - remove the product/item from the overview
						CustomProductBuilderController.updateOverViewVBox("remove from overview", selectedHBox);
						selected.setSelected(false);
					}
				}
			}
		};
		selected.setOnAction(eventCheckBox);
	}

	/**
	 * @return selectedHBox that is associated with this component.
	 */
	public SelectedHBox getSelectedHBox() {
		return selectedHBox;
	}

	/**
	 * @param selectedHBox to bind to this component.
	 */
	public void setSelectedHBox(SelectedHBox selectedHBox) {
		this.selectedHBox = selectedHBox;
	}

	/**
	 * @return checkbox
	 */
	public CheckBox getSelected() {
		return selected;
	}

}
