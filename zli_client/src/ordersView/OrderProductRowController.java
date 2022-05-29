package ordersView;

import entities.ProductsBase;
import inputs.InputChecker;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class OrderProductRowController {
	private ProductsBase pb;
	private int qty;
	
    @FXML
    private Label productID;

    @FXML
    private ImageView productImage;

    @FXML
    private Label productName;

    @FXML
    private Label productPrice;

    @FXML
    private Label productQty;


	public void setProductBase(ProductsBase pb,int qty) {
		this.pb = pb;
		this.qty = qty;
	}
    
    public void initRow() {
    	productID.setText("Poduct ID: "+pb.getId());
    	productName.setText("Product Name:\n"+pb.getName());
    	productPrice.setText("Price: "+InputChecker.price(pb.getPrice()));
    	productQty.setText("QTY: "+qty);
    	productImage.setImage(new Image(pb.getImagePath()));
    }
}
