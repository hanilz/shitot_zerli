package customProduct;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
    private Label totalAmountField;

    @FXML
    private CheckBox whiteFilterCheckBox;

    @FXML
    private CheckBox yellowFilterCheckBox;

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
		customProductScrollPane.setContent(ManageData.customSelectorVBox);
	}
	
	public void updateOverviewScrollPane() {
		//customProductOverviewScrollPane.setContent(addToCartButton);
	}

}
