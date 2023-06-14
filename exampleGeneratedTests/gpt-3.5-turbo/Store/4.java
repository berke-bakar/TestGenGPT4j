import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class StoreTest {
    private Store store;
    private Map<String, Product> products;
    private Map<String, Cart> carts;

    @Mock
    private Cart cartMock;

    @Mock
    private Product productMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        store = new Store();
        products = new HashMap<>();
        carts = new HashMap<>();
    }

    @Test
    void testCreateProduct() {
        String productId = "p1";
        double price = 5.0;

        Product product = store.createProduct(productId, price);
        assertThat(product).isNotNull();
        assertThat(product.getProductId()).isEqualTo(productId);
        assertThat(product.getPrice()).isEqualTo(price);

        assertThrows(IllegalArgumentException.class, () -> store.createProduct(null, price));
        assertThrows(IllegalArgumentException.class, () -> store.createProduct(productId, -1.0));
        assertThrows(IllegalStateException.class, () -> {
            products.put(productId, productMock);
            store.createProduct(productId, price);
        });
    }

    @Test
    void testGetProduct() {
        String productId = "p1";
        Product product = new Product(productId, 5.0);
        products.put(productId, product);

        Product result = store.getProduct(productId);
        assertThat(result).isNotNull();
        assertThat(result.getProductId()).isEqualTo(productId);
        assertThat(result.getPrice()).isEqualTo(product.getPrice());

        assertThrows(IllegalArgumentException.class, () -> store.getProduct(null));
        assertThrows(IllegalStateException.class, () -> store.getProduct("non-existing"));
    }

    @Test
    void testCreateCart() {
        String cartId = "c1";
        Cart cart = store.createCart(cartId);
        assertThat(cart).isNotNull();
        assertThat(cart.getCartId()).isEqualTo(cartId);

        assertThrows(IllegalArgumentException.class, () -> store.createCart(null));
        assertThrows(IllegalStateException.class, () -> {
            carts.put(cartId, cartMock);
            store.createCart(cartId);
        });
    }

    @Test
    void testGetCart() {
        String cartId = "c1";
        Cart cart = new Cart(cartId);
        carts.put(cartId, cart);

        Cart result = store.getCart(cartId);
        assertThat(result).isNotNull();
        assertThat(result.getCartId()).isEqualTo(cartId);

        assertThrows(IllegalArgumentException.class, () -> store.getCart(null));
        assertThrows(IllegalStateException.class, () -> store.getCart("non-existing"));
    }

    @Test
    void testAddToCart() {
        String cartId = "c1";
        String productId = "p1";
        int quantity = 2;
        when(cartMock.getCartId()).thenReturn(cartId);
        when(productMock.getProductId()).thenReturn(productId);
        when(products.get(productId)).thenReturn(productMock);
        store.addToCart(cartId, productId, quantity);
        verify(cartMock, times(1)).addProduct(productMock, quantity);

        assertThrows(IllegalArgumentException.class, () -> store.addToCart(cartId, productId, 0));
        assertThrows(IllegalStateException.class, () -> store.addToCart(cartId, "non-existing", quantity));
    }

    @Test
    void testCheckout() {
        String cartId = "c1";
        double price = 5.0;
        when(cartMock.getTotal()).thenReturn(price);
        when(carts.remove(cartId)).thenReturn(cartMock);
        double result = store.checkout(cartId);
        assertThat(result).isEqualTo(price);

        assertThrows(IllegalArgumentException.class, () -> store.checkout(null));
        assertThrows(IllegalStateException.class, () -> store.checkout("non-existing"));
    }
}