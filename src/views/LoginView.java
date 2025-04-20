package views;

import components.StyledInputs;
import components.Typography;
import listeners.LoginViewListeners;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * The initial view presented to the user, providing fields for email and
 * password to log into the application. It also includes a button to navigate
 * to the account creation view. This view utilizes a {@link GridBagLayout} for
 * organizing its components in a responsive manner.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */
public class LoginView extends SuperView {

    /**
     * Constructs the {@code LoginView}, initializing its UI components, layout,
     * and attaching the necessary action listeners for user interaction with
     * the login and create account functionalities.
     */
    public LoginView() {
        super("Login");

        setMinimumSize(new Dimension(400, 220));
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 245));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel titleLabel = new Typography.StyledTitleField("Welcome to Coffee Ordering");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);

        JLabel subtitleLabel = new Typography.StyledSubtitleField("Please log in to continue");
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 20, 10);
        mainPanel.add(subtitleLabel, gbc);

        // Email
        JTextField emailField = new StyledInputs.StyledTextField();
        addFormField(mainPanel, gbc, "Email:", emailField, 2);

        // Password
        JPasswordField passwordField = new StyledInputs.StyledPasswordField(20);
        addFormField(mainPanel, gbc, "Password:", passwordField, 4);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonsPanel.setBackground(Color.WHITE);

        JButton createAccountButton = new StyledInputs.StyledButton("Create Account");
        buttonsPanel.add(createAccountButton);

        JButton loginButton = new StyledInputs.PrimaryButton("Login");
        buttonsPanel.add(loginButton);

        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        mainPanel.add(buttonsPanel, gbc);

        add(mainPanel, BorderLayout.CENTER);

        LoginViewListeners listeners = new LoginViewListeners(controller, this, emailField, passwordField);

        createAccountButton.addActionListener(listeners.getCreateAccountButtonListener());

        loginButton.addActionListener(listeners.getLoginButtonListener());
        getRootPane().setDefaultButton(loginButton);
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Helper method to add a labeled form field (e.g., JTextField, JPasswordField)
     * to the panel using the provided GridBagConstraints.
     *
     * @param panel     The JPanel to which the form field is added.
     * @param gbc       The GridBagConstraints to control the layout of the field.
     * @param labelText The text for the label of the form field.
     * @param field     The JComponent (e.g., JTextField, JPasswordField) to be
     *                  added.
     * @param gridY     The grid y-coordinate where the label and field will be
     *                  placed.
     */
    private void addFormField(JPanel panel, GridBagConstraints gbc,
            String labelText, JComponent field,
            int gridY) {

        JLabel label = new Typography.StyledLabel(labelText);
        gbc.gridy = gridY;
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(label, gbc);

        gbc.gridy = gridY + 1;
        panel.add(field, gbc);
    }
}