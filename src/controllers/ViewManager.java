package controllers;

import enums.ViewType;
import views.*;

import java.awt.*;

public class ViewManager {
    private final AppController controller;
    private ViewType currentView;
    private ViewType previousView;

    public ViewManager(AppController controller) {
        this.controller = controller;
    }

    public void setDisplay(ViewType view) {

        if (this.currentView == view) {
            System.out.println("Warning: Attempting to set the same view as current view: " + view);
            return;
        }

        this.previousView = this.currentView;
        this.currentView = view;

        disposeWindows();
        displayNewView(view);
    }

    public ViewType getPreviousView() {
        return this.previousView;
    }

    public ViewType getCurrentView() {
        return this.currentView;
    }

    public void refreshCurrentView() {
        disposeWindows();
        displayNewView(this.currentView);
    }

    private void disposeWindows() {
        Window[] windows = Window.getWindows();
        for (Window window : windows) {
            window.dispose();
        }
    }

    private void displayNewView(ViewType view) {
        SuperView viewToDisplay = createView(this.currentView);
        viewToDisplay.pack();
        viewToDisplay.setLocationRelativeTo(null);
        viewToDisplay.setVisible(true);

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