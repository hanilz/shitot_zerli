package survey;


import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class questionHBox extends HBox{
	Label question;
	int answer = 0;
	VBox qNa = new VBox();

	public questionHBox(String question) {
		this.question = new Label(question);
		this.getChildren().add(this.question);
		this.getChildren().add(new Separator(Orientation.VERTICAL));
		initSelection();
	}

	private void initSelection() {
		//add question
		for (int i = 1; i <= 10; i++) {
			HBox answer = new HBox();
			answer.setPadding(new Insets(5));
			answer.setSpacing(10);
			answer.getChildren().add(new Label("Q"+i));
			answer.getChildren().add(new Separator(Orientation.HORIZONTAL));
			answer.getChildren().add(new RadioButton());
			qNa.getChildren().add(answer);
		}
	}
	
	
	
	
}
