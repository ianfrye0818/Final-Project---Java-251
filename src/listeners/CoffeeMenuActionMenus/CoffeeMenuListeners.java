package listeners.CoffeeMenuActionMenus;

import controllers.AppController;
import enums.ViewType;

import java.awt.event.ActionListener;

/**
 * Provides {@code ActionListener} implementations for actions within the main
 * coffee menu view. Currently, it only provides a listener for the "Add New
 * Coffee"
 * button.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */
public class CoffeeMenuListeners {
    private final AppController controller;

    /**
     * Constructs a new {@code CoffeeMenuListeners} with the specified application
     * controller.
     *
     * @param controller The application controller providing access to navigation.
     */
    public CoffeeMenuListeners(AppController controller) {
        this.controller = controller;
    }

    /**
     * Returns an {@code ActionListener} that navigates the application to the
     * view for creating a new coffee. This listener is typically attached to an
     * "Add New Coffee" button in the coffee menu.
     *
     * @return An {@code ActionListener} for navigating to the create coffee view.
     */
    public ActionListener getAddNewCoffeeButtonListener() {
        return e -> controller.setDisplay(ViewType.CREATE_COFFEE_VIEW);
    }
}