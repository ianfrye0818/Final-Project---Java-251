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

/**
 * The central controller for the application, managing services, repositories,
 * and the view manager. It follows a singleton pattern to ensure only one
 * instance exists throughout the application lifecycle. It also initializes
 * database connections and tables upon creation.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */
public class AppController {
    private ICustomerRepository customerRepository;
    private ICoffeeRepository coffeeRepository;
    private IOrderRepository orderRepository;
    private final ViewManager viewManager;
    private final ICustomerService customerService;
    private final ICoffeeService coffeeService;
    private final IOrderService orderService;

    /**
     * The tax rate applied to orders.
     */
    public static final double TAX_RATE = 0.08;

    private static AppController instance;

    /**
     * Returns the singleton instance of the {@code AppController}. If no instance
     * exists, a new one is created in a thread-safe manner.
     *
     * @return The singleton instance of the {@code AppController}.
     */
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

    /**
     * Private constructor to enforce the singleton pattern. Initializes database
     * connections, repositories, services, and the view manager. If database
     * initialization fails, the application will exit.
     */
    private AppController() {
        try {
            Connection conn = ConnectionFactory.getConnection();
            initializeRepositories(conn);
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

    /**
     * Starts the application by setting the initial display to the login view.
     */
    public void start() {
        viewManager.setDisplay(ViewType.LOGIN_VIEW);
    }

    /**
     * Sets the current display to the specified {@link ViewType}, delegating
     * the action to the {@link ViewManager}.
     *
     * @param view The {@code ViewType} to display.
     */
    public void setDisplay(ViewType view) {
        viewManager.setDisplay(view);
    }

    /**
     * Returns the {@link ICoffeeService} instance.
     *
     * @return The coffee service.
     */
    public ICoffeeService getCoffeeService() {
        return this.coffeeService;
    }

    /**
     * Returns the {@link ICustomerService} instance.
     *
     * @return The customer service.
     */
    public ICustomerService getCustomerService() {
        return this.customerService;
    }

    /**
     * Returns the {@link IOrderService} instance.
     *
     * @return The order service.
     */
    public IOrderService getOrderService() {
        return this.orderService;
    }

    /**
     * Returns the {@link ICoffeeRepository} instance.
     *
     * @return The coffee repository.
     */
    public ICoffeeRepository getCoffeeRepository() {
        return this.coffeeRepository;
    }

    /**
     * Returns the {@link IOrderRepository} instance.
     *
     * @return The order repository.
     */
    public IOrderRepository getOrderRepository() {
        return this.orderRepository;
    }

    /**
     * Returns the {@link ViewManager} instance.
     *
     * @return The view manager.
     */
    public ViewManager getViewManager() {
        return this.viewManager;
    }

    /**
     * Initializes the repositories for customers, coffees, and orders using the
     * provided database connection. It also calls the {@code initTable()} method
     * for each repository to ensure their respective tables exist in the database.
     *
     * @param conn The {@link Connection} to the database.
     * @throws SQLException If an error occurs during database interaction.
     */
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