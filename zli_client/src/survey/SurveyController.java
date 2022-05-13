package survey;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import util.ManageScreens;
import util.Screens;

public class SurveyController implements Initializable{

	private static Survey survey;
	
    @FXML
    private Button discardButton;

    @FXML
    private Label surveyTitleLabel;

    @FXML
    private VBox questionVbox;

    @FXML
    private Button submitButton;

    @FXML
    void discardSurveyAnswer(ActionEvent event) {
    	ManageScreens.changeScreenTo(Screens.SURVEY_HOME);
    }

    @FXML
    void saveSurveyAnswer(ActionEvent event) {
    	int[] answers = new int[6];
    	for (int i = 0; i < 6; i++) {
    		answers[i] = ((questionHBox) questionVbox.getChildren().get(i*2)).getSelected();
		}
    	// TODO add SQL saving functionality
    	System.out.println(Arrays.toString(answers));
    	ManageScreens.changeScreenTo(Screens.SURVEY_HOME);
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		surveyTitleLabel.setText(survey.getSurveyName());
		
		for (int i = 0; i < 6; i++) {
			//TODO add questions using question hBox
			questionHBox question = new questionHBox(survey.getQuestion(i));
			questionVbox.getChildren().add(question);
			questionVbox.getChildren().add(new Separator(Orientation.HORIZONTAL));
		}
	}
	
	public static void setSurvey(Survey survey2) {
		survey = survey2;
	}

}
