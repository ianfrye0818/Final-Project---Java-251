package views;

import components.CoffeeSelectComboBox;
import components.QuantitySpinner;
import components.StyledInputs;
import components.Typography;
import entites.Coffee;
import listeners.CreateOrderViewListeners;
import stores.AuthStore;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * The view for creating a new coffee order. It presents the logged-in
 * customer's
 * information, a dropdown to select a coffee, a spinner to specify the
 * quantity,
 * and a dynamic price summary including subtotal, tax, and total. It includes
 * buttons to navigate back to the main menu and to place the order. This view
 * retrieves the current user's information from the {@link AuthStore} and
 * dynamically updates the price based on the selected coffee and quantity.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */
public class CreateOrderView extends SuperView {
    private JLabel subtotalLabel;
    private JLabel taxLabel;
    private JLabel totalLabel;

    /**
     * Constructs the {@code CreateOrderView}, initializing its UI components,
     * layout, and attaching the necessary action listeners for user interaction,
     * including updating the price based on coffee selection and quantity.
     */
    public CreateOrderView() {
        super("Create Order");
        AuthStore authStore = AuthStore.getInstance();

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

        JTextField firstNameField = new StyledInputs.StyledTextField(true,
                authStore.get() != null ? authStore.get().getFirstName() : "");
        addFormField(mainPanel, gbc, "First Name:", firstNameField, 3, 0);

        JTextField lastNameField = new StyledInputs.StyledTextField(true,
                authStore.get() != null ? authStore.get().getLastName() : "");
        addFormField(mainPanel, gbc, "Last Name:", lastNameField, 4, 0);

        // Right Column - Order Details
        gbc.gridx = 1;

        addSectionHeader(mainPanel, gbc, "Order Details", 2, 1);

        JComboBox<Coffee> coffeeComboBox = new CoffeeSelectComboBox(controller);
        addFormField(mainPanel, gbc, "Select Coffee:", coffeeComboBox, 3, 1);

        JSpinner quantitySpinner = new QuantitySpinner();
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

        JButton placeOrderButton = new StyledInputs.PrimaryButton("Place Order");
        buttonsPanel.add(placeOrderButton);

        gbc.gridy = 8;
        mainPanel.add(buttonsPanel, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Add listeners
        CreateOrderViewListeners listeners = new CreateOrderViewListeners(
                controller,
                this,
                coffeeComboBox,
                quantitySpinner,
                subtotalLabel,
                taxLabel,
                totalLabel);

        backButton.addActionListener(listeners.getBackButtonListener());
        placeOrderButton.addActionListener(listeners.getPlaceOrderButtonListener());
        coffeeComboBox.addItemListener(listeners.getCoffeeSelectionListener());
        quantitySpinner.addChangeListener(e -> listeners.updateOrderPrice());

        // Initial price update
        if (coffeeComboBox.getItemCount() > 0) {
            listeners.updateOrderPrice();
        }

        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Helper method to add a labeled form field to the panel.
     *
     * @param panel     The panel to add the field to.
     * @param gbc       The {@code GridBagConstraints} to use for layout.
     * @param labelText The text for the label.
     * @param field     The {@code JComponent} to add (e.g., JTextField, JComboBox,
     *                  JSpinner).
     * @param gridy     The grid y-coordinate for the field.
     * @param gridx     The grid x-coordinate for the field.
     */
    private void addFormField(JPanel panel, GridBagConstraints gbc, String labelText, JComponent field, int gridy,
            int gridx) {
        gbc.gridy = gridy;
        gbc.gridx = gridx;

        CreateCoffeeView.setFormFieldProps(panel, gbc, labelText, field);
    }

    /**
     * Helper method to create the panel displaying the price summary.
     *
     * @return A {@code JPanel} containing labels for subtotal, tax, and total
     *         amounts.
     */
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

    /**
     * Helper method to add a row to the price panel.
     *
     * @param panel      The panel to add the row to.
     * @param labelText  The text for the label (e.g., "Subtotal:").
     * @param valueLabel The {@code JLabel} to display the price value.
     */
    private void addPriceRow(JPanel panel, String labelText, JLabel valueLabel) {
        addPriceRow(panel, labelText, valueLabel, false);
    }

    /**
     * Helper method to add a row to the price panel with an option to make the
     * label and value bold.
     *
     * @param panel      The panel to add the row to.
     * @param labelText  The text for the label.
     * @param valueLabel The {@code JLabel} to display the price value.
     * @param isBold     A boolean indicating whether the label and value should be
     *                   bold.
     */
    protected void addPriceRow(JPanel panel, String labelText, JLabel valueLabel, boolean isBold) {
        JLabel label = new JLabel(labelText);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        valueLabel.setHorizontalAlignment(SwingConstants.RIGHT); // Right align the values

        checkIsBold(isBold, label, valueLabel);

        // Add some horizontal padding to the price values
        valueLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

        panel.add(label);
        panel.add(valueLabel);
    }
}