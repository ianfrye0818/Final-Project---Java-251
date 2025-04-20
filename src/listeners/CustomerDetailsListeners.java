package listeners;

import java.awt.event.ActionListener;
import java.util.Objects;

import controllers.AppController;
import entites.Customer;
import enums.ViewType;
import stores.AuthStore;
import utils.DialogUtils;
import views.SuperView;

public class CustomerDetailsListeners {

  private final AppController controller;
  private final SuperView view;

  public CustomerDetailsListeners(AppController controller, SuperView view) {
    this.controller = controller;
    this.view = view;
  }

  public ActionListener getDeleteAccountButtonListener(Integer customerId, ViewType returningView) {
    return e -> {

      try {
        String message = "";
        // check if account being deleted is current account
        Customer currentCustomer = AuthStore.getInstance().get();
        System.out.println("currentCustomer: " + currentCustomer.getCustomerId());
        System.out.println("customerId: " + customerId);
        if (Objects.equals(customerId, currentCustomer.getCustomerId())) {
          message = "You are trying to delete your own account.\nPlease note that this action is irreversible.\nAfter deletion, you current session will persist, but you will not be able to login again.";
        } else {
          message = "Are you sure you want to delete this account?";
        }

        boolean isConfirmed = DialogUtils.showConfirmation(view, message);
        if (!isConfirmed) {
          return;
        }

        controller.getCustomerService().deleteCustomer(customerId);
        DialogUtils.showSuccess(view, "Account deleted successfully");
        controller.setDisplay(returningView);
      } catch (Exception ex) {
        DialogUtils.showError(view, "Failed to delete account.");
      }
    };
  }

  public ActionListener getAddCreditButtonListener(int customerId) {
    return e -> {
      DialogUtils.showAddCreditDialog(view, controller);
    };
  }

  public ActionListener getBackButtonListener(ViewType returningView) {
    return e -> {
      controller.setDisplay(returningView);
    };
  }
}
