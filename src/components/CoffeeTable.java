package components;

import controllers.AppController;
import enums.ViewType;
import models.Coffee;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class CoffeeTable extends JTable {
    private DefaultTableModel model;
    private List<Coffee> coffeeList;
    private AppController controller;


    public CoffeeTable(AppController controller) {
        this.controller = controller;
        this.model = new DefaultTableModel(
                new Object[]{"Name", "Description", "Price", "In Stock"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        this.setModel(model);

        this.setRowHeight(35);
        this.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 18));
        getTableHeader().setReorderingAllowed(false);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        getColumnModel().getColumn(3).setCellRenderer(centerRenderer); // In Stock


        loadCoffeeData(model);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && getSelectedRow() != -1) {
                    Coffee selectedCoffee = coffeeList.get(getSelectedRow());
                    controller.getCoffeeStore().set(selectedCoffee);
                    controller.setDisplay(ViewType.UPDATE_COFFEE_VIEW);
                }
            }
        });


    }

    private void loadCoffeeData(DefaultTableModel model) {
        this.coffeeList = this.controller.getCoffeeService().getAllCoffees();

        for (Coffee coffee : coffeeList) {
            model.addRow(new Object[]{
                    coffee.getCoffeeName(),
                    coffee.getCoffeeDescription(),
                    String.format("%.2f", coffee.getPrice()), // Format price
                    coffee.getIsInStock() ? "Yes" : "No"
            });
        }
    }
}
