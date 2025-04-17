package views;

import components.StyledInputs;
import components.Typography;
import controllers.AppController;
import enums.ViewType;
import listeners.AccountListeners;
import models.Customer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UpdateAccountView extends SuperView {
        private final String title;
        private final Customer customer;
        private JTextField emailField;
        private JPasswordField passwordField;
        private JTextField firstNameField;
        private JTextField lastNameField;
        private JTextField streetField;
        private JTextField cityField;
        private JTextField stateField;
        private JTextField zipField;
        private JTextField phoneField;

        public UpdateAccountView(AppController controller) {
                this.title = "Update Account";
                this.customer = controller.getCustomerStore().get();

                if (customer == null) {
                        JOptionPane.showMessageDialog(this, "No customer found", "Error", JOptionPane.ERROR_MESSAGE);
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

                // Header Section (spans both columns)
                JLabel titleLabel = new Typography.StyledTitleField("Update Account");
                gbc.gridy = 0;
                mainPanel.add(titleLabel, gbc);

                JLabel subtitleLabel = new Typography.StyledSubtitleField("Update your account information");
                gbc.gridy = 1;
                gbc.insets = new Insets(0, 10, 25, 10);
                mainPanel.add(subtitleLabel, gbc);

                // Left Column - Personal Information
                gbc.gridwidth = 1;
                gbc.weightx = 0.5;
                gbc.gridx = 0;

                // Section Header - Personal Information
                addSectionHeader(mainPanel, gbc, "Personal Information", 2, 0);

                // First Name
                firstNameField = new StyledInputs.StyledTextField(15);
                firstNameField.setText(customer.getFirstName());
                addFormField(mainPanel, gbc, "First Name:", firstNameField, 3, 0);

                // Last Name
                lastNameField = new StyledInputs.StyledTextField(15);
                lastNameField.setText(customer.getLastName());
                addFormField(mainPanel, gbc, "Last Name:", lastNameField, 4, 0);

                // Email
                emailField = new StyledInputs.StyledTextField(20);
                emailField.setText(customer.getEmail());
                addFormField(mainPanel, gbc, "Email:", emailField, 5, 0);

                // Phone
                phoneField = new StyledInputs.StyledTextField(15);
                phoneField.setText(customer.getPhone());
                addFormField(mainPanel, gbc, "Phone:", phoneField, 6, 0);

                // Right Column - Address Information
                gbc.gridx = 1;

                // Section Header - Address
                addSectionHeader(mainPanel, gbc, "Address Information", 2, 1);

                // Street
                streetField = new StyledInputs.StyledTextField(20);
                streetField.setText(customer.getStreet());
                addFormField(mainPanel, gbc, "Street:", streetField, 3, 1);

                // City
                cityField = new StyledInputs.StyledTextField(15);
                cityField.setText(customer.getCity());
                addFormField(mainPanel, gbc, "City:", cityField, 4, 1);

                // State and Zip (in one panel)
                JPanel stateZipPanel = new JPanel(new GridLayout(1, 2, 10, 0));
                stateZipPanel.setBackground(Color.WHITE);

                // State
                JPanel statePanel = new JPanel(new BorderLayout(0, 5));
                statePanel.setBackground(Color.WHITE);
                JLabel stateLabel = new JLabel("State:");
                stateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                statePanel.add(stateLabel, BorderLayout.NORTH);
                stateField = new StyledInputs.StyledTextField(2);
                stateField.setText(customer.getState());
                statePanel.add(stateField, BorderLayout.CENTER);
                stateZipPanel.add(statePanel);

                // Zip
                JPanel zipPanel = new JPanel(new BorderLayout(0, 5));
                zipPanel.setBackground(Color.WHITE);
                JLabel zipLabel = new JLabel("Zip:");
                zipLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                zipPanel.add(zipLabel, BorderLayout.NORTH);
                zipField = new StyledInputs.StyledTextField(7);
                zipField.setText(customer.getZip());
                zipPanel.add(zipField, BorderLayout.CENTER);
                stateZipPanel.add(zipPanel);

                gbc.gridy = 5;
                mainPanel.add(stateZipPanel, gbc);

                // Bottom Section - Account Security (spans both columns)
                gbc.gridx = 0;
                gbc.gridwidth = 2;
                gbc.gridy = 7;
                addSectionHeader(mainPanel, gbc, "Account Security", 7, 0);

                // Password
                passwordField = new StyledInputs.StyledPasswordField(20);
                passwordField.setText("NotRealPassword");
                gbc.gridy = 8;
                addFormField(mainPanel, gbc, "Password:", passwordField, 8, 0);

                // Buttons Panel
                JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
                buttonsPanel.setBackground(Color.WHITE);

                JButton backButton = new StyledInputs.StyledButton("Back to Menu");
                buttonsPanel.add(backButton);

                JButton updateButton = new StyledInputs.StyledButton("Update Account", new Color(79, 55, 48),
                                Color.WHITE);
                buttonsPanel.add(updateButton);

                gbc.gridy = 9;
                gbc.insets = new Insets(20, 8, 8, 8);
                mainPanel.add(buttonsPanel, gbc);

                add(mainPanel, BorderLayout.CENTER);

                // Create and add listeners
                AccountListeners listeners = new AccountListeners(
                                controller,
                                this,
                                firstNameField,
                                lastNameField,
                                streetField,
                                cityField,
                                stateField,
                                zipField,
                                phoneField,
                                emailField,
                                passwordField);

                backButton.addActionListener(listeners.getBackButtonListener(ViewType.COFFEE_MENU_VIEW));
                updateButton.addActionListener(listeners.getMutateButtonListener());

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

        private void addFormField(JPanel panel, GridBagConstraints gbc, String labelText, JTextField field, int gridy,
                        int gridx) {
                gbc.gridy = gridy;
                gbc.gridx = gridx;

                JPanel fieldPanel = new JPanel(new BorderLayout(0, 5));
                fieldPanel.setBackground(Color.WHITE);

                JLabel label = new JLabel(labelText);
                label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                fieldPanel.add(label, BorderLayout.NORTH);
                fieldPanel.add(field, BorderLayout.CENTER);

                panel.add(fieldPanel, gbc);
        }

        @Override
        public String getTitle() {
                return super.getTitle() + " - " + title;
        }
}