package Interfaces;

/**
 * An interface for a store that manages a single object.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */

public interface IStore<T> {

    public void set(T t);

    public void clear();

    public T get();

    public void subscribe(Runnable listener);

    public void unsubscribe(Runnable listener);

}