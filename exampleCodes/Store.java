package org.example;

import java.util.HashMap;
import java.util.Map;

public class Store {
    private final Map<String, Product> products = new HashMap<>();
    private final Map<String, Cart> carts = new HashMap<>();

    public Product createProduct(String productId, double price) {
        if (productId == null || price <= 0) {
            throw new IllegalArgumentException("Product ID must not be null and Price must be greater than 0");
        }

        if (products.containsKey(productId)) {
            throw new IllegalStateException("Product already exists");
        }

        Product product = new Product(productId, price);
        products.put(productId, product);
        return product;
    }

    public Product getProduct(String productId) {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID must not be null");
        }

        Product product = products.get(productId);
        if (product == null) {
            throw new IllegalStateException("Product does not exist");
        }

        return product;
    }

    public Cart createCart(String cartId) {
        if (cartId == null) {
            throw new IllegalArgumentException("Cart ID must not be null");
        }

        if (carts.containsKey(cartId)) {
            throw new IllegalStateException("Cart already exists");
        }

        Cart cart = new Cart(cartId);
        carts.put(cartId, cart);
        return cart;
    }

    public Cart getCart(String cartId) {
        if (cartId == null) {
            throw new IllegalArgumentException("Cart ID must not be null");
        }

        Cart cart = carts.get(cartId);
        if (cart == null) {
            throw new IllegalStateException("Cart does not exist");
        }

        return cart;
    }

    public void addToCart(String cartId, String productId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        Cart cart = getCart(cartId);
        Product product = getProduct(productId);

        cart.addProduct(product, quantity);
    }

    public double checkout(String cartId) {
        Cart cart = getCart(cartId);
        double total = cart.getTotal();
        cart.clear();
        return total;
    }
}

class Product {
    private final String productId;
    private final double price;

    public Product(String productId, double price) {
        this.productId = productId;
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public double getPrice() {
        return price;
    }
}

class Cart {
    private final String cartId;
    private final Map<Product, Integer> products = new HashMap<>();

    public Cart(String cartId) {
        this.cartId = cartId;
    }

    public String getCartId() {
        return cartId;
    }

    public void addProduct(Product product, int quantity) {
        products.put(product, products.getOrDefault(product, 0) + quantity);
    }

    public double getTotal() {
        return products.entrySet().stream()
                .mapToDouble(e -> e.getKey().getPrice() * e.getValue())
                .sum();
    }

    public void clear() {
        products.clear();
    }
}
