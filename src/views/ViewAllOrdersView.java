package views;

import components.StyledInputs;
import components.TitlePanel;
import components.tables.OrderTable;
import controllers.AppController;
import enums.ViewType;
import dto.OrderTableDto;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ViewAllOrdersView extends SuperView {
    private final String title;

    public ViewAllOrdersView(AppController controller) {
        this.title = "All Orders";

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1000, 650));
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 245));

        // Title Panel
        JPanel titlePanel = new TitlePanel("Order Management");
        add(titlePanel, BorderLayout.NORTH);

        // Order Table
        OrderTable orderTable = new OrderTable(controller);
        orderTable.loadData(); // Load the order data

        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(orderTable);
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
            OrderTableDto selectedOrder = orderTable.getSelectedItem();
            if (selectedOrder != null) {
                orderTable.handleViewDetails();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Please select an order to view details",
                        "No Selection",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        bottomPanel.add(backButton);
        bottomPanel.add(viewDetailsButton);

        // Info Panel
        JPanel infoPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        infoPanel.setBackground(new Color(245, 245, 245));
        infoPanel.setBorder(new EmptyBorder(0, 0, 10, 20));

        // Total Orders
        JLabel totalOrdersLabel = new JLabel(
                "Total Orders: " + controller.getOrderService().getAllOrders().size(),
                SwingConstants.RIGHT);
        totalOrdersLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Total Revenue
        double totalRevenue = controller.getOrderService().getAllOrders().stream()
                .mapToDouble(order -> order.getTotal())
                .sum();
        JLabel revenueLabel = new JLabel(
                String.format("Total Revenue: $%.2f", totalRevenue),
                SwingConstants.RIGHT);
        revenueLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        infoPanel.add(totalOrdersLabel);
        infoPanel.add(revenueLabel);

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
