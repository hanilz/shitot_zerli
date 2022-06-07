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

/**AnalysisViewRowController used as a controller for the AnalysisRow FXMl
 * the row contains the survey information and the button to fetch and display the survey file
 * initRow must be called before initializing the row
 * @author Eitan
 *
 */
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

    /**when the open report button is pressed the report is fetched and then displayed to the user
     * @param event
     */
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
    
    /**must be called to initialize the row
     * @param surveyID
     * @param surveyName
     */
    public void initRow(int surveyID,String surveyName) {
    	this.surveyID=surveyID;
    	surveryIDNumber.setText(surveyID+"");
    	surveyNameLabel.setText(surveyName);
    }
    
	/**used to open a file for the user afeter pressing the button
	 * @param file
	 */
	private void openFile(File file) {
		try {
			desktop.open(file);
			System.out.println(file.toPath().toString());
		} catch (IOException ex) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
		}
	}
}
