package order;

import java.net.URL;
import java.util.ResourceBundle;

import entities.SingletonOrder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class GreetingCardVBoxController implements Initializable {

    @FXML
    private Label fromLabel;

    @FXML
    private Label greetingCardLabel;

    @FXML
    private CheckBox isIncludedCheckBox;

    @FXML
    private HBox mainHBox;
    
    @FXML
    private Label titleLabel;

    @FXML
    private Label toLabel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		isIncludedCheckBox.setSelected(SingletonOrder.getInstance().getIsGreetingCard());
		
		if (isIncludedCheckBox.isSelected()) 
			setLabelsText();
		else 
			mainHBox.setVisible(false);	
	}

	private void setLabelsText() {
		toLabel.setText(SingletonOrder.getInstance().getGreetingCardTo());
		fromLabel.setText(SingletonOrder.getInstance().getGreetingCardFrom());
		greetingCardLabel.setText(SingletonOrder.getInstance().getGreetingCardContent());
	}
}
