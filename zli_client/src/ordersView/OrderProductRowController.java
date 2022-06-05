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
//    	System.out.println(product);
//    	System.out.println(((Product)product).getItems());
//    	if(product instanceof CustomProduct) {
//    		System.out.println(((CustomProduct) product).getProducts());    		
//    	}
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

