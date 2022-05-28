package util;

import java.net.URL;
import java.util.ArrayList;

import cart.CartController;
import catalog.CatalogController;
import catalog.SplashScreenController;
import client.ClientScreen;
import customProduct.CustomProductBuilderController;
import customerComplaint.ComplaintViewController;
import customerComplaint.CustomerComplaintHomeController;
import deliveryCoordination.DeliveryCoordinatorController;
import entities.User;
import home.HomeGuestController;
import home.HomeUserTypesController;
import home.LoginScreenController;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import manageCatalog.ManageCatalogController;
import mangeCustomerOrders.ManageCustomerOrdersController;
import mangeUsers.ManageUsersController;
import order.CheckoutController;
import order.DeliveryController;
import order.GreetingCardController;
import order.PaymentSuccessfulController;
import ordersView.ViewOrdersController;
import registerUser.RegistrationController;
import report.ReportsController;
import survey.SurveyHomeController;
import surveyAnalysis.AnalyzeAnswersController;
import surveyAnalysis.SurveyAnswersHomeController;
import surveyAnalysisView.SurveyAnalysisViewHomeController;

public class ManageScreens {
	private static Stage stage;
	private static Screens previousScreen, currentScreen;
	private static Stage popupStage;
	private static ArrayList<Stage> openedPopups = new ArrayList<>();

