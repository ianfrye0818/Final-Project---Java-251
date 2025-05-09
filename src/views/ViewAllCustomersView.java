package views;

import components.StyledInputs;
import components.TableJPanel;
import components.TitlePanel;
import components.tables.CustomerTable;
import enums.ViewType;
import stores.SelectedCustomerStore;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * The view displaying a table of all customers in the system. It uses a
 * {@link CustomerTable} component to present the customer data, which is loaded
 * from the controller's customer service. Users can select a customer from the
 * table and click a "View Details" button to navigate to the
 * {@link CustomerDetailView} for more information. The view also displays the
 * total number of customers. It utilizes a {@link BorderLayout} for overall
 * layout and custom table and title components.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */
public class ViewAllCustomersView extends SuperView {
    private final CustomerTable customerTable;

    /**
     * Constructs the {@code ViewAllCustomersView}, initializing its UI components,
     * layout, and loading the customer data into the table. It also sets up the
     * "View Details" button to handle navigation to the customer details view.
     */
    public ViewAllCustomersView() {
        super("All Customers");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1000, 650)); // Slightly wider for all columns
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 245));

        // Title Panel
        JPanel titlePanel = new TitlePanel("Customer Management");
        add(titlePanel, BorderLayout.NORTH);

        // Customer Table
        customerTable = new CustomerTable(controller, this);
        customerTable.loadData(); // Load the customer data

        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(customerTable);
        scrollPane.setBorder(new EmptyBorder(0, 20, 0, 20));
        add(scrollPane, BorderLayout.CENTER);

        JButton viewDetailsButton = getJButton(customerTable);

        JPanel bottomPanel = new TableJPanel(controller, viewDetailsButton);

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

    /**
     * Creates and returns the "View Details" button, attaching an action listener
     * that handles the navigation to the {@link CustomerDetailView} for the
     * selected customer.
     *
     * @param customerTable The {@code CustomerTable} from which the selected
     *                      customer is retrieved.
     * @return The created "View Details" {@code JButton}.
     */
    private JButton getJButton(CustomerTable customerTable) {
        JButton viewDetailsButton = new StyledInputs.PrimaryButton("View Details");
        viewDetailsButton.addActionListener(e -> handleViewDetails());
        return viewDetailsButton;
    }

    /**
     * Handles the action of the "View Details" button. It retrieves the selected
     * customer from the table, stores it in the {@link SelectedCustomerStore},
     * and navigates the application to the {@link CustomerDetailView}.
     */
    public void handleViewDetails() {
        SelectedCustomerStore.getInstance().set(customerTable.getSelectedItem());
        controller.setDisplay(ViewType.CUSTOMER_DETAIL_VIEW);
    }
}