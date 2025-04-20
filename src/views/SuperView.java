package views;

import controllers.AppController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SuperView extends JFrame {
    protected AppController controller;

    public SuperView(String title) {
        this.controller = AppController.getInstance();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setTitle("Java Cafe - " + title);
    }

    protected void addSectionHeader(JPanel panel, GridBagConstraints gbc, String text, int gridy, int gridx) {
        JLabel header = new JLabel(text);
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.setForeground(new Color(79, 55, 48));
        gbc.gridy = gridy;
        gbc.gridx = gridx;
        gbc.insets = new Insets(20, 8, 8, 8);
        panel.add(header, gbc);
    }

    protected void checkIsBold(boolean isBold, JLabel label, JLabel valueLabel) {
        if (isBold) {
            label.setFont(new Font("Segoe UI", Font.BOLD, 16));
            valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        } else {
            label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            valueLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }
    }

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
