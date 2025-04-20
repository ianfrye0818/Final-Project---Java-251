package Interfaces;

import dto.CreateCustomerDto;
import dto.UpdateCustomerDto;
import entites.Customer;

import java.sql.SQLException;
import java.util.List;

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
