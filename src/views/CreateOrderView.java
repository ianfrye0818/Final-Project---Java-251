package views;

import components.CoffeeSelectComboBox;
import components.QuantitySpinner;
import components.StyledInputs;
import components.Typography;
import controllers.AppController;
import listeners.CreateOrderListeners;
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
    private Customer currentCustomer;

    public CreateOrderView(AppController appController) {
        this.title = "Create Order";
        this.currentCustomer = appController.getCustomerStore().get();

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
        JLabel titleLabel = new Typography.StyledTitleField("Create Order");
        gbc.gridy = 0;
        mainPanel.add(titleLabel, gbc);

        JLabel subtitleLabel = new Typography.StyledSubtitleField("Place your coffee order");
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 25, 10);
        mainPanel.add(subtitleLabel, gbc);

        // Left Column - Customer Information
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        gbc.gridx = 0;

        addSectionHeader(mainPanel, gbc, "Customer Information", 2, 0);

        firstNameField = new StyledInputs.StyledTextField(15, true, currentCustomer.getFirstName());
        addFormField(mainPanel, gbc, "First Name:", firstNameField, 3, 0);

        lastNameField = new StyledInputs.StyledTextField(15, true, currentCustomer.getLastName());
        addFormField(mainPanel, gbc, "Last Name:", lastNameField, 4, 0);

        // Right Column - Order Details
        gbc.gridx = 1;

        addSectionHeader(mainPanel, gbc, "Order Details", 2, 1);

        coffeeComboBox = new CoffeeSelectComboBox(appController);
        addFormField(mainPanel, gbc, "Select Coffee:", coffeeComboBox, 3, 1);

        quantitySpinner = new QuantitySpinner();
        addFormField(mainPanel, gbc, "Quantity:", quantitySpinner, 4, 1);

        // Price Summary (spans both columns)
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.gridy = 6;
        addSectionHeader(mainPanel, gbc, "Price Summary", 6, 0);

        JPanel pricePanel = createPricePanel();
        gbc.gridy = 7;
        gbc.insets = new Insets(15, 15, 25, 15);
        mainPanel.add(pricePanel, gbc);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonsPanel.setBackground(Color.WHITE);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JButton backButton = new StyledInputs.StyledButton("Back to Menu");
        buttonsPanel.add(backButton);

        JButton placeOrderButton = new StyledInputs.StyledButton("Place Order", new Color(79, 55, 48), Color.WHITE);
        buttonsPanel.add(placeOrderButton);

        gbc.gridy = 8;
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

    private void addSectionHeader(JPanel panel, GridBagConstraints gbc, String text, int gridy, int gridx) {
        JLabel header = new JLabel(text);
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.setForeground(new Color(79, 55, 48));
        gbc.gridy = gridy;
        gbc.gridx = gridx;
        gbc.insets = new Insets(20, 8, 8, 8);
        panel.add(header, gbc);
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, String labelText, JComponent field, int gridy,
            int gridx) {
        gbc.gridy = gridy;
        gbc.gridx = gridx;

        JPanel fieldPanel = new JPanel(new BorderLayout(0, 5));
        fieldPanel.setBackground(Color.WHITE);
        fieldPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 15));

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        fieldPanel.add(label, BorderLayout.NORTH);
        fieldPanel.add(field, BorderLayout.CENTER);

        panel.add(fieldPanel, gbc);
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