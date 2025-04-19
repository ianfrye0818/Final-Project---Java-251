package listeners;

import controllers.AppController;
import dto.CreateOrderDto;
import dto.OrderCoffeeDto;
import dto.OrderCustomerDto;
import entites.Coffee;
import entites.Customer;
import entites.Order;
import enums.ViewType;
import stores.OrderStore;
import utils.DialogUtils;
import views.CreateOrderView;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

public class CreateOrderViewListeners {

    private final AppController appController;
    private final CreateOrderView view;
    private final JComboBox<Coffee> coffeeComboBox;
    private final JSpinner quantitySpinner;
    private final JLabel subtotalLabel;
    private final JLabel taxLabel;
    private final JLabel totalLabel;

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

    public ActionListener getBackButtonListener() {
        return e -> appController.setDisplay(ViewType.COFFEE_MENU_VIEW);
    }

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
                    .setQtyOrdered(quantity)
                    .setTotal(total)
                    .build();

            Customer currentCustomer = appController.getLoggedinCustomerStore().get();
            if (total > currentCustomer.getCreditLimit()) {
                handleInsufficientCredit(currentCustomer.getCreditLimit());
                return;
            }

            try {

                appController.getOrderService().createOrder(order);
                appController.setDisplay(ViewType.ORDER_DETAIL_VIEW);
            } catch (Exception ex) {
                DialogUtils.showError(view, "Error placing order: " + ex.getMessage());
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
        boolean confirmation = DialogUtils.showConfirmation(view,
                message + "\nWould you like to update your account to add more credit?");

        if (confirmation) {
            DialogUtils.showAddCreditDialog(view, appController);
        }
    }

    // private void setOrder(CreateOrderDto dto) {
    // Customer currentCustomer = appController.getLoggedinCustomerStore().get();
    // appController.getOrderStore().set(
    // new Order.Builder()
    // .setQtyOrdered(dto.getQtyOrdered())
    // .setTotal(dto.getTotal())
    // .setCustomer(new OrderCustomerDto.Builder()
    // .setCustomerId(currentCustomer.getCustomerId())
    // .setCustomerEmail(currentCustomer.getEmail())
    // .setCustomerName(currentCustomer.getFirstName() + " " +
    // currentCustomer.getLastName())
    // .setCustomerPhone(currentCustomer.getPhone())
    // .build())
    // .setCoffee(new OrderCoffeeDto.Builder()
    // .setCoffeeId(dto.getCoffeeId())
    // .build())
    // .build());
    // }

}