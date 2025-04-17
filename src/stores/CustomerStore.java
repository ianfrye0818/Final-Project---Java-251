package stores;

import Interfaces.IStore;
import models.Customer;

public class CustomerStore implements IStore<Customer> {
    private Customer currentCustomer = new Customer.Builder().setCustomerId(1).setFirstName("Ian").setLastName("Kelley")
            .setStreet("123 Main St").setCity("Anytown").setState("CA").setZip("12345").setPhone("1234567890")
            .setEmail("ian@gmail.com").build();

    // private Customer currentCustomer;

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
