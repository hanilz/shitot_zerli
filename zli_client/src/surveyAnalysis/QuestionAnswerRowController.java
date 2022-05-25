package surveyAnalysis;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class QuestionAnswerRowController {

    @FXML
    private GridPane gradeGridPane;

    @FXML
    private Label questionLabel;
    
    public void initRow(String question,int[] grades) {
    	this.questionLabel.setText(question);
    	for(int i=1; i<=10; i++) {
    		gradeGridPane.add(new Label(""+grades[i-1]), i, 1);
    	}
    }

}
