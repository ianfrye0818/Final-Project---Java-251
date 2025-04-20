package stores;

import java.util.ArrayList;
import java.util.List;

import Interfaces.IStore;

/**
 * A generic class that implements the {@link IStore} interface to provide a
 * simple state management mechanism. It holds a single state of type {@code T}
 * and allows components to subscribe to changes in this state. When the state
 * is updated (via {@code set()} or {@code clear()}), all subscribed listeners
 * are notified.
 *
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 * 
 * @param <T> The type of the state being managed by the store.
 */

public class Store<T> implements IStore<T> {
  private T state;
  private final List<Runnable> listeners = new ArrayList<>();

  /**
   * Sets the current state of the store to the provided value and notifies all
   * subscribed listeners of the change.
   *
   * @param t The new state to set.
   */
  @Override
  public void set(T t) {
    this.state = t;
    notifyListeners();
  }

  /**
   * Clears the current state of the store by setting it to {@code null} and
   * notifies all subscribed listeners of the change.
   */
  @Override
  public void clear() {
    this.state = null;
    notifyListeners();
  }

  /**
   * Returns the current state held by the store.
   *
   * @return The current state of type {@code T}, or {@code null} if the state is
   *         clear.
   */
  @Override
  public T get() {
    return state;
  }

  /**
   * Subscribes a {@link Runnable} listener to be notified whenever the state of
   * the store changes. The listener's {@code run()} method will be executed when
   * {@code set()} or {@code clear()} is called.
   *
   * @param listener The {@link Runnable} to subscribe for state change
   *                 notifications.
   */
  @Override
  public void subscribe(Runnable listener) {
    listeners.add(listener);
  }

  /**
   * Unsubscribes a {@link Runnable} listener, so it will no longer receive
   * notifications when the state of the store changes.
   *
   * @param listener The {@link Runnable} to unsubscribe.
   */
  @Override
  public void unsubscribe(Runnable listener) {
    listeners.remove(listener);
  }

  /**
   * Notifies all subscribed listeners by executing their {@code run()} methods.
   * This method is called internally whenever the state of the store is updated.
   */
  private void notifyListeners() {
    for (Runnable listener : listeners) {
      listener.run();
    }
  }
}