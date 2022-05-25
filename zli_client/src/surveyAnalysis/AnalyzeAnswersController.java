package surveyAnalysis;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.ClientFormController;
import javafx.animation.PauseTransition;
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
import javafx.stage.FileChooser;
import javafx.util.Duration;
import util.Commands;
import util.ManageScreens;

public class AnalyzeAnswersController implements Initializable {
	public static int surveyID;
	
	private File savedFile;

    @FXML
    private Button submitButton;
    
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
    void submitAnalysis(ActionEvent event) {
    	System.out.println("TODO save file to db "+savedFile.getName());
    	submitButton.setDisable(true);
    	submitButton.setText("submitted");
    	PauseTransition pause = new PauseTransition(Duration.seconds(5));
    	pause.setOnFinished(event2 ->{
	    	submitButton.setText("Submit Analysis");
    		}
    	);
    	pause.play();
    	//TODO upload to db
		ManageScreens.displayAlert("File Uploaded","Your file has been uploaded");
    }

	@FXML
	void uploadAnalysis(ActionEvent event) {
		final FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(ManageScreens.getStage());
        if (file != null) {
            savedFile=file;
            submitButton.setDisable(false);
        }
        else
        	submitButton.setDisable(true);
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
