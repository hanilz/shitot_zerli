package surveyAnalysisView;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.ClientFormController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import util.Commands;
import util.ManageScreens;

/**
 * SurveyAnalysisViewHomeController used as controller for the
 * SurveyReportsHomeScreen FXML file loads all the surveys that have reports
 * uploaded for them and allows the user to open the uploaded file
 * 
 * @author Eitan
 *
 */
public class SurveyAnalysisViewHomeController implements Initializable {
	private HashMap<Integer, String> surveyMap;
	@FXML
	private ImageView homeImage;

	@FXML
	private VBox reportList;

	/**used to return to the home screen
	 * @param event
	 */
	@FXML
	void returnHome(MouseEvent event) {
		ManageScreens.home();
	}

	/**
	 *used to get the surveys that have answers and loads the rows for the user
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.FETCH_ANALYZED_SURVEYS);
		surveyMap = (HashMap<Integer, String>) ClientFormController.client.accept(message);
		for (int surveyID : surveyMap.keySet()) {
			try {
				reportList.getChildren().add(loadRow(surveyID, surveyMap.get(surveyID)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**used to load a survey row
	 * @param surveyID
	 * @param surveyTitle
	 * @return
	 * @throws IOException
	 */
	private HBox loadRow(int surveyID, String surveyTitle) throws IOException {
		FXMLLoader loader = new FXMLLoader(AnalysisViewRowController.class.getResource("AnalysisViewRow.fxml"));
		HBox orderRow = (HBox) loader.load();
		((AnalysisViewRowController) loader.getController()).initRow(surveyID, surveyTitle);
		return orderRow;
	}
}