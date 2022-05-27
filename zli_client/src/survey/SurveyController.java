package survey;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.ClientFormController;
import entities.Survey;
import entities.SurveyQuestion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import util.Commands;
import util.ManageScreens;
import util.Screens;

/**
 * @author Eitan
 *	SurveyController class is used to display a single survey once it is selected
 *	to initialize the survey the method calling this class has to first initialize the survey in order for it to be displayed
 */
public class SurveyController implements Initializable{

	private static Survey survey = new Survey(0, "Not Initialized", new ArrayList<SurveyQuestion>());
	
    @FXML
    private Label errorLabel;
	
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
    	HashMap<SurveyQuestion,Integer> answers = new HashMap<>();
    	for (int i = 0; i < survey.getQuestions().size(); i++) {
    		int ans = ((questionHBox) questionVbox.getChildren().get(i*2)).getSelected();
    		if(ans == 0) {
    			errorLabel.setVisible(true);
    			return;
    		}
    		answers.put(survey.getQuestion(i),ans);
		}
    	HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.SUBMIT_SURVEY);
		message.put("answers", answers);
		Object response = ClientFormController.client.accept(message);
		System.out.println((String)response);
    	ManageScreens.changeScreenTo(Screens.SURVEY_HOME);
    	ManageScreens.displayAlert("Survey Saved", "Thank you for filling our survey!\nyour opinion really matters to us!");
    }

    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if(survey==null)
			System.out.println("null survey");
		
		surveyTitleLabel.setText(survey.getSurveyName());
		
		for (int i = 0; i < survey.getQuestions().size(); i++) {
			questionHBox question = new questionHBox(survey.getQuestion(i));
			questionVbox.getChildren().add(question);
			questionVbox.getChildren().add(new Separator(Orientation.HORIZONTAL));
		}
	}
	
	public static void setSurvey(Survey survey2) {
		survey = survey2;
	}

}
