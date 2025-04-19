package stores;

import java.util.List;

import Interfaces.IStore;
import entites.Customer;

public class AuthStore implements IStore<Customer> {

    private Customer currentCustomer;

    private List<Runnable> listeners;

    @Override
    public void set(Customer customer) {
        this.currentCustomer = customer;
        notifyListeners();
    }

    @Override
    public void clear() {
        this.currentCustomer = null;
        notifyListeners();
    }

    @Override
    public Customer get() {
        return this.currentCustomer;
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
