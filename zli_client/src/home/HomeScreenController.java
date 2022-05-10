package home;

import java.net.URL;
import java.util.ResourceBundle;

import catalog.CatalogController;
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
import util.Screens;

public class HomeScreenController implements Initializable {// Guest

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
		ChangeScreen.to(Screens.LOGIN);
	}

	void changeToLoggedHomeScreen() {// maybe can be removed
		ChangeScreen.to(Screens.USER_HOME);
	}

	@FXML
	void changeToCatalogScreen(MouseEvent event) {
		ChangeScreen.to(Screens.CATALOG);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		userNameLabel.setText(User.getUserInstance().getUsername());
	}

}
