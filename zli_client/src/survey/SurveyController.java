package survey;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.ClientFormController;
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
    	//int[] answers = new int[survey.getQuestions().size()];
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
		message.put("command", "Submit survey Answer");
		message.put("answers", answers);
		Object response = ClientFormController.client.accept(message);
		System.out.println((String)response);
    	ManageScreens.changeScreenTo(Screens.SURVEY_HOME);
    }

    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if(survey==null)
			System.out.println("null survey");
		
		surveyTitleLabel.setText(survey.getSurveyName());
		
		for (int i = 0; i < survey.getQuestions().size(); i++) {
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
