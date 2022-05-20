package customProduct;

import java.util.ArrayList;

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
	
	private ArrayList<SelectedHBox> selectedList = new ArrayList<>();

	public SelectorHBox(ProductsBase product) {
		super(product);
	}

	@Override
	public void initHBox() {
		initPriceVBox();
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

	private void checkSelected() {
		EventHandler<ActionEvent> event = new EventHandler<>() {
			@Override
			public void handle(ActionEvent event) {
				if (event.getSource() instanceof CheckBox) {
					if(selected.isSelected()) {
						//init selectedHBox
						selectedList.add(new SelectedHBox(product));
						for(SelectedHBox current : selectedList)
							current.initHBox();
					}
					else {
						//if not selected
					}
					selected.setSelected(!selected.isSelected());
				}
			}
		};
		selected.setOnAction(event);
	}

}
