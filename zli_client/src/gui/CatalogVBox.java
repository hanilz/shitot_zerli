package gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import entities.Cart;
import entities.Product;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CatalogVBox extends VBox {
	private Product product;  // will be used to get the data from
	private Label nameLabel = new Label();  // will show the product name
	private ImageView image;
	private Label priceLabel = new Label("Price:");
	private Label amountLabel = new Label();;  // will show the price
	private HBox priceHBox = new HBox();
	private Button addToCartButton = new Button("Add To Cart"); 
	
	public CatalogVBox(Product product) {
		this.product = product;	
	}
	
	
	/**
	 * 
	 */
	public void initVBox() {
		nameLabel.setText(product.getProductName());
		
		try {
			image = new ImageView(new Image(new FileInputStream(product.getImagePath())));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		image.setFitHeight(120);
		image.setFitWidth(200);
		image.setPreserveRatio(true);
		
		amountLabel.setText("" + ((int)product.getProductPrice()) + " ILS");
		
		priceHBox.getChildren().add(priceLabel);
		priceHBox.getChildren().add(amountLabel);
		priceHBox.setAlignment(Pos.CENTER);
		priceHBox.setSpacing(25);
		
		addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	Cart.getInstance().addToCart(product, 1, true);
		    	System.out.println("Cart is: " + Cart.getInstance().getCart());
		    	System.out.println(product.getProductName() + " added to cart woohoooo");
		    }
		});
		
		this.getChildren().add(nameLabel);
		this.getChildren().add(image);
		this.getChildren().add(priceHBox);
		this.getChildren().add(addToCartButton);
		
		this.setAlignment(Pos.CENTER);
		//VBox.setMargin(this, new Insets(10,10,10,10));
	}
}
