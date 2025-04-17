package controllers;

import enums.ViewType;
import views.*;

import java.awt.*;

public class ViewManager {
    private final AppController controller;
    private ViewType currenView;
    private ViewType previousView;

    public ViewManager(AppController controller) {
        this.controller = controller;
    }

    public void setDisplay(ViewType view) {
        this.previousView = this.currenView;
        this.currenView = view;

        Window[] windows = Window.getWindows();
        for (Window window : windows) {
            window.dispose();
        }

        SuperView viewToDisplay = createView(view);
        if (viewToDisplay != null) {
            viewToDisplay.pack();
            viewToDisplay.setLocationRelativeTo(null);
            viewToDisplay.setVisible(true);
        }
    }

    public void goBack() {
        if (this.previousView != null) {
            this.setDisplay(this.previousView);
        }
    }

    public ViewType getPreviousView() {
        return this.previousView;
    }

    public ViewType getCurrentView() {
        return this.currenView;
    }

    private SuperView createView(ViewType viewType) {
        switch (viewType) {
            case COFFEE_MENU_VIEW:
                return new CoffeeMenuView(controller);
            case CREATE_ACCOUNT_VIEW:
                return new CreateAccountView(controller);
            case CREATE_COFFEE_VIEW:
                return new CreateCoffeeView(controller);
            case CREATE_ORDER_VIEW:
                return new CreateOrderView(controller);
            case ORDER_HISTORY_VIEW:
                return new CustomerOrderHistoryView(controller);
            case LOGIN_VIEW:
                return new LoginView(controller);
            case ORDER_DETAIL_VIEW:
                return new OrderDetailView(controller);
            case UPDATE_ACCOUNT_VIEW:
                return new UpdateAccountView(controller);
            case UPDATE_COFFEE_VIEW:
                return new UpdateCoffeeView(controller);
            case CUSTOMER_DETAIL_VIEW:
                return new CustomerDetailView(controller);
            case VIEW_ALL_CUSTOMERS_VIEW:
                return new ViewAllCustomersView(controller);
            case VIEW_ALL_ORDERS_VIEW:
                return new ViewAllOrdersView(controller);
            default:
                return null;
        }
    }
}