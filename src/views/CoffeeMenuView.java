package views;

import components.StyledInputs;
import components.Typography;
import components.tables.CoffeeTable;
import listeners.CoffeeMenuActionListeners;
import listeners.CoffeeMenuActionMenus.AccountMenuListeners;
import listeners.CoffeeMenuActionMenus.AdminMenuListeners;
import listeners.CoffeeMenuActionMenus.CoffeeMenuListeners;
import services.ImageIconService;
import stores.AuthStore;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * The main view displaying the coffee menu and providing actions related to
 * coffee orders, user accounts, and administrative functions. It includes a
 * table of available coffees, a menu bar with options for coffee actions,
 * account management, and admin controls, as well as a bottom panel for
 * initiating order creation and viewing coffee details. This view updates
 * dynamically based on the authentication state managed by the
 * {@link AuthStore}.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 * 
 */

public class CoffeeMenuView extends SuperView {
    private final JMenuItem currentUserItem;
    private final JMenuItem creditsItem;
    private final AuthStore authStore;
    private CoffeeTable coffeeTable;

    /**
     * Constructs the {@code CoffeeMenuView}, initializing its UI components,
     * action listeners, and subscribing to changes in the authentication store
     * to update user-specific information.
     */
    public CoffeeMenuView() {
        super("Coffee Menu");
        authStore = AuthStore.getInstance();
        authStore.subscribe(this::updateCustomerInfo);
        CoffeeMenuActionListeners coffeeMenuActionListeners = new CoffeeMenuActionListeners(controller);
        CoffeeMenuListeners coffeeMenuListeners = new CoffeeMenuListeners(controller);
        AccountMenuListeners accountMenuListeners = new AccountMenuListeners(controller, this);
        AdminMenuListeners adminMenuListeners = new AdminMenuListeners(controller, this);

        setMinimumSize(new Dimension(850, 650));
        getContentPane().setBackground(new Color(245, 245, 245));

        // JPanel titlePanel = new TitlePanel("Our Coffee Selection");
        // add(titlePanel, BorderLayout.NORTH);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(new Color(245, 245, 245));

        // Get the logo image icon
        ImageIcon logoIcon = ImageIconService.getImageIcon();
        if (logoIcon != null) {
            // Resize the logo image icon
            ImageIcon resizedIcon = ImageIconService.resizeImageIcon(logoIcon, 50, 50);
            JLabel logoLabel = new JLabel(resizedIcon);
            logoLabel.setBorder(new EmptyBorder(10, 20, 10, 10));

            // Get the title label
            JLabel titleLabel = new Typography.StyledTitleField("Our Coffee Selection");
            titleLabel.setForeground(new Color(79, 55, 48));
            titleLabel.setHorizontalAlignment(JLabel.CENTER);

            // Add the logo label and title label to the title panel
            titlePanel.add(logoLabel, BorderLayout.WEST);
            titlePanel.add(titleLabel, BorderLayout.CENTER);

            // Add the title panel to the top of the frame
            add(titlePanel, BorderLayout.NORTH);

        }

        // Create the coffee table
        coffeeTable = new CoffeeTable(controller);

        // Create the scroll pane for the coffee table
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
        currentUserItem = new JMenuItem(
                "Logged in as: " + (authStore.get() != null ? authStore.get().getEmail() : "Not logged in"));
        currentUserItem.setEnabled(false); // Make it non-clickable
        creditsItem = new JMenuItem(
                "Credits: $"
                        + String.format("%.2f", (authStore.get() != null ? authStore.get().getCreditLimit() : 0.00)));
        creditsItem.setEnabled(false); // Make it non-clickable
        JMenuItem updateAccountItem = new JMenuItem("Update Account");
        JMenuItem viewAccountItem = new JMenuItem("View Account");
        JMenuItem deleteAccountItem = new JMenuItem("Delete Account");
        JMenuItem addCreditsItem = new JMenuItem("Add Credits");
        JMenuItem logoutItem = new JMenuItem("Logout");

        // Account Menu Items
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
        JMenuItem resetDatabaseItem = new JMenuItem("Clear Database");
        JMenuItem populateDatabaseItem = new JMenuItem("Clear/Populate Database");
        adminMenu.add(viewAllCustomersItem);
        adminMenu.add(viewAllOrdersItem);
        adminMenu.add(resetDatabaseItem);
        adminMenu.add(populateDatabaseItem);

        menuBar.add(coffeeMenu);
        menuBar.add(accountMenu);
        menuBar.add(adminMenu); // Add the Admin menu
        setJMenuBar(menuBar);

        // Bottom Panel with Create Order Button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        bottomPanel.setBackground(new Color(245, 245, 245));
        JButton createOrderButton = new StyledInputs.PrimaryButton("Create Order");
        // View Details button
        JButton viewDetailsButton = new StyledInputs.StyledButton("Update Coffee");
        viewDetailsButton.addActionListener(
                coffeeMenuActionListeners.getCoffeeTableSelectedItemListener(coffeeTable,
                        this));

        bottomPanel.add(createOrderButton);
        bottomPanel.add(viewDetailsButton);

        this.add(bottomPanel, BorderLayout.SOUTH);

        // Listeners

        // Top Menu Listeners
        logoutItem.addActionListener(accountMenuListeners.getLogoutActionListener());
        addNewCoffeeItem.addActionListener(coffeeMenuListeners.getAddNewCoffeeButtonListener());
        updateAccountItem.addActionListener(accountMenuListeners.getUpdateAccountActionListener());
        viewAccountItem.addActionListener(accountMenuListeners.getViewAccountActionListener());
        deleteAccountItem.addActionListener(
                accountMenuListeners.getDeleteAccountActionListener(
                        authStore.get() != null ? authStore.get().getCustomerId() : -1));
        addCreditsItem.addActionListener(accountMenuListeners.getAddCreditsActionListener());
        viewAllCustomersItem.addActionListener(adminMenuListeners.getViewAllCustomersListener());
        viewAllOrdersItem.addActionListener(adminMenuListeners.getViewAllOrdersListener());
        resetDatabaseItem.addActionListener(adminMenuListeners.getClearDatabaseListener(this));
        populateDatabaseItem.addActionListener(adminMenuListeners.resetDatabaseListener(this));

        // Bottom Panel Listeners
        createOrderButton.addActionListener(coffeeMenuActionListeners.getCreateOrderButtonActionListener());

        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Updates the displayed customer infoprmation (email and credits) in the menu
     * bar. This method is called whenever the authenticated customer in the
     * {@link AuthStore} changes.
     */
    private void updateCustomerInfo() {
        System.out.println("Updating customer info");
        if (authStore.get() != null) {
            currentUserItem.setText("Logged in as: " + authStore.get().getEmail());
            creditsItem.setText(
                    "Credits: $" + String.format("%.2f", authStore.get().getCreditLimit()));
        } else {
            currentUserItem.setText("Not logged in");
            creditsItem.setText("Credits: $0.00");
        }
    }

    /**
     * Refreshes the {@link CoffeeTable} by reloading the data and repainting the
     * component.
     */
    public void refreshTable() {
        System.out.println("Refreshing table");
        coffeeTable.loadData(); // Explicitly load the data
        this.revalidate(); // Ensure the UI updates
        this.repaint(); // Repaint the component
    }
}