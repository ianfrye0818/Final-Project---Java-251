package views;

import controllers.AppController;
import enums.ViewType;
import listeners.MutateAccountListeners;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import components.StyledInputs;
import components.Typography;
import models.Customer;

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

  public UpdateAccountView(AppController appController) {
    this.title = "Update Account";
    this.customer = appController.getCustomerStore().get();

    if (customer == null) {
      JOptionPane.showMessageDialog(this, "No customer found", "Error", JOptionPane.ERROR_MESSAGE);
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

    JLabel titleLabel = new Typography.StyledTitleField("Update Account");
    gbc.gridy = 0;
    mainPanel.add(titleLabel, gbc);

    JLabel subtitleLabel = new Typography.StyledSubtitleField("Update your account information");
    gbc.gridy = 1;
    gbc.insets = new Insets(0, 10, 15, 10);
    mainPanel.add(subtitleLabel, gbc);

    // Email
    emailField = new StyledInputs.StyledTextField(20);
    emailField.setText(customer.getEmail());
    addFormField(mainPanel, gbc, "Email:", emailField,
        2, 0, 1.0, GridBagConstraints.REMAINDER);

    // Password
    passwordField = new StyledInputs.StyledPasswordField(20);
    passwordField.setText(
        "NotRealPassword");
    addFormField(mainPanel, gbc, "Password:", passwordField,
        4, 0, 1.0, GridBagConstraints.REMAINDER);

    // First Name
    firstNameField = new StyledInputs.StyledTextField(10);
    firstNameField.setText(customer.getFirstName());
    addFormField(mainPanel, gbc, "First Name:", firstNameField,
        6, 0, 1.0, GridBagConstraints.REMAINDER);

    // Last Name
    lastNameField = new StyledInputs.StyledTextField(10);
    lastNameField.setText(customer.getLastName());
    addFormField(mainPanel, gbc, "Last Name:", lastNameField,
        8, 0, 1.0, GridBagConstraints.REMAINDER);

    // Street
    streetField = new StyledInputs.StyledTextField(20);
    streetField.setText(customer.getStreet());
    addFormField(mainPanel, gbc, "Street:", streetField,
        10, 0, 1.0, GridBagConstraints.REMAINDER);

    // City, State, Zip in one row
    cityField = new StyledInputs.StyledTextField(10);
    cityField.setText(customer.getCity());
    addFormField(mainPanel, gbc, "City:", cityField,
        12, 0, 0.33, 1);

    stateField = new StyledInputs.StyledTextField(3);
    stateField.setText(customer.getState());
    addFormField(mainPanel, gbc, "State:", stateField,
        12, 1, 0.33, 1);

    zipField = new StyledInputs.StyledTextField(7);
    zipField.setText(customer.getZip());
    addFormField(mainPanel, gbc, "Zip:", zipField,
        12, 2, 0.34, 1);

    // Reset gridx and gridwidth for next row
    gbc.gridx = 0;
    gbc.gridwidth = GridBagConstraints.REMAINDER;

    // Phone
    phoneField = new StyledInputs.StyledTextField(20);
    phoneField.setText(customer.getPhone());
    addFormField(mainPanel, gbc, "Phone:", phoneField,
        14, 0, 1.0, GridBagConstraints.REMAINDER);

    // Buttons Panel
    JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
    buttonsPanel.setBackground(Color.WHITE);

    JButton backButton = new StyledInputs.StyledButton("Back to Menu");
    buttonsPanel.add(backButton);

    JButton updateButton = new StyledInputs.StyledButton("Update Account", new Color(79, 55, 48),
        Color.WHITE);
    buttonsPanel.add(updateButton);

    gbc.gridy = 16;
    mainPanel.add(buttonsPanel, gbc);

    add(mainPanel, BorderLayout.CENTER);

    // Create and add listeners
    MutateAccountListeners listeners = new MutateAccountListeners(
        appController,
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

    backButton.addActionListener(listeners.getBackButtonListener());
    updateButton.addActionListener(listeners.getMutateButtonListener());

    pack();
    setLocationRelativeTo(null);
  }

  @Override
  public String getTitle() {
    return super.getTitle() + " - " + title;
  }
}