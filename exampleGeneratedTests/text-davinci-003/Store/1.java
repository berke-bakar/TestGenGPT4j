import org.example.Store;
import org.example.Product;
import org.example.Cart;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.Map;

public class StoreTest {

    @Test
    void createProduct_nullProductId_throwsIllegalArgumentException(){
        Store store = new Store();
        assertThrows(IllegalArgumentException.class, () -> {
            store.createProduct(null, 10.00);
        });
    }

    @Test
    void createProduct_invalidPrice_throwsIllegalArgumentException(){
        Store store = new Store();
        assertThrows(IllegalArgumentException.class, () -> {
            store.createProduct("prodId", -10.00);
        });
    }

    @Test
    void createProduct_validInput_createsProduct(){
        Store store = new Store();
        Product product = store.createProduct("prodId", 10.00);
        assertEquals(10.00, product.getPrice());
        assertEquals("prodId", product.getProductId());
    }

    @Test
    void createProduct_productExists_throwsIllegalStateException() {
        Store store = new Store();
        store.createProduct("prodId", 10.00);
        assertThrows(IllegalStateException.class, () -> {
            store.createProduct("prodId", 10.00);
        });
    }

    @Test
    void getProduct_nullProductId_throwsIllegalArgumentException(){
        Store store = new Store();
        assertThrows(IllegalArgumentException.class, () -> {
            store.getProduct(null);
        });
    }

    @Test
    void getProduct_productExists_productReturned(){
        Store store = new Store();
        store.createProduct("prodId", 10.00);
        Product product = store.getProduct("prodId");
        assertEquals("prodId", product.getProductId());
    }

    @Test
    void getProduct_productNotExists_throwsIllegalStateException(){
        Store store = new Store();
        assertThrows(IllegalStateException.class, () -> {
            store.getProduct("prodNotExist");
        });
    }

    @Test
    void createCart_nullCartId_throwsIllegalArgumentException(){
        Store store = new Store();
        assertThrows(IllegalArgumentException.class, () -> {
            store.createCart(null);
        });
    }

    @Test
    void createCart_validInput_createsCart(){
        Store store = new Store();
        Cart cart = store.createCart("cartId");
        assertEquals("cartId", cart.getCartId());
    }

    @Test
    void createCart_cartExists_throwsIllegalStateException() {
        Store store = new Store();
        store.createCart("cartId");
        assertThrows(IllegalStateException.class, () -> {
            store.createCart("cartId");
        });
    }

    @Test
    void getCart_nullCartId_throwsIllegalArgumentException(){
        Store store = new Store();
        assertThrows(IllegalArgumentException.class, () -> {
            store.getCart(null);
        });
    }

    @Test
    void getCart_cartExists_cartReturned(){
        Store store = new Store();
        store.createCart("cartId");
        Cart cart = store.getCart("cartId");
        assertEquals("cartId", cart.getCartId());
    }

    @Test
    void getCart_cartNotExists_throwsIllegalStateException(){
        Store store = new Store();
        assertThrows(IllegalStateException.class, () -> {
            store.getCart("cartNotExist");
        });
    }

    @Test
    void addToCart_invalidQuantity_throwsIllegalArgumentException(){
        Store store = new Store();
        store.createProduct("prodId", 10.00);
        store.createCart("cartId");
        assertThrows(IllegalArgumentException.class, () -> {
            store.addToCart("cartId", "prodId", 0);
        });
    }

    @Test
    void addToCart_validInput_addsProduct(){
        Store store = new Store();
        store.createProduct("prodId", 10.00);
        store.createCart("cartId");
        store.addToCart("cartId", "prodId", 5);
        Cart cart = store.getCart("cartId");
        Map<Product, Integer> products = new HashMap<Product, Integer>();
        products.put(store.getProduct("prodId"), 5);
        assertEquals(products, cart.products);
    }

    @Test
    void checkout_validInput_correctTotal(){
        Store store = new Store();
        store.createProduct("prodId", 10.00);
        store.createCart("cartId");
        store.addToCart("cartId", "prodId", 5);
        double total = store.checkout("cartId");
        assertEquals(50.0, total);
    }

    @Test
    void checkout_validInput_clearsCart(){
        Store store = new Store();
        store.createProduct("prodId", 10.00);
        store.createCart("cartId");
        store.addToCart("cartId", "prodId", 5);
        store.checkout("cartId");
        Cart cart = store.getCart("cartId");
        Map<Product, Integer> products = new HashMap<Product, Integer>();
        assertEquals(products, cart.products);
    }
}