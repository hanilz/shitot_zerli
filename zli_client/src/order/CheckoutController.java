package order;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import entities.Cart;
import entities.Product;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import util.ManageScreens;
import util.Screens;
import util.InputChecker;

public class CheckoutController implements Initializable{

    @FXML
    private Button backButton;

    @FXML
    private TextField cardNumberField;

    @FXML
    private TextField csvField;

    @FXML
    private TextField firstNameField;

    @FXML
    private ImageView homeButton;

    @FXML
    private TextField idField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField monthField;

    @FXML
    private VBox orderSummaryVBox;

    @FXML
    private Button placeOrderButton;

    @FXML
    private TextField yearField;
    
    @FXML
    private Label totalPriceLabel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Set<Product> products = Cart.getInstance().getCart().keySet();
		System.out.println("Adding all product to cart screen...");

		for (Product product : products) {
			Integer quantity = Cart.getInstance().getCart().get(product);
			System.out.println("product to add is "+ product.getProductName() + " with the quantity " + quantity);
			
			OrderSummaryHBox productSummaryHBox = new OrderSummaryHBox(product, quantity);
			productSummaryHBox.initHBox();
			orderSummaryVBox.getChildren().add(productSummaryHBox);
		}
		totalPriceLabel.setText(InputChecker.price(Cart.getInstance().getTotalPrice()));
	}
	
    @FXML
    void changeToDeliveryScreen(MouseEvent event) {
    	ManageScreens.changeScreenTo(Screens.DELIVERY_DETAILS);
    }

    @FXML
    void placeOrder(MouseEvent event) {
    	try {
    		ManageScreens.openPopupFXML(getClass().getResource("PaymentSuccessfulPopup.fxml"), "Payment Successful!");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void changeToHome(MouseEvent event) {
    	ManageScreens.home();
    }
}
