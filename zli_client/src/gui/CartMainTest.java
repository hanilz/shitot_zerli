package gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CartMainTest extends Application {
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
			FXMLLoader loader = new FXMLLoader(getClass().getResource("CartScreen.fxml"));
			root = loader.load();
			primaryStage.setTitle("Cart");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		clientStage = primaryStage;
		primaryStage.show();
	}

}
