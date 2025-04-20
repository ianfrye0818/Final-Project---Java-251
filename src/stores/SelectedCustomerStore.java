package stores;

import entites.Customer;

public class SelectedCustomerStore extends BaseStore<Customer> {

  private static final SelectedCustomerStore instance = new SelectedCustomerStore();

  private SelectedCustomerStore() {
  }

  public static SelectedCustomerStore getInstance() {
    return instance;
  }

}
