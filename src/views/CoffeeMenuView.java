package views;

import components.CoffeeTable;
import components.StyledInputs;
import components.TitlePanel;
import controllers.AppController;
import listeners.CoffeeMenuListeners;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CoffeeMenuView extends SuperView {
    private final String title;

    public CoffeeMenuView(AppController controller) {
        this.title = "Coffee Menu";

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

        buttonPanel.add(logoutButton);
        buttonPanel.add(createOrderButton);
        buttonPanel.add(addNewCoffeeButton);

        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBackground(new Color(245, 245, 245));
        infoPanel.add(getCreditLabel(), BorderLayout.EAST);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setBackground(new Color(245, 245, 245));
        southPanel.add(buttonPanel, BorderLayout.CENTER);
        southPanel.add(infoPanel, BorderLayout.SOUTH); // Moved to SOUTH

        this.add(southPanel, BorderLayout.SOUTH);

        CoffeeMenuListeners listeners = new CoffeeMenuListeners(controller);

        logoutButton.addActionListener(listeners.getLogoutButtonListener());

        createOrderButton.addActionListener(listeners.getCreateOrderButtonListener());

        addNewCoffeeButton.addActionListener(listeners.getAddNewCoffeeButtonListener());

    }

    private JLabel getCreditLabel() {
        JLabel creditLabel = new JLabel("Available Credit: $" + String.format("%.2f", 0f));
        creditLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        creditLabel.setForeground(new Color(79, 55, 48));
        creditLabel.setBorder(new EmptyBorder(10, 20, 10, 20)); // Padding

        return creditLabel;
    }

    @Override
    public String getTitle() {
        return super.getTitle() + " - " + title;
    }
}