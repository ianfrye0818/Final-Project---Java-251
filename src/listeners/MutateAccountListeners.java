package listeners;

import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controllers.AppController;
import dto.ValidateCreateAccountDto;
import enums.ViewType;
import services.ValidatorService;
import views.SuperView;

public class MutateAccountListeners {
  private final AppController appController;
  private final SuperView view;
  private final JTextField firstNameField;
  private final JTextField lastNameField;
  private final JTextField streetField;
  private final JTextField cityField;
  private final JTextField stateField;
  private final JTextField zipField;
  private final JTextField phoneField;
  private final JTextField emailField;
  private final JPasswordField passwordField;

  public MutateAccountListeners(
      AppController appController,
      SuperView view,
      JTextField firstNameField,
      JTextField lastNameField,
      JTextField streetField,
      JTextField cityField,
      JTextField stateField,
      JTextField zipField,
      JTextField phoneField,
      JTextField emailField,
      JPasswordField passwordField) {
    this.appController = appController;
    this.view = view;
    this.firstNameField = firstNameField;
    this.lastNameField = lastNameField;
    this.streetField = streetField;
    this.cityField = cityField;
    this.stateField = stateField;
    this.zipField = zipField;
    this.phoneField = phoneField;
    this.emailField = emailField;
    this.passwordField = passwordField;
  }

  public ActionListener getBackButtonListener(ViewType viewType) {
    return e -> appController.setDisplay(viewType);
  }

  public ActionListener getMutateButtonListener() {
    return e -> {
      ValidateCreateAccountDto dto = buildDto();

      if (!validateDto(dto)) {
        return;
      }

      try {
        appController.getAuthService().register(dto);
        appController.setDisplay(ViewType.COFFEE_MENU_VIEW);
      } catch (Exception e1) {
        System.out.println(e1.getMessage());
        showError("Failed to create account.");
      }
    };
  }

  private ValidateCreateAccountDto buildDto() {
    return new ValidateCreateAccountDto.Builder()
        .setFirstName(firstNameField.getText().trim())
        .setLastName(lastNameField.getText().trim())
        .setStreet(streetField.getText().trim())
        .setCity(cityField.getText().trim())
        .setState(stateField.getText().trim())
        .setZip(zipField.getText().trim())
        .setPhone(phoneField.getText().trim())
        .setEmail(emailField.getText().trim())
        .setPassword(new String(passwordField.getPassword()))
        .build();
  }

  private boolean validateDto(ValidateCreateAccountDto dto) {
    if (!ValidatorService.isValidCustomer(dto)) {
      showError("All fields are required.");
      return false;
    }

    if (!ValidatorService.isValidEmail(dto.getEmail())) {
      showError("Invalid email format.");
      return false;
    }

    if (!ValidatorService.isValidPhoneNumber(dto.getPhone())) {
      showError("Phone number must be 10 digits (e.g., 1234567890)");
      return false;
    }

    if (!ValidatorService.isValidZipCode(dto.getZip())) {
      showError("Zip code must be 5 digits (e.g., 12345)");
      return false;
    }

    if (doesCustomerExist(dto)) {
      showError("Customer already exists.");
      return false;
    }

    return true;
  }

  private void showError(String message) {
    JOptionPane.showMessageDialog(view, message, "Validation Error",
        JOptionPane.ERROR_MESSAGE);
  }

  private boolean doesCustomerExist(ValidateCreateAccountDto dto) {
    try {
      return appController.getCustomerService().getCustomerByEmail(dto.getEmail()) != null;
    } catch (Exception e) {
      return false;
    }
  }
}
