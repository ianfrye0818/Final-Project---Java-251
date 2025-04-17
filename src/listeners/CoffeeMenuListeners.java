package listeners;

import controllers.AppController;
import enums.ViewType;

import java.awt.event.ActionListener;

public class CoffeeMenuListeners {
    private final AppController controller;

    public CoffeeMenuListeners(AppController controller) {
        this.controller = controller;
    }

    public ActionListener getAddNewCoffeeButtonListener() {
        return e -> controller.setDisplay(ViewType.CREATE_COFFEE_VIEW);
    }
}
