package stores;

import Interfaces.IStore;
import entites.Order;
import java.util.List;

public class OrderStore implements IStore<Order> {
    private Order currentOrder;
    private List<Runnable> listeners;

    @Override
    public void set(Order order) {
        this.currentOrder = order;
        notifyListeners();
    }

    @Override
    public void clear() {
        this.currentOrder = null;
        notifyListeners();
    }

    @Override
    public Order get() {
        return this.currentOrder;
    }

    @Override
    public void addChangeListener(Runnable listener) {
        listeners.add(listener);
    }

    @Override
    public void removeChangeListener(Runnable listener) {
        listeners.remove(listener);
    }

    private void notifyListeners() {
        for (Runnable listener : listeners) {
            listener.run();
        }
    }
}
