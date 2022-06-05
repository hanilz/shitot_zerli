package cart;

import entities.Item;
import inputs.InputChecker;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

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
    private Text priceLabel;
    
    public void initVBox(Item item) {
    	this.item = item;
    	itemColorLabel.setText(item.getColor());
    	itemImage.setImage(new Image(item.getImagePath()));
    	itemNameTopLabel.setText(item.getName() + " - " + item.getColor());
    	itemNameLabel.setText(item.getName());
    	itemTypeLabel.setText(item.getType());
    	priceLabel.setText(InputChecker.price(item.getPrice()));
    	if(item.isDiscount()) {
    		priceLabel.setId("originalPriceLabel");
    		discountLabel = new Label(InputChecker.price(item.calculateDiscount()));
    		discountLabel.setId("discountLabel");
    		priceHBox.getChildren().add(discountLabel);
    	}
    }
}
