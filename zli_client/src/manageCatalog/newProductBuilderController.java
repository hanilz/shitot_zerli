package manageCatalog;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.ClientFormController;
import entities.Item;
import entities.Product;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import util.Commands;
import util.ManageData;
import util.ManageScreens;

public class newProductBuilderController implements Initializable {
	private ObservableList<Item> avaiableItems;
	private HashMap<Item, Integer> productItems = new HashMap<>();
	private newProductBuilderController npbc;
	private double price;

	@FXML
	private Button addButton;

	@FXML
	private Button browseImageButton;

	@FXML
	private TextField colorField;

	@FXML
	private TextArea descriptionTextArea;

	@FXML
	private Button discardButton;

	@FXML
	private Label fileNotSelectedLabel;

	@FXML
	private TextField flowerTypeTextField;

	@FXML
	private VBox itemSelectorVBox;

	@FXML
	private Label loadingLabel;

	@FXML
	private TextField nameField;

	@FXML
	private TextField priceField;

	@FXML
	private TextField productTypeTextField;

	@FXML
	private Label addItemsErrorLabel;

	@FXML
	private Label fillFieldsErrorLabel;

	@FXML
	private RadioButton ItemRadioButton;

	@FXML
	private RadioButton productRadioButton;

	@FXML
	private Label productTypeLabel;

	@FXML
	void addNewProductToCatalog(ActionEvent event) {
		if (ItemRadioButton.isSelected()) {
			addItemToCataog();
		} else if (productRadioButton.isSelected()) {
			addProductToCatalog();
		}
		ManageScreens.previousScreen();
	}

	private void addProductToCatalog() {
		if (productItems.isEmpty()) {
			addItemsErrorLabel.setVisible(true);
			PauseTransition pause = new PauseTransition(Duration.seconds(3));
			pause.setOnFinished(e -> addItemsErrorLabel.setVisible(false));
			pause.play();
			return;
		}
		if (!checkFieldsProduct()) {
			fillFieldsErrorLabel.setVisible(true);
			PauseTransition pause = new PauseTransition(Duration.seconds(3));
			pause.setOnFinished(e -> fillFieldsErrorLabel.setVisible(false));
			pause.play();
			return;
		}
		Product newProduct = new Product(0, nameField.getText(), colorField.getText(), price,
				productTypeTextField.getText(), null, 0, flowerTypeTextField.getText(),
				descriptionTextArea.getText(), productItems);
		
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.INSERT_NEW_PRODUCT);
		message.put("product", newProduct);
		Object response = ClientFormController.client.accept(message);
		int added = (int) response;
		if(added!=-1) {
			ManageScreens.displayAlert("Added Successfully", "Your product has been added successfully");
		}
	}

	private void addItemToCataog() {
		if (!checkFieldsItem()) {
			fillFieldsErrorLabel.setVisible(true);
			PauseTransition pause = new PauseTransition(Duration.seconds(3));
			pause.setOnFinished(e -> fillFieldsErrorLabel.setVisible(false));
			pause.play();
			return;
		}
		Item newItem = new Item(0,nameField.getText() , colorField.getText(), price, productTypeTextField.getText(), null, 0);
		
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.INSERT_NEW_ITEM);
		message.put("item", newItem);
		Object response = ClientFormController.client.accept(message);
		int added = (int) response;
		if(added!=-1) {
			ManageScreens.displayAlert("Added Successfully", "Your item has been added successfully");
		}
	}

	private boolean checkFieldsItem() {
		if (isEmpty(productTypeTextField) || isEmpty(colorField) || isEmpty(nameField) || isEmpty(priceField)) {
			return false;
		}
		return true;
	}

	private boolean checkFieldsProduct() {
		if (isEmpty(descriptionTextArea) || isEmpty(flowerTypeTextField) || isEmpty(productTypeTextField)
				|| isEmpty(colorField) || isEmpty(nameField) || isEmpty(priceField)) {
			return false;
		}
		return true;
	}

	private boolean isEmpty(TextArea ta) {
		return ta.getText().length() == 0;
	}

	private boolean isEmpty(TextField tf) {
		return tf.getText().length() == 0;
	}

	@FXML
	void chanageToManageCatalogScreen(ActionEvent event) {
		ManageScreens.previousScreen();
	}

	@FXML
	void openImageBrowser(ActionEvent event) {
		// TBD
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		npbc = this;
		initRowsThread();
		addLisetners();

	}

	private void addLisetners() {
		descriptionTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
			if (descriptionTextArea.getLength() > 100)
				descriptionTextArea.setText(oldValue);
		});
		nameField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (nameField.getLength() > 45)
				nameField.setText(oldValue);
		});
		colorField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (colorField.getLength() > 45)
				colorField.setText(oldValue);
		});
		priceField.textProperty().addListener((observable, oldValue, newValue) -> {
			priceField.setText(newValue.replaceAll("[^0-9]", ""));
			if (priceField.getText().length() == 0)
				price = 0;
			else
				price = Double.parseDouble(priceField.getText());
		});
		flowerTypeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (colorField.getLength() > 45)
				colorField.setText(oldValue);
		});
		productTypeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (colorField.getLength() > 45)
				colorField.setText(oldValue);
		});
	}

	public void initRowsThread() {
		Thread taskThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						HashMap<String, Object> message = new HashMap<>();
						message.put("command", Commands.FETCH_ITEMS);
						Object response = ClientFormController.client.accept(message);
						avaiableItems = (ObservableList<Item>) response;
						for (Item item : avaiableItems) {
							try {
								itemSelectorVBox.getChildren().add(loadItemRow(item));
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						itemSelectorVBox.getChildren().remove(loadingLabel);
					}
				});
			}
		});
		taskThread.start();
	}

	private HBox loadItemRow(Item item) throws IOException {
		FXMLLoader loader = new FXMLLoader(
				ProductBuilderItemRowController.class.getResource("productBuilderItemRow.fxml"));
		HBox itemRow = (HBox) loader.load();
		((ProductBuilderItemRowController) loader.getController()).setItem(item);
		((ProductBuilderItemRowController) loader.getController()).setBuilderController(npbc);
		return itemRow;
	}

	/**
	 * adds an item to the product item map
	 * 
	 * @param item
	 * @param quantity
	 */
	public void addItemToProduct(Item item, int quantity) {
		productItems.put(item, quantity);
	}

	/**
	 * removes an item from the product times map
	 * 
	 * @param item
	 */
	public void removeFromProduct(Item item) {
		productItems.remove(item);
	}

	@FXML
	void setToItem(ActionEvent event) {
		itemSelectorVBox.setDisable(true);
		flowerTypeTextField.setDisable(true);
		descriptionTextArea.setDisable(true);
		productTypeLabel.setText("Item Type:");
	}

	@FXML
	void setToProduct(ActionEvent event) {
		itemSelectorVBox.setDisable(false);
		flowerTypeTextField.setDisable(false);
		descriptionTextArea.setDisable(false);
		productTypeLabel.setText("Product Type:");
	}
}
