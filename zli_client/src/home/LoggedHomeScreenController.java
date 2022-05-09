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

public class LoggedHomeScreenController implements Initializable {

	  @FXML
	    private GridPane buttonGrid;

	    @FXML
	    private Button editProfileButton;

	    @FXML
	    private HBox hbox;

	    @FXML
	    private Button logoutButton;

	    @FXML
	    private GridPane sideHomeGrid;

	    @FXML
	    private Label userNameLabel;


	    @FXML
	    void changeScreenGuestHome(MouseEvent event) {// Logout
		User.getUserInstance().logout();
		try {
			ChangeScreen.changeScene(getClass().getResource("HomeNotLoggedInScreen.fxml"), "Home");
		} catch (Exception e) {
		}

	}

	@FXML
	void changeScreenToEdit(MouseEvent event) {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		userNameLabel.setText(User.getUserInstance().getUsername());

	}

}
