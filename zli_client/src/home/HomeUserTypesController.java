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
import util.ManageClients;
import util.ManageScreens;
import util.Screens;
import util.UserType;

public class HomeUserTypesController implements HomeInterface,Initializable {

    @FXML
    private Button exitButton;

    @FXML
    private GridPane gridOptions;

    @FXML
    private HBox hbox;

    @FXML
    private Button logoutButton;

    @FXML
    private Label userNameLabel;
    
    private ArrayList<HomeVBox> buttons = new ArrayList<>();
    
	@Override
	public void exitHomeScreen(MouseEvent event) {
		//release the client from the ocsf server + disconnect from the db
		ManageClients.exitClient();
		//exit window
		System.exit(0);
	}

	@Override
	public void logoutFromUser(MouseEvent event) {
		User.getUserInstance().logout();
		ManageScreens.changeScreenTo(Screens.GUEST_HOME);	
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
					gridOptions.addRow(i);
				buttons.add(new HomeVBox(ButtonsNames[i], urls[i], imagePath[i]));// saving
				this.gridOptions.add(buttons.get(i), i+2 % 3, i+2 / 3);
			}
	}


}