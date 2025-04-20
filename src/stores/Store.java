package stores;

import java.util.ArrayList;
import java.util.List;

import Interfaces.IStore;

public class Store<T> implements IStore<T> {
  private T state;
  private final List<Runnable> listeners = new ArrayList<>();

  @Override
  public void set(T t) {
    this.state = t;
    notifyListeners();
  }

  @Override
  public void clear() {
    this.state = null;
    notifyListeners();
  }

  @Override
  public T get() {
    return state;
  }

  @Override
  public void subscribe(Runnable listener) {
    listeners.add(listener);
  }

  @Override
  public void unsubscribe(Runnable listener) {
    listeners.remove(listener);
  }

  private void notifyListeners() {
    for (Runnable listener : listeners) {
      listener.run();
    }
  }
}
