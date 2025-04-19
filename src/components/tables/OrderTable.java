package components.tables;

import components.StyledTable;
import controllers.AppController;
import entites.Order;
import enums.ViewType;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class OrderTable extends StyledTable<Order> {
    private final AppController controller;

    public OrderTable(AppController controller) {
        super(List.of("Order ID", "Customer Name", "Coffee Name", "Quantity", "Total Price"), false);
        this.controller = controller;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && getSelectedRow() != -1) {
                    handleViewDetails();
                }
            }
        });
    }

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

    public void handleViewDetails() {
        Order selectedOrder = getSelectedItem();

        System.out.println("selectedOrder: " + selectedOrder);

        if (selectedOrder != null) {
            Order order = controller.getOrderService().getOrderById(selectedOrder.getOrderId());
            controller.getOrderStore().set(order);
            controller.setDisplay(ViewType.ORDER_DETAIL_VIEW);
        }
    }
}
