package listeners.CoffeeMenuActionMenus;

import Interfaces.ICoffeeService;
import Interfaces.ICustomerService;
import Interfaces.IOrderService;
import controllers.AppController;
import enums.ViewType;
import utils.DialogUtils;
import views.CoffeeMenuView;
import views.SuperView;

import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Provides {@code ActionListener} implementations for actions within the admin
 * menu
 * of the coffee menu view. These listeners handle viewing all customers,
 * viewing all
 * orders, clearing the database, and resetting (clearing and repopulating) the
 * database.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */

public class AdminMenuListeners {
    private final AppController controller;
    private final ICustomerService customerService;
    private final ICoffeeService coffeeService;
    private final IOrderService orderService;
    private final CoffeeMenuView coffeeMenuView;

    /**
     * Constructs a new {@code AdminMenuListeners} with the specified application
     * controller.
     * It retrieves instances of the customer, coffee, and order services from the
     * controller.
     *
     * @param controller The application controller providing access to services and
     *                   navigation.
     */
    public AdminMenuListeners(AppController controller, CoffeeMenuView coffeeMenuView) {
        this.controller = controller;
        this.customerService = controller.getCustomerService();
        this.coffeeService = controller.getCoffeeService();
        this.orderService = controller.getOrderService();
        this.coffeeMenuView = coffeeMenuView;
    }

    /**
     * Returns an {@code ActionListener} that navigates the application to the view
     * displaying all customers.
     *
     * @return An {@code ActionListener} for viewing all customers.
     */
    public ActionListener getViewAllCustomersListener() {
        return e -> controller.setDisplay(ViewType.VIEW_ALL_CUSTOMERS_VIEW);
    }

    /**
     * Returns an {@code ActionListener} that navigates the application to the view
     * displaying all orders.
     *
     * @return An {@code ActionListener} for viewing all orders.
     */
    public ActionListener getViewAllOrdersListener() {
        return e -> controller.setDisplay(ViewType.VIEW_ALL_ORDERS_VIEW);
    }

    /**
     * Returns an {@code ActionListener} that clears all data from the application's
     * databases (orders, customers, and coffees). It prompts the user for
     * confirmation
     * before proceeding. Upon successful clearing, it displays a success message
     * and
     * navigates back to the coffee menu view.
     *
     * @param parent The parent {@code SuperView} used for displaying dialogs.
     * @return An {@code ActionListener} for clearing the database.
     */
    public ActionListener getClearDatabaseListener(SuperView parent) {
        return e -> {
            clearDatabase(parent);
            DialogUtils.showSuccess(parent, "Database reset successfully");
            controller.setDisplay(ViewType.COFFEE_MENU_VIEW);
        };
    }

    /**
     * Returns an {@code ActionListener} that resets the application's databases by
     * first clearing all existing data and then populating the databases with
     * initial
     * data. It prompts the user for confirmation before clearing. Upon successful
     * repopulation, it displays a success message and navigates back to the coffee
     * menu view.
     *
     * @param parent The parent {@code SuperView} used for displaying dialogs.
     * @return An {@code ActionListener} for resetting the database.
     */
    public ActionListener resetDatabaseListener(SuperView parent) {
        return e -> {
            clearDatabase(parent);
            populateDatabase(parent);
            DialogUtils.showSuccess(parent, "Database populated successfully");
            coffeeMenuView.refreshTable();
            controller.setDisplay(ViewType.COFFEE_MENU_VIEW);
        };
    }

    /**
     * Clears all data from the orders, customers, and coffees databases. It prompts
     * the user for confirmation before proceeding with the deletion. If an
     * SQLException
     * occurs during the process, an error message is displayed.
     *
     * @param parent The parent {@code SuperView} used for displaying the
     *               confirmation dialog
     *               and any error messages.
     */
    private void clearDatabase(SuperView parent) {
        try {
            var confirm = DialogUtils.showConfirmation(parent,
                    "Are you sure you want to clear the databases?\nThis will delete all data from the tables. If you are currently logged in, your session will only be persisted until you logout.\nYou will need to recreate an account to continue using the application.");

            if (!confirm) {
                return;
            }
            orderService.resetDatabase();
            customerService.resetDatabase();
            coffeeService.resetDatabase();
            coffeeMenuView.refreshTable();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            DialogUtils.showError(parent, "Error resetting database");

        }
    }

    /**
     * Populates the orders, customers, and coffees databases with initial data
     * by calling the respective service methods. If an SQLException occurs during
     * the process, an error message is displayed.
     *
     * @param parent The parent {@code SuperView} used for displaying any error
     *               messages.
     */
    private void populateDatabase(SuperView parent) {
        try {
            orderService.populateDatabase();
            customerService.populateDatabase();
            coffeeService.populateDatabase();

        } catch (SQLException e) {
            DialogUtils.showError(parent, "Error populating database");
        }
    }
}