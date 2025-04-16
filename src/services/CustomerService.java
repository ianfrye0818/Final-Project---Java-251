package services;

import controllers.AppController;
import dto.ValidateCreateAccountDto;
import exceptions.NotFoundException;
import models.Customer;

import java.util.List;

public class CustomerService {

    private final AppController controller;

    public CustomerService(AppController controller) {
        this.controller = controller;
    }

    public Customer getCurrentCustomer() {
        return this.controller.getCustomerStore().get();
    }

    public List<Customer> getAllCustomers() {
        return this.controller.getCustomerRepository().findAll();
    }

    public Customer getCustomerById(int id) {

        Customer customer = this.controller.getCustomerRepository().findById(id);
        if (customer == null) {
            throw new NotFoundException("Customer with id " + id + " not found");
        }

        return customer;
    }

    public Customer getCustomerByEmail(String email) {

        Customer customer = this.controller.getCustomerRepository().findByEmail(email);
        if (customer == null) {
            throw new NotFoundException("Customer not found");
        }

        return customer;
    }

    public boolean createCustomer(ValidateCreateAccountDto dto) {
        Customer customer = this.mapDtoToCustomer(dto);
        return this.controller.getCustomerRepository().save(customer);

    }

    public boolean updateCustomer(ValidateCreateAccountDto dto) {
        Customer customer = this.mapDtoToCustomer(dto);
        return this.controller.getCustomerRepository().update(customer);
    }

    public boolean deleteCustomer(int id) {
        return this.controller.getCustomerRepository().deleteById(id);
    }

    private Customer mapDtoToCustomer(ValidateCreateAccountDto dto) {
        return new Customer.Builder()
                .setFirstName(dto.getFirstName())
                .setLastName(dto.getLastName())
                .setStreet(dto.getStreet())
                .setCity(dto.getCity())
                .setState(dto.getState())
                .setZip(dto.getZip())
                .setPhone(dto.getPhone())
                .setEmail(dto.getEmail())
                .setCreditLimit(50)
                .build();
    }
}
