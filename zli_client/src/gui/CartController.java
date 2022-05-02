package gui;

import java.io.IOException;
import java.util.Set;

import entities.Cart;
import entities.Product;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class CartController {
	Cart cart;
	@FXML
	private Button buyButton;

	@FXML
	private VBox cartItemVbox;

	@FXML
	private Label priceLabel;

	@FXML
	void buyProducts(MouseEvent event) {
		Product p1 = new Product(0, "Rose Bouquet - White, Red, Pink", "floerType", "productClor", 15, "ProductType",
				"descreption", "/resources/products/1.png");
		Product p2 = new Product(1, "Carnation Bouquet - Mixed", "floerType", "productClor", 12, "ProductType",
				"descreption", "/resources/products/3.jpg");
		cart = new Cart();
		cart.addToCart(p1, 1);
		cart.addToCart(p2, 3);

		// initcart();
		intitializeCart();
	}

	public void initcart() {
		try {
			Set<Product> items = cart.getCart().keySet();

			for (Product product : items) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("CartRow.fxml"));
				Parent item = loader.load();
//	    		ImageView productImg = (ImageView)item.getChildren().get(0);//img
//	    		productImg.setImage(new Image(product.getImagePath()));
//	    		item.getChildren().set(0, productImg);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void intitializeCart() {
		double totalPrice = 0;
		Set<Product> items = cart.getCart().keySet();
		for (Product product : items) {
			HBox cartRow = new HBox();
			cartRow.setAlignment(Pos.CENTER);
			cartRow.setSpacing(20);
			cartRow.setPadding(new Insets(0, 7, 7, 0));

			ImageView productImg = new ImageView(product.getImagePath());
			productImg.setFitHeight(80);
			productImg.setFitWidth(100);
			cartRow.getChildren().add(productImg);

			cartRow.getChildren().add(new Label("CN:\n" + product.getProductID()));

			Label productName = new Label(product.getProductName());
			productName.setFont(new Font(30));

			productName.setMinWidth(520);
			productName.setPrefWidth(520);

			cartRow.getChildren().add(productName);
			VBox quantity = new VBox();
			quantity.setAlignment(Pos.CENTER);
			quantity.setSpacing(10);
			quantity.getChildren().add(new Label("Quantity"));
			TextField qbox = new TextField();
			qbox.setMinWidth(50);
			qbox.setPrefWidth(50);
			qbox.setText(cart.getCart().get(product).toString());
			qbox.setOnKeyPressed(e -> {
				System.out.println("Clicked");
				String id = ((Label) qbox.getParent().getChildrenUnmodifiable().get(1)).getText();
				String[] lines = id.split("\\n");
				int pid = Integer.parseInt(lines[1]);
				Product old = cart.findByID(pid);
				int oldQty = cart.getCart().get(old);
				cart.addToCart(cart.findByID(pid), Integer.parseInt(qbox.getText()));
				String oldPrice = priceLabel.getText();
				String[] price = oldPrice.split(" ");
				double oldPriceInt = Integer.parseInt(price[0]);
				oldPriceInt = oldPriceInt - oldQty * old.getProductPrice()
						+ cart.getCart().get(product) * product.getProductPrice();
				priceLabel.setText(oldPriceInt + " $");
			});
			quantity.getChildren().add(qbox);
			cartRow.getChildren().add(quantity);

			VBox price = new VBox();
			price.setAlignment(Pos.CENTER);
			Label pricelbl = new Label("Price");
			pricelbl.setFont(new Font(20));
			price.getChildren().add(pricelbl);
			Label priceAmount = new Label(product.getProductPrice() + "$");
			priceAmount.setFont(new Font(20));
			price.getChildren().add(priceAmount);
			cartRow.getChildren().add(price);

			Button removeBtn = new Button("X");
			removeBtn.setStyle("-fx-background-color : Red ; -fx-font-size:16 ; -fx-font-weight: bold");
			removeBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					String id = ((Label) removeBtn.getParent().getChildrenUnmodifiable().get(1)).getText();
					String[] lines = id.split("\\n");
					cart.removeFromCart(cart.findByID(Integer.parseInt(lines[1])));
					cartItemVbox.getChildren().clear();
					intitializeCart();
				}
			});
			cartRow.getChildren().add(removeBtn);

			cartItemVbox.getChildren().add(cartRow);
			totalPrice += product.getProductPrice() * cart.getCart().get(product);
		}
		priceLabel.setText(totalPrice + " $");
	}

