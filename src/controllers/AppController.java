package controllers;

import java.sql.Connection;
import java.sql.SQLException;
import Interfaces.ICoffeeRepository;
import Interfaces.ICustomerRepository;
import Interfaces.IOrderRepository;
import enums.ViewType;
import repositories.CoffeeRepository;
import repositories.CustomerRepository;
import repositories.OrderRepository;
import services.CoffeeService;
import services.CustomerService;
import services.OrderService;
import utils.ConnectionFactory;

public class AppController {
    private ICustomerRepository customerRepository;
    private ICoffeeRepository coffeeRepository;
    private IOrderRepository orderRepository;
    private final ViewManager viewManager;
    private final CustomerService customerService;
    private final CoffeeService coffeeService;
    private final OrderService orderService;

    public static final double TAX_RATE = 0.08;

    private static AppController instance;

    public static AppController getInstance() {
        if (instance == null) {
            instance = new AppController();
        }
        return instance;
    }

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
        this.coffeeService = new CoffeeService((ICoffeeRepository) this.coffeeRepository);
        this.orderService = new OrderService(this.orderRepository, (CustomerRepository) this.customerRepository);

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
    public CoffeeService getCoffeeService() {
        return this.coffeeService;
    }

    public CustomerService getCustomerService() {
        return this.customerService;
    }

    public OrderService getOrderService() {
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