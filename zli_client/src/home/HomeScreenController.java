package home;

import java.net.URL;
import java.util.ResourceBundle;

import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import util.ChangeScreen;

public class HomeScreenController implements Initializable {//Guest

	@FXML
    private Button catalogButton;

    @FXML
    private HBox hbox;

    @FXML
    private Button loginBtn;

    @FXML
    private GridPane sideHomeGrid;

    @FXML
    private Label userNameLabel;

	@FXML
	void changeToLoginScreen(MouseEvent event) {
		try {
			ChangeScreen.changeScene(getClass().getResource("LoginScreen.fxml"), "Login");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void changeToLoggedHomeScreen() {
		try {
			ChangeScreen.changeScene(ChangeScreen.class.getResource("../home/HomeLoggedInScreen.fxml"), "HomeScreen");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    @FXML
    void changeToCatalogScreen(MouseEvent event) {
    	try {
    		ChangeScreen.changeScene(ChangeScreen.class.getResource("../catalog/CatalogScreen.fxml"), "Catalog");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if(User.getUserInstance().isUserLoggedIn())
			try {
				ChangeScreen.changeScene(getClass().getResource("../home/HomeLoggedInScreen.fxml"), "HomeScreen");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		userNameLabel.setText(User.getUserInstance().getUsername());		
	}
	
	

}
