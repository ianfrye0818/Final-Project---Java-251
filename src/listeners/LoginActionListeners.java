package listeners;

import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import controllers.AppController;
import enums.ViewType;
import services.ValidatorService;
import views.SuperView;

public class LoginActionListeners {
  private final AppController appController;
  private final SuperView view;
  private JTextField emailField;
  private JPasswordField passwordField;

  public LoginActionListeners(AppController appController, SuperView view, JTextField emailField,
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
          showError("Invalid email format.");
          return;
        }

        if (password.isEmpty()) {
          showError("Password is required.");
        }
        appController.getAuthService().login(email);
        appController.setDisplay(ViewType.COFFEE_MENU_VIEW);
      } catch (Exception e1) {
        System.out.println(e1.getMessage());
        showError(e1.getMessage());
      }
    };
  }

  public ActionListener getCreateAccountButtonListener() {
    return e -> appController.setDisplay(ViewType.CREATE_ACCOUNT_VIEW);
  }

  private void showError(String message) {
    JOptionPane.showMessageDialog(view, message, "Validation Error",
        JOptionPane.ERROR_MESSAGE);
  }
}
