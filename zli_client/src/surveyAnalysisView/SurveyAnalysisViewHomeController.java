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

public class SurveyAnalysisViewHomeController implements Initializable {
	private HashMap<Integer, String> surveyMap;
	@FXML
	private ImageView homeImage;

	@FXML
	private VBox reportList;

	@FXML
	void returnHome(MouseEvent event) {
		ManageScreens.home();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.FETCH_ANALYZED_SURVEYS);
		surveyMap = (HashMap<Integer, String>) ClientFormController.client.accept(message);
		for(int surveyID : surveyMap.keySet()) {
			try {
				reportList.getChildren().add(loadRow(surveyID,surveyMap.get(surveyID)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private HBox loadRow(int surveyID, String surveyTitle) throws IOException {
		FXMLLoader loader = new FXMLLoader(AnalysisViewRowController.class.getResource("AnalysisViewRow.fxml"));
		HBox orderRow = (HBox) loader.load();
		((AnalysisViewRowController) loader.getController()).initRow(surveyID,surveyTitle);
		return orderRow;
	}

//	private static void configureFileChooser(final FileChooser fileChooser) {
//		fileChooser.setTitle("View Files");
//		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
//		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
//	}

}
//		if (!files.isEmpty()) {
//			for (SurveyAnalysisFile file : files) {
//				File newFile = FilesHandler.saveFile(file);
//				Button btn = new Button(file.getFileName());
//				btn.setOnAction(new EventHandler<ActionEvent>() {
//					@Override
//					public void handle(ActionEvent event) {
//						if (newFile != null) {
//							openFile(newFile);
//						}
//					}
//				});
//				reportList.getChildren().add(btn);
//			}
//		}
//
//		else
//			reportList.getChildren().add(new Label("failed to fetch"));