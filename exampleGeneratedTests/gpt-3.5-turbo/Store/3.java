import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StoreTest {
    private Store store;

    @Test
    void testCreateProduct() {
        store = new Store();
        Product product = store.createProduct("p1", 10.0);
        assertEquals("p1", product.getProductId());
        assertEquals(10.0, product.getPrice());
    }

    @Test
    void testCreateProductInvalidArgs() {
        store = new Store();
        assertThrows(IllegalArgumentException.class, () -> store.createProduct(null, 10.0));
        assertThrows(IllegalArgumentException.class, () -> store.createProduct("p1", -10.0));
    }

    @Test
    void testCreateProductAlreadyExists() {
        store = new Store();
        store.createProduct("p1", 10.0);
        assertThrows(IllegalStateException.class, () -> store.createProduct("p1", 20.0));
    }

    @Test
    void testGetProduct() {
        store = new Store();
        store.createProduct("p1", 10.0);
        Product product = store.getProduct("p1");
        assertEquals("p1", product.getProductId());
        assertEquals(10.0, product.getPrice());
    }

    @Test
    void testGetProductInvalidArgs() {
        store = new Store();
        assertThrows(IllegalArgumentException.class, () -> store.getProduct(null));
    }

    @Test
    void testGetProductDoesNotExist() {
        store = new Store();
        assertThrows(IllegalStateException.class, () -> store.getProduct("p1"));
    }

    @Test
    void testCreateCart() {
        store = new Store();
        Cart cart = store.createCart("c1");
        assertEquals("c1", cart.getCartId());
    }

    @Test
    void testCreateCartInvalidArgs() {
        store = new Store();
        assertThrows(IllegalArgumentException.class, () -> store.createCart(null));
    }

    @Test
    void testCreateCartAlreadyExists() {
        store = new Store();
        store.createCart("c1");
        assertThrows(IllegalStateException.class, () -> store.createCart("c1"));
    }

    @Test
    void testGetCart() {
        store = new Store();
        store.createCart("c1");
        Cart cart = store.getCart("c1");
        assertEquals("c1", cart.getCartId());
    }

    @Test
    void testGetCartInvalidArgs() {
        store = new Store();
        assertThrows(IllegalArgumentException.class, () -> store.getCart(null));
    }

    @Test
    void testGetCartDoesNotExist() {
        store = new Store();
        assertThrows(IllegalStateException.class, () -> store.getCart("c1"));
    }

    @Test
    void testAddToCart() {
        store = new Store();
        store.createProduct("p1", 10.0);
        store.createCart("c1");
        store.addToCart("c1", "p1", 2);
        Cart cart = store.getCart("c1");
        assertEquals(20.0, cart.getTotal());
    }

    @Test
    void testAddToCartInvalidArgs() {
        store = new Store();
        store.createProduct("p1", 10.0);
        store.createCart("c1");
        assertThrows(IllegalArgumentException.class, () -> store.addToCart("c1", null, 2));
        assertThrows(IllegalArgumentException.class, () -> store.addToCart(null, "p1", 2));
        assertThrows(IllegalArgumentException.class, () -> store.addToCart("c1", "p1", -2));
    }

    @Test
    void testAddToCartProductDoesNotExist() {
        store = new Store();
        store.createCart("c1");
        assertThrows(IllegalStateException.class, () -> store.addToCart("c1", "p1", 2));
    }

    @Test
    void testCheckout() {
        store = new Store();
        store.createProduct("p1", 10.0);
        store.createCart("c1");
        store.addToCart("c1", "p1", 2);
        double total = store.checkout("c1");
        assertEquals(20.0, total);
        Cart cart = store.getCart("c1");
        assertEquals(0, cart.getTotal());
    }

    @Test
    void testCheckoutInvalidArgs() {
        store = new Store();
        store.createCart("c1");
        assertThrows(IllegalArgumentException.class, () -> store.checkout(null));
    }

    @Test
    void testCheckoutCartDoesNotExist() {
        store = new Store();
        assertThrows(IllegalStateException.class, () -> store.checkout("c1"));
    }
}