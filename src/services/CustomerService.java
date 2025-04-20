package services;

import Interfaces.ICustomerRepository;
import Interfaces.ICustomerService;
import dto.CreateCustomerDto;
import dto.UpdateCustomerDto;
import entites.Customer;
import stores.AuthStore;

import java.sql.SQLException;
import java.util.List;

/**
 * Implements the {@link ICustomerService} interface to provide business logic
 * for managing {@link Customer} entities, including CRUD operations, login,
 * logout, and registration. This service acts as an intermediary between the
 * application's controllers and the {@link ICustomerRepository}, delegating
 * data access operations to the repository and managing authentication state.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */

public class CustomerService implements ICustomerService {

    private final ICustomerRepository customerRepository;

    /**
     * Constructs a new {@code CustomerService} with the specified customer
     * repository.
     *
     * @param customerRepository The {@link ICustomerRepository} to be used for data
     *                           access.
     */
    public CustomerService(ICustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Retrieves all {@link Customer} entities from the data source. This method
     * delegates the operation to the {@link ICustomerRepository#findAll()} method.
     *
     * @return A {@code List} of all {@link Customer} entities.
     */
    @Override
    public List<Customer> getAllCustomers() {
        return this.customerRepository.findAll();
    }

    /**
     * Retrieves a specific {@link Customer} entity by its ID. This method delegates
     * the operation to the {@link ICustomerRepository#findById(int)} method.
     *
     * @param id The ID of the customer to retrieve.
     * @return The {@link Customer} entity with the specified ID, or {@code null} if
     *         not found.
     */
    @Override
    public Customer getCustomerById(int id) {
        return this.customerRepository.findById(id);
    }

    /**
     * Retrieves a specific {@link Customer} entity by their email address. This
     * method delegates the operation to the
     * {@link ICustomerRepository#findByEmail(String)} method.
     *
     * @param email The email address of the customer to retrieve.
     * @return The {@link Customer} entity with the specified email address, or
     *         {@code null} if not found.
     */
    @Override
    public Customer getCustomerByEmail(String email) {
        return this.customerRepository.findByEmail(email);
    }

    /**
     * Creates a new {@link Customer} entity using the provided
     * {@link CreateCustomerDto}.
     * This method delegates the save operation to the
     * {@link ICustomerRepository#save(CreateCustomerDto)} method.
     *
     * @param customer The {@link CreateCustomerDto} containing the details of the
     *                 customer to create.
     * @return The newly created {@link Customer} entity.
     */
    @Override
    public Customer createCustomer(CreateCustomerDto customer) {
        return this.customerRepository.save(customer);
    }

    /**
     * Updates an existing {@link Customer} entity using the provided
     * {@link UpdateCustomerDto}. This method delegates the update operation to the
     * {@link ICustomerRepository#update(CreateCustomerDto)} method.
     *
     * @param customer The {@link UpdateCustomerDto} containing the updated details
     *                 of the customer.
     * @return The updated {@link Customer} entity.
     */
    @Override
    public Customer updateCustomer(UpdateCustomerDto customer) {
        return this.customerRepository.update(customer);
    }

    /**
     * Deletes a {@link Customer} entity by its ID. This method delegates the delete
     * operation to the {@link ICustomerRepository#deleteById(int)} method.
     *
     * @param id The ID of the customer to delete.
     * @return {@code true} if the customer was successfully deleted; {@code false}
     *         otherwise.
     */
    @Override
    public boolean deleteCustomer(int id) {
        return this.customerRepository.deleteById(id);
    }

    /**
     * Resets the underlying data store for customers. This method delegates the
     * operation to the {@link ICustomerRepository#resetDatabase()} method.
     *
     * @throws SQLException If a database error occurs during the reset operation.
     */
    @Override
    public void resetDatabase() throws SQLException {
        this.customerRepository.resetDatabase();
    }

    /**
     * Populates the underlying data store with initial customer data. This method
     * delegates the operation to the {@link ICustomerRepository#populateDatabase()}
     * method.
     *
     * @throws SQLException If a database error occurs during the population
     *                      operation.
     */
    @Override
    public void populateDatabase() throws SQLException {
        this.customerRepository.populateDatabase();
    }

    /**
     * Authenticates a customer by their email address. If a customer with the
     * provided email exists, their information is stored in the {@link AuthStore}.
     * If no such customer exists, a {@code RuntimeException} is thrown.
     *
     * @param email The email address of the customer attempting to log in.
     * @throws RuntimeException If no customer is found with the given email.
     */
    @Override
    public void login(String email) {
        Customer customer = this.getCustomerByEmail(email);
        if (customer == null) {
            throw new RuntimeException("Invalid email or password");
        }
        AuthStore.getInstance().set(customer);
    }

    /**
     * Clears the current authentication state by clearing the instance of the
     * {@link AuthStore}, effectively logging out the current user.
     */
    @Override
    public void logout() {
        AuthStore.getInstance().clear();
    }

    /**
     * Registers a new customer using the provided {@link CreateCustomerDto}. If
     * the customer is successfully created, they are automatically logged in. If
     * the customer creation fails, a {@code RuntimeException} is thrown.
     *
     * @param customer The {@link CreateCustomerDto} containing the registration
     *                 details.
     * @throws RuntimeException If the customer creation fails.
     */
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