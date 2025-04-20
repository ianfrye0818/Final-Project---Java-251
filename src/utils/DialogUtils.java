package utils;

import java.awt.Component;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import components.AddCreditDialog;
import controllers.AppController;
import views.SuperView;

/**
 * A utility class providing static methods for displaying various types of
 * dialog boxes to the user, such as error messages, success messages,
 * validation errors, confirmation prompts, and custom dialogs like the
 * {@link AddCreditDialog}.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */
public class DialogUtils {

  /**
   * Displays an error message dialog.
   *
   * @param parent  The parent {@code Component} for the dialog, used for
   *                positioning.
   * @param message The error message to display.
   */
  public static void showError(Component parent, String message) {
    JOptionPane.showMessageDialog(parent, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Displays a success message dialog.
   *
   * @param parent  The parent {@code Component} for the dialog, used for
   *                positioning.
   * @param message The success message to display.
   */
  public static void showSuccess(Component parent, String message) {
    JOptionPane.showMessageDialog(parent, message, "Success", JOptionPane.INFORMATION_MESSAGE);
  }

  /**
   * Displays a validation error message dialog.
   *
   * @param parent  The parent {@code Component} for the dialog, used for
   *                positioning.
   * @param message The validation error message to display.
   */
  public static void showValidationError(Component parent, String message) {
    JOptionPane.showMessageDialog(parent, message, "Validation Error", JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Displays a confirmation dialog with "Yes" and "No" options.
   *
   * @param parentComponent The parent {@code Component} for the dialog.
   * @param message         The confirmation message to display.
   * @return {@code true} if the user clicks "Yes", {@code false} otherwise.
   */
  public static boolean showConfirmation(Component parentComponent, String message) {
    return JOptionPane.showConfirmDialog(
        parentComponent,
        message,
        "Confirm",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION;
  }

  /**
   * Displays a custom "Add Credit" dialog.
   *
   * @param parent     The parent {@link SuperView} for the dialog.
   * @param controller The {@link AppController} instance, likely used to handle
   *                   actions from the dialog.
   */
  public static void showAddCreditDialog(SuperView parent, AppController controller) {
    JDialog addCreditDialog = new AddCreditDialog(parent, controller);
    addCreditDialog.setVisible(true);
  }
}