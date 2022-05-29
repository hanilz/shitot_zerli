package mangeUsers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import util.ManageScreens;
import util.Screens;

public class ManageHomeVBoxController {

	@FXML
	private VBox screenBox;

	@FXML
	private ImageView screenImage;

	@FXML
	private Label screenName;
	private Screens screen;

	public void setScreen(Screens screen) {
		this.screen=screen;
		screenName.setText(ManageScreens.getName(screen));
		screenImage.setImage(new Image(ManageScreens.getIconPath(screen)));
		
	}

    @FXML
    void removeScreen(MouseEvent event) {
		ManageUsersPermissionController.removeScreen(screen);
	}

}
