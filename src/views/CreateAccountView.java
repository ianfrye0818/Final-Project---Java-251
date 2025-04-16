package views;

import controllers.AppController;
import listeners.MutateAccountListeners;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import components.StyledInputs;
import components.Typography;

import java.awt.*;

public class CreateAccountView extends SuperView {

        private final String title;
        private JTextField emailField;
        private JPasswordField passwordField;
        private JTextField firstNameField;
        private JTextField lastNameField;
        private JTextField streetField;
        private JTextField cityField;
        private JTextField stateField;
        private JTextField zipField;
        private JTextField phoneField;

        public CreateAccountView(AppController appController) {
                this.title = "Create Account";

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
                gbc.gridwidth = GridBagConstraints.REMAINDER; // Default to single column

                JLabel titleLabel = new Typography.StyledTitleField("Create Account");
                gbc.gridy = 0;
                mainPanel.add(titleLabel, gbc);

                JLabel subtitleLabel = new Typography.StyledSubtitleField("Please fill in the following details");
                gbc.gridy = 1;
                gbc.insets = new Insets(0, 10, 15, 10);
                mainPanel.add(subtitleLabel, gbc);
                // Email
                emailField = new StyledInputs.StyledTextField(20);
                addFormField(mainPanel, gbc, "Email:", emailField,
                                2, 0, 1.0, GridBagConstraints.REMAINDER);

                // Password
                passwordField = new StyledInputs.StyledPasswordField(20);
                addFormField(mainPanel, gbc, "Password:", passwordField,
                                4, 0, 1.0, GridBagConstraints.REMAINDER);

                // First Name
                firstNameField = new StyledInputs.StyledTextField(10);
                addFormField(mainPanel, gbc, "First Name:", firstNameField,
                                6, 0, 1.0, GridBagConstraints.REMAINDER);

                // Last Name
                lastNameField = new StyledInputs.StyledTextField(10);
                addFormField(mainPanel, gbc, "Last Name:", lastNameField,
                                8, 0, 1.0, GridBagConstraints.REMAINDER);

                // Street
                streetField = new StyledInputs.StyledTextField(20);
                addFormField(mainPanel, gbc, "Street:", streetField,
                                10, 0, 1.0, GridBagConstraints.REMAINDER);

                // City, State, Zip in one row
                cityField = new StyledInputs.StyledTextField(10);
                addFormField(mainPanel, gbc, "City:", cityField,
                                12, 0, 0.33, 1);

                stateField = new StyledInputs.StyledTextField(3);
                addFormField(mainPanel, gbc, "State:", stateField,
                                12, 1, 0.33, 1);

                zipField = new StyledInputs.StyledTextField(7);
                addFormField(mainPanel, gbc, "Zip:", zipField,
                                12, 2, 0.34, 1);

                // Reset gridx and gridwidth for next row
                gbc.gridx = 0;
                gbc.gridwidth = GridBagConstraints.REMAINDER;

                // Phone
                phoneField = new StyledInputs.StyledTextField(20);
                addFormField(mainPanel, gbc, "Phone:", phoneField,
                                14, 0, 1.0, GridBagConstraints.REMAINDER);

                // Buttons Panel
                JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
                buttonsPanel.setBackground(Color.WHITE);

                JButton backButton = new StyledInputs.StyledButton("Back To Login");
                buttonsPanel.add(backButton);

                JButton createButton = new StyledInputs.StyledButton("Create Account", new Color(79, 55, 48),
                                Color.WHITE);
                buttonsPanel.add(createButton);

                gbc.gridy = 16;
                mainPanel.add(buttonsPanel, gbc);

                add(mainPanel, BorderLayout.CENTER);

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
                createButton.addActionListener(listeners.getMutateButtonListener());

        }

        @Override
        public String getTitle() {
                return super.getTitle() + " - " + title;
        }

}