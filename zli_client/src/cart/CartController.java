package cart;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import entities.Cart;
import entities.ProductsBase;
import entities.User;
import home.LoginScreenController;
import inputs.InputChecker;
import javafx.animation.PauseTransition;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import util.ManageScreens;
import util.Screens;

public class CartController implements Initializable {
	private static Cart cart = Cart.getInstance();

	@FXML
	private Button backToCatalogButton;

	@FXML
	private Button buyButton;

	@FXML
	private VBox cartItemVBox;

    @FXML
    private Label fillAllQuantitiesField;
	
	@FXML
	private Label priceLabel;
	
	@FXML
	private Pane overviewPane;

	@FXML
	private Button emptyCartButton;

	public static CartController instance;

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

	private boolean areFieldsEmpty() {
		boolean result = false;
		for(Node node : cartItemVBox.getChildren()) 
			if(node instanceof CartHBox) {
				CartHBox currentHBox = (CartHBox) node;
				if(currentHBox.getQuantityField().getText().isEmpty()) {
					currentHBox.getQuantityField().setText("1");
					result = true;
				}
		}
		return result;
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
		if(areFieldsEmpty()) {
			showFillAllQuantitiesLabel();
			return;
		}
		LoginScreenController.resetLogin();
		if (!User.getUserInstance().isUserLoggedIn())// guest tries to buy
		{
			LoginScreenController.enableCartPopup(true);// enable popup
			ManageScreens.changeScreenTo(Screens.LOGIN);
		}
		changeToGreatingCard();
	}

	public static void changeToGreatingCard() {
		if (User.getUserInstance().isUserLoggedIn()) {
			ManageScreens.changeScreenTo(Screens.GREETING_CARD);
		}
	}

	void showFillAllQuantitiesLabel() {
		fillAllQuantitiesField.setText("* Please fill all the fields of the quantities!");
		PauseTransition pause = new PauseTransition(Duration.seconds(3));
		pause.setOnFinished(e -> fillAllQuantitiesField.setText(""));
		pause.play();
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
		overviewPane.getChildren().clear();
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

	public void clearCartOverview(ProductsBase product) {
		if(overviewPane.getChildren().isEmpty())
			return;
		VBox productOverview =(VBox)overviewPane.getChildren().get(0);
		String title = ((Label)productOverview.getChildren().get(0)).getText();
		if(product.getName().equals(title))
			overviewPane.getChildren().clear();
	}
}
