package mangeUsers;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.ClientFormController;
import entities.ManageUsers;
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


/**ManageUsersController used as a controller for the manageUsers.fxml file
 * this class allows a manager to suspend and re-activate a customer account
 * @author Eitan
 *
 */
public class ManageUsersController implements Initializable {
	private static ObservableList<ManageUsers> users = FXCollections.observableArrayList();

    @FXML
    private Button backBtn;
    
    @FXML
    private ImageView homeImage;

	@FXML
	private TableColumn<ManageUsers, String> firstName;

	@FXML
	private TableColumn<ManageUsers, String> lastName;

	@FXML
	private TableColumn<ManageUsers, String> id;

	@FXML
	private TableColumn<ManageUsers, String> userType;


	@FXML
	private TableColumn<ManageUsers, String> status;

	@FXML
	private TableView<ManageUsers> userTable;
	

    /**Returns to the home screen when the home image is pressed
     * @param event
     */
    @FXML
    void changeScreenToMain(ActionEvent event) {
    	ManageScreens.home();
    }
	
    /**Returns to the home screen when the back button is pressed
     * @param event
     */
    @FXML
    void returnHome(MouseEvent event) {
    	ManageScreens.home();
    }

	/**
	 *used to initialize the table for the manager to be able to suspend and re-activate customers
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//define table columns
		firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		userType.setCellValueFactory(new PropertyValueFactory<>("userType"));
		status.setCellValueFactory(new PropertyValueFactory<>("status"));
		
		System.out.println("requesting users");
		//create a request to fetch the table data
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.FETCH_ALL_USERS_DETAILS);
		Object response = ClientFormController.client.accept(message);
		users = (ObservableList<ManageUsers>) response;
		System.out.println("got users");
		userTable.setItems(users);//set the information in the table
		addButtonToTable();//call method to add buttons to the final column

	}


	/**
	 * method to add a button to the last column in the table for each row
	 */
	private void addButtonToTable() {
		TableColumn<ManageUsers, Void> colBtn = new TableColumn<>("Action");

		Callback<TableColumn<ManageUsers, Void>, TableCell<ManageUsers, Void>> cellFactory = new Callback<TableColumn<ManageUsers, Void>, TableCell<ManageUsers, Void>>() {
			@Override
			public TableCell<ManageUsers, Void> call(final TableColumn<ManageUsers, Void> param) {
				final TableCell<ManageUsers, Void> cell = new TableCell<ManageUsers, Void>() {

					private Button btn = new Button("Change Status");
					{
						btn.setOnAction((ActionEvent event) -> {
							ManageUsers data = getTableView().getItems().get(getIndex());
							HashMap<String, Object> message = new HashMap<>();
							message.put("command", Commands.CHANGE_USER_STATUS);
							message.put("id", data.getIdUser());
							Object response = ClientFormController.client.accept(message);
							
							if (response.equals("Suspended")&&data.getStatus().equals("Active")) {
								users.get(users.indexOf(data)).setStatus("Suspended");
							} else {
								if(response.equals("Active"))
									users.get(users.indexOf(data)).setStatus("Active");
							}
							userTable.refresh();
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
		userTable.getColumns().add(colBtn);
	}

}
