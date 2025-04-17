package listeners;

import controllers.AppController;
import enums.ViewType;
import views.SuperView;

import javax.swing.*;

import components.AddCreditDialog;

import java.awt.event.ActionListener;

public class AccountMenuListeners {
    private final AppController controller;
    private final SuperView view;

    public AccountMenuListeners(AppController controller, SuperView view) {
        this.controller = controller;
        this.view = view;
    }

    public ActionListener getUpdateAccountActionListener() {
        return e -> controller.setDisplay(ViewType.UPDATE_ACCOUNT_VIEW);
    }

    public ActionListener getViewAccountActionListener() {
        return e -> controller.setDisplay(ViewType.CUSTOMER_DETAIL_VIEW);
    }

    public ActionListener getDeleteAccountActionListener(int customerId) {
        return e -> {
            // Show confirmation dialog
            int confirmation = JOptionPane.showConfirmDialog(
                    view,
                    "Are you sure you want to delete your account? This action cannot not be undone!",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION);

            // Delete Account
            if (confirmation == JOptionPane.YES_OPTION) {
                controller.getCustomerService().deleteCustomer(customerId);
                controller.getCustomerStore().clear();
                controller.setDisplay(ViewType.LOGIN_VIEW);
            }
        };
    }

    public ActionListener getLogoutActionListener() {
        return e -> {
            controller.getAuthService().logout();
            controller.setDisplay(ViewType.LOGIN_VIEW);
        };
    }

    public ActionListener getAddCreditsActionListener() {
        return e -> {
            JDialog addCreditsDialog = new AddCreditDialog(view, controller, controller.getCustomerStore().get());
            addCreditsDialog.setVisible(true);
        };
    }
}
