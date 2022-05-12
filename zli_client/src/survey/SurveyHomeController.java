package survey;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import client.ClientFormController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import util.ManageScreens;
import util.Screens;

public class SurveyHomeController implements Initializable {
	//Map<Integer,String> surveys = new HashMap<>();
	List<Survey> surveys = new ArrayList<>();
 	@FXML
	private ImageView homeImage;

	@FXML
	private VBox surveyList;

	@FXML
	void returnHome(MouseEvent event) {
		ManageScreens.changeScreenTo(Screens.USER_HOME);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// create a request to fetch the table data
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", "Fetch Surveys");
		Object response = ClientFormController.client.accept(message);
		surveys = (ArrayList)response;//(ObservableList<Survey>) response;
		System.out.println("got surveys");

		for(Survey survey:surveys) {
			SurveyHomeRowHBox shrh = new SurveyHomeRowHBox(survey);
			
			surveyList.getChildren().add(shrh);
			surveyList.getChildren().add(new Separator(Orientation.HORIZONTAL));
			
			
			//surveyList.getChildren().add(new Label(survey.getIdSurvey() +" :"+survey.getSurveyName()));//test worked
		}
		//userTable.setItems(users);// set the information in the table
		// TODO Auto-generated method stub
		// add sql connection

	}

}