package components.tables;

import components.StyledTable;
import controllers.AppController;
import entites.Coffee;
import enums.ViewType;
import stores.CoffeeStore;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * 
 * A custom table component for displaying coffee data.
 * Extends {@link StyledTable} and provides specific functionality
 * for loading, displaying, and interacting with {@link Coffee} entities.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */
public class CoffeeTable extends StyledTable<Coffee> {
    private final AppController controller;

    /**
     * Constructs a new {@code CoffeeTable} with the specified application
     * controller.
     * Initializes the table with column headers, sets up column alignment,
     * loads coffee data, and adds a mouse listener for viewing details.
     *
     * @param controller The application controller providing access to services and
     *                   navigation.
     */
    public CoffeeTable(AppController controller) {
        super(
                List.of("Name", "Description", "Price", "In Stock"));

        this.controller = controller;

        // Center align price and in stock columns
        setCenterAlignedColumns(2, 3);

        // Load the data
        loadData();

        addMouseListener(new MouseAdapter() {
            /**
             * Handles mouse click events on the table. If a row is double-clicked,
             * it triggers the display of the details view for the selected coffee.
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
     * Loads coffee data from the application controller and populates the table.
     * Retrieves all {@link Coffee} entities and adds them as rows to the table,
     * formatting the price and in-stock status for display.
     */
    @Override
    public void loadData() {
        this.data = this.controller.getCoffeeService().getAllCoffees();
        clearRows();

        for (Coffee coffee : this.data) {
            addRow(new Object[] {
                    coffee.getName(),
                    coffee.getDescription(),
                    String.format("$%.2f", coffee.getPrice()),
                    coffee.getIsInStock() ? "Yes" : "No"
            });
        }
    }

    /**
     * Handles the action of viewing the details of a selected coffee.
     * Retrieves the selected {@link Coffee} entity, stores it in the
     * {@link CoffeeStore}, and navigates the application to the update coffee view.
     */
    public void handleViewDetails() {
        Coffee selectedCoffee = getSelectedItem();
        CoffeeStore.getInstance().set(selectedCoffee);
        controller.setDisplay(ViewType.UPDATE_COFFEE_VIEW);
    }
}