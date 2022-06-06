package catalog;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import entities.Cart;
import entities.User;
import home.LoginScreenController;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import util.ManageData;
import util.ManageScreens;
import util.Screens;

/**
 * Controller for catalog screen - Display all the items and products from the
 * database and let the customer add them to their cart.
 */
public class CatalogController implements Initializable {

	@FXML
	private ImageView cartImage;

	@FXML
	private VBox cartVBox;

	@FXML
	private Label catalogLbl;

	@FXML
	private ScrollPane catalogScrollPane;

	@FXML
	private VBox catalogVBox;

	@FXML
	private Button filterButton;

	@FXML
	private ImageView homeImage;

	@FXML
	private VBox homeVBox;

	@FXML
	private HBox loginHBox;

	@FXML
	private Pane discountPane1;

	@FXML
	private Pane discountPane2;

	@FXML
	private Pane discountPane3;

	@FXML
	private TextField searchBar;

	@FXML
	private Button searchButton;

	@FXML
	private ScrollPane filterScrollPane;

	@FXML
	private ImageView loginIcon;

	@FXML
	private Label loginLabel;

	@FXML
	private VBox loginVBox;

	@FXML
	private Label totalAmountCartLabel;

	private static CatalogController instance;

	private GridPane catalogGrid = ManageData.catalogGrid;

	/**
	 * Change to cart screen after clicking cart icon.
	 * 
	 * @param event
	 */
	@FXML
	void changeToCartScreen(MouseEvent event) {
		ManageScreens.changeScreenTo(Screens.CART);
	}

	/**
	 * Change to home screen after clicking home icon.
	 * 
	 * @param event
	 */
	@FXML
	void changeToHomeScreen(MouseEvent event) {
		ManageScreens.home();
	}

	/**
	 * Open the login popup.
	 * 
	 * @param event
	 */
	@FXML
	void openLogin(MouseEvent event) {
		if (!User.getUserInstance().isUserLoggedIn()) {
			LoginScreenController.loginFromCatalog();
		} else {
			User.getUserInstance().logout();
			ManageScreens.changeScreenTo(Screens.CATALOG);// to refresh screen
		}
	}

	@FXML
	void search(MouseEvent event) {

	}

	/**
	 * Refresh the small label near the cart button.
	 */
	public static void refreshTotalAmountInCart() {
		instance.setTotalAmountCartLabel();
	}

	/**
	 * Refresh the small label near the cart button.
	 */
	private void setTotalAmountCartLabel() {
		totalAmountCartLabel.setText("" + Cart.getInstance().getCart().size());
	}

	/**
	 * Initialize the catalog screen - Set the catalog grid and check if the user is
	 * logged in
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
		initTotalAmountCartLabel();
		catalogGrid = ManageData.catalogGrid;
		catalogScrollPane.setContent(catalogGrid);
		if (User.getUserInstance().isUserLoggedIn()) {
			loginLabel.setText("Welcome,\n" + User.getUserInstance().getUsername());
			loginLabel.setWrapText(true);
			loginLabel.setPrefHeight(50);
			loginLabel.setPrefWidth(150);
			loginVBox.setPrefHeight(73);
			loginVBox.setPrefWidth(100);
			loginIcon.setImage(new Image("resources/home/logout.png"));
		}
		initDiscountPanes();
	}

	/**
	 * Display the 3 most discounted products in the right side of the screen.
	 */
	private void initDiscountPanes() {
		VBox discount1 = initDiscountVBox(0);
		VBox discount2 = initDiscountVBox(1);
		VBox discount3 = initDiscountVBox(2);

		discountPane1.getChildren().add(discount1);
		discountPane2.getChildren().add(discount2);
		discountPane3.getChildren().add(discount3);
	}

	/**
	 * Load a new discount VBox for the right side of the catalog.
	 * 
	 * @param index
	 * @return
	 */
	private VBox initDiscountVBox(int index) {
		VBox discountVBox = null;
		try {
			FXMLLoader loader = new FXMLLoader(CatalogDiscountVBox.class.getResource("CatalogDiscountVBox.fxml"));
			discountVBox = (VBox) loader.load();
			((CatalogDiscountVBox) loader.getController()).initVBox(ManageData.products.get(index));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return discountVBox;
	}

	/**
	 * Initialize the value and behavior of the small label near the cart Button.
	 */
	private void initTotalAmountCartLabel() {
		setTotalAmountCartLabel();
		totalAmountCartLabel.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				ScaleTransition transition = new ScaleTransition(Duration.seconds(0.5), totalAmountCartLabel);
				transition.setToX(1.4);
				transition.setToY(1.4);
				transition.play();
				PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
				pause.setOnFinished(e -> {
					transition.setToX(1);
					transition.setToY(1);
					transition.play();
				});
				pause.play();
			}
		});
	}
}
