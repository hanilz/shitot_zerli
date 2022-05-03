package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class HomeScreenController {

	@FXML
	private Button catalogButton;

	@FXML
	private ImageView catalogImage;

	@FXML
	private ImageView editProfileImage;

	@FXML
	private GridPane gridOptions;

	@FXML
	private HBox hbox;

	@FXML
	private Button loginBtn;

	@FXML
	private ImageView ordersImage;

	@FXML
	private Button registerBtn;

	@FXML
	private GridPane sideHomeGrid;

	@FXML
	private Label welcomeLbl;

	@FXML
	void changeToLoginScreen(MouseEvent event) {
		try {
			ClientScreen.changeScene(getClass().getResource("LoginScreen.fxml"), "Login");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    @FXML
    void changeToCatalogScreen(MouseEvent event) {
    	try {
			ClientScreen.changeScene(getClass().getResource("CatalogScreen2.fxml"), "Catalog");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	

}
