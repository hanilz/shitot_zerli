package cart;

import entities.Item;
import inputs.InputChecker;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * Controller for item overview which is used in the cart screen.
 */
public class CartProductOverviewItemVBox {
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
    
    /**
     * Get an Item and set all information accordingly.
     * @param item
     */
    public void initVBox(Item item) {
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
