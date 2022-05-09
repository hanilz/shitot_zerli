package userScreens;

import java.net.URL;
import java.util.ArrayList;
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

public class ManageUsersController implements Initializable{
	private static ObservableList<UserDetails> users = FXCollections.observableArrayList();
	private HashMap<Button,Button> buttons = new HashMap<>(); 
	
//    @FXML
//    private TableColumn<UserDetails, Button> action;

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
    
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		idAccount.setCellValueFactory(new PropertyValueFactory<>("idAccount"));
		firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		id.setCellValueFactory(new PropertyValueFactory<>("id"));
		email.setCellValueFactory(new PropertyValueFactory<>("email"));
		phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
		status.setCellValueFactory(new PropertyValueFactory<>("status"));
		//action.setCellValueFactory(new PropertyValueFactory<>(new Button("test")));
		
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", "fetch all user details");
		Object response = ClientFormController.client.accept(message);
		users = (ObservableList<UserDetails>) response;
		userTable.setItems(users);
		addButtonToTable();
		
	}
	
	
	private void addButtonToTable() {
        TableColumn<UserDetails, Void> colBtn = new TableColumn("Action");
        colBtn.setPrefWidth(90);

        Callback<TableColumn<UserDetails, Void>, TableCell<UserDetails, Void>> cellFactory = new Callback<TableColumn<UserDetails, Void>, TableCell<UserDetails, Void>>() {
            @Override
            public TableCell<UserDetails, Void> call( TableColumn<UserDetails, Void> param) {
                 TableCell<UserDetails, Void> cell = new TableCell<UserDetails, Void>() {

                    private Button btn = new Button("Suspend");
                    {
                    	buttons.put(btn,btn);
                        btn.setOnAction((ActionEvent event) -> {
                        	UserDetails data = getTableView().getItems().get(getIndex());
                        	HashMap<String, Object> message = new HashMap<>();
                        	message.put("command", "change user status");
                        	message.put("id", data.getIdAccount());
                        	Object response = ClientFormController.client.accept(message);
                            
                        	if(data.getStatus().equals("Active")) {
                        		users.get(users.indexOf(data)).setStatus("Suspended");
                        		//userTable.refresh();
                        		buttons.get(btn).setText("Activate");                        		
                        	}
                        	else {
                        		users.get(users.indexOf(data)).setStatus("Active");
                        		//userTable.refresh();
                        		buttons.get(btn).setText("Suspend");
                        	}
                        	setGraphic(btn);
                        	userTable.refresh();
                        	
//                        	if(data.getStatus().equals("Active")) {
//                        		btn.setText("Suspend");
//                        	}
//                        	else {
//                        		btn.setText("Activate");                        		
//                        	}

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
