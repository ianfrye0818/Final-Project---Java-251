package listeners.CoffeeMenuActionMenus;

import controllers.AppController;
import enums.ViewType;
import utils.DialogUtils;
import views.SuperView;

import java.awt.event.ActionListener;

public class AccountMenuListeners {
    private final AppController controller;
    private final SuperView view;

    public AccountMenuListeners(AppController controller, SuperView view) {
        this.controller = controller;
        this.view = view;
    }

    public ActionListener getUpdateAccountActionListener() {
        return e -> {
            view.setSelectedCustomer(view.getLoggedInUser());
            controller.setDisplay(ViewType.UPDATE_ACCOUNT_VIEW);
        };
    }

    public ActionListener getViewAccountActionListener() {

        return e -> {
            view.setSelectedCustomer(view.getLoggedInUser());
            controller.setDisplay(ViewType.CUSTOMER_DETAIL_VIEW);
        };
    }

    public ActionListener getDeleteAccountActionListener(int customerId) {
        return e -> {
            view.setSelectedCustomer(view.getLoggedInUser());
            boolean confirmation = DialogUtils.showConfirmation(
                    view,
                    "You are trying to delete your own account.\nPlease note that this action is irreversible.\nAfter deletion, you current session will persist, but you will not be able to login again.");

            // Delete Account
            if (confirmation) {
                controller.getCustomerService().deleteCustomer(customerId);
                controller.getLoggedinCustomerStore().clear();
                controller.setDisplay(ViewType.LOGIN_VIEW);
            }
        };
    }

    public ActionListener getLogoutActionListener() {
        return e -> {
            controller.getSelectedCustomerStore().clear();
            controller.getCustomerService().logout();
            controller.setDisplay(ViewType.LOGIN_VIEW);
        };
    }

    public ActionListener getAddCreditsActionListener() {
        return e -> {
            view.setSelectedCustomer(view.getLoggedInUser());
            DialogUtils.showAddCreditDialog(view, controller);
        };
    }

    // private void setSelectedCustomer() {
    // Customer loggedInCustomer = controller.getLoggedinCustomerStore().get();
    // if (loggedInCustomer == null) {
    // DialogUtils.showError(view, "No customer logged in");
    // return;
    // }
    // System.out.println("Setting selected customer: " + loggedInCustomer);
    // controller.getSelectedCustomerStore().set(loggedInCustomer);
    // System.out.println("Selected customer: " +
    // controller.getSelectedCustomerStore().get());
    // }
}
