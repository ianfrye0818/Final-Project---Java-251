package components;

import controllers.AppController;
import dto.UpdateCustomerDto;
import entites.Customer;
import stores.SelectedCustomerStore;
import stores.AuthStore;
import utils.DialogUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * A custom dialog for adding credit to a selected customer's account.
 * This dialog allows users to input an amount to increase the credit limit
 * of the currently selected customer, with validation to ensure the input
 * is a positive whole number and the new credit limit does not exceed $100.00.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */
public class AddCreditDialog extends JDialog {
    private final Customer currentCustomer;
    private final JTextField addCreditTextField;
    private final JLabel errorLabel;

    /**
     * Constructs an {@code AddCreditDialog} for the given parent frame and
     * application controller.
     * It retrieves the currently selected customer from the
     * {@link SelectedCustomerStore}
     * and sets up the UI components for displaying customer information and adding
     * credit.
     *
     * @param parentView The parent {@code JFrame} for this dialog.
     * @param controller The {@link AppController} providing access to customer
     *                   service and view management.
     */
    public AddCreditDialog(JFrame parentView, AppController controller) {
        super(parentView, "Add Credit", true);
        this.currentCustomer = SelectedCustomerStore.getInstance().get();

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

        addCreditTextField = new StyledInputs.StyledTextField();
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
        JButton addButton = new StyledInputs.PrimaryButton("Add Credit");

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
                    // errorLabel.setText("Please enter a positive amount.");
                    DialogUtils.showValidationError(parentView, "Please enter a positive amount.");
                    return;
                }

                // Validate that the credits entered are a whole number
                if (creditToAdd != Math.floor(creditToAdd)) {
                    // errorLabel.setText("Please enter a whole number amount.");
                    DialogUtils.showValidationError(parentView, "Please enter a whole number amount.");
                    return;
                }

                double newCreditLimit = currentCustomer.getCreditLimit() + creditToAdd;

                // Validate that current credits + added don't exceed 100.00 credits
                if (newCreditLimit > 100.00) {
                    // errorLabel.setText("Credit limit cannot exceed $100.00.");
                    DialogUtils.showValidationError(parentView, "Credit limit cannot exceed $100.00.");
                    return;
                }

                // If everything is successful, update the credit and close the dialog
                currentCustomer.setCreditLimit(newCreditLimit);
                Customer updatedCustomer = controller.getCustomerService()
                        .updateCustomer(UpdateCustomerDto.fromCustomer(currentCustomer));
                if (updatedCustomer != null) {
                    // If the updated customer is the currently logged-in user, update the AuthStore
                    Customer loggedInUser = AuthStore.getInstance().get();
                    if (loggedInUser != null && loggedInUser.getCustomerId() == updatedCustomer.getCustomerId()) {
                        AuthStore.getInstance().set(updatedCustomer);
                    }
                    dispose();
                    controller.getViewManager().refreshCurrentView();
                } else {
                    DialogUtils.showError(parentView, "Failed to add credits. Please try again.");
                }

            } catch (NumberFormatException ex) {
                DialogUtils.showValidationError(parentView, "Please enter a valid number.");
            }
        });

        cancelButton.addActionListener(e -> dispose());

        getRootPane().setDefaultButton(addButton);

        // Final dialog setup
        setMinimumSize(new Dimension(400, 0));
        pack();
        setLocationRelativeTo(parentView);
    }
}