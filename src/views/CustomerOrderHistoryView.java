package views;

import components.StyledInputs;
import components.Typography;
import entites.Customer;
import entites.Order;
import enums.ViewType;
import stores.AuthStore;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

/**
 * The view displaying the order history for the currently logged-in customer.
 * It retrieves the customer's information from the {@link AuthStore} and their
 * order history from the controller. The view presents the customer's name and
 * the total number of orders, followed by a list of individual orders, each
 * showing the coffee name, quantity, and total price. If no orders are found,
 * a message indicating this is displayed. A "Back to Menu" button allows
 * navigation back to the main coffee menu. This view uses {@link GridBagLayout}
 * for flexible layout management within the main panel and individual order
 * cards.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */
public class CustomerOrderHistoryView extends SuperView {
    private final Customer currentCustomer;
    private final List<Order> customerOrders;

    /**
     * Constructs the {@code CustomerOrderHistoryView}, retrieving the logged-in
     * customer and their order history, and initializing the UI components to
     * display this information.
     */
    public CustomerOrderHistoryView() {
        super("Order History");
        currentCustomer = AuthStore.getInstance().get();
        AuthStore authStore = AuthStore.getInstance();
        this.customerOrders = controller.getOrderService().getOrdersByCustomerId(authStore.get().getCustomerId());

        setMinimumSize(new Dimension(800, 600));
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 245));

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        // Title Section
        JLabel titleLabel = new Typography.StyledTitleField("Order History");
        gbc.gridy = 0;
        mainPanel.add(titleLabel, gbc);

        // Customer Info Section
        JPanel customerInfoPanel = createCustomerInfoPanel();
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 8, 20, 8);
        mainPanel.add(customerInfoPanel, gbc);

        // Orders Section
        if (customerOrders.isEmpty()) {
            JLabel noOrdersLabel = new JLabel("No orders found");
            noOrdersLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
            noOrdersLabel.setHorizontalAlignment(SwingConstants.CENTER);
            gbc.gridy = 2;
            mainPanel.add(noOrdersLabel, gbc);
        } else {
            JPanel ordersPanel = createOrdersPanel();
            JScrollPane scrollPane = new JScrollPane(ordersPanel);
            scrollPane.setBorder(null);
            scrollPane.getVerticalScrollBar().setUnitIncrement(16);

            gbc.gridy = 2;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weighty = 1.0;
            mainPanel.add(scrollPane, gbc);
        }

        // Back Button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        JButton backButton = new StyledInputs.StyledButton("Back to Menu");
        backButton.addActionListener(e -> controller.setDisplay(ViewType.COFFEE_MENU_VIEW));
        buttonPanel.add(backButton);

        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0.0;
        mainPanel.add(buttonPanel, gbc);

        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Creates and returns a panel displaying the current customer's basic
     * information, such as their full name and the total number of orders.
     *
     * @return A {@code JPanel} containing customer information.
     */
    private JPanel createCustomerInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Customer name
        JLabel nameLabel = new JLabel("Customer: " +
                currentCustomer.getFirstName() + " " + currentCustomer.getLastName());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nameLabel, gbc);

        // Total orders
        JLabel ordersLabel = new JLabel("Total Orders: " + customerOrders.size());
        ordersLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridy = 1;
        panel.add(ordersLabel, gbc);

        return panel;
    }

    /**
     * Creates and returns a panel containing individual order cards for each
     * order in the customer's history.
     *
     * @return A {@code JPanel} containing the list of order cards.
     */
    private JPanel createOrdersPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 10, 0);

        for (int i = 0; i < customerOrders.size(); i++) {
            Order order = customerOrders.get(i);
            JPanel orderCard = createOrderCard(order);
            gbc.gridy = i;
            panel.add(orderCard, gbc);
        }

        return panel;
    }

    /**
     * Creates and returns a panel representing a single order, displaying the
     * coffee name, quantity ordered, and the total price of the order.
     *
     * @param order The {@link Order} object to create the card for.
     * @return A {@code JPanel} representing an order card.
     */
    private JPanel createOrderCard(Order order) {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230)),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2, 5, 2, 5);

        // Coffee name and quantity
        JLabel coffeeLabel = new JLabel(order.getCoffee().getCoffeeName());
        coffeeLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        card.add(coffeeLabel, gbc);

        JLabel quantityLabel = new JLabel(String.format("Qty: %.0f", order.getQtyOrdered()));
        quantityLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.weightx = 0.0;
        card.add(quantityLabel, gbc);

        // Total price
        JLabel totalLabel = new JLabel(String.format("Total: $%.2f", order.getTotal()));
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        gbc.gridx = 2;
        card.add(totalLabel, gbc);

        return card;
    }
}