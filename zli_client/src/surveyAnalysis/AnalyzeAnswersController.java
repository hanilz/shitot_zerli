package surveyAnalysis;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.ClientFormController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import util.Commands;
import util.ManageScreens;

public class AnalyzeAnswersController implements Initializable {
	public static int surveyID;

	@FXML
	private VBox answerVbox;

	@FXML
	private Button closeButton;

	@FXML
	private Label surveyTitleLabel;

	@FXML
	private Button uploadButton;

	@FXML
	void closeSurvey(ActionEvent event) {
		ManageScreens.previousScreen();
	}

	@FXML
	void uploadAnalysis(ActionEvent event) {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.GET_SURVEY_ANSWERS);
		message.put("surveyID", surveyID);
		Object response = ClientFormController.client.accept(message);

		@SuppressWarnings("unchecked")
		ArrayList<QuestionAnswer> questions = (ArrayList<QuestionAnswer>) response;// (ObservableList<Survey>) response;
		for (QuestionAnswer qa : questions) {
			FXMLLoader loader = new FXMLLoader(QuestionAnswerRowController.class.getResource("QusetionAnswerRow.fxml"));
			HBox surveyRow;
			try {
				surveyRow = (HBox) loader.load();
				((QuestionAnswerRowController) loader.getController()).initRow(qa.getQuestion(), qa.getAnswers());
				answerVbox.getChildren().add(surveyRow);
				answerVbox.getChildren().add(new Separator(Orientation.HORIZONTAL));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void setSurvey(int surveyID2) {
		surveyID = surveyID2;
	}

}
