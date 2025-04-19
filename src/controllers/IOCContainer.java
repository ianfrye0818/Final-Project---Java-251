package controllers;

import Interfaces.Container;
import Interfaces.DependencyFactory;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class IOCContainer implements Container {
    private final Map<Class<?>, Object> singletons = new HashMap<>();
    private final Map<Class<?>, Class<?>> implementations = new HashMap<>();
    private final Map<Class<?>, DependencyFactory<?>> factories = new HashMap<>();


    @Override
    public <T> T resolve(Class<T> type) {
        // Check if we have a singleton
        if (singletons.containsKey(type)) {
            return type.cast(singletons.get(type));
        }

        // Check if we have a factory
        if (factories.containsKey(type)) {
            DependencyFactory<T> factory = (DependencyFactory<T>) factories.get(type);
            return factory.create(this);
        }

        // Check if we have a registered implementation
        Class<?> implementationClass = implementations.getOrDefault(type, type);

        try {
            // Find the constructor with the most parameters
            Constructor<?> constructor = findSuitableConstructor(implementationClass);

            // Get constructor parameters
            Class<?>[] paramTypes = constructor.getParameterTypes();
            Object[] params = new Object[paramTypes.length];

            // Resolve dependencies recursively
            for (int i = 0; i < paramTypes.length; i++) {
                params[i] = resolve(paramTypes[i]);
            }

            // Create the instance
            T instance = (T) constructor.newInstance(params);

            return instance;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create instance of " + type.getName(), e);
        }
    }


    @Override
    public <T> void register(Class<T> abstraction, Class<? extends T> implementation) {
        implementations.put(abstraction, implementation);
    }

    @Override
    public <T> void registerSingleton(Class<T> abstraction, T instance) {
        singletons.put(abstraction, instance);
    }


    @Override
    public <T> void registerFactory(Class<T> abstraction, DependencyFactory<T> factory) {
        factories.put(abstraction, factory);
    }


    private <T> Constructor<?> findSuitableConstructor(Class<T> clazz) {
        Constructor<?>[] constructors = clazz.getConstructors();
        if (constructors.length == 0) {
            constructors = clazz.getDeclaredConstructors();
        }

        Constructor<?> constructor = constructors[0];

        for (Constructor<?> c : constructors) {
            if (c.getParameterCount() < constructor.getParameterCount()) {
                constructor = c;
            }
        }

        constructor.setAccessible(true);

        return constructor;

    }
}
