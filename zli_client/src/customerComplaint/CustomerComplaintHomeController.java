package customerComplaint;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import util.ManageScreens;
import util.Screens;

public class CustomerComplaintHomeController implements Initializable{

    @FXML
    private VBox complaintList;

    @FXML
    private ImageView homeImage;

    @FXML
    private Button newCustomerComplaintButton;

    @FXML
    void createNewCustomerComplaint(ActionEvent event) {
    	ManageScreens.changeScreenTo(Screens.COMPLAINT);
    }

    @FXML
    void returnHome(MouseEvent event) {
    	ManageScreens.changeScreenTo(Screens.USER_HOME);
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
    
}

	


