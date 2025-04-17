package listeners;

import controllers.AppController;
import dto.ValidateCreateAccountDto;
import enums.ViewType;
import models.Customer;
import services.ValidatorService;
import views.SuperView;

import javax.swing.*;
import java.awt.event.ActionListener;

public class AccountListeners {
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

    public AccountListeners(
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

            if (validateDto(dto)) {
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

    public ActionListener getUpdateCoffeeButtonListener(int customerId) {
        return e -> {
            ValidateCreateAccountDto dto = buildDto();

            if (validateDto(dto)) {
                return;
            }

            try {
                Customer customer = dto.toCustomer(customerId);
                appController.getCustomerService().updateCustomer(customer);
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
            return true;
        }

        if (!ValidatorService.isValidEmail(dto.getEmail())) {
            showError("Invalid email format.");
            return true;
        }

        if (!ValidatorService.isValidPhoneNumber(dto.getPhone())) {
            showError("Phone number must be 10 digits (e.g., 1234567890)");
            return true;
        }

        if (!ValidatorService.isValidZipCode(dto.getZip())) {
            showError("Zip code must be 5 digits (e.g., 12345)");
            return true;
        }

        if (doesCustomerExist(dto)) {
            showError("Customer already exists.");
            return true;
        }

        return false;
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
