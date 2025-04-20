package listeners.CoffeeMenuActionMenus;

import controllers.AppController;
import enums.ViewType;
import stores.AuthStore;
import stores.SelectedCustomerStore;
import utils.DialogUtils;
import views.SuperView;

import java.awt.event.ActionListener;

/**
 * Provides {@code ActionListener} implementations for actions within the
 * account menu
 * of the coffee menu view. These listeners handle updating account information,
 * viewing account details, deleting the account, logging out, and adding
 * credits.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */
public class AccountMenuListeners {
    private final AppController controller;
    private final SuperView view;
    private final AuthStore authStore;
    private final SelectedCustomerStore selectedCustomerStore;

    /**
     * Constructs a new {@code AccountMenuListeners} with the specified application
     * controller and the current super view. It initializes the {@code AuthStore}
     * and {@code SelectedCustomerStore} instances.
     *
     * @param controller The application controller providing access to services and
     *                   navigation.
     * @param view       The current {@code SuperView} from which the menu actions
     *                   are triggered.
     */
    public AccountMenuListeners(AppController controller, SuperView view) {
        this.controller = controller;
        this.view = view;
        this.authStore = AuthStore.getInstance();
        this.selectedCustomerStore = SelectedCustomerStore.getInstance();
    }

    /**
     * Returns an {@code ActionListener} that navigates the application to the
     * update account view, setting the currently logged-in user as the selected
     * customer in the {@code SelectedCustomerStore}.
     *
     * @return An {@code ActionListener} for updating the account.
     */
    public ActionListener getUpdateAccountActionListener() {
        return e -> {
            selectedCustomerStore.set(authStore.get());
            controller.setDisplay(ViewType.UPDATE_ACCOUNT_VIEW);
        };
    }

    /**
     * Returns an {@code ActionListener} that navigates the application to the
     * customer detail view, setting the currently logged-in user as the selected
     * customer in the {@code SelectedCustomerStore}.
     *
     * @return An {@code ActionListener} for viewing account details.
     */
    public ActionListener getViewAccountActionListener() {
        return e -> {
            selectedCustomerStore.set(authStore.get());
            controller.setDisplay(ViewType.CUSTOMER_DETAIL_VIEW);
        };
    }

    /**
     * Returns an {@code ActionListener} that handles the deletion of the user's
     * account.
     * It prompts the user for confirmation before proceeding with the deletion
     * using
     * {@link DialogUtils#showConfirmation(java.awt.Component, String)}. If
     * confirmed,
     * it calls the customer service to delete the account and then navigates to the
     * login view.
     *
     * @param customerId The ID of the customer to be deleted (should be the
     *                   logged-in user's ID).
     * @return An {@code ActionListener} for deleting the account.
     */
    public ActionListener getDeleteAccountActionListener(int customerId) {
        return e -> {
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

    /**
     * Returns an {@code ActionListener} that handles the logout action. It clears
     * the authentication store, calls the customer service to perform any logout
     * operations, and then navigates the application back to the login view.
     *
     * @return An {@code ActionListener} for logging out.
     */
    public ActionListener getLogoutActionListener() {
        return e -> {
            authStore.clear();
            controller.getCustomerService().logout();
            controller.setDisplay(ViewType.LOGIN_VIEW);
        };
    }

    /**
     * Returns an {@code ActionListener} that displays a dialog for adding credits
     * to the currently logged-in user's account. It sets the logged-in user as the
     * selected customer in the {@code SelectedCustomerStore} before showing the
     * dialog
     * using {@link DialogUtils#showAddCreditDialog(java.awt.Frame, AppController)}.
     *
     * @return An {@code ActionListener} for adding credits.
     */
    public ActionListener getAddCreditsActionListener() {
        return e -> {
            selectedCustomerStore.set(authStore.get());
            DialogUtils.showAddCreditDialog(view, controller);
        };
    }
}