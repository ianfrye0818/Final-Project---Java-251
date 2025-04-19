package stores;

import Interfaces.IStore;
import entites.Customer;

public class AuthStore implements IStore<Customer> {

    private Customer currentCustomer;

    @Override
    public void set(Customer customer) {
        this.currentCustomer = customer;

    }

    @Override
    public void clear() {
        this.currentCustomer = null;
    }

    @Override
    public Customer get() {
        return this.currentCustomer;
    }

}
