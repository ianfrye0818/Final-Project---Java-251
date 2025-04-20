package listeners;

import controllers.AppController;
import dto.CreateCustomerDto;
import dto.UpdateCustomerDto;
import entites.Customer;
import enums.ViewType;
import services.ValidatorService;
import stores.AuthStore;
import utils.DialogUtils;
import views.SuperView;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.Objects;

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
    }

    public ActionListener getBackButtonListener(ViewType viewType) {
        return e -> appController.setDisplay(viewType);
    }

    public ActionListener getCreateAccountButtonListener() {
        return e -> {
            CreateCustomerDto dto = buildDto();

            if (!isValidDto(dto) || doesCustomerExist(dto)) {
                return;
            }

            try {
                // create customer
                appController.getCustomerService().createCustomer(dto);
                DialogUtils.showSuccess(view, "Account created successfully");
                // login customer
                appController.getCustomerService().login(dto.getEmail());
                // set display to coffee menu
                appController.setDisplay(ViewType.COFFEE_MENU_VIEW);
            } catch (Exception e1) {
                System.out.println(e1.getMessage());
                DialogUtils.showError(view, "Failed to create account.");
            }
        };
    }

    public ActionListener getUpdateAccountButtonListener(int customerId, double creditLimit) {
        return e -> {
            UpdateCustomerDto dto = buildUpdateDto(customerId, creditLimit);

            if (!isValidDto(dto)) {
                return;
            }

            try {
                Customer updatedCustomer = appController.getCustomerService().updateCustomer(dto);
                if (Objects.equals(updatedCustomer.getCustomerId(), AuthStore.getInstance().get().getCustomerId())) {
                    AuthStore.getInstance().set(updatedCustomer);
                }
                DialogUtils.showSuccess(view, "Account updated successfully");
                appController.setDisplay(ViewType.COFFEE_MENU_VIEW);
            } catch (Exception e1) {
                System.out.println(e1.getMessage());
                DialogUtils.showError(view, "Failed to update account.");
            }
        };
    }

    private CreateCustomerDto buildDto() {
        return new CreateCustomerDto.Builder()
                .setFirstName(firstNameField.getText().trim())
                .setLastName(lastNameField.getText().trim())
                .setStreet(streetField.getText().trim())
                .setCity(cityField.getText().trim())
                .setState(stateField.getText().trim())
                .setZip(zipField.getText().trim())
                .setPhone(phoneField.getText().trim())
                .setEmail(emailField.getText().trim())
                .build();
    }

    private UpdateCustomerDto buildUpdateDto(int customerId, double creditLimit) {
        return new UpdateCustomerDto.Builder()
                .setCustomerId(customerId)
                .setFirstName(firstNameField.getText().trim())
                .setLastName(lastNameField.getText().trim())
                .setStreet(streetField.getText().trim())
                .setCity(cityField.getText().trim())
                .setState(stateField.getText().trim())
                .setZip(zipField.getText().trim())
                .setPhone(phoneField.getText().trim())
                .setCreditLimit(creditLimit)
                .setEmail(emailField.getText().trim())
                .build();
    }

    private boolean isValidDto(CreateCustomerDto dto) {
        if (!ValidatorService.isValidCustomer(dto)) {
            DialogUtils.showValidationError(view, "All fields are required.");
            return false;
        }

        if (!ValidatorService.isValidEmail(dto.getEmail())) {
            DialogUtils.showValidationError(view, "Invalid email format.");
            return false;
        }

        if (!ValidatorService.isValidPhoneNumber(dto.getPhone())) {
            DialogUtils.showValidationError(view, "Phone number must be 10 digits (e.g., 1234567890)");
            return false;
        }

        if (!ValidatorService.isValidZipCode(dto.getZip())) {
            DialogUtils.showValidationError(view, "Zip code must be 5 digits (e.g., 12345)");
            return false;
        }

        return true;
    }

    private boolean doesCustomerExist(CreateCustomerDto dto) {
        try {
            boolean exsists = appController.getCustomerService().getCustomerByEmail(dto.getEmail()) != null;
            if (exsists) {
                DialogUtils.showValidationError(view, "Customer already exists.");
                return true;
            }

            return false;
        } catch (Exception e) {
            return false;
        }
    }

}
