package services;

import entites.Customer;
import java.sql.SQLException;
import java.util.List;

import Interfaces.ICustomerRepository;
import controllers.AppController;
import dto.CreateCustomerDto;
import dto.UpdateCustomerDto;

public class CustomerService {

    private final ICustomerRepository customerRepository;
    private final AppController controller;

    public CustomerService(ICustomerRepository customerRepository, AppController controller) {
        this.customerRepository = customerRepository;
        this.controller = controller;
    }

    public List<Customer> getAllCustomers() {
        return this.customerRepository.findAll();
    }

    public Customer getCustomerById(int id) {

        return this.customerRepository.findById(id);

    }

    public Customer getCustomerByEmail(String email) {

        return this.customerRepository.findByEmail(email);
    }

    public Customer createCustomer(CreateCustomerDto customer) {
        return this.customerRepository.save(customer);

    }

    public Customer updateCustomer(UpdateCustomerDto customer) {
        return this.customerRepository.update(customer);
    }

    public boolean deleteCustomer(int id) {
        return this.customerRepository.deleteById(id);
    }

    public void resetDatabase() throws SQLException {
        this.customerRepository.resetDatabase();
    }

    public void populateDatabase() throws SQLException {
        this.customerRepository.populateDatabase();
    }

    public void login(String email) {
        Customer customer = this.getCustomerByEmail(email);
        if (customer == null) {
            throw new RuntimeException("Invalid email or password");
        }
        this.controller.getLoggedinCustomerStore().set(customer);
    }

    public void logout() {
        this.controller.getLoggedinCustomerStore().clear();
    }

    public void register(CreateCustomerDto customer) {
        Customer newCustomer = this.createCustomer(customer);
        if (newCustomer != null) {
            this.login(customer.getEmail());
        } else {
            throw new RuntimeException("Failed to create customer");
        }
    }

}
