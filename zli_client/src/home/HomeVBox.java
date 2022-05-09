package home;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import util.ChangeScreen;

public class HomeVBox extends VBox {
	private ImageView image;
	private Button button;
	private String url;

	public HomeVBox(String buttonName, String url, String imagePath) {
		this.url = url;
		button = new Button();
		button.setText(buttonName);
		if (imagePath != null)
			image = new ImageView(imagePath);
		init();
	}

	public void init() {
		this.getChildren().add(button);
		if (image != null) {
			image.setFitWidth(147);
			image.setFitHeight(150);
			this.getChildren().add(image);
		}
		button.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				try {
					ChangeScreen.changeScene(getClass().getResource(url), button.getText());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		this.setAlignment(Pos.CENTER);
	}

}
