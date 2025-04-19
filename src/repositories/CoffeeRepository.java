package repositories;

import Interfaces.ICoffeeRepository;
import dto.CreateCoffeeDto;
import dto.UpdateCoffeeDto;
import entites.Coffee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoffeeRepository implements ICoffeeRepository {

    private final Connection connection;

    public CoffeeRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void initTable() throws SQLException {
        resetDatabase();
        populateDatabase();
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
    public Coffee save(CreateCoffeeDto coffee) {
        String sql = """
                INSERT INTO COFFEE(COFFEE_NAME, DESCRIPTION, PRICE, IS_IN_STOCK)
                VALUES (?, ?, ?, ?)
                """;

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setCoffeeProps(coffee, stmt);
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int coffeeId = generatedKeys.getInt(1);
                    return findById(coffeeId);
                }
            }
        } catch (SQLException e) {
            System.out.println("Save Coffee Failed: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Coffee update(CreateCoffeeDto coffee) {
        if (!(coffee instanceof UpdateCoffeeDto)) {
            throw new IllegalArgumentException("Coffee must be an instance of UpdateCoffeeDto");
        }
        UpdateCoffeeDto updateCoffee = (UpdateCoffeeDto) coffee;
        String sql = """
                   UPDATE COFFEE
                   SET COFFEE_NAME = ?,
                   DESCRIPTION = ?,
                   PRICE = ?,
                   IS_IN_STOCK = ?
                   WHERE COFFEE_ID = ?
                """;
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setCoffeeProps(updateCoffee, stmt);
            stmt.setInt(5, updateCoffee.getCoffeeId());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int coffeeId = generatedKeys.getInt(1);
                    return findById(coffeeId);
                }
            }
        } catch (SQLException e) {
            System.out.println("Update Coffee Failed: " + e.getMessage());
            return null;
        }
        return null;
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
                .setName(rs.getString("COFFEE_NAME"))
                .setDescription(rs.getString("DESCRIPTION"))
                .setPrice(rs.getDouble("PRICE"))
                .setInStock(rs.getBoolean("IS_IN_STOCK"))
                .build();
    }

    private void setCoffeeProps(CreateCoffeeDto coffee, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, coffee.getName());
        stmt.setString(2, coffee.getDescription());
        stmt.setDouble(3, coffee.getPrice());
        stmt.setBoolean(4, coffee.isInStock());
    }

    private List<CreateCoffeeDto> createInitialMenu() {
        List<CreateCoffeeDto> menu = new ArrayList<>();
        menu.add(new CreateCoffeeDto.Builder()
                .setName("Latte")
                .setDescription("A latte is a coffee drink made with espresso and steamed milk.")
                .setPrice(3.50)
                .setInStock(true)
                .build());
        menu.add(new CreateCoffeeDto.Builder()
                .setName("Cappuccino")
                .setDescription("A cappuccino is a coffee drink made with espresso and steamed milk.")
                .setPrice(4.00)
                .setInStock(true)
                .build());
        menu.add(new CreateCoffeeDto.Builder()
                .setName("Americano")
                .setDescription("An americano is a coffee drink made with espresso and water.")
                .setPrice(2.50)
                .setInStock(true)
                .build());
        menu.add(new CreateCoffeeDto.Builder()
                .setName("Espresso")
                .setDescription("An espresso is a coffee drink made with espresso and water.")
                .setPrice(2.00)
                .setInStock(true)
                .build());

        menu.add(new CreateCoffeeDto.Builder()
                .setName("Mocha")
                .setDescription("A mocha is a coffee drink made with espresso, chocolate, and steamed milk.")
                .setPrice(3.00)
                .setInStock(true)
                .build());

        menu.add(new CreateCoffeeDto.Builder()
                .setName("Macchiato")
                .setDescription("A macchiato is a coffee drink made with espresso and steamed milk.")
                .setPrice(2.50)
                .setInStock(true)
                .build());
        return menu;
    }

    @Override
    public void resetDatabase() throws SQLException {
        String dropSQL = "DROP TABLE COFFEE";
        String createSQL = """
                CREATE TABLE COFFEE (
                  COFFEE_ID          INTEGER   GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) CONSTRAINT PK_COFFEE_ID PRIMARY KEY,
                  COFFEE_NAME        VARCHAR(255) NOT NULL,
                  DESCRIPTION        VARCHAR(255) NOT NULL,
                  PRICE              DOUBLE NOT NULL,
                  IS_IN_STOCK        BOOLEAN DEFAULT TRUE NOT NULL
                )
                """;
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(dropSQL);
        } catch (SQLException ex) {
            System.out.println("Failed to drop table COFFEE: " + ex.getMessage());
        }
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(createSQL);
        } catch (SQLException ex) {
            System.out.println("Failed to create table COFFEE: " + ex.getMessage());
        }
    }

    @Override
    public void populateDatabase() throws SQLException {
        List<CreateCoffeeDto> menu = createInitialMenu();
        for (CreateCoffeeDto coffee : menu) {
            save(coffee);
        }
    }

}
