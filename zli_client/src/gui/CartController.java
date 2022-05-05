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
	private static Cart cart = Cart.getInstance();
		
	@FXML
    private Button backToCatalogButton;
	
	@FXML
	private Button buyButton;

    @FXML
    private VBox cartItemVBox;

	@FXML
	private Label priceLabel;
	
	private static CartController instance;

	public void initCart() {
		instance=this;
		Set<Product> products = cart.getCart().keySet();
		System.out.println("Adding all product to cart screen...");

		for (Product product : products) {
			Integer quantity = cart.getCart().get(product);
			System.out.println("product to add is "+ product.getProductName() + " with the quantity " + quantity);
			
			CartHBox productHBox = new CartHBox(product, quantity);
			productHBox.initHBox();
			cartItemVBox.getChildren().add(productHBox);
		}
		refreshTotalPrice();
	}
	
	 @FXML
	    void changeToHomeScreen(MouseEvent event) {
		 try {
				ClientScreen.changeScene(getClass().getResource("HomeNotLoggedInScreen.fxml"), "Catalog Screen");
			} catch (Exception e) {
				e.printStackTrace();
			}

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
	
	public void refreshTotalPrice() {
		priceLabel.setText(cart.getTotalPrice() + " �");
		if(priceLabel.getText().equals("0.0 �"))
		{
			buyButton.setStyle("-fx-background-color: red");
			buyButton.setDisable(true);
		}
		else
		{
			buyButton.setStyle("-fx-background-color: green");
			buyButton.setDisable(false);
		}
	}
	
	public static void connectionWithCartHBox(String command) {
		if(command.contains("refresh total price"))
			instance.refreshTotalPrice();
		else if(command.contains("refresh cart")) {
			instance.resetCart();
			instance.initCart();
		}
	}

	/**
	 * we need to clear the cart VBox in order to prevent duplicates 
	 * from appearing when we need to refresh the cart.
	 */
	private void resetCart() {
		cartItemVBox.getChildren().clear();
	}	
}
