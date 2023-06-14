import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StoreTest {
    private Store store;

    @BeforeEach
    public void setUp() {
        store = new Store();
    }

    @Test
    public void testCreateProduct() {
        Product product = store.createProduct("1", 10.0);
        assertEquals("1", product.getProductId());
        assertEquals(10.0, product.getPrice(), 0.001);
    }

    @Test
    public void testCreateProductWithInvalidId() {
        assertThrows(IllegalArgumentException.class,
                () -> store.createProduct(null, 10.0));
    }

    @Test
    public void testCreateProductWithInvalidPrice() {
        assertThrows(IllegalArgumentException.class,
                () -> store.createProduct("1", -10.0));
    }

    @Test
    public void testCreateProductThatAlreadyExists() {
        store.createProduct("1", 10.0);
        assertThrows(IllegalStateException.class,
                () -> store.createProduct("1", 10.0));
    }

    @Test
    public void testGetProduct() {
        Product product = store.createProduct("1", 10.0);
        assertEquals(product, store.getProduct("1"));
    }

    @Test
    public void testGetProductThatDoesNotExist() {
        assertThrows(IllegalStateException.class,
                () -> store.getProduct("1"));
    }

    @Test
    public void testGetProductWithInvalidId() {
        assertThrows(IllegalArgumentException.class,
                () -> store.getProduct(null));
    }

    @Test
    public void testCreateCart() {
        Cart cart = store.createCart("1");
        assertEquals("1", cart.getCartId());
    }

    @Test
    public void testCreateCartWithInvalidId() {
        assertThrows(IllegalArgumentException.class,
                () -> store.createCart(null));
    }

    @Test
    public void testCreateCartThatAlreadyExists() {
        store.createCart("1");
        assertThrows(IllegalStateException.class,
                () -> store.createCart("1"));
    }

    @Test
    public void testGetCart() {
        Cart cart = store.createCart("1");
        assertEquals(cart, store.getCart("1"));
    }

    @Test
    public void testGetCartThatDoesNotExist() {
        assertThrows(IllegalStateException.class,
                () -> store.getCart("1"));
    }

    @Test
    public void testGetCartWithInvalidId() {
        assertThrows(IllegalArgumentException.class,
                () -> store.getCart(null));
    }

    @Test
    public void testAddToCart() {
        store.createProduct("1", 10.0);
        store.createCart("1");
        store.addToCart("1", "1", 1);
        assertEquals(10.0, store.getCart("1").getTotal(), 0.001);
    }

    @Test
    public void testAddToCartWithInvalidQuantity() {
        store.createProduct("1", 10.0);
        store.createCart("1");
        assertThrows(IllegalArgumentException.class,
                () -> store.addToCart("1", "1", -1));
    }

    @Test
    public void testAddToCartWithNonExistingProduct() {
        store.createCart("1");
        assertThrows(IllegalStateException.class,
                () -> store.addToCart("1", "1", 1));
    }

    @Test
    public void testAddToCartWithNonExistingCart() {
        store.createProduct("1", 10.0);
        assertThrows(IllegalStateException.class,
                () -> store.addToCart("1", "1", 1));
    }

    @Test
    public void testCheckout() {
        store.createProduct("1", 10.0);
        store.createCart("1");
        store.addToCart("1", "1", 2);
        assertEquals(20.0, store.checkout("1"), 0.001);
        assertEquals(0.0, store.getCart("1").getTotal(), 0.001);
    }

    @Test
    public void testCheckoutOnNonExistingCart() {
        assertThrows(IllegalStateException.class,
                () -> store.checkout("1"));
    }
}