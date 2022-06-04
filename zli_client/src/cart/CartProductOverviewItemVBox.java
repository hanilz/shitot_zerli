package cart;

import entities.Item;
import inputs.InputChecker;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class CartProductOverviewItemVBox {
	private Item item;
	private Label discountLabel;
	
    @FXML
    private Label itemColorLabel;

    @FXML
    private ImageView itemImage;

    @FXML
    private Label itemNameLabel;

    @FXML
    private Label itemNameTopLabel;

    @FXML
    private Label itemTypeLabel;

    @FXML
    private HBox priceHBox;

    @FXML
    private Label priceLabel;
    
    public void initVBox(Item item) {
    	this.item = item;
    	itemColorLabel.setText(item.getColor());
    	itemImage.setImage(new Image(item.getImagePath()));
    	itemNameTopLabel.setText(item.getName() + " - " + item.getColor());
    	itemNameLabel.setText(item.getName());
    	itemTypeLabel.setText(item.getType());
    	if(item.isDiscount()) {
    		discountLabel = new Label(InputChecker.price(item.calculateDiscount()));
    		priceHBox.getChildren().add(discountLabel);
    	}
    }
}