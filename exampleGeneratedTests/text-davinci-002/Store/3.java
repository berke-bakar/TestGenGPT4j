import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StoreTest {

    private Store store;

    @BeforeEach
    public void setup() {
        store = new Store();
    }

    @Test
    public void testCreateProduct() {
        Store store = new Store();
        assertThrows(
                IllegalArgumentException.class,
                () -> store.createProduct(null, 1),
                "Product ID must not be null");
        assertThrows(
                IllegalArgumentException.class,
                () -> store.createProduct("id", 0),
                "Price must be greater than 0");

        // Create a product
        Product p1 = store.createProduct("p1", 1);
        assertEquals("p1", p1.getProductId());
        assertEquals(1, p1.getPrice(), 0);

        // Try to create a product with the same ID
        assertThrows(
                IllegalStateException.class,
                () -> store.createProduct("p1", 2),
                "Product already exists");
    }

    @Test
    public void testGetProduct() {
        // Get a product that does not exist
        assertThrows(
                IllegalStateException.class,
                () -> store.getProduct("p1"),
                "Product does not exist");

        // Create a product and get it
        Product p1 = store.createProduct("p1", 1);
        Product p1FromStore = store.getProduct("p1");
        assertEquals(p1, p1FromStore);

        // Try to get a product with null ID
        assertThrows(
                IllegalArgumentException.class,
                () -> store.getProduct(null),
                "Product ID must not be null");
    }

    @Test
    public void testCreateCart() {
        // Try to create a cart with null ID
        assertThrows(
                IllegalArgumentException.class,
                () -> store.createCart(null),
                "Cart ID must not be null");

        // Create a cart
        Cart cart1 = store.createCart("cart1");
        assertEquals("cart1", cart1.getCartId());
        assertEquals(0, cart1.getTotal(), 0);

        // Try to create a cart with the same ID
        assertThrows(
                IllegalStateException.class,
                () -> store.createCart("cart1"),
                "Cart already exists");
    }

    @Test
    public void testGetCart() {
        // Get a cart that does not exist
        assertThrows(
                IllegalStateException.class,
                () -> store.getCart("cart1"),
                "Cart does not exist");

        // Create a cart and get it
        Cart cart1 = store.createCart("cart1");
        Cart cart1FromStore = store.getCart("cart1");
        assertEquals(cart1, cart1FromStore);

        // Try to get a cart with null ID
        assertThrows(
                IllegalArgumentException.class,
                () -> store.getCart(null),
                "Cart ID must not be null");
    }

    @Test
    public void testAddToCart() {
        // Add a product to a cart that does not exist
        assertThrows(
                IllegalStateException.class,
                () -> store.addToCart("cart1", "p1", 1),
                "Cart does not exist");

        // Add a product that does not exist to a cart
        store.createCart("cart1");
        assertThrows(
                IllegalStateException.class,
                () -> store.addToCart("cart1", "p1", 1),
                "Product does not exist");

        // Add a valid product to a cart
        Product p1 = store.createProduct("p1", 1);
        store.addToCart("cart1", "p1", 1);
        Cart cart1 = store.getCart("cart1");
        assertEquals(1, cart1.getTotal(), 0);

        // Add the same product to the cart again
        store.addToCart("cart1", "p1", 1);
        cart1 = store.getCart("cart1");
        assertEquals(2, cart1.getTotal(), 0);

        // Try to add a negative quantity of product to the cart
        assertThrows(
                IllegalArgumentException.class,
                () -> store.addToCart("cart1", "p1", -1),
                "Quantity must be positive");
    }

    @Test
    public void testCheckout() {
        // Try to checkout a cart that does not exist
        assertThrows(
                IllegalStateException.class,
                () -> store.checkout("cart1"),
                "Cart does not exist");

        // Checkout an empty cart
        store.createCart("cart1");
        double total = store.checkout("cart1");
        assertEquals(0, total, 0);

        // Checkout a cart with products
        Product p1 = store.createProduct("p1", 1);
        Product p2 = store.createProduct("p2", 2);
        store.addToCart("cart1", "p1", 2);
        store.addToCart("cart1", "p2", 4);
        total = store.checkout("cart1");
        assertEquals(12, total, 0);

        // Check that the cart is empty after checkout
        Cart cart1 = store.getCart("cart1");
        assertEquals(0, cart1.getTotal(), 0);
    }
}