package listeners;

import controllers.AppController;
import enums.ViewType;
import services.ValidatorService;
import utils.DialogUtils;
import views.SuperView;

import javax.swing.*;
import java.awt.event.ActionListener;

public class LoginViewListeners {
    private final AppController appController;
    private final SuperView view;
    private final JTextField emailField;
    private final JPasswordField passwordField;

    public LoginViewListeners(AppController appController, SuperView view, JTextField emailField,
            JPasswordField passwordField) {
        this.appController = appController;
        this.view = view;
        this.emailField = emailField;
        this.passwordField = passwordField;
    }

    public ActionListener getLoginButtonListener() {
        return e -> {
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());

            try {

                boolean isEmailValid = ValidatorService.isValidEmail(email);
                if (!isEmailValid) {
                    DialogUtils.showValidationError(view, "Invalid email format.");
                    return;
                }

                if (password.isEmpty()) {
                    DialogUtils.showValidationError(view, "Password is required.");
                    return;
                }
                appController.getCustomerService().login(email);
                appController.setDisplay(ViewType.COFFEE_MENU_VIEW);
            } catch (Exception e1) {
                System.out.println(e1.getMessage());
                DialogUtils.showError(view, e1.getMessage());
            }
        };
    }

    public ActionListener getCreateAccountButtonListener() {
        return e -> appController.setDisplay(ViewType.CREATE_ACCOUNT_VIEW);
    }

}
