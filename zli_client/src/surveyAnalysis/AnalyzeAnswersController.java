package surveyAnalysis;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.ClientFormController;
import entities.QuestionAnswer;
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
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import util.Commands;
import util.ManageScreens;

/**AnalyzeAnswersController used as a controller class for the AnalyzeAnswersScreen FXML
 * needs to be initializes with surveyID and surveyName to work
 * allows the user to upload a file to the DB as a reort for the survey
 * @author Eitan
 *
 */
public class AnalyzeAnswersController implements Initializable {
	private static int surveyID;
	private static String surveyName;

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

	/**used to close the screen and return to the previous one
	 * @param event
	 */
	@FXML
	void closeSurvey(ActionEvent event) {
		ManageScreens.previousScreen();
	}

	/**used to submit a survey analysis to the DB after uploading it
	 * @param event
	 */
	@FXML
	void submitAnalysis(ActionEvent event) {
		if(!convertAndUploadToDb(savedFile)) {
			ManageScreens.displayAlert("Failed to Upload", "Your file failed to upload, Please try again");
			return;
		}
		System.out.println("TODO save file to db " + savedFile.getName());
		submitButton.setDisable(true);
		submitButton.setText("submitted");
		PauseTransition pause = new PauseTransition(Duration.seconds(5));
		pause.setOnFinished(event2 -> {
			submitButton.setText("Submit Analysis");
		});
		pause.play();
		ManageScreens.displayAlert("File Uploaded", "Your file has been uploaded");
	}

	/**Uploads a file to the DB
	 * @param savedFile2
	 * @return
	 */
	private boolean convertAndUploadToDb(File savedFile2) {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.UPLOAD_FILE);
		message.put("FILE", savedFile2);
		message.put("surveyID", surveyID);
		Object response = ClientFormController.client.accept(message);
		return (boolean)response;
	}

	/**Used to allow the user to upload a file to the class to later be uploaded to the DB
	 * @param event
	 */
	@FXML
	void uploadAnalysis(ActionEvent event) {
		final FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(ManageScreens.getStage());
		if (file != null) {
			savedFile = file;
			submitButton.setDisable(false);
		} else
			submitButton.setDisable(true);
	}

	
	/**
	 *used to get and initialize the survey answers 
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		surveyTitleLabel.setText(surveyName);
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.GET_SURVEY_ANSWERS);
		message.put("surveyID", surveyID);
		Object response = ClientFormController.client.accept(message);

		@SuppressWarnings("unchecked")
		ArrayList<QuestionAnswer> questions = (ArrayList<QuestionAnswer>) response;// (ObservableList<Survey>) response;
		if(questions.isEmpty()) {
			Label noSurveys = new Label("Sorry, No one answered this survey yet, noting to display.");
			noSurveys.setFont(new Font(24));
			answerVbox.getChildren().add(noSurveys);
			uploadButton.setDisable(true);
		}
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

	/**used to set the survey and name and id
	 * must be called before initializing the screen
	 * @param surveyID2
	 * @param surveyName2
	 */
	public static void setSurvey(int surveyID2, String surveyName2) {
		surveyID = surveyID2;
		surveyName = surveyName2;
	}
	
}
