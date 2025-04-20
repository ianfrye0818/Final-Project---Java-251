package views;

import components.StyledInputs;
import components.Typography;
import listeners.CoffeeListeners;
import stores.CoffeeStore;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * The view for updating the details of an existing coffee item. It retrieves
 * the selected coffee's information from the {@link CoffeeStore} and populates
 * the form fields. Administrators can modify the coffee's name, price,
 * availability
 * ("In Stock"), and description. Buttons are provided to submit the updates,
 * delete the coffee item, and navigate back to the coffee menu. This view
 * utilizes a {@link GridBagLayout} for flexible layout management and reuses
 * custom styled input components.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */
public class UpdateCoffeeView extends SuperView {

    /**
     * Constructs the {@code UpdateCoffeeView}, initializing its UI components,
     * layout, and pre-filling the form fields with the selected coffee's
     * information
     * obtained from the {@link CoffeeStore}. It also attaches the necessary action
     * listeners for updating, deleting, and navigating back.
     */
    public UpdateCoffeeView() {
        super("Update Coffee");
        CoffeeStore coffeeStore = CoffeeStore.getInstance();
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
        JLabel titleLabel = new Typography.StyledTitleField(
                "Update Coffee - " + coffeeStore.get().getName());
        gbc.gridy = 0;
        mainPanel.add(titleLabel, gbc);

        JLabel subtitleLabel = new Typography.StyledSubtitleField("Update the coffee details");
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 25, 10);
        mainPanel.add(subtitleLabel, gbc);

        // Left Column - Basic Information
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        gbc.gridx = 0;

        addSectionHeader(mainPanel, gbc, "Basic Information", 2, 0);

        // Name Field
        JTextField nameField = new StyledInputs.StyledTextField();
        nameField.setText(coffeeStore.get().getName());
        addFormField(mainPanel, gbc, "Coffee Name:", nameField, 3, 0);

        // Price Field
        JTextField priceField = new StyledInputs.StyledTextField();
        priceField.setText(String.valueOf(coffeeStore.get().getPrice()));
        addFormField(mainPanel, gbc, "Price ($):", priceField, 4, 0);

        // In Stock Checkbox with custom panel
        JPanel stockPanel = new JPanel(new BorderLayout(0, 5));
        stockPanel.setBackground(Color.WHITE);
        stockPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 15));

        JLabel stockLabel = new JLabel("Availability:");
        stockLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        stockPanel.add(stockLabel, BorderLayout.NORTH);

        JCheckBox inStockBox = new JCheckBox("In Stock");
        inStockBox.setSelected(coffeeStore.get().getIsInStock());
        inStockBox.setBackground(Color.WHITE);
        inStockBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        stockPanel.add(inStockBox, BorderLayout.CENTER);

        gbc.gridy = 5;
        mainPanel.add(stockPanel, gbc);

        // Right Column - Description
        gbc.gridx = 1;
        gbc.gridy = 2;
        addSectionHeader(mainPanel, gbc, "Description", 2, 1);

        // Description Field
        JTextArea descriptionField = new StyledInputs.StyledTextArea(20);
        descriptionField.setText(coffeeStore.get().getDescription());
        descriptionField.setRows(8); // Set a fixed number of rows
        JScrollPane scrollPane = new JScrollPane(descriptionField);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(122, 122, 122)));

        gbc.gridy = 3;
        gbc.gridheight = 3; // Span multiple rows
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        mainPanel.add(scrollPane, gbc);

        // Reset constraints for buttons
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonsPanel.setBackground(Color.WHITE);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        JButton backButton = new StyledInputs.StyledButton("Back to Menu");
        buttonsPanel.add(backButton);

        JButton updateButton = new StyledInputs.PrimaryButton("Update Coffee");
        buttonsPanel.add(updateButton);

        JButton deleteButton = new StyledInputs.DestructiveButton("Delete Coffee");
        buttonsPanel.add(deleteButton);

        mainPanel.add(buttonsPanel, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Add listeners
        CoffeeListeners listeners = new CoffeeListeners(
                controller,
                this,
                nameField,
                descriptionField,
                priceField,
                inStockBox);

        backButton.addActionListener(listeners.getBackButtonListener());
        updateButton.addActionListener(listeners.getUpdateButtonListener(coffeeStore.get().getCoffeeId()));
        deleteButton.addActionListener(listeners.getDeleteButtonListener(coffeeStore.get().getCoffeeId()));
        pack();
        setLocationRelativeTo(null);
    }
}