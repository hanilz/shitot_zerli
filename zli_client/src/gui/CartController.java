package gui;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import entities.Cart;
import entities.Product;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class CartController implements Initializable {
	Cart cart = Cart.getInstance();
	
	private double totalPrice = 0;
	
	@FXML
    private Button backToCatalogButton;
	
	@FXML
	private Button buyButton;

    @FXML
    private VBox cartItemVBox;

	@FXML
	private Label priceLabel;

	public void initCart() {
		Set<Product> products = cart.getCart().keySet();
		System.out.println("Adding all product to cart screen...");

		for (Product product : products) {
			Integer quantity = cart.getCart().get(product);
			System.out.println("product to add is "+ product.getProductName() + " with the quantity " + quantity);
			
			CartHBox productHBox = new CartHBox(product, quantity);
			productHBox.initHBox();
			cartItemVBox.getChildren().add(productHBox);
			totalPrice += productHBox.getTotalSumPrice();
		}
		
		priceLabel.setText(totalPrice + " ₪");
	}

    @FXML
    void changeToCatalogScreen(MouseEvent event) {
		try {
			ClientScreen.changeScene(getClass().getResource("CatalogScreen2.fxml"), "Catalog Screen");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @FXML
    void changeToGreetingCardScreen(MouseEvent event) {
		try {
			ClientScreen.changeScene(getClass().getResource("GreetingCardScreen.fxml"), "Greeting Card Screen");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("Entered cart hellllooo");
		initCart();
	}
}
