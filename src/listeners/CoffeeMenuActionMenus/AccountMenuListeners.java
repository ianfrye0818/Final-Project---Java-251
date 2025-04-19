package listeners.CoffeeMenuActionMenus;

import controllers.AppController;
import entites.Customer;
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
            setSelectedCustomer();
            controller.setDisplay(ViewType.UPDATE_ACCOUNT_VIEW);
        };
    }

    public ActionListener getViewAccountActionListener() {

        return e -> {
            setSelectedCustomer();
            controller.setDisplay(ViewType.CUSTOMER_DETAIL_VIEW);
        };
    }

    public ActionListener getDeleteAccountActionListener(int customerId) {
        return e -> {
            setSelectedCustomer();
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
            setSelectedCustomer();
            DialogUtils.showAddCreditDialog(view, controller);
        };
    }

    private void setSelectedCustomer() {
        Customer loggedInCustomer = controller.getLoggedinCustomerStore().get();
        if (loggedInCustomer == null) {
            DialogUtils.showError(view, "No customer logged in");
            return;
        }
        controller.getSelectedCustomerStore().set(loggedInCustomer);
    }
}
