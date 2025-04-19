package stores;

import Interfaces.IStore;
import entites.Customer;

public class SelectedCustomerStore implements IStore<Customer> {

  private Customer selectedCustomer;

  @Override
  public void set(Customer t) {
    this.selectedCustomer = t;
  }

  @Override
  public void clear() {
    this.selectedCustomer = null;
  }

  @Override
  public Customer get() {
    return this.selectedCustomer;
  }

}
