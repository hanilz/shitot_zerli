package order;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Set;

import client.ClientFormController;
import entities.AccountPayment;
import entities.Branch;
import entities.Cart;
import entities.DeliveriesOrders;
import entities.Order;
import entities.OrderProduct;
import entities.Product;
import entities.SingletonOrder;
import entities.User;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import util.InputChecker;
import util.ManageScreens;
import util.Screens;

public class CheckoutController implements Initializable{

    @FXML
    private Button backButton;

    @FXML
    private TextField cardNumberField;

    @FXML
    private TextField firstNameField;

    @FXML
    private ImageView homeButton;

    @FXML
    private TextField idField;

    @FXML
    private TextField lastNameField;

    @FXML
    private Label messageLabel;

    @FXML
    private TextField monthField;

    @FXML
    private VBox orderSummaryVBox;

    @FXML
    private Button placeOrderButton;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private TextField vccField;

    @FXML
    private TextField yearField;

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
    		if(checkCheckoutFields()) {
    			insertPaymentDetailsToDB();
    			insertOrderToDB();
    			ManageScreens.openPopupFXML(getClass().getResource("PaymentSuccessfulPopup.fxml"), "Payment Successful!");
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    private boolean checkCheckoutFields() {
    	if(InputChecker.isPaymentFieldsEmpty(firstNameField.getText(), lastNameField.getText(), cardNumberField.getText(), monthField.getText(), yearField.getText(), vccField.getText(), idField.getText())) {
    		showMessage("* Please fill all the fields!");
    		return false;
    	}
    	else if(!InputChecker.checkPaymentInput(firstNameField.getText(), lastNameField.getText(), cardNumberField.getText(), monthField.getText(), yearField.getText(), vccField.getText(), idField.getText())) {
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
    	AccountPayment accountPayment = new AccountPayment(fullName, cardNumber, cardDate, cardVCC, User.getUserInstance());
    	
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", "insert account payment");
		message.put("account payment", accountPayment);
		Object response = ClientFormController.client.accept(message);
		if(response.equals("insert account payment successful"))
			System.out.println("yayy!! account payment added to the db");
		else
			System.out.println("of pufff, insert failed!");
    }
    
    private void insertOrderToDB() {
    	double totalPrice = Cart.getInstance().getTotalPrice();
    	String greetingCard = SingletonOrder.getInstance().getGreetingCard();
    	String dOrder = ""; //TODO: for now, we need to insert new text area for dOrder
    	Branch branch = SingletonOrder.getInstance().getBranch();
    	String status = "Waiting for approvel"; //TODO: const class or enum for order status
    	String paymentMethod = "credit card"; //TODO: talk with everyone about this field in the db.
    	User user = User.getUserInstance();
    	HashMap<String, Object> message = new HashMap<>();
    	message.put("command", "insert order");
    	message.put("order", new Order(totalPrice, greetingCard, dOrder, branch, status, paymentMethod, user));
		Object response = ClientFormController.client.accept(message);
		if((Integer) response != -1) {
			System.out.println("yayy!! order added to the db");
			insertOrderProductsToDB((Integer) response);
			if(SingletonOrder.getInstance().getDelivery() != null)
				insertDelivery((Integer) response);
		}
		else
			System.out.println("of pufff, insert failed!");
		

    }
    
    private void insertOrderProductsToDB(int idOrder) {
    	HashMap<String, Object> message = new HashMap<>();
    	ArrayList<OrderProduct> orderProductsList = new ArrayList<>();
    	message.put("command", "insert order products");
    	for(Product currentProduct : Cart.getInstance().getCart().keySet())
    		orderProductsList.add(new OrderProduct(idOrder, currentProduct, Cart.getInstance().getCart().get(currentProduct)));
    	message.put("list order products", orderProductsList);
    	Object response = ClientFormController.client.accept(message);
		if(response.equals("insert order products successful"))
			System.out.println("yayy!! order products added to the db");
		else
			System.out.println("of pufff, insert failed!");
    }
    
    private void insertDelivery(int idOrder) {
    	//first - insert the delivery into the db
    	HashMap<String, Object> message = new HashMap<>();
    	message.put("command", "insert delivery");
    	message.put("delivery", SingletonOrder.getInstance().getDelivery());
		Object response = ClientFormController.client.accept(message);
		if((Integer) response != -1) {
			System.out.println("yayy!! delivery added to the db");
			insertDeliveriesOrders(idOrder, (Integer) response);
		}
    }
    
    private void insertDeliveriesOrders(int idOrder, int idDelivery) {
    	//second - insert the delivery order into the db
    	HashMap<String, Object> message = new HashMap<>();
    	message.put("command", "insert delivery order");
    	message.put("delivery order", new DeliveriesOrders(idOrder, idDelivery));
		Object response = ClientFormController.client.accept(message);
		if(response.equals("insert delivery order successful"))
			System.out.println("yayy!! delivery order added to the db");
		else
			System.out.println("of pufff, insert failed!");
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
}
