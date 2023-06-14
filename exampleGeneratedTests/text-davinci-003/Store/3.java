import org.example.Cart;
import org.example.Product;
import org.example.Store;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StoreTest {

    @Test
    void testCreateProduct() {
        Store store = new Store();
        Product product = store.createProduct("A1", 10.0);
        Assertions.assertNotNull(product);
    }

    @Test
    void testCreateProduct_InvalidID() {
        Store store = new Store();
        Assertions.assertThrows(IllegalArgumentException.class, () -> store.createProduct(null, 10.0));
    }

    @Test
    void testCreateProduct_InvalidPrice() {
        Store store = new Store();
        Assertions.assertThrows(IllegalArgumentException.class, () -> store.createProduct("A1", 0.0));
    }

    @Test
    void testCreateProduct_DuplicateProduct() {
        Store store = new Store();
        store.createProduct("A1", 10.0);
        Assertions.assertThrows(IllegalStateException.class, () -> store.createProduct("A1", 10.0));
    }

    @Test
    void testGetProduct_ValidProduct() {
        Store store = new Store();
        Product product = store.createProduct("A1", 10.0);
        Assertions.assertEquals(product, store.getProduct("A1"));
    }

    @Test
    void testGetProduct_InvalidID() {
        Store store = new Store();
        Assertions.assertThrows(IllegalArgumentException.class, () -> store.getProduct(null));
    }

    @Test
    void testGetProduct_NonExistentProduct() {
        Store store = new Store();
        Assertions.assertThrows(IllegalStateException.class, () -> store.getProduct("A1"));
    }

    @Test
    void testCreateCart() {
        Store store = new Store();
        Cart cart = store.createCart("1");
        Assertions.assertNotNull(cart);
    }

    @Test
    void testCreateCart_InvalidID() {
        Store store = new Store();
        Assertions.assertThrows(IllegalArgumentException.class, () -> store.createCart(null));
    }

    @Test
    void testCreateCart_DuplicateCart() {
        Store store = new Store();
        store.createCart("1");
        Assertions.assertThrows(IllegalStateException.class, () -> store.createCart("1"));
    }

    @Test
    void testGetCart_ValidCart() {
        Store store = new Store();
        Cart cart = store.createCart("1");
        Assertions.assertEquals(cart, store.getCart("1"));
    }

    @Test
    void testGetCart_InvalidID() {
        Store store = new Store();
        Assertions.assertThrows(IllegalArgumentException.class, () -> store.getCart(null));
    }

    @Test
    void testGetCart_NonExistentCart() {
        Store store = new Store();
        Assertions.assertThrows(IllegalStateException.class, () -> store.getCart("1"));
    }

    @Test
    void testAddToCart_validProductId() {
        Store store = new Store();
        Product product = store.createProduct("A2", 10.0);
        Cart cart = store.createCart("1");
        store.addToCart("1", "A2", 1);
        Assertions.assertNotNull(cart);
    }

    @Test
    void testAddToCart_InvalidProductId() {
        Store store = new Store();
        Cart cart = store.createCart("1");
        Assertions.assertThrows(IllegalArgumentException.class, () -> store.addToCart("1", null, 1));
    }

    @Test
    void testAddToCart_InvalidQuantity() {
        Store store = new Store();
        Cart cart = store.createCart("1");
        Assertions.assertThrows(IllegalArgumentException.class, () -> store.addToCart("1", "A2", 0));
    }

    @Test
    void testCheckout_ValidCartId() {
        Store store = new Store();
        Product product = store.createProduct("A2", 10.0);
        Cart cart = store.createCart("1");
        store.addToCart("1", "A2", 1);
        double total = store.checkout("1");
        Assertions.assertEquals(10.0, total);
    }

    @Test
    void testCheckout_InvalidCartId() {
        Store store = new Store();
        Assertions.assertThrows(IllegalArgumentException.class, () -> store.checkout(null));
    }

}