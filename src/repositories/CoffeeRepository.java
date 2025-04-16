package repositories;

import Interfaces.IRepository;
import models.Coffee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoffeeRepository implements IRepository<Coffee> {

    private final Connection connection;

    public CoffeeRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void initTable() throws SQLException {
        String dropSql = "DROP TABLE COFFEE";
        String createSQL = """
                 CREATE TABLE COFFEE
                (
                    COFFEE_ID           INTEGER     GENERATED ALWAYS AS IDENTITY  (START WITH 1, INCREMENT BY 1  ) CONSTRAINT PK_COFFEE_ID PRIMARY KEY,
                    COFFEE_NAME         VARCHAR(255)                NOT NULL,
                    DESCRIPTION         VARCHAR(255)                NOT NULL,
                    PRICE               DOUBLE                      NOT NULL,
                    IS_IN_STOCK         BOOLEAN     DEFAULT TRUE    NOT NULL
                )
                """;

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(dropSql);
        } catch (SQLException ex) {
            System.out.println("Failed to drop table COFFEE: " + ex.getMessage());
            // Do nothing here since if it doesn't exist we will just create it.
        }

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createSQL);

            // Create initial menu
            List<Coffee> menu = createInitialMenu();
            for (Coffee coffee : menu) {
                save(coffee);
            }
        }
    }

    @Override
    public List<Coffee> findAll() {
        String sql = """
                    SELECT * FROM COFFEE ORDER BY COFFEE_ID
                """;
        List<Coffee> coffees = new ArrayList<>();

        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Coffee coffee = mapToCoffee(rs);
                coffees.add(coffee);

            }
            return coffees;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            // Do nothing here since if it doesn't exist we will just create it
        }
        return coffees;
    }

    @Override
    public Coffee findById(int coffeeId) {
        String sql = """
                    SELECT * FROM COFFEE WHERE COFFEE_ID = ?
                """;

        Coffee coffee = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, coffeeId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                coffee = mapToCoffee(rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            // Do nothing here since we will just return a null obj.
        }

        return coffee;
    }

    @Override
    public boolean save(Coffee coffee) {
        String sql = """
                INSERT INTO COFFEE(COFFEE_NAME, DESCRIPTION, PRICE, IS_IN_STOCK)
                VALUES (?, ?, ?, ?)
                """;

        return saveCoffee(coffee, sql);
    }

    @Override
    public boolean update(Coffee coffee) {
        String sql = """
                   UPDATE COFFEE
                   SET COFFEE_NAME = ?,
                   DESCRIPTION = ?,
                   PRICE = ?,
                   IS_IN_STOCK = ?
                   WHERE COFFEE_ID = ?
                """;
        return saveCoffee(coffee, sql);
    }

    @Override
    public boolean deleteById(int coffeeId) {
        String sql = """
                    DELETE FROM COFFEE WHERE COFFEE_ID = ?
                """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, coffeeId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Delete by Id Failed: " + e.getMessage());
            return false;
        }

        return true;
    }

    private Coffee mapToCoffee(ResultSet rs) throws SQLException {
        return new Coffee.Builder()
                .setCoffeeId(rs.getInt("COFFEE_ID"))
                .setCoffeeName(rs.getString("COFFEE_NAME"))
                .setCoffeeDescription(rs.getString("DESCRIPTION"))
                .setPrice(rs.getDouble("PRICE"))
                .setInStock(rs.getBoolean("IS_IN_STOCK"))
                .build();
    }

    private boolean saveCoffee(Coffee coffee, String sql) {
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, coffee.getCoffeeName());
            stmt.setString(2, coffee.getCoffeeDescription());
            stmt.setDouble(3, coffee.getPrice());
            stmt.setBoolean(4, coffee.getIsInStock());
            int result = stmt.executeUpdate();

            return result > 0;
        } catch (SQLException e) {
            System.out.println("Save Coffee Failed: " + e.getMessage());
            return false;
        }
    }

    private List<Coffee> createInitialMenu() {
        List<Coffee> menu = new ArrayList<>();
        menu.add(new Coffee.Builder()
                .setCoffeeId(1)
                .setCoffeeName("Latte")
                .setCoffeeDescription("A latte is a coffee drink made with espresso and steamed milk.")
                .setPrice(3.50)
                .setInStock(true)
                .build());
        menu.add(new Coffee.Builder()
                .setCoffeeId(2)
                .setCoffeeName("Cappuccino")
                .setCoffeeDescription("A cappuccino is a coffee drink made with espresso and steamed milk.")
                .setPrice(4.00)
                .setInStock(true)
                .build());
        menu.add(new Coffee.Builder()
                .setCoffeeId(3)
                .setCoffeeName("Americano")
                .setCoffeeDescription("An americano is a coffee drink made with espresso and water.")
                .setPrice(2.50)
                .setInStock(true)
                .build());
        menu.add(new Coffee.Builder()
                .setCoffeeId(4)
                .setCoffeeName("Espresso")
                .setCoffeeDescription("An espresso is a coffee drink made with espresso and water.")
                .setPrice(2.00)
                .setInStock(true)
                .build());

        menu.add(new Coffee.Builder()
                .setCoffeeId(5)
                .setCoffeeName("Mocha")
                .setCoffeeDescription("A mocha is a coffee drink made with espresso, chocolate, and steamed milk.")
                .setPrice(3.00)
                .setInStock(true)
                .build());

        menu.add(new Coffee.Builder()
                .setCoffeeId(6)
                .setCoffeeName("Macchiato")
                .setCoffeeDescription("A macchiato is a coffee drink made with espresso and steamed milk.")
                .setPrice(2.50)
                .setInStock(true)
                .build());
        return menu;
    }

}
