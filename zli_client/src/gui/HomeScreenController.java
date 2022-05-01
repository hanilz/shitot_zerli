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
	void goToLoignScreen(MouseEvent event) {
		try {
			ClientMain.changeScene(getClass().getResource("LoginScreen.fxml"), "Loign");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
