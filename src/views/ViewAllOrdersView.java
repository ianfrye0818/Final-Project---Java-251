package views;

import components.StyledInputs;
import components.TableJPanel;
import components.TitlePanel;
import components.tables.OrderTable;
import entites.Order;
import utils.DialogUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ViewAllOrdersView extends SuperView {
    public ViewAllOrdersView() {
        super("All Orders");

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
        // JPanel bottomPanel = getJPanel(controller, orderTable);
        JButton viewDetailsButton = getJButton(orderTable);
        JPanel bottomPanel = new TableJPanel(controller, viewDetailsButton);

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
                .mapToDouble(Order::getTotal)
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

    private JButton getJButton(OrderTable orderTable) {
        JButton viewDetailsButton = new StyledInputs.PrimaryButton("View Details");
        viewDetailsButton.addActionListener(e -> {
            Order selectedOrder = orderTable.getSelectedItem();
            if (selectedOrder != null) {
                orderTable.handleViewDetails();
            } else {
                DialogUtils.showError(this, "Please select an order to view details");
            }
        });
        return viewDetailsButton;
    }

}
