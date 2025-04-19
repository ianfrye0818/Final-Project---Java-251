package components;

import controllers.AppController;
import entites.Coffee;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;

public class CoffeeSelectComboBox extends StyledInputs.StyledComboBox<Coffee> {
    public CoffeeSelectComboBox(AppController appController) {
        super(appController.getCoffeeService().getAllCoffees()
                .stream()
                .filter(Coffee::getIsInStock)
                .toArray(Coffee[]::new));

        setRenderer(new DefaultListCellRenderer() {
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
