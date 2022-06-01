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

	public void setItem(Item item) {
		this.item = item;
		itemIDLabel.setText(item.getId() + "");
		itemNameLabel.setText(item.getName());
		itemImage.setImage(new Image(item.getImagePath()));
	}

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
	 * returns true if the item is selected
	 * 
	 * @return
	 */
	public boolean isSelected() {
		return includeCheckBox.isSelected();
	}

	/**
	 * returns the desired quantity for the product
	 * 
	 * @return
	 */
	public int getQuantity() {
		return Integer.parseInt(qtytextField.getText());
	}

	public void setBuilderController(newProductBuilderController newProductBuilderController) {
		this.newProductBuilderController = newProductBuilderController;
	}

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
			System.out.println("" + quantity);
		});
	}
}
