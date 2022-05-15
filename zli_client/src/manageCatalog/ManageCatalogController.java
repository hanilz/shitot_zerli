package manageCatalog;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import client.ClientFormController;
import entities.Item;
import entities.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import util.Commands;
import util.ManageScreens;

public class ManageCatalogController implements Initializable {// might extends

	@FXML
	private Label catalogLbl;

	@FXML
	private HBox hbox;

	@FXML
	private ImageView homeImage;

	@FXML
	private ScrollPane scrollCatalogPane;

	private ObservableList<Product> products = FXCollections.observableArrayList();

	private ObservableList<Item> items = FXCollections.observableArrayList();
	
	private ObservableList<String> searchBy = FXCollections.observableArrayList("Name","Id","Type","Color");

	@FXML
	private GridPane catalogGrid;
	
    @FXML
    private Button searchBtn;

    @FXML
    private ComboBox<String> searchOptions;

    @FXML
    private TextArea searchText;

	private ArrayList<ManageCatalogVBox> catalogVBoxList = new ArrayList<>();

	@FXML
	void goToHomeScreen(MouseEvent event) {
		ManageScreens.home();
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fetchProducts();
		fetchItems();
		initCatalogItemVBoxes();
		initGrid();
		searchOptions.setItems(searchBy);
	}

	@SuppressWarnings("unchecked")
	private void fetchItems() {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.FETCH_ITEMS);
		Object response = ClientFormController.client.accept(message);
		items = (ObservableList<Item>) response;
	}

	@SuppressWarnings("unchecked")
	private void fetchProducts() {
		HashMap<String, Object> message = new HashMap<>();
		message.put("command", Commands.FETCH_PRODUCTS);
		Object response = ClientFormController.client.accept(message);
		products = (ObservableList<Product>) response;
	}

	private void initGrid() {
		int numberOfRows = (int) Math.ceil((products.size() + items.size()) / 3.0);
		for (int i = 0; i < numberOfRows; i++) {
			RowConstraints rowConst = new RowConstraints();
			rowConst.setPercentHeight(100.0 / numberOfRows);
			catalogGrid.getRowConstraints().add(rowConst);
		}
		for (int i = 0; i < catalogVBoxList.size(); i++) {
			catalogGrid.add(catalogVBoxList.get(i), i % 3, i / 3);
		}
		catalogGrid.setVgap(20);
	}

	private void initCatalogItemVBoxes() {
		for (int i = 0; i < products.size(); i++) {
			ManageCatalogVBox catalogProductVBox = new ManageCatalogVBox(products.get(i));
			catalogProductVBox.initVBox();
			catalogVBoxList.add(catalogProductVBox);
		}
		for (int i = 0; i < items.size(); i++) {
			ManageCatalogVBox catalogItemVBox = new ManageCatalogVBox(items.get(i));
			catalogItemVBox.initVBox();
			catalogVBoxList.add(catalogItemVBox);
		}
	}

    @FXML
    void search(MouseEvent event) {

    }
    @FXML
    void selectSearchOption(ActionEvent event) {
    	String s=searchOptions.getSelectionModel().getSelectedItem().toString();
    	System.out.println(s);
    }

}
