package report;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import entities.Report;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

/**
 * This class displays the complaints or income histogram report after receiving
 * the data from the ReportController.
 *
 */
public class HistogramReportController implements Initializable {

	/**
	 * To setting the name of the branch title in the screen.
	 */
	@FXML
	private Label branchLabel;
	
	@FXML
	private NumberAxis countValuesAxis;

	/**
	 * To init the histogramChart in the screen.
	 */
	@FXML
	private BarChart<String, Integer> histogramChart;

	/**
	 * To setting the month axis in the histogram.
	 */
	@FXML
	private CategoryAxis monthAxis;

	/**
	 * To setting the qurater title in the screen.
	 */
	@FXML
	private Label quraterLabel;

	/**
	 * To setting the report type title in the screen.
	 */
	@FXML
	private Label reportTypeLabel;

	/**
	 * Saving the data of the complaints (month and total complaints) or income.
	 */
	private static Map<String, Integer> totalDataPerBranch = new HashMap<>();

	/**
	 * Saving the report that selected.
	 */
	private static Report selectedReport;

	/**
	 * Initialize the title labels of the histogram report that selected and init
	 * the bar chart in the histogram based on the report type.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		monthAxis.setLabel("Months");
		quraterLabel.setText("Q" + selectedReport.getQuarter() + " " + selectedReport.getYear());
		branchLabel.setText(selectedReport.getBranch().toString());
		reportTypeLabel.setText(selectedReport.getType());

		initBarChart();
		switch (selectedReport.getType()) {
		case "complaints":
			reportTypeLabel.setText("Complaints Report");
			countValuesAxis.setLabel("Number of complaints");
			break;
		case "income histogram":
			reportTypeLabel.setText("Income Histogram Report");
			countValuesAxis.setLabel("Total Income");
			break;
		}
	}

	/**
	 * init the bar in the histogram chart to present the total complaints/income
	 * for each month in the qurater.
	 */
	private void initBarChart() {
		List<String> monthList = new ArrayList<String>(totalDataPerBranch.keySet());
		Collections.reverse(monthList);
		XYChart.Series<String, Integer> monthSeries = new XYChart.Series<>();
		monthSeries.setName("Q" + selectedReport.getQuarter() + " Data");
		for (String currentMonth : monthList) {
			monthSeries.getData()
					.add(new XYChart.Data<String, Integer>(currentMonth, totalDataPerBranch.get(currentMonth)));
		}
		histogramChart.getData().add(monthSeries);
	}

	/**
	 * Setting the selectedReport from the ReportController.
	 * 
	 * @param selectedReport
	 */
	public static void setSelectedReport(Report selectedReport) {
		HistogramReportController.selectedReport = selectedReport;
	}

	/**
	 * Setting the map for saving the month and the total complaints/incomes from
	 * ReportController.
	 * 
	 * @param totalDataPerBranch
	 */
	public static void setTotalDataPerBranch(Map<String, Integer> totalDataPerBranch) {
		HistogramReportController.totalDataPerBranch = totalDataPerBranch;
	}

}
