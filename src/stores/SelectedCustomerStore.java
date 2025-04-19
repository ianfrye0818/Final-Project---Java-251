package stores;

import java.util.List;

import Interfaces.IStore;
import entites.Customer;

public class SelectedCustomerStore implements IStore<Customer> {

  private Customer selectedCustomer;
  private List<Runnable> listeners;

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

  @Override
  public void set(Customer t) {
    this.selectedCustomer = t;
    notifyListeners();
  }

  @Override
  public void clear() {
    this.selectedCustomer = null;
    notifyListeners();
  }

  @Override
  public Customer get() {
    return this.selectedCustomer;
  }

}
