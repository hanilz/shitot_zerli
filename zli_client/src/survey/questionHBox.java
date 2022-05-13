package survey;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class questionHBox extends HBox {
	private Label question;
	private int selected = 0;

	public questionHBox(String question) {
		this.setAlignment(Pos.CENTER_LEFT);
		this.question = new Label(question);
		this.question.setPrefWidth(400);
		this.question.setFont(new Font(16));
		this.getChildren().add(this.question);
		this.getChildren().add(new Separator(Orientation.VERTICAL));
		this.setSpacing(12);
		this.setPadding(new Insets(5));
		initSelection();
	}

	private void initSelection() {
		// add question
		ToggleGroup tg = new ToggleGroup();
		for (int i = 1; i <= 10; i++) {
			RadioButton rb = new RadioButton();
			rb.setText("" + i);
			rb.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent actionEvent) {
					rb.getText();
					System.out.println(rb.getText());
					selected = Integer.parseInt(rb.getText());
				}
			});
			rb.setToggleGroup(tg);
			this.getChildren().add(rb);
		}
	}

	public int getSelected() {
		return selected;
	}

}