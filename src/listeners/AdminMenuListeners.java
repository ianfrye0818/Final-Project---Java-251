package listeners;

import controllers.AppController;
import enums.ViewType;

import java.awt.event.ActionListener;

public class AdminMenuListeners {
    private AppController controller;

    public AdminMenuListeners(AppController controller) {
        this.controller = controller;
    }

    public ActionListener getViewAllCustomersListener() {
        return e -> {
            controller.setDisplay(ViewType.VIEW_ALL_CUSTOMERS_VIEW);
        };
    }

    public ActionListener getViewAllOrdersListener() {
        return e -> {
            controller.setDisplay(ViewType.VIEW_ALL_ORDERS_VIEW);
        };
    }
}
