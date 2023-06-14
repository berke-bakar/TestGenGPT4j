import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class StoreTest {

    @Test
    public void testCreateProduct() {
        Store store = new Store();
        assertThrows(IllegalArgumentException.class,
            () -> store.createProduct(null, 1.0));
        assertThrows(IllegalArgumentException.class,
            () -> store.createProduct("", 1.0));
        assertThrows(IllegalArgumentException.class,
            () -> store.createProduct("1", 0));
        assertThrows(IllegalStateException.class,
            () -> {
                store.createProduct("1", 1.0);
                store.createProduct("1", 1.0);
            });
        store.createProduct("1", 1.0);
    }

    @Test
    public void testGetProduct() {
        Store store = new Store();
        assertThrows(IllegalArgumentException.class,
            () -> store.getProduct(null));
        assertThrows(IllegalStateException.class,
            () -> store.getProduct("1"));
        store.createProduct("1", 1.0);
        store.getProduct("1");
    }

    @Test
    public void testCreateCart() {
        Store store = new Store();
        assertThrows(IllegalArgumentException.class,
            () -> store.createCart(null));
        assertThrows(IllegalStateException.class,
            () -> {
                store.createCart("1");
                store.createCart("1");
            });
        store.createCart("1");
    }

    @Test
    public void testGetCart() {
        Store store = new Store();
        assertThrows(IllegalArgumentException.class,
            () -> store.getCart(null));
        assertThrows(IllegalStateException.class,
            () -> store.getCart("1"));
        store.createCart("1");
        store.getCart("1");
    }

    @Test
    public void testAddToCart() {
        Store store = new Store();
        store.createProduct("1", 1.0);
        store.createCart("1");
        assertThrows(IllegalArgumentException.class,
            () -> store.addToCart("1", "1", 0));
        store.addToCart("1", "1", 1);
    }

    @Test
    public void testCheckout() {
        Store store = new Store();
        store.createProduct("1", 1.0);
        store.createCart("1");
        store.addToCart("1", "1", 1);
        assertEquals(1.0, store.checkout("1"));
        assertEquals(0.0, store.checkout("1"));
    }

}