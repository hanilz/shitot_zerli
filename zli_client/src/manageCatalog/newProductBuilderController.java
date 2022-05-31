package manageCatalog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import util.ManageScreens;

public class newProductBuilderController {

    @FXML
    private Button addButton;

    @FXML
    private Button backButton;

    @FXML
    private Button browseImageButton;

    @FXML
    private VBox catalogVBox;

    @FXML
    private TextField colorField;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private ComboBox<?> flowerTypeComboBox;

    @FXML
    private CheckBox itemCheckBox;

    @FXML
    private HBox loginHBox;

    @FXML
    private VBox loginVBox;

    @FXML
    private TextField nameField;

    @FXML
    private TextField priceField;

    @FXML
    private CheckBox productCheckBox;

    @FXML
    private ComboBox<?> typeComboBox;

    @FXML
    private ColumnConstraints upperGrid;

    @FXML
    void addToCatalog(MouseEvent event) {

    }

    @FXML
    void chanageToManageCatalogScreen(MouseEvent event) {
    	ManageScreens.previousScreen();
    }

    @FXML
    void itemAdder(MouseEvent event) {

    }

    @FXML
    void openImageProwser(ActionEvent event) {

    }

    @FXML
    void productAdder(MouseEvent event) {

    }

}
