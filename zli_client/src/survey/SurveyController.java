package survey;

import java.net.URL;
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

	Survey survey;
	
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
    	// TODO add SQL saving functionality
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

}
