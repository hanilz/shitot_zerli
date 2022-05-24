package cart;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import entities.Cart;
import entities.ProductsBase;
import entities.User;
import home.LoginScreenController;
import inputs.InputChecker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.ManageScreens;
import util.Screens;
import util.UserType;

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

    @FXML
    private Pane overviewPane;
	
    @FXML
    private Button emptyCartButton;

	private static CartController instance;

	public void initCart() {
		instance = this;
		Set<ProductsBase> totalCart = cart.getCart().keySet();
		
		System.out.println("Adding all product to cart screen...");

		for (ProductsBase currentItem : totalCart) {
			Integer quantity = cart.getCart().get(currentItem);
			System.out.println("product to add is " + currentItem.getName() + " with the quantity " + quantity);
			CartHBox productHBox = new CartHBox(currentItem, quantity);
			productHBox.initHBox();
			cartItemVBox.getChildren().add(productHBox);
		}
		refreshTotalPrice();
	}

	@FXML
	void changeToHomeScreen(MouseEvent event) {
		ManageScreens.home();
	}

	@FXML
	void changeToCatalogScreen(MouseEvent event) {
		ManageScreens.changeScreenTo(Screens.CATALOG);
	}

	@FXML
	void buy(MouseEvent event) {
		if (!User.getUserInstance().isUserLoggedIn())// guest tries to buy
		{
			Parent popup;
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("../home/LoginScreen.fxml"));
				popup = loader.load();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
			Scene scene = new Scene(popup);
			Stage stage = new Stage();
			stage.setScene(scene);
			LoginScreenController.enablePopup(true);// enable popup
			ManageScreens.addPopup(stage);
			stage.showAndWait();
			ManageScreens.removePopup(stage);
			LoginScreenController.enablePopup(false);// disable popup
			if(User.getUserInstance().getType()!=UserType.CUSTOMER)//need to FIX!!!!!need to show message only customers can buy
				User.getUserInstance().logout();
		}
		if (User.getUserInstance().isUserLoggedIn())
			ManageScreens.changeScreenTo(Screens.GREATING_CARD);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println(cart);
		initCart();
	}

	public void refreshTotalPrice() {
		priceLabel.setText(InputChecker.price(cart.getTotalPrice()));
		if (cart.isCartEmpty()) {
			buyButton.setStyle("-fx-background-color: red");
			buyButton.setDisable(true);
			emptyCartButton.setVisible(false);
		} else {
			buyButton.setStyle("-fx-background-color: green");
			buyButton.setDisable(false);
		}
	}

	public static void connectionWithCartHBox(String command) {
		if (command.contains("refresh total price"))
			instance.refreshTotalPrice();
		else if (command.contains("refresh cart")) {
			instance.resetCart();
			instance.initCart();
		}
	}
	
    @FXML
    void emptyCart(MouseEvent event) {
    	cart.emptyCart();
    	connectionWithCartHBox("refresh cart");
    }
    
    public static void setOverviewVBox(VBox vBox) {
    	instance.setOverviewScrollPane(vBox);
    }
    
    private void setOverviewScrollPane(VBox vBox) {
    	overviewPane.getChildren().clear();
    	overviewPane.getChildren().add(vBox);
    }
    
	/**
	 * we need to clear the cart VBox in order to prevent duplicates from appearing
	 * when we need to refresh the cart.
	 */
	private void resetCart() {
		cartItemVBox.getChildren().clear();
	}
}
