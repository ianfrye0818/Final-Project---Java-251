package stores;

import entites.Order;

/**
 * A singleton store for managing a single selected {@link Order} entity within
 * the application. This class extends the generic {@link Store} class to hold
 * and provide access to a specific order, for example, one selected for viewing
 * details or for modification. Only one instance of {@code OrderStore} exists
 * throughout the application's lifecycle, ensuring a consistent way to access
 * the currently selected order.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */
public class OrderStore extends Store<Order> {
    private static final OrderStore instance = new OrderStore();

    /**
     * Private constructor to enforce the singleton pattern. Prevents direct
     * instantiation of the {@code OrderStore} from outside the class.
     */
    private OrderStore() {
    }

    /**
     * Returns the single instance of the {@code OrderStore}. This is the global
     * access point for the selected order store.
     *
     * @return The singleton instance of {@code OrderStore}.
     */
    public static OrderStore getInstance() {
        return instance;
    }
}