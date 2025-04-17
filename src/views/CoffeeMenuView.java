package views;

import components.StyledInputs;
import components.TitlePanel;
import components.tables.CoffeeTable;
import controllers.AppController;
import enums.ViewType;
import listeners.AccountMenuListeners;
import listeners.AdminMenuListeners;
import listeners.CoffeeMenuListeners;
import models.Coffee;
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
        setMinimumSize(new Dimension(850, 650));
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 245));

        JPanel titlePanel = new TitlePanel("Our Coffee Selection");
        add(titlePanel, BorderLayout.NORTH);

        CoffeeTable coffeeTable = new CoffeeTable(controller);

        JScrollPane scrollPane = new JScrollPane(coffeeTable);
        scrollPane.setBorder(new EmptyBorder(0, 20, 0, 20));
        this.add(scrollPane, BorderLayout.CENTER);

        // Top Left Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu coffeeMenu = new JMenu("Coffee Actions");
        JMenu accountMenu = new JMenu("Account");
        JMenu adminMenu = new JMenu("Admin"); // New Admin Menu

        // Coffee Actions Menu Items
        JMenuItem addNewCoffeeItem = new JMenuItem("Add New Coffee");
        coffeeMenu.add(addNewCoffeeItem);

        // Account Actions Menu Items
        JMenuItem currentUserItem = new JMenuItem("Logged in as: " + currentCustomer.getEmail());
        currentUserItem.setEnabled(false); // Make it non-clickable
        JMenuItem creditsItem = new JMenuItem("Credits: $" + String.format("%.2f", currentCustomer.getCreditLimit()));
        creditsItem.setEnabled(false); // Make it non-clickable
        JMenuItem updateAccountItem = new JMenuItem("Update Account");
        JMenuItem viewAccountItem = new JMenuItem("View Account");
        JMenuItem deleteAccountItem = new JMenuItem("Delete Account");
        JMenuItem addCreditsItem = new JMenuItem("Add Credits");
        JMenuItem logoutItem = new JMenuItem("Logout");

        accountMenu.add(currentUserItem);
        accountMenu.add(creditsItem);
        accountMenu.addSeparator();
        accountMenu.add(updateAccountItem);
        accountMenu.add(viewAccountItem);
        accountMenu.add(deleteAccountItem);
        accountMenu.add(addCreditsItem);
        accountMenu.addSeparator();
        accountMenu.add(logoutItem);

        // Admin Menu Items
        JMenuItem viewAllCustomersItem = new JMenuItem("View All Customers");
        JMenuItem viewAllOrdersItem = new JMenuItem("View All Orders");
        adminMenu.add(viewAllCustomersItem);
        adminMenu.add(viewAllOrdersItem);

        menuBar.add(coffeeMenu);
        menuBar.add(accountMenu);
        menuBar.add(adminMenu); // Add the Admin menu
        setJMenuBar(menuBar);

        // Bottom Panel with Create Order Button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        bottomPanel.setBackground(new Color(245, 245, 245));
        JButton createOrderButton = new StyledInputs.StyledButton("Create Order", new Color(109, 81, 70), Color.WHITE);

        // View Details button
        JButton viewDetailsButton = new StyledInputs.StyledButton("Update Coffee");
        viewDetailsButton.addActionListener(e -> {
            Coffee selectedCoffee = coffeeTable.getSelectedItem();
            if (selectedCoffee != null) {
                coffeeTable.handleViewDetails();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Please select a coffee to update",
                        "No Selection",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        bottomPanel.add(createOrderButton);
        bottomPanel.add(viewDetailsButton);

        this.add(bottomPanel, BorderLayout.SOUTH);

        // Listeners

        CoffeeMenuListeners coffeeMenuListeners = new CoffeeMenuListeners(controller);
        AccountMenuListeners accountMenuListeners = new AccountMenuListeners(controller, this);
        AdminMenuListeners adminMenuListeners = new AdminMenuListeners(controller);
        logoutItem.addActionListener(accountMenuListeners.getLogoutActionListener());
        createOrderButton.addActionListener(e -> controller.setDisplay(ViewType.CREATE_ORDER_VIEW));
        addNewCoffeeItem.addActionListener(coffeeMenuListeners.getAddNewCoffeeButtonListener());
        updateAccountItem.addActionListener(accountMenuListeners.getUpdateAccountActionListener());
        viewAccountItem.addActionListener(accountMenuListeners.getViewAccountActionListener());
        deleteAccountItem.addActionListener(
                accountMenuListeners.getDeleteAccountActionListener(currentCustomer.getCustomerId()));
        addCreditsItem.addActionListener(accountMenuListeners.getAddCreditsActionListener());
        viewAllCustomersItem.addActionListener(adminMenuListeners.getViewAllCustomersListener());
        viewAllOrdersItem.addActionListener(adminMenuListeners.getViewAllOrdersListener());

        pack();
        setLocationRelativeTo(null);
    }

    @Override
    public String getTitle() {
        return super.getTitle() + " - " + title;
    }
}
//
