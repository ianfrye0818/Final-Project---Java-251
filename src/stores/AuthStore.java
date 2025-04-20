package stores;

import entites.Customer;

public class AuthStore extends BaseStore<Customer> {
    private static final AuthStore instance = new AuthStore();

    private AuthStore() {
    }

    public static AuthStore getInstance() {
        return instance;
    }
}
