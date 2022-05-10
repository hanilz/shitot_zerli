package util;

import java.net.URL;

import catalog.CatalogController;
import client.ClientScreen;
import entities.User;
import home.LoggedHomeScreenController;
import home.LoginScreenController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import order.CartController;
import order.CheckoutController;
import order.DeliveryController;
import order.GreetingCardController;
import order.PaymentSuccessfulController;

public class ChangeScreen {
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
		ChangeScreen.stage = stage;
	}

	public static Stage getStage() {
		return stage;
	}

	public static Stage getPopupStage() {
		return popupStage;
	}

	public static void to(Screens screen) {
		try {
			switch (screen) {
			case GUEST_HOME:
				ChangeScreen.changeScene(LoggedHomeScreenController.class.getResource("HomeNotLoggedInScreen.fxml"),
						"HomeScreen");
				break;
			case CATALOG:
				ChangeScreen.changeScene(CatalogController.class.getResource("CatalogScreen.fxml"), "Catalog");
				break;
			case USER_HOME:
				ChangeScreen.changeScene(LoggedHomeScreenController.class.getResource("HomeLoggedInScreen.fxml"),
						"HomeScreen");
				break;
			case LOGIN:
				ChangeScreen.changeScene(LoginScreenController.class.getResource("LoginScreen.fxml"), "Login");
				break;
			case CART:
				ChangeScreen.changeScene(CartController.class.getResource("CartScreen.fxml"), "Cart Screen");
				break;
			case GREATING_CARD:
				ChangeScreen.changeScene(GreetingCardController.class.getResource("GreetingCardScreen.fxml"),
						"Greeting Card Screen");
				break;
			case DELIVERY_DETAILS:
				ChangeScreen.changeScene(DeliveryController.class.getResource("DeliveryDetailsScreen.fxml"),
						"Delivery Details Screen");
				break;
			case PAYMENT_SUCCESSFULY:
				ChangeScreen.openPopupFXML(PaymentSuccessfulController.class.getResource("PaymentSuccessfulPopup.fxml"), "Payment Successful!");
				break;
			case CHECKOUT:
				ChangeScreen.changeScene(CheckoutController.class.getResource("CheckoutScreen.fxml"), "Checkout Screen");
				break;
			case CLIENT:
				ChangeScreen.changeScene(ClientScreen.class.getResource("ClientScreen.fxml"), "Zli Client");
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void home()
	{
		if (User.getUserInstance().isUserLoggedIn())
	    	ChangeScreen.to(Screens.USER_HOME);
		else
			ChangeScreen.to(Screens.GUEST_HOME);
	}
}
