package listeners.CoffeeMenuActionMenus;

import Interfaces.ICoffeeService;
import Interfaces.ICustomerService;
import Interfaces.IOrderService;
import controllers.AppController;
import enums.ViewType;
import utils.DialogUtils;
import views.SuperView;

import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AdminMenuListeners {
    private final AppController controller;

    private final ICustomerService customerService;
    private final ICoffeeService coffeeService;
    private final IOrderService orderService;

    public AdminMenuListeners(AppController controller) {
        this.controller = controller;
        this.customerService = controller.getCustomerService();
        this.coffeeService = controller.getCoffeeService();
        this.orderService = controller.getOrderService();
    }

    public ActionListener getViewAllCustomersListener() {
        return e -> controller.setDisplay(ViewType.VIEW_ALL_CUSTOMERS_VIEW);
    }

    public ActionListener getViewAllOrdersListener() {
        return e -> controller.setDisplay(ViewType.VIEW_ALL_ORDERS_VIEW);
    }

    public ActionListener getClearDatabaseListener(SuperView parent) {
        return e -> {
            clearDatabase(parent);
            DialogUtils.showSuccess(parent, "Database reset successfully");
            controller.setDisplay(ViewType.COFFEE_MENU_VIEW);
        };
    }

    public ActionListener resetDatabaseListener(SuperView parent) {
        return e -> {
            clearDatabase(parent);
            populateDatabase(parent);
            DialogUtils.showSuccess(parent, "Database populated successfully");
            controller.setDisplay(ViewType.COFFEE_MENU_VIEW);
        };
    }

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

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            DialogUtils.showError(parent, "Error resetting database");

        }
    }

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
