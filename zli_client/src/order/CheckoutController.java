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
import entities.Item;
import entities.Order;
import entities.OrderCustomProduct;
import entities.OrderItem;
import entities.OrderProduct;
import entities.Product;
import entities.ProductsBase;
import entities.SingletonOrder;
import entities.User;
import inputs.InputChecker;
import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import util.Commands;
import util.ManageScreens;
import util.Screens;
import util.UserType;

public class CheckoutController implements Initializable {

	@FXML
	private HBox storeCreditLeftHBox;

	@FXML
	private Label totalDiscountsLabel;

	@FXML
	private Label storeCreditLeftLabel;

	@FXML
	private Button backButton;

	@FXML
	private Label cartSummaryTotalLabel;

	@FXML
	private VBox cartSummaryVBox;

	@FXML
	private Label cartTotalLabel;

	@FXML
	private Button deliveryDetailsButton;

	@FXML
	private Label deliveryFeeLabel;

	@FXML
	private Button greetingCardButton;

	@FXML
	private ImageView homeButton;

	@FXML
	private Label messageLabel;

	@FXML
	private TextArea orderCommentsTextArea;

	@FXML
	private ComboBox<AccountPayment> paymentMethodComboBox;

	@FXML
	private Button placeOrderButton;

	@FXML
	private Label specialDiscountsLabel;

	@FXML
	private Label storeCreditAmountLabel;

	@FXML
	private Label storeCreditUsedLabel;

	@FXML
	private Label totalLabel;

	@FXML
	private CheckBox useStoreCreditCheckBox;

	private ArrayList<CustomProduct> customProducts = new ArrayList<>();
	private ArrayList<ProductsBase> customProductsBase = new ArrayList<>();

	private ArrayList<Product> products = new ArrayList<>();

	private ArrayList<Item> items = new ArrayList<>();

	private ObservableList<AccountPayment> accountPayments;

	private double deliveryFee;
	private double cartTotal;
	private double totalPriceBeforeCredit;
	private double usedStoreCredit;
	private double totalPrice;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initCartSummaryVBox();
		initPaymentMethodComboBox();

		storeCreditAmountLabel.setText(InputChecker.price(User.getUserInstance().getStoreCredit()));
		if (User.getUserInstance().getStoreCredit() == 0)
			useStoreCreditCheckBox.setDisable(true);

		deliveryFee = (SingletonOrder.getInstance().getIsPickup() ? 0
				: (SingletonOrder.getInstance().getIsExpress() ? 30 : 15));

		deliveryFeeLabel.setText(InputChecker.price(deliveryFee));

		totalPriceBeforeCredit = deliveryFee + cartTotal;

		storeCreditUsedLabel.setText(InputChecker.price(0));

		specialDiscountsLabel.setText(InputChecker.price(calculateDiscount()));

		totalPriceBeforeCredit += calculateDiscount();
		totalPrice = totalPriceBeforeCredit;
		totalLabel.setText(InputChecker.price(totalPriceBeforeCredit));
		totalDiscountsLabel.setText(
				InputChecker.price(Cart.getInstance().getTotalDiscountPrice() - Cart.getInstance().getTotalPrice()));

