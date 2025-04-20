package controllers;

import Interfaces.*;
import enums.ViewType;
import repositories.CoffeeRepository;
import repositories.CustomerRepository;
import repositories.OrderRepository;
import services.CoffeeService;
import services.CustomerService;
import services.OrderService;
import utils.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class AppController {
    private ICustomerRepository customerRepository;
    private ICoffeeRepository coffeeRepository;
    private IOrderRepository orderRepository;
    private final ViewManager viewManager;
    private final ICustomerService customerService;
    private final ICoffeeService coffeeService;
    private final IOrderService orderService;

    public static final double TAX_RATE = 0.08;

    private static AppController instance;
    // private static boolean initialized = false;

    public static AppController getInstance() {
        if (instance == null) {
            synchronized (AppController.class) {
                if (instance == null) {
                    instance = new AppController();
                }
            }
        }
        return instance;
    }

    private AppController() {
        try {
            Connection conn = ConnectionFactory.getConnection();
            initializeRepositories(conn);
            // initialized = true;
        } catch (SQLException e) {
            System.out.println("Failed to initialize repositories: " + e.getMessage());
            System.out.println("Exiting....");
            System.exit(1);
        }

        // Initialize services
        this.customerService = new CustomerService(this.customerRepository);
        this.coffeeService = new CoffeeService(this.coffeeRepository);
        this.orderService = new OrderService(this.orderRepository, this.customerRepository);

        // Initialize view manager
        this.viewManager = new ViewManager(this);
    }

    public void start() {
        viewManager.setDisplay(ViewType.LOGIN_VIEW);
    }

    public void setDisplay(ViewType view) {
        viewManager.setDisplay(view);
    }

    // Getters for services
    public ICoffeeService getCoffeeService() {
        return this.coffeeService;
    }

    public ICustomerService getCustomerService() {
        return this.customerService;
    }

    public IOrderService getOrderService() {
        return this.orderService;
    }

    public ICoffeeRepository getCoffeeRepository() {
        return this.coffeeRepository;
    }

    public IOrderRepository getOrderRepository() {
        return (IOrderRepository) this.orderRepository;
    }

    public ViewManager getViewManager() {
        return this.viewManager;
    }

    private void initializeRepositories(Connection conn) throws SQLException {
        customerRepository = new CustomerRepository(conn);
        coffeeRepository = new CoffeeRepository(conn);
        orderRepository = new OrderRepository(conn);

        customerRepository.initTable();
        System.out.println("Customer table initialized");

        coffeeRepository.initTable();
        System.out.println("Coffee table initialized");

        orderRepository.initTable();
        System.out.println("Order table initialized");
    }
}