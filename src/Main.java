import Interfaces.IRepository;
import controllers.AppController;
import models.Coffee;
import models.Customer;
import models.Order;
import repositories.CoffeeRepository;
import repositories.CustomerRepository;
import repositories.OrderRepository;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    private static IRepository<Customer> customerRepository;
    private static IRepository<Coffee> coffeeRepository;
    private static IRepository<Order> orderRepository;

    public static void main(String[] args) {

        try {
            Connection conn = ConnectionFactory.getConnection();
            System.out.println("Connected to the database");
            initializeRepositories(conn);

            AppController appController = new AppController.Builder()
                    .customerRepository(customerRepository)
                    .coffeeRepository(coffeeRepository)
                    .orderRepository(orderRepository)
                    .build();
            appController.start();

            // Only close when the application is actually shutting down
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                ConnectionFactory.closeConnection(conn);
            }));

        } catch (SQLException ex) {
            System.out.println("Failed to connect to DB: " + ex.getMessage());
            System.exit(1);
        }
    }

    private static void initializeRepositories(Connection conn) throws SQLException {
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