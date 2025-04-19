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

    public boolean isCustomerPresent() {
        ViewType previousView = controller.getViewManager().getPreviousView();
        Customer selectedCustomer = controller.getSelectedCustomerStore().get();
        if (selectedCustomer == null) {
            DialogUtils.showError(this, "No customer found");
            controller.setDisplay(previousView);
            return false;
        }
        return true;
    }

    public boolean isUserPresent() {
        Customer loggedInCustomer = controller.getLoggedinCustomerStore().get();
        if (loggedInCustomer == null) {
            DialogUtils.showError(this, "No user found");
            controller.setDisplay(ViewType.LOGIN_VIEW);
            return false;
        }
        return true;
    }

    public boolean isOrderPresent() {
        ViewType previousView = controller.getViewManager().getPreviousView();
        Order order = controller.getOrderStore().get();
        if (order == null) {
            DialogUtils.showError(this, "No order found");
            controller.setDisplay(previousView);
            return false;
        }
        return true;
    }

    public boolean isCoffeePresent() {
        ViewType previousView = controller.getViewManager().getPreviousView();
        Coffee coffee = controller.getCoffeeStore().get();
        if (coffee == null) {
            DialogUtils.showError(this, "No coffee found");
            controller.setDisplay(previousView);
            return false;
        }
        return true;
    }

    public void setSelectedCustomer(Customer customer) {
        System.out.println("Setting selected customer: " + customer);
        controller.getSelectedCustomerStore().set(customer);
    }

    public Customer getSelectedCustomer() {
        Customer customer = controller.getSelectedCustomerStore().get();
        System.out.println("Selected customer: " + customer);
        if (customer == null) {
            throw new RuntimeException("No customer found");
        }
        return customer;
    }

    public Customer getLoggedInUser() {
        Customer customer = controller.getLoggedinCustomerStore().get();
        System.out.println("Logged in user: " + customer);
        if (customer == null) {
            throw new RuntimeException("No user found");
        }
        return customer;
    }

    public Order getSelectedOrder() {
        Order order = controller.getOrderStore().get();
        System.out.println("Selected order: " + order);
        if (order == null) {
            throw new RuntimeException("No order found");
        }
        return order;
    }

    public Coffee getSelectedCoffee() {
        Coffee coffee = controller.getCoffeeStore().get();
        System.out.println("Selected coffee: " + coffee);
        if (coffee == null) {
            throw new RuntimeException("No coffee found");
        }
        return coffee;
    }

    public void setSelectedCoffee(Coffee coffee) {
        System.out.println("Setting selected coffee: " + coffee);
        controller.getCoffeeStore().set(coffee);
    }

    public void setSelectedOrder(Order order) {
        System.out.println("Setting selected order: " + order);
        controller.getOrderStore().set(order);
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
