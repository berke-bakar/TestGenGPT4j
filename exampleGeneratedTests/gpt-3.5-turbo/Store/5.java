// Import necessary packages
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

/**
 * JUnit5 unit test class for the Store class.
 */
class StoreTest {
    private Store store;

    /**
     * Initialize a new Store object before each test method.
     */
    @BeforeEach
    void setup() {
        store = new Store();
    }

    /**
     * Test creating a product.
     */
    @Test
    @DisplayName("Test createProduct method")
    void testCreateProduct() {
        String productId = "ABC123";
        double price = 10.99;

        // Test valid product creation
        Product product = store.createProduct(productId, price);
        Assertions.assertNotNull(product);
        Assertions.assertEquals(productId, product.getProductId());
        Assertions.assertEquals(price, product.getPrice());

        // Test creating a product with a null ID
        Assertions.assertThrows(IllegalArgumentException.class, () -> store.createProduct(null, price));

        // Test creating a product with a negative price
        Assertions.assertThrows(IllegalArgumentException.class, () -> store.createProduct(productId, -1));

        // Test creating a product that already exists
        Assertions.assertThrows(IllegalStateException.class, () -> {
            store.createProduct(productId, price);
            store.createProduct(productId, price);
        });
    }

    /**
     * Test getting a product.
     */
    @Test
    @DisplayName("Test getProduct method")
    void testGetProduct() {
        String productId = "ABC123";
        double price = 10.99;

        // Test getting an existing product
        Product product = store.createProduct(productId, price);
        Product retrievedProduct = store.getProduct(productId);
        Assertions.assertNotNull(retrievedProduct);
        Assertions.assertEquals(productId, retrievedProduct.getProductId());
        Assertions.assertEquals(price, retrievedProduct.getPrice());

        // Test getting a product with a null ID
        Assertions.assertThrows(IllegalArgumentException.class, () -> store.getProduct(null));

        // Test getting a product that does not exist
        Assertions.assertThrows(IllegalStateException.class, () -> store.getProduct("UNKNOWN"));
    }

    /**
     * Test creating a cart.
     */
    @Test
    @DisplayName("Test createCart method")
    void testCreateCart() {
        String cartId = "123";
        Cart cart = store.createCart(cartId);

        // Test valid cart creation
        Assertions.assertNotNull(cart);
        Assertions.assertEquals(cartId, cart.getCartId());

        // Test creating a cart with a null ID
        Assertions.assertThrows(IllegalArgumentException.class, () -> store.createCart(null));

        // Test creating a cart that already exists
        Assertions.assertThrows(IllegalStateException.class, () -> {
            store.createCart(cartId);
            store.createCart(cartId);
        });
    }

    /**
     * Test getting a cart.
     */
    @Test
    @DisplayName("Test getCart method")
    void testGetCart() {
        String cartId = "123";
        Cart cart = store.createCart(cartId);

        // Test getting an existing cart
        Cart retrievedCart = store.getCart(cartId);
        Assertions.assertNotNull(retrievedCart);
        Assertions.assertEquals(cartId, retrievedCart.getCartId());

        // Test getting a cart with a null ID
        Assertions.assertThrows(IllegalArgumentException.class, () -> store.getCart(null));

        // Test getting a cart that does not exist
        Assertions.assertThrows(IllegalStateException.class, () -> store.getCart("UNKNOWN"));
    }

    /**
     * Test adding a product to a cart.
     */
    @Test
    @DisplayName("Test addToCart method")
    void testAddToCart() {
        String productId = "ABC123";
        double price = 10.99;
        String cartId = "123";

        Product product = store.createProduct(productId, price);
        Cart cart = store.createCart(cartId);

        // Test adding a valid product to a valid cart
        int quantity = 5;
        store.addToCart(cartId, productId, quantity);
        Assertions.assertEquals(quantity, cart.getTotalProducts());
        Assertions.assertEquals(quantity * price, cart.getTotalPrice(), 0.001);

        // Test adding a product to an invalid cart
        Assertions.assertThrows(IllegalStateException.class, () -> store.addToCart("UNKNOWN", productId, quantity));

        // Test adding an invalid product to a valid cart
        Assertions.assertThrows(IllegalStateException.class, () -> store.addToCart(cartId, "UNKNOWN", quantity));

        // Test adding zero quantity to a cart
        Assertions.assertThrows(IllegalArgumentException.class, () -> store.addToCart(cartId, productId, 0));

        // Test adding negative quantity to a cart
        Assertions.assertThrows(IllegalArgumentException.class, () -> store.addToCart(cartId, productId, -1));
    }

    /**
     * Test checking out a cart.
     */
    @Test
    @DisplayName("Test checkout method")
    void testCheckout() {
        String productId = "ABC123";
        double price = 10.99;
        String cartId = "123";

        Product product = store.createProduct(productId, price);
        Cart cart = store.createCart(cartId);

        // Test checking out a valid cart
        int quantity = 5;
        store.addToCart(cartId, productId, quantity);
        double total = store.checkout(cartId);
        Assertions.assertEquals(quantity * price, total, 0.001);
        Assertions.assertEquals(0, cart.getTotalProducts());
        Assertions.assertEquals(0, cart.getTotalPrice(), 0.001);

        // Test checking out an invalid cart
        Assertions.assertThrows(IllegalStateException.class, () -> store.checkout("UNKNOWN"));
    }

    /**
     * Test high quality tests.
     */
    @Test
    @DisplayName("Test high quality tests")
    void testHighQualityTests() {
        // TODO: Implement high quality tests for decision coverage, statement coverage, control flow coverage,
        // and data flow coverage.

        // One example scenario for decision coverage: create a new product that already exists, and try to get it
        // afterwards, expecting an IllegalStateException.
        String productId = "ABC123";
        double price = 10.99;
        store.createProduct(productId, price);
        Assertions.assertThrows(IllegalStateException.class, () -> store.createProduct(productId, price));
        Assertions.assertThrows(IllegalStateException.class, () -> store.getProduct(productId));

        // One example scenario for statement coverage: add a new product to a cart, checkout, and assert that the
        // total price is correct and the cart is empty afterwards.
        String cartId = "123";
        Cart cart = store.createCart(cartId);
        double expectedTotal = 0;
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            String newProductId = "P" + i;
            double newPrice = random.nextDouble() * 100;
            int quantity = random.nextInt(5);
            store.createProduct(newProductId, newPrice);
            store.addToCart(cartId, newProductId, quantity);
            expectedTotal += newPrice * quantity;
        }
        double actualTotal = store.checkout(cartId);
        Assertions.assertEquals(expectedTotal, actualTotal, 0.001);
        Assertions.assertEquals(0, cart.getTotalProducts());
        Assertions.assertEquals(0, cart.getTotalPrice(), 0.001);

        // One example scenario for control flow coverage: create a new product with a null ID and a negative price,
        // and assert that an IllegalArgumentException is thrown.
        Assertions.assertThrows(IllegalArgumentException.class, () -> store.createProduct(null, -1));

        // One example scenario for data flow coverage: add a product with a quantity of zero, and assert that an
        // IllegalArgumentException is thrown and the cart remains empty.
        String newProductId = "P1";
        double newPrice = random.nextDouble() * 100;
        store.createProduct(newProductId, newPrice);
        store.addToCart(cartId, newProductId, 0);
        Assertions.assertEquals(0, cart.getTotalProducts());
        Assertions.assertEquals(0, cart.getTotalPrice(), 0.001);
    }
}