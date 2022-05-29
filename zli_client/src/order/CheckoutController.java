package order;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.ClientFormController;
import entities.AccountPayment;
import entities.Branch;
import entities.Cart;
import entities.CustomProduct;
import entities.DeliveriesOrders;
import entities.Item;
import entities.Order;
import entities.OrderItem;
import entities.OrderProduct;
import entities.Product;
import entities.ProductsBase;
import entities.SingletonOrder;
import entities.User;
import inputs.InputChecker;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import util.Commands;
import util.ManageScreens;
import util.Screens;

public class CheckoutController implements Initializable {


    @FXML
    private TextField StoreCreditField;

    @FXML
    private Label TotalLabel;

    @FXML
    private Button backButton;

    @FXML
    private Label cartSummaryTotalLabel;

    @FXML
    private VBox cartSummaryVBox;

    @FXML
    private Label cartTotalLabel;

    @FXML
    private Pane deliveryDetailsPane;

    @FXML
    private Label deliveryFeeLabel;

    @FXML
    private Pane greetingCardPane;

    @FXML
    private ImageView homeButton;

    @FXML
    private Label maxLabel;

    @FXML
    private Label messageLabel;

    @FXML
    private Label minLabel;

    @FXML
    private ComboBox<?> paymentMethodComboBox;

    @FXML
    private Button placeOrderButton;

    @FXML
    private Label specialDiscountsLabel;

    @FXML
    private Label storeCreditAmountLabel;

    @FXML
    private Slider storeCreditSlider;

    @FXML
    private Label storeCreditUsedLabel;

	private ArrayList<CustomProduct> customProducts = new ArrayList<>();

	private ArrayList<Product> products = new ArrayList<>();
	
	private ArrayList<Item> items = new ArrayList<>();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("Adding all product to cart screen...");

