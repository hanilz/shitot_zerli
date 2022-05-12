package survey;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import client.ClientFormController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
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
		Font font = new Font(24);
		for(Survey survey:surveys) {
			HBox surveyRow = new HBox();
			surveyRow.setPadding(new Insets(20));
			surveyRow.setSpacing(20);
			Label surveyName = new Label("Survey ID: " + survey.getIdSurvey() + "Survey Name: " + survey.getSurveyName());
			surveyName.setFont(font);
			//surveyRow.getChildren().add();
			
			
			
			surveyList.getChildren().add(new Label(survey.getIdSurvey() +" :"+survey.getSurveyName()));//test worked
		}
		//userTable.setItems(users);// set the information in the table
		// TODO Auto-generated method stub
		// add sql connection

	}

}