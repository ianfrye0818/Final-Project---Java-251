package views;

import components.StyledInputs;
import components.Typography;
import controllers.AppController;
import enums.ViewType;
import models.Order;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class OrderDetailView extends SuperView {
    private final String title;
    private final Order order;
    private final ViewType sourceView;

    public OrderDetailView(AppController controller) {
        this.title = "Order Summary";
        this.order = controller.getOrderStore().get();
        this.sourceView = controller.getViewManager().getPreviousView();

        if (order == null) {
            JOptionPane.showMessageDialog(this, "No order found", "Error", JOptionPane.ERROR_MESSAGE);
            controller.setDisplay(ViewType.COFFEE_MENU_VIEW);
            return;
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        addDetailField(mainPanel, gbc, "Name:", order.getCustomer().getCustomerName(), 3, 0);
        addDetailField(mainPanel, gbc, "Email:", order.getCustomer().getCustomerEmail(), 4, 0);
        addDetailField(mainPanel, gbc, "Phone:", order.getCustomer().getCustomerPhone(), 5, 0);

        // Right Column - Order Details
        gbc.gridx = 1;

        // Order Section
        addSectionHeader(mainPanel, gbc, "Order Details", 2, 1);
        addDetailField(mainPanel, gbc, "Coffee:", order.getCoffee().getCoffeeName(), 3, 1);
        addDetailField(mainPanel, gbc, "Price per Unit:", String.format("$%.2f", order.getCoffee().getPrice()), 4, 1);
        addDetailField(mainPanel, gbc, "Quantity:", String.valueOf((int) order.getNumberOrdered()), 5, 1);

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

        String buttonText = sourceView == ViewType.VIEW_ALL_ORDERS_VIEW ? "Back to Orders" : "Back to Menu";
        JButton backButton = new StyledInputs.StyledButton(buttonText);
        backButton.addActionListener(e -> controller.setDisplay(sourceView));
        buttonPanel.add(backButton);

        gbc.gridy = 9;
        mainPanel.add(buttonPanel, gbc);

        add(mainPanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
    }

    private void addSectionHeader(JPanel panel, GridBagConstraints gbc, String text, int gridy, int gridx) {
        JLabel header = new JLabel(text);
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.setForeground(new Color(79, 55, 48));
        gbc.gridy = gridy;
        gbc.gridx = gridx;
        gbc.insets = new Insets(20, 8, 8, 8);
        panel.add(header, gbc);
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

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

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

        if (isBold) {
            label.setFont(new Font("Segoe UI", Font.BOLD, 16));
            valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        } else {
            label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            valueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }

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
        return order.getCoffee().getPrice() * order.getNumberOrdered();
    }

    private double calculateTax() {
        return calculateSubTotal() * AppController.TAX_RATE;
    }

    private double calculateTotal() {
        return calculateSubTotal() + calculateTax();
    }

    @Override
    public String getTitle() {
        return super.getTitle() + " - " + title;
    }
}