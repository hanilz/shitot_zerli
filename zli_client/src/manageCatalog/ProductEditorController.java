package manageCatalog;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.ClientFormController;
import entities.Item;
import entities.Product;
import entities.ProductsBase;
import inputs.InputChecker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.Commands;
import util.ManageScreens;

public class ProductEditorController implements Initializable {

	@FXML
	private TextField basePriceField;

	@FXML
	private Button closeButton;

	@FXML
	private TextField discountField;

	@FXML
	private TextArea editDesTextArea;

	@FXML
	private Label flowerTypeLabel;

	@FXML
	private Label priceDiscountLabel;

	@FXML
	private ImageView productImageView;

	@FXML
	private Label productNameLabel;

	@FXML
	private Label productTypeLabel;

	@FXML
	private Button removeButton;

	@FXML
	private Button saveButton;

	private static ProductsBase product;

	public static void setProductEditorController(ProductsBase product) {
		ProductEditorController.product = product;
	}

	private void setLabelOnPopup() {
		productImageView.setImage(new Image(product.getImagePath()));
		productNameLabel.setText(product.getName());
		productTypeLabel.setText(product.getType());
		flowerTypeLabel.setText("");
		basePriceField.setText(product.getPrice() + "");
		priceDiscountLabel.setText(InputChecker.price(0));
		discountField.setText(product.getDiscount() + "");
		if (product instanceof Product) {
			flowerTypeLabel.setText(((Product) product).getFlowerType());
			editDesTextArea.setText(((Product) product).getProductDescription());
			editDesTextArea.setDisable(false);
		} else if (product instanceof Item) {
			flowerTypeLabel.setText(product.getName());
			editDesTextArea.setText("");
			editDesTextArea.setDisable(true);
		}
	}

	@FXML
	void closeProductPopup(MouseEvent event) {
		((Stage) closeButton.getScene().getWindow()).close();
	}

	@FXML
	void removeProduct(MouseEvent event) {
		if (ManageScreens.getYesNoDecisionAlert("Product Editor", "",
				"Are you sure you want to remove this product?")) {
			HashMap<String, Object> message = new HashMap<>();
			message.put("command", Commands.REMOVE_PRODUCT_BASE);
			if (product instanceof Product) {
				message.put("type", "product");
				message.put("product", product.getId());
			} else if (product instanceof Item) {
				message.put("type", "item");
				message.put("item", product.getId());
			}
			boolean response = (boolean) ClientFormController.client.accept(message);
			if (response) {
				ManageScreens.displayAlert("Product Editor", "Product removed successfully!");
				((Stage) removeButton.getScene().getWindow()).close();
			} else
				ManageScreens.displayAlert("Product Editor", "Can't remove product");
		}
	}

	@FXML
	void saveProduct(MouseEvent event) {
		if (InputChecker.isFieldsEmpty(basePriceField.getText())
				|| InputChecker.isFieldsEmpty(discountField.getText())) {
			if (product instanceof Product && InputChecker.isFieldsEmpty(editDesTextArea.getText()))
				ManageScreens.displayAlert("Product Editor", "Please fill the fields before saving!");
			setToDefult();
			return;
		} else if (InputChecker.isContainsLetters(basePriceField.getText())
				|| InputChecker.isContainsLetters(discountField.getText())) {
			ManageScreens.displayAlert("Product Editor", "Please check the price or the discout that inserted!");
			setToDefult();
			return;
		}
		if (isUpdated()) {
			ManageScreens.displayAlert("Product Editor", "Product updated successfully!");
			calculateDiscount();
		} else
			ManageScreens.displayAlert("Product Editor", "Can't update product");
	}

	private boolean isUpdated() {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.UPDATE_PRODUCTS_BASE);
		if (product instanceof Product) {
			message.put("type", "product");
			message.put("unit", new Product(product.getId(), Double.parseDouble(basePriceField.getText()),
					Integer.parseInt(discountField.getText()), editDesTextArea.getText()));
		} else if (product instanceof Item) {
			message.put("type", "item");
			message.put("unit", new Item(product.getId(), Double.parseDouble(basePriceField.getText()),
					Double.parseDouble(discountField.getText())));
		}
		boolean response = (boolean) ClientFormController.client.accept(message);
		if (response)
			return true;
		return false;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		HashMap<String, Object> message = new HashMap<>();

		if (product instanceof Product) {
			message.put("command", Commands.GET_PRODUCT);
			message.put("product", product.getId());
			Product responseProduct = (Product) ClientFormController.client.accept(message);
			product = responseProduct;
		} else if (product instanceof Item) {
			message.put("command", Commands.GET_ITEM);
			message.put("item", product.getId());
			Item responseItem = (Item) ClientFormController.client.accept(message);
			product = responseItem;
		}
		setLabelOnPopup();
		if (product.getDiscount() != 0) {
			double priceAfterDiscount = product.getPrice() - (product.getPrice() * (product.getDiscount() / 100));
			priceDiscountLabel.setText(InputChecker.price(priceAfterDiscount));
		}
	}

	private void calculateDiscount() {
		if (Double.parseDouble(discountField.getText()) != 0) {
			double price = Double.parseDouble(basePriceField.getText());
			double discount = Double.parseDouble(discountField.getText());
			double priceAfterDiscount = price - (price * (discount / 100));
			priceDiscountLabel.setText(InputChecker.price(priceAfterDiscount));
		} else
			priceDiscountLabel.setText(InputChecker.price(0));
	}

	private void setToDefult() {
		if (editDesTextArea.getText().isEmpty() && product instanceof Product)
			editDesTextArea.setText(((Product) product).getProductDescription());
		basePriceField.setText(product.getPrice() + "");
		discountField.setText(0 + "");
	}

}
