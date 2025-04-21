package listeners;

import java.awt.event.ActionListener;
import java.util.Objects;

import controllers.AppController;
import entites.Customer;
import enums.ViewType;
import stores.AuthStore;
import utils.DialogUtils;
import views.SuperView;

/**
 * Provides {@code ActionListener} implementations for actions within the
 * customer
 * details view. These listeners handle deleting a customer account, adding
 * credit
 * to a customer's account, and navigating back to a previous view.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */

public class CustomerDetailsListeners {

  private final AppController controller;
  private final SuperView view;

  /**
   * Constructs a new {@code CustomerDetailsListeners} with the specified
   * application
   * controller and the current super view.
   *
   * @param controller The application controller providing access to services and
   *                   navigation.
   * @param view       The current {@code SuperView} where the customer details
   *                   are displayed.
   */
  public CustomerDetailsListeners(AppController controller, SuperView view) {
    this.controller = controller;
    this.view = view;
  }

  /**
   * Returns an {@code ActionListener} that handles the deletion of a customer
   * account.
   * It prompts the user for confirmation before proceeding with the deletion
   * using
   * {@link DialogUtils#showConfirmation(java.awt.Component, String)}. The
   * confirmation
   * message varies depending on whether the account being deleted is the
   * currently
   * logged-in user's account. If confirmed, it calls the customer service to
   * delete
   * the account, displays a success message, and navigates back to the specified
   * returning view. If any error occurs during the deletion process, an error
   * message
   * is displayed.
   *
   * @param customerId    The ID of the customer account to be deleted.
   * @param returningView The {@code ViewType} to navigate back to after the
   *                      deletion
   *                      (or if the deletion is cancelled).
   * @return An {@code ActionListener} for deleting a customer account.
   */
  public ActionListener getDeleteAccountButtonListener(Integer customerId, ViewType returningView) {
    return e -> {

      try {
        String message = "";
        // check if account being deleted is current account
        Customer currentCustomer = AuthStore.getInstance().get();
        System.out.println("currentCustomer: " + currentCustomer.getCustomerId());
        System.out.println("customerId: " + customerId);
        if (Objects.equals(customerId, currentCustomer.getCustomerId())) {
          message = "You are trying to delete your own account.\nPlease note that this action is irreversible.\nAfter deletion, your current session end and you will not be able to login with this account again";
        } else {
          message = "Are you sure you want to delete this account?";
        }

        boolean isConfirmed = DialogUtils.showConfirmation(view, message);
        if (!isConfirmed) {
          return;
        }

        controller.getCustomerService().deleteCustomer(customerId);
        DialogUtils.showSuccess(view, "Account deleted successfully");
        if (Objects.equals(customerId, currentCustomer.getCustomerId())) {
          AuthStore.getInstance().clear();
          controller.setDisplay(ViewType.LOGIN_VIEW);
        } else {
          controller.setDisplay(returningView);
        }
      } catch (Exception ex) {
        DialogUtils.showError(view, "Failed to delete account.");
      }
    };
  }

  /**
   * Returns an {@code ActionListener} that displays a dialog for adding credit to
   * the specified customer's account using
   * {@link DialogUtils#showAddCreditDialog(java.awt.Frame, AppController)}.
   *
   * @param customerId The ID of the customer account to add credit to (currently
   *                   not
   *                   used within the listener's action, but might be intended
   *                   for future use).
   * @return An {@code ActionListener} for displaying the add credit dialog.
   */
  public ActionListener getAddCreditButtonListener(int customerId) {
    return e -> {
      DialogUtils.showAddCreditDialog(view, controller);
    };
  }

  /**
   * Returns an {@code ActionListener} that navigates the application back to the
   * specified {@code returningView}.
   *
   * @param returningView The {@code ViewType} to navigate back to.
   * @return An {@code ActionListener} for navigating back.
   */
  public ActionListener getBackButtonListener(ViewType returningView) {
    return e -> {
      controller.setDisplay(returningView);
    };
  }
}