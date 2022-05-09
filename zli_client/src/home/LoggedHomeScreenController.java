package home;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import entities.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import util.ChangeScreen;
import util.UserType;

public class LoggedHomeScreenController implements Initializable {
	@FXML
	private GridPane buttonGrid;

	@FXML
	private HBox hbox;

	@FXML
	private Button logoutButton;

	@FXML
	private GridPane sideHomeGrid;

	@FXML
	private Label userNameLabel;

	private ArrayList<HomeVBox> buttons = new ArrayList<>();

	@FXML
	void changeScreenGuestHome(MouseEvent event) {// Logout
		User.getUserInstance().logout();
		try {
			ChangeScreen.changeScene(getClass().getResource("HomeNotLoggedInScreen.fxml"), "Home");
		} catch (Exception e) {
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String[] buttonsNames = null, fxmls = null, imagePath = null;
		ArrayList<String[]> typeGUI = UserType.get(User.getUserInstance().getType());// get user gui
		if (typeGUI.get(0) != null && typeGUI.get(1) != null && typeGUI.get(2) != null)// check data is valid
			setScreen(typeGUI.get(0), typeGUI.get(1), typeGUI.get(2));// implement GUI in Home
		userNameLabel.setText(User.getUserInstance().getUsername());

	}

	private void setScreen(String[] ButtonsNames, String[] urls, String[] imagePath) {// implement gui in home
		if (ButtonsNames != null)
			for (int i = 0; i < ButtonsNames.length; i++) {
				if (i / 3 > 0 && i % 3 == 0)
					buttonGrid.addRow(i);
				if (i / 3 == 0) {
					buttonGrid.addColumn(i+1);
				}
				buttons.add(new HomeVBox(ButtonsNames[i], urls[i], imagePath[i]));// saving
				this.buttonGrid.add(buttons.get(i), i+1 % 3, i+1 / 3);
				;
			}
	}

}
