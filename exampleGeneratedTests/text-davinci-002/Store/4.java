public class StoreTest {

    @Test
    public void testCreateProduct_validProductIdAndPrice_productCreated() {
        // Arrange
        String productId = "p1";
        double price = 1.0;

        // Act
        Store store = new Store();
        store.createProduct(productId, price);

        // Assert
        assertThat(store.getProduct(productId), notNullValue());
        assertThat(store.getProduct(productId).getPrice(), equalTo(price));
    }

    @Test
    public void testCreateProduct_nullProductId_throwsIllegalArgumentException() {
        // Arrange
        String productId = null;
        double price = 1.0;

        // Act
        Store store = new Store();

        // Assert
        assertThrows(IllegalArgumentException.class,
                () -> store.createProduct(productId, price));
    }

    @Test
    public void testCreateProduct_zeroPrice_throwsIllegalArgumentException() {
        // Arrange
        String productId = "p1";
        double price = 0.0;

        // Act
        Store store = new Store();

        // Assert
        assertThrows(IllegalArgumentException.class,
                () -> store.createProduct(productId, price));
    }

    @Test
    public void testCreateProduct_negativePrice_throwsIllegalArgumentException() {
        // Arrange
        String productId = "p1";
        double price = -1.0;

        // Act
        Store store = new Store();

        // Assert
        assertThrows(IllegalArgumentException.class,
                () -> store.createProduct(productId, price));
    }

    @Test
    public void testCreateProduct_productIdAlreadyExists_throwsIllegalStateException() {
        // Arrange
        String productId = "p1";
        double price = 1.0;

        // Act
        Store store = new Store();
        store.createProduct(productId, price);

        // Assert
        assertThrows(IllegalStateException.class,
                () -> store.createProduct(productId, price));
    }

    @Test
    public void testGetProduct_productIdExists_returnsProduct() {
        // Arrange
        String productId = "p1";
        double price = 1.0;

        // Act
        Store store = new Store();
        store.createProduct(productId, price);
        Product product = store.getProduct(productId);

        // Assert
        assertThat(product, notNullValue());
        assertThat(product.getProductId(), equalTo(productId));
        assertThat(product.getPrice(), equalTo(price));
    }

    @Test
    public void testGetProduct_nullProductId_throwsIllegalArgumentException() {
        // Arrange
        String productId = null;

        // Act
        Store store = new Store();

        // Assert
        assertThrows(IllegalArgumentException.class,
                () -> store.getProduct(productId));
    }

    @Test
    public void testGetProduct_productIdDoesNotExist_throwsIllegalStateException() {
        // Arrange
        String productId = "p1";

        // Act
        Store store = new Store();

        // Assert
        assertThrows(IllegalStateException.class,
                () -> store.getProduct(productId));
    }

    @Test
    public void testCreateCart_validCartId_cartCreated() {
        // Arrange
        String cartId = "c1";

        // Act
        Store store = new Store();
        store.createCart(cartId);

        // Assert
        assertThat(store.getCart(cartId), notNullValue());
        assertThat(store.getCart(cartId).getCartId(), equalTo(cartId));
    }

    @Test
    public void testCreateCart_nullCartId_throwsIllegalArgumentException() {
        // Arrange
        String cartId = null;

        // Act
        Store store = new Store();

        // Assert
        assertThrows(IllegalArgumentException.class,
                () -> store.createCart(cartId));
    }

    @Test
    public void testCreateCart_cartIdAlreadyExists_throwsIllegalStateException() {
        // Arrange
        String cartId = "c1";

        // Act
        Store store = new Store();
        store.createCart(cartId);

        // Assert
        assertThrows(IllegalStateException.class,
                () -> store.createCart(cartId));
    }

    @Test
    public void testGetCart_cartIdExists_returnsCart() {
        // Arrange
        String cartId = "c1";

        // Act
        Store store = new Store();
        store.createCart(cartId);
        Cart cart = store.getCart(cartId);

        // Assert
        assertThat(cart, notNullValue());
        assertThat(cart.getCartId(), equalTo(cartId));
    }

    @Test
    public void testGetCart_nullCartId_throwsIllegalArgumentException() {
        // Arrange
        String cartId = null;

        // Act
        Store store = new Store();

        // Assert
        assertThrows(IllegalArgumentException.class,
                () -> store.getCart(cartId));
    }

    @Test
    public void testGetCart_cartIdDoesNotExist_throwsIllegalStateException() {
        // Arrange
        String cartId = "c1";

        // Act
        Store store = new Store();

        // Assert
        assertThrows(IllegalStateException.class,
                () -> store.getCart(cartId));
    }

    @Test
    public void testAddToCart_validCartIdAndProductIdAndQuantity_productAddedToCart() {
        // Arrange
        String cartId = "c1";
        String productId = "p1";
        int quantity = 1;
        double price = 1.0;

        // Act
        Store store = new Store();
        store.createCart(cartId);
        store.createProduct(productId, price);
        store.addToCart(cartId, productId, quantity);
        Cart cart = store.getCart(cartId);

        // Assert
        assertThat(cart.getTotal(), equalTo(price * quantity));
    }

    @Test
    public void testAddToCart_nullCartId_throwsIllegalArgumentException() {
        // Arrange
        String cartId = null;
        String productId = "p1";
        int quantity = 1;

        // Act
        Store store = new Store();

        // Assert
        assertThrows(IllegalArgumentException.class,
                () -> store.addToCart(cartId, productId, quantity));
    }

    @Test
    public void testAddToCart_nullProductId_throwsIllegalArgumentException() {
        // Arrange
        String cartId = "c1";
        String productId = null;
        int quantity = 1;

        // Act
        Store store = new Store();

        // Assert
        assertThrows(IllegalArgumentException.class,
                () -> store.addToCart(cartId, productId, quantity));
    }

    @Test
    public void testAddToCart_quantityIsZero_throwsIllegalArgumentException() {
        // Arrange
        String cartId = "c1";
        String productId = "p1";
        int quantity = 0;

        // Act
        Store store = new Store();

        // Assert
        assertThrows(IllegalArgumentException.class,
                () -> store.addToCart(cartId, productId, quantity));
    }

    @Test
    public void testAddToCart_quantityIsNegative_throwsIllegalArgumentException() {
        // Arrange
        String cartId = "c1";
        String productId = "p1";
        int quantity = -1;

        // Act
        Store store = new Store();

        // Assert
        assertThrows(IllegalArgumentException.class,
                () -> store.addToCart(cartId, productId, quantity));
    }

    @Test
    public void testAddToCart_cartIdDoesNotExist_throwsIllegalStateException() {
        // Arrange
        String cartId = "c1";
        String productId = "p1";
        int quantity = 1;

        // Act
        Store store = new Store();

        // Assert
        assertThrows(IllegalStateException.class,
                () -> store.addToCart(cartId, productId, quantity));
    }

    @Test
    public void testAddToCart_productIdDoesNotExist_throwsIllegalStateException() {
        // Arrange
        String cartId = "c1";
        String productId = "p1";
        int quantity = 1;

        // Act
        Store store = new Store();
        store.createCart(cartId);

        // Assert
        assertThrows(IllegalStateException.class,
                () -> store.addToCart(cartId, productId, quantity));
    }

    @Test
    public void testCheckout_validCartId_returnsTotalAndClearsCart() {
        // Arrange
        String cartId = "c1";
        String productId = "p1";
        int quantity = 2;
        double price = 1.0;

        // Act
        Store store = new Store();
        store.createCart(cartId);
        store.createProduct(productId, price);
        store.addToCart(cartId, productId, quantity);
        double total = store.checkout(cartId);
        Cart cart = store.getCart(cartId);

        // Assert
        assertThat(total, equalTo(price * quantity));
        assertThat(cart.getTotal(), equalTo(0.0));
    }

    @Test
    public void testCheckout_nullCartId_throwsIllegalArgumentException() {
        // Arrange
        String cartId = null;

        // Act
        Store store = new Store();

        // Assert
        assertThrows(IllegalArgumentException.class,
                () -> store.checkout(cartId));
    }

    @Test
    public void testCheckout_cartIdDoesNotExist_throwsIllegalStateException() {
        // Arrange
        String cartId = "c1";

        // Act
        Store store = new Store();

        // Assert
        assertThrows(IllegalStateException.class,
                () -> store.checkout(cartId));
    }
}