//	private class cartrow {
//		private Product product;
//		private HBox row;
//
//		public cartrow(Product product) {
//			this.product = product;
//			this.row = createRow();
//		}
//
//		private HBox createRow() {
//			row = new HBox();
//			row.setAlignment(Pos.CENTER);
//			row.setSpacing(20);
//			row.setPadding(new Insets(0, 7, 7, 0));
//
//			ImageView productImg = new ImageView(product.getImagePath());
//			productImg.setFitHeight(80);
//			productImg.setFitWidth(100);
//			row.getChildren().add(productImg);
//
//			row.getChildren().add(new Label("CN:\n" + product.getProductID()));
//
//			Label productName = new Label(product.getProductName());
//			productName.setFont(new Font(30));
//
//			productName.setMinWidth(520);
//			productName.setPrefWidth(520);
//
//			row.getChildren().add(productName);
//			VBox quantity = new VBox();
//			quantity.setAlignment(Pos.CENTER);
//			quantity.setSpacing(10);
//			quantity.getChildren().add(new Label("Quantity"));
//			TextField qbox = new TextField();
//			qbox.setMinWidth(50);
//			qbox.setPrefWidth(50);
//			qbox.setText(cart.getCart().get(product).toString());
//			qbox.setOnKeyPressed(e -> {
//				System.out.println("Clicked");
//				String id = ((Label) qbox.getParent().getChildrenUnmodifiable().get(1)).getText();
//				String[] lines = id.split("\\n");
//				int pid = Integer.parseInt(lines[1]);
//				Product old = cart.findByID(pid);
//				int oldQty = cart.getCart().get(old);
//				cart.addToCart(cart.findByID(pid), Integer.parseInt(qbox.getText()));
//				String oldPrice = priceLabel.getText();
//				String[] price = oldPrice.split(" ");
//				int oldPriceInt = Integer.parseInt(price[0]);
//				oldPriceInt = oldPriceInt - oldQty * old.getProductPrice()
//						+ cart.getCart().get(product) * product.getProductPrice();
//				priceLabel.setText(oldPriceInt + " $");
//			});
//			quantity.getChildren().add(qbox);
//			row.getChildren().add(quantity);
//
//			VBox price = new VBox();
//			price.setAlignment(Pos.CENTER);
//			Label pricelbl = new Label("Price");
//			pricelbl.setFont(new Font(20));
//			price.getChildren().add(pricelbl);
//			Label priceAmount = new Label(product.getProductPrice() + "$");
//			priceAmount.setFont(new Font(20));
//			price.getChildren().add(priceAmount);
//			row.getChildren().add(price);
//
//			Button removeBtn = new Button("X");
//			removeBtn.setStyle("-fx-background-color : Red ; -fx-font-size:16 ; -fx-font-weight: bold");
//			removeBtn.setOnAction(new EventHandler<ActionEvent>() {
//				@Override
//				public void handle(ActionEvent e) {
//					String id = ((Label) removeBtn.getParent().getChildrenUnmodifiable().get(1)).getText();
//					String[] lines = id.split("\\n");
//					cart.removeFromCart(cart.findByID(Integer.parseInt(lines[1])));
//					cartItemVbox.getChildren().clear();
//					intitializeCart();
//				}
//			});
//			row.getChildren().add(removeBtn);
//			return row;
//		}
//
//	}
}