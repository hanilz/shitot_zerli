package mangeUsers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import util.ManageScreens;
import util.Screens;

/**
 * @author dolev An optional screen to add to a specific user shown as a
 *         card(similar to card on home screen)
 */
public class AddVBoxController {

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
	}

	/**
	 * @param event If the specific user have less then 6 screens then adding the
	 *              card to Manage Screen and removing the card from add user screen
	 */
	@FXML
	void addScreen(MouseEvent event) {
		if (ManageUsersPermissionController.getUserScreens().size() < 6) {
			ManageUsersPermissionController.addScreen(screen);
			AddScreensController.removeScreens(screen);
		}
		AddScreensController.setLimitText();
	}
}
