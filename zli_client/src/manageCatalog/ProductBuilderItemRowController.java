package manageCatalog;

import java.net.URL;
import java.util.ResourceBundle;

import entities.Item;
import inputs.InputChecker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * This class init the row in the adding new product to select items for the new
 * product in the manageCatalog screen.
 */
public class ProductBuilderItemRowController implements Initializable {
	private Item item;
	private newProductBuilderController newProductBuilderController;
	private int quantity;

	@FXML
	private CheckBox includeCheckBox;

	@FXML
	private Label itemIDLabel;

	@FXML
	private Label itemNameLabel;

	@FXML
	private ImageView itemImage;

	@FXML
	private TextField qtytextField;

	@FXML
	private HBox rowHBox;

	/**
	 * This method setting for each item in the row, the details of the items that
	 * we have in the catalog.
	 * 
	 * @param item
	 */
	public void setItem(Item item) {
		this.item = item;
		itemIDLabel.setText(item.getId() + "");
		itemNameLabel.setText(item.getName());
		itemImage.setImage(new Image(item.getImagePath()));
	}

	/**
	 * This method adding the item to the new product after the employee selected
	 * the item.
	 * 
	 * @param event
	 */
	@FXML
	void addItemToProduct(ActionEvent event) {
		if (!includeCheckBox.isSelected()) {
			rowHBox.setStyle("-fx-background-color :  ECE4DB ; -fx-background-radius : 40");
			newProductBuilderController.removeFromProduct(item);
			qtytextField.setDisable(true);
			return;
		}
		// box is selected
		qtytextField.setDisable(false);
		rowHBox.setStyle("-fx-background-color :  D8E2DC ; -fx-background-radius : 40");
		if (qtytextField.getText().equals("0")) {
			qtytextField.setText("1");
			quantity = 1;
		}
		newProductBuilderController.addItemToProduct(item, quantity);
	}

	/**
	 * This method checking if item selected to a new product.
	 * 
	 * @return true if the item is selected
	 */
	public boolean isSelected() {
		return includeCheckBox.isSelected();
	}

	/**
	 * This method gets the quantity that inserted by the employee.
	 * @return the desired quantity for the product.
	 */
	public int getQuantity() {
		return Integer.parseInt(qtytextField.getText());
	}

	public void setBuilderController(newProductBuilderController newProductBuilderController) {
		this.newProductBuilderController = newProductBuilderController;
	}

	/**
	 * This method initialize the items that we have in the catalog so the employee
	 * will select item to a new product.
	 * And this method setting a listener for the qunatityTextField to check a valid input.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		qtytextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (InputChecker.isValidNubmer(newValue))
				quantity = Integer.parseInt(newValue);
			else {
				if (newValue.length() > 0)
					qtytextField.setText(newValue.replaceAll("[^0-9]", ""));
				else
					quantity = 0;

			}
			if (quantity > 0)
				newProductBuilderController.addItemToProduct(item, quantity);
			else
				newProductBuilderController.removeFromProduct(item);
		});
	}
}
