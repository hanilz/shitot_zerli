package deliveryCoordination;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import util.ManageScreens;

public class DeliveryCoordinatorController implements Initializable {

	@FXML
	private ImageView homeImage;

	@FXML
	private VBox orderVBox;

	@FXML
	void returnHome(MouseEvent event) {
		ManageScreens.home();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			// TODO fetch orders for this delivery coordinator
			orderVBox.getChildren().add(loadRow());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private HBox loadRow() throws IOException {
		FXMLLoader loader = new FXMLLoader(OrderRowController.class.getResource("OrderRow.fxml"));
		HBox orderRow = (HBox) loader.load();
		// ((NotificationRowController) loader.getController()).setNotification(notif);
		return orderRow;
	}

}
