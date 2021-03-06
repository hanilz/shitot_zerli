package cart;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import entities.Cart;
import entities.CustomProduct;
import entities.Item;
import entities.Product;
import entities.ProductsBase;
import entities.User;
import home.LoginScreenController;
import inputs.InputChecker;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import util.ManageScreens;
import util.Screens;

/**
 * Cart screen controller class
 */
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
	private Text priceLabel;

	@FXML
	private Pane overviewPane;

	@FXML
	private VBox totalPriceVBox;

	@FXML
	private Button emptyCartButton;

	private Label discountLabel = new Label("bla");

	public static CartController instance;

	/**
	 * Initialize the Cart ScrollPane based on the ProductsBase in the cart.
	 */
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

	/**
	 * @return true if a field is empty, false otherwise
	 */
	private boolean areFieldsEmpty() {
		boolean result = false;
		for (Node node : cartItemVBox.getChildren())
			if (node instanceof CartHBox) {
				CartHBox currentHBox = (CartHBox) node;
				if (currentHBox.getQuantityField().getText().isEmpty()) {
					currentHBox.getQuantityField().setText("1");
					result = true;
				}
			}
		return result;
	}

	/**
	 * Move to home screen.
	 * 
	 * @param event
	 */
	@FXML
	void changeToHomeScreen(MouseEvent event) {
		ManageScreens.home();
	}

	/**
	 * Move to catalog screen.
	 * 
	 * @param event
	 */
	@FXML
	void changeToCatalogScreen(MouseEvent event) {
		ManageScreens.changeScreenTo(Screens.CATALOG);
	}

	/**
	 * Attempt to move to GreetingCard Screen - if not logged in as user, open a
	 * login popup.
	 * @param event
	 */
	@FXML
	void buy(MouseEvent event) {
		if (areFieldsEmpty()) {
			showFillAllQuantitiesLabel();
			return;
		}
		if (!User.getUserInstance().isUserLoggedIn())// guest tries to buy
		{
			LoginScreenController instance = new LoginScreenController();
			LoginScreenController.setInstance(instance);
			LoginScreenController.loginFromCart();// enable popup
		}
		changeToGreatingCard();
	}

	/**
	 * Move to GreetingCard screen.
	 */
	public static void changeToGreatingCard() {
		if (User.getUserInstance().isUserLoggedIn()) {
			ManageScreens.changeScreenTo(Screens.GREETING_CARD);
		}
	}

	/**
	 * Show a warning label if one of the quantity labels is 0.
	 */
	void showFillAllQuantitiesLabel() {
		fillAllQuantitiesField.setText("* Please fill all the fields of the quantities!");
		PauseTransition pause = new PauseTransition(Duration.seconds(3));
		pause.setOnFinished(e -> fillAllQuantitiesField.setText(""));
		pause.play();
	}

	/**
	 * Initialize the cart ScrollPane and show the first ProductBase's overview on the right.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println(cart);
		initCart();
		if (!cartItemVBox.getChildren().isEmpty()) {
			CartHBox hbox = (CartHBox) cartItemVBox.getChildren().get(0);
			createOverviewVBox(hbox.getProduct());
		}
	}

	/**
	 * Create an overview of a productsBase and display it on the right side
	 * @param product
	 */
	public static void createOverviewVBox(ProductsBase product) {
		if (product instanceof CustomProduct) {
			CartProductOverviewVBox overviewVBox = null;
			CustomProduct customProduct = (CustomProduct) product;
			overviewVBox = new CartProductOverviewEditVBox(customProduct);
			overviewVBox.initVBox();
			setOverviewVBox(overviewVBox);
		} else if (product instanceof Product) {
			CartProductOverviewVBox overviewVBox = null;
			Product currentProduct = (Product) product;
			overviewVBox = new CartProductOverviewNoEditVBox(currentProduct);
			overviewVBox.initVBox();
			setOverviewVBox(overviewVBox);
		} else { // Item
			Item currentItem = (Item) product;
			VBox overviewVBox = null;
			try {
				FXMLLoader loader = new FXMLLoader(
						CartProductOverviewItemVBox.class.getResource("CartProductOverviewItemVBox.fxml"));
				overviewVBox = (VBox) loader.load();
				((CartProductOverviewItemVBox) loader.getController()).initVBox(currentItem);
			} catch (IOException e) {
				e.printStackTrace();
			}
			setOverviewVBox(overviewVBox);
		}
	}

	/**
	 * Refresh the total price and discount labels.
	 */
	public void refreshTotalPrice() {
		priceLabel.setText(InputChecker.price(cart.getTotalPrice()));
		discountLabel.setText(InputChecker.price(cart.getTotalDiscountPrice()));
		if (cart.isCartEmpty()) {
			buyButton.setStyle("-fx-background-color: red");
			buyButton.setDisable(true);
			emptyCartButton.setVisible(false);
			if (isDiscountInPrice())
				totalPriceVBox.getChildren().remove(discountLabel);
		} else {
			buyButton.setStyle("-fx-background-color: #55a630");
			buyButton.setDisable(false);
			initDiscountLabel();
		}
	}

	/**
	 * If there is a product with a discount in the cart, display the discount label.
	 */
	private void initDiscountLabel() {
		if (isDiscount()) {
			discountLabel.setId("discountLabel");
			priceLabel.setId("originalPriceLabel");
			if (!isDiscountInPrice())
				totalPriceVBox.getChildren().add(discountLabel);
		} else {
			if (isDiscountInPrice())
				totalPriceVBox.getChildren().remove(discountLabel);
			priceLabel.setStrikethrough(false);
		}
	}

	/**
	 * Used in order to access non-static methods of cart controller in a static way.
	 * @param command
	 */
	public static void connectionWithCartHBox(String command) {
		if (command.contains("refresh total price"))
			instance.refreshTotalPrice();
		else if (command.contains("refresh cart")) {
			instance.resetCart();
			instance.initCart();
		}
	}

	/**
	 * Clicking on this button empties the cart.
	 * @param event
	 */
	@FXML
	void emptyCart(MouseEvent event) {
		cart.emptyCart();
		overviewPane.getChildren().clear();
		connectionWithCartHBox("refresh cart");
		overviewPane.getChildren().clear();
	}

	/**
	 * Set the overview VBox in the right side.
	 * @param vBox the OverviewVBox
	 */
	public static void setOverviewVBox(VBox vBox) {
		instance.setOverviewScrollPane(vBox);
	}

	/**
	 * Clear the pane and load a new vbox
	 * @param vBox
	 */
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

	/**
	 * Clear the cart overview Pane - called when a productsBase is removed from the cart.
	 * @param product
	 */
	public void clearCartOverview(ProductsBase product) {
		if (overviewPane.getChildren().isEmpty())
			return;
		VBox productOverview = (VBox) overviewPane.getChildren().get(0);
		String title = ((Label) productOverview.getChildren().get(0)).getText();
		if (product instanceof Item) {
			Item item = (Item) product;
			if (item.toString().equals(title))
				overviewPane.getChildren().clear();
		} else if (product.getName().equals(title))
			overviewPane.getChildren().clear();
	}

	/**
	 * Go over all nodes in the cartVBox and check if any of them has a discount.
	 * @return true if there is discount, false otherwise
	 */
	private boolean isDiscount() {
		for (Node node : cartItemVBox.getChildren())
			if (node instanceof CartHBox) {
				CartHBox cartHBox = (CartHBox) node;
				if (cartHBox.getProduct().isDiscount())
					return true;
			}
		return false;
	}

	/**
	 * @return true if discountLabel is in totalPriceVBox, false otherwise
	 */
	private boolean isDiscountInPrice() {
		return totalPriceVBox.getChildren().contains(discountLabel);
	}
}
