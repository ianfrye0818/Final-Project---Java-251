package listeners;

import controllers.AppController;
import dto.OrderCoffeeDto;
import dto.OrderCustomerDto;
import enums.ViewType;
import models.Coffee;
import models.Customer;
import models.Order;
import views.CreateOrderView;

import javax.swing.*;

import components.AddCreditDialog;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

public class CreateOrderListeners {

    private final AppController appController;
    private final CreateOrderView view;
    private final JComboBox<Coffee> coffeeComboBox;
    private final JSpinner quantitySpinner;
    private final JLabel subtotalLabel;
    private final JLabel taxLabel;
    private final JLabel totalLabel;

    public CreateOrderListeners(
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

    public ActionListener getBackButtonListener() {
        return e -> appController.setDisplay(ViewType.COFFEE_MENU_VIEW);
    }

    public ActionListener getPlaceOrderListener() {
        return e -> {
            if (coffeeComboBox.getItemCount() == 0) {
                showError("No coffee available to order");
                return;
            }

            Coffee selectedCoffee = (Coffee) coffeeComboBox.getSelectedItem();
            int quantity = (int) quantitySpinner.getValue();
            double total = Double.parseDouble(totalLabel.getText());

            Customer currentCustomer = appController.getCustomerStore().get();
            if (total > currentCustomer.getCreditLimit()) {
                handleInsufficientCredit(currentCustomer.getCreditLimit());
                return;
            }

            try {
                OrderCustomerDto customerDto = new OrderCustomerDto.Builder()
                        .setCustomerId(currentCustomer.getCustomerId())
                        .setCustomerName(currentCustomer.getFirstName() + " " +
                                currentCustomer.getLastName())
                        .setCustomerEmail(currentCustomer.getEmail())
                        .setCustomerPhone(currentCustomer.getPhone())
                        .build();

                OrderCoffeeDto coffeeDto = new OrderCoffeeDto.Builder()
                        .setCoffeeId(selectedCoffee.getCoffeeId())
                        .setCoffeeName(selectedCoffee.getCoffeeName())
                        .setPrice(selectedCoffee.getPrice())
                        .build();

                Order order = new Order.Builder()
                        .setCustomer(customerDto)
                        .setCoffee(coffeeDto)
                        .setNumberOrdered(quantity)
                        .setTotal(total)
                        .build();

                appController.getOrderService().createOrder(order);
                appController.setDisplay(ViewType.ORDER_DETAIL_VIEW);
            } catch (Exception ex) {
                showError("Error placing order: " + ex.getMessage());
            }
        };
    }

    public ItemListener getCoffeeSelectionListener() {
        return e -> updateOrderPrice();
    }

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

    private void handleInsufficientCredit(double creditLimit) {
        String message = String.format("Your order exceeds your available credit limit of $%.2f", creditLimit);
        int option = JOptionPane.showConfirmDialog(view,
                message + "\nWould you like to update your account to add more credit?",
                "Insufficient Credit",
                JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            JDialog addCreditDialog = new AddCreditDialog(view, appController, appController.getCustomerStore().get());
            addCreditDialog.setVisible(true);

        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(view, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}