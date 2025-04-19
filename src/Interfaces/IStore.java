package Interfaces;

public interface IStore<T> {
    public void addChangeListener(Runnable listener);

    public void removeChangeListener(Runnable listener);

    public void set(T t);

    public void clear();

    public T get();
}