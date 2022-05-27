package surveyAnalysis;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.ClientFormController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import util.Commands;
import util.ManageScreens;

public class SurveyAnswersHomeController implements Initializable {
	private HashMap<Integer, String> surveys = new HashMap<>();
	@FXML
	private ImageView homeImage;

	@FXML
	private VBox surveyList;

	@FXML
	void returnHome(MouseEvent event) {
		ManageScreens.home();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.FETCH_SURVEYS);
		Object response = ClientFormController.client.accept(message);
		surveys = (HashMap<Integer, String>) response;
		if(surveys.isEmpty()) {
			Label noSurveys = new Label("Sorry, no surveys to display at the moment.");
			noSurveys.setFont(new Font(24));
			surveyList.getChildren().add(noSurveys);
		}
		try {
			for (int surveyID : surveys.keySet()) {
				FXMLLoader loader = new FXMLLoader(AnswerRowController.class.getResource("AnswerRow.fxml"));
				HBox surveyRow = (HBox) loader.load();
				((AnswerRowController) loader.getController()).initRow(surveyID, surveys.get(surveyID));
				surveyList.getChildren().add(surveyRow);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}