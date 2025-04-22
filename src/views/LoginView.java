package views;

import components.StyledInputs;
import components.Typography;
import listeners.LoginViewListeners;
import services.ImageIconService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.net.URL;

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

        ImageIcon logoIcon = ImageIconService.getImageIcon();

        if (logoIcon != null) {
            ImageIcon resizedIcon = ImageIconService.resizeImageIcon(logoIcon, 100, 100);
            JLabel logoLabel = new JLabel(resizedIcon);
            logoLabel.setHorizontalAlignment(JLabel.CENTER);

            gbc.gridy = 0;
            gbc.gridx = 0;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            mainPanel.add(logoLabel, gbc);

            gbc.gridy = 1;
        }

        JLabel titleLabel = new Typography.StyledTitleField("Welcome to Coffee Ordering");
        gbc.gridx = 0;
        // gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);

        JLabel subtitleLabel = new Typography.StyledSubtitleField(
                "Please log in to continue");
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 10, 20, 10);
        mainPanel.add(subtitleLabel, gbc);

        // Email
        JTextField emailField = new StyledInputs.StyledTextField();

        addFormField(mainPanel, gbc, "Email:", emailField, 3);

        // Password
        JPasswordField passwordField = new StyledInputs.StyledPasswordField(20);
        addFormField(mainPanel, gbc, "Password:", passwordField, 5);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonsPanel.setBackground(Color.WHITE);

        JButton createAccountButton = new StyledInputs.StyledButton("Create Account");
        buttonsPanel.add(createAccountButton);

        JButton loginButton = new StyledInputs.PrimaryButton("Login");
        buttonsPanel.add(loginButton);

        gbc.gridy = 7;
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

    // public ImageIcon getLogoIcon() {
    // ImageIcon logoIcon = null;
    // try {
    // URL imageUrl = getClass().getResource("/assets/images/logo.png");
    // if (imageUrl != null) {
    // logoIcon = new ImageIcon(imageUrl);
    // } else {
    // File imageFile = new File("src/assets/images/logo.png");
    // if (imageFile.exists()) {
    // logoIcon = new ImageIcon(imageFile.getAbsolutePath());
    // }
    // }
    // return logoIcon;
    // } catch (Exception ex) {
    // System.err.println("Error loading logo icon: " + ex.getMessage());
    // return null;
    // }
    // }

    // public void setImageIcon(ImageIcon icon, JPanel mainPanel) {
    // if (icon != null) {
    // Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
    // icon = new ImageIcon(img);
    // JLabel logoLabel = new JLabel(icon);
    // logoLabel.setHorizontalAlignment(JLabel.CENTER);

    // GridBagConstraints gbc = new GridBagConstraints();
    // gbc.gridy = 0;
    // gbc.gridx = 0;
    // gbc.gridwidth = 2;
    // gbc.anchor = GridBagConstraints.CENTER;
    // mainPanel.add(logoLabel, gbc);

    // gbc.gridy = 1;
    // } else {
    // System.err.println("Failed to set image icon");
    // }
    // }
}