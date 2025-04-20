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

/**
 * The view displaying a table of all orders in the system. It uses an
 * {@link OrderTable} component to present the order data, which is loaded
 * from the controller's order service. Administrators can select an order
 * from the table and click a "View Details" button (currently non-functional
 * in this view) for more information. The view also displays the total number
 * of orders and the total revenue generated from all orders. It utilizes a
 * {@link BorderLayout} for overall layout and custom table and title
 * components.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */
public class ViewAllOrdersView extends SuperView {

    /**
     * Constructs the {@code ViewAllOrdersView}, initializing its UI components,
     * layout, and loading the order data into the table. It also sets up the
     * (currently non-functional) "View Details" button and displays summary
     * information about the orders.
     */
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

    /**
     * Creates and returns the "View Details" button, attaching an action listener
     * that (currently) displays an error message prompting the user to select
     * an order. In a fully implemented version, this button would likely navigate
     * to an order detail view.
     *
     * @param orderTable The {@code OrderTable} from which the selected order
     *                   is (intended to be) retrieved.
     * @return The created "View Details" {@code JButton}.
     */
    private JButton getJButton(OrderTable orderTable) {
        JButton viewDetailsButton = new StyledInputs.PrimaryButton("View Details");
        viewDetailsButton.addActionListener(e -> {
            Order selectedOrder = orderTable.getSelectedItem();
            if (selectedOrder != null) {
                // In a real implementation, navigate to order details view here
                orderTable.handleViewDetails();
            } else {
                DialogUtils.showError(this, "Please select an order to view details");
            }
        });
        return viewDetailsButton;
    }
}