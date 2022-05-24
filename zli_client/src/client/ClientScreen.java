package client;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import util.MailService;
import util.ManageScreens;

public class ClientScreen extends Application {

	public static void main(String args[]) throws Exception {
		launch(args);
	}

	/**
	 * Initialize the screen from the fxml file and presenting the screen after the
	 * initial from the fxml file
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ClientScreen.fxml"));
			root = loader.load();
			primaryStage.setTitle("Zli: Connect To Server");
			primaryStage.getIcons().add(new Image("/resources/connectWallpaper.png"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		scene.getStylesheets().add("resources/css/ClientScreen.css");
		ManageScreens.setStage(primaryStage);
		primaryStage.show();
	}

}
