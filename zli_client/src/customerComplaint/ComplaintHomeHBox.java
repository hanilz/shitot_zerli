package customerComplaint;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ComplaintHomeHBox extends HBox {
	Complaint complaint;
	
	public ComplaintHomeHBox(Complaint complaint) {
		this.complaint = complaint;
		initHBox();
		initContent();
	}

	private void initHBox() {
		this.setPrefWidth(960);
		this.setPadding(new Insets(5));
		this.setSpacing(15);
		this.setAlignment(Pos.CENTER_LEFT);
	}
	private void initContent() {
		// TODO Auto-generated method stub
		this.getChildren().add(new Label("ComplaintID: "+ complaint.getComplaintID()));
	}
	
}
