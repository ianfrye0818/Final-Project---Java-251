package views;

import controllers.AppController;
import enums.ViewType;
import components.StyledInputs;
import components.Typography;
import models.Order;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class OrderSummaryView extends SuperView {
  private final String title;
  private final Order order;

  public OrderSummaryView(AppController appController) {
    this.title = "Order Summary";
    this.order = appController.getOrderStore().get();

    if (order == null) {
      JOptionPane.showMessageDialog(this, "No order found", "Error", JOptionPane.ERROR_MESSAGE);
      appController.setDisplay(ViewType.COFFEE_MENU_VIEW);
      return;
    }

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

    // Title and Subtitle
    JLabel titleLabel = new Typography.StyledTitleField("Order Summary");
    gbc.gridy = 0;
    mainPanel.add(titleLabel, gbc);

    JLabel subtitleLabel = new Typography.StyledSubtitleField("Thank you for your order!");
    gbc.gridy = 1;
    gbc.insets = new Insets(0, 10, 25, 10);
    mainPanel.add(subtitleLabel, gbc);

    // Order Details Panel
    JPanel orderDetailsPanel = createOrderDetailsPanel();
    gbc.gridy = 2;
    gbc.insets = new Insets(0, 8, 20, 8);
    mainPanel.add(orderDetailsPanel, gbc);

    // Price Panel
    JPanel pricePanel = createPricePanel();
    gbc.gridy = 3;
    gbc.insets = new Insets(15, 8, 25, 8);
    mainPanel.add(pricePanel, gbc);

    // Button Panel
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    buttonPanel.setBackground(Color.WHITE);

    JButton backToMenuButton = new StyledInputs.StyledButton("Back to Menu");
    backToMenuButton.addActionListener(e -> appController.setDisplay(ViewType.COFFEE_MENU_VIEW));
    buttonPanel.add(backToMenuButton);

    gbc.gridy = 4;
    mainPanel.add(buttonPanel, gbc);

    add(mainPanel, BorderLayout.CENTER);
    pack();
    setLocationRelativeTo(null);

  }

  private JPanel createOrderDetailsPanel() {
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBackground(Color.WHITE);
    panel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(230, 230, 230)),
        BorderFactory.createEmptyBorder(15, 15, 15, 15)));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridwidth = 1;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(5, 5, 5, 5);

    // Left column (labels)
    gbc.gridx = 0;
    gbc.anchor = GridBagConstraints.WEST;
    gbc.weightx = 0.0; // Don't expand labels

    JLabel customerLabel = new JLabel("Customer:");
    customerLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
    gbc.gridy = 0;
    panel.add(customerLabel, gbc);

    JLabel coffeeLabel = new JLabel("Coffee:");
    coffeeLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
    gbc.gridy = 1;
    panel.add(coffeeLabel, gbc);

    JLabel quantityLabel = new JLabel("Quantity:");
    quantityLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
    gbc.gridy = 2;
    panel.add(quantityLabel, gbc);

    // Right column (values)
    gbc.gridx = 1;
    gbc.weightx = 1.0; // Expand value column
    gbc.insets = new Insets(5, 20, 5, 5); // Add more left padding to values

    JLabel nameValue = new JLabel(order.getCustomer().getCustomerName());
    nameValue.setFont(new Font("Segoe UI", Font.PLAIN, 16));
    gbc.gridy = 0;
    panel.add(nameValue, gbc);

    JLabel coffeeValue = new JLabel(order.getCoffee().getCoffeeName());
    coffeeValue.setFont(new Font("Segoe UI", Font.PLAIN, 16));
    gbc.gridy = 1;
    panel.add(coffeeValue, gbc);

    JLabel quantityValue = new JLabel(String.valueOf((int) order.getNumberOrdered()));
    quantityValue.setFont(new Font("Segoe UI", Font.PLAIN, 16));
    gbc.gridy = 2;
    panel.add(quantityValue, gbc);

    return panel;
  }

  private JPanel createPricePanel() {
    JPanel pricePanel = new JPanel(new GridLayout(3, 2, 5, 5));
    pricePanel.setBackground(Color.WHITE);
    pricePanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));

    addPriceRow(pricePanel, "Subtotal:", String.format("$%.2f", calculateSubTotal()));
    addPriceRow(pricePanel, "Tax:", String.format("$%.2f", calculateTax()));
    addPriceRow(pricePanel, "Total:", String.format("$%.2f", calculateTotal()), true);

    return pricePanel;
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

  private void addPriceRow(JPanel panel, String labelText, String value) {
    addPriceRow(panel, labelText, value, false);
  }

  private void addPriceRow(JPanel panel, String labelText, String value, boolean isBold) {
    JLabel label = new JLabel(labelText);
    JLabel valueLabel = new JLabel(value);

    label.setHorizontalAlignment(SwingConstants.RIGHT);
    valueLabel.setHorizontalAlignment(SwingConstants.RIGHT);

    if (isBold) {
      label.setFont(new Font("Segoe UI", Font.BOLD, 16));
      valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
    } else {
      label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
      valueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    }

    valueLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

    panel.add(label);
    panel.add(valueLabel);
  }

  @Override
  public String getTitle() {
    return super.getTitle() + " - " + title;
  }
}