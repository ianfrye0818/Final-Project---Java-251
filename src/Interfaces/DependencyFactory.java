package Interfaces;

@FunctionalInterface
public interface DependencyFactory<T> {
    T create(Container container);
}