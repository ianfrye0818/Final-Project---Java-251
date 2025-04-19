package stores;

import java.util.List;

import Interfaces.IStore;
import entites.Coffee;

public class CoffeeStore implements IStore<Coffee> {
    private Coffee currentCoffee;
    private List<Runnable> listeners;

    @Override
    public void set(Coffee coffee) {
        this.currentCoffee = coffee;
        notifyListeners();
    }

    @Override
    public void clear() {
        this.currentCoffee = null;
        notifyListeners();
    }

    @Override
    public Coffee get() {
        return this.currentCoffee;
    }

    @Override
    public void addChangeListener(Runnable listener) {
        listeners.add(listener);
    }

    @Override
    public void removeChangeListener(Runnable listener) {
        listeners.remove(listener);
    }

    private void notifyListeners() {
        for (Runnable listener : listeners) {
            listener.run();
        }
    }
}
