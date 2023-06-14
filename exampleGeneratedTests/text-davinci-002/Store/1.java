import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class StoreTest {

    @Test
    public void testCreateProduct() {
        Store store = new Store();
        Product product = store.createProduct("p1", 10);
        assertEquals("p1", product.getProductId());
        assertEquals(10, product.getPrice(), 0.00001);
    }

    @Test
    public void testGetProduct() {
        Store store = new Store();
        store.createProduct("p1", 10);
        Product product = store.getProduct("p1");
        assertEquals("p1", product.getProductId());
        assertEquals(10, product.getPrice(), 0.00001);
    }

    @Test
    public void testCreateCart() {
        Store store = new Store();
        Cart cart = store.createCart("c1");
        assertEquals("c1", cart.getCartId());
    }

    @Test
    public void testGetCart() {
        Store store = new Store();
        store.createCart("c1");
        Cart cart = store.getCart("c1");
        assertEquals("c1", cart.getCartId());
    }

    @Test
    public void testAddToCart() {
        Store store = new Store();
        store.createProduct("p1", 10);
        store.createCart("c1");
        store.addToCart("c1", "p1", 1);
        Cart cart = store.getCart("c1");
        assertEquals(1, cart.getTotal(), 0.00001);
    }

    @Test
    public void testCheckout() {
        Store store = new Store();
        store.createProduct("p1", 10);
        store.createCart("c1");
        store.addToCart("c1", "p1", 1);
        double total = store.checkout("c1");
        assertEquals(10, total, 0.00001);
        Cart cart = store.getCart("c1");
        assertEquals(0, cart.getTotal(), 0.00001);
    }

    @Test
    public void testQuantity() {
        Store store = new Store();
        store.createProduct("p1", 10);
        store.createCart("c1");
        store.addToCart("c1", "p1", 2);
        Cart cart = store.getCart("c1");
        assertEquals(2, cart.getTotal(), 0.00001);
        store.addToCart("c1", "p1", 3);
        cart = store.getCart("c1");
        assertEquals(8, cart.getTotal(), 0.00001);
    }

    @Test
    public void testMultipleProducts() {
        Store store = new Store();
        store.createProduct("p1", 10);
        store.createProduct("p2", 20);
        store.createCart("c1");
        store.addToCart("c1", "p1", 1);
        store.addToCart("c1", "p2", 2);
        Cart cart = store.getCart("c1");
        assertEquals(50, cart.getTotal(), 0.00001);
    }

    @Test
    public void testMultipleCarts() {
        Store store = new Store();
        store.createProduct("p1", 10);
        store.createCart("c1");
        store.createCart("c2");
        store.addToCart("c1", "p1", 1);
        store.addToCart("c2", "p1", 3);
        assertEquals(10, store.checkout("c1"), 0.00001);
        assertEquals(30, store.checkout("c2"), 0.00001);
    }

    @Test
    public void testNullProductId() {
        assertThrows(IllegalArgumentException.class, () -> {
            Store store = new Store();
            store.createProduct(null, 10);
        });
    }

    @Test
    public void testInvalidPrice() {
        assertThrows(IllegalArgumentException.class, () -> {
            Store store = new Store();
            store.createProduct("p1", -1);
        });
    }

    @Test
    public void testDuplicateProduct() {
        assertThrows(IllegalStateException.class, () -> {
            Store store = new Store();
            store.createProduct("p1", 10);
            store.createProduct("p1", 10);
        });
    }

    @Test
    public void testNullGetProduct() {
        assertThrows(IllegalArgumentException.class, () -> {
            Store store = new Store();
            store.getProduct(null);
        });
    }

    @Test
    public void testNonExistentGetProduct() {
        assertThrows(IllegalStateException.class, () -> {
            Store store = new Store();
            store.getProduct("p1");
        });
    }

    @Test
    public void testNullCartId() {
        assertThrows(IllegalArgumentException.class, () -> {
            Store store = new Store();
            store.createCart(null);
        });
    }

    @Test
    public void testDuplicateCartId() {
        assertThrows(IllegalStateException.class, () -> {
            Store store = new Store();
            store.createCart("c1");
            store.createCart("c1");
        });
    }

    @Test
    public void testNullGetCart() {
        assertThrows(IllegalArgumentException.class, () -> {
            Store store = new Store();
            store.getCart(null);
        });
    }

    @Test
    public void testNonExistentGetCart() {
        assertThrows(IllegalStateException.class, () -> {
            Store store = new Store();
            store.getCart("c1");
        });
    }

    @Test
    public void testNullAddToCart() {
        assertThrows(IllegalArgumentException.class, () -> {
            Store store = new Store();
            store.addToCart(null, "p1", 1);
        });
    }

    @Test
    public void testNullAddToCartProductId() {
        assertThrows(IllegalArgumentException.class, () -> {
            Store store = new Store();
            store.addToCart("c1", null, 1);
        });
    }

    @Test
    public void testInvalidQuantity() {
        assertThrows(IllegalArgumentException.class, () -> {
            Store store = new Store();
            store.addToCart("c1", "p1", -1);
        });
    }

    @Test
    public void testNullCheckout() {
        assertThrows(IllegalArgumentException.class, () -> {
            Store store = new Store();
            store.checkout(null);
        });
    }
}