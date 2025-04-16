package listeners;

import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import controllers.AppController;
import enums.ViewType;
import models.Coffee;
import views.SuperView;

public class MutateCoffeeListeners {
  private final AppController appController;
  private final SuperView view;
  private final JTextField nameField;
  private final JTextComponent descriptionField;
  private final JTextField priceField;
  private final JCheckBox inStockBox;

  public MutateCoffeeListeners(
      AppController appController,
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

  public ActionListener getMutationButtonListener() {
    return e -> {
      if (!validateInputs()) {
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

  private boolean validateInputs() {
    String name = nameField.getText().trim();
    String description = descriptionField.getText().trim();
    String priceStr = priceField.getText().trim();

    if (name.isEmpty() || description.isEmpty() || priceStr.isEmpty()) {
      showError("All fields are required.");
      return false;
    }

    try {
      double price = Double.parseDouble(priceStr);
      if (price <= 0) {
        showError("Price must be greater than zero.");
        return false;
      }
    } catch (NumberFormatException e) {
      showError("Price must be a valid number.");
      return false;
    }

    return true;
  }

  private void showError(String message) {
    JOptionPane.showMessageDialog(view, message, "Validation Error",
        JOptionPane.ERROR_MESSAGE);
  }
}