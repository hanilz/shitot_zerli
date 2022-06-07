package surveyAnalysis;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import util.ManageScreens;
import util.Screens;

/**
 * used as a controller for the AnswerRow FXML that is used to display a row of
 * a survey for the user to view the answers for it
 * Must be initialized with surveyID and surveyName to work
 * @author Eitan
 *
 */
public class AnswerRowController {
	private int surveyID;
	private String surveyName;
	@FXML
	private Label surveyTitle;

	@FXML
	private Button viewAnswersButton;

	/**
	 * when the view Answers button is pressed the answers for the survey are
	 * fetched, the loaded into the next screen
	 * 
	 * @param event
	 */
	@FXML
	void viewAnswers(ActionEvent event) {
		AnalyzeAnswersController.setSurvey(surveyID, surveyName);
		ManageScreens.changeScreenTo(Screens.VIEW_ANSWERS_FOR_SURVEY);
	}

	/**used to initialize the details for the row
	 * @param surveyID
	 * @param surveyName
	 */
	public void initRow(int surveyID, String surveyName) {
		this.surveyID = surveyID;
		this.surveyName = surveyName;
		surveyTitle.setText("Survey ID: " + surveyID + "\tSurvey Name: " + surveyName);
	}

}