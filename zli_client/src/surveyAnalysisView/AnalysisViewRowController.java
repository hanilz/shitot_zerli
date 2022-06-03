package surveyAnalysisView;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import client.ClientFormController;
import entities.SurveyAnalysisFile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import util.Commands;
import util.FilesHandler;

public class AnalysisViewRowController {
	private Desktop desktop = Desktop.getDesktop();
	private File surveyFile;
	private int surveyID;
	
    @FXML
    private Label surveryIDNumber;

    @FXML
    private Label surveyNameLabel;

    @FXML
    private Button viewAnalysisButton;

    @FXML
    void openReportAnalysis(ActionEvent event) {
    	if(surveyFile == null) {
    		HashMap<String, Object> message = new HashMap<>();
    		message.put("command", Commands.FETCH_FILE);
    		message.put("idSurvey", surveyID);
    		SurveyAnalysisFile file = (SurveyAnalysisFile) ClientFormController.client.accept(message);
    		surveyFile=FilesHandler.saveFile(file);
    	}
    	openFile(surveyFile);
    }
    
    public void initRow(int surveyID,String surveyName) {
    	this.surveyID=surveyID;
    	surveryIDNumber.setText(surveyID+"");
    	surveyNameLabel.setText(surveyName);
    }
    
	private void openFile(File file) {
		try {
			desktop.open(file);
			System.out.println(file.toPath().toString());
		} catch (IOException ex) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
		}
	}
}
