package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginScreenController {

    @FXML
    private Button loginButton;

    @FXML
    private TextField passwordLabel;

    @FXML
    private TextField usernameLabel;

    @FXML
    void loginUserIntoSystem(MouseEvent event) {
    	String username = usernameLabel.getText();
    	String password = passwordLabel.getText();
    	//at this point we need to login the user into the system 
    	//but we need to still add the functionality on the server side
    	//** add server connection
    	//we assume the credentials were entered correctly
		try {
			ClientMain.changeScene(getClass().getResource("CatalogScreen.fxml"), "Catalog");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    
    // TODO: handle login with OCSF
    private Boolean loginClient(String username,String password) {
//    	if(user connected(username,password)) {
//    		return false;
//    	}
//    	
    	return true;
    }
}
