package customerComplaint;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

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
		createLabel("ComplaintID: "+ complaint.getComplaintID());
		createLabel("Order Number: " + complaint.getOrderID());
		createLabel("Reason: " +complaint.getComplaintReason());
//		Label complaintID = new Label("ComplaintID: "+ complaint.getComplaintID());
//		complaintID.setFont(new Font(24));
//		this.getChildren().add(complaintID);
//		this.getChildren().add(new Separator(Orientation.VERTICAL));
//		Label orderID = new Label("Order Number: " + complaint.getOrderID());
//		orderID.setFont(new Font(24));
//		this.getChildren().add(orderID);
//		this.getChildren().add(new Separator(Orientation.VERTICAL));
//		Label complaintReason = new Label("Reason: " +complaint.getCmplaintReason());
//		complaintReason.setFont(new Font(24));
//		this.getChildren().add(complaintReason);
//		this.getChildren().add(new Separator(Orientation.VERTICAL));
	}
	
	private void createLabel(String str) {
		Label label = new Label(str);
		label.setFont(new Font(20));
		this.getChildren().add(label);
		this.getChildren().add(new Separator(Orientation.VERTICAL));
	}
	
}
