package repositories;

import Interfaces.ICustomerRepository;
import models.Customer;

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
        String dropSql = "DROP TABLE CUSTOMER";
        String createSql = """
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
                CREDIT_LIMIT        FLOAT           NOT NULL
                )
                """;

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(dropSql);
        } catch (SQLException ex) {
            System.out.println("Failed to drop table CUSTOMER: " + ex.getMessage());
        }

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(createSql);
            seed();
        }
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
                SELECT * FROM CUSTOMER WHERE EMAIL_ADDRESS = ?
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
    public boolean save(Customer customer) {
        String sql = """
                INSERT INTO CUSTOMER (FIRST_NAME, LAST_NAME, STREET, CITY, STATE, ZIP, EMAIL_ADDRESS, PHONE_NUMBER, CREDIT_LIMIT)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        return saveCustomer(customer, sql);

    }

    @Override
    public boolean update(Customer customer) {
        String sql = """
                UPDATE CUSTOMER
                SET FIRST_NAME = ?,
                LAST_NAME = ?,
                STREET = ?,
                CITY = ?,
                STATE = ?,
                ZIP = ?,
                EMAIL_ADDRESS = ?,
                PHONE = ?,
                CREDIT_LIMIT = ?
                WHERE CUSTOMER_ID = ?
                """;
        return saveCustomer(customer, sql);
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

    private boolean saveCustomer(Customer customer, String sql) {
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setString(3, customer.getStreet());
            stmt.setString(4, customer.getCity());
            stmt.setString(5, customer.getState());
            stmt.setString(6, customer.getZip());
            stmt.setString(7, customer.getEmail());
            stmt.setString(8, customer.getPhone());
            stmt.setDouble(9, customer.getCreditLimit());
            int result = stmt.executeUpdate();

            return result > 0;
        } catch (SQLException e) {
            System.out.println("Save Customer Failed: " + e.getMessage());
            return false;
        }
    }

    private void seed() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer.Builder()
                .setFirstName("Ian")
                .setLastName("Frye")
                .setStreet("123 Main St")
                .setCity("Anytown")
                .setState("CA")
                .setZip("12345")
                .setEmail("ianfrye@gmail.com")
                .setPhone("3368307157")
                .setCreditLimit(100.00)
                .build());

        customers.add(new Customer.Builder()
                .setFirstName("Jane")
                .setLastName("Doe")
                .setStreet("456 Main St")
                .setCity("Anytown")
                .setState("CA")
                .setZip("12345")
                .setEmail("jane.doe@example.com")
                .setPhone("3368307157")
                .setCreditLimit(100.00)
                .build());

        customers.add(new Customer.Builder()
                .setFirstName("Jim")
                .setLastName("Beam")
                .setStreet("789 Main St")
                .setCity("Anytown")
                .setState("CA")
                .setZip("12345")
                .setEmail("jim.beam@example.com")
                .setPhone("3368307157")
                .setCreditLimit(100.00)
                .build());

        for (Customer customer : customers) {
            save(customer);
        }
    }

}
