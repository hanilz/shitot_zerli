package util;

import java.net.URL;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ManageScreens {
	private static Stage stage;
	private static Stage popupStage;

	/**
	 * This method will help us to switch between the screens
	 * 
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
		Platform.runLater(new Runnable() { // this thread will help us change between scenes and avoid exceptions
			@Override
			public void run() {
				stage.setScene(scene);
				stage.setTitle(title);
				stage.show();
			}
		});
	}

	public static void openPopupFXML(URL url, String title) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		try {
			loader = new FXMLLoader(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Parent root = loader.load();
		Scene scene = new Scene(root);
		popupStage = new Stage();
		Platform.runLater(new Runnable() { // this thread will help us change between scenes and avoid exceptions
			@Override
			public void run() {
				popupStage.setTitle(title);
				popupStage.setScene(scene);
				popupStage.initModality(Modality.APPLICATION_MODAL);
				popupStage.showAndWait();
			}
		});
	}

	public static void setStage(Stage stage) {
		ManageScreens.stage = stage;
	}

	public static Stage getStage() {
		return stage;
	}

	public static Stage getPopupStage() {
		return popupStage;
	}
}
