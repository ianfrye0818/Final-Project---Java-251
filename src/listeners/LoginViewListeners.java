package listeners;

import controllers.AppController;
import enums.ViewType;
import services.ValidatorService;
import utils.DialogUtils;
import views.SuperView;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Provides {@code ActionListener} implementations for actions within the login
 * view,
 * such as handling the login attempt and navigating to the create account view.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */

public class LoginViewListeners {
    private final AppController appController;
    private final SuperView view;
    private final JTextField emailField;
    private final JPasswordField passwordField;

    /**
     * Constructs a new {@code LoginViewListeners} with references to the
     * application
     * controller, the login view, and the input fields for email and password.
     *
     * @param appController The application controller providing access to services
     *                      and navigation.
     * @param view          The {@code SuperView} representing the login view.
     * @param emailField    The text field where the user enters their email
     *                      address.
     * @param passwordField The password field where the user enters their password.
     */
    public LoginViewListeners(AppController appController, SuperView view, JTextField emailField,
            JPasswordField passwordField) {
        this.appController = appController;
        this.view = view;
        this.emailField = emailField;
        this.passwordField = passwordField;
    }

    /**
     * Returns an {@code ActionListener} that handles the login attempt. It
     * retrieves
     * the entered email and password, performs basic client-side validation on the
     * email format and password presence, and then attempts to log in the user
     * using
     * the customer service. Upon successful login, it navigates to the coffee menu
     * view.
     * If validation fails or an error occurs during login, appropriate error
     * messages
     * are displayed to the user.
     *
     * @return An {@code ActionListener} for the login button.
     */
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

    /**
     * Returns an {@code ActionListener} that navigates the application to the view
     * for creating a new account. This listener is typically attached to a "Create
     * Account" button on the login view.
     *
     * @return An {@code ActionListener} for navigating to the create account view.
     */
    public ActionListener getCreateAccountButtonListener() {
        return e -> appController.setDisplay(ViewType.CREATE_ACCOUNT_VIEW);
    }

}