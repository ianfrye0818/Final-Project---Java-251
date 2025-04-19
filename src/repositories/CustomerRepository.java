package repositories;

import Interfaces.ICustomerRepository;
import dto.CreateCustomerDto;
import dto.UpdateCustomerDto;
import entites.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository implements ICustomerRepository {

    private final Connection connection;

    public CustomerRepository(Connection conn) {
        this.connection = conn;
    }

    @Override
    public void initTable() throws SQLException {
        resetDatabase();
        populateDatabase();
    }

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
            // Do nothing here we will just return an empty list.
        }
        return customers;
    }

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
            // try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            // if (generatedKeys.next()) {
            // int customerId = generatedKeys.getInt(1);
            // Customer cust = findById(customerId);
            // System.out.println("Customer in reposiotry: " + cust)
            // return cust;
            // }
            // }
        } catch (SQLException e) {
            System.out.println("Save Customer Failed: " + e.getMessage());
            return null;
        }

    }

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

    @Override
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

    @Override
    public void populateDatabase() throws SQLException {
        List<CreateCustomerDto> customers = generateInitialCustomers();
        for (CreateCustomerDto customer : customers) {
            save(customer);
        }
    }

}
