package report;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import entities.Report;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class IncomeReportController implements Initializable {

    @FXML
    private Label branchLabel;

    @FXML
    private VBox itemType;

    @FXML
    private HBox itemsHBox;

    @FXML
    private VBox productType;

    @FXML
    private HBox productsHBox;

    @FXML
    private Label quraterLabel;

    @FXML
    private VBox totaItemSum;

    @FXML
    private Label totalIncomeItemsLbl;

    @FXML
    private Label totalIncomeProductsLbl;

    @FXML
    private VBox totalProductSum;
    
    private static Map<String,Integer> itemsLabels = new HashMap<>();
    
    private static Map<String,Integer> productsLabels = new HashMap<>();
    
    private static Report selectedReport;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		int totalItems = 0;
		int totalProducts = 0;
		System.out.println(itemsLabels.size());
		//init items type
		for(String currentItemType : itemsLabels.keySet()) {
			HBox itemTypeHBox = new HBox();
			itemTypeHBox.getChildren().add(new Label(currentItemType));
			itemType.getChildren().add(itemTypeHBox);
		}
		//init total sum of items
		for(Integer currentItemSum : itemsLabels.values()) {
			HBox itemSumHBox = new HBox();
			itemSumHBox.getChildren().add(new Label(currentItemSum + ""));
			totaItemSum.getChildren().add(itemSumHBox);
			totalItems += currentItemSum;
		}
		
		//init products type
		for(String currentProductType : productsLabels.keySet()) {
			HBox productTypeHBox = new HBox();
			productTypeHBox.getChildren().add(new Label(currentProductType));
			productType.getChildren().add(productTypeHBox);
		}
		//init total sum of products
		for(Integer currentProductSum : productsLabels.values()) {
			HBox productSumHBox = new HBox();
			productSumHBox.getChildren().add(new Label(currentProductSum + ""));
			totalProductSum.getChildren().add(productSumHBox);
			totalProducts += currentProductSum;
		}
		
		totalIncomeItemsLbl.setText(totalItems + "");
		totalIncomeProductsLbl.setText(totalProducts + "");
		branchLabel.setText(selectedReport.getBranch().toString());
		quraterLabel.setText("Q"+selectedReport.getQuarter() + "");
	}

	public static void setItemsLabels(Map<String, Integer> itemsLabels) {
		IncomeReportController.itemsLabels = itemsLabels;
	}

	public static void setProductsLabels(Map<String, Integer> productsLabels) {
		IncomeReportController.productsLabels = productsLabels;
	}

	public static void setSelectedReport(Report selectedReport) {
		IncomeReportController.selectedReport = selectedReport;
	}
}