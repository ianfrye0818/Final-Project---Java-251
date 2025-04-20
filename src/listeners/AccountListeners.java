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

/**
 * Provides {@code ActionListener} implementations for actions related to
 * customer accounts,
 * such as creating a new account and updating an existing account. It handles
 * input
 * validation and interacts with the customer service to perform these
 * operations.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */

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

    /**
     * Constructs a new {@code AccountListeners} with references to the application
     * controller, the current view, and the input fields for customer information.
     *
     * @param appController  The application controller providing access to services
     *                       and navigation.
     * @param view           The current {@code SuperView} where the account actions
     *                       are performed.
     * @param firstNameField The text field for the customer's first name.
     * @param lastNameField  The text field for the customer's last name.
     * @param streetField    The text field for the customer's street address.
     * @param cityField      The text field for the customer's city.
     * @param stateField     The text field for the customer's state.
     * @param zipField       The text field for the customer's zip code.
     * @param phoneField     The text field for the customer's phone number.
     * @param emailField     The text field for the customer's email address.
     * @param passwordField  The password field (currently not used in this class,
     *                       but might be in associated UI).
     */
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

    /**
     * Returns an {@code ActionListener} that navigates the application back to the
     * specified {@code ViewType}.
     *
     * @param viewType The {@code ViewType} to navigate back to.
     * @return An {@code ActionListener} for navigating back.
     */
    public ActionListener getBackButtonListener(ViewType viewType) {
        return e -> appController.setDisplay(viewType);
    }

    /**
     * Returns an {@code ActionListener} that handles the creation of a new customer
     * account.
     * It builds a {@link CreateCustomerDto} from the input fields, validates the
     * DTO,
     * checks if a customer with the provided email already exists, and if all
     * checks pass,
     * attempts to create the account using the customer service. Upon successful
     * creation,
     * it logs in the new user and navigates to the coffee menu view. If any error
     * occurs
     * during validation or account creation, appropriate error messages are
     * displayed.
     *
     * @return An {@code ActionListener} for creating a new account.
     */
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

    /**
     * Returns an {@code ActionListener} that handles updating an existing customer
     * account.
     * It builds an {@link UpdateCustomerDto} from the input fields and the provided
     * customer ID and credit limit, validates the DTO, and then attempts to update
     * the
     * account using the customer service. If the updated customer is the currently
     * logged-in user, the {@link AuthStore} is updated. Upon successful update, a
     * success
     * message is displayed, and the application navigates to the coffee menu view.
     * If
     * any error occurs during validation or account update, an error message is
     * displayed.
     *
     * @param customerId  The ID of the customer to be updated.
     * @param creditLimit The current credit limit of the customer.
     * @return An {@code ActionListener} for updating the account.
     */
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

    /**
     * Builds a {@link CreateCustomerDto} from the values in the input fields.
     *
     * @return A new {@code CreateCustomerDto} instance.
     */
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

    /**
     * Builds an {@link UpdateCustomerDto} from the values in the input fields,
     * along
     * with the provided customer ID and credit limit.
     *
     * @param customerId  The ID of the customer to be updated.
     * @param creditLimit The current credit limit of the customer.
     * @return A new {@code UpdateCustomerDto} instance.
     */
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

    /**
     * Validates the fields of a {@link CreateCustomerDto} using the
     * {@link ValidatorService}.
     * If any validation fails, an appropriate error message is displayed to the
     * user.
     *
     * @param dto The {@code CreateCustomerDto} to validate.
     * @return {@code true} if the DTO is valid; {@code false} otherwise.
     */
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

    /**
     * Checks if a customer with the email address in the provided
     * {@link CreateCustomerDto}
     * already exists in the database. If a customer with the same email is found, a
     * validation error is displayed.
     *
     * @param dto The {@code CreateCustomerDto} containing the email to check.
     * @return {@code true} if a customer with the email exists; {@code false}
     *         otherwise.
     */
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