package gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Set;

import entities.Cart;
import entities.Product;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class CartHBox extends HBox {
	private Cart cart = Cart.getInstance();

	private Product product;

	private int quantity;
	
	private double totalSumPrice = 0;
	
	private ImageView image;
	private VBox idNameVBox = new VBox();
	private Label idLabel;
	private Label nameLabel;
	private VBox quantityVBox = new VBox();
	private Label quantityLabel = new Label("Quantity");
	private TextField quantityField = new TextField();
	private VBox priceVBox = new VBox();
	private Label priceLabel = new Label("Price");
	private Label amountLabel;
	private Button removeButton = new Button("X");

	public CartHBox(Product product, int quantity) {
		this.product = product;
		this.quantity = quantity;
	}

	public void initHBox() {
		this.setAlignment(Pos.CENTER);
		this.setSpacing(20);
		this.setPadding(new Insets(0, 7, 7, 0));

		try {
			image = new ImageView(new Image(new FileInputStream(product.getImagePath())));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		image.setFitHeight(80);
		image.setFitWidth(100);
		image.setPreserveRatio(true);

		idLabel = new Label("CatID: " + product.getProductID());

		nameLabel = new Label(product.getProductName());
		nameLabel.setFont(new Font(30));
		nameLabel.setMinWidth(520);
		nameLabel.setPrefWidth(520);
		
		idNameVBox.getChildren().add(idLabel);
		idNameVBox.getChildren().add(nameLabel);
		
		quantityField.setText("" + quantity);
		quantityField.setAlignment(Pos.CENTER);
		quantityField.setMinWidth(50);
		quantityField.setPrefWidth(50);
		
		quantityField.setOnKeyPressed(e -> {
			int id = product.getProductID();
			Integer newQuantity = Integer.parseInt(quantityField.getText());
			cart.addToCart(product, Integer.parseInt(quantityField.getText()), false);
			totalSumPrice = product.getProductPrice() * newQuantity;
			amountLabel.setText(totalSumPrice + " ₪");
			quantity = newQuantity;
		});
		
		quantityVBox.setAlignment(Pos.CENTER);
		quantityVBox.setSpacing(10);

		quantityVBox.getChildren().add(quantityLabel);
		quantityVBox.getChildren().add(quantityField);

		priceLabel.setFont(new Font(20));
		totalSumPrice = product.getProductPrice() * quantity;
		amountLabel = new Label(totalSumPrice + " ₪");
		amountLabel.setFont(new Font(20));
		
		priceVBox.setAlignment(Pos.CENTER);
		
		priceVBox.getChildren().add(priceLabel);
		priceVBox.getChildren().add(amountLabel);

		removeButton.setStyle("-fx-background-color : Red ; -fx-font-size:16 ; -fx-font-weight: bold");
		removeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				cart.removeFromCart(product);
				// TODO: update parent to remove the current HBox of this button
			}
		});
		
		this.getChildren().add(image);
		this.getChildren().add(idNameVBox);
		this.getChildren().add(quantityVBox);
		this.getChildren().add(priceVBox);
		this.getChildren().add(removeButton);
	}
	
	public double getTotalSumPrice() {return totalSumPrice;}
}
