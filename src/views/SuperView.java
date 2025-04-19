package views;

import controllers.AppController;
import entites.Coffee;
import entites.Customer;
import entites.Order;
import enums.ViewType;
import utils.DialogUtils;
import java.util.List;
import javax.swing.*;
import java.awt.*;

public class SuperView extends JFrame {
    protected AppController controller;

    public SuperView(AppController controller, String title) {
        this.controller = controller;
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

    protected boolean isCustomerPresent(ViewType returnView) {
        Customer customer = controller.getLoggedinCustomerStore().get();
        if (customer == null) {
            DialogUtils.showError(this, "No customer found");
            controller.setDisplay(returnView);
            return false;
        }
        return true;
    }

    protected boolean isOrderPresent(ViewType returnView) {
        Order order = controller.getOrderStore().get();
        if (order == null) {
            DialogUtils.showError(this, "No order found");
            controller.setDisplay(returnView);
            return false;
        }
        return true;
    }

    protected boolean isCoffeePresent(ViewType returnView) {
        Coffee coffee = controller.getCoffeeStore().get();
        if (coffee == null) {
            DialogUtils.showError(this, "No coffee found");
            controller.setDisplay(returnView);
            return false;
        }
        return true;
    }

    protected FocusTraversalPolicy getFocusTraversalPolicy(List<Component> tabOrder) {

        FocusTraversalPolicy policy = new FocusTraversalPolicy() {

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
                return tabOrder.get(0);
            }

            @Override
            public Component getLastComponent(Container container) {
                return tabOrder.get(tabOrder.size() - 1);
            }

            @Override
            public Component getDefaultComponent(Container container) {
                return tabOrder.get(0);
            }
        };
        return policy;
    }

}
