package Interfaces;

import dto.CreateCustomerDto;
import dto.UpdateCustomerDto;
import entites.Customer;

import java.sql.SQLException;
import java.util.List;

/**
 * An interface for a service that manages customer entities.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */

public interface ICustomerService {
    List<Customer> getAllCustomers();

    Customer getCustomerById(int id);

    Customer getCustomerByEmail(String email);

    Customer createCustomer(CreateCustomerDto customer);

    Customer updateCustomer(UpdateCustomerDto customer);

    boolean deleteCustomer(int id);

    void resetDatabase() throws SQLException;

    void populateDatabase() throws SQLException;

    void login(String email);

    void logout();

    void register(CreateCustomerDto customer);
}
