package views;

import components.StyledInputs;
import components.Typography;
import controllers.AppController;
import listeners.LoginViewListeners;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginView extends SuperView {
    public LoginView(AppController controller) {
        super(controller, "Login");

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