package gui;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ClientMain extends Application {
	public static Stage clientStage;
	
	public static void main(String args[]) throws Exception {
		launch(args);
	}

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
		clientStage = primaryStage;
		primaryStage.show();
	}
	
	public static void changeScene(URL url, String title) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		try {
			loader = new FXMLLoader(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Parent root = loader.load();
		Scene scene = new Scene(root);
		clientStage.setScene(scene);
		clientStage.setTitle(title);
		clientStage.show();
	}
}
