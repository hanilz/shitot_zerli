package cartTest;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entities.Cart;
import entities.Product;

class CartTest {
	//TODO - wait for Sergay
	private Cart cart;
	
	private Product product1;
	private Product product2;
	private Product product3;

	@BeforeEach
	void setUp() throws Exception {
		cart = Cart.getInstance();
	}

	@Test
	void test() {
		fail("Not yet implemented");
	}
}
