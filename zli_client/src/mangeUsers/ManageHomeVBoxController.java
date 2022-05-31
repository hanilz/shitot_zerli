package mangeUsers;

import entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import util.ManageScreens;
import util.Screens;
import util.UserType;

public class ManageHomeVBoxController {

	@FXML
	private VBox screenBox;

	@FXML
	private ImageView screenImage;

	@FXML
	private Label screenName;
	@FXML
	private Button removeButton;

	private Screens screen;

	public void setScreen(Screens screen) {
		this.screen = screen;
		screenName.setText(ManageScreens.getName(screen));
		screenImage.setImage(new Image(ManageScreens.getIconPath(screen)));
		if (isManagerPremissionScreen())
			removeButton.setVisible(false);
	}

	@FXML
	void removeScreen(MouseEvent event) {
		ManageUsersPermissionController.removeScreen(screen);
	}

	private boolean isManagerPremissionScreen() {//store manager cannot remove screen permission from himself but can remove from other StoreMangers
		return ((ManageUsersPermissionController.connect().User().getIdUser()) == (User.getUserInstance().getIdUser())
				&& User.getUserInstance().getType().equals(UserType.STORE_MANGER)
				&& screen.equals(Screens.USER_PREMISSION))
				|| (ManageUsersPermissionController.connect().User().getUserType()
						.equals(UserType.STORE_MANGER.toString()) && screen.equals(Screens.USER_PREMISSION)
						&& !User.getUserInstance().getType().equals(UserType.STORE_MANGER));
	}

}
