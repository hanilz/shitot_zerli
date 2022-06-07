package surveyAnalysis;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * QuestionAnswerRowController used as a controller for the QuestionAnswerRow
 * FXML, it loads the survey questions answers and shows the average grade for the question 
 * @author Eitan
 *
 */
public class QuestionAnswerRowController {

	@FXML
	private Label averageLabel;

	@FXML
	private GridPane gradeGridPane;

	@FXML
	private Label questionLabel;

	/**used to initialize the answers analysis row
	 * @param question
	 * @param grades
	 */
	public void initRow(String question, int[] grades) {
		Double average = 0.0;
		int sumOfAnswers = 0;
		this.questionLabel.setText(question);
		for (int i = 1; i <= 10; i++) {
			average += i * grades[i - 1];
			sumOfAnswers += grades[i - 1];
			gradeGridPane.add(new Label("" + grades[i - 1]), i, 1);
		}
		average /= sumOfAnswers;
		averageLabel.setText(String.format("%.2f", average));
	}

}
