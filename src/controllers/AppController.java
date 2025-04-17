package controllers;

import Interfaces.ICustomerRepository;
import Interfaces.IOrderRepository;
import Interfaces.IRepository;
import Interfaces.IStore;
import enums.ViewType;
import models.Coffee;
import models.Customer;
import models.Order;
import services.AuthService;
import services.CoffeeService;
import services.CustomerService;
import services.OrderService;
import stores.CoffeeStore;
import stores.CustomerStore;
import stores.OrderStore;

public class AppController {
    private final IRepository<Customer> customerRepository;
    private final IRepository<Coffee> coffeeRepository;
    private final IRepository<Order> orderRepository;
    private final ViewManager viewManager;
    private final AuthService authService;
    private final CustomerService customerService;
    private final CoffeeService coffeeService;
    private final OrderService orderService;
    private final IStore<Customer> customerStore;
    private final IStore<Order> orderStore;
    private final IStore<Coffee> coffeeStore;

    public static final double TAX_RATE = 0.08;

    private AppController(Builder builder) {
        this.customerRepository = builder.customerRepository;
        this.coffeeRepository = builder.coffeeRepository;
        this.orderRepository = builder.orderRepository;

        // Initialize stores
        this.customerStore = new CustomerStore();
        this.orderStore = new OrderStore();
        this.coffeeStore = new CoffeeStore();

        // Initialize services
        this.customerService = new CustomerService(this);
        this.authService = new AuthService(this);
        this.coffeeService = new CoffeeService(this);
        this.orderService = new OrderService(this);

        // Initialize view manager
        this.viewManager = new ViewManager(this);
    }

    public void start() {
        viewManager.setDisplay(ViewType.VIEW_ALL_ORDERS_VIEW);
    }

    public void setDisplay(ViewType view) {
        viewManager.setDisplay(view);
    }

    // Getters for services
    public CoffeeService getCoffeeService() {
        return this.coffeeService;
    }

    public CustomerService getCustomerService() {
        return this.customerService;
    }

    public OrderService getOrderService() {
        return this.orderService;
    }

    public AuthService getAuthService() {
        return this.authService;
    }

    public IStore<Customer> getCustomerStore() {
        return this.customerStore;
    }

    public IStore<Order> getOrderStore() {
        return this.orderStore;
    }

    public IStore<Coffee> getCoffeeStore() {
        return this.coffeeStore;
    }

    public ICustomerRepository getCustomerRepository() {
        return (ICustomerRepository) this.customerRepository;
    }

    public IRepository<Coffee> getCoffeeRepository() {
        return this.coffeeRepository;
    }

    public IOrderRepository getOrderRepository() {
        return (IOrderRepository) this.orderRepository;
    }

    public ViewManager getViewManager() {
        return this.viewManager;
    }

    public static class Builder {
        private IRepository<Customer> customerRepository;
        private IRepository<Coffee> coffeeRepository;
        private IRepository<Order> orderRepository;

        public Builder customerRepository(IRepository<Customer> customerRepository) {
            this.customerRepository = customerRepository;
            return this;
        }

        public Builder coffeeRepository(IRepository<Coffee> coffeeRepository) {
            this.coffeeRepository = coffeeRepository;
            return this;
        }

        public Builder orderRepository(IRepository<Order> orderRepository) {
            this.orderRepository = orderRepository;
            return this;
        }

        public AppController build() {
            return new AppController(this);
        }
    }
}