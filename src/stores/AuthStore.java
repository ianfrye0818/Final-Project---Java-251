package stores;

import entites.Customer;

/**
 * A singleton store for managing the currently authenticated {@link Customer}.
 * This class extends the generic {@link Store} class to hold and manage
 * the authentication state of the application. Only one instance of
 * {@code AuthStore} exists throughout the application's lifecycle, providing
 * a global point of access to the authenticated customer.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */
public class AuthStore extends Store<Customer> {
    private static final AuthStore instance = new AuthStore();

    /**
     * Private constructor to enforce the singleton pattern. Prevents direct
     * instantiation of the {@code AuthStore} from outside the class.
     */
    private AuthStore() {
    }

    /**
     * Returns the single instance of the {@code AuthStore}. This is the global
     * access point for the authenticated customer store.
     *
     * @return The singleton instance of {@code AuthStore}.
     */
    public static AuthStore getInstance() {
        return instance;
    }
}