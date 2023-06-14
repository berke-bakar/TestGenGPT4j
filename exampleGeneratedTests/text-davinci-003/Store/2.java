import org.example.Store;
import org.example.Product;
import org.example.Cart;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.HashMap;
import java.util.Map;

@DisplayName("Store Unit Tests")
public class StoreTest {
    Store store = new Store();
    Product product = new Product("prod1", 2.99);
    Cart cart = new Cart("cart1");

    // Tests for createProduct()
    @Test
    @DisplayName("createProduct(): success")
    void createProduct_success() {
        assertNotNull(store.createProduct("prod1", 2.99));
    }

    @Test
    @DisplayName("createProduct(): failure - productId is null")
    void createProduct_failure_productId_null() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            store.createProduct(null, 2.99);
        });
        assertEquals("Product ID must not be null and Price must be greater than 0", exception.getMessage());
    }

    @Test
    @DisplayName("createProduct(): failure - price is 0 or negative")
    void createProduct_failure_price_below_1() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            store.createProduct("prod1", 0);
        });
        assertEquals("Product ID must not be null and Price must be greater than 0", exception.getMessage());
    }

    @Test
    @DisplayName("createProduct(): failure - product exists")
    void createProduct_failure_product_exists() {
        store.createProduct("prod1", 2.99);
        Throwable exception = assertThrows(IllegalStateException.class, () -> {
            store.createProduct("prod1", 2.99);
        });
        assertEquals("Product already exists", exception.getMessage());
    }

    // Tests for getProduct()
    @Test
    @DisplayName("getProduct(): success")
    void getProduct_success() {
        store.createProduct("prod1", 2.99);
        assertEquals(product, store.getProduct("prod1"));
    }

    @Test
    @DisplayName("getProduct(): failure - productId is null")
    void getProduct_failure_productId_null() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            store.getProduct(null);
        });
        assertEquals("Product ID must not be null", exception.getMessage());
    }

    @Test
    @DisplayName("getProduct(): failure - product does not exist")
    void getProduct_failure_product_does_not_exist() {
        Throwable exception = assertThrows(IllegalStateException.class, () -> {
            store.getProduct("prod1");
        });
        assertEquals("Product does not exist", exception.getMessage());
    }

    // Tests for createCart()
    @Test
    @DisplayName("createCart(): success")
    void createCart_success() {
        assertNotNull(store.createCart("cart1"));
    }

    @Test
    @DisplayName("createCart(): failure - cartId is null")
    void createCart_failure_cartId_null() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            store.createCart(null);
        });
        assertEquals("Cart ID must not be null", exception.getMessage());
    }

    @Test
    @DisplayName("createCart(): failure - cart already exists")
    void createCart_failure_cart_already_exists() {
        store.createCart("cart1");
        Throwable exception = assertThrows(IllegalStateException.class, () -> {
            store.createCart("cart1");
        });
        assertEquals("Cart already exists", exception.getMessage());
    }

    // Tests for getCart()
    @Test
    @DisplayName("getCart(): success")
    void getCart_success() {
        store.createCart("cart1");
        assertEquals(cart, store.getCart("cart1"));
    }

    @Test
    @DisplayName("getCart(): failure - cartId is null")
    void getCart_failure_cartId_null() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            store.getCart(null);
        });
        assertEquals("Cart ID must not be null", exception.getMessage());
    }

    @Test
    @DisplayName("getCart(): failure - cart does not exist")
    void getCart_failure_cart_does_not_exist() {
        Throwable exception = assertThrows(IllegalStateException.class, () -> {
            store.getCart("cart1");
        });
        assertEquals("Cart does not exist", exception.getMessage());
    }

    // Tests for addToCart()
    @Test
    @DisplayName("addToCart(): success")
    void addToCart_success() {
        store.createProduct("prod1", 2.99);
        store.createCart("cart1");
        store.addToCart("cart1", "prod1", 10);
        assertEquals(10, store.getCart("cart1").products.get(product));
    }

    @Test
    @DisplayName("addToCart(): failure - quantity is 0 or negative")
    void addToCart_failure_quantity_0_or_negative() {
        store.createProduct("prod1", 2.99);
        store.createCart("cart1");
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            store.addToCart("cart1", "prod1", 0);
        });
        assertEquals("Quantity must be positive", exception.getMessage());
    }

    // Tests for checkout()
    @Test
    @DisplayName("checkout(): success")
    void checkout_success() {
        store.createProduct("prod1", 2.99);
        store.createCart("cart1");
        store.addToCart("cart1", "prod1", 10);
        assertEquals(29.90, store.checkout("cart1"));
        assertEquals(0, store.getCart("cart1").products.size());
    }
}