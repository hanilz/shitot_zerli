package customProduct;

import entities.ProductsBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import util.InputChecker;

public class SelectorHBox extends CustomProductHBox implements ICustomProductHBox {

	private CheckBox selected = new CheckBox();
	private VBox priceVBox = new VBox();
	private Label priceLabel = new Label("Price:");
	private Label amountLabel;
	
	private SelectedHBox selectedHBox;

	public SelectorHBox(ProductsBase product) {
		super(product);
	}

	@Override
	public void initHBox() {
		initPriceVBox();
		setCheckBoxEvent();
		super.initHBox();
		this.getChildren().add(selected);
		this.getChildren().add(priceVBox);
	}

	private void initPriceVBox() {
		amountLabel = new Label(InputChecker.price(product.getPrice()));
		amountLabel.setFont(new Font(23));
		priceVBox.getChildren().add(priceLabel);
		priceVBox.getChildren().add(amountLabel);
		priceVBox.setSpacing(5);
	}

	private void setCheckBoxEvent() {
		EventHandler<ActionEvent> event = new EventHandler<>() {
			@Override
			public void handle(ActionEvent event) {
				if (event.getSource() instanceof CheckBox) {
					if(selected.isSelected()) {
						//init selectedHBox when the product/item is selected
						selectedHBox = new SelectedHBox(product);
						selectedHBox.initHBox();
						CustomProductBuilderController.updateOverViewVBox("add to overview", selectedHBox);
						selected.setSelected(true);
					}
					else {
						//if not selected - remove the product/item from the overview
						selectedHBox = null;
						CustomProductBuilderController.updateOverViewVBox("remove from overview", selectedHBox);
						selected.setSelected(false);
					}
				}
			}
		};
		selected.setOnAction(event);
	}

	public SelectedHBox getSelectedHBox() {
		return selectedHBox;
	}

	public CheckBox getSelected() {
		return selected;
	}

}
