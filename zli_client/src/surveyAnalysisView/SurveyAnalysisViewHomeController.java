package surveyAnalysisView;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import client.ClientFormController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import util.Commands;
import util.ManageScreens;

public class SurveyAnalysisViewHomeController implements Initializable {
	private Desktop desktop = Desktop.getDesktop();
	final FileChooser fileChooser = new FileChooser();
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
		message.put("command", Commands.FETCH_FILES);
		ArrayList<File> files = (ArrayList<File>) ClientFormController.client.accept(message);
		if (!files.isEmpty()) {
			for (File file : files) {
				Button btn = new Button(file.getName());
				btn.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						// configureFileChooser(fileChooser);
						// File file = fileChooser.showOpenDialog(ManageScreens.getStage());
						if (file != null) {
							openFile(file);
						}
					}
				});
				reportList.getChildren().add(btn);
			}
		}

		else
			reportList.getChildren().add(new Label("failed to fetch"));
	}

	private static void configureFileChooser(final FileChooser fileChooser) {
		fileChooser.setTitle("View Files");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
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