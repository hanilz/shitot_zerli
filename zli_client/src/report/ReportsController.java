package report;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
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
import javafx.scene.image.ImageView;
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
    private ImageView homeImage;

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
    void returnHome(MouseEvent event) {
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

	@SuppressWarnings("unchecked")
	private void getIncomeReport(Report selectedReport) {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.GET_ITEMS_INCOME_REPORT);
		message.put("selected report",
				new Report(selectedReport.getDateRange(), selectedReport.getType(), selectedReport.getIdBranch()));
		Map<String, Integer> response = (Map<String, Integer>) ClientFormController.client.accept(message);
		PopupReportController.setItemsLabels(response);

		message.put("command", Commands.GET_PRODUCTS_INCOME_REPORT);
		message.put("selected report",
				new Report(selectedReport.getDateRange(), selectedReport.getType(), selectedReport.getIdBranch()));
		response = (Map<String, Integer>) ClientFormController.client.accept(message);
		PopupReportController.setProductsLabels(response);
		
		PopupReportController.setSelectedReport(selectedReport);
	}

	@SuppressWarnings("unchecked")
	private void getOrdersReport(Report selectedReport) {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.GET_ITEMS_ORDERS_REPORT);
		message.put("selected report",
				new Report(selectedReport.getDateRange(), selectedReport.getType(), selectedReport.getIdBranch()));
		Map<String, Integer> response = (Map<String, Integer>) ClientFormController.client.accept(message);
		PopupReportController.setItemsLabels(response);
		
		message.put("command", Commands.GET_PRODUCTS_ORDERS_REPORT);
		message.put("selected report",
				new Report(selectedReport.getDateRange(), selectedReport.getType(), selectedReport.getIdBranch()));
		response = (Map<String, Integer>) ClientFormController.client.accept(message);
		PopupReportController.setProductsLabels(response);
		
		PopupReportController.setSelectedReport(selectedReport);
	}

	private void openSelectedReport(Report selectedReport) {
		switch (selectedReport.getType()) {
		case "income":
			getIncomeReport(selectedReport);
			try {
				ManageScreens.openPopupFXML(PopupReportController.class.getResource("PopupReport.fxml"),
						"Income Report");
			} catch (Exception e) {}
			break;
		case "orders":
			getOrdersReport(selectedReport);
			try {
				ManageScreens.openPopupFXML(PopupReportController.class.getResource("PopupReport.fxml"),
						"Orders Report");
			} catch (Exception e) {}
			break;
		}
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
							selectedReport.setBranch(branchComboBox.getValue());
							selectedReport.setDateRange(setDateRangeForQuery(selectedReport));
							openSelectedReport(selectedReport);
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

	private String setDateRangeForQuery(Report selectedReport) {
		StringBuilder sb = new StringBuilder(selectedReport.getDateToString());
		String dateRange = selectedReport.getDateToString().charAt(5) + ""
				+ selectedReport.getDateToString().charAt(6);
		//int monthToInt = Integer.parseInt(selectedMonth) - 1;
		//String dateRange = monthToInt + "";
		switch (selectedReport.getQuarter()) {
		case 1:
			sb.setCharAt(6, dateRange.charAt(1));
			sb.append(" 00:00:00");
			return "'2022-01-01 00:00:00' and '" + sb.toString()+"'";
		case 2:
			sb.setCharAt(6, dateRange.charAt(1));
			sb.append(" 00:00:00");
			return "'2022-04-01 00:00:00' and '" + sb.toString()+"'";
		case 3:
			sb.setCharAt(6, dateRange.charAt(1));
			sb.append(" 00:00:00");
			return "'2022-07-01 00:00:00' and '" + sb.toString()+"'";
		case 4:
			sb.setCharAt(5, dateRange.charAt(0));
			sb.setCharAt(6, dateRange.charAt(1));
			return "'2022-10-01 00:00:00' and '" + sb.toString()+"'";
		}
		return "";
	}

}
