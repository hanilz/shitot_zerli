package customerComplaint;


import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.ClientFormController;
import entities.Complaint;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import util.Commands;
import util.ManageScreens;
import util.Screens;

/**CustomerComplaintHomeController used as a controller class for the CustomerComplaintHomeScreen.fxml
 * @author Eitan
 *
 */
public class CustomerComplaintHomeController implements Initializable{
	private static ObservableList<Complaint> complaints = FXCollections.observableArrayList();

    @FXML
    private TableColumn<Complaint, Integer> complaintID;

    @FXML
    private TableColumn<Complaint, String> complaintReason;

    @FXML
    private TableColumn<Complaint, Integer> orderID;

    @FXML
    private TableColumn<Complaint, String> date;
    
    @FXML
    private TableView<Complaint> complaintsTable;

    @FXML
    private ImageView homeImage;

    @FXML
    private Button newCustomerComplaintButton;



    /**when the create button is pressed opens a new screen to fill a new complaint
     * @param event
     */
    @FXML
    void createNewCustomerComplaint(ActionEvent event) {
    	ManageScreens.changeScreenTo(Screens.COMPLAINT);
    }

    /**returns to the main home screen when the home image is pressed
     * @param event
     */
    @FXML
    void returnHome(MouseEvent event) {
    	ManageScreens.home();
    }

	/**
	 *gets the complaints from the DB and displays them in the table
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.FETCH_COMPLAINTS);
		message.put("HandlerID", User.getUserInstance().getIdUser());
		Object response = ClientFormController.client.accept(message);
		complaints = (ObservableList<Complaint>)response;
		complaintID.setCellValueFactory(new PropertyValueFactory<>("complaintID"));
		orderID.setCellValueFactory(new PropertyValueFactory<>("orderID"));
		complaintReason.setCellValueFactory(new PropertyValueFactory<>("complaintReason"));
		date.setCellValueFactory(new PropertyValueFactory<>("date"));
		complaintsTable.setItems(complaints);
		addButtonToTable();
	}
    
	
	
	/**
	 * adds a button to each row of the table, when the button is pressed the complaint opens in a new screen
	 */
	private void addButtonToTable() {
		TableColumn<Complaint, Void> colBtn = new TableColumn<>("Action");

		Callback<TableColumn<Complaint, Void>, TableCell<Complaint, Void>> cellFactory = new Callback<TableColumn<Complaint, Void>, TableCell<Complaint, Void>>() {
			@Override
			public TableCell<Complaint, Void> call(final TableColumn<Complaint, Void> param) {
				final TableCell<Complaint, Void> cell = new TableCell<Complaint, Void>() {

					private Button btn = new Button("View");
					{
						btn.setOnAction((ActionEvent event) -> {
							Complaint data = getTableView().getItems().get(getIndex());
							//System.out.println(data.getComplaintReason());
							ComplaintViewController.setComplaint(data);
							ManageScreens.changeScreenTo(Screens.COMPLAINT_VIEW);
							//complaintsTable.refresh();
						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(btn);
						}
					}
				};
				return cell;
			}
		};
		colBtn.setCellFactory(cellFactory);
		complaintsTable.getColumns().add(colBtn);
	}
}

	


