package components;

import controllers.AppController;
import enums.ViewType;

import javax.swing.*;
import java.awt.*;

/**
 * A custom JPanel designed to hold table-related controls, such as a "Back to
 * menu"
 * button and a "View Details" button. It uses a {@code FlowLayout} for
 * arranging
 * its components and has a light gray background.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */
public class TableJPanel extends JPanel {
    /**
     * Constructs a new {@code TableJPanel} with the specified application
     * controller
     * and a "View Details" button. It initializes the layout, background color,
     * and adds the "Back to menu" button with an action listener to navigate to the
     * coffee menu view.
     *
     * @param controller        The application controller providing access to
     *                          navigation.
     * @param viewDetailsButton The {@code JButton} used to view details of a
     *                          selected item
     *                          in the associated table.
     */
    public TableJPanel(AppController controller, JButton viewDetailsButton) {
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15));
        setBackground(new Color(245, 245, 245));

        JButton backButton = new StyledInputs.StyledButton("Back to menu");
        backButton.addActionListener(e -> controller.setDisplay(ViewType.COFFEE_MENU_VIEW));

        add(backButton);
        add(viewDetailsButton);
    }
}