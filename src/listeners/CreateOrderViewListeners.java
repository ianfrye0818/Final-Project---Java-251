package listeners;

import controllers.AppController;
import dto.CreateOrderDto;
import entites.Coffee;
import entites.Customer;
import entites.Order;
import enums.ViewType;
import stores.AuthStore;
import stores.OrderStore;
import stores.SelectedCustomerStore;
import utils.DialogUtils;
import views.CreateOrderView;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

/**
 * Provides {@code ActionListener} and {@code ItemListener} implementations for
 * actions
 * within the {@code CreateOrderView}. This includes handling navigation,
 * placing a new
 * order, and updating the displayed order price based on coffee selection and
 * quantity.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */

public class CreateOrderViewListeners {

    private final AppController appController;
    private final CreateOrderView view;
    private final JComboBox<Coffee> coffeeComboBox;
    private final JSpinner quantitySpinner;
    private final JLabel subtotalLabel;
    private final JLabel taxLabel;
    private final JLabel totalLabel;

    /**
     * Constructs a new {@code CreateOrderViewListeners} with references to the
     * application
     * controller, the create order view, and the UI components used for input and
     * display.
     *
     * @param appController   The application controller providing access to
     *                        services and navigation.
     * @param view            The {@code CreateOrderView} where the order creation
     *                        is performed.
     * @param coffeeComboBox  The combo box for selecting the coffee to order.
     * @param quantitySpinner The spinner for selecting the quantity of the coffee.
     * @param subtotalLabel   The label displaying the subtotal of the order.
     * @param taxLabel        The label displaying the calculated tax for the order.
     * @param totalLabel      The label displaying the total price of the order.
     */
    public CreateOrderViewListeners(
            AppController appController,
            CreateOrderView view,
            JComboBox<Coffee> coffeeComboBox,
            JSpinner quantitySpinner,
            JLabel subtotalLabel,
            JLabel taxLabel,
            JLabel totalLabel) {
        this.appController = appController;
        this.view = view;
        this.coffeeComboBox = coffeeComboBox;
        this.quantitySpinner = quantitySpinner;
        this.subtotalLabel = subtotalLabel;
        this.taxLabel = taxLabel;
        this.totalLabel = totalLabel;
    }

    /**
     * Returns an {@code ActionListener} that navigates the application back to the
     * coffee menu view.
     *
     * @return An {@code ActionListener} for navigating back to the coffee menu.
     */
    public ActionListener getBackButtonListener() {
        return e -> appController.setDisplay(ViewType.COFFEE_MENU_VIEW);
    }

    /**
     * Returns an {@code ActionListener} that handles the placement of a new order.
     * It retrieves the selected coffee and quantity from the input components,
     * calculates the total order price, and creates a {@link CreateOrderDto}.
     * It checks if the logged-in customer has sufficient credit before attempting
     * to create the order using the order service. If the credit is insufficient,
     * it calls {@link #handleInsufficientCredit(double)}. Upon successful order
     * creation, it stores the created order in the {@link OrderStore} and navigates
     * to the order detail view. If any error occurs during order placement, an
     * error
     * message is displayed.
     *
     * @return An {@code ActionListener} for placing a new order.
     */
    public ActionListener getPlaceOrderButtonListener() {
        return e -> {

            if (coffeeComboBox.getItemCount() == 0) {
                DialogUtils.showError(view, "No coffee available to order");
                return;
            }

            Coffee selectedCoffee = (Coffee) coffeeComboBox.getSelectedItem();
            int quantity = (int) quantitySpinner.getValue();
            double total = Double.parseDouble(totalLabel.getText());

            CreateOrderDto order = new CreateOrderDto.Builder()
                    .setCoffeeId(selectedCoffee.getCoffeeId())
                    .setCustomerId(AuthStore.getInstance().get().getCustomerId())
                    .setQtyOrdered(quantity)
                    .setTotal(total)
                    .build();

            Customer loggedInCustomer = AuthStore.getInstance().get();
            if (total > loggedInCustomer.getCreditLimit()) {
                handleInsufficientCredit(loggedInCustomer.getCreditLimit());
                return;
            }

            try {

                Order createdOrder = appController.getOrderService().createOrder(order);
                System.out.println("Created order: " + createdOrder);
                OrderStore.getInstance().set(createdOrder);
                appController.setDisplay(ViewType.ORDER_DETAIL_VIEW);
            } catch (Exception ex) {
                DialogUtils.showError(view, "Error placing order: " + ex.getMessage());
            }
        };
    }

    /**
     * Returns an {@code ItemListener} that is triggered when the selected coffee
     * in the combo box changes. It calls the {@link #updateOrderPrice()} method
     * to recalculate and update the displayed order price.
     *
     * @return An {@code ItemListener} for handling coffee selection changes.
     */
    public ItemListener getCoffeeSelectionListener() {
        return e -> updateOrderPrice();
    }

    /**
     * Updates the displayed subtotal, tax, and total labels based on the currently
     * selected coffee and quantity. It retrieves the selected coffee from the combo
     * box and the quantity from the spinner, calculates the subtotal, tax (using
     * the
     * application's tax rate), and the total price, and then formats and sets these
     * values to the respective labels.
     */
    public void updateOrderPrice() {
        Coffee selectedCoffee = (Coffee) coffeeComboBox.getSelectedItem();
        if (selectedCoffee != null) {
            int quantity = (int) quantitySpinner.getValue();
            double subtotal = selectedCoffee.getPrice() * quantity;
            double tax = subtotal * AppController.TAX_RATE;
            double total = subtotal + tax;

            subtotalLabel.setText(String.format("%.2f", subtotal));
            taxLabel.setText(String.format("%.2f", tax));
            totalLabel.setText(String.format("%.2f", total));

        }
    }

    /**
     * Handles the scenario where the logged-in customer has insufficient credit to
     * place the order. It displays a confirmation dialog informing the customer
     * about
     * their credit limit and asking if they would like to add more credit. If the
     * customer confirms, it sets the logged-in customer in the
     * {@link SelectedCustomerStore} and displays the add credit dialog using
     * {@link DialogUtils#showAddCreditDialog(java.awt.Frame, AppController)}.
     *
     * @param creditLimit The current credit limit of the logged-in customer.
     */
    private void handleInsufficientCredit(double creditLimit) {
        String message = String.format("Your order exceeds your available credit limit of $%.2f", creditLimit);
        boolean confirmation = DialogUtils.showConfirmation(view,
                message + "\nWould you like to update your account to add more credit?");

        if (confirmation) {
            SelectedCustomerStore.getInstance().set(AuthStore.getInstance().get());
            DialogUtils.showAddCreditDialog(view, appController);
        }
    }

}