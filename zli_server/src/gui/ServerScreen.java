package gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class ServerScreen extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 *Initialize the screen from the fxml file and presenting the screen after the initial from the fxml file
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root;
		ServerFormController cont;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ServerScreen.fxml"));
			root = loader.load();
			cont = loader.getController();
			primaryStage.setTitle("Zli Server Configuration");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.setOnCloseRequest((event) -> {
			cont.closeServerWindow();
	    });
		primaryStage.show();

	}

}
