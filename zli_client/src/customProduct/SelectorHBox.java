package customProduct;

import entities.ProductsBase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;

public class SelectorHBox extends CustomProductHBox implements ICustomProductHBox {

	private CheckBox selected = new CheckBox();
	private SelectedHBox selectedHBox;

	public SelectorHBox(ProductsBase product) {
		super(product);
	}

	@Override
	public void initHBox() {
		setCheckBoxEvent();
		this.getChildren().add(selected);
		super.initHBox();
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
						//selectedHBox = null;
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
