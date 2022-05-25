package report;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.ClientFormController;
import entities.Branch;
import entities.Report;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import util.Commands;
import util.ManageScreens;
import util.UserType;

public class ReportsController implements Initializable {

	@FXML
	private ComboBox<Branch> branchComboBox;

	@FXML
	private Label noReportsLabel;

	@FXML
	private ComboBox<Integer> quraterComboBox;

	@FXML
	private TableView<Report> reportsTable;

	@FXML
	private TableColumn<Report, String> typeCol;

	@FXML
	private TableColumn<Report, String> dateCol;

	@FXML
	private Button searchButton;

	@FXML
	private ComboBox<String> yearComboBox;

	@FXML
	private Button homeButton;

	private ObservableList<Branch> branches;

	private ObservableList<Report> reports;

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Object response = checkUserType();
		branches = (ObservableList<Branch>) response;

		setBranchesComboBox();
		getReportsFromDB();
		setYearComboBox();
		setQuraterComboBox();
		addButtonToTableAndEvent();

		typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
		dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
	}

	private void setBranchesComboBox() {
		for (Branch branch : branches)
			branchComboBox.getItems().add(branch);
	}

	private Object checkUserType() {
		HashMap<String, Object> message = new HashMap<>();
		if (User.getUserInstance().getType() == UserType.CEO) {
			message.put("command", Commands.FETCH_BRANCHES);
		} else if (User.getUserInstance().getType() == UserType.STORE_MANGER) {
			message.put("command", Commands.FETCH_BRANCHES_PER_MANAGER);
			message.put("manager id", User.getUserInstance().getIdUser());
		}
		return ClientFormController.client.accept(message);
	}

	@SuppressWarnings("unchecked")
	private void getReportsFromDB() {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.FETCH_REPORTS);
		reports = (ObservableList<Report>) ClientFormController.client.accept(message);
	}

	private void setYearComboBox() {
		for (Report currentReport : reports)
			if (!yearComboBox.getItems().contains(currentReport.getYear()))
				yearComboBox.getItems().add(currentReport.getYear());
	}

	private void setQuraterComboBox() {
		quraterComboBox.getItems().addAll(1, 2, 3, 4);
	}

	@FXML
	void changeToHomeScreen(MouseEvent event) {
		ManageScreens.home();
	}

	@FXML
	void searchReportsButton(MouseEvent event) {
		ObservableList<Report> reportsFound = FXCollections.observableArrayList();
		for (Report currentReport : reports) {
			Report checkReport = new Report(branchComboBox.getValue().getIdBranch(), yearComboBox.getValue(),
					quraterComboBox.getValue());
			if (currentReport.equals(checkReport))
				reportsFound.add(currentReport);
		}
		if (reportsFound.size() != 0) {
			reportsTable.setItems(reportsFound);
			noReportsLabel.setVisible(false);
		} else {
			reportsTable.getItems().clear();
			noReportsLabel.setVisible(true);
		}
	}

	private void getIncomeReport() {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.GET_INCOME_REPORT);
		reports = (ObservableList<Report>) ClientFormController.client.accept(message);
		/*
		 * SELECT itemType, SUM(quantity*itemPrice) as totalSum FROM items JOIN
		 * order_items ON items.itemId=order_items.idItem JOIN orders ON orders.idOrder
		 * = order_items.idOrder AND orders.idBranch = 1 GROUP BY itemType;
		 */
		/*
		 * SELECT productType, SUM(quantity*productPrice) as totalSum FROM products JOIN
		 * order_products ON products.productId=order_products.idProduct JOIN orders ON
		 * orders.idOrder = order_products.idOrder AND orders.idBranch = 1 GROUP BY
		 * productType;
		 */
	}

	private void getOrdersReport() {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.GET_ORDERS_REPORT);
		reports = (ObservableList<Report>) ClientFormController.client.accept(message);
		/*
		 * SELECT itemType, SUM(quantity) as totalQuantity FROM items JOIN order_items
		 * ON items.itemId=order_items.idItem JOIN orders ON orders.idOrder =
		 * order_items.idOrder AND orders.idBranch = 1 GROUP BY itemType;
		 */
		/*
		 * SELECT productType, SUM(quantity) as totalSum FROM products JOIN
		 * order_products ON products.productId=order_products.idProduct JOIN orders ON
		 * orders.idOrder = order_products.idOrder AND orders.idBranch = 1 GROUP BY
		 * productType;
		 */

	}

	private void openSelectedReport() {

	}

	private void addButtonToTableAndEvent() {
		TableColumn<Report, Void> viewCol = new TableColumn<>("View");
		Callback<TableColumn<Report, Void>, TableCell<Report, Void>> cellFactory = new Callback<TableColumn<Report, Void>, TableCell<Report, Void>>() {
			@Override
			public TableCell<Report, Void> call(final TableColumn<Report, Void> param) {
				final TableCell<Report, Void> cell = new TableCell<Report, Void>() {
					private final Button btn = new Button("View Report");
					{
						btn.setOnAction((ActionEvent event) -> {
							int index = getTableRow().getIndex();
							Report selectedReport = getTableView().getItems().get(index);
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
		viewCol.setCellFactory(cellFactory);
		reportsTable.getColumns().add(viewCol);

	}
	
	private enum ReportType {
		INCOME_REPORT,ORDERS_REPORT;
	}

}
