package stores;

import entites.Coffee;

public class CoffeeStore extends BaseStore<Coffee> {
    private static final CoffeeStore instance = new CoffeeStore();

    private CoffeeStore() {
    }

    public static CoffeeStore getInstance() {
        return instance;
    }
}
