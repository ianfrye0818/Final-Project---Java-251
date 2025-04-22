package controllers;

import enums.ViewType;
import views.*;

import java.awt.*;

/**
 * Manages the display of different views within the application.
 * It keeps track of the current and previous views, disposes of old views,
 * and creates and displays new ones based on the requested {@link ViewType}.
 *
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */
public class ViewManager {
    private ViewType currentView;
    private ViewType previousView;
    AppController controller;
    private Point previousLocation = null;

    /**
     * Constructs a new {@code ViewManager} associated with the given application
     * controller.
     *
     * @param controller The main application controller.
     */
    public ViewManager(AppController controller) {
        this.controller = controller;
    }

    /**
     * Sets the display to the specified {@link ViewType}. If the requested view is
     * the
     * same as the current view, a warning is logged and no action is taken.
     * Otherwise,
     * the previous view is recorded, the current view is updated, any existing
     * {@link SuperView} windows are disposed of, and the new view is displayed.
     *
     * @param view The {@code ViewType} to display.
     */
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

    /**
     * Returns the {@link ViewType} of the previously displayed view.
     *
     * @return The previous {@code ViewType}.
     */
    public ViewType getPreviousView() {
        return this.previousView;
    }

    /**
     * Returns the {@link ViewType} of the currently displayed view.
     *
     * @return The current {@code ViewType}.
     */
    public ViewType getCurrentView() {
        return this.currentView;
    }

    /**
     * Refreshes the currently displayed view by disposing of any existing
     * {@link SuperView} windows and then displaying the current view again.
     */
    public void refreshCurrentView() {
        disposeWindows();
        displayNewView();
    }

    /**
     * Disposes of all currently open {@link SuperView} windows. This is typically
     * called before displaying a new view to ensure only one main view is visible.
     */
    private void disposeWindows() {
        if (currentView != null) {
            Window[] windows = Window.getWindows();
            for (Window window : windows) {
                if (window instanceof SuperView) {
                    previousLocation = window.getLocation();
                    window.dispose();
                }
            }
        }
    }

    /**
     * Creates a new view based on the {@link #currentView} and makes it visible
     * in the center of the screen. The view is packed to its preferred size before
     * being displayed.
     */
    private void displayNewView() {
        SuperView viewToDisplay = createView(this.currentView);
        viewToDisplay.pack();

        if (previousLocation != null) {
            viewToDisplay.setLocation(previousLocation);
        } else {
            viewToDisplay.setLocationRelativeTo(null);
        }
        viewToDisplay.setVisible(true);

    }

    /**
     * Creates an instance of the {@link SuperView} subclass corresponding to the
     * given {@link ViewType}.
     *
     * @param viewType The {@code ViewType} for which to create a view.
     * @return A new instance of the corresponding {@link SuperView} subclass.
     */
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