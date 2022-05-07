package client;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
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
			primaryStage.setTitle("Zli Client");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		ManageScreens.setStage(primaryStage);
		primaryStage.show();
	}

}