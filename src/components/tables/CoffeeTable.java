package components.tables;

import components.StyledTable;
import controllers.AppController;
import entites.Coffee;
import enums.ViewType;
import stores.CoffeeStore;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class CoffeeTable extends StyledTable<Coffee> {
    private final AppController controller;

    public CoffeeTable(AppController controller) {
        super(
                List.of("Name", "Description", "Price", "In Stock"));

        this.controller = controller;

        // Center align price and in stock columns
        setCenterAlignedColumns(2, 3);

        // Load the data
        loadData();

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
        this.data = this.controller.getCoffeeService().getAllCoffees();
        clearRows();

        for (Coffee coffee : this.data) {
            addRow(new Object[] {
                    coffee.getName(),
                    coffee.getDescription(),
                    String.format("%.2f", coffee.getPrice()),
                    coffee.getIsInStock() ? "Yes" : "No"
            });
        }
    }

    public void handleViewDetails() {
        Coffee selectedCoffee = getSelectedItem();
        CoffeeStore.getInstance().set(selectedCoffee);
        controller.setDisplay(ViewType.UPDATE_COFFEE_VIEW);
    }
}
