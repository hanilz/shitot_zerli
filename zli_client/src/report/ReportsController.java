package report;

import java.net.URL;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
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

/**
 * This class controllers the functionality of the view reports. It's saving all
 * the reports of the branches based on the user type (CEO or store manager).
 */
public class ReportsController implements Initializable {

	/**
	 * comboBox for selecting the branches.
	 */
	@FXML
	private ComboBox<Branch> branchComboBox;

	/**
	 * error label if no report found.
	 */
	@FXML
	private Label noReportsLabel;

	/**
	 * comBox for selecting a qurater.
	 */
	@FXML
	private ComboBox<Integer> quraterComboBox;

	/**
	 * tableView of the reports that selected after selecting branch, year and
	 * qurater.
	 */
	@FXML
	private TableView<Report> reportsTable;

	/**
	 * reportType col of the tableview.
	 */
	@FXML
	private TableColumn<Report, String> typeCol;

	/**
	 * date col of the tableview.
	 */
	@FXML
	private TableColumn<Report, String> dateCol;

	/**
	 * homeButton near the title of the screen.
	 */
	@FXML
	private ImageView homeImage;

	/**
	 * comboBox for selecting an year.
	 */
	@FXML
	private ComboBox<String> yearComboBox;

	/**
	 * homeButton to go back to the home screen.
	 */
	@FXML
	private Button homeButton;

	/**
	 * To present the branches in the comboBox.
	 */
	private ObservableList<Branch> branches;

	/**
	 * To present the reports in the comboBox.
	 */
	private ObservableList<Report> reports;

	/**
	 * Recieving the data from the server to initialize the branches comboBox, year
	 * and reports after selecting data in the comboBox. And initialize the col in
	 * the tableview.
	 */
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

	/**
	 * Setting the branches into comboBox after getting the data from the server.
	 */
	private void setBranchesComboBox() {
		for (Branch branch : branches)
			branchComboBox.getItems().add(branch);
	}

	/**
	 * Checking the userType and initialize the reports based on the type. If the
	 * user is store manager: it will display only the reports of the stores that he
	 * manage. If the user is CEO: it will display all the reports per branch.
	 * 
	 * @return message from the zli server after executing the querty.
	 */
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