		greetingCardButton.setDisable(!SingletonOrder.getInstance().getIsGreetingCard());
	}

	private double calculateDiscount() {
		String type = User.getUserInstance().getType().toString();
		if (type.contains("NEW"))
			return -totalPriceBeforeCredit * 0.2;
		return 0;
	}

	private void initPaymentMethodComboBox() {
		fetchAccountPayments();
		paymentMethodComboBox.getItems().addAll(accountPayments);
	}

	private void initCartSummaryVBox() {
		System.out.println("Adding all product to cart screen...");

		for (ProductsBase product : Cart.getInstance().getCart().keySet()) {
			Integer quantity = Cart.getInstance().getCart().get(product);
			System.out.println("product to add is " + product.getName() + " with the quantity " + quantity);

			OrderSummaryHBox productSummaryHBox = new OrderSummaryHBox(product, quantity);
			productSummaryHBox.initHBox();
			cartSummaryVBox.getChildren().add(productSummaryHBox);

			addToLists(product);
		}
		cartTotal = Cart.getInstance().getTotalDiscountPrice();
		cartSummaryTotalLabel.setText(InputChecker.price(cartTotal));
		cartTotalLabel.setText(InputChecker.price(Cart.getInstance().getTotalPrice()));
	}

	private void addToLists(ProductsBase product) {
		if (product instanceof CustomProduct) {
			customProducts.add((CustomProduct) product);
			customProductsBase.add(product);
		} else if (product instanceof Product)
			products.add((Product) product);
		else if (product instanceof Item)
			items.add((Item) product);
	}

	@FXML
	void changeToDeliveryScreen(MouseEvent event) {
		ManageScreens.changeScreenTo(Screens.DELIVERY_DETAILS);
	}

	@FXML
	void placeOrder(MouseEvent event) {
		try {
			if (checkPaymentComboBox()) {
				insertOrderToDB();
				if (useStoreCreditCheckBox.isSelected())
					updateUserStoreCredit();
				if (User.getUserInstance().getType().equals(UserType.NEW_CUSTOMER))
					updateUserTypeToCustomer();
				ManageScreens.openPopupFXML(getClass().getResource("PaymentSuccessfulPopup.fxml"),
						"Payment Successful!");
				ManageScreens.getPopupStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
					public void handle(WindowEvent we) {
						PaymentSuccessfulController.restAndReturn();
					}
				});

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateUserTypeToCustomer() {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.UPDATE_USER_TYPE_TO_CUSTOMER);
		message.put("idUser", User.getUserInstance().getIdUser());
		Object response = ClientFormController.client.accept(message);
		if (response.equals("update user type to customer successful")) {
			System.out.println("yayy!! updated user " + User.getUserInstance().getIdUser() + " type to customer!");
			User.getUserInstance().setType("CUSTOMER");
		} else
			System.out.println("of pufff, update user type to customer failed!");
	}

	private void updateUserStoreCredit() {
		HashMap<String, Object> message = new HashMap<>();
		double newStoreCredit = User.getUserInstance().getStoreCredit() - usedStoreCredit;
		message.put("command", Commands.UPDATE_USER_STORE_CREDIT);
		message.put("store credit", newStoreCredit);
		message.put("idUser", User.getUserInstance().getIdUser());
		Object response = ClientFormController.client.accept(message);
		if (response.equals("update user store credit successful")) {
			System.out.println("yayy!! user store credit updated successfully in the db to "
					+ (User.getUserInstance().getStoreCredit() - usedStoreCredit) + "!");
			User.getUserInstance().setStoreCredit(newStoreCredit);
		} else
			System.out.println("of pufff, update user store credit failed!");
	}

	private boolean checkPaymentComboBox() {
		if (InputChecker.isNull(paymentMethodComboBox.getValue())) {
			showMessage("* Please select a payment method!");
			return false;
		}
		return true;
	}

	private void insertOrderToDB() {
		SingletonOrder.getInstance().formatGreetingCard();
		String greetingCard = SingletonOrder.getInstance().getGreetingCard();
		String dOrder = orderCommentsTextArea.getText();
		boolean isPickup = SingletonOrder.getInstance().getIsPickup();
		Branch branch = isPickup ? SingletonOrder.getInstance().getPickupBranch()
				: SingletonOrder.getInstance().getBranch();
		String status = "Waiting for Approval"; // TODO: const class or enum for order status
		String paymentMethod = "credit card";
		User user = User.getUserInstance();
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.INSERT_ORDER);
		message.put("order",
				new Order(totalPriceBeforeCredit, greetingCard, dOrder, branch, status, paymentMethod, user));
		Object response = ClientFormController.client.accept(message);
		Integer orderId = (Integer) response;
		if (orderId != -1) { // if successful
			System.out.println("yayy!! order " + orderId + " added to the db!");
			if (products.size() > 0)
				insertOrderProductsToDB(orderId);
			if (items.size() > 0)
				insertOrderItemsToDB(orderId);
			if (customProducts.size() > 0)
				insertCustomProductsData(orderId);
			insertDelivery(orderId);
		} else
			System.out.println("of pufff, order insert failed!");

	}

	private void insertCustomProductsData(Integer orderId) {
		insertCustomProductsToDB();
		if (isCustomProductProducts())
			insertCustomProductProductsToDB();
		if (isCustomProductItems())
			insertCustomProductItemsToDB();
		insertOrderCustomProductsToDB(orderId);
	}

	private boolean isCustomProductItems() {
		for (CustomProduct customProduct : customProducts)
			if (customProduct.getItems().size() > 0)
				return true;
		return false;
	}

	private boolean isCustomProductProducts() {
		for (CustomProduct customProduct : customProducts)
			if (customProduct.getProducts().size() > 0)
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
			orderItemsList
					.add(new OrderItem(idOrder, (Item) currentItem, Cart.getInstance().getCart().get(currentItem)));
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
		boolean isPickup = SingletonOrder.getInstance().getIsPickup();
		message.put("command", isPickup ? Commands.INSERT_PICKUP : Commands.INSERT_DELIVERY);
		message.put("delivery",
				isPickup ? SingletonOrder.getInstance().getPickup() : SingletonOrder.getInstance().getDelivery());
		message.put("idOrder", idOrder);
		Object response = ClientFormController.client.accept(message);
		if ((Integer) response != -1)
			System.out.println("yayy!! delivery added to the db");
	}

	@SuppressWarnings("unchecked")
	private void insertCustomProductsToDB() {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.INSERT_CUSTOM_PRODUCTS);
		message.put("custom products", customProducts);
		Object response = ClientFormController.client.accept(message);
		customProducts = (ArrayList<CustomProduct>) response; // will update the ids of the custom products
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
		ArrayList<OrderCustomProduct> orderCustomProductsList = new ArrayList<>();
		message.put("command", Commands.INSERT_ORDER_CUSTOM_PRODUCTS);
		for (int i = 0; i < customProducts.size(); i++) {
			CustomProduct currentCustomProduct = customProducts.get(i);
			ProductsBase currentCustomProductBase = customProductsBase.get(i);
			orderCustomProductsList.add(new OrderCustomProduct(orderId, currentCustomProduct,
					Cart.getInstance().getCart().get(currentCustomProductBase)));
		}
		message.put("list order custom products", orderCustomProductsList);
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
	void changeToHome(MouseEvent event) {
		ManageScreens.home();
	}

	@FXML
	void useStoreCreditClicked(MouseEvent event) {
		if (useStoreCreditCheckBox.isSelected()) {
			calculateUsedStoreCredit();
		} else
			setUsedStoreCreditToZero();
	}

	private void setUsedStoreCreditToZero() {
		usedStoreCredit = 0;
		totalPrice = totalPriceBeforeCredit;

		storeCreditUsedLabel.setText(InputChecker.price(usedStoreCredit));
		totalLabel.setText(InputChecker.price(totalPrice));

		storeCreditLeftHBox.setVisible(false);
	}

	private void calculateUsedStoreCredit() {
		usedStoreCredit = User.getUserInstance().getStoreCredit() > totalPriceBeforeCredit ? totalPriceBeforeCredit
				: User.getUserInstance().getStoreCredit();
		totalPrice = totalPriceBeforeCredit - usedStoreCredit;

		storeCreditUsedLabel.setText(InputChecker.price(-usedStoreCredit));
		totalLabel.setText(InputChecker.price(totalPrice));

		storeCreditLeftHBox.setVisible(true);
		storeCreditLeftLabel.setText(InputChecker.price(User.getUserInstance().getStoreCredit() - usedStoreCredit));
	}

	// for getting all account payment methods for the account payment ComboBox
	@SuppressWarnings("unchecked")
	private void fetchAccountPayments() {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.FETCH_ACCOUNT_PAYMENTS);
		message.put("idUser", User.getUserInstance().getIdUser());
		Object response = ClientFormController.client.accept(message);
		accountPayments = (ObservableList<AccountPayment>) response;
	}

	@FXML
	void openDeliveryDetailsPopup(MouseEvent event) {
		try {
			if (SingletonOrder.getInstance().getIsPickup())
				ManageScreens.openPopupFXML(
						DeliveryDetailsPickupVBoxController.class.getResource("DeliveryDetailsPickupVBox.fxml"),
						"Pickup Details");
			else
				ManageScreens.openPopupFXML(
						DeliveryDetailsDeliveryVBoxController.class.getResource("DeliveryDetailsDeliveryVBox.fxml"),
						"Delivery Details");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void openGreetingCardPopup(MouseEvent event) {
		try {
			ManageScreens.openPopupFXML(GreetingCardVBoxController.class.getResource("GreetingCardVBox.fxml"),
					"Greeting Card Details");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
