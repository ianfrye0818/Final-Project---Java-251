package stores;

import entites.Customer;

/**
 * A singleton store for managing a single, specifically selected
 * {@link Customer}
 * entity within the application. This class extends the generic {@link Store}
 * class to hold and provide access to a particular customer, for example, one
 * chosen from a list for viewing detailed information or for editing. Only one
 * instance of {@code SelectedCustomerStore} exists throughout the application's
 * lifecycle, ensuring a consistent way to access the currently selected
 * customer.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */

public class SelectedCustomerStore extends Store<Customer> {

  private static final SelectedCustomerStore instance = new SelectedCustomerStore();

  /**
   * Private constructor to enforce the singleton pattern. Prevents direct
   * instantiation of the {@code SelectedCustomerStore} from outside the class.
   */
  private SelectedCustomerStore() {
  }

  /**
   * Returns the single instance of the {@code SelectedCustomerStore}. This is the
   * global access point for the selected customer store.
   *
   * @return The singleton instance of {@code SelectedCustomerStore}.
   */
  public static SelectedCustomerStore getInstance() {
    return instance;
  }

}