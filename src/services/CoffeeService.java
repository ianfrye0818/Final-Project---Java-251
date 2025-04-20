package services;

import Interfaces.ICoffeeRepository;
import Interfaces.ICoffeeService;
import dto.CreateCoffeeDto;
import dto.UpdateCoffeeDto;
import entites.Coffee;

import java.sql.SQLException;
import java.util.List;

public class CoffeeService implements ICoffeeService {
    private final ICoffeeRepository coffeeRepository;

    public CoffeeService(ICoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }

    @Override
    public List<Coffee> getAllCoffees() {
        return this.coffeeRepository.findAll();
    }

    @Override
    public Coffee getCoffeeById(int id) {
        return this.coffeeRepository.findById(id);
    }

    @Override
    public Coffee createCoffee(CreateCoffeeDto coffee) {
        return this.coffeeRepository.save(coffee);
    }

    @Override
    public Coffee updateCoffee(UpdateCoffeeDto coffee) {
        return this.coffeeRepository.update(coffee);
    }

    @Override
    public boolean deleteCoffee(int id) {
        return this.coffeeRepository.deleteById(id);
    }

    @Override
    public void resetDatabase() throws SQLException {
        this.coffeeRepository.resetDatabase();
    }

    @Override
    public void populateDatabase() throws SQLException {
        this.coffeeRepository.populateDatabase();
    }
}