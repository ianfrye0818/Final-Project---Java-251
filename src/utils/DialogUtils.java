package utils;

import java.awt.Component;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import components.AddCreditDialog;
import controllers.AppController;
import entites.Customer;
import views.SuperView;

public class DialogUtils {
  public static void showError(Component parent, String message) {
    JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  public static void showSuccess(Component parent, String message) {
    JOptionPane.showMessageDialog(parent, message, "Success", JOptionPane.INFORMATION_MESSAGE);
  }

  public static void showValidationError(Component parent, String message) {
    JOptionPane.showMessageDialog(parent, message, "Validation Error", JOptionPane.ERROR_MESSAGE);
  }

  public static boolean showConfirmation(Component parentComponent, String message) {
    return JOptionPane.showConfirmDialog(
        parentComponent,
        message,
        "Confirm",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION;
  }

  public static void showAddCreditDialog(SuperView parent, AppController controller) {
    JDialog addCreditDialog = new AddCreditDialog(parent, controller);
    addCreditDialog.setVisible(true);
  }
}
