package views;

import components.StyledInputs;
import components.Typography;
import entites.Customer;
import listeners.CustomerDetailsListeners;
import stores.SelectedCustomerStore;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CustomerDetailView extends SuperView {

    public CustomerDetailView() {
        super("Customer Details");
        Customer selectedCustomer = SelectedCustomerStore.getInstance().get();

        CustomerDetailsListeners listeners = new CustomerDetailsListeners(controller, this);
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
        gbc.gridwidth = 2;

        // Header Section (spans both columns)
        JLabel titleLabel = new Typography.StyledTitleField("Customer Details");
        gbc.gridy = 0;
        mainPanel.add(titleLabel, gbc);

        JLabel subtitleLabel = new Typography.StyledSubtitleField(
                String.format("%s %s", selectedCustomer.getFirstName(), selectedCustomer.getLastName()));
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 25, 10);
        mainPanel.add(subtitleLabel, gbc);

        // Left Column
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;

        // Contact Information Section (Left)
        addSectionHeader(mainPanel, gbc, "Contact Information", 2, 0);
        addReadOnlyField(mainPanel, gbc, "Email:", selectedCustomer.getEmail(), 3, 0);
        addReadOnlyField(mainPanel, gbc, "Phone:", selectedCustomer.getPhone(), 5, 0);

        // Right Column
        gbc.gridx = 1;

        // Address Section (Right)
        addSectionHeader(mainPanel, gbc, "Address", 2, 1);
        addReadOnlyField(mainPanel, gbc, "Street:", selectedCustomer.getStreet(), 3, 1);

        // City, State, Zip panel
        JPanel locationPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        locationPanel.setBackground(Color.WHITE);

        addReadOnlyFieldToPanel(locationPanel, "City:", selectedCustomer.getCity());
        addReadOnlyFieldToPanel(locationPanel, "State:", selectedCustomer.getState());
        addReadOnlyFieldToPanel(locationPanel, "Zip:", selectedCustomer.getZip());

        gbc.gridy = 5;
        mainPanel.add(locationPanel, gbc);

        // Account Information Section (spans both columns)
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.gridy = 7;
        addSectionHeader(mainPanel, gbc, "Account Information", 7, 0);

        // Credit Limit (centered)
        JPanel creditPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        creditPanel.setBackground(Color.WHITE);
        JLabel creditLabel = new JLabel(String.format("Available Credit: $%.2f", selectedCustomer.getCreditLimit()));
        creditLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        creditLabel.setForeground(new Color(79, 55, 48));
        creditPanel.add(creditLabel);

        gbc.gridy = 8;
        mainPanel.add(creditPanel, gbc);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonsPanel.setBackground(Color.WHITE);

        JButton backButton = new StyledInputs.StyledButton("Back to Menu");
        backButton.addActionListener(listeners.getBackButtonListener(controller.getViewManager().getPreviousView()));
        buttonsPanel.add(backButton);

        JButton deleteAccountButton = new StyledInputs.DestructiveButton("Delete Account");
        deleteAccountButton.addActionListener(listeners.getDeleteAccountButtonListener(selectedCustomer.getCustomerId(),
                controller.getViewManager().getPreviousView()));
        buttonsPanel.add(deleteAccountButton);

        JButton addCreditButton = new StyledInputs.PrimaryButton("Add Credit");
        addCreditButton.addActionListener(listeners.getAddCreditButtonListener(selectedCustomer.getCustomerId()));
        buttonsPanel.add(addCreditButton);

        gbc.gridy = 9;
        gbc.insets = new Insets(20, 8, 8, 8);
        mainPanel.add(buttonsPanel, gbc);

        add(mainPanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
    }

    private void addReadOnlyField(JPanel panel, GridBagConstraints gbc, String labelText, String value, int gridy,
                                  int gridx) {
        gbc.gridy = gridy;
        gbc.gridx = gridx;

        JPanel fieldPanel = new JPanel(new BorderLayout(0, 5));
        fieldPanel.setBackground(Color.WHITE);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        fieldPanel.add(label, BorderLayout.NORTH);

        JTextField field = new StyledInputs.StyledTextField(true, value);
        fieldPanel.add(field, BorderLayout.CENTER);

        panel.add(fieldPanel, gbc);
    }

    private void addReadOnlyFieldToPanel(JPanel panel, String labelText, String value) {
        JPanel fieldPanel = new JPanel(new GridLayout(2, 1, 0, 4));
        fieldPanel.setBackground(Color.WHITE);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        fieldPanel.add(label);

        JTextField field = new StyledInputs.StyledTextField(true, value);
        fieldPanel.add(field);

        panel.add(fieldPanel);
    }

}
