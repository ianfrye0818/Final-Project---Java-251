package stores;

import entites.Coffee;

/**
 * A singleton store for managing a single selected {@link Coffee} entity within
 * the application. This class extends the generic {@link Store} class to hold
 * and provide access to a specific coffee, for example, one selected for
 * viewing
 * details or for an order. Only one instance of {@code CoffeeStore} exists
 * throughout the application's lifecycle, ensuring a consistent way to access
 * the currently selected coffee.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */

public class CoffeeStore extends Store<Coffee> {
    private static final CoffeeStore instance = new CoffeeStore();

    /**
     * Private constructor to enforce the singleton pattern. Prevents direct
     * instantiation of the {@code CoffeeStore} from outside the class.
     */
    private CoffeeStore() {
    }

    /**
     * Returns the single instance of the {@code CoffeeStore}. This is the global
     * access point for the selected coffee store.
     *
     * @return The singleton instance of {@code CoffeeStore}.
     */
    public static CoffeeStore getInstance() {
        return instance;
    }
}