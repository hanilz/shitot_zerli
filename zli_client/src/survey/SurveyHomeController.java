package survey;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.ClientFormController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import util.Commands;
import util.ManageScreens;
import util.Screens;

public class SurveyHomeController implements Initializable {
	// Map<Integer,String> surveys = new HashMap<>();
	HashMap<Integer, String> surveys = new HashMap<>();
	@FXML
	private ImageView homeImage;

	@FXML
	private VBox surveyList;

	@FXML
	void returnHome(MouseEvent event) {
		ManageScreens.changeScreenTo(Screens.USER_HOME);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// create a request to fetch the table data
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.FETCH_SURVEYS);
		Object response = ClientFormController.client.accept(message);
		surveys = (HashMap<Integer, String>) response;
		System.out.println("got surveys");
		if(surveys.isEmpty()) {
			Label noSurveys = new Label("Sorry, no surveys to display at the moment.");
			noSurveys.setFont(new Font(24));
			surveyList.getChildren().add(noSurveys);
		}
		
		for (int surveyID : surveys.keySet()) {
			SurveyHomeRowHBox shrh = new SurveyHomeRowHBox(surveyID, surveys.get(surveyID));
			surveyList.getChildren().add(shrh);
			surveyList.getChildren().add(new Separator(Orientation.HORIZONTAL));
		}

	}

}