package manageCatalog;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.ClientFormController;
import entities.Item;
import entities.Product;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import util.Commands;
import util.ManageData;
import util.ManageScreens;

/**
 * newProductBuilderController is a controller class for the NewProduct FXML
 * file is is used to create a new product or item to add to the catalog
 * 
 * @author Eitan
 *
 */
public class newProductBuilderController implements Initializable {
	private static ManageCatalogController manageCatalogController;
	private ArrayList<Item> avaiableItems;
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

	/**
	 * adds a product or an item to the catalog
	 * 
	 * @param event
	 */
	@FXML
	void addNewProductToCatalog(ActionEvent event) {
		if (ItemRadioButton.isSelected()) {
			addItemToCataog();
		} else if (productRadioButton.isSelected()) {
			addProductToCatalog();
		}
	}

	/**
	 * used to add a product to the catalog
	 */
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
				productTypeTextField.getText(), "/resources/catalog/product.png", 0, flowerTypeTextField.getText(),
				descriptionTextArea.getText(), productItems);

		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.INSERT_NEW_PRODUCT);
		message.put("product", newProduct);
		Object response = ClientFormController.client.accept(message);
		int added = (int) response;
		if (added != -1) {
			ManageScreens.displayAlert("Added Successfully", "Your product has been added successfully");
			manageCatalogController.addNewProcuctToManageData(newProduct);
		}
		ManageScreens.previousScreen();
	}

	/**
	 * used to add an item to the catalog
	 * 
	 */
	private void addItemToCataog() {
		if (!checkFieldsItem()) {
			fillFieldsErrorLabel.setVisible(true);
			PauseTransition pause = new PauseTransition(Duration.seconds(3));
			pause.setOnFinished(e -> fillFieldsErrorLabel.setVisible(false));
			pause.play();
			return;
		}
		Item newItem = new Item(0, nameField.getText(), colorField.getText(), price, productTypeTextField.getText(),
				"/resources/catalog/flower.png", 0);

		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.INSERT_NEW_ITEM);
		message.put("item", newItem);
		Object response = ClientFormController.client.accept(message);
		int added = (int) response;
		if (added != -1) {
			ManageScreens.displayAlert("Added Successfully", "Your item has been added successfully");
			manageCatalogController.addNewItemToManageData(newItem);
		}
		ManageScreens.previousScreen();
	}

	/**
	 * checks if all the fields are filled for an item
	 * 
	 * @return
	 */
	private boolean checkFieldsItem() {
		if (isEmpty(productTypeTextField) || isEmpty(colorField) || isEmpty(nameField) || isEmpty(priceField)) {
			return false;
		}
		return true;
	}

	/**
	 * checks if all the fields are filled for an product
	 * 
	 * @return
	 */
	private boolean checkFieldsProduct() {
		if (isEmpty(descriptionTextArea) || isEmpty(flowerTypeTextField) || isEmpty(productTypeTextField)
				|| isEmpty(colorField) || isEmpty(nameField) || isEmpty(priceField)) {
			return false;
		}
		return true;
	}

	/**
	 * return true if the textArea is empty
	 * 
	 * @param ta
	 * @return
	 */
	private boolean isEmpty(TextArea ta) {
		return ta.getText().isEmpty();
	}

	/**
	 * return true if the textFiels is empty
	 * 
	 * @param ta
	 * @return
	 */
	private boolean isEmpty(TextField tf) {
		return tf.getText().isEmpty();
	}

	/**
	 * used to go back to the manage catalog screen
	 * 
	 * @param event
	 */
	@FXML
	void chanageToManageCatalogScreen(ActionEvent event) {
		if (ManageScreens.getYesNoDecisionAlert("Discard Product\\ Item",
				"Are you sure you want to discard the Product\\ Item you just created?", null))
			ManageScreens.previousScreen();
	}

	@FXML
	void openImageBrowser(ActionEvent event) {
		// TBD
	}

	/**
	 * used to initialize the screen
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		npbc = this;
		initRowsThread();
		addLisetners();

	}

	/**
	 * adds all listeners to the fields to make sure that the input is correct
	 */
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

	/**
	 * used to load the items after the screen already loads to avoid long waits on
	 * entering the screen
	 */
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
						avaiableItems = ManageData.items;
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

	/**
	 * used to load a single item row to the screen
	 * 
	 * @param item
	 * @return
	 * @throws IOException
	 */
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

	/**sets the fields that need to be filled for an item
	 * @param event
	 */
	@FXML
	void setToItem(ActionEvent event) {
		itemSelectorVBox.setDisable(true);
		flowerTypeTextField.setDisable(true);
		descriptionTextArea.setDisable(true);
		productTypeLabel.setText("Item Type:");
	}

	/**sets the fields that need to be filled for an product
	 * @param event
	 */
	@FXML
	void setToProduct(ActionEvent event) {
		itemSelectorVBox.setDisable(false);
		flowerTypeTextField.setDisable(false);
		descriptionTextArea.setDisable(false);
		productTypeLabel.setText("Product Type:");
	}

	public static void setManageCatalog(ManageCatalogController manageCatalogController) {
		// TODO Auto-generated method stub
		newProductBuilderController.manageCatalogController =manageCatalogController;
	}
}
