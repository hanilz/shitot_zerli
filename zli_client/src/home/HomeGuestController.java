package home;

import java.net.URL;
import java.util.ResourceBundle;


import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import util.ManageClients;
import util.ManageScreens;
import util.Screens;

public class HomeGuestController implements Initializable {

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
    private Button exitButton;

	@FXML
	private GridPane sideHomeGrid;

	@FXML
	private Label welcomeLbl;
	
    @FXML
    private Label TypeLabel;


	@FXML
	void changeToLoginScreen(MouseEvent event) {
		ManageScreens.changeScreenTo(Screens.LOGIN);
	}
	
    @FXML
    void changeToCatalogScreen(MouseEvent event) {
    	ManageScreens.changeScreenTo(Screens.CATALOG);
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		TypeLabel.setText(User.getUserInstance().getUsername());
		exitButton.setCursor(Cursor.HAND);
		loginBtn.setCursor(Cursor.HAND);
	}
	
    @FXML
    void exitApplication(MouseEvent event) {
		//release the client from the ocsf server + disconnect from the db
		ManageClients.exitClient();
		//exit window
		System.exit(0);
    }

}
