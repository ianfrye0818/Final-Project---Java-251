package Interfaces;

import dto.CreateCoffeeDto;
import dto.UpdateCoffeeDto;
import entites.Coffee;

import java.sql.SQLException;
import java.util.List;

public interface ICoffeeService {
    List<Coffee> getAllCoffees();

    Coffee getCoffeeById(int id);

    Coffee createCoffee(CreateCoffeeDto coffee);

    Coffee updateCoffee(UpdateCoffeeDto coffee);

    boolean deleteCoffee(int id);

    void resetDatabase() throws SQLException;

    void populateDatabase() throws SQLException;
}
