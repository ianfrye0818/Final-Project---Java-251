package views;

import controllers.AppController;
import listeners.CreateOrderListeners;
import components.CoffeeSelectComboBox;
import components.QuantitySpinner;
import components.StyledInputs;
import components.Typography;
import models.Coffee;
import models.Customer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CreateOrderView extends SuperView {
  private final String title;
  private JComboBox<Coffee> coffeeComboBox;
  private JSpinner quantitySpinner;
  private JLabel subtotalLabel;
  private JLabel taxLabel;
  private JLabel totalLabel;
  private JTextField firstNameField;
  private JTextField lastNameField;

  public CreateOrderView(AppController appController) {
    this.title = "Create Order";

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
    JLabel titleLabel = new Typography.StyledTitleField("Create Order");
    gbc.gridy = 0;
    mainPanel.add(titleLabel, gbc);

    JLabel subtitleLabel = new Typography.StyledSubtitleField("Place your coffee order");
    gbc.gridy = 1;
    gbc.insets = new Insets(0, 10, 15, 10);
    mainPanel.add(subtitleLabel, gbc);

    // Customer Info
    Customer currentCustomer = appController.getCustomerService().getCurrentCustomer();

    // First Name Field (on its own row)
    firstNameField = new StyledInputs.StyledTextField(10, true, currentCustomer.getFirstName());
    addFormField(mainPanel, gbc, "First Name:", firstNameField,
        2, 0, 1.0, GridBagConstraints.REMAINDER); // Changed to full width

    // Last Name Field (on next row)
    lastNameField = new StyledInputs.StyledTextField(10, true, currentCustomer.getLastName());
    addFormField(mainPanel, gbc, "Last Name:", lastNameField,
        4, 0, 1.0, GridBagConstraints.REMAINDER); // Changed gridy to 4 and full width

    coffeeComboBox = new CoffeeSelectComboBox(appController);
    addFormField(mainPanel, gbc, "Select Coffee:", coffeeComboBox, 7, 0, 1.0, GridBagConstraints.REMAINDER);

    quantitySpinner = new QuantitySpinner();
    addFormField(mainPanel, gbc, "Quantity:", quantitySpinner, 8, 0, 1.0, GridBagConstraints.REMAINDER);

    // Price Panel
    JPanel pricePanel = createPricePanel();
    gbc.gridy = 10;
    gbc.insets = new Insets(15, 8, 15, 8);
    mainPanel.add(pricePanel, gbc);

    // Buttons Panel
    JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Increased horizontal gap
    buttonsPanel.setBackground(Color.WHITE);
    buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Add vertical padding

    JButton backButton = new StyledInputs.StyledButton("Back to Menu");
    buttonsPanel.add(backButton);

    JButton placeOrderButton = new StyledInputs.StyledButton("Place Order", new Color(79, 55, 48), Color.WHITE);
    buttonsPanel.add(placeOrderButton);

    gbc.gridy = 11;
    mainPanel.add(buttonsPanel, gbc);

    add(mainPanel, BorderLayout.CENTER);

    // Add listeners
    CreateOrderListeners listeners = new CreateOrderListeners(
        appController,
        this,
        coffeeComboBox,
        quantitySpinner,
        subtotalLabel,
        taxLabel,
        totalLabel);

    backButton.addActionListener(listeners.getBackButtonListener());
    placeOrderButton.addActionListener(listeners.getPlaceOrderListener());
    coffeeComboBox.addItemListener(listeners.getCoffeeSelectionListener());
    quantitySpinner.addChangeListener(e -> listeners.updateOrderPrice());

    // Initial price update
    if (coffeeComboBox.getItemCount() > 0) {
      listeners.updateOrderPrice();
    }

    pack();
    setLocationRelativeTo(null);
  }

  private JPanel createPricePanel() {
    JPanel pricePanel = new JPanel(new GridLayout(3, 2, 5, 5));
    pricePanel.setBackground(Color.WHITE);
    pricePanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));

    subtotalLabel = new JLabel("0.00");
    taxLabel = new JLabel("0.00");
    totalLabel = new JLabel("0.00");

    addPriceRow(pricePanel, "Subtotal:", subtotalLabel);
    addPriceRow(pricePanel, "Tax:", taxLabel);
    addPriceRow(pricePanel, "Total:", totalLabel, true);

    return pricePanel;
  }

  private void addPriceRow(JPanel panel, String labelText, JLabel valueLabel) {
    addPriceRow(panel, labelText, valueLabel, false);
  }

  private void addPriceRow(JPanel panel, String labelText, JLabel valueLabel, boolean isBold) {
    JLabel label = new JLabel(labelText);
    label.setHorizontalAlignment(SwingConstants.RIGHT);
    valueLabel.setHorizontalAlignment(SwingConstants.RIGHT); // Right align the values

    if (isBold) {
      label.setFont(new Font("Segoe UI", Font.BOLD, 16));
      valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
    } else {
      label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
      valueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    }

    // Add some horizontal padding to the price values
    valueLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

    panel.add(label);
    panel.add(valueLabel);
  }

  @Override
  public String getTitle() {
    return super.getTitle() + " - " + title;
  }
}