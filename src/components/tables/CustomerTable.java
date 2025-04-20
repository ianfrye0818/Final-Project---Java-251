package components.tables;

import components.StyledTable;
import controllers.AppController;
import entites.Customer;
import views.SuperView;
import views.ViewAllCustomersView;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * 
 * A custom table component for displaying customer data.
 * Extends {@link StyledTable} and provides specific functionality
 * for loading and displaying {@link Customer} entities. It also
 * handles double-click events to trigger customer detail viewing.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */
public class CustomerTable extends StyledTable<Customer> {
    private final AppController controller;

    /**
     * Constructs a new {@code CustomerTable} with the specified application
     * controller and associated view.
     * Initializes the table with column headers and adds a mouse listener to handle
     * double-click events
     * for viewing customer details in the provided {@link ViewAllCustomersView}.
     *
     * @param controller The application controller providing access to customer
     *                   data.
     * @param view       The {@link SuperView}, specifically expected to be a
     *                   {@link ViewAllCustomersView},
     *                   which handles the display of customer details.
     */
    public CustomerTable(AppController controller, SuperView view) {
        super(List.of("Name", "Street", "City", "State", "Zip", "Email", "Phone", "Credit Limit"), false);
        this.controller = controller;
        addMouseListener(new MouseAdapter() {
            /**
             * Handles mouse click events on the table. If a row is double-clicked,
             * it casts the provided {@link SuperView} to {@link ViewAllCustomersView}
             * and calls its {@code handleViewDetails()} method to display the selected
             * customer's details.
             *
             * @param e The {@code MouseEvent} that occurred.
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && getSelectedRow() != -1) {
                    ((ViewAllCustomersView) view).handleViewDetails();
                }
            }
        });
    }

    /**
     * Loads customer data from the application controller and populates the table.
     * Retrieves all {@link Customer} entities and adds them as rows to the table,
     * formatting the customer's full name and credit limit for display.
     */
    @Override
    public void loadData() {
        this.data = this.controller.getCustomerService().getAllCustomers();
        clearRows();
        for (Customer customer : this.data) {
            addRow(new Object[] {
                    customer.getFirstName() + " " + customer.getLastName(),
                    customer.getStreet(),
                    customer.getCity(),
                    customer.getState(),
                    customer.getZip(),
                    customer.getEmail(),
                    customer.getPhone(),
                    String.format("$%.2f", customer.getCreditLimit())
            });
        }
    }

}