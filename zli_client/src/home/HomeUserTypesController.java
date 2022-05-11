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
		ArrayList<Screens> userScreens = ManageClients.getUserScreens(User.getUserInstance().getType());
		setScreen(userScreens);// 
		userNameLabel.setText(User.getUserInstance().getUsername());	
	}

	private void setScreen(ArrayList<Screens> userScreens) {// 
		if (userScreens != null)
			for (int i = 0; i < userScreens.size();i++) {
				if (i / 3 > 0 && i % 3 == 0)
					gridOptions.addRow(i/3);
				buttons.add(new HomeVBox(userScreens.get(i)));
				this.gridOptions.add(buttons.get(i), i+2 % 3, i+2 / 3);
			}
		System.out.println(buttons);
	}
	


}