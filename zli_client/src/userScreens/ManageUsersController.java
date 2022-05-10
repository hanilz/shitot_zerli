package userScreens;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.ClientFormController;
import entities.UserDetails;
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
import javafx.util.Callback;
import util.ManageScreens;

public class ManageUsersController implements Initializable {
	private static ObservableList<UserDetails> users = FXCollections.observableArrayList();

    @FXML
    private Button backBtn;

	@FXML
	private TableColumn<UserDetails, String> email;

	@FXML
	private TableColumn<UserDetails, String> firstName;

	@FXML
	private TableColumn<UserDetails, String> id;

	@FXML
	private TableColumn<UserDetails, Integer> idAccount;

	@FXML
	private TableColumn<UserDetails, String> lastName;

	@FXML
	private TableColumn<UserDetails, String> phoneNumber;

	@FXML
	private TableColumn<UserDetails, String> status;

	@FXML
	private TableView<UserDetails> userTable;
	

    @FXML
    void changeScreenToMain(ActionEvent event) {
    	ManageScreens.home();
    }

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//define table columns
		idAccount.setCellValueFactory(new PropertyValueFactory<>("idAccount"));
		firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		email.setCellValueFactory(new PropertyValueFactory<>("email"));
		phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
		status.setCellValueFactory(new PropertyValueFactory<>("status"));
		
		//create a request to fetch the table data
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", "fetch all user details");
		Object response = ClientFormController.client.accept(message);
		users = (ObservableList<UserDetails>) response;
		userTable.setItems(users);//set the information in the table
		addButtonToTable();//call method to add buttons to the final column

	}

	//method to add a button to the last column in the table for each row
	private void addButtonToTable() {
		TableColumn<UserDetails, Void> colBtn = new TableColumn("Action");

		Callback<TableColumn<UserDetails, Void>, TableCell<UserDetails, Void>> cellFactory = new Callback<TableColumn<UserDetails, Void>, TableCell<UserDetails, Void>>() {
			@Override
			public TableCell<UserDetails, Void> call(final TableColumn<UserDetails, Void> param) {
				final TableCell<UserDetails, Void> cell = new TableCell<UserDetails, Void>() {

					private Button btn = new Button("Change Status");
					{
						btn.setOnAction((ActionEvent event) -> {
							UserDetails data = getTableView().getItems().get(getIndex());
							HashMap<String, Object> message = new HashMap<>();
							message.put("command", "change user status");
							message.put("id", data.getIdAccount());
							Object response = ClientFormController.client.accept(message);
							
							if (response.equals("Suspended")&&data.getStatus().equals("Active")) {
								users.get(users.indexOf(data)).setStatus("Suspended");
								// buttons.get(btn).setText("Activate");
							} else {
								if(response.equals("Active"))
									users.get(users.indexOf(data)).setStatus("Active");
								// buttons.get(btn).setText("Suspend");
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
