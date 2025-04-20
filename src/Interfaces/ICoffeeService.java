package Interfaces;

import dto.CreateCoffeeDto;
import dto.UpdateCoffeeDto;
import entites.Coffee;

import java.sql.SQLException;
import java.util.List;

/**
 * An interface for a service that manages coffee entities.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */

public interface ICoffeeService {
    List<Coffee> getAllCoffees();

    Coffee getCoffeeById(int id);

    Coffee createCoffee(CreateCoffeeDto coffee);

    Coffee updateCoffee(UpdateCoffeeDto coffee);

    boolean deleteCoffee(int id);

    void resetDatabase() throws SQLException;

    void populateDatabase() throws SQLException;
}
