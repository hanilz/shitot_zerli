package util;

import java.net.URL;

import catalog.CatalogController;
import client.ClientScreen;
import customProduct.CustomProductBuilderController;
import customerComplaint.ComplaintViewController;
import customerComplaint.CustomerComplaintHomeController;
import entities.User;
import home.HomeGuestController;
import home.HomeUserTypesController;
import home.LoginScreenController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import manageCatalog.ManageCatalogController;
import order.CartController;
import order.CheckoutController;
import order.DeliveryController;
import order.GreetingCardController;
import order.PaymentSuccessfulController;
import survey.SurveyHomeController;
import userScreens.ManageUsersController;

public class ManageScreens {
	private static Stage stage;
	private static Stage popupStage;

	/**
	 * This method will help us to switch between the screens
	 * 
	 * @param url
	 * @param title
	 * @throws Exception
	 */
	public static void changeScene(URL url, String title) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		try {
			loader = new FXMLLoader(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Parent root = loader.load();
		root.styleProperty();
		Scene scene = new Scene(root);
		Platform.runLater(new Runnable() { // this thread will help us change between scenes and avoid exceptions
			@Override
			public void run() {
				stage.setScene(scene);
				stage.setTitle(title);
				stage.show();
			}
		});
	}

	public static void openPopupFXML(URL url, String title) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		try {
			loader = new FXMLLoader(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Parent root = loader.load();
		Scene scene = new Scene(root);
		popupStage = new Stage();
		Platform.runLater(new Runnable() { // this thread will help us change between scenes and avoid exceptions
			@Override
			public void run() {
				popupStage.setTitle(title);
				popupStage.setScene(scene);
				popupStage.initModality(Modality.APPLICATION_MODAL);
				popupStage.showAndWait();
			}
		});
	}

	public static void setStage(Stage stage) {
		ManageScreens.stage = stage;
	}

	public static Stage getStage() {
		return stage;
	}

	public static Stage getPopupStage() {
		return popupStage;
	}

	public static void changeScreenTo(Screens screen) {

		try {
			switch (screen) {
			case GUEST_HOME:
				ManageScreens.changeScene(HomeGuestController.class.getResource("HomeGuestScreen.fxml"), "HomeScreen");
				break;
			case USER_HOME:
				ManageScreens.changeScene(HomeUserTypesController.class.getResource("HomeUserTypesScreen.fxml"),
						"HomeScreen");
				break;
			case LOGIN:
				ManageScreens.changeScene(LoginScreenController.class.getResource("LoginScreen.fxml"), "Login");
				break;
			case CATALOG:
				ManageScreens.changeScene(CatalogController.class.getResource("CatalogScreen.fxml"), "Catalog");
				break;
			case CART:
				ManageScreens.changeScene(CartController.class.getResource("CartScreen.fxml"), "Cart Screen");
				break;
			case GREATING_CARD:
				ManageScreens.changeScene(GreetingCardController.class.getResource("GreetingCardScreen.fxml"),
						"Greeting Card Screen");
				break;
			case DELIVERY_DETAILS:
				ManageScreens.changeScene(DeliveryController.class.getResource("DeliveryDetailsScreen.fxml"),
						"Delivery Details Screen");
				break;
			case PAYMENT_SUCCESSFULY:
				ManageScreens.openPopupFXML(
						PaymentSuccessfulController.class.getResource("PaymentSuccessfulPopup.fxml"),
						"Payment Successful!");
				break;
			case CHECKOUT:
				ManageScreens.changeScene(CheckoutController.class.getResource("CheckoutScreen.fxml"),
						"Checkout Screen");
				break;
			case CLIENT:
				ManageScreens.changeScene(ClientScreen.class.getResource("ClientScreen.fxml"), "Zli Client");
				break;
			case MANAGE_USERS:
				ManageScreens.changeScene(ManageUsersController.class.getResource("ManageUsers.fxml"), "Manage Users");
				break;
			case SURVEY_HOME:
				ManageScreens.changeScene(SurveyHomeController.class.getResource("SurveyHomeScreen.fxml"), "Survey Home");
				break;
			case SURVEY:
				ManageScreens.changeScene(SurveyHomeController.class.getResource("Survey.fxml"), "Survey");
				break;
			case COMPLAINT_HOME:
				ManageScreens.changeScene(CustomerComplaintHomeController.class.getResource("CustomerComplaintHomeScreen.fxml"), "Customer Complaint Home");
				break;
			case COMPLAINT:
				ManageScreens.changeScene(CustomerComplaintHomeController.class.getResource("CustomerComplaintScreen.fxml"), "Customer Complaint");
				break;
			case EDIT_CATALOG:
				ManageScreens.changeScene(ManageCatalogController.class.getResource("ManageCatalog.fxml"), "Manage Catalog");
				break;
			case CUSTOM_PRODUCT_BUILDER:
				ManageScreens.changeScene(CustomProductBuilderController.class.getResource("CustomProductBuilder.fxml"), "Custom Product Builder");
				break;
			case COMPLAINT_VIEW:
				ManageScreens.changeScene(ComplaintViewController.class.getResource("CustomerComplaintView.fxml"), "Customer Complaint View");
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void home() {
		if (User.getUserInstance().isUserLoggedIn())
			ManageScreens.changeScreenTo(Screens.USER_HOME);
		else
			ManageScreens.changeScreenTo(Screens.GUEST_HOME);
	}

	//set the name for the button in the home screen
	public static String getName(Screens user) {
		switch (user) {
		case CART:
			return "Cart";
		case CATALOG:
			return "Catalog";
		case CHECKOUT:
			return "Checkout";
		case CLIENT:
			return "Client";
		case DELIVERY_DETAILS:
			return "Delivery Details";
		case GREATING_CARD:
			return "Greating Card";
		case GUEST_HOME:
			return "Guest Home";
		case LOGIN:
			return "Login";
		case PAYMENT_SUCCESSFULY:
			return "Payment";
		case PRODUCT_DETAILS_POPUP:
			return "Product Details";
		case REPORT:
			return "Report";
		case USER_HOME:
			return "Home";
		case VIEW_COMPLAINTS_REPORT:
			return "Compaints Report";
		case VIEW_INCOME_REPORT:
			return "Income Report";
		case VIEW_ORDERS:
			return "Orders";
		case VIEW_ORDERS_REPORT:
			break;
		case MANAGE_USERS:
			return "Manage Users";
		case SURVEY_HOME:
			return "Survey";
		case COMPLAINT_HOME:
			return "Customer Complaints";
		case EDIT_CATALOG:
			return "Manage Catalog";
		}
		return null;
	}

	public static String getIconPath(Screens user) {
		switch (user) {
		case CART:
			return "resources/catalog/cart.png";
		case CATALOG:
			return "resources/home/store.png";
		case CHECKOUT:
			return "";
		case CLIENT:
			return "";
		case DELIVERY_DETAILS:
			return "";
		case GREATING_CARD:
			return "";
		case GUEST_HOME:
			return "resources/catalog/home.png";
		case LOGIN:
			return "resources/home/login.png";
		case PAYMENT_SUCCESSFULY:
			return "";
		case PRODUCT_DETAILS_POPUP:
			return "";
		case REPORT:
			return "";
		case USER_HOME:
			return "resources/catalog/home.png";
		case VIEW_COMPLAINTS_REPORT:
			return "";
		case VIEW_INCOME_REPORT:
			return "";
		case VIEW_ORDERS:
			return "";
		case VIEW_ORDERS_REPORT:
			return "";
		case MANAGE_USERS:
			return "resources/home/manageUsers.png";
		case SURVEY_HOME:
			return "resources/home/survey.png";
		case COMPLAINT_HOME:
			return "resources/home/complaint.png";
		case EDIT_CATALOG:
			return "resources/catalog/editCatalog.png";
		default:
			return "";// no such user//
		}
	}
}
