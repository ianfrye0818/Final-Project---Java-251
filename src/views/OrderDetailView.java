package views;

import components.StyledInputs;
import components.Typography;
import controllers.AppController;
import entites.Order;
import enums.ViewType;
import stores.OrderStore;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class OrderDetailView extends SuperView {

    private Order selectedOrder;

    public OrderDetailView(AppController controller) {
        super(controller, "Order Summary");
        ViewType previousView = controller.getViewManager().getPreviousView();
        selectedOrder = OrderStore.getInstance().get();

        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(800, 600));
        getContentPane().setBackground(new Color(245, 245, 245));

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(20, 30, 20, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridwidth = 2; // Two columns layout

        // Title Section (spans both columns)
        JLabel titleLabel = new Typography.StyledTitleField("Order Summary");
        gbc.gridy = 0;
        mainPanel.add(titleLabel, gbc);

        JLabel subtitleLabel = new Typography.StyledSubtitleField("Thank you for your order!");
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 25, 10);
        mainPanel.add(subtitleLabel, gbc);

        // Left Column - Customer Details
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        gbc.gridx = 0;

        // Customer Section
        addSectionHeader(mainPanel, gbc, "Customer Information", 2, 0);
        addDetailField(mainPanel, gbc, "Name:", selectedOrder.getCustomer().getCustomerName(), 3, 0);
        addDetailField(mainPanel, gbc, "Email:", selectedOrder.getCustomer().getCustomerEmail(), 4, 0);
        addDetailField(mainPanel, gbc, "Phone:", selectedOrder.getCustomer().getCustomerPhone(), 5, 0);

        // Right Column - Order Details
        gbc.gridx = 1;

        // Order Section
        addSectionHeader(mainPanel, gbc, "Order Details", 2, 1);
        addDetailField(mainPanel, gbc, "Coffee:", selectedOrder.getCoffee().getCoffeeName(), 3, 1);
        addDetailField(mainPanel, gbc, "Price per Unit:",
                String.format("$%.2f", selectedOrder.getCoffee().getPrice()), 4, 1);
        addDetailField(mainPanel, gbc, "Quantity:", String.valueOf((int) selectedOrder.getQtyOrdered()), 5, 1);

        // Price Summary (spans both columns)
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.gridy = 7;
        addSectionHeader(mainPanel, gbc, "Price Summary", 7, 0);

        JPanel pricePanel = createPricePanel();
        gbc.gridy = 8;
        gbc.insets = new Insets(15, 15, 25, 15);
        mainPanel.add(pricePanel, gbc);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton backButton = new StyledInputs.StyledButton("Back");
        backButton.addActionListener(e -> controller.setDisplay(previousView));
        buttonPanel.add(backButton);

        gbc.gridy = 9;
        mainPanel.add(buttonPanel, gbc);

        add(mainPanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
    }

    private void addDetailField(JPanel panel, GridBagConstraints gbc, String labelText, String value, int gridy,
            int gridx) {
        gbc.gridy = gridy;
        gbc.gridx = gridx;

        JPanel fieldPanel = new JPanel(new BorderLayout(0, 5));
        fieldPanel.setBackground(Color.WHITE);
        fieldPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 15));

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        fieldPanel.add(label, BorderLayout.NORTH);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        fieldPanel.add(valueLabel, BorderLayout.CENTER);

        panel.add(fieldPanel, gbc);
    }

    private JPanel createPricePanel() {
        JPanel pricePanel = new JPanel(new GridBagLayout());
        pricePanel.setBackground(Color.WHITE);
        pricePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230)),
                BorderFactory.createEmptyBorder(15, 25, 15, 25)));

        // TODO: Check to make sure this didn't effect anything

        // GridBagConstraints gbc = new GridBagConstraints();
        // gbc.fill = GridBagConstraints.HORIZONTAL;
        // gbc.insets = new Insets(5, 5, 5, 5);

        addPriceRow(pricePanel, "Subtotal:", String.format("$%.2f", calculateSubTotal()), 0);
        addPriceRow(pricePanel, "Tax:", String.format("$%.2f", calculateTax()), 1);
        addPriceRow(pricePanel, "Total:", String.format("$%.2f", calculateTotal()), 2, true);

        return pricePanel;
    }

    private void addPriceRow(JPanel panel, String labelText, String value, int gridy) {
        addPriceRow(panel, labelText, value, gridy, false);
    }

    private void addPriceRow(JPanel panel, String labelText, String value, int gridy, boolean isBold) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = gridy;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel label = new JLabel(labelText);
        JLabel valueLabel = new JLabel(value);

        checkIsBold(isBold, label, valueLabel);

        // Label (left-aligned)
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(label, gbc);

        // Value (right-aligned)
        gbc.gridx = 1;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(valueLabel, gbc);
    }

    private double calculateSubTotal() {
        return selectedOrder.getCoffee().getPrice() * selectedOrder.getQtyOrdered();
    }

    private double calculateTax() {
        return calculateSubTotal() * AppController.TAX_RATE;
    }

    private double calculateTotal() {
        return calculateSubTotal() + calculateTax();
    }

}