	/**
	 * Getting the reports from the database to init the reports into the TableView
	 * after selecting branch, year and qurater.
	 */
	@SuppressWarnings("unchecked")
	private void getReportsFromDB() {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.FETCH_REPORTS);
		reports = (ObservableList<Report>) ClientFormController.client.accept(message);
	}

	/**
	 * Setting the year comboBox based on the information in the database.
	 */
	private void setYearComboBox() {
		for (Report currentReport : reports)
			if (!yearComboBox.getItems().contains(currentReport.getYear()))
				yearComboBox.getItems().add(currentReport.getYear());
	}

	/**
	 * Setting the quraterComboBoox.
	 */
	private void setQuraterComboBox() {
		quraterComboBox.getItems().addAll(1, 2, 3, 4);
	}

	/**
	 * Returns to home when the user clicked on the button.
	 * @param MouseEvent
	 */
	@FXML
	void returnHome(MouseEvent event) {
		ManageScreens.home();
	}

	/**
	 * After selecting branch, qurater and year, it will search the reports based on the selection.
	 * If reports are found, it will display on the tableView, else, the label "No Reports Found" will appear.
	 * @param MouseEvent
	 */
	@FXML
	void searchReportsButton(MouseEvent event) {
		if (!isInputValid())
			return;
		ObservableList<Report> reportsFound = FXCollections.observableArrayList();
		for (Report currentReport : reports) {
			Report checkReport = new Report(branchComboBox.getValue().getIdBranch(), yearComboBox.getValue(),
					quraterComboBox.getValue());
			if (currentReport.equals(checkReport) && !currentReport.getType().equals("income histogram"))
				reportsFound.add(currentReport);
			else if (currentReport.equals(checkReport) && currentReport.getType().equals("income histogram")
					&& User.getUserInstance().getType() == UserType.CEO) // if the user is ceo, we will add the income
																			// histogram report
				reportsFound.add(currentReport);
		}
		if (reportsFound.size() != 0) {
			reportsTable.setItems(reportsFound);
			noReportsLabel.setVisible(false);
			reportsTable.refresh();
		} else {
			reportsTable.getItems().clear();
			noReportsLabel.setText("No Reports Found");
			noReportsLabel.setVisible(true);
		}
	}

	/**
	 * Checking if the user selected all the comboBox so the controller will display the reports on the tableView.
	 * @return true or false, based on the user input.
	 */
	private boolean isInputValid() {
		if (branchComboBox.getValue() == null || yearComboBox.getValue() == null
				|| quraterComboBox.getValue() == null) {
			noReportsLabel.setText("Please check your selection.");
			noReportsLabel.setVisible(true);
			return false;
		} else {
			noReportsLabel.setVisible(false);
			return true;
		}
	}

	/**
	 * Receiving the income information for the income report using SQL queries (using aggression functions). 
	 * Setting the information after receiving the data from the server to the income report screen.
	 * @param selectedReport
	 */
	@SuppressWarnings("unchecked")
	private void getIncomeReport(Report selectedReport) {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.GET_ITEMS_INCOME_REPORT);
		message.put("selected report", new Report(selectedReport.getDateRange(), selectedReport.getType(),
				selectedReport.getIdBranch(), selectedReport.getDate()));
		Map<String, Integer> response = (Map<String, Integer>) ClientFormController.client.accept(message);
		PopupReportController.setItemsLabels(response);

		message.put("command", Commands.GET_PRODUCTS_INCOME_REPORT);
		message.put("selected report", new Report(selectedReport.getDateRange(), selectedReport.getType(),
				selectedReport.getIdBranch(), selectedReport.getDate()));
		response = (Map<String, Integer>) ClientFormController.client.accept(message);
		PopupReportController.setProductsLabels(response);

		message.put("command", Commands.GET_CUSTOM_INCOME_REPORT);
		message.put("selected report", new Report(selectedReport.getDateRange(), selectedReport.getType(),
				selectedReport.getIdBranch(), selectedReport.getDate()));
		Integer responseCustom = (Integer) ClientFormController.client.accept(message);
		PopupReportController.setTotalCustom(responseCustom);

		message.put("command", Commands.GET_TOTAL_REFUNDS);
		message.put("selected report", new Report(selectedReport.getDateRange(), selectedReport.getType(),
				selectedReport.getIdBranch(), selectedReport.getDate()));
		Integer responseRefunds = (Integer) ClientFormController.client.accept(message);
		PopupReportController.setTotalRefunds(responseRefunds);

		PopupReportController.setSelectedReport(selectedReport);
	}

	/**
	 * Receiving the orders information for the orders report using SQL queries (using aggression functions). 
	 * Setting the information after receiving the data from the server to the orders report screen.
	 * @param selectedReport
	 */
	@SuppressWarnings("unchecked")
	private void getOrdersReport(Report selectedReport) {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.GET_ITEMS_ORDERS_REPORT);
		message.put("selected report", new Report(selectedReport.getDateRange(), selectedReport.getType(),
				selectedReport.getIdBranch(), selectedReport.getDate()));
		Map<String, Integer> response = (Map<String, Integer>) ClientFormController.client.accept(message);
		PopupReportController.setItemsLabels(response);

		message.put("command", Commands.GET_PRODUCTS_ORDERS_REPORT);
		message.put("selected report", new Report(selectedReport.getDateRange(), selectedReport.getType(),
				selectedReport.getIdBranch(), selectedReport.getDate()));
		response = (Map<String, Integer>) ClientFormController.client.accept(message);
		PopupReportController.setProductsLabels(response);

		message.put("command", Commands.GET_CUSTOM_ORDERS_REPORT);
		message.put("selected report", new Report(selectedReport.getDateRange(), selectedReport.getType(),
				selectedReport.getIdBranch(), selectedReport.getDate()));
		Integer responseCustom = (Integer) ClientFormController.client.accept(message);
		PopupReportController.setTotalCustom(responseCustom);

		PopupReportController.setSelectedReport(selectedReport);
	}

	/**
	 * Init the Report Screen based on the report that selected.
	 * This method checks for each reports types which data to receive and which screen to display.
	 * @param selectedReport
	 */
	private void openSelectedReport(Report selectedReport) {
		switch (selectedReport.getType()) {
		case "income":
			getIncomeReport(selectedReport);
			try {
				ManageScreens.openPopupUnlimitedFXML(PopupReportController.class.getResource("PopupReport.fxml"),
						"Income Report");
			} catch (Exception e) {
			}
			break;
		case "orders":
			getOrdersReport(selectedReport);
			try {
				ManageScreens.openPopupUnlimitedFXML(PopupReportController.class.getResource("PopupReport.fxml"),
						"Orders Report");
			} catch (Exception e) {
			}
			break;
		case "complaints":
			getComplaintsReportPerBranch(selectedReport);
			try {
				ManageScreens.openPopupUnlimitedFXML(
						HistogramReportController.class.getResource("HistogramReport.fxml"), "Complaints Report");
			} catch (Exception e) {
			}
			break;
		case "income histogram":
			getIncomeHistogramReportPerBranch(selectedReport);
			try {
				ManageScreens.openPopupUnlimitedFXML(
						HistogramReportController.class.getResource("HistogramReport.fxml"), "Income Histogram Report");
			} catch (Exception e) {
			}
			break;
		}
	}

	/**
	 * Receiving the income information for the income histogram report using SQL queries (using aggression functions). 
	 * Setting the information after receiving the data from the server to the income histogram report screen.
	 * @param selectedReport
	 */
	@SuppressWarnings("unchecked")
	private void getIncomeHistogramReportPerBranch(Report selectedReport) {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.GET_INCOME_HISTOGRAM_REPORT);
		message.put("selected report",
				new Report(selectedReport.getDateRange(), selectedReport.getType(), selectedReport.getIdBranch()));
		Map<String, Integer> response = (Map<String, Integer>) ClientFormController.client.accept(message);
		HistogramReportController.setTotalDataPerBranch(response);
		HistogramReportController.setSelectedReport(selectedReport);
	}

	/**
	 * Receiving the complaints information for the complaints histogram report using SQL queries (using aggression functions). 
	 * Setting the information after receiving the data from the server to the complaints histogram report screen.
	 * @param selectedReport
	 */
	@SuppressWarnings("unchecked")
	private void getComplaintsReportPerBranch(Report selectedReport) {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.GET_COMPLAINT_REPORT);
		message.put("selected report",
				new Report(selectedReport.getDateRange(), selectedReport.getType(), selectedReport.getIdBranch()));
		Map<String, Integer> response = (Map<String, Integer>) ClientFormController.client.accept(message);
		HistogramReportController.setTotalDataPerBranch(response);
		HistogramReportController.setSelectedReport(selectedReport);
	}

	/**
	 * Init another col in the tableview to view the reports.
	 * If the user selected specific report, it will set the relevant data to present the report.
	 */
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
							if (selectedReport.getType().equals("income histogram")
									&& selectedReport.getType().equals("complaints"))
								selectedReport.setDateRange(setDateRangeQuarterForQuery(selectedReport));
							else {
								selectedReport.setDateRange(setDateRangeCurrentMonthForQuery(selectedReport));
								selectedReport.setMonth();
							}
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

	/**
	 * Setting the date range for the SQL query of the current month to display the report of the current month.
	 * @param selectedReport
	 * @return dateRange String based on the current month.
	 */
	private String setDateRangeCurrentMonthForQuery(Report selectedReport) {
		java.util.Date currentDate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		int month = cal.get(Calendar.MONTH);
		if (month >= 10 && month <= 12)
			return "'" + Year.now().getValue() + "-" + month + "-01 00:00:00' and '" + Year.now().getValue() + "-"
					+ month + "-" + Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH) + " 00:00:00'";
		return "'" + Year.now().getValue() + "-0" + month + "-01 00:00:00' and '" + Year.now().getValue() + "-0" + month
				+ "-" + Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH) + " 00:00:00'";
	}

	/**
	 * Setting the date range for the SQL query for each quarter.
	 * @param selectedReport
	 * @return dateRange String based on the quarter.
	 */
	private String setDateRangeQuarterForQuery(Report selectedReport) {
		StringBuilder sb = new StringBuilder(selectedReport.getDateToString());
		String dateRange = selectedReport.getDateToString().charAt(5) + "" + selectedReport.getDateToString().charAt(6);
		switch (selectedReport.getQuarter()) {
		case 1:
			sb.setCharAt(6, dateRange.charAt(1));
			sb.append(" 00:00:00");
			return "'" + Year.now().getValue() + "-01-01 00:00:00' and '" + sb.toString() + "'";
		case 2:
			sb.setCharAt(6, dateRange.charAt(1));
			sb.append(" 00:00:00");
			return "'" + Year.now().getValue() + "-04-01 00:00:00' and '" + sb.toString() + "'";
		case 3:
			sb.setCharAt(6, dateRange.charAt(1));
			sb.append(" 00:00:00");
			return "'" + Year.now().getValue() + "-07-01 00:00:00' and '" + sb.toString() + "'";
		case 4:
			sb.setCharAt(5, dateRange.charAt(0));
			sb.setCharAt(6, dateRange.charAt(1));
			return "'" + Year.now().getValue() + "-10-01 00:00:00' and '" + sb.toString() + "'";
		}
		return "";
	}

}
