package repositories;

import Interfaces.IOrderRepository;
import dto.OrderCoffeeDto;
import dto.OrderCustomerDto;
import models.Order;

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
            List<Order> orders = createInitialOrders();
            for (Order order : orders) {
                save(order);
            }
        }
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

    @Override
    public boolean save(Order obj) {
        String sql = """
                INSERT INTO COFFEE_ORDER (CUSTOMER_ID, COFFEE_ID, QUANTITY_ORDERED, TOTAL_PRICE)
                VALUES (?, ?, ?, ?)
                """;
        return saveOrder(obj, sql);

    }

    @Override
    public boolean update(Order obj) {

        String sql = """
                UPDATE COFFEE_ORDER
                SET CUSTOMER_ID = ?,
                COFFEE_ID = ?,
                QUANTITY_ORDERED = ?,
                TOTAL_PRICE = ?
                WHERE ORDER_ID = ?
                """;

        return saveOrder(obj, sql);

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
                .setNumberOrdered(rs.getDouble("QUANTITY_ORDERED"))
                .build();
    }

    private boolean saveOrder(Order order, String sql) {
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, order.getCustomer().getCustomerId());
            stmt.setInt(2, order.getCoffee().getCoffeeId());
            stmt.setDouble(3, order.getNumberOrdered());
            stmt.setDouble(4, order.getTotal());
            int result = stmt.executeUpdate();

            return result > 0;
        } catch (SQLException e) {
            System.out.println("Save Order Failed: " + e.getMessage());
            return false;
        }
    }

    private List<Order> createInitialOrders() {
        List<Order> orders = new ArrayList<>();
        int[] coffeeIds = { 1, 2, 3, 4, 5, 6 };
        int[] customerIds = { 1, 2, 3 };
        double[] quantities = { 1.0, 2.0, 3.0, 4.0, 5.0, 6.0 };
        double[] totalPrices = { 3.50, 4.00, 2.50, 2.00, 3.00, 2.50 };

        for (int i = 0; i < 10; i++) {
            int coffeeId = coffeeIds[getRandomIndex(coffeeIds)];
            int customerId = customerIds[getRandomIndex(customerIds)];
            double quantity = quantities[getRandomIndex(quantities)];
            double totalPrice = totalPrices[getRandomIndex(totalPrices)];
            String[] coffeeNames = { "Latte", "Cappuccino", "Americano", "Espresso", "Mocha", "Macchiato" };
            String[] customerNames = { "John Doe", "Jane Smith", "Jim Beam" };

            Order order = new Order.Builder()
                    .setCoffee(new OrderCoffeeDto.Builder().setCoffeeId(coffeeId)
                            .setCoffeeName(coffeeNames[getRandomIndex(coffeeNames)]).setPrice(totalPrice).build())
                    .setCustomer(new OrderCustomerDto.Builder().setCustomerId(customerId)
                            .setCustomerName(customerNames[getRandomIndex(customerNames)]).build())
                    .setNumberOrdered(quantity).setTotal(totalPrice).build();
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

    private int getRandomIndex(String[] array) {
        return (int) (Math.random() * array.length);
    }

}
