package deliveryCoordination;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.ClientFormController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import util.Commands;
import util.ManageScreens;

public class DeliveryCoordinatorController implements Initializable {
	ArrayList<DeliveryCoordinatorView> orders;
	@FXML
	private ImageView homeImage;

	@FXML
	private VBox orderVBox;

	@FXML
	void returnHome(MouseEvent event) {
		ManageScreens.home();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			HashMap<String, Object> message = new HashMap<>();
			message.put("command", Commands.FETCH_ORDERS_DELIVERY_COORDINATOR);
			Object response = ClientFormController.client.accept(message);
			orders = (ArrayList<DeliveryCoordinatorView>)response;
			for(DeliveryCoordinatorView order :orders)
				orderVBox.getChildren().add(loadRow(order));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private HBox loadRow(DeliveryCoordinatorView deliveryCoordinatorView) throws IOException {
		FXMLLoader loader = new FXMLLoader(OrderRowController.class.getResource("OrderRow.fxml"));
		HBox orderRow = (HBox) loader.load();
		((OrderRowController) loader.getController()).setDeliveryController(this);
		((OrderRowController) loader.getController()).setOrder(deliveryCoordinatorView);
		return orderRow;
	}
	
	public void removeRow(DeliveryCoordinatorView deliveryCoordinatorView, HBox row) {
		orders.remove(orders.indexOf(deliveryCoordinatorView));
		orderVBox.getChildren().remove(orderVBox.getChildren().indexOf(row));
	}

}
