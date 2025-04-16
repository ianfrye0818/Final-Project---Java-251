package views;

import components.CoffeeTable;
import components.StyledInputs;
import components.TitlePanel;
import controllers.AppController;
import enums.ViewType;
import listeners.CoffeeMenuListeners;
import models.Customer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CoffeeMenuView extends SuperView {
    private final String title;
    private final Customer currentCustomer;

    public CoffeeMenuView(AppController controller) {
        this.title = "Coffee Menu";
        this.currentCustomer = controller.getCustomerStore().get();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(850, 650)); // Slightly larger
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 245));

        JPanel titlePanel = new TitlePanel("Our Coffee Selection");
        add(titlePanel, BorderLayout.NORTH);

        JTable coffeeTable = new CoffeeTable(controller);

        JScrollPane scrollPane = new JScrollPane(coffeeTable);
        scrollPane.setBorder(new EmptyBorder(0, 20, 0, 20)); // Side padding for table
        this.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setBackground(new Color(245, 245, 245));

        JButton logoutButton = new StyledInputs.StyledButton("Logout");
        JButton createOrderButton = new StyledInputs.StyledButton("Create Order", new Color(109, 81, 70), Color.WHITE);
        JButton addNewCoffeeButton = new StyledInputs.StyledButton("Add New Coffee");
        JButton updateAccountButton = new StyledInputs.StyledButton("Update Account");

        buttonPanel.add(logoutButton);
        buttonPanel.add(createOrderButton);
        buttonPanel.add(addNewCoffeeButton);
        buttonPanel.add(updateAccountButton);

        JPanel infoPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        infoPanel.setBackground(new Color(245, 245, 245));
        infoPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel userLabel = new JLabel("Logged in as: " + currentCustomer.getEmail());
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        userLabel.setForeground(new Color(79, 55, 48));
        infoPanel.add(userLabel);

        JLabel creditLabel = getCreditLabel();
        infoPanel.add(creditLabel);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setBackground(new Color(245, 245, 245));
        southPanel.add(buttonPanel, BorderLayout.CENTER);
        southPanel.add(infoPanel, BorderLayout.SOUTH);

        this.add(southPanel, BorderLayout.SOUTH);

        CoffeeMenuListeners listeners = new CoffeeMenuListeners(controller);

        logoutButton.addActionListener(listeners.getLogoutButtonListener());
        createOrderButton.addActionListener(listeners.getCreateOrderButtonListener());
        addNewCoffeeButton.addActionListener(listeners.getAddNewCoffeeButtonListener());
        updateAccountButton.addActionListener(e -> controller.setDisplay(ViewType.UPDATE_ACCOUNT_VIEW));
    }

    private JLabel getCreditLabel() {
        JLabel creditLabel = new JLabel(
                "Available Credit: $" + String.format("%.2f", currentCustomer.getCreditLimit()));
        creditLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        creditLabel.setForeground(new Color(79, 55, 48));
        creditLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        return creditLabel;
    }

    @Override
    public String getTitle() {
        return super.getTitle() + " - " + title;
    }
}