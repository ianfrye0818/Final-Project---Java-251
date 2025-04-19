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

public class OrderRepository implements IOrderRepository {
    private final Connection connection;

    public OrderRepository(Connection conn) {
        this.connection = conn;
    }

    @Override
    public void initTable() throws SQLException {
        resetDatabase();
        populateDatabase();
    }

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

    // @Override
    // public boolean save(CreateOrderDto order) {
    // String sql = """
    // INSERT INTO COFFEE_ORDER (CUSTOMER_ID, COFFEE_ID, QUANTITY_ORDERED,
    // TOTAL_PRICE)
    // VALUES (?, ?, ?, ?)
    // """;
    // try (PreparedStatement stmt = connection.prepareStatement(sql)) {
    // setOrderProps(order, stmt);
    // stmt.executeUpdate();
    // } catch (SQLException e) {
    // System.out.println("Save Order Failed: " + e.getMessage());
    // return false;
    // }
    // return true;
    // }

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
                    System.out.println("Order ID: " + orderId);
                    Order foundOrder = findById(orderId);
                    System.out.println("Found order: " + foundOrder);
                    return foundOrder;
                }
            }
        } catch (SQLException e) {
            System.out.println("Save Order Failed: " + e.getMessage());
            return null;
        }
        return null;
    }

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

    private void setOrderProps(CreateOrderDto order, PreparedStatement stmt) throws SQLException {
        stmt.setInt(1, order.getCustomerId());
        stmt.setInt(2, order.getCoffeeId());
        stmt.setDouble(3, order.getQtyOrdered());
        stmt.setDouble(4, order.getTotal());
    }

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

    private int getRandomIndex(int[] array) {
        return (int) (Math.random() * array.length);
    }

    private int getRandomIndex(double[] array) {
        return (int) (Math.random() * array.length);
    }

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

    public void populateDatabase() {
        List<CreateOrderDto> orders = createInitialOrders();
        for (CreateOrderDto order : orders) {
            save(order);
        }
    }
}
