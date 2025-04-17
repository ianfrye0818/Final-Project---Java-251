package components;

import controllers.AppController;
import enums.ViewType;
import models.Customer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AddCreditDialog extends JDialog {
    private final Customer currentCustomer;
    private JTextField addCreditTextField;
    private JLabel errorLabel;

    public AddCreditDialog(JFrame parentView, AppController controller, Customer customer) {
        super(parentView, "Add Credit", true);
        this.currentCustomer = customer;

        // Set dialog styling
        setBackground(new Color(245, 245, 245));
        getRootPane().setBorder(new EmptyBorder(20, 20, 20, 20));

        // Main panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        // Title
        JLabel titleLabel = new Typography.StyledTitleField("Add Credit");
        gbc.gridy = 0;
        mainPanel.add(titleLabel, gbc);

        // Customer Info Panel
        JPanel customerInfoPanel = new JPanel(new GridBagLayout());
        customerInfoPanel.setBackground(Color.WHITE);
        customerInfoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        GridBagConstraints infogbc = new GridBagConstraints();
        infogbc.insets = new Insets(5, 5, 5, 5);
        infogbc.fill = GridBagConstraints.HORIZONTAL;
        infogbc.weightx = 1.0;
        infogbc.gridwidth = GridBagConstraints.REMAINDER;

        // Customer Email
        JLabel emailLabel = new JLabel("Customer:");
        emailLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        infogbc.gridy = 0;
        customerInfoPanel.add(emailLabel, infogbc);

        JLabel emailValue = new JLabel(currentCustomer.getEmail());
        emailValue.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        infogbc.gridy = 1;
        customerInfoPanel.add(emailValue, infogbc);

        // Current Credit
        JLabel currentCreditLabel = new JLabel("Current Credit:");
        currentCreditLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        infogbc.gridy = 2;
        infogbc.insets = new Insets(15, 5, 5, 5);
        customerInfoPanel.add(currentCreditLabel, infogbc);

        JLabel creditValue = new JLabel(String.format("$%.2f", currentCustomer.getCreditLimit()));
        creditValue.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        infogbc.gridy = 3;
        infogbc.insets = new Insets(5, 5, 5, 5);
        customerInfoPanel.add(creditValue, infogbc);

        // Add customer info panel to main panel
        gbc.gridy = 1;
        gbc.insets = new Insets(15, 8, 15, 8);
        mainPanel.add(customerInfoPanel, gbc);

        // Add Credit Input
        JLabel addCreditLabel = new JLabel("Amount to Add ($):");
        addCreditLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridy = 2;
        gbc.insets = new Insets(8, 8, 4, 8);
        mainPanel.add(addCreditLabel, gbc);

        addCreditTextField = new StyledInputs.StyledTextField(10);
        gbc.gridy = 3;
        mainPanel.add(addCreditTextField, gbc);

        // Error Label
        errorLabel = new JLabel("");
        errorLabel.setForeground(new Color(220, 53, 69)); // Bootstrap danger red
        errorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        gbc.gridy = 4;
        gbc.insets = new Insets(4, 8, 15, 8);
        mainPanel.add(errorLabel, gbc);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(Color.WHITE);

        JButton cancelButton = new StyledInputs.StyledButton("Cancel");
        JButton addButton = new StyledInputs.StyledButton("Add Credit", new Color(79, 55, 48), Color.WHITE);

        buttonPanel.add(cancelButton);
        buttonPanel.add(addButton);

        gbc.gridy = 5;
        mainPanel.add(buttonPanel, gbc);

        // Add main panel to dialog
        add(mainPanel);

        // Button Actions
        addButton.addActionListener(e -> {
            try {
                double creditToAdd = Double.parseDouble(addCreditTextField.getText());

                // Validate that the credits entered are a positive number
                if (creditToAdd <= 0) {
                    errorLabel.setText("Please enter a positive amount.");
                    return;
                }

                // Validate that the credits entered are a whole number
                if (creditToAdd != Math.floor(creditToAdd)) {
                    errorLabel.setText("Please enter a whole number amount.");
                    return;
                }

                double newCreditLimit = currentCustomer.getCreditLimit() + creditToAdd;

                // Validate that current credits + added don't exceed 100.00 credits
                if (newCreditLimit > 100.00) {
                    errorLabel.setText("Credit limit cannot exceed $100.00.");
                    return;
                }

                // If everything is successful, update the credit and close the dialog
                currentCustomer.setCreditLimit(newCreditLimit);
                boolean updated = controller.getCustomerService().updateCustomer(currentCustomer);
                if (updated) {
                    dispose();
                    controller.setDisplay(ViewType.COFFEE_MENU_VIEW);
                } else {
                    errorLabel.setText("Failed to add credits. Please try again.");
                }

            } catch (NumberFormatException ex) {
                errorLabel.setText("Please enter a valid number.");
            }
        });

        cancelButton.addActionListener(e -> dispose());

        // Final dialog setup
        setMinimumSize(new Dimension(400, 0));
        pack();
        setLocationRelativeTo(parentView);
    }
}
