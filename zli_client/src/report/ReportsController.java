package report;

import java.net.URL;
import java.util.ResourceBundle;

import entities.Report;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ReportsController implements Initializable {

    @FXML
    private ComboBox<?> branchComboBox;

    @FXML
    private Label noReportsLabel;

    @FXML
    private ComboBox<?> quraterComboBox;

    @FXML
    private TableView<Report> reportsTable;
    
    @FXML
    private TableColumn<?, ?> typeCol;

    @FXML
    private TableColumn<?, ?> viewCol;
    
    @FXML
    private TableColumn<?, ?> dateCol;

    @FXML
    private Button searchButton;

    @FXML
    private ComboBox<?> yearComboBox;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}

}
