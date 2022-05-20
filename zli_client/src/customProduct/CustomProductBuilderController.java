package customProduct;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import util.ManageData;
import util.ManageScreens;
import util.Screens;

public class CustomProductBuilderController implements Initializable {

	@FXML
	private Button addToCartButton;

	@FXML
	private Button backToCatalogButton;

	@FXML
	private CheckBox carnationFilterCheckBox;

	@FXML
	private Label catalogLbl;

	@FXML
	private CheckBox chocolateFilterCheckBox;

	@FXML
	private ScrollPane customProductOverviewScrollPane;

	@FXML
	private ScrollPane customProductScrollPane;

	@FXML
	private CheckBox daffodilFilterCheckBox;

	@FXML
	private CheckBox flowerFilterCheckBox;

	@FXML
	private ImageView homeImage;

	@FXML
	private CheckBox lilyFilterCheckBox;

	@FXML
	private CheckBox orchidFilterCheckBox;

	@FXML
	private CheckBox peonyFilterCheckBox;

	@FXML
	private CheckBox pinklFilterCheckBox;

	@FXML
	private CheckBox redFilterCheckBox;

	@FXML
	private CheckBox roseFilterCheckBox;

	@FXML
	private Label totalPriceLabel;

	@FXML
	private CheckBox whiteFilterCheckBox;

	@FXML
	private CheckBox yellowFilterCheckBox;

	private static CustomProductBuilderController customControllerInstace;

	private VBox overViewVBox = new VBox();

	@FXML
	void addToCart(MouseEvent event) {

	}

	@FXML
	void changeToCatalog(MouseEvent event) {
		ManageScreens.changeScreenTo(Screens.CATALOG);
	}

	@FXML
	void changeToHomeScreen(MouseEvent event) {
		ManageScreens.home();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setCheckBoxToFalse();
		customProductScrollPane.setContent(ManageData.customSelectorVBox);
		customControllerInstace = this;
	}

	public static void updateOverViewVBox(String command, SelectedHBox overViewSelected) {
		customControllerInstace.updateOverviewScrollPane(command, overViewSelected);
	}

	public void updateOverviewScrollPane(String command, SelectedHBox overViewSelected) {
		if (command.equals("add to overview")) {
			addProductIntoOverViewVBox(overViewSelected);
			customProductOverviewScrollPane.setContent(overViewVBox);
		} else {
			overViewVBox.getChildren().clear();
			removeFromOverView(overViewSelected);
			checkSelectedInSelector();
			customProductOverviewScrollPane.setContent(overViewVBox);
		}
	}

	private void addProductIntoOverViewVBox(SelectedHBox overViewSelected) {
		if (!overViewVBox.getChildren().contains(overViewSelected) && overViewSelected != null) {
			overViewVBox.getChildren().add(overViewSelected);
			updateTotalPrice();
		}
	}

	private void checkSelectedInSelector() {
		for (Node current : ManageData.customSelectorVBox.getChildren()) {
			if (current instanceof SelectorHBox) {
				SelectorHBox currentSelector = (SelectorHBox) current;
				if (currentSelector.getSelected().isSelected())
					overViewVBox.getChildren().add(currentSelector.getSelectedHBox());
			}
		}
		updateTotalPrice();
	}

	private void removeFromOverView(SelectedHBox overViewSelected) {
		for (Node current : ManageData.customSelectorVBox.getChildren()) {
			if (current instanceof SelectorHBox) {
				SelectorHBox currentSelector = (SelectorHBox) current;
				if (overViewSelected != null && currentSelector.getSelectedHBox() != null) {
					if (currentSelector.getSelectedHBox().equals(overViewSelected)
							&& currentSelector.getSelected().isSelected()) {
						currentSelector.getSelected().setSelected(false);
						break;
					}
				}
			}
		}
	}

	private void setCheckBoxToFalse() {
		for (Node currentHBox : ManageData.customSelectorVBox.getChildren()) {
			if (currentHBox instanceof SelectorHBox) {
				SelectorHBox currentSelector = (SelectorHBox) currentHBox;
				currentSelector.getSelected().setSelected(false);
			}
		}
	}

	public static void updateTotalPriceLabel() {
		customControllerInstace.updateTotalPrice();
	}

	private void updateTotalPrice() {
		double totalPrice = 0;
		for (Node currentSelected : overViewVBox.getChildren()) {
			if (currentSelected instanceof SelectedHBox) {
				totalPrice += (((SelectedHBox) currentSelected).getQuantity())
						* (((SelectedHBox) currentSelected).getProduct().getPrice());
			}
		}

		totalPriceLabel.setText("" + totalPrice);
	}

}
