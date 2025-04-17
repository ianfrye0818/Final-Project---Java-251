package components.tables;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;

import components.StyledTable;
import controllers.AppController;
import dto.OrderTableDto;
import enums.ViewType;
import models.Order;

public class OrderTable extends StyledTable<OrderTableDto> {
  private final AppController controller;

  public OrderTable(AppController controller) {
    super(null, List.of("Order ID", "Customer Name", "Coffee Name", "Quantity", "Total Price"), false);
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
    this.data = this.controller.getOrderService().getAllOrders().stream()
        .map(OrderTableDto::fromOrder)
        .collect(Collectors.toList());

    clearRows();
    for (OrderTableDto dto : this.data) {
      addRow(new Object[] {
          dto.getOrderId(),
          dto.getCustomerName(),
          dto.getCoffeeName(),
          dto.getQuantity(),
          dto.getTotalPrice()
      });
    }

  }

  public void handleViewDetails() {
    OrderTableDto selectedOrder = getSelectedItem();
    if (selectedOrder != null) {
      Order order = controller.getOrderService().getOrderById(selectedOrder.getOrderId());
      controller.getOrderStore().set(order);
      controller.setDisplay(ViewType.ORDER_DETAIL_VIEW);
    }
  }
}
