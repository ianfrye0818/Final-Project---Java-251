package listeners;

import controllers.AppController;
import enums.ViewType;
import models.Coffee;
import views.SuperView;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.event.ActionListener;

public class CoffeeListeners {
    private final AppController appController;
    private final SuperView view;
    private final JTextField nameField;
    private final JTextComponent descriptionField;
    private final JTextField priceField;
    private final JCheckBox inStockBox;
    private int coffeeId;

    public CoffeeListeners(AppController appController,
            SuperView view,
            JTextField nameField,
            JTextComponent descriptionField,
            JTextField priceField,
            JCheckBox inStockBox,
            int coffeeId) {
        this.appController = appController;
        this.view = view;
        this.nameField = nameField;
        this.descriptionField = descriptionField;
        this.priceField = priceField;
        this.inStockBox = inStockBox;
        this.coffeeId = coffeeId;
    }

    public CoffeeListeners(AppController appController,
            SuperView view,
            JTextField nameField,
            JTextComponent descriptionField,
            JTextField priceField,
            JCheckBox inStockBox) {
        this.appController = appController;
        this.view = view;
        this.nameField = nameField;
        this.descriptionField = descriptionField;
        this.priceField = priceField;
        this.inStockBox = inStockBox;
    }

    public ActionListener getBackButtonListener() {
        return e -> appController.setDisplay(ViewType.COFFEE_MENU_VIEW);
    }

    public ActionListener getDeleteButtonListener() {
        return e -> {
            int confirmation = JOptionPane.showConfirmDialog(view,
                    "Are you sure you want to delete this coffee?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                appController.getCoffeeService().deleteCoffee(coffeeId);
                appController.setDisplay(ViewType.COFFEE_MENU_VIEW);
            }
        };
    }

    public ActionListener getCreateButtonListener() {
        return e -> {
            if (validateInputs()) {
                return;
            }

            try {
                Coffee coffee = new Coffee.Builder()
                        .setCoffeeName(nameField.getText().trim())
                        .setCoffeeDescription(descriptionField.getText().trim())
                        .setPrice(Double.parseDouble(priceField.getText().trim()))
                        .setInStock(inStockBox.isSelected())
                        .build();

                appController.getCoffeeService().createCoffee(coffee);
                appController.setDisplay(ViewType.COFFEE_MENU_VIEW);
            } catch (Exception ex) {
                showError("Failed to create coffee: " + ex.getMessage());
            }
        };
    }

    public ActionListener getUpdateButtonListener() {
        return e -> {
            if (validateInputs()) {
                return;
            }

            try {
                Coffee coffee = new Coffee.Builder()
                        .setCoffeeId(coffeeId)
                        .setCoffeeName(nameField.getText().trim())
                        .setCoffeeDescription(descriptionField.getText().trim())
                        .setPrice(Double.parseDouble(priceField.getText().trim()))
                        .setInStock(inStockBox.isSelected())
                        .build();

                appController.getCoffeeService().updateCoffee(coffee);
            } catch (Exception ex) {
                showError("Failed to create coffee: " + ex.getMessage());
            }
        };
    }

    private boolean validateInputs() {
        String name = nameField.getText().trim();
        String description = descriptionField.getText().trim();
        String priceStr = priceField.getText().trim();

        if (name.isEmpty() || description.isEmpty() || priceStr.isEmpty()) {
            showError("All fields are required.");
            return true;
        }

        try {
            double price = Double.parseDouble(priceStr);
            if (price <= 0) {
                showError("Price must be greater than zero.");
                return true;
            }
        } catch (NumberFormatException e) {
            showError("Price must be a valid number.");
            return true;
        }

        return false;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(view, message, "Validation Error",
                JOptionPane.ERROR_MESSAGE);
    }
}