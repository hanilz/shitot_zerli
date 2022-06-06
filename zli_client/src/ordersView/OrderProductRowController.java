package ordersView;

import catalog.ProductDetailsController;
import catalog.ItemDetailsVBox;
import entities.CustomProduct;
import entities.Product;
import entities.ProductsBase;
import inputs.InputChecker;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.ManageScreens;

/**OrderProductRowController is used to control a single product row in the orderView FxmlFile
 * it controls an OrderProductRow FXML file
 * must be initialized with a product and a quantity before loading
 * @author Eitan
 *
 */
public class OrderProductRowController{
	private ProductsBase product;
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


	/**used to initialize the product and the quantity 
	 * @param pb
	 * @param qty
	 */
	public void setProductBase(ProductsBase pb,int qty) {
		this.product = pb;
		this.qty = qty;
	}
    
    /**
     * used to set the text and the image for the row
     */
    public void initRow() {
    	productID.setText(""+product.getId());
    	productName.setText(product.getName());
    	productPrice.setText(InputChecker.price(product.getPrice()));
    	productQty.setText(""+qty);
    	productImage.setImage(new Image(product.getImagePath()));
    }
    
    
    /**used to open a product details Pop-up for the product in the order
     * @param event
     */
    @FXML
    void openDetails(MouseEvent event) {
    	if(product instanceof Product) {
			try {
				if(product instanceof CustomProduct)
					ProductDetailsController.setCustomProduct(((CustomProduct)product).getProducts());
				ProductDetailsController.setProduct((Product)product);
				ManageScreens.openPopupFXML(ProductDetailsController.class.getResource("ProductDetailsPopup.fxml"), product.getName());
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
		else {
			ItemDetailsVBox popup = new ItemDetailsVBox(product);
			popup.initProductVBox();
			Scene scene = new Scene(popup);
			Stage stage = new Stage();
			stage.setTitle("Product Details - " + product.getName());
			stage.setScene(scene);
			ManageScreens.addPopup(stage);
			stage.showAndWait();
			ManageScreens.removePopup(stage);
		}
    }
   
}

