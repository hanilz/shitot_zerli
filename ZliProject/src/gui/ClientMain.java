package gui;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientMain extends Application {
	public static Stage clientStage;
	
	public static void main(String args[]) throws Exception {
		launch(args);
	}

	/**
	 *Initialize the screen from the fxml file and presenting the screen after the initial from the fxml file
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
		clientStage = primaryStage;
		primaryStage.show();
	}
	
	/**
	 * This method will help us to switch between the screens
	 * @param url
	 * @param title
	 * @throws Exception
	 */
	public static void changeScene(URL url, String title) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		try {
			loader = new FXMLLoader(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Parent root = loader.load();
		Scene scene = new Scene(root);
		Platform.runLater(new Runnable() {  // this thread will help us change between scenes and avoid exceptions
		    @Override
		    public void run() {
		    	clientStage.setScene(scene);
				clientStage.setTitle(title);
				clientStage.show();		    
			}
		});
	}
}
