package survey;

import java.util.ArrayList;
import java.util.HashMap;

import client.ClientFormController;
import entities.Survey;
import entities.SurveyQuestion;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import util.Commands;
import util.ManageScreens;
import util.Screens;

/**
 * SurveyHomeRowHBox extends HBox represents a row for a survey that can be
 * filled by the user in the survey screen
 * 
 * @author Eitan
 *
 */
public class SurveyHomeRowHBox extends HBox {
	private int surveyID;
	private String title;
	private Survey survey;

	public SurveyHomeRowHBox(int surveyID, String title) {
		this.surveyID = surveyID;
		this.title = title;
		this.setAlignment(Pos.CENTER_LEFT);
		initHBox();
	}

	private void initHBox() {
		this.setId("surveyHBox");
		this.setPadding(new Insets(20));
		this.setSpacing(20);
		Label surveyName = new Label("Survey ID: " + surveyID + "\tSurvey Name: " + title);
		surveyName.setId("surveyName");
		surveyName.setPrefWidth(1000);
		this.getChildren().add(surveyName);
		Button btn = new Button("Fill Survey");

		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				HashMap<String, Object> message = new HashMap<>();
				message.put("command", Commands.GET_SERVEY);
				message.put("surveyID", surveyID);
				Object response = ClientFormController.client.accept(message);

				@SuppressWarnings("unchecked")
				ArrayList<SurveyQuestion> questions = (ArrayList<SurveyQuestion>) response;
				survey = new Survey(surveyID, title, questions);
				SurveyController.setSurvey(survey);
				ManageScreens.changeScreenTo(Screens.SURVEY);
			}
		});

		this.getChildren().add(btn);
	}
}
