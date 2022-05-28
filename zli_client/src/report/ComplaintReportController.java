package report;

import java.net.URL;
import java.util.HashMap;
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

public class ComplaintReportController implements Initializable {

    @FXML
    private Label branchLabel;

    @FXML
    private BarChart<String, Integer> complaintsChart;

    @FXML
    private NumberAxis monthsAxis;

    @FXML
    private CategoryAxis numberOfComplaintsAxis;

    @FXML
    private Label quraterLabel;

    private static Map<String, Integer> totalComplaintsPerBranch = new HashMap<>();
    
    private static Report selectedReport;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		quraterLabel.setText(selectedReport.getQuarter()+"");
		branchLabel.setText(selectedReport.getBranch().toString());
		
		for(String currentMonth : totalComplaintsPerBranch.keySet()) {
			XYChart.Series<String,Integer> monthSeries = new XYChart.Series<>();
			monthSeries.setName(currentMonth);
			monthSeries.getData().add(new XYChart.Data<String,Integer>(currentMonth, totalComplaintsPerBranch.get(currentMonth)));
			complaintsChart.getData().add(monthSeries);
		}
	}

	public static void setSelectedReport(Report selectedReport) {
		ComplaintReportController.selectedReport = selectedReport;
	}

	public static void setTotalComplaintsPerBranch(Map<String, Integer> totalComplaintsPerBranch) {
		ComplaintReportController.totalComplaintsPerBranch = totalComplaintsPerBranch;
	}

}
