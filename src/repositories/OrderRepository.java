package repositories;

import Interfaces.IOrderRepository;
import dto.CreateOrderDto;
import dto.UpdateOrderDto;
import dto.OrderCoffeeDto;
import dto.OrderCustomerDto;
import entites.Order;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements the {@link IOrderRepository} interface to provide data access
 * operations for {@link Order} entities. This repository interacts with the
 * 'COFFEE_ORDER' table in the database, joining with 'CUSTOMER' and 'COFFEE'
 * tables to retrieve comprehensive order information.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */

public class OrderRepository implements IOrderRepository {
    private final Connection connection;

    /**
     * Constructs a new {@code OrderRepository} with the specified database
     * connection.
     *
     * @param conn The {@link Connection} to the database.
     */
    public OrderRepository(Connection conn) {
        this.connection = conn;
    }

    /**
     * Initializes the 'COFFEE_ORDER' table in the database by first dropping it
     * if it exists and then recreating it. After creation, it populates the table
     * with initial order data.
     *
     * @throws SQLException If a database error occurs during table manipulation or
     *                      population.
     */
    @Override
    public void initTable() throws SQLException {
        resetDatabase();
        populateDatabase();
    }

    /**
     * Retrieves all {@link Order} entities from the 'COFFEE_ORDER' table, joining
     * with 'CUSTOMER' and 'COFFEE' tables to fetch related customer and coffee
     * details.
     * The results are ordered by 'ORDER_ID'.
     *
     * @return A {@code List} of all {@link Order} entities in the table, including
     *         associated customer and coffee information, or an empty list if no
     *         orders exist
     *         or if a database error occurs.
     */
    @Override
    public List<Order> findAll() {
        String sql = """
                SELECT CO.*, C.CUSTOMER_ID, C.FIRST_NAME, C.LAST_NAME, COF.COFFEE_ID, COF.COFFEE_NAME, COF.PRICE FROM COFFEE_ORDER CO
                JOIN CUSTOMER C ON CO.CUSTOMER_ID = C.CUSTOMER_ID
                JOIN COFFEE COF ON CO.COFFEE_ID = COF.COFFEE_ID
                ORDER BY ORDER_ID
                """;

        List<Order> orders = new ArrayList<>();

        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Order order = mapToOrder(rs);
                orders.add(order);
            }
            return orders;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return orders;
    }

    /**
     * Retrieves a specific {@link Order} entity from the 'COFFEE_ORDER' table based
     * on its ID, joining with 'CUSTOMER' and 'COFFEE' tables to fetch related
     * details.
     *
     * @param id The ID of the order to retrieve.
     * @return The {@link Order} entity with the specified ID, including associated
     *         customer and coffee information, or {@code null} if no such order
     *         exists or
     *         if a database error occurs.
     */
    @Override
    public Order findById(int id) {

        Order order = null;
        String sql = """
                SELECT CO.*, C.CUSTOMER_ID, C.FIRST_NAME, C.LAST_NAME, COF.COFFEE_ID, COF.COFFEE_NAME, COF.PRICE FROM COFFEE_ORDER CO
                JOIN CUSTOMER C ON CO.CUSTOMER_ID = C.CUSTOMER_ID
                JOIN COFFEE COF ON CO.COFFEE_ID = COF.COFFEE_ID
                WHERE CO.ORDER_ID = ?
                """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                order = mapToOrder(rs);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return order;
    }

    /**
     * Retrieves all {@link Order} entities associated with a specific customer ID
     * from the 'COFFEE_ORDER' table.
     *
     * @param customerId The ID of the customer whose orders are to be retrieved.
     * @return A {@code List} of {@link Order} entities for the given customer ID,
     *         or an empty list if no orders are found for that customer or if a
     *         database
     *         error occurs.
     */
    @Override
    public List<Order> findByCustomerId(int customerId) {
        String sql = """
                SELECT * FROM COFFEE_ORDER WHERE CUSTOMER_ID = ?
                """;

        List<Order> orders = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Order order = mapToOrder(rs);
                orders.add(order);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return orders;
    }

    /**
     * Saves a new order to the 'COFFEE_ORDER' table using the provided
     * {@link CreateOrderDto}. Upon successful insertion, it retrieves and returns
     * the newly created {@link Order} entity, including its generated ID and
     * associated customer and coffee details.
     *
     * @param order The {@link CreateOrderDto} containing the details of the order
     *              to save.
     * @return The saved {@link Order} entity, or {@code null} if the save operation
     *         fails.
     */
    @Override
    public Order save(CreateOrderDto order) {
        System.out.println("Saving order: " + order);
        String sql = """
                INSERT INTO COFFEE_ORDER (CUSTOMER_ID, COFFEE_ID, QUANTITY_ORDERED, TOTAL_PRICE)
                VALUES (?, ?, ?, ?)
                """;
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setOrderProps(order, stmt);
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int orderId = generatedKeys.getInt(1);
                    Order foundOrder = findById(orderId);
                    return foundOrder;
                }
            }
        } catch (SQLException e) {
            System.out.println("Save Order Failed: " + e.getMessage());
            return null;
        }
        return null;
    }

    /**
     * Updates an existing order in the 'COFFEE_ORDER' table using the provided
     * {@link CreateOrderDto}. The {@code order} object must be an instance of
     * {@link UpdateOrderDto} to include the order's ID for the WHERE clause.
     * Upon successful update, it retrieves and returns the updated {@link Order}
     * entity with associated customer and coffee details.
     *
     * @param order The {@link CreateOrderDto} (must be {@link UpdateOrderDto})
     *              containing the updated details of the order.
     * @return The updated {@link Order} entity, or {@code null} if the update
     *         operation fails or if the provided DTO is not an instance of
     *         {@link UpdateOrderDto}.
     * @throws IllegalArgumentException If the provided {@code order} is not an
     *                                  instance of {@link UpdateOrderDto}.
     */
    @Override
    public Order update(CreateOrderDto order) {
        if (!(order instanceof UpdateOrderDto)) {
            throw new IllegalArgumentException("Order must be an instance of UpdateOrderDto");
        }
        UpdateOrderDto updateOrder = (UpdateOrderDto) order;

        String sql = """
                UPDATE COFFEE_ORDER
                SET CUSTOMER_ID = ?,
                COFFEE_ID = ?,
                QUANTITY_ORDERED = ?,
                TOTAL_PRICE = ?
                WHERE ORDER_ID = ?
                """;

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setOrderProps(updateOrder, stmt);
            stmt.setInt(5, updateOrder.getOrderId());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int orderId = generatedKeys.getInt(1);
                    return findById(orderId);
                }
            }
        } catch (SQLException e) {
            System.out.println("Update Order Failed: " + e.getMessage());
            return null;
        }
        return null;
    }

    /**
     * Deletes an order from the 'COFFEE_ORDER' table based on its ID.
     *
     * @param id The ID of the order to delete.
     * @return {@code true} if the deletion was successful; {@code false} otherwise
     *         or if a database error occurs.
     */
    @Override
    public boolean deleteById(int id) {
        String sql = "DELETE FROM COFFEE_ORDER WHERE ORDER_ID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Delete by Id Failed: " + e.getMessage());
            return false;
        }

        return true;
    }

    /**
     * Maps a row from the {@link ResultSet} to an {@link Order} entity, including
     * associated {@link OrderCustomerDto} and {@link OrderCoffeeDto}.
     *
     * @param rs The {@link ResultSet} containing the data for an order, along with
     *           customer and coffee details from joined tables.
     * @return A new {@link Order} entity populated with data from the result set.
     * @throws SQLException If an error occurs while accessing the result set.
     */
    private Order mapToOrder(ResultSet rs) throws SQLException {
        OrderCustomerDto customer = new OrderCustomerDto.Builder()
                .setCustomerId(rs.getInt("CUSTOMER_ID"))
                .setOrderId(rs.getInt("ORDER_ID"))
                .setCustomerName(rs.getString("FIRST_NAME") + " " + rs.getString("LAST_NAME"))
                .build();

        OrderCoffeeDto coffee = new OrderCoffeeDto.Builder()
                .setOrderId(rs.getInt("ORDER_ID"))
                .setCoffeeId(rs.getInt("COFFEE_ID"))
                .setCoffeeName(rs.getString("COFFEE_NAME"))
                .setPrice(rs.getDouble("PRICE"))
                .build();

        return new Order.Builder().setOrderId(rs.getInt("ORDER_ID"))
                .setCustomer(customer)
                .setCoffee(coffee)
                .setTotal(rs.getDouble("TOTAL_PRICE"))
                .setQtyOrdered(rs.getDouble("QUANTITY_ORDERED"))
                .build();
    }

    /**
     * Sets the properties of an order in a {@link PreparedStatement} based on the
     * data from a {@link CreateOrderDto}.
     *
     * @param order The {@link CreateOrderDto} containing the order's properties.
     * @param stmt  The {@link PreparedStatement} to set the properties on.
     * @throws SQLException If an error occurs while setting the statement
     *                      parameters.
     */
    private void setOrderProps(CreateOrderDto order, PreparedStatement stmt) throws SQLException {
        stmt.setInt(1, order.getCustomerId());
        stmt.setInt(2, order.getCoffeeId());
        stmt.setDouble(3, order.getQtyOrdered());
        stmt.setDouble(4, order.getTotal());
    }

    /**
     * Creates a list of initial {@link CreateOrderDto} objects representing some
     * default orders for populating the database. The order details are randomly
     * generated based on predefined coffee and customer IDs and some sample
     * quantities
     * and total prices.
     *
     * @return A {@code List} of {@link CreateOrderDto} objects for initial orders.
     */
    private List<CreateOrderDto> createInitialOrders() {
        List<CreateOrderDto> orders = new ArrayList<>();
        int[] coffeeIds = { 1, 2, 3, 4, 5, 6 };
        int[] customerIds = { 1, 2, 3 };
        double[] quantities = { 1.0, 2.0, 3.0, 4.0, 5.0, 6.0 };
        double[] totalPrices = { 3.50, 4.00, 2.50, 2.00, 3.00, 2.50 };

        for (int i = 0; i < 10; i++) {
            int coffeeId = coffeeIds[getRandomIndex(coffeeIds)];
            int customerId = customerIds[getRandomIndex(customerIds)];
            double quantity = quantities[getRandomIndex(quantities)];
            double totalPrice = totalPrices[getRandomIndex(totalPrices)];

            CreateOrderDto order = new CreateOrderDto.Builder()
                    .setCoffeeId(coffeeId)
                    .setCustomerId(customerId)
                    .setQtyOrdered(quantity)
                    .setTotal(totalPrice)
                    .build();
            orders.add(order);
        }
        return orders;
    }

    /**
     * Returns a random index within the bounds of the provided integer array.
     *
     * @param array The integer array to get a random index from.
     * @return A random index within the array's bounds.
     */
    private int getRandomIndex(int[] array) {
        return (int) (Math.random() * array.length);
    }

    /**
     * Returns a random index within the bounds of the provided double array.
     *
     * @param array The double array to get a random index from.
     * @return A random index within the array's bounds.
     */
    private int getRandomIndex(double[] array) {
        return (int) (Math.random() * array.length);
    }

    /**
     * Drops the 'COFFEE_ORDER' table from the database if it exists.
     * Creates the 'COFFEE_ORDER' table in the database with the necessary columns
     * and constraints.
     *
     * @throws SQLException If a database error occurs during the table creation.
     */
    @Override
    public void resetDatabase() {
        String dropSQL = "DROP TABLE COFFEE_ORDER";
        String createSQL = """
                CREATE TABLE COFFEE_ORDER (
                  ORDER_ID          INTEGER   GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) CONSTRAINT PK_ORDER_ID PRIMARY KEY,
                  CUSTOMER_ID       INTEGER   NOT NULL,
                  COFFEE_ID         INTEGER   NOT NULL,
                  QUANTITY_ORDERED  DOUBLE    NOT NULL,
                  TOTAL_PRICE       DOUBLE    NOT NULL
                )
                """;

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(dropSQL);
        } catch (SQLException ex) {
            System.out.println("Failed to drop table COFFEE_ORDER: " + ex.getMessage());
        }

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(createSQL);
        } catch (SQLException ex) {
            System.out.println("Failed to create table COFFEE_ORDER: " + ex.getMessage());
        }
    }

    /**
     * Populates the 'COFFEE_ORDER' table with the initial order data generated by
     * {@link #createInitialOrders()}.
     */
    @Override
    public void populateDatabase() {
        List<CreateOrderDto> orders = createInitialOrders();
        for (CreateOrderDto order : orders) {
            save(order);
        }
    }
}