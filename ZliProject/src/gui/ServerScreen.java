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

	@Override
	public void start(Stage primaryStage) throws Exception {
		// AnchorPane anchorPane;
		Parent root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ServerScreen.fxml"));
			root = loader.load();
			primaryStage.setTitle("Zli Server Configuration");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

}
