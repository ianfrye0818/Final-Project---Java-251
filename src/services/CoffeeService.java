package services;

import Interfaces.ICoffeeRepository;
import Interfaces.ICoffeeService;
import dto.CreateCoffeeDto;
import dto.UpdateCoffeeDto;
import entites.Coffee;

import java.sql.SQLException;
import java.util.List;

/**
 * Implements the {@link ICoffeeService} interface to provide business logic
 * for managing {@link Coffee} entities. This service acts as an intermediary
 * between the application's controllers and the {@link ICoffeeRepository},
 * delegating data access operations to the repository.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */

public class CoffeeService implements ICoffeeService {
    private final ICoffeeRepository coffeeRepository;

    /**
     * Constructs a new {@code CoffeeService} with the specified coffee repository.
     *
     * @param coffeeRepository The {@link ICoffeeRepository} to be used for data
     *                         access.
     */
    public CoffeeService(ICoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }

    /**
     * Retrieves all {@link Coffee} entities from the data source. This method
     * delegates the operation to the {@link ICoffeeRepository#findAll()} method.
     *
     * @return A {@code List} of all {@link Coffee} entities.
     */
    @Override
    public List<Coffee> getAllCoffees() {
        return this.coffeeRepository.findAll();
    }

    /**
     * Retrieves a specific {@link Coffee} entity by its ID. This method delegates
     * the operation to the {@link ICoffeeRepository#findById(int)} method.
     *
     * @param id The ID of the coffee to retrieve.
     * @return The {@link Coffee} entity with the specified ID, or {@code null} if
     *         not found.
     */
    @Override
    public Coffee getCoffeeById(int id) {
        return this.coffeeRepository.findById(id);
    }

    /**
     * Creates a new {@link Coffee} entity using the provided
     * {@link CreateCoffeeDto}.
     * This method delegates the save operation to the
     * {@link ICoffeeRepository#save(CreateCoffeeDto)} method.
     *
     * @param coffee The {@link CreateCoffeeDto} containing the details of the
     *               coffee to create.
     * @return The newly created {@link Coffee} entity.
     */
    @Override
    public Coffee createCoffee(CreateCoffeeDto coffee) {
        return this.coffeeRepository.save(coffee);
    }

    /**
     * Updates an existing {@link Coffee} entity using the provided
     * {@link UpdateCoffeeDto}. This method delegates the update operation to the
     * {@link ICoffeeRepository#update(CreateCoffeeDto)} method.
     *
     * @param coffee The {@link UpdateCoffeeDto} containing the updated details of
     *               the coffee.
     * @return The updated {@link Coffee} entity.
     */
    @Override
    public Coffee updateCoffee(UpdateCoffeeDto coffee) {
        return this.coffeeRepository.update(coffee);
    }

    /**
     * Deletes a {@link Coffee} entity by its ID. This method delegates the delete
     * operation to the {@link ICoffeeRepository#deleteById(int)} method.
     *
     * @param id The ID of the coffee to delete.
     * @return {@code true} if the coffee was successfully deleted; {@code false}
     *         otherwise.
     */
    @Override
    public boolean deleteCoffee(int id) {
        return this.coffeeRepository.deleteById(id);
    }

    /**
     * Resets the underlying data store for coffees. This method delegates the
     * operation to the {@link ICoffeeRepository#resetDatabase()} method.
     *
     * @throws SQLException If a database error occurs during the reset operation.
     */
    @Override
    public void resetDatabase() throws SQLException {
        this.coffeeRepository.resetDatabase();
    }

    /**
     * Populates the underlying data store with initial coffee data. This method
     * delegates the operation to the {@link ICoffeeRepository#populateDatabase()}
     * method.
     *
     * @throws SQLException If a database error occurs during the population
     *                      operation.
     */
    @Override
    public void populateDatabase() throws SQLException {
        this.coffeeRepository.populateDatabase();
    }
}