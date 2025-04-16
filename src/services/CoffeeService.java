package services;

import controllers.AppController;
import models.Coffee;
import java.util.List;

public class CoffeeService {
    private final AppController controller;

    public CoffeeService(AppController controller) {
        this.controller = controller;
    }

    public List<Coffee> getAllCoffees() {
        return this.controller.getCoffeeRepository().findAll();
    }

    public Coffee getCoffeeById(int id) {
        return this.controller.getCoffeeRepository().findById(id);
    }

    public boolean createCoffee(Coffee coffee) {
        return this.controller.getCoffeeRepository().save(coffee);
    }

    public boolean updateCoffee(Coffee coffee) {
        return this.controller.getCoffeeRepository().update(coffee);
    }

    public boolean deleteCoffee(int id) {
        return this.controller.getCoffeeRepository().deleteById(id);

    }
}
