package Interfaces;

public interface IStore<T> {

    public void set(T t);

    public void clear();

    public T get();

    public void subscribe(Runnable listener);

    public void unsubscribe(Runnable listener);

}