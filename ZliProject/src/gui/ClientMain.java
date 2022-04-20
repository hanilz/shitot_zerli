package gui;

import java.io.IOException;

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
//		ClientMain.clientStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//		    @Override
//		    public void handle(WindowEvent e) {
//		        // SHOW A DIALOG HERE
//		    	System.out.println("Bye bye!");
//		        System.exit(0);
//		    }
//		});
		primaryStage.show();
	}
}
