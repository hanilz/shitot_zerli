package survey;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SurveyController implements Initializable{

	Survey questionnere;
	
    @FXML
    private Button discardButton;

    @FXML
    private Label qTitleLabel;

    @FXML
    private VBox questionVbox;

    @FXML
    private Button submitButton;

    @FXML
    void discardQuestionnaireAnswer(ActionEvent event) {
    	
    }

    @FXML
    void saveQuestionnaireAnswer(ActionEvent event) {

    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}

}
