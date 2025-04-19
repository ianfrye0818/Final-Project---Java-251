package controllers;

import Interfaces.*;
import entites.Coffee;
import entites.Customer;
import entites.Order;
import enums.ViewType;
import repositories.CoffeeRepository;
import repositories.CustomerRepository;
import repositories.OrderRepository;
import services.CoffeeService;
import services.OrderService;
import stores.AuthStore;
import stores.CoffeeStore;
import stores.OrderStore;
import stores.SelectedCustomerStore;
import utils.ConnectionFactory;

import java.sql.Connection;

public class AppController {

    private static AppController instance;

    private final Container container;

    public static final double TAX_RATE = 0.08;

    private AppController() {
        container = new IOCContainer();

        container.registerFactory(Connection.class, c -> {
            try {
                return ConnectionFactory.getConnection();
            } catch (Exception e) {
                throw new RuntimeException("Failed to create connection", e);
            }
        });

        container.register(ICustomerRepository.class, CustomerRepository.class);
        container.register(ICoffeeRepository.class, CoffeeRepository.class);
        container.register(IOrderRepository.class, OrderRepository.class);

        container.register(IStore.class, null);

        container.registerSingleton(ViewManager.class, new ViewManager());
        container.registerFactory(IStore.class, c -> new AuthStore());
        container.registerFactory(IStore.class, c -> new SelectedCustomerStore());
        container.registerFactory(IStore.class, c -> new OrderStore());
        container.registerFactory(IStore.class, c -> new CoffeeStore());
    }


    public static AppController getInstance() {
        if (instance == null) {
            instance = new AppController();
        }
        return instance;
    }

    public void start() {
        getViewManager().showMainView();
    }

    public void setDisplay(ViewType view) {
        getViewManager().setDisplay(view);
    }

    public CoffeeService getCoffeeService() {
        return container.resolve(CoffeeService.class);
    }

    public ViewManager getViewManager() {
        return container.resolve(ViewManager.class);
    }

    public OrderService getOrderService() {
        return container.resolve(OrderService.class);
    }

    public IStore<Customer> getLoggedCustomerStore() {
        return (IStore<Customer>) container.resolve(IStore.class);
    }

    public IStore<Customer> getSelectedCustomerStore() {
        return (IStore<Customer>) container.resolve(IStore.class);
    }

    public IStore<Order> getOrderStore() {
        return (IStore<Order>) container.resolve(IStore.class);
    }

    public IStore<Coffee> getCoffeeStore() {
        return (IStore<Coffee>) container.resolve(IStore.class);
    }

    public ICoffeeRepository getCoffeeRepository() {
        return container.resolve(ICoffeeRepository.class);
    }

    public IOrderRepository getOrderRepository() {
        return container.resolve(IOrderRepository.class);
    }

    public ICustomerRepository getCustomerRepository() {
        return container.resolve(ICustomerRepository.class);
    }

    public Container getContainer() {
        return container;
    }


}
