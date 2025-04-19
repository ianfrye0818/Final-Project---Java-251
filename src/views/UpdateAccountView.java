package views;

import components.StyledInputs;
import components.Typography;
import controllers.AppController;
import entites.Customer;
import enums.ViewType;
import listeners.AccountListeners;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UpdateAccountView extends SuperView {
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField streetField;
    private JTextField cityField;
    private JTextField stateField;
    private JTextField zipField;
    private JPasswordField passwordField;
    private JButton updateButton;
    private JButton backButton;

    private final List<Component> tabOrder = new ArrayList<>();

    public UpdateAccountView(AppController controller) {
        super(controller, "Update Account");
        if (!isCustomerPresent(ViewType.COFFEE_MENU_VIEW))
            return;
        Customer currentCustomer = controller.getLoggedinCustomerStore().get();

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
        this.firstNameField = new StyledInputs.StyledTextField();
        firstNameField.setText(currentCustomer.getFirstName());
        addFormField(mainPanel, gbc, "First Name:", firstNameField, 3, 0);

        // Last Name
        this.lastNameField = new StyledInputs.StyledTextField();
        lastNameField.setText(currentCustomer.getLastName());
        addFormField(mainPanel, gbc, "Last Name:", lastNameField, 4, 0);

        // Email
        this.emailField = new StyledInputs.StyledTextField();
        emailField.setText(currentCustomer.getEmail());
        addFormField(mainPanel, gbc, "Email:", emailField, 5, 0);

        // Phone
        this.phoneField = new StyledInputs.StyledTextField();
        phoneField.setText(currentCustomer.getPhone());
        addFormField(mainPanel, gbc, "Phone:", phoneField, 6, 0);

        // Right Column - Address Information
        gbc.gridx = 1;

        // Section Header - Address
        addSectionHeader(mainPanel, gbc, "Address Information", 2, 1);

        // Street
        this.streetField = new StyledInputs.StyledTextField();
        streetField.setText(currentCustomer.getStreet());
        addFormField(mainPanel, gbc, "Street:", streetField, 3, 1);

        // City
        this.cityField = new StyledInputs.StyledTextField();
        cityField.setText(currentCustomer.getCity());
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
        this.stateField = new StyledInputs.StyledTextField();
        stateField.setText(currentCustomer.getState());
        statePanel.add(stateField, BorderLayout.CENTER);
        stateZipPanel.add(statePanel);

        // Zip
        JPanel zipPanel = new JPanel(new BorderLayout(0, 5));
        zipPanel.setBackground(Color.WHITE);
        JLabel zipLabel = new JLabel("Zip:");
        zipLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        zipPanel.add(zipLabel, BorderLayout.NORTH);
        this.zipField = new StyledInputs.StyledTextField();
        zipField.setText(currentCustomer.getZip());
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
        this.passwordField = new StyledInputs.StyledPasswordField(20);
        passwordField.setText("NotRealPassword");
        gbc.gridy = 8;
        addFormField(mainPanel, gbc, "Password:", passwordField, 8, 0);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonsPanel.setBackground(Color.WHITE);

        backButton = new StyledInputs.StyledButton("Back to Menu");
        buttonsPanel.add(backButton);

        this.updateButton = new StyledInputs.PrimaryButton("Update Account");
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
        updateButton.addActionListener(listeners.getUpdateAccountButtonListener(currentCustomer.getCustomerId()));

        tabOrder.add(firstNameField);
        tabOrder.add(lastNameField);
        tabOrder.add(emailField);
        tabOrder.add(phoneField);
        tabOrder.add(streetField);
        tabOrder.add(cityField);
        tabOrder.add(stateField);
        tabOrder.add(zipField);
        tabOrder.add(passwordField);
        tabOrder.add(backButton);
        tabOrder.add(updateButton);

        setFocusTraversalPolicy(getFocusTraversalPolicy(tabOrder));
        setFocusTraversalKeysEnabled(true);
        getRootPane().setDefaultButton(updateButton);

        pack();
        setLocationRelativeTo(null);
    }

}