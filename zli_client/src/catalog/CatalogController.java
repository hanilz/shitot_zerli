package catalog;

import java.net.URL;
import java.util.ResourceBundle;

import entities.Cart;
import entities.User;
import home.LoginScreenController;
import javafx.fxml.FXML;
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
import javafx.scene.layout.VBox;
import util.ManageData;
import util.ManageScreens;
import util.Screens;

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

	@FXML
	void changeToCartScreen(MouseEvent event) {
		LoginScreenController.enableHomeFlow(true);
		ManageScreens.changeScreenTo(Screens.CART);
	}

	@FXML
	void changeToHomeScreen(MouseEvent event) {
		LoginScreenController.enableHomeFlow(true);
		ManageScreens.home();
	}

    @FXML
    void openLogin(MouseEvent event) {
    	if(!User.getUserInstance().isUserLoggedIn())
		ManageScreens.changeScreenTo(Screens.LOGIN);
    	else {
    		User.getUserInstance().logout();
    		ManageScreens.changeScreenTo(Screens.CATALOG);
    	}
	}

	@FXML
	void search(MouseEvent event) {

	}
	
	public static void refreshTotalAmountInCart() {
		instance.setTotalAmountCartLabel();
	}
	
	private void setTotalAmountCartLabel() {
		totalAmountCartLabel.setText(""+Cart.getInstance().getCart().size());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
		setTotalAmountCartLabel();
		catalogGrid = ManageData.catalogGrid;
		catalogScrollPane.setContent(catalogGrid);
		LoginScreenController.enableHomeFlow(false);
		if(User.getUserInstance().isUserLoggedIn())
		{
			loginLabel.setText("Welcome "+User.getUserInstance().getUsername());
			loginVBox.setPrefHeight(73);
			loginVBox.setPrefWidth(100);
			loginIcon.setImage(new Image("resources/home/logout.png"));
		}
	}
}
