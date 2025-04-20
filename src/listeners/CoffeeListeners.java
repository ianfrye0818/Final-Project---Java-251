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

/**
 * Provides {@code ActionListener} implementations for actions related to coffee
 * management,
 * such as creating, updating, and deleting coffee entries. It handles input
 * validation
 * and interacts with the coffee service to perform these operations.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */

public class CoffeeListeners {
    private final AppController appController;
    private final SuperView view;
    private final JTextField nameField;
    private final JTextComponent descriptionField;
    private final JTextField priceField;
    private final JCheckBox inStockBox;

    /**
     * Constructs a new {@code CoffeeListeners} with references to the application
     * controller, the current view, and the input fields for coffee information.
     *
     * @param appController    The application controller providing access to
     *                         services and navigation.
     * @param view             The current {@code SuperView} where the coffee
     *                         actions are performed.
     * @param nameField        The text field for the coffee name.
     * @param descriptionField The text component for the coffee description.
     * @param priceField       The text field for the coffee price.
     * @param inStockBox       The checkbox indicating if the coffee is in stock.
     */
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

    /**
     * Returns an {@code ActionListener} that navigates the application back to the
     * coffee menu view.
     *
     * @return An {@code ActionListener} for navigating back to the coffee menu.
     */
    public ActionListener getBackButtonListener() {
        return e -> appController.setDisplay(ViewType.COFFEE_MENU_VIEW);
    }

    /**
     * Returns an {@code ActionListener} that handles the deletion of a coffee
     * entry.
     * It prompts the user for confirmation before proceeding with the deletion
     * using
     * {@link DialogUtils#showConfirmation(java.awt.Component, String)}. If
     * confirmed,
     * it calls the coffee service to delete the coffee and then displays a success
     * message and navigates back to the coffee menu view.
     *
     * @param coffeeId The ID of the coffee to be deleted.
     * @return An {@code ActionListener} for deleting a coffee.
     */
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

    /**
     * Returns an {@code ActionListener} that handles the creation of a new coffee
     * entry.
     * It first validates the input fields using the {@link #validateInputs()}
     * method.
     * If the inputs are valid, it builds a {@link CreateCoffeeDto} from the input
     * values
     * and then attempts to create the coffee using the coffee service. Upon
     * successful
     * creation, it displays a success message and navigates back to the coffee menu
     * view.
     * If any error occurs during input validation or coffee creation, an
     * appropriate
     * error message is displayed.
     *
     * @return An {@code ActionListener} for creating a new coffee.
     */
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

    /**
     * Returns an {@code ActionListener} that handles the updating of an existing
     * coffee entry.
     * It first validates the input fields using the {@link #validateInputs()}
     * method.
     * If the inputs are valid, it builds an {@link UpdateCoffeeDto} from the input
     * values
     * and the provided coffee ID, and then attempts to update the coffee using the
     * coffee
     * service. Upon successful update, it displays a success message and navigates
     * back
     * to the coffee menu view. If any error occurs during input validation or
     * coffee update,
     * an appropriate error message is displayed.
     *
     * @param coffeeId The ID of the coffee to be updated.
     * @return An {@code ActionListener} for updating a coffee.
     */
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

    /**
     * Validates the input fields for creating or updating a coffee. It checks if
     * the
     * name, description, and price fields are non-empty and if the price is a valid
     * positive number. If any validation fails, an appropriate error message is
     * displayed
     * to the user.
     *
     * @return {@code true} if any of the inputs are invalid; {@code false} if all
     *         inputs are valid.
     */
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