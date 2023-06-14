import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

import static org.junit.jupiter.api.Assertions.*;

public class StoreTest {
    // Test that createProduct() throws an IllegalArgumentException when productId is null
    @Test
    public void createProduct_shouldThrowIllegalArgumentExceptionWhenProductIdIsNull() {
        Store store = new Store();
        String productId = null;
        double price = 10.0;
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            store.createProduct(productId, price);
        });
        assertEquals("Product ID must not be null and Price must be greater than 0",
                illegalArgumentException.getMessage());
    }

    // Test that createProduct() throws an IllegalArgumentException when price is 0 or less
    @Test
    public void createProduct_shouldThrowIllegalArgumentExceptionWhenPriceLessThanZero() {
        Store store = new Store();
        String productId = "Test";
        double price = 0;
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            store.createProduct(productId, price);
        });
        assertEquals("Product ID must not be null and Price must be greater than 0",
                illegalArgumentException.getMessage());
    }

    // Test that createProduct() throws an IllegalStateException when productId already exists
    @Test
    public void createProduct_shouldThrowIllegalStateExceptionWhenProductIdExists() {
        Store store = new Store();
        String productId = "Test";
        double price = 10.0;
        store.createProduct(productId, price);
        IllegalStateException illegalStateException = assertThrows(IllegalStateException.class, () -> {
            store.createProduct(productId, price);
        });
        assertEquals("Product already exists",
                illegalStateException.getMessage());
    }

    // Test that getProduct() throws an IllegalArgumentException when productId is null
    @Test
    public void getProduct_shouldThrowIllegalArgumentExceptionWhenProductIdIsNull() {
        Store store = new Store();
        String productId = null;
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            store.getProduct(productId);
        });
        assertEquals("Product ID must not be null",
                illegalArgumentException.getMessage());
    }

    // Test that getProduct() throws an IllegalStateException when productId does not exists
    @Test
    public void getProduct_shouldThrowIllegalStateExceptionWhenProductIdDoesNotExist() {
        Store store = new Store();
        String productId = "Test";
        IllegalStateException illegalStateException = assertThrows(IllegalStateException.class, () -> {
            store.getProduct(productId);
        });
        assertEquals("Product does not exist",
                illegalStateException.getMessage());
    }

    // Test that createCart() throws an IllegalArgumentException when cartId is null
    @Test
    public void createCart_shouldThrowIllegalArgumentExceptionWhenCartIdIsNull() {
        Store store = new Store();
        String cartId = null;
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            store.createCart(cartId);
        });
        assertEquals("Cart ID must not be null",
                illegalArgumentException.getMessage());
    }

    // Test that createCart() throws an IllegalStateException when cartId already exists
    @Test
    public void createCart_shouldThrowIllegalStateExceptionWhenCartIdExists() {
        Store store = new Store();
        String cartId = "Test";
        store.createCart(cartId);
        IllegalStateException illegalStateException = assertThrows(IllegalStateException.class, () -> {
            store.createCart(cartId);
        });
        assertEquals("Cart already exists",
                illegalStateException.getMessage());
    }

    // Test that getCart() throws an IllegalArgumentException when cartId is null
    @Test
    public void getCart_shouldThrowIllegalArgumentExceptionWhenCartIdIsNull() {
        Store store = new Store();
        String cartId = null;
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            store.getCart(cartId);
        });
        assertEquals("Cart ID must not be null",
                illegalArgumentException.getMessage());
    }

    // Test that getCart() throws an IllegalStateException when cartId does not exists
    @Test
    public void getCart_shouldThrowIllegalStateExceptionWhenCartIdDoesNotExist() {
        Store store = new Store();
        String cartId = "Test";
        IllegalStateException illegalStateException = assertThrows(IllegalStateException.class, () -> {
            store.getCart(cartId);
        });
        assertEquals("Cart does not exist",
                illegalStateException.getMessage());
    }

    // Test that addToCart() throws an IllegalArgumentException when quantity is less than or equal to 0
    @Test
    public void addToCart_shouldThrowIllegalArgumentExceptionWhenQuantityLessThanOrEqualToZero() {
        Store store = new Store();
        String productId = "Test";
        double price = 10.0;
        String cartId = "Test Cart";
        store.createProduct(productId, price);
        store.createCart(cartId);
        int quantity = 0;
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
            store.addToCart(cartId, productId, quantity);
        });
        assertEquals("Quantity must be positive",
                illegalArgumentException.getMessage());
    }
}