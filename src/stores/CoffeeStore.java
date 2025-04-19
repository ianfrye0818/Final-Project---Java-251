package stores;

import Interfaces.IStore;
import entites.Coffee;

public class CoffeeStore implements IStore<Coffee> {
    private Coffee currentCoffee;

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
