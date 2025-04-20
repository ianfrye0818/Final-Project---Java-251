package services;

import Interfaces.ICustomerRepository;
import Interfaces.ICustomerService;
import dto.CreateCustomerDto;
import dto.UpdateCustomerDto;
import entites.Customer;
import stores.AuthStore;

import java.sql.SQLException;
import java.util.List;

public class CustomerService implements ICustomerService {

    private final ICustomerRepository customerRepository;

    public CustomerService(ICustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return this.customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(int id) {

        return this.customerRepository.findById(id);

    }

    @Override
    public Customer getCustomerByEmail(String email) {

        return this.customerRepository.findByEmail(email);
    }

    @Override
    public Customer createCustomer(CreateCustomerDto customer) {
        return this.customerRepository.save(customer);

    }

    @Override
    public Customer updateCustomer(UpdateCustomerDto customer) {
        return this.customerRepository.update(customer);
    }

    @Override
    public boolean deleteCustomer(int id) {
        return this.customerRepository.deleteById(id);
    }

    @Override
    public void resetDatabase() throws SQLException {
        this.customerRepository.resetDatabase();
    }

    @Override
    public void populateDatabase() throws SQLException {
        this.customerRepository.populateDatabase();
    }

    @Override
    public void login(String email) {
        Customer customer = this.getCustomerByEmail(email);
        if (customer == null) {
            throw new RuntimeException("Invalid email or password");
        }
        AuthStore.getInstance().set(customer);
    }

    @Override
    public void logout() {
        AuthStore.getInstance().clear();
    }

    @Override
    public void register(CreateCustomerDto customer) {
        Customer newCustomer = this.createCustomer(customer);
        if (newCustomer != null) {
            this.login(customer.getEmail());
        } else {
            throw new RuntimeException("Failed to create customer");
        }
    }

}
