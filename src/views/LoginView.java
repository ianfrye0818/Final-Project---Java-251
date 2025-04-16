package views;

import controllers.AppController;
import listeners.LoginActionListeners;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import components.StyledInputs;
import components.Typography;

import java.awt.*;

public class LoginView extends SuperView {
    private String title;
    private JTextField emailField;
    private JPasswordField passwordField;

    public LoginView(AppController appController) {
        this.title = "Login";

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(400, 220)); // Slightly increased height
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

        JLabel titleLablel = new Typography.StyledTitleField("Welcome to Coffee Ordering");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLablel, gbc);

        JLabel subtitleLabel = new Typography.StyledSubtitleField("Please log in to continue");
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 20, 10);
        mainPanel.add(subtitleLabel, gbc);

        // Email
        emailField = new StyledInputs.StyledTextField(20);
        addFormField(mainPanel, gbc, "Email:", emailField, 2, 0, 1.0, GridBagConstraints.REMAINDER);

        // Password
        passwordField = new StyledInputs.StyledPasswordField(20);
        addFormField(mainPanel, gbc, "Password:", passwordField, 4, 0, 1.0, GridBagConstraints.REMAINDER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonsPanel.setBackground(Color.WHITE);

        JButton createAccountButton = new StyledInputs.StyledButton("Create Account");
        buttonsPanel.add(createAccountButton);

        JButton loginButton = new StyledInputs.StyledButton("Login", new Color(79, 55, 48), Color.WHITE, 14);

        buttonsPanel.add(loginButton);

        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        mainPanel.add(buttonsPanel, gbc);

        add(mainPanel, BorderLayout.CENTER);

        LoginActionListeners listeners = new LoginActionListeners(appController, this, emailField, passwordField);

        createAccountButton.addActionListener(listeners.getCreateAccountButtonListener());

        loginButton.addActionListener(listeners.getLoginButtonListener());
        pack();
        setLocationRelativeTo(null);
    }

    @Override
    public String getTitle() {
        return super.getTitle() + " - " + title;
    }
}