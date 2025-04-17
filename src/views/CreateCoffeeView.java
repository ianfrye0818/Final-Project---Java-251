package views;

import components.StyledInputs;
import components.Typography;
import controllers.AppController;
import listeners.CoffeeListeners;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CreateCoffeeView extends SuperView {
    private final String title;
    private JTextField nameField;
    private JTextArea descriptionField;
    private JTextField priceField;
    private JCheckBox inStockBox;

    public CreateCoffeeView(AppController appController) {
        this.title = "Create Coffee";

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

        // Title Section (spans both columns)
        JLabel titleLabel = new Typography.StyledTitleField("Create New Coffee");
        gbc.gridy = 0;
        mainPanel.add(titleLabel, gbc);

        JLabel subtitleLabel = new Typography.StyledSubtitleField("Add a new coffee to the menu");
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 25, 10);
        mainPanel.add(subtitleLabel, gbc);

        // Left Column - Basic Information
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        gbc.gridx = 0;

        addSectionHeader(mainPanel, gbc, "Basic Information", 2, 0);

        // Name Field
        nameField = new StyledInputs.StyledTextField(20);
        addFormField(mainPanel, gbc, "Coffee Name:", nameField, 3, 0);

        // Price Field
        priceField = new StyledInputs.StyledTextField(10);
        addFormField(mainPanel, gbc, "Price ($):", priceField, 4, 0);

        // In Stock Checkbox with custom panel
        JPanel stockPanel = new JPanel(new BorderLayout(0, 5));
        stockPanel.setBackground(Color.WHITE);
        stockPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 15));

        JLabel stockLabel = new JLabel("Availability:");
        stockLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        stockPanel.add(stockLabel, BorderLayout.NORTH);

        inStockBox = new JCheckBox("In Stock");
        inStockBox.setSelected(true);
        inStockBox.setBackground(Color.WHITE);
        inStockBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        stockPanel.add(inStockBox, BorderLayout.CENTER);

        gbc.gridy = 5;
        mainPanel.add(stockPanel, gbc);

        // Right Column - Description
        gbc.gridx = 1;
        gbc.gridy = 2;
        addSectionHeader(mainPanel, gbc, "Description", 2, 1);

        // Description Field
        descriptionField = new StyledInputs.StyledTextArea(20);
        descriptionField.setRows(8); // Set a fixed number of rows
        JScrollPane scrollPane = new JScrollPane(descriptionField);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(122, 122, 122)));

        gbc.gridy = 3;
        gbc.gridheight = 3; // Span multiple rows
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        mainPanel.add(scrollPane, gbc);

        // Reset constraints for buttons
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonsPanel.setBackground(Color.WHITE);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        JButton backButton = new StyledInputs.StyledButton("Back to Menu");
        buttonsPanel.add(backButton);

        JButton createButton = new StyledInputs.StyledButton("Create Coffee", new Color(79, 55, 48), Color.WHITE);
        buttonsPanel.add(createButton);

        mainPanel.add(buttonsPanel, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Add listeners
        CoffeeListeners listeners = new CoffeeListeners(
                appController,
                this,
                nameField,
                descriptionField,
                priceField,
                inStockBox);

        backButton.addActionListener(listeners.getBackButtonListener());
        createButton.addActionListener(listeners.getCreateButtonListener());

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

    private void addFormField(JPanel panel, GridBagConstraints gbc, String labelText, JComponent field, int gridy,
            int gridx) {
        gbc.gridy = gridy;
        gbc.gridx = gridx;

        JPanel fieldPanel = new JPanel(new BorderLayout(0, 5));
        fieldPanel.setBackground(Color.WHITE);
        fieldPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 15));

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
