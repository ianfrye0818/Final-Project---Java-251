package components.tables;

import components.StyledTable;
import controllers.AppController;
import entites.Customer;
import views.SuperView;
import views.ViewAllCustomersView;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class CustomerTable extends StyledTable<Customer> {
    private final AppController controller;

    public CustomerTable(AppController controller, SuperView view) {
        super(List.of("Name", "Street", "City", "State", "Zip", "Email", "Phone", "Credit Limit"), false);
        this.controller = controller;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && getSelectedRow() != -1) {
                    // TODO: Make sure this works
                    ((ViewAllCustomersView) view).handleViewDetails();
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

}
