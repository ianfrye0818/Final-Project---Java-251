package views;

import components.StyledInputs;
import components.TitlePanel;
import components.tables.CustomerTable;
import controllers.AppController;
import enums.ViewType;
import models.Customer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ViewAllCustomersView extends SuperView {
    private final String title;

    public ViewAllCustomersView(AppController controller) {
        this.title = "All Customers";

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1000, 650)); // Slightly wider for all columns
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 245));

        // Title Panel
        JPanel titlePanel = new TitlePanel("Customer Management");
        add(titlePanel, BorderLayout.NORTH);

        // Customer Table
        CustomerTable customerTable = new CustomerTable(controller);
        customerTable.loadData(); // Load the customer data

        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(customerTable);
        scrollPane.setBorder(new EmptyBorder(0, 20, 0, 20));
        add(scrollPane, BorderLayout.CENTER);

        // Bottom Panel with Back Button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        bottomPanel.setBackground(new Color(245, 245, 245));

        // Back to Menu Button
        JButton backButton = new StyledInputs.StyledButton("Back to Menu");
        backButton.addActionListener(e -> controller.setDisplay(ViewType.COFFEE_MENU_VIEW));

        // View Details Button
        JButton viewDetailsButton = new StyledInputs.StyledButton("View Details", new Color(109, 81, 70), Color.WHITE);
        viewDetailsButton.addActionListener(e -> {
            Customer selectedCustomer = customerTable.getSelectedItem();
            if (selectedCustomer != null) {
                customerTable.handleViewDetails();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Please select a customer to view details",
                        "No Selection",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        bottomPanel.add(backButton);
        bottomPanel.add(viewDetailsButton);

        // Info Panel
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        infoPanel.setBackground(new Color(245, 245, 245));
        JLabel totalCustomersLabel = new JLabel(
                "Total Customers: " + controller.getCustomerService().getAllCustomers().size());
        totalCustomersLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        totalCustomersLabel.setBorder(new EmptyBorder(0, 0, 10, 20));
        infoPanel.add(totalCustomersLabel);

        // Combine bottom panels
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setBackground(new Color(245, 245, 245));
        southPanel.add(bottomPanel, BorderLayout.CENTER);
        southPanel.add(infoPanel, BorderLayout.SOUTH);

        add(southPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    @Override
    public String getTitle() {
        return super.getTitle() + " - " + title;
    }
}
