package stores;

import entites.Order;

public class OrderStore extends BaseStore<Order> {
    private static final OrderStore instance = new OrderStore();

    private OrderStore() {
    }

    public static OrderStore getInstance() {
        return instance;
    }
}
