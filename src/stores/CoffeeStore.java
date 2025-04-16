package stores;

import Interfaces.IStore;
import models.Coffee;

public class CoffeeStore implements IStore<Coffee> {
    private Coffee currentCoffee = new Coffee.Builder().setCoffeeId(1).setCoffeeName("Coffee")
            .setCoffeeDescription("Coffee").setPrice(1.00).setInStock(true).build();

    @Override
    public void set(Coffee coffee) {
        this.currentCoffee = coffee;
    }

    @Override
    public void clear() {
        this.currentCoffee = null;
    }

    @Override
    public Coffee get() {
        return this.currentCoffee;
    }
}
