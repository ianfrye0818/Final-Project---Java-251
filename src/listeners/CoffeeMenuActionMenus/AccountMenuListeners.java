package listeners.CoffeeMenuActionMenus;

import controllers.AppController;
import enums.ViewType;
import stores.AuthStore;
import stores.SelectedCustomerStore;
import utils.DialogUtils;
import views.SuperView;

import java.awt.event.ActionListener;

public class AccountMenuListeners {
    private final AppController controller;
    private final SuperView view;
    AuthStore authStore;
    SelectedCustomerStore selectedCustomerStore;

    public AccountMenuListeners(AppController controller, SuperView view) {
        this.controller = controller;
        this.view = view;
        authStore = AuthStore.getInstance();
        selectedCustomerStore = SelectedCustomerStore.getInstance();
    }

    public ActionListener getUpdateAccountActionListener() {
        return e -> {
            // view.setSelectedCustomer(view.getLoggedInUser());
            selectedCustomerStore.set(authStore.get());
            controller.setDisplay(ViewType.UPDATE_ACCOUNT_VIEW);
        };
    }

    public ActionListener getViewAccountActionListener() {

        return e -> {
            // view.setSelectedCustomer(view.getLoggedInUser());
            selectedCustomerStore.set(authStore.get());
            controller.setDisplay(ViewType.CUSTOMER_DETAIL_VIEW);
        };
    }

    public ActionListener getDeleteAccountActionListener(int customerId) {
        return e -> {
            // view.setSelectedCustomer(view.getLoggedInUser());
            selectedCustomerStore.set(authStore.get());
            boolean confirmation = DialogUtils.showConfirmation(
                    view,
                    "You are trying to delete your own account.\nPlease note that this action is irreversible.\nAfter deletion, you current session will persist, but you will not be able to login again.");

            // Delete Account
            if (confirmation) {
                controller.getCustomerService().deleteCustomer(customerId);

                controller.setDisplay(ViewType.LOGIN_VIEW);
            }
        };
    }

    public ActionListener getLogoutActionListener() {
        return e -> {
            authStore.clear();
            controller.getCustomerService().logout();
            controller.setDisplay(ViewType.LOGIN_VIEW);
        };
    }

    public ActionListener getAddCreditsActionListener() {
        return e -> {
            // view.setSelectedCustomer(view.getLoggedInUser());
            selectedCustomerStore.set(authStore.get());
            DialogUtils.showAddCreditDialog(view, controller);
        };
    }

}
