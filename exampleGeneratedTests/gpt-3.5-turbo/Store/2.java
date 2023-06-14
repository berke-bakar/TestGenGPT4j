import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class StoreTest {

    private Store store;

    @BeforeEach
    void setUp() {
        store = new Store();
        store.createProduct("p1", 10.0);
        store.createProduct("p2", 20.0);
        store.createCart("c1");
    }

    @Test
    void createProduct() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            store.createProduct(null, 10.0);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            store.createProduct("p1", -10.0);
        });
        Assertions.assertThrows(IllegalStateException.class, () -> {
            store.createProduct("p1", 10.0);
        });
        Assertions.assertNotNull(store.createProduct("p3", 30.0));
    }

    @Test
    void getProduct() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            store.getProduct(null);
        });
        Assertions.assertThrows(IllegalStateException.class, () -> {
            store.getProduct("p3");
        });
        Assertions.assertNotNull(store.getProduct("p1"));
    }

    @Test
    void createCart() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            store.createCart(null);
        });
        Assertions.assertThrows(IllegalStateException.class, () -> {
            store.createCart("c1");
        });
        Assertions.assertNotNull(store.createCart("c2"));
    }

    @Test
    void getCart() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            store.getCart(null);
        });
        Assertions.assertThrows(IllegalStateException.class, () -> {
            store.getCart("c2");
        });
        Assertions.assertNotNull(store.getCart("c1"));
    }

    @Test
    void addToCart() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            store.addToCart("c1", "p1", 0);
        });
        Assertions.assertThrows(IllegalStateException.class, () -> {
            store.addToCart("c2", "p1", 1);
        });
        store.addToCart("c1", "p1", 2);
        Assertions.assertEquals(2, store.getCart("c1").getTotalProducts());
    }

    @Test
    void checkout() {
        Assertions.assertEquals(0.0, store.checkout("c1"));
        store.addToCart("c1", "p1", 2);
        store.addToCart("c1", "p2", 1);
        Assertions.assertEquals(40.0, store.checkout("c1"));
    }
}