package gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.Character.UnicodeBlock;

import entities.Product;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.Currency;

public class OrderSummaryHBox extends HBox {
	private ImageView image;
	
	private Label priceLabel = new Label();
	
	private Label quantityLabel = new Label();

	private Product product;
	
	private int quantity;
	
	public OrderSummaryHBox(Product product, int quantity) {
		this.product = product;
		this.quantity = quantity;
	}
	
	public void initHBox() {
		this.setAlignment(Pos.CENTER);
		this.setSpacing(15);

		try {
			image = new ImageView(new Image(new FileInputStream(product.getImagePath())));
			image.setOnMouseReleased(new EventHandler<MouseEvent>() {
			    @Override
			    public void handle(MouseEvent event) {
			    	ProductVBox popup = new ProductVBox(product);
					popup.initProductVBox();
					Scene scene = new Scene(popup);
					Stage stage = new Stage();
					stage.setTitle("Product Details - "+ product.getProductName());
					stage.setScene(scene);
					stage.showAndWait();
					// productImage1.setDisable(false);
			    }
			});
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		image.setFitHeight(80);
		image.setFitWidth(70);
		image.setPreserveRatio(true);
		image.setCursor(Cursor.HAND);
		
		quantityLabel.setText("" + quantity);
		
		priceLabel.setFont(new Font(20));
		priceLabel.setText("" + product.getProductPrice() + " NIS");
		
		this.getChildren().add(image);
		this.getChildren().add(quantityLabel);
		this.getChildren().add(new Label("X"));
		this.getChildren().add(priceLabel);
	}
}
