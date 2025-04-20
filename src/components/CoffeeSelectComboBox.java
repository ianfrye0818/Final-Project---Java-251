package components;

import controllers.AppController;
import entites.Coffee;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;

/**
 * A custom combo box component for selecting {@link Coffee} entities that are
 * in stock.
 * Extends {@link StyledInputs.StyledComboBox} and populates its items with
 * available coffees.
 * It also customizes the rendering of the list items and the appearance of the
 * dropdown arrow.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */
public class CoffeeSelectComboBox extends StyledInputs.StyledComboBox<Coffee> {
    /**
     * Constructs a new {@code CoffeeSelectComboBox} using the provided application
     * controller.
     * It retrieves all in-stock coffees from the coffee service and populates the
     * combo box.
     * Additionally, it sets a custom renderer for displaying coffee names in the
     * dropdown
     * and customizes the UI for the dropdown arrow.
     *
     * @param appController The application controller providing access to the
     *                      coffee service.
     */
    public CoffeeSelectComboBox(AppController appController) {
        super(appController.getCoffeeService().getAllCoffees()
                .stream()
                .filter(Coffee::getIsInStock)
                .toArray(Coffee[]::new));

        setRenderer(new DefaultListCellRenderer() {
            /**
             * Overrides the default list cell renderer to display the name of the
             * {@link Coffee} object.
             * It also adds padding and a hand cursor for better user interaction in the
             * dropdown list.
             *
             * @param list         The {@code JList} we're painting.
             * @param value        The value returned by
             *                     {@code list.getModel().getElementAt(index)}.
             * @param index        The cell index.
             * @param isSelected   {@code true} if the specified cell is currently selected;
             *                     {@code false} otherwise.
             * @param cellHasFocus {@code true} if the cell has the focus; {@code false}
             *                     otherwise.
             * @return A {@code JLabel} configured to display the coffee name with custom
             *         styling.
             */
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                if (value instanceof Coffee coffee) {
                    value = coffee.getName();
                }
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,
                        cellHasFocus);
                label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Add padding to dropdown items
                label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                return label;
            }
        });

        setUI(new BasicComboBoxUI() {
            /**
             * Overrides the method to create a custom arrow button for the combo box.
             * The custom button has a specific font, color, background, border, and focus
             * appearance.
             *
             * @return A {@code JButton} representing the custom dropdown arrow.
             */
            @Override
            protected JButton createArrowButton() {
                JButton button = new JButton("▼");
                button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                button.setForeground(Color.DARK_GRAY);
                button.setBackground(Color.WHITE);
                button.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
                button.setFocusPainted(false);
                button.setContentAreaFilled(false);
                return button;
            }
        });

    }

}