import org.example.Store;
import org.example.Product;
import org.example.Cart;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StoreTest {
    Store store;

    @BeforeEach
    void setup() {
        store = new Store();
    }

    @Test
    void createProduct() {
        Product product = store.createProduct("prod1", 10.0);
        assertEquals("prod1", product.getProductId());
        assertEquals(10.0, product.getPrice());
    }

    @Test
    void createProductNullProductId() {
        assertThrows(IllegalArgumentException.class, () -> store.createProduct(null, 10.0));
    }

    @Test
    void createProductNegativePrice() {
        assertThrows(IllegalArgumentException.class, () -> store.createProduct("prod2", -1.0));
    }

    @Test
    void createProductAlreadyExists() {
        store.createProduct("prod3", 10.0);

        assertThrows(IllegalStateException.class, () -> store.createProduct("prod3", 10.0));
    }

    @Test
    void getProduct() {
        Product product = store.createProduct("prod4", 10.0);
        assertEquals(product, store.getProduct("prod4"));
    }

    @Test
    void getProductNullProductId() {
        assertThrows(IllegalArgumentException.class, () -> store.getProduct(null));
    }

    @Test
    void getProductDoesNotExist() {
        assertThrows(IllegalStateException.class, () -> store.getProduct("prod5"));
    }

    @Test
    void createCart() {
        Cart cart = store.createCart("cart1");
        assertEquals("cart1", cart.getCartId());
    }

    @Test
    void createCartNullCartId() {
        assertThrows(IllegalArgumentException.class, () -> store.createCart(null));
    }

    @Test
    void createCartAlreadyExists() {
        store.createCart("cart2");

        assertThrows(IllegalStateException.class, () -> store.createCart("cart2"));
    }

    @Test
    void getCart() {
        Cart cart = store.createCart("cart3");
        assertEquals(cart, store.getCart("cart3"));
    }

    @Test
    void getCartNullCartId() {
        assertThrows(IllegalArgumentException.class, () -> store.getCart(null));
    }

    @Test
    void getCartDoesNotExist() {
        assertThrows(IllegalStateException.class, () -> store.getCart("cart4"));
    }

    @Test
    void addToCart() {
        Product product = store.createProduct("prod6", 10.0);
        Cart cart = store.createCart("cart5");

        store.addToCart("cart5", "prod6", 1);
        assertEquals(cart.getTotal(), 10.0);
        assertEquals(cart.getCartId(), "cart5");
        assertEquals(1, cart.getProductQuantity(product));
    }

    @Test
    void addToCartNegativeQuantity() {
        store.createProduct("prod7", 10.0);
        store.createCart("cart6");

        assertThrows(IllegalArgumentException.class, () -> store.addToCart("cart6", "prod7", -1));
    }

    @Test
    void checkout() {
        Product product = store.createProduct("prod8", 10.0);
        Cart cart = store.createCart("cart7");
        store.addToCart("cart7", "prod8", 1);

        assertEquals(10.0, store.checkout("cart7"));
        assertEquals(0.0, cart.getTotal());
    }
}