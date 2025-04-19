package services;

import entites.Coffee;
import java.sql.SQLException;
import java.util.List;
import Interfaces.ICoffeeRepository;
import dto.CreateCoffeeDto;
import dto.UpdateCoffeeDto;

public class CoffeeService {
    private final ICoffeeRepository coffeeRepository;

    public CoffeeService(ICoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }

    public List<Coffee> getAllCoffees() {
        return this.coffeeRepository.findAll();
    }

    public Coffee getCoffeeById(int id) {
        return this.coffeeRepository.findById(id);
    }

    public Coffee createCoffee(CreateCoffeeDto coffee) {
        return this.coffeeRepository.save(coffee);
    }

    public Coffee updateCoffee(UpdateCoffeeDto coffee) {
        return this.coffeeRepository.update(coffee);
    }

    public boolean deleteCoffee(int id) {
        return this.coffeeRepository.deleteById(id);
    }

    public void resetDatabase() throws SQLException {
        this.coffeeRepository.resetDatabase();
    }

    public void populateDatabase() throws SQLException {
        this.coffeeRepository.populateDatabase();
    }
}