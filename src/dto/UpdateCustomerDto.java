package dto;

import entites.Customer;

/**
 * A data transfer object (DTO) class for updating a customer.
 * It contains the customer ID, first name, last name, email, phone, street,
 * city, state, zip, and credit limit of a customer.
 * 
 * @author Ian Frye
 * @version 1.0
 * @since 2025-04-20
 */

public class UpdateCustomerDto extends CreateCustomerDto {
  private final int customerId;

  public UpdateCustomerDto(int customerId, String firstName, String lastName, String email, String phone, String street,
      String city, String state, String zip, double creditLimit) {
    super(firstName, lastName, email, phone, street, city, state, zip, creditLimit);
    this.customerId = customerId;
  }

  public int getCustomerId() {
    return customerId;
  }

  public static class Builder {
    private int customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String street;
    private String city;
    private String state;
    private String zip;
    private double creditLimit;

    public Builder setCustomerId(int customerId) {
      this.customerId = customerId;
      return this;
    }

    public Builder setFirstName(String firstName) {
      this.firstName = firstName;
      return this;
    }

    public Builder setLastName(String lastName) {
      this.lastName = lastName;
      return this;
    }

    public Builder setEmail(String email) {
      this.email = email;
      return this;
    }

    public Builder setPhone(String phone) {
      this.phone = phone;
      return this;
    }

    public Builder setStreet(String street) {
      this.street = street;
      return this;
    }

    public Builder setCity(String city) {
      this.city = city;
      return this;
    }

    public Builder setState(String state) {
      this.state = state;
      return this;
    }

    public Builder setZip(String zip) {
      this.zip = zip;
      return this;
    }

    public Builder setCreditLimit(double creditLimit) {
      this.creditLimit = creditLimit;
      return this;
    }

    public UpdateCustomerDto build() {
      return new UpdateCustomerDto(customerId, firstName, lastName, email, phone, street, city, state, zip,
          creditLimit);
    }
  }

  public static UpdateCustomerDto fromCustomer(Customer customer) {
    return new UpdateCustomerDto(customer.getCustomerId(), customer.getFirstName(), customer.getLastName(),
        customer.getEmail(), customer.getPhone(), customer.getStreet(), customer.getCity(), customer.getState(),
        customer.getZip(), customer.getCreditLimit());
  }

  public Customer toCustomer() {
    return new Customer.Builder()
        .setCustomerId(customerId)
        .setFirstName(getFirstName())
        .setLastName(getLastName())
        .setEmail(getEmail())
        .setPhone(getPhone())
        .setStreet(getStreet())
        .setCity(getCity())
        .setState(getState())
        .setZip(getZip())
        .setCreditLimit(getCreditLimit())
        .build();
  }
}
