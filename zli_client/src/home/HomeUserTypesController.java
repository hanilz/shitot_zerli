package home;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import util.ManageClients;
import util.ManageScreens;

public class HomeUserTypesController implements HomeInterface {

    @FXML
    private Button exitButton;

    @FXML
    private GridPane gridOptions;

    @FXML
    private HBox hbox;

    @FXML
    private Button logoutButton;

    @FXML
    private Label userLabel;
    
	@Override
	public void exitHomeScreen(MouseEvent event) {
		//release the client from the ocsf server + disconnect from the db
		ManageClients.exitClient();
		//exit window
		System.exit(0);
	}

	@Override
	public void logoutFromUser(MouseEvent event) {
		//disconnect from the db
		ManageClients.exitClient();
		//change Scene to guest or show login screen
		try {
			ManageScreens.changeScene(getClass().getResource("HomeGuestScreen.fxml"), "Zli Home: Guest");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}