package components.tables;

import components.StyledTable;
import controllers.AppController;
import entites.Order;
import enums.ViewType;
import stores.OrderStore;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * A custom table component for displaying order data.
 * Extends {@link StyledTable} and provides specific functionality
 * for loading, displaying, and interacting with {@link Order} entities.
 * It includes the capability to view order details on double-click.
 *
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */
public class OrderTable extends StyledTable<Order> {
    private final AppController controller;

    /**
     * Constructs a new {@code OrderTable} with the specified application
     * controller.
     * Initializes the table with column headers and adds a mouse listener to handle
     * double-click events for viewing order details.
     *
     * @param controller The application controller providing access to order data
     *                   and navigation.
     */
    public OrderTable(AppController controller) {
        super(List.of("Order ID", "Customer Name", "Coffee Name", "Quantity", "Total Price"), false);
        this.controller = controller;

        addMouseListener(new MouseAdapter() {
            /**
             * Handles mouse click events on the table. If a row is double-clicked,
             * it triggers the display of the order details view for the selected order.
             *
             * @param e The {@code MouseEvent} that occurred.
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && getSelectedRow() != -1) {
                    handleViewDetails();
                }
            }
        });
    }

    /**
     * Loads order data from the application controller and populates the table.
     * Retrieves all {@link Order} entities and adds them as rows to the table,
     * displaying the order ID, customer name, coffee name, quantity ordered, and
     * total price.
     */
    @Override
    public void loadData() {
        this.data = this.controller.getOrderService().getAllOrders();

        clearRows();
        for (Order order : this.data) {
            addRow(new Object[] {
                    order.getOrderId(),
                    order.getCustomer().getCustomerName(),
                    order.getCoffee().getCoffeeName(),
                    order.getQtyOrdered(),
                    order.getTotal()
            });
        }

    }

    /**
     * Handles the action of viewing the details of a selected order.
     * Retrieves the selected {@link Order} entity, fetches the complete order
     * details
     * using the order ID, stores it in the {@link OrderStore}, and navigates the
     * application to the order detail view.
     */
    public void handleViewDetails() {
        Order selectedOrder = getSelectedItem();

        System.out.println("selectedOrder: " + selectedOrder);

        if (selectedOrder != null) {
            Order order = controller.getOrderService().getOrderById(selectedOrder.getOrderId());
            OrderStore.getInstance().set(order);
            controller.setDisplay(ViewType.ORDER_DETAIL_VIEW);
        }
    }
}