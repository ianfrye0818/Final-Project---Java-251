package controllers;

import enums.ViewType;
import views.*;

import java.awt.*;

public class ViewManager {
    private final AppController controller;
    private ViewType currenView;
    private ViewType previousView;

    public ViewManager() {
        this.controller = AppController.getInstance();
    }

    public void setDisplay(ViewType view) {
        this.previousView = this.currenView;
        this.currenView = view;

        Window[] windows = Window.getWindows();
        for (Window window : windows) {
            window.dispose();
        }

        SuperView viewToDisplay = createView(view);
        viewToDisplay.pack();
        viewToDisplay.setLocationRelativeTo(null);
        viewToDisplay.setVisible(true);
    }

    public void showMainView() {
        setDisplay(ViewType.LOGIN_VIEW);
    }

    public ViewType getPreviousView() {
        return this.previousView;
    }

    public ViewType getCurrentView() {
        return this.currenView;
    }

    private SuperView createView(ViewType viewType) {
        return switch (viewType) {
            case COFFEE_MENU_VIEW -> new CoffeeMenuView(controller);
            case CREATE_ACCOUNT_VIEW -> new CreateAccountView(controller);
            case CREATE_COFFEE_VIEW -> new CreateCoffeeView(controller);
            case CREATE_ORDER_VIEW -> new CreateOrderView(controller);
            case ORDER_HISTORY_VIEW -> new CustomerOrderHistoryView(controller);
            case LOGIN_VIEW -> new LoginView(controller);
            case ORDER_DETAIL_VIEW -> new OrderDetailView(controller);
            case UPDATE_ACCOUNT_VIEW -> new UpdateAccountView(controller);
            case UPDATE_COFFEE_VIEW -> new UpdateCoffeeView(controller);
            case CUSTOMER_DETAIL_VIEW -> new CustomerDetailView(controller);
            case VIEW_ALL_CUSTOMERS_VIEW -> new ViewAllCustomersView(controller);
            case VIEW_ALL_ORDERS_VIEW -> new ViewAllOrdersView(controller);
        };
    }
}