package stores;

import Interfaces.IStore;
import entites.Order;

public class OrderStore implements IStore<Order> {
    private Order currentOrder;

    @Override
    public void set(Order order) {
        this.currentOrder = order;
    }

    @Override
    public void clear() {
        this.currentOrder = null;
    }

    @Override
    public Order get() {
        return this.currentOrder;
    }
}
