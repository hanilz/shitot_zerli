package surveyAnalysis;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import util.ManageScreens;
import util.Screens;

public class AnswerRowController {
	private int surveyID;
	private String surveyName;
	@FXML
	private Label surveyTitle;

	@FXML
	private Button viewAnswersButton;

	@FXML
	void viewAnswers(ActionEvent event) {
		AnalyzeAnswersController.setSurvey(surveyID);
		ManageScreens.changeScreenTo(Screens.VIEW_ANSWERS_FOR_SURVEY);
	}

	public void initRow(int surveyID, String surveyName) {
		this.surveyID = surveyID;
		this.surveyName = surveyName;
		surveyTitle.setText("Survey ID: " + surveyID + "\tSurvey Name: " + surveyName);
	}

}