package report;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import entities.Report;
import inputs.InputChecker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PopupReportController implements Initializable {

    @FXML
    private Label branchLabel;

    @FXML
    private VBox itemTypeVBox;

    @FXML
    private HBox itemsHBox;

    @FXML
    private VBox productTypeVBox;

    @FXML
    private HBox productsHBox;

    @FXML
    private Label quraterLabel;

    @FXML
    private Label refundsLabel;

    @FXML
    private Label reportTypeItemsLabel;

    @FXML
    private Label reportTypeLabel;

    @FXML
    private Label reportTypeProductsLabel;

    @FXML
    private Label totalCustomLabel;

    @FXML
    private Label totalItemsLabel;

    @FXML
    private VBox totalItemsVBox;

    @FXML
    private Label totalProductsLabel;

    @FXML
    private VBox totalProductsVBox;

    @FXML
    private Label totalRefundsLabel;

    @FXML
    private Label totalReportTpeCustomLabel;

    @FXML
    private HBox totalReportTypeCustom;

    @FXML
    private Label totalReportTypeItemsLabel;

    @FXML
    private Label totalReportTypeProductsLabel;

	private static Map<String, Integer> itemsLabels = new HashMap<>();

	private static Map<String, Integer> productsLabels = new HashMap<>();

	private static Report selectedReport;
	
	private static Integer totalCustom;
	
	private static Integer totalRefunds;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		switch (selectedReport.getType()) {
		case "income":
			reportTypeLabel.setText("Income Report");
			reportTypeItemsLabel.setText("Income of items per item type:");
			reportTypeProductsLabel.setText("Income of products per product type:");
			totalReportTypeProductsLabel.setText("Total income of products:");
			totalReportTypeItemsLabel.setText("Total income of items:");
			totalReportTpeCustomLabel.setText("Total income of custom products:");
			refundsLabel.setText(InputChecker.price(totalRefunds));
			break;
		case "orders":
			reportTypeLabel.setText("Orders Report");
			reportTypeItemsLabel.setText("Quantity of items per item type in orders:");
			reportTypeProductsLabel.setText("Quantity of products per product type in orders:");
			totalReportTypeProductsLabel.setText("Total quantity of products per order:");
			totalReportTypeItemsLabel.setText("Total quantity of items per order:");
			totalReportTpeCustomLabel.setText("Total quantity of custom products:");
			totalRefundsLabel.setVisible(false);
			refundsLabel.setVisible(false);
			break;
		}
		
		initProductsAndItemsTypesVBox();
	}

	private void initProductsAndItemsTypesVBox() {
		boolean isIncome = selectedReport.getType().equals("income");
		int totalItems = 0;
		int totalProducts = 0;
		// init items type
		for (String currentItemType : itemsLabels.keySet()) {
			HBox itemTypeHBox = new HBox();
			itemTypeHBox.getChildren().add(new Label(currentItemType));
			itemTypeVBox.getChildren().add(itemTypeHBox);
		}
		// init total sum of items
		for (Integer currentItemSum : itemsLabels.values()) {
			HBox itemSumHBox = new HBox();
			if(isIncome)
				itemSumHBox.getChildren().add(new Label(InputChecker.price(currentItemSum)));
			else
				itemSumHBox.getChildren().add(new Label(currentItemSum + ""));
			totalItemsVBox.getChildren().add(itemSumHBox);
			totalItems += currentItemSum;
		}

		// init products type
		for (String currentProductType : productsLabels.keySet()) {
			HBox productTypeHBox = new HBox();
			productTypeHBox.getChildren().add(new Label(currentProductType));
			productTypeVBox.getChildren().add(productTypeHBox);
		}
		// init total sum of products
		for (Integer currentProductSum : productsLabels.values()) {
			HBox productSumHBox = new HBox();
			if(isIncome)
				productSumHBox.getChildren().add(new Label(InputChecker.price(currentProductSum)));
			else
				productSumHBox.getChildren().add(new Label(currentProductSum + ""));
			totalProductsVBox.getChildren().add(productSumHBox);
			totalProducts += currentProductSum;
		}

		if(isIncome) {
			totalProductsLabel.setText(InputChecker.price(totalItems));
			totalItemsLabel.setText(InputChecker.price(totalProducts));
			totalCustomLabel.setText(InputChecker.price(totalCustom));
		}
		else {
			totalProductsLabel.setText(totalItems + "");
			totalItemsLabel.setText(totalProducts + "");
			totalCustomLabel.setText(totalCustom+"");
		}
		branchLabel.setText(selectedReport.getBranch().toString());
		quraterLabel.setText("Q" + selectedReport.getQuarter() + "");
		
	}

	public static void setItemsLabels(Map<String, Integer> itemsLabels) {
		PopupReportController.itemsLabels = itemsLabels;
	}

	public static void setProductsLabels(Map<String, Integer> productsLabels) {
		PopupReportController.productsLabels = productsLabels;
	}

	public static void setSelectedReport(Report selectedReport) {
		PopupReportController.selectedReport = selectedReport;
	}

	public static void setTotalCustom(Integer totalCustom) {
		PopupReportController.totalCustom = totalCustom;
	}

	public static Integer getTotalRefunds() {
		return totalRefunds;
	}

	public static void setTotalRefunds(Integer totalRefunds) {
		PopupReportController.totalRefunds = totalRefunds;
	}
}
