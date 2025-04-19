package listeners;

import controllers.AppController;
import dto.CreateCoffeeDto;
import dto.UpdateCoffeeDto;
import enums.ViewType;
import utils.DialogUtils;
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

    public ActionListener getDeleteButtonListener(int coffeeId) {
        return e -> {
            boolean confirmation = DialogUtils.showConfirmation(view,
                    "Are you sure you want to delete this coffee?");
            if (confirmation) {
                appController.getCoffeeService().deleteCoffee(coffeeId);
                DialogUtils.showSuccess(view, "Coffee deleted successfully");
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
                CreateCoffeeDto coffee = new CreateCoffeeDto.Builder()
                        .setName(nameField.getText().trim())
                        .setDescription(descriptionField.getText().trim())
                        .setPrice(Double.parseDouble(priceField.getText().trim()))
                        .setInStock(inStockBox.isSelected())
                        .build();

                appController.getCoffeeService().createCoffee(coffee);
                DialogUtils.showSuccess(view, "Coffee created successfully");
                appController.setDisplay(ViewType.COFFEE_MENU_VIEW);
            } catch (Exception ex) {
                DialogUtils.showError(view, "Failed to create coffee: " + ex.getMessage());
            }
        };
    }

    public ActionListener getUpdateButtonListener(int coffeeId) {
        return e -> {
            if (validateInputs()) {
                return;
            }

            try {
                UpdateCoffeeDto coffee = new UpdateCoffeeDto.Builder()
                        .setCoffeeId(coffeeId)
                        .setName(nameField.getText().trim())
                        .setDescription(descriptionField.getText().trim())
                        .setPrice(Double.parseDouble(priceField.getText().trim()))
                        .setInStock(inStockBox.isSelected())
                        .build();

                appController.getCoffeeService().updateCoffee(coffee);
                DialogUtils.showSuccess(view, "Coffee updated successfully");
                appController.setDisplay(ViewType.COFFEE_MENU_VIEW);
            } catch (Exception ex) {
                DialogUtils.showError(view, "Failed to update coffee: " + ex.getMessage());
            }
        };
    }

    private boolean validateInputs() {
        String name = nameField.getText().trim();
        String description = descriptionField.getText().trim();
        String priceStr = priceField.getText().trim();

        if (name.isEmpty() || description.isEmpty() || priceStr.isEmpty()) {
            DialogUtils.showValidationError(view, "All fields are required.");
            return true;
        }

        try {
            double price = Double.parseDouble(priceStr);
            if (price <= 0) {
                DialogUtils.showValidationError(view, "Price must be greater than zero.");
                return true;
            }
        } catch (NumberFormatException e) {
            DialogUtils.showValidationError(view, "Price must be a valid number.");
            return true;
        }

        return false;
    }

}