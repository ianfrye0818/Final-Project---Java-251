package views;

import controllers.AppController;
import listeners.MutateCoffeeListeners;
import components.StyledInputs;
import components.Typography;
import models.Coffee;
import enums.ViewType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UpdateCoffeeView extends SuperView {
    private final String title;
    private final Coffee selectedCoffee;
    private JTextField nameField;
    private JTextArea descriptionField;
    private JTextField priceField;
    private JCheckBox inStockBox;

    public UpdateCoffeeView(AppController appController) {
        this.title = "Update Coffee";
        this.selectedCoffee = appController.getCoffeeStore().get();

        if (selectedCoffee == null) {
            JOptionPane.showMessageDialog(this, "No coffee selected", "Error", JOptionPane.ERROR_MESSAGE);
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

        // Title and Subtitle
        JLabel titleLabel = new Typography.StyledTitleField("Update Coffee");
        gbc.gridy = 0;
        mainPanel.add(titleLabel, gbc);

        JLabel subtitleLabel = new Typography.StyledSubtitleField("Update coffee details");
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 15, 10);
        mainPanel.add(subtitleLabel, gbc);

        // Name Field
        nameField = new StyledInputs.StyledTextField(20);
        nameField.setText(selectedCoffee.getCoffeeName());
        addFormField(mainPanel, gbc, "Coffee Name:", nameField,
                2, 0, 1.0, GridBagConstraints.REMAINDER);

        // Description Field
        descriptionField = new StyledInputs.StyledTextArea(20);
        descriptionField.setText(selectedCoffee.getCoffeeDescription());
        JScrollPane scrollPane = new JScrollPane(descriptionField);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(122, 122, 122)));

        // Add label
        gbc.gridy = 4;
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        mainPanel.add(descriptionLabel, gbc);

        // Add scroll pane containing text area
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.BOTH; // Allow vertical growth
        gbc.weighty = 1.0; // Give extra vertical space to this component
        mainPanel.add(scrollPane, gbc);

        // Reset constraints for next components
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        // Price Field
        priceField = new StyledInputs.StyledTextField(10);
        priceField.setText(String.format("%.2f", selectedCoffee.getPrice()));
        addFormField(mainPanel, gbc, "Price ($):", priceField,
                6, 0, 1.0, GridBagConstraints.REMAINDER);

        // In Stock Checkbox
        inStockBox = new JCheckBox("In Stock");
        inStockBox.setSelected(selectedCoffee.getIsInStock());
        inStockBox.setBackground(Color.WHITE);
        inStockBox.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridy = 8;
        mainPanel.add(inStockBox, gbc);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonsPanel.setBackground(Color.WHITE);

        JButton backButton = new StyledInputs.StyledButton("Back to Menu");
        buttonsPanel.add(backButton);

        JButton updateButton = new StyledInputs.StyledButton("Update Coffee", new Color(79, 55, 48), Color.WHITE);
        buttonsPanel.add(updateButton);

        gbc.gridy = 9;
        mainPanel.add(buttonsPanel, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Add listeners
        MutateCoffeeListeners listeners = new MutateCoffeeListeners(
                appController,
                this,
                nameField,
                descriptionField,
                priceField,
                inStockBox);

        backButton.addActionListener(listeners.getBackButtonListener());
        updateButton.addActionListener(listeners.getMutationButtonListener());

        pack();
        setLocationRelativeTo(null);
    }

    @Override
    public String getTitle() {
        return super.getTitle() + " - " + title;
    }
}