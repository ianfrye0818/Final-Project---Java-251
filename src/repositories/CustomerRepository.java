package repositories;

import Interfaces.ICustomerRepository;
import dto.CreateCustomerDto;
import dto.UpdateCustomerDto;
import entites.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements the {@link ICustomerRepository} interface to provide data access
 * operations for {@link Customer} entities. This repository interacts with the
 * 'CUSTOMER' table in the database to perform CRUD operations and table
 * initialization.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */
public class CustomerRepository implements ICustomerRepository {

    private final Connection connection;

    /**
     * Constructs a new {@code CustomerRepository} with the specified database
     * connection.
     *
     * @param conn The {@link Connection} to the database.
     */
    public CustomerRepository(Connection conn) {
        this.connection = conn;
    }

    /**
     * Initializes the 'CUSTOMER' table in the database by first dropping it if it
     * exists
     * and then recreating it. After creation, it populates the table with initial
     * customer data.
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
     * Retrieves all {@link Customer} entities from the 'CUSTOMER' table, ordered by
     * their ID.
     *
     * @return A {@code List} of all {@link Customer} entities in the table, or an
     *         empty list
     *         if no customers exist or if a database error occurs.
     */
    @Override
    public List<Customer> findAll() {
        String sql = """
                  SELECT * FROM CUSTOMER ORDER BY CUSTOMER_ID
                """;

        List<Customer> customers = new ArrayList<>();

        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Customer customer = mapToCustomer(rs);
                customers.add(customer);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            // Do nothing here we will just return an empty list.
        }
        return customers;
    }

    /**
     * Retrieves a {@link Customer} entity from the 'CUSTOMER' table based on its
     * ID.
     *
     * @param id The ID of the customer to retrieve.
     * @return The {@link Customer} entity with the specified ID, or {@code null} if
     *         no
     *         such customer exists or if a database error occurs.
     */
    @Override
    public Customer findById(int id) {
        String sql = """
                SELECT * FROM CUSTOMER WHERE CUSTOMER_ID = ?
                """;
        Customer customer = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                customer = mapToCustomer(rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            // Do nothing here since we will just return a null obj.
        }

        return customer;
    }

    /**
     * Retrieves a {@link Customer} entity from the 'CUSTOMER' table based on their
     * email address.
     * The comparison is case-insensitive.
     *
     * @param email The email address of the customer to retrieve.
     * @return The {@link Customer} entity with the specified email address, or
     *         {@code null} if no
     *         such customer exists or if a database error occurs.
     */
    @Override
    public Customer findByEmail(String email) {
        String sql = """
                SELECT * FROM CUSTOMER WHERE LOWER(EMAIL_ADDRESS) = LOWER(?)
                """;
        Customer customer = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                customer = mapToCustomer(rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            // Do nothing here since we will just return a null obj.
        }

        return customer;
    }

    /**
     * Saves a new customer to the 'CUSTOMER' table using the provided
     * {@link CreateCustomerDto}.
     * Upon successful insertion, it retrieves and returns the newly created
     * {@link Customer}
     * entity, including its generated ID.
     *
     * @param customer The {@link CreateCustomerDto} containing the details of the
     *                 customer to save.
     * @return The saved {@link Customer} entity, or {@code null} if the save
     *         operation fails.
     */
    @Override
    public Customer save(CreateCustomerDto customer) {
        String sql = """
                INSERT INTO CUSTOMER (FIRST_NAME, LAST_NAME, STREET, CITY, STATE, ZIP, EMAIL_ADDRESS, PHONE_NUMBER, CREDIT_LIMIT)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setCustomerProps(customer, stmt);
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int customerId = generatedKeys.getInt(1);
                    return findById(customerId);
                }
            }
        } catch (SQLException e) {
            System.out.println("Save Customer Failed: " + e.getMessage());
            return null;
        }

        return null;

    }

    /**
     * Updates an existing customer in the 'CUSTOMER' table using the provided
     * {@link CreateCustomerDto}. The {@code customer} object must be an instance of
     * {@link UpdateCustomerDto} to include the customer's ID for the WHERE clause.
     * Upon successful update, it retrieves and returns the updated {@link Customer}
     * entity.
     *
     * @param customer The {@link CreateCustomerDto} (must be
     *                 {@link UpdateCustomerDto})
     *                 containing the updated details of the customer.
     * @return The updated {@link Customer} entity, or {@code null} if the update
     *         operation fails
     *         or if the provided DTO is not an instance of
     *         {@link UpdateCustomerDto}.
     * @throws IllegalArgumentException If the provided {@code customer} is not an
     *                                  instance of {@link UpdateCustomerDto}.
     */
    @Override
    public Customer update(CreateCustomerDto customer) {
        if (!(customer instanceof UpdateCustomerDto)) {
            throw new IllegalArgumentException("Customer must be an instance of UpdateCustomerDto");
        }
        UpdateCustomerDto updateCustomer = (UpdateCustomerDto) customer;
        String sql = """
                UPDATE CUSTOMER
                SET FIRST_NAME = ?,
                LAST_NAME = ?,
                STREET = ?,
                CITY = ?,
                STATE = ?,
                ZIP = ?,
                EMAIL_ADDRESS = ?,
                PHONE_NUMBER = ?,
                CREDIT_LIMIT = ?
                WHERE CUSTOMER_ID = ?
                """;
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setCustomerProps(updateCustomer, stmt);
            stmt.setInt(10, updateCustomer.getCustomerId());
            int result = stmt.executeUpdate();

            if (result == 0)
                throw new RuntimeException("Failed to update customer");

            return findById(updateCustomer.getCustomerId());
        } catch (SQLException e) {
            System.out.println("Save Customer Failed: " + e.getMessage());
            return null;
        }

    }

    /**
     * Deletes a customer from the 'CUSTOMER' table based on their ID.
     *
     * @param id The ID of the customer to delete.
     * @return {@code true} if the deletion was successful; {@code false} otherwise
     *         or if a database error occurs.
     */
    @Override
    public boolean deleteById(int id) {
        String sql = "DELETE FROM CUSTOMER WHERE CUSTOMER_ID = ?";

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
     * Maps a row from the {@link ResultSet} to a {@link Customer} entity.
     *
     * @param rs The {@link ResultSet} containing the data for a customer.
     * @return A new {@link Customer} entity populated with data from the result
     *         set.
     * @throws SQLException If an error occurs while accessing the result set.
     */
    private Customer mapToCustomer(ResultSet rs) throws SQLException {
        return new Customer.Builder()
                .setCustomerId(rs.getInt("CUSTOMER_ID"))
                .setFirstName(rs.getString("FIRST_NAME"))
                .setLastName(rs.getString("LAST_NAME"))
                .setStreet(rs.getString("STREET"))
                .setCity(rs.getString("CITY"))
                .setState(rs.getString("STATE"))
                .setZip(rs.getString("ZIP"))
                .setEmail(rs.getString("EMAIL_ADDRESS"))
                .setPhone(rs.getString("PHONE_NUMBER"))
                .setCreditLimit(rs.getFloat("CREDIT_LIMIT"))
                .build();
    }

    /**
     * Sets the properties of a customer in a {@link PreparedStatement} based on the
     * data from a {@link CreateCustomerDto}.
     *
     * @param customer The {@link CreateCustomerDto} containing the customer's
     *                 properties.
     * @param stmt     The {@link PreparedStatement} to set the properties on.
     * @throws SQLException If an error occurs while setting the statement
     *                      parameters.
     */
    private void setCustomerProps(CreateCustomerDto customer, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, customer.getFirstName());
        stmt.setString(2, customer.getLastName());
        stmt.setString(3, customer.getStreet());
        stmt.setString(4, customer.getCity());
        stmt.setString(5, customer.getState());
        stmt.setString(6, customer.getZip());
        stmt.setString(7, customer.getEmail());
        stmt.setString(8, customer.getPhone());
        stmt.setDouble(9, customer.getCreditLimit());
    }

    /**
     * Generates a list of initial {@link CreateCustomerDto} objects representing
     * some default customers for populating the database.
     *
     * @return A {@code List} of {@link CreateCustomerDto} objects for initial
     *         customers.
     */
    private List<CreateCustomerDto> generateInitialCustomers() {
        List<CreateCustomerDto> customers = new ArrayList<>();
        customers.add(new CreateCustomerDto.Builder()
                .setFirstName("Billy")
                .setLastName("Shower")
                .setStreet("123 Main St")
                .setCity("Anytown")
                .setState("CA")
                .setZip("12345")
                .setEmail("billy@gmail.com")
                .setPhone("3368307157")
                .setCreditLimit(10.00)
                .build());

        customers.add(new CreateCustomerDto.Builder()
                .setFirstName("Jane")
                .setLastName("Doe")
                .setStreet("456 Main St")
                .setCity("Anytown")
                .setState("CA")
                .setZip("12345")
                .setEmail("jane@gmail.com")
                .setPhone("3368307157")
                .setCreditLimit(50.00)
                .build());

        customers.add(new CreateCustomerDto.Builder()
                .setFirstName("John")
                .setLastName("Doe")
                .setStreet("789 Main St")
                .setCity("Anytown")
                .setState("CA")
                .setZip("12345")
                .setEmail("john@gmail.com")
                .setPhone("3368307157")
                .setCreditLimit(20.00)
                .build());

        return customers;
    }

    /**
     * Drops the 'CUSTOMER' table from the database if it exists.
     * Creates the 'CUSTOMER' table in the database with the necessary columns and
     * constraints.
     *
     * @throws SQLException If a database error occurs during the drop or create
     *                      operation.
     */
    public void resetDatabase() throws SQLException {
        String dropSQL = "DROP TABLE CUSTOMER";
        String createSQL = """
                CREATE TABLE CUSTOMER (
                  CUSTOMER_ID         INTEGER         GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) CONSTRAINT PK_CUSTOMER_ID PRIMARY KEY,
                  FIRST_NAME          VARCHAR(50)     NOT NULL,
                  LAST_NAME           VARCHAR(50)     NOT NULL,
                  STREET              VARCHAR(50)     NOT NULL,
                  CITY                VARCHAR(50)     NOT NULL,
                  STATE               VARCHAR(50)     NOT NULL,
                  ZIP                 VARCHAR(50)     NOT NULL,
                  EMAIL_ADDRESS       VARCHAR(50)     NOT NULL,
                  PHONE_NUMBER        VARCHAR(50)     NOT NULL,
                  CREDIT_LIMIT        DOUBLE          NOT NULL
                )
                """;
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(dropSQL);
        } catch (SQLException ex) {
            System.out.println("Failed to drop table CUSTOMER: " + ex.getMessage());
        }

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(createSQL);
        } catch (SQLException ex) {
            System.out.println("Failed to create table CUSTOMER: " + ex.getMessage());
        }
    }

    /**
     * Populates the 'CUSTOMER' table with the initial customer data defined in
     * {@link #generateInitialCustomers()}.
     *
     * @throws SQLException If a database error occurs during the save operation for
     *                      any customer.
     */
    @Override
    public void populateDatabase() throws SQLException {
        List<CreateCustomerDto> customers = generateInitialCustomers();
        for (CreateCustomerDto customer : customers) {
            save(customer);
        }
    }

}