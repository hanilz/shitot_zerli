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

public class HistogramReportController implements Initializable {

	@FXML
	private Label branchLabel;

	@FXML
	private NumberAxis countValuesAxis;

	@FXML
	private BarChart<String, Integer> histogramChart;

	@FXML
	private CategoryAxis monthAxis;

	@FXML
	private Label quraterLabel;

	@FXML
	private Label reportTypeLabel;

	private static Map<String, Integer> totalDataPerBranch = new HashMap<>();

	private static Report selectedReport;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		monthAxis.setLabel("Months");
		quraterLabel.setText("Q"+selectedReport.getQuarter());
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
				countValuesAxis.setLabel("Number of incomes");
				break;
		}
	}

	private void initBarChart() {
		List<String> monthList = new ArrayList<String>(totalDataPerBranch.keySet());
		Collections.reverse(monthList);
		XYChart.Series<String, Integer> monthSeries = new XYChart.Series<>();
		monthSeries.setName("Q"+selectedReport.getQuarter()+" Data");
		for (String currentMonth : monthList) {
			monthSeries.getData().add(new XYChart.Data<String, Integer>(currentMonth, totalDataPerBranch.get(currentMonth)));
		}
		histogramChart.getData().add(monthSeries);
	}

	public static void setSelectedReport(Report selectedReport) {
		HistogramReportController.selectedReport = selectedReport;
	}

	public static void setTotalDataPerBranch(Map<String, Integer> totalDataPerBranch) {
		HistogramReportController.totalDataPerBranch = totalDataPerBranch;
	}

}
