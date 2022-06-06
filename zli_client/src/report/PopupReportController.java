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
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This class displays the income or orders report after receiving the data from
 * the ReportController.
 */
public class PopupReportController implements Initializable {

	/**
	 * For display the branch of the reported that selected.
	 */
	@FXML
	private Label branchLabel;

	/**
	 * To init the item types to the itemTypeVBox.
	 */
	@FXML
	private VBox itemTypeVBox;

	/**
	 * The content of the report that selected.
	 */
	@FXML
	private VBox mainVBox;

	/**
	 * To init the product types to the productTypeVBox.
	 */
	@FXML
	private VBox productTypeVBox;

	/**
	 * To init the month of the report that selected.
	 */
	@FXML
	private Label quraterLabel;

	/**
	 * To init the lables of the refunds when the income report selected.
	 */
	@FXML
	private HBox refundHbox;

	@FXML
	private Separator refundSeperator;

	/**
	 * To init the total refunds of the income report.
	 */
	@FXML
	private Label refundsLabel;

	/**
	 * To init the title of the items label.
	 */
	@FXML
	private Label reportTypeItemsLabel;

	/**
	 * To init the report type that selected.
	 */
	@FXML
	private Label reportTypeLabel;

	/**
	 * To init the title of the products label.
	 */
	@FXML
	private Label reportTypeProductsLabel;

	/**
	 * To init the total income/orders of the customProducts.
	 */
	@FXML
	private Label totalCustomLabel;

	/**
	 * To init the title of the total income/sales based on the report type.
	 */
	@FXML
	private Label totalIncomeLabel;

	/**
	 * To init the total income/orders based on the branch.
	 */
	@FXML
	private Label totalIncomeQuantityLabel;

	/**
	 * To init the totalItems of the income/orders report (based on sales or the income).
	 */
	@FXML
	private Label totalItemsLabel;

	@FXML
	private VBox totalItemsVBox;

	/**
	 * To init the totalProducts of the income/orders report (based on sales or the income).
	 */
	@FXML
	private Label totalProductsLabel;

	@FXML
	private VBox totalProductsVBox;

	/**
	 * To init the total refunds title.
	 */
	@FXML
	private Label totalRefundsLabel;

	/**
	 * To init the title of the total custom products.
	 */
	@FXML
	private Label totalReportTpeCustomLabel;

	/**
	 * To init the title of the items based on the report that selected.
	 */
	@FXML
	private Label totalReportTypeItemsLabel;

	/**
	 * To init the title of the products based on the report that selected.
	 */
	@FXML
	private Label totalReportTypeProductsLabel;

	/**
	 * Saving the items information after receiving the info from the
	 * ReportController.
	 */
	private static Map<String, Integer> itemsLabels = new HashMap<>();

	/**
	 * Saving the product information after receiving the info from the
	 * ReportController.
	 */
	private static Map<String, Integer> productsLabels = new HashMap<>();

	/**
	 * Saving the report that selected.
	 */
	private static Report selectedReport;

	/**
	 * Saving the total income/orders of the customProducts.
	 */
	private static Integer totalCustom;

	/**
	 * Saving the total refunds for the income report.
	 */
	private static Integer totalRefunds;

	/**
	 * initialize the labels based on the report type that selected from the
	 * ReportController and it's initialize the content of the report.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		switch (selectedReport.getType()) {
		case "income":
			reportTypeLabel.setText("Income Report");
			reportTypeItemsLabel.setText("Income from items per item type:");
			reportTypeProductsLabel.setText("Income from products per product type:");
			totalReportTypeProductsLabel.setText("Total income from products:");
			totalReportTypeItemsLabel.setText("Total income from items:");
			totalReportTpeCustomLabel.setText("Total income from custom products:");
			totalIncomeLabel.setText("Net Income:");
			refundsLabel.setText(InputChecker.price(totalRefunds));
			break;
		case "orders":
			reportTypeLabel.setText("Orders Report");
			reportTypeItemsLabel.setText("Quantity of items per item type:");
			reportTypeProductsLabel.setText("Quantity of products per product type:");
			totalReportTypeProductsLabel.setText("Total quantity of products per order:");
			totalReportTypeItemsLabel.setText("Total quantity of items per order:");
			totalReportTpeCustomLabel.setText("Total quantity of custom products:");
			totalIncomeLabel.setText("Net Sales:");
			mainVBox.getChildren().remove(refundSeperator);
			mainVBox.getChildren().remove(refundHbox);
			break;
		}
		initProductsAndItemsTypesVBox();
	}

	/**
	 * initialize the itemVbox based on the item types in the database and
	 * initialize the productsVbox based on the product types in the database. It's
	 * initialize the total of the income/orders for each item types or product
	 * types. Init the total products/items/custom produts income/orders (based on
	 * the reportType) and setting the name of the branch and the current month.
	 */
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
			if (isIncome)
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
			if (isIncome)
				productSumHBox.getChildren().add(new Label(InputChecker.price(currentProductSum)));
			else
				productSumHBox.getChildren().add(new Label(currentProductSum + ""));
			totalProductsVBox.getChildren().add(productSumHBox);
			totalProducts += currentProductSum;
		}

		if (isIncome) {
			totalProductsLabel.setText(InputChecker.price(totalItems));
			totalItemsLabel.setText(InputChecker.price(totalProducts));
			totalCustomLabel.setText(InputChecker.price(totalCustom));
			totalIncomeQuantityLabel
					.setText(InputChecker.price(totalItems + totalProducts + totalCustom - totalRefunds));
		} else {
			totalProductsLabel.setText(totalItems + "");
			totalItemsLabel.setText(totalProducts + "");
			totalCustomLabel.setText(totalCustom + "");
			totalIncomeQuantityLabel.setText(totalItems + totalProducts + totalCustom + "");
		}
		branchLabel.setText(selectedReport.getBranch().toString());
		quraterLabel.setText(selectedReport.getMonth() + " " + selectedReport.getYear());

	}

	/**
	 * Setting the map of items (depeding on the reportType).
	 * 
	 * @param itemsLabels
	 */
	public static void setItemsLabels(Map<String, Integer> itemsLabels) {
		PopupReportController.itemsLabels = itemsLabels;
	}

	/**
	 * Setting the map of products (depeding on the reportType).
	 * @param productsLabels
	 */
	public static void setProductsLabels(Map<String, Integer> productsLabels) {
		PopupReportController.productsLabels = productsLabels;
	}

	/**
	 * Setting the selectedReport from the ReportController.
	 * @param selectedReport
	 */
	public static void setSelectedReport(Report selectedReport) {
		PopupReportController.selectedReport = selectedReport;
	}

	/**
	 * Setting the total custom products based on the report type.
	 * @param totalCustom
	 */
	public static void setTotalCustom(Integer totalCustom) {
		PopupReportController.totalCustom = totalCustom;
	}

	/**
	 * Setting the total refunds for the income report.
	 * @param totalRefunds
	 */
	public static void setTotalRefunds(Integer totalRefunds) {
		PopupReportController.totalRefunds = totalRefunds;
	}
}