		for (ProductsBase product : Cart.getInstance().getCart().keySet()) {
			Integer quantity = Cart.getInstance().getCart().get(product);
			System.out.println("product to add is " + product.getName() + " with the quantity " + quantity);

			OrderSummaryHBox productSummaryHBox = new OrderSummaryHBox(product, quantity);
			productSummaryHBox.initHBox();
			cartSummaryVBox.getChildren().add(productSummaryHBox);
			
			addToLists(product);
		}
		cartSummaryTotalLabel.setText(InputChecker.price(Cart.getInstance().getTotalPrice()));
	}

	private void addToLists(ProductsBase product) {
		if(product instanceof CustomProduct)
			customProducts.add((CustomProduct) product);
		else if(product instanceof Product)
			products.add((Product) product);
		else if(product instanceof Item)
			items.add((Item) product);
	}
	
	@FXML
	void changeToDeliveryScreen(MouseEvent event) {
		ManageScreens.changeScreenTo(Screens.DELIVERY_DETAILS);
	}

	@FXML
	void placeOrder(MouseEvent event) {
		try {
			if (checkCheckoutFields()) {
				insertPaymentDetailsToDB();
				insertOrderToDB();
				ManageScreens.openPopupFXML(getClass().getResource("PaymentSuccessfulPopup.fxml"),
						"Payment Successful!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean checkCheckoutFields() {
		if (InputChecker.isPaymentFieldsEmpty(firstNameField.getText(), lastNameField.getText(),
				cardNumberField.getText(), monthField.getText(), yearField.getText(), vccField.getText(),
				idField.getText())) {
			showMessage("* Please fill all the fields!");
			return false;
		} else if (!InputChecker.checkPaymentInput(firstNameField.getText(), lastNameField.getText(),
				cardNumberField.getText(), monthField.getText(), yearField.getText(), vccField.getText(),
				idField.getText())) {
			showMessage("* Please check the input!");
			return false;
		}
		return true;
	}

	private void insertPaymentDetailsToDB() {
		String fullName = String.format("%s %s", firstNameField.getText(), lastNameField.getText());
		String cardNumber = cardNumberField.getText();
		String cardDate = String.format("%s/%s", monthField.getText(), yearField.getText());
		String cardVCC = vccField.getText();
		AccountPayment accountPayment = new AccountPayment(fullName, cardNumber, cardDate, cardVCC,
				User.getUserInstance());

		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.INSERT_ACCOUNT_PAYMENT);
		message.put("account payment", accountPayment);
		Object response = ClientFormController.client.accept(message);
		if (response.equals("insert account payment successful"))
			System.out.println("yayy!! account payment added to the db");
		else
			System.out.println("of pufff, insert failed!");
	}

	private void insertOrderToDB() {
		double totalPrice = Cart.getInstance().getTotalPrice();
		String greetingCard = SingletonOrder.getInstance().getGreetingCard();
		String dOrder = ""; // TODO: for now, we need to insert new text area for dOrder
		Branch branch = SingletonOrder.getInstance().getBranch();
		String status = "Waiting for approval"; // TODO: const class or enum for order status
		String paymentMethod = "credit card"; // TODO: talk with everyone about this field in the db.
		User user = User.getUserInstance();
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.INSERT_ORDER);
		message.put("order", new Order(totalPrice, greetingCard, dOrder, branch, status, paymentMethod, user));
		Object response = ClientFormController.client.accept(message);
		Integer orderId = (Integer) response;
		if (orderId != -1) {
			System.out.println("yayy!! order added to the db");
			if(products.size() > 0)
				insertOrderProductsToDB(orderId);
			if(items.size() > 0)
				insertOrderItemsToDB(orderId);
			if(customProducts.size() > 0)
				insertCustomProductsData(orderId);
			if (SingletonOrder.getInstance().getDelivery() != null)
				insertDelivery(orderId);
		} else
			System.out.println("of pufff, insert failed!");

	}

	private void insertCustomProductsData(Integer orderId) {
		insertCustomProductsToDB();
		if(isCustomProductProducts())
			insertCustomProductProductsToDB();
		if(isCustomProductItems())
			insertCustomProductItemsToDB();
		insertOrderCustomProductsToDB(orderId);
	}

	private boolean isCustomProductItems() {
		for(CustomProduct customProduct : customProducts)
			if(customProduct.getItems().size() > 0)
				return true;
		return false;
	}

	private boolean isCustomProductProducts() {
		for(CustomProduct customProduct : customProducts)
			if(customProduct.getProducts().size() > 0)
				return true;
		return false;
	}

	private void insertOrderProductsToDB(int idOrder) {
		HashMap<String, Object> message = new HashMap<>();
		ArrayList<OrderProduct> orderProductsList = new ArrayList<>();
		message.put("command", Commands.INSERT_ORDERS_PRODUCT);
		for (Product currentProduct : products)
			orderProductsList.add(new OrderProduct(idOrder, (Product) currentProduct,
					Cart.getInstance().getCart().get(currentProduct)));
		message.put("list order products", orderProductsList);
		Object response = ClientFormController.client.accept(message);
		if (response.equals("insert order products successful"))
			System.out.println("yayy!! order products added to the db");
		else
			System.out.println("of pufff, insert failed!");
	}

	private void insertOrderItemsToDB(int idOrder) {
		HashMap<String, Object> message = new HashMap<>();
		ArrayList<OrderItem> orderItemsList = new ArrayList<>();
		message.put("command", Commands.INSERT_ORDERS_ITEMS);
		for (Item currentItem : items)
			orderItemsList.add(new OrderItem(idOrder, (Item) currentItem,
					Cart.getInstance().getCart().get(currentItem)));
		message.put("list order items", orderItemsList);
		Object response = ClientFormController.client.accept(message);
		if (response.equals("insert order items successful"))
			System.out.println("yayy!! order items added to the db");
		else
			System.out.println("of pufff, insert failed!");
	}

	
	private void insertDelivery(int idOrder) {
		// first - insert the delivery into the db
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.INSERT_DELIVERY);
		message.put("delivery", SingletonOrder.getInstance().getDelivery());
		Object response = ClientFormController.client.accept(message);
		if ((Integer) response != -1) {
			System.out.println("yayy!! delivery added to the db");
			insertDeliveriesOrders(idOrder, (Integer) response);
		}
	}

	private void insertDeliveriesOrders(int idOrder, int idDelivery) {
		// second - insert the delivery order into the db
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.INSERT_DELIVERY_ORDER);
		message.put("delivery order", new DeliveriesOrders(idOrder, idDelivery));
		Object response = ClientFormController.client.accept(message);
		if (response.equals("insert delivery order successful"))
			System.out.println("yayy!! delivery order added to the db");
		else
			System.out.println("of pufff, insert failed!");
	}

	@SuppressWarnings("unchecked")
	private void insertCustomProductsToDB() {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.INSERT_CUSTOM_PRODUCTS);
		message.put("custom products", customProducts);
		Object response = ClientFormController.client.accept(message);
		customProducts = (ArrayList<CustomProduct>) response;  // will update the ids of the custom products
	}

	private void insertCustomProductProductsToDB() {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.INSERT_CUSTOM_PRODUCT_PRODUCTS);
		message.put("custom products", customProducts);
		Object response = ClientFormController.client.accept(message);
		if (response.equals("insert custom product products successful"))
			System.out.println("yayy!! custom product products added to the db");
		else
			System.out.println("pufff of, insert custom product products failed!");
	}

	private void insertCustomProductItemsToDB() {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.INSERT_CUSTOM_PRODUCT_ITEMS);
		message.put("custom products", customProducts);
		Object response = ClientFormController.client.accept(message);
		if (response.equals("insert custom product items successful"))
			System.out.println("yayy!! custom product items added to the db");
		else
			System.out.println("pufff of, insert custom product items failed!");
		
	}

	private void insertOrderCustomProductsToDB(Integer orderId) {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.INSERT_ORDER_CUSTOM_PRODUCTS);
		message.put("custom products", customProducts);
		message.put("idOrder", orderId);
		Object response = ClientFormController.client.accept(message);
		if (response.equals("insert order custom products successful"))
			System.out.println("yayy!! order custom products added to the db");
		else
			System.out.println("pufff of, insert order custom products failed!");		
	}
	
	private void showMessage(String textToShow) {
		messageLabel.setText(textToShow);
		PauseTransition pause = new PauseTransition(Duration.seconds(3));
		pause.setOnFinished(e -> messageLabel.setText(""));
		pause.play();
	}

    @FXML
    void setStoreCredit(MouseEvent event) {

    }

    @FXML
    void setStoreCreditBox(KeyEvent event) {

    }
	
	@FXML
	void changeToHome(MouseEvent event) {
		ManageScreens.home();
	}
}
