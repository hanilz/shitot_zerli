package survey;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import util.ManageScreens;
import util.Screens;

public class SurveyHomeRowHBox extends HBox{
	private Survey survey; 
	public SurveyHomeRowHBox(Survey survey) {
		this.survey=survey;
		this.setAlignment(Pos.CENTER_LEFT);
		initHBox();
	}
	
	
	private void initHBox() {
		Font font = new Font(24);
		this.setPadding(new Insets(20));
		this.setSpacing(20);
		Label surveyName = new Label("Survey ID: " + survey.getIdSurvey() + "\tSurvey Name: " + survey.getSurveyName());
		surveyName.setFont(font);
		surveyName.setPrefWidth(700);
		this.getChildren().add(surveyName);
		Button btn = new Button("Fill Survey");
		
		btn.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		        System.out.println("Clicked "+ survey.getIdSurvey());
		        SurveyController.setSurvey(survey);
		        ManageScreens.changeScreenTo(Screens.SURVEY);
		    }
		});
		
		this.getChildren().add(btn);
	}
}
