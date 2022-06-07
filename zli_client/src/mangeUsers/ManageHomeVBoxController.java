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

/**
 * @author dolev
 *A screen to remove to a specific user shown as a
 *         card(similar to card on home screen)
 */
public class ManageHomeVBoxController {

	/**
	 *GUI VBox to show how the card will be shown to the specific user in his home
	 * screen
	 */
	@FXML
	private VBox screenBox;
	/**
	 * GUI Screen image of the card
	 */
	@FXML
	private ImageView screenImage;
	/**
	 * GUI Name of the Card(name of the action/screen)
	 */
	@FXML
	private Label screenName;
	/**
	 * GUI Button to remove screen from employee home screen
	 */
	@FXML
	private Button removeButton;
	/**
	 * The screen the card will act to
	 */
	private Screens screen;

	/**
	 * @param screen Setting the Card to show the screen attributes
	 */
	public void setScreen(Screens screen) {
		this.screen = screen;
		screenName.setText(ManageScreens.getName(screen));
		screenImage.setImage(new Image(ManageScreens.getIconPath(screen)));
		if (isManagerPremissionScreen())
			removeButton.setVisible(false);
	}

	/**
	 * Remove the screen from employee home screen
	 */
	@FXML
	void removeScreen(MouseEvent event) {
		ManageUsersPermissionController.removeScreen(screen);
	}

	/**
	 * @return Make sure Manager cannot remove permission from himself
	 * 
	 */
	private boolean isManagerPremissionScreen() {//store manager cannot remove screen permission from himself but can remove from other StoreMangers
		return ((ManageUsersPermissionController.connect().User().getIdUser()) == (User.getUserInstance().getIdUser())
				&& User.getUserInstance().getType().equals(UserType.STORE_MANGER)
				&& screen.equals(Screens.USER_PREMISSION))
				|| (ManageUsersPermissionController.connect().User().getUserType()
						.equals(UserType.STORE_MANGER.toString()) && screen.equals(Screens.USER_PREMISSION)
						&& !User.getUserInstance().getType().equals(UserType.STORE_MANGER));
	}

}
