package repositories;

import Interfaces.ICoffeeRepository;
import dto.CreateCoffeeDto;
import dto.UpdateCoffeeDto;
import entites.Coffee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements the {@link ICoffeeRepository} interface to provide data access
 * operations for {@link Coffee} entities. This repository interacts with the
 * 'COFFEE' table in the database to perform CRUD operations and table
 * initialization.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */
public class CoffeeRepository implements ICoffeeRepository {

    private final Connection connection;

    /**
     * Constructs a new {@code CoffeeRepository} with the specified database
     * connection.
     *
     * @param connection The {@link Connection} to the database.
     */
    public CoffeeRepository(Connection connection) {
        this.connection = connection;
    }

    /**
     * Initializes the 'COFFEE' table in the database by first dropping it if it
     * exists
     * and then recreating it. After creation, it populates the table with initial
     * coffee menu items.
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
     * Retrieves all {@link Coffee} entities from the 'COFFEE' table, ordered by
     * their ID.
     *
     * @return A {@code List} of all {@link Coffee} entities in the table, or an
     *         empty list
     *         if no coffees exist or if a database error occurs.
     */
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

    /**
     * Retrieves a {@link Coffee} entity from the 'COFFEE' table based on its ID.
     *
     * @param coffeeId The ID of the coffee to retrieve.
     * @return The {@link Coffee} entity with the specified ID, or {@code null} if
     *         no
     *         such coffee exists or if a database error occurs.
     */
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

    /**
     * Saves a new coffee to the 'COFFEE' table using the provided
     * {@link CreateCoffeeDto}.
     * Upon successful insertion, it retrieves and returns the newly created
     * {@link Coffee}
     * entity, including its generated ID.
     *
     * @param coffee The {@link CreateCoffeeDto} containing the details of the
     *               coffee to save.
     * @return The saved {@link Coffee} entity, or {@code null} if the save
     *         operation fails.
     */
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

    /**
     * Updates an existing coffee in the 'COFFEE' table using the provided
     * {@link CreateCoffeeDto}. The {@code coffee} object must be an instance of
     * {@link UpdateCoffeeDto} to include the coffee's ID for the WHERE clause.
     * Upon successful update, it retrieves and returns the updated {@link Coffee}
     * entity.
     *
     * @param coffee The {@link CreateCoffeeDto} (must be {@link UpdateCoffeeDto})
     *               containing the updated details of the coffee.
     * @return The updated {@link Coffee} entity, or {@code null} if the update
     *         operation fails
     *         or if the provided DTO is not an instance of {@link UpdateCoffeeDto}.
     * @throws IllegalArgumentException If the provided {@code coffee} is not an
     *                                  instance of {@link UpdateCoffeeDto}.
     */
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

    /**
     * Deletes a coffee from the 'COFFEE' table based on its ID.
     *
     * @param coffeeId The ID of the coffee to delete.
     * @return {@code true} if the deletion was successful; {@code false} otherwise
     *         or if a database error occurs.
     */
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

    /**
     * Maps a row from the {@link ResultSet} to a {@link Coffee} entity.
     *
     * @param rs The {@link ResultSet} containing the data for a coffee.
     * @return A new {@link Coffee} entity populated with data from the result set.
     * @throws SQLException If an error occurs while accessing the result set.
     */
    private Coffee mapToCoffee(ResultSet rs) throws SQLException {
        return new Coffee.Builder()
                .setCoffeeId(rs.getInt("COFFEE_ID"))
                .setName(rs.getString("COFFEE_NAME"))
                .setDescription(rs.getString("DESCRIPTION"))
                .setPrice(rs.getDouble("PRICE"))
                .setInStock(rs.getBoolean("IS_IN_STOCK"))
                .build();
    }

    /**
     * Sets the properties of a coffee in a {@link PreparedStatement} based on the
     * data from a {@link CreateCoffeeDto}.
     *
     * @param coffee The {@link CreateCoffeeDto} containing the coffee's properties.
     * @param stmt   The {@link PreparedStatement} to set the properties on.
     * @throws SQLException If an error occurs while setting the statement
     *                      parameters.
     */
    private void setCoffeeProps(CreateCoffeeDto coffee, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, coffee.getName());
        stmt.setString(2, coffee.getDescription());
        stmt.setDouble(3, coffee.getPrice());
        stmt.setBoolean(4, coffee.isInStock());
    }

    /**
     * Creates a list of initial {@link CreateCoffeeDto} objects representing the
     * default coffee menu.
     *
     * @return A {@code List} of {@link CreateCoffeeDto} objects for initial coffee
     *         items.
     */
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

    /**
     * Drops the 'COFFEE' table from the database if it exists. Any errors during
     * the drop operation are logged but do not halt the execution.
     *
     * @throws SQLException If a database error occurs during the drop operation.
     */
    @Override
    public void resetDatabase() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            // First disable foreign key constraints
            stmt.execute("SET REFERENTIAL_INTEGRITY FALSE");

            // Drop the table
            stmt.executeUpdate("DROP TABLE COFFEE");

            // Re-enable foreign key constraints
            stmt.execute("SET REFERENTIAL_INTEGRITY TRUE");

            // Create the table
            createTable();
        } catch (SQLException ex) {
            System.out.println("Failed to reset COFFEE table: " + ex.getMessage());
            try {
                // Make sure to re-enable constraints even if there's an error
                connection.createStatement().execute("SET REFERENTIAL_INTEGRITY TRUE");
            } catch (SQLException e) {
                System.out.println("Failed to re-enable referential integrity: " + e.getMessage());
            }
            throw ex;
        }
    }

    /**
     * Creates the 'COFFEE' table in the database with the necessary columns and
     * constraints.
     * Any errors during the create operation are logged and rethrown as an
     * SQLException.
     *
     * @throws SQLException If a database error occurs during the table creation.
     */
    private void createTable() throws SQLException {
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
            stmt.executeUpdate(createSQL);
        } catch (SQLException ex) {
            System.out.println("Failed to create table COFFEE: " + ex.getMessage());
            throw ex; // Re-throw the exception to be handled by the caller
        }
    }

    /**
     * Populates the 'COFFEE' table with the initial coffee menu items defined in
     * {@link #createInitialMenu()}.
     *
     * @throws SQLException If a database error occurs during the save operation for
     *                      any coffee item.
     */
    @Override
    public void populateDatabase() throws SQLException {
        List<CreateCoffeeDto> menu = createInitialMenu();
        for (CreateCoffeeDto coffee : menu) {
            save(coffee);
        }
    }

}