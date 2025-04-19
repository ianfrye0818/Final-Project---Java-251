package Interfaces;

public interface Container {
    <T> T resolve(Class<T> type);

    <T> void register(Class<T> abstraction, Class<? extends T> implementation);

    <T> void registerSingleton(Class<T> abstraction, T instance);

    <T> void registerFactory(Class<T> abstraction, DependencyFactory<T> factory);
}


