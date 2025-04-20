package controllers;

import enums.ViewType;
import views.*;

import java.awt.*;

public class ViewManager {
    private ViewType currentView;
    private ViewType previousView;
    AppController controller;

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
        displayNewView();
    }

    public ViewType getPreviousView() {
        return this.previousView;
    }

    public ViewType getCurrentView() {
        return this.currentView;
    }

    public void refreshCurrentView() {
        disposeWindows();
        displayNewView();
    }

    private void disposeWindows() {
        if (currentView != null) {
            Window[] windows = Window.getWindows();
            for (Window window : windows) {
                if (window instanceof SuperView) {
                    window.dispose();
                }
            }
        }
    }

    private void displayNewView() {
        SuperView viewToDisplay = createView(this.currentView);
        viewToDisplay.pack();
        viewToDisplay.setLocationRelativeTo(null);
        viewToDisplay.setVisible(true);

    }

    private SuperView createView(ViewType viewType) {
        return switch (viewType) {
            case COFFEE_MENU_VIEW -> new CoffeeMenuView();
            case CREATE_ACCOUNT_VIEW -> new CreateAccountView();
            case CREATE_COFFEE_VIEW -> new CreateCoffeeView();
            case CREATE_ORDER_VIEW -> new CreateOrderView();
            case ORDER_HISTORY_VIEW -> new CustomerOrderHistoryView();
            case LOGIN_VIEW -> new LoginView();
            case ORDER_DETAIL_VIEW -> new OrderDetailView();
            case UPDATE_ACCOUNT_VIEW -> new UpdateAccountView();
            case UPDATE_COFFEE_VIEW -> new UpdateCoffeeView();
            case CUSTOMER_DETAIL_VIEW -> new CustomerDetailView();
            case VIEW_ALL_CUSTOMERS_VIEW -> new ViewAllCustomersView();
            case VIEW_ALL_ORDERS_VIEW -> new ViewAllOrdersView();
        };
    }
}