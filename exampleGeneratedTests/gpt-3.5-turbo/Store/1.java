import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(Lifecycle.PER_CLASS)
public class StoreTest {
    private Store store;

    @BeforeEach
    void setUp() {
        store = new Store();
    }

    @AfterEach
    void tearDown() {
        store = null;
    }

    @Nested
    @DisplayName("Given a Store")
    class GivenStore {
        @Test
        @DisplayName("when a product is created with valid arguments, then the product is created successfully")
        void testCreateProductWithValidArguments() {
            Product product = store.createProduct("p1", 10.0);
            assertNotNull(product);
            assertEquals("p1", product.getProductId());
            assertEquals(10.0, product.getPrice());
        }

        @Test
        @DisplayName("when a product is created with null ID, then an IllegalArgumentException is thrown")
        void testCreateProductWithNullId() {
            assertThrows(IllegalArgumentException.class, () -> store.createProduct(null, 10.0));
        }

        @Test
        @DisplayName("when a product is created with negative price, then an IllegalArgumentException is thrown")
        void testCreateProductWithNegativePrice() {
            assertThrows(IllegalArgumentException.class, () -> store.createProduct("p1", -10.0));
        }

        @Test
        @DisplayName("when a product is created with existing ID, then an IllegalStateException is thrown")
        void testCreateProductWithExistingId() {
            store.createProduct("p1", 10.0);
            assertThrows(IllegalStateException.class, () -> store.createProduct("p1", 20.0));
        }

        @Test
        @DisplayName("when a product is retrieved with valid ID, then the product is retrieved successfully")
        void testGetProductWithValidId() {
            store.createProduct("p1", 10.0);
            Product product = store.getProduct("p1");
            assertNotNull(product);
            assertEquals("p1", product.getProductId());
            assertEquals(10.0, product.getPrice());
        }

        @Test
        @DisplayName("when a product is retrieved with null ID, then an IllegalArgumentException is thrown")
        void testGetProductWithNullId() {
            assertThrows(IllegalArgumentException.class, () -> store.getProduct(null));
        }

        @Test
        @DisplayName("when a product is retrieved with non-existing ID, then an IllegalStateException is thrown")
        void testGetProductWithNonExistingId() {
            assertThrows(IllegalStateException.class, () -> store.getProduct("p1"));
        }

        @Test
        @DisplayName("when a cart is created with valid ID, then the cart is created successfully")
        void testCreateCartWithValidId() {
            Cart cart = store.createCart("c1");
            assertNotNull(cart);
            assertEquals("c1", cart.getCartId());
        }

        @Test
        @DisplayName("when a cart is created with null ID, then an IllegalArgumentException is thrown")
        void testCreateCartWithNullId() {
            assertThrows(IllegalArgumentException.class, () -> store.createCart(null));
        }

        @Test
        @DisplayName("when a cart is created with existing ID, then an IllegalStateException is thrown")
        void testCreateCartWithExistingId() {
            store.createCart("c1");
            assertThrows(IllegalStateException.class, () -> store.createCart("c1"));
        }

        @Test
        @DisplayName("when a cart is retrieved with valid ID, then the cart is retrieved successfully")
        void testGetCartWithValidId() {
            store.createCart("c1");
            Cart cart = store.getCart("c1");
            assertNotNull(cart);
            assertEquals("c1", cart.getCartId());
        }

        @Test
        @DisplayName("when a cart is retrieved with null ID, then an IllegalArgumentException is thrown")
        void testGetCartWithNullId() {
            assertThrows(IllegalArgumentException.class, () -> store.getCart(null));
        }

        @Test
        @DisplayName("when a cart is retrieved with non-existing ID, then an IllegalStateException is thrown")
        void testGetCartWithNonExistingId() {
            assertThrows(IllegalStateException.class, () -> store.getCart("c1"));
        }

        @Test
        @DisplayName("when a product is added to a cart with valid arguments, then the product is added successfully")
        void testAddToCartWithValidArguments() {
            store.createProduct("p1", 10.0);
            store.createCart("c1");
            store.addToCart("c1", "p1", 1);
            Cart cart = store.getCart("c1");
            assertEquals(1, cart.getProducts().size());
            assertTrue(cart.getProducts().containsKey(store.getProduct("p1")));
            assertEquals(1, cart.getProducts().get(store.getProduct("p1")));
        }

        @Test
        @DisplayName("when a product is added to a cart with non-existing cart ID, then an IllegalStateException is thrown")
        void testAddToCartWithNonExistingCartId() {
            store.createProduct("p1", 10.0);
            assertThrows(IllegalStateException.class, () -> store.addToCart("c1", "p1", 1));
        }

        @Test
        @DisplayName("when a product is added to a cart with non-existing product ID, then an IllegalStateException is thrown")
        void testAddToCartWithNonExistingProductId() {
            store.createCart("c1");
            assertThrows(IllegalStateException.class, () -> store.addToCart("c1", "p1", 1));
        }

        @Test
        @DisplayName("when a product is added to a cart with negative quantity, then an IllegalArgumentException is thrown")
        void testAddToCartWithNegativeQuantity() {
            store.createProduct("p1", 10.0);
            store.createCart("c1");
            assertThrows(IllegalArgumentException.class, () -> store.addToCart("c1", "p1", -1));
        }

        @Test
        @DisplayName("when a cart is checked out, then the total is returned and the cart is cleared")
        void testCheckout() {
            store.createProduct("p1", 10.0);
            store.createCart("c1");
            store.addToCart("c1", "p1", 2);
            double total = store.checkout("c1");
            assertEquals(20.0, total);
            Cart cart = store.getCart("c1");
            assertTrue(cart.getProducts().isEmpty());
        }
    }
}