package components.tables;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import components.StyledTable;
import controllers.AppController;
import enums.ViewType;
import models.Customer;

public class CustomerTable extends StyledTable<Customer> {
  private final AppController controller;

  public CustomerTable(AppController controller) {
    super(null, List.of("Name", "Street", "City", "State", "Zip", "Email", "Phone", "Credit Limit"), false);
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

  public void handleViewDetails() {
    Customer selectedCustomer = getSelectedItem();
    controller.getCustomerStore().set(selectedCustomer);
    controller.setDisplay(ViewType.CUSTOMER_DETAIL_VIEW);
  }

}
