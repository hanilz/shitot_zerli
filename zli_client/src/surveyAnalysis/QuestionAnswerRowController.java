package surveyAnalysis;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class QuestionAnswerRowController {

    @FXML
    private Label averageLabel;
    
    @FXML
    private GridPane gradeGridPane;

    @FXML
    private Label questionLabel;
    
    public void initRow(String question,int[] grades) {
    	Double average = 0.0;
    	int sumOfAnswers =0;
    	this.questionLabel.setText(question);
    	for(int i=1; i<=10; i++) {
    		average+=i*grades[i-1];
    		sumOfAnswers+=grades[i-1];
    		gradeGridPane.add(new Label(""+grades[i-1]), i, 1);
    	}
    	average/=sumOfAnswers;
    	averageLabel.setText(String.format("%.2f",average));
    }

}
