package Interfaces;

public interface IStore<T> {
    public void set(T t);

    public void clear();

    public T get();
}