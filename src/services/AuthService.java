package services;

import controllers.AppController;
import dto.ValidateCreateAccountDto;
import models.Customer;

public class AuthService {

    private final AppController controller;

    public AuthService(AppController controller) {
        this.controller = controller;
    }

    public void login(String email) {
        Customer customer = this.controller.getCustomerService().getCustomerByEmail(email);
        if (customer == null) {
            throw new RuntimeException("Invalid email or password");
        }
        this.controller.getCustomerStore().set(customer);
    }

    public void logout() {
        this.controller.getCustomerStore().clear();
    }

    public void register(ValidateCreateAccountDto customer) {
        boolean isSuccess = this.controller.getCustomerService().createCustomer(customer);
        if (isSuccess && customer.getEmail() != null) {
            this.login(customer.getEmail());
        } else {
            throw new RuntimeException("Failed to create customer");
        }
    }
}