	/**
	 * This method will help us to switch between the screens
	 * 
	 * @param url
	 * @param title
	 * @throws Exception
	 */
	public static void changeScene(URL url, String title) throws Exception {
		// setIconApplication();
		FXMLLoader loader = new FXMLLoader();
		try {
			loader = new FXMLLoader(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Parent root = loader.load();
		root.styleProperty();
		Scene scene = new Scene(root);
		setStyleSheet(url, scene);
		Platform.runLater(new Runnable() { // this thread will help us change between scenes and avoid exceptions
			@Override
			public void run() {
				stage.setScene(scene);
				stage.setTitle(title);
				stage.show();
			}
		});
	}

	private static void setStyleSheet(URL url, Scene scene) {
		String[] splitedUrl = url.toString().split("/");
		String style = splitedUrl[splitedUrl.length - 1];
		style = style.substring(0, style.length() - 4);
		style += "css";
		scene.getStylesheets().add("resources/css/" + style);
	}
	
	
	/**add a title for the alert and the text do display it
	 * @param title
	 * @param text
	 */
	public static void displayAlert(String title, String text) {
		Alert a = new Alert(AlertType.NONE,title,ButtonType.CLOSE);
		a.setTitle(title);
		a.setContentText(text);
		a.show();
	}

	public static void openPopupFXML(URL url, String title) throws Exception {
		// setIconApplication();
		FXMLLoader loader = new FXMLLoader();
		try {
			loader = new FXMLLoader(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Parent root = loader.load();
		Scene scene = new Scene(root);
		popupStage = new Stage();
		addPopup(popupStage);
		setIconApplication(popupStage);
		Platform.runLater(new Runnable() { // this thread will help us change between scenes and avoid exceptions
			@Override
			public void run() {
				popupStage.setTitle(title);
				popupStage.setScene(scene);
				popupStage.initModality(Modality.APPLICATION_MODAL);
				popupStage.showAndWait();
				removePopup(popupStage);
			}
		});
	}

	public static void setPreviousScreen(Screens lastScreen)// added
	{
		previousScreen = lastScreen;
	}

	private static void setIconApplication(Stage stage) {
		stage.getIcons().add(new Image("/resources/icon.png"));
	}

	public static void addPopup(Stage popup) {
		openedPopups.add(popup);
	}

	public static void removePopup(Stage popup) {
		openedPopups.remove(popup);
	}

	public static void closeAllPopups() {
		for (int i = 0; i < openedPopups.size(); i++)
			openedPopups.get(i).close();
		openedPopups.clear();
	}

	public static void setStage(Stage stage) {
		ManageScreens.stage = stage;
		setIconApplication(stage);
	}

	public static Stage getStage() {
		return stage;
	}

	public static Stage getPopupStage() {
		return popupStage;
	}

	public static void changeScreenTo(Screens screen) {
		setPreviousScreen(currentScreen);// saved one become last one
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
				ManageScreens.openPopupFXML(LoginScreenController.class.getResource("LoginScreen.fxml"), "Login");// popup
				break;
			case CATALOG_SPLASH_SCREEN:
				ManageScreens.changeScene(SplashScreenController.class.getResource("SplashScreen.fxml"),
						"Loading Zli Catalog...");
				break;
			case CATALOG:
				ManageScreens.changeScene(CatalogController.class.getResource("CatalogScreen.fxml"), "Zli: Catalog");
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
				ManageScreens.changeScene(ClientScreen.class.getResource("ClientScreen.fxml"),
						"Zli: Connect To Client");
				break;
			case MANAGE_USERS:
				ManageScreens.changeScene(ManageUsersController.class.getResource("ManageUsers.fxml"), "Manage Users");
				break;
			case SURVEY_HOME:
				ManageScreens.changeScene(SurveyHomeController.class.getResource("SurveyHomeScreen.fxml"),
						"Survey Home");
				break;
			case SURVEY:
				ManageScreens.changeScene(SurveyHomeController.class.getResource("Survey.fxml"), "Survey");
				break;
			case COMPLAINT_HOME:
				ManageScreens.changeScene(
						CustomerComplaintHomeController.class.getResource("CustomerComplaintHomeScreen.fxml"),
						"Customer Complaint Home");
				break;
			case COMPLAINT:
				ManageScreens.changeScene(
						CustomerComplaintHomeController.class.getResource("CustomerComplaintScreen.fxml"),
						"Customer Complaint");
				break;
			case EDIT_CATALOG:
				ManageScreens.changeScene(ManageCatalogController.class.getResource("ManageCatalog.fxml"),
						"Manage Catalog");
				break;
			case CUSTOM_PRODUCT_BUILDER:
				ManageScreens.changeScene(CustomProductBuilderController.class.getResource("CustomProductBuilder.fxml"),
						"Custom Product Builder");
				break;
			case COMPLAINT_VIEW:
				ManageScreens.changeScene(ComplaintViewController.class.getResource("CustomerComplaintView.fxml"),
						"Customer Complaint View");
				break;
			case REGISTER_CUSTMER:
				ManageScreens.changeScene(RegistrationController.class.getResource("RegistrationScreen.fxml"),
						"Register New Customer");
				break;
			case VIEW_ORDERS_MANAGER:
				ManageScreens.changeScene(
						ManageCustomerOrdersController.class.getResource("ManageCustomerOrdersScreen.fxml"),
						"Manage Customer Orders");
				break;
			case VIEW_ORDERS_CUSTOMER:
				ManageScreens.changeScene(ViewOrdersController.class.getResource("ViewOrdersScreen.fxml"),
						"View Orders");
				break;

			case VIEW_ANSWERED_SURVEYS:
				ManageScreens.changeScene(SurveyAnswersHomeController.class.getResource("SurveyAnswersHomeScreen.fxml"),
						"Survey Answers");
				break;
			case VIEW_ANSWERS_FOR_SURVEY:
				ManageScreens.changeScene(AnalyzeAnswersController.class.getResource("AnalayzeAnswersScreen.fxml"),
						"Survey Answers");
				break;
			case VIEW_REPORTS:
				ManageScreens.changeScene(ReportsController.class.getResource("reportsScreen.fxml"),
						"View Reports");
				break;
			case VIEW_SURVEY_ANALYSIS_RESULTS:
				ManageScreens.changeScene(SurveyAnalysisViewHomeController.class.getResource("SurveyReportsHomeScreen.fxml"),
						"View Reports");
				break;
			case DELIVER_ORDERS:
				ManageScreens.changeScene(DeliveryCoordinatorController.class.getResource("DeliveryCoordinatorScreen.fxml"),
						"Delivery Coordinator");
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		currentScreen = screen;// save currrent screen to the one showes

	}

	public static void home() {
		if (User.getUserInstance().isUserLoggedIn())
			ManageScreens.changeScreenTo(Screens.USER_HOME);
		else
			ManageScreens.changeScreenTo(Screens.GUEST_HOME);
	}

	public static void previousScreen()// added
	{
		if (previousScreen == Screens.GUEST_HOME || previousScreen == Screens.USER_HOME)
			home();
		else
			changeScreenTo(previousScreen);
	}

	// set the name for the button in the home screen
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
		case REGISTER_CUSTMER:
			return "Register Customer";
		case VIEW_ORDERS_MANAGER:
			return "Manage Orders";
		case VIEW_ORDERS_CUSTOMER:
			return "View Orders";
		case VIEW_ANSWERED_SURVEYS:
			return "Analyze Surveys";
		case VIEW_REPORTS:
			return "View Reports";
		case VIEW_SURVEY_ANALYSIS_RESULTS:
			return "Survey Results";
		case DELIVER_ORDERS:
			return "Deliver Orders";
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
		case REGISTER_CUSTMER:
			return "resources/home/newUser.png";
		case VIEW_ORDERS_MANAGER:
			return "resources/home/order.png";
		case VIEW_ORDERS_CUSTOMER:
			return "resources/home/order.png";
		case VIEW_ANSWERED_SURVEYS:
			return "resources/home/survey.png";
		case VIEW_REPORTS:
			return "resources/home/reports.png";
		case VIEW_SURVEY_ANALYSIS_RESULTS:
			return "resources/home/survey_results.png";
		case DELIVER_ORDERS:
			return "resources/home/delivery.png";
		default:
			return "";// no such user//
		}
	}
}
