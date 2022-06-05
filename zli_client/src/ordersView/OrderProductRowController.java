package ordersView;

import catalog.ProductDetailsController;
import catalog.ProductVBox;
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

public class OrderProductRowController {
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


	public void setProductBase(ProductsBase pb,int qty) {
		this.product = pb;
		this.qty = qty;
	}
    
    public void initRow() {
    	productID.setText(""+product.getId());
    	productName.setText(product.getName());
    	productPrice.setText(InputChecker.price(product.getPrice()));
    	productQty.setText(""+qty);
    	productImage.setImage(new Image(product.getImagePath()));
    }
    
    @FXML
    void openDetails(MouseEvent event) {
    	if(product instanceof Product) {
			try {
				ProductDetailsController.setProduct((Product)product);
				if(product instanceof CustomProduct)
					ProductDetailsController.setCustomProduct(((CustomProduct)product).getProducts());
				ManageScreens.openPopupFXML(ProductDetailsController.class.getResource("ProductDetailsPopup.fxml"), product.getName());
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
		else {
			ProductVBox popup = new ProductVBox(product);
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
