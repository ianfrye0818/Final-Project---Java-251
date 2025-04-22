package views;

import controllers.AppController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * An abstract base class for all views in the application. It extends
 * {@link JFrame}
 * and provides common functionalities such as setting the default close
 * operation,
 * using a {@link BorderLayout}, setting the title with the application name,
 * and
 * providing helper methods for adding section headers, styling labels based on
 * boldness, adding labeled form fields, and managing focus traversal order.
 * Subclasses of {@code SuperView} represent specific screens or dialogs in the
 * user interface.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */
public class SuperView extends JFrame {
    protected AppController controller;

    /**
     * Constructs a {@code SuperView} with the specified title. It initializes the
     * application controller, sets the default close operation to exit, uses a
     * {@code BorderLayout}, and sets the title of the frame prepended with the
     * application name.
     *
     * @param title The title of the specific view.
     */
    public SuperView(String title) {
        this.controller = AppController.getInstance();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setTitle("Java Cafe - " + title);

        ImageIcon appIcon = new ImageIcon("src/assets/images/icon.png");
        setIconImage(appIcon.getImage());
    }

    /**
     * Adds a styled section header to the specified panel.
     *
     * @param panel The {@code JPanel} to which the header is added.
     * @param gbc   The {@code GridBagConstraints} to control the layout of the
     *              header.
     * @param text  The text to be displayed in the header.
     * @param gridy The grid y-coordinate where the header will be placed.
     * @param gridx The grid x-coordinate where the header will be placed.
     */
    protected void addSectionHeader(JPanel panel, GridBagConstraints gbc, String text, int gridy, int gridx) {
        JLabel header = new JLabel(text);
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.setForeground(new Color(79, 55, 48));
        gbc.gridy = gridy;
        gbc.gridx = gridx;
        gbc.insets = new Insets(20, 8, 8, 8);
        panel.add(header, gbc);
    }

    /**
     * Styles the provided labels (label and valueLabel) to be either bold or plain
     * based on the {@code isBold} flag.
     *
     * @param isBold     A boolean indicating whether the labels should be bold.
     * @param label      The {@code JLabel} for the field's label.
     * @param valueLabel The {@code JLabel} for the field's value.
     */
    protected void checkIsBold(boolean isBold, JLabel label, JLabel valueLabel) {
        if (isBold) {
            label.setFont(new Font("Segoe UI", Font.BOLD, 16));
            valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        } else {
            label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            valueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }
    }

    /**
     * Adds a labeled text field to the specified panel.
     *
     * @param panel The {@code JPanel} to which the form field is added.
     * @param gbc   The {@code GridBagConstraints} to control the layout of the
     *              field.
     * @param text  The text for the label of the text field.
     * @param field The {@code JTextField} to be added.
     * @param gridy The grid y-coordinate where the field will be placed.
     * @param gridx The grid x-coordinate where the field will be placed.
     */
    protected void addFormField(JPanel panel, GridBagConstraints gbc, String text, JTextField field, int gridy,
            int gridx) {
        gbc.gridy = gridy;
        gbc.gridx = gridx;

        JPanel fieldPanel = new JPanel(new BorderLayout(0, 5));
        fieldPanel.setBackground(Color.WHITE);

        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        fieldPanel.add(label, BorderLayout.NORTH);

        fieldPanel.add(field, BorderLayout.CENTER);

        panel.add(fieldPanel, gbc);
    }

    /**
     * Creates and returns a {@code FocusTraversalPolicy} based on the provided
     * list of components, defining the order in which components receive focus
     * when the user navigates using the Tab key.
     *
     * @param tabOrder A {@code List} of {@code Component} objects in the desired
     *                 focus traversal order.
     * @return A {@code FocusTraversalPolicy} for the given component order.
     */
    protected FocusTraversalPolicy getFocusTraversalPolicy(List<Component> tabOrder) {
        return new FocusTraversalPolicy() {
            @Override
            public Component getComponentAfter(Container container, Component component) {
                int idx = tabOrder.indexOf(component);
                return tabOrder.get((idx + 1) % tabOrder.size());
            }

            @Override
            public Component getComponentBefore(Container container, Component component) {
                int idx = tabOrder.indexOf(component);
                return tabOrder.get((idx - 1 + tabOrder.size()) % tabOrder.size());
            }

            @Override
            public Component getFirstComponent(Container container) {
                return tabOrder.getFirst();
            }

            @Override
            public Component getLastComponent(Container container) {
                return tabOrder.getLast();
            }

            @Override
            public Component getDefaultComponent(Container container) {
                return tabOrder.getFirst();
            }
        };
    }